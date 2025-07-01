<script lang="ts">
import Chat from '@/components/ProjectChat.vue'
import { ProjectControllerApi } from '@/api/apis/ProjectControllerApi';
import { Configuration } from '@/api/runtime';
import { UserControllerApi } from '@/api/apis/UserControllerApi'
import { FilesControllerApi } from '@/api/apis/FilesControllerApi';
import * as userUtils from "../user.js";
//import { FilesControllerApi, UploadFileRequest } from '@/api/index.js';

export default {
  components: {
    chat: Chat,
  },

  data() {
    return {
      ownerName: undefined,
      isOwner: undefined,
      hoveredMemberId: null,
      projectId: null,
      project: undefined,
      projects: [],
      turnedIn: undefined,
      members: [],
      user: undefined,
      editingField: null,
      editedTitle: '',
      editedDescription: '',
      titleChanged: false,
      descriptionChanged: false,
      users: [],
      userList: [],
      toggleUserList: false,
      selectedFile: null as File | null,
      files: [],
      hoveredFileId: [],
      selectedUserId: undefined,
    };
  },

  methods: {
    async init() {
      this.user = userUtils.getLoggedUser();
      this.projectId = Number(this.$route.params.projectId);

      await this.getAllProjects();
      await this.getAllUsers();
      await this.getProjectFiles();
    },

    async downloadFile(file) {

      try {
        const response = await fetch(`http://localhost:8080/api/projects/${this.projectId}/files/${file.id}`, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${this.user.token}`,
          }
        });

        if (!response.ok) {
          throw new Error("error HTTP: " + response.status);
        }

        const blob = await response.blob();

        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = file.filename || 'plik';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);

      } catch (error) {
        console.error("Błąd podczas pobierania pliku:", error);
        alert("Nie udało się pobrać pliku.");
      }
    },



    async removeFile(file){
      const confirmed = confirm("Czy na pewno chcesz usunąć plik "+ file.filename + "?");
      if (!confirmed) {
        return;
      }
      const configuration = new Configuration({ accessToken: this.user.token });
      const filesControllerApi = new FilesControllerApi(configuration);

      try {
        const requestParams = {
          projectId: Number(this.projectId),
          fileId: Number(file.id)
        };

      await filesControllerApi.deleteFile(requestParams);

        alert("Plik usunięty");
        await this.getProjectFiles();
      } catch (error) {
        console.error("Błąd podczas usuwania pliku:", error);
      }


    },

    truncateFilename(filename) {
      if (filename.length <= 60) {
        return filename;
      }
      return filename.slice(0, 60) + '...';
    },

    goBack(){
        this.$router.push('/projects/');
    },

    removeMember(memberId) {
      this.removeUser(Number(memberId));
      this.members = this.members.filter(member => member.id !== memberId);
      this.updateSelectableUsers();
    },

    openChooseFile(){
      this.$refs.fileInput.click();
      },

    handleFileAdd(event) {
      const selectedFile = event.target.files[0];
      if (!selectedFile) return;

      this.file = selectedFile;

      this.uploadFile();
    },

    updateSelectableUsers() {
      const memberIds = this.members.map(member => member.id);
      const ownerId = this.user.loggedUser.id;
      this.userList = this.users.filter(user =>
          !memberIds.includes(user.id) && user.id !== ownerId
      );
    },

    handleSelectChange() {
      const selectedUser = this.users.find(user => user.id === this.selectedUserId);
      if (selectedUser && !this.members.some(member => member.id === selectedUser.id)) {
        this.members.push({ id: selectedUser.id, username: selectedUser.username });
        console.error(this.members);
      }
      this.addUser(Number(this.projectId),Number(this.selectedUserId));
      this.selectedUserId = '';
      this.toggleUserList = false;
      this.updateSelectableUsers();
    },

    async getAllUsers() {
      const configuration = new Configuration({ accessToken: this.user.token });
      const userControllerApi = new UserControllerApi(configuration);

      try {
        const users = await userControllerApi.getAllUsers();
        this.users = users;
        console.error("Pobrani użytkownicy:", this.users);
        this.getOwnerById(this.project.ownerId);

      } catch (error) {
        console.error("Błąd podczas pobierania użytkowników:", error);
        this.errorMessage = 'Nie udało się pobrać użytkowników.';
      }
    },

    getOwnerById(userId) {
      if (!this.users || this.users.length === 0) {
        console.warn('Brak użytkowników do przeszukania');
        return null;
      }

      const user = this.users.find(u => u.id === userId);
      if (user) {
        this.ownerName = user.username;
      } else {
        console.warn(`Nie znaleziono użytkownika o id ${userId}`);
        this.ownerName = null;
      }
    },

    showUserList(){
      this.updateSelectableUsers();
      this.toggleUserList = true;
      this.$nextTick(() => {
        this.$refs.userSelect.focus();
        this.$refs.userSelect.click();
      });
    },

    async markAsComplete() {
      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const requestParams = { projectId: this.project.id };
        const updatedProject = await projectControllerApi.completeProject(requestParams);

        this.project = updatedProject;
        console.error("mark complete response", this.project);
        this.checkCompletionStatus();
      } catch (error) {
        console.error("Błąd podczas oznaczania projektu jako zakończony:", error);
      }
    },

    async markAsUncomplete() {
      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const requestParams = { projectId: this.project.id };
        const updatedProject = await projectControllerApi.uncompleteProject(requestParams);

        this.project = updatedProject;
        this.checkCompletionStatus();
      } catch (error) {
        console.error("Błąd podczas cofania zakończenia projektu:", error);
      }
    },

    handleCompletion(){
      if(this.turnedIn === 1){
        this.markAsComplete();
      }
      else if (this.turnedIn === 0){
        this.markAsUncomplete();
      }
    },

    checkCompletionStatus(){
      if(this.project.completionDate === undefined)
        this.turnedIn = 0;
      else this.turnedIn = 1;
    },

    async getProjectFiles() {
      const configuration = new Configuration({ accessToken: this.user.token });
      const filesControllerApi = new FilesControllerApi(configuration);

      try {
        const requestParams = {
          projectId: Number(this.projectId)
        };

        const response = await filesControllerApi.listFiles(requestParams);
        this.files = response;

        console.log("Pobrane pliki:", this.files);
      } catch (error) {
        console.error("Błąd podczas pobierania plików:", error);
      }
    },


    async getAllProjects() {
      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const requestParams = {
          ownerId: this.user.loggedUser.id,
          memberId: this.user.loggedUser.id,
          byMember: null,
        };
        const response = await projectControllerApi.getAllProjects(requestParams);
        this.projects = response;
        const found = this.projects.find(p => p.id === Number(this.projectId));
        if (found) {
          this.project = found;
          this.editedTitle = found.title;
          this.editedDescription = found.description;
          this.members = found.members;
          if (this.project.ownerId === this.user.loggedUser.id) {
            this.isOwner = true;
            console.error("jest ownerem");
          }
          else this.isOwner = false;

        }
        console.error(this.project);
        this.checkCompletionStatus();
      } catch (error) {
        console.error("Błąd podczas pobierania projektów:", error);
      }
    },

    async updateProject() {
      console.log('updateProject called with projectId:', this.projectId);

      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const updateRequestBody = {
          title: this.editedTitle,
          description: this.editedDescription,
        };

        await projectControllerApi.updateProject({
          projectId: Number(this.projectId),
          projectUpdateRequestBody: updateRequestBody,
        });

        console.log('Projekt zaktualizowany pomyślnie');

      } catch (error) {
        if (
            error instanceof SyntaxError &&
            error.message.includes('Unexpected end of JSON input')
        ) {
          console.log('Projekt zaktualizowany pomyślnie (pusta odpowiedź)');
          alert('Projekt zaktualizowany pomyślnie');
        } else {
          console.error('Błąd podczas aktualizacji projektu:', error);
          alert('Błąd podczas aktualizacji projektu');
        }      }
    },

    async addUser(projectId, userId) {
      console.log('addUserToProject called with:', { projectId, userId });

      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const requestBody = { projectId, userId };

        await projectControllerApi.addUserToProject({ userAndProjectRequestBody: requestBody });

        console.log('Użytkownik dodany pomyślnie');
        alert('Użytkownik dodany do projektu');
      } catch (error) {
        if (
            error instanceof SyntaxError &&
            error.message.includes('Unexpected end of JSON input')
        ) {
          console.log('Użytkownik dodany pomyślnie (pusta odpowiedź)');
          alert('Użytkownik dodany do projektu');
        } else {
          console.error('Błąd podczas dodawania użytkownika:', error);
          alert('Błąd podczas dodawania użytkownika');
        }
      }
    },

    async removeUser(userId) {
      //console.log('removeUserFromProject called with:', { projectId, userId });

      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const requestBody = {
          projectId: Number(this.projectId),
          userId: userId };

        await projectControllerApi.deleteUserFromProject({ userAndProjectRequestBody: requestBody });

        console.log('Użytkownik usunięty pomyślnie');
        alert('Użytkownik usunięty z projektu');
      } catch (error) {
        if (
            error instanceof SyntaxError &&
            error.message.includes('Unexpected end of JSON input')
        ) {
          console.log('Użytkownik usunięty pomyślnie (pusta odpowiedź)');
          alert('Użytkownik usunięty z projektu');
        } else {
          console.error('Błąd podczas usuwania użytkownika:', error);
          alert('Błąd podczas usuwania użytkownika');
        }
      }
    },

    async uploadFile() {
      if (!this.selectedFile) return;

      const formData = new FormData();
      formData.append("file", this.selectedFile);

      try {
        const response = await fetch(`http://localhost:8080/api/projects/${this.projectId}/files`, {
          method: "POST",
          headers: {
            Authorization: `Bearer ${this.user.token}`,
          },
          body: formData,
        });

        if (response.ok) {
          alert("Plik został dodany pomyślnie!");
          this.selectedFile = null;
          await this.getProjectFiles();
        } else {
          alert("Błąd podczas przesyłania pliku.");
        }
      } catch (error) {
        console.error(error);
      }
    },


    openFilePicker() {
      this.$refs.fileInput.click();
    },

    onFileChange(event: Event) {
      const input = event.target as HTMLInputElement;
      if (input.files?.length) {
        this.selectedFile = input.files[0];
        this.uploadFile();
      }
    },

    startEditing(field) {
      this.editingField = field;
    },

    onTitleInput(event) {
      this.editedTitle = event.target.value;
      this.titleChanged = this.editedTitle !== this.project.title;
    },

    onDescriptionInput(event) {
      this.editedDescription = event.target.value;
      this.descriptionChanged = this.editedDescription !== this.project.description;
    },
  },

  mounted() {
    this.init();
  }
}
</script>


<template>
  <div class="details-main">

  <h2 class="title">Szczegóły projektu</h2>
  <div class="project-details" v-if="project">
    <div class="project-info">
      <div class="row">
        <div class="label">Tytuł</div>
        <input
            type="text"
            v-model="editedTitle"
            @input="onTitleInput"
        />
      </div>
      <div class="row">
        <div class="label">Opis</div>
        <input
            type="text"
            v-model="editedDescription"
            @input="onDescriptionInput"
        />
      </div>
      <div class="row">
        <div class="label">Ukończony</div>
        <div class="value">
          <input class="checkbox" type="checkbox" id="turnedIn" v-model="turnedIn" :true-value="1" :false-value="0" @change="handleCompletion()" />
        </div>
      </div>
      <div v-if="this.turnedIn === 1" class="row">
        <div class="label">Data ukończenia</div>
        <div class="value">{{
            new Date(this.project.completionDate).toLocaleString('pl-PL', {
              weekday: 'long',
              year: 'numeric',
              month: 'long',
              day: 'numeric',
              hour: '2-digit',
              minute: '2-digit'
            })
          }}</div>
      </div>
      <div class="row">
        <div class="label">Właściciel</div>
        <div class="value">{{ this.ownerName }}</div>
      </div>
      <div class="row">
        <div class="label">Członkowie</div>
        <div class="value">
          <ul>
            <li
                v-for="member in members"
                :key="member.id"
                class="member-item"
                @mouseenter="hoveredMemberId = member.id"
                @mouseleave="hoveredMemberId = null"
            >
              {{ member.username }}
              <span
                  class="remove-icon"
                  v-if="hoveredMemberId === member.id && isOwner"
                  @click="removeMember(member.id)"
              >
    ×
  </span>
            </li>
          </ul>
        </div>
        <div v-if="!toggleUserList && isOwner" class="add-button" @click="showUserList()">+</div>

        <select style="height: fit-content" v-if="toggleUserList" v-model="selectedUserId" @change="handleSelectChange"   ref="userSelect">
          <option v-for="user in userList" :key="user.id" :value="user.id">
            {{ user.username }}
          </option>
        </select>


      </div>

      <div class="row">
        <div class="label">Pliki</div>
        <div class="value">
          <div class="add-button" style="margin-left: auto; margin-bottom: 1vmin" @click="openFilePicker">+</div>
          <ul class="file-list">
            <li
                v-for="file in files"
                :key="file.filename"
                class="file-item"
                @mouseenter="hoveredFileId = file.id"
                @mouseleave="hoveredFileId = null"

            >
              {{ truncateFilename(file.filename) }}
              <span
                  class="download-icon"
                  v-if="hoveredFileId === file.id"
                  @click="downloadFile(file)"
              >
<svg width="15px" height="15px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
<path fill-rule="evenodd" clip-rule="evenodd" d="M23 22C23 22.5523 22.5523 23 22 23H2C1.44772 23 1 22.5523 1 22C1 21.4477 1.44772 21 2 21H22C22.5523 21 23 21.4477 23 22Z" fill="#ffffff"/>
<path fill-rule="evenodd" clip-rule="evenodd" d="M13.3099 18.6881C12.5581 19.3396 11.4419 19.3396 10.6901 18.6881L5.87088 14.5114C4.47179 13.2988 5.32933 11 7.18074 11L9.00001 11V3C9.00001 1.89543 9.89544 1 11 1L13 1C14.1046 1 15 1.89543 15 3L15 11H16.8193C18.6707 11 19.5282 13.2988 18.1291 14.5114L13.3099 18.6881ZM11.3451 16.6091C11.7209 16.9348 12.2791 16.9348 12.6549 16.6091L16.8193 13H14.5C13.6716 13 13 12.3284 13 11.5V3L11 3V11.5C11 12.3284 10.3284 13 9.50001 13L7.18074 13L11.3451 16.6091Z" fill="#ffffff"/>
</svg>
  </span>
              <span
                  class="remove-icon"
                  v-if="hoveredFileId === file.id"
                  @click="removeFile(file)"
              >
    ×
  </span>
              <div class="upload-date">{{ new Date(file.uploadDate).toLocaleString('pl-PL', {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
              })}}</div>
            </li>
          </ul>
        </div>
      </div>

      <input
          type="file"
          ref="fileInput"
          style="display: none"
          @change="onFileChange"
      />


    <div class="bottom-panel">
    <div class="links back" @click="goBack">Powrót</div>

    <button style="margin-left: auto"
        @click="updateProject"
        :disabled="!titleChanged && !descriptionChanged"
    >
      Zapisz
    </button>
    </div>


    <chat :user-list="users"></chat>
  </div>
  </div>
  </div>
</template>


<style scoped>
.project-info {
  width: 50vw;
  margin: 1rem auto;
  font-family: Arial, sans-serif;
}

.row {
  display: flex;
  padding: 0.5rem 0;
  border-bottom: 1px solid #ddd;
}

.label {
  flex: 0 0 100px;
  font-family: 'Arial Black', sans-serif;

}

.details-main{
  display: flex;
  flex-direction: column;
}

.add-button{
  background-color: #0d6efd;
  width:3vmin;
  border-radius: 1vmin;
  text-align: center;
  font-weight: bold;
  height: fit-content;
}

.value {
  flex: 1;
  font-family: 'Arial Black', sans-serif;

}

ul, ol{
  background-color: unset;
  display: flex;
}

li{
  margin: 0 1vmin 1vmin 0;
}

.member-item {
  display: flex;
  align-items: center;
  border-radius: 4px;
}

.file-item{
  display: flex;
  align-items: center;
  border-radius: 4px;
}


.remove-icon {
  cursor: pointer;
  color: white;
  background-color: red;
  width: 3vmin;
  text-align: center;
  border-radius: 1vmin;
  font-weight: bold;
  margin-left: 1vmin;
}

.download-icon{
  cursor: pointer;
  color: white;
  background-color: #1B2A41;
  width: 3vmin;
  text-align: center;
  border-radius: 1vmin;
  font-weight: bold;
  margin-left: 1vmin;
}

.member-item:hover .remove-icon {
  display: inline;
}

.file-list{
  flex-direction: column;
  overflow-y: scroll;
  overflow: auto;
  max-height: 240px;
}

.upload-date{
  margin-left: auto;
}

.project-details {
  background-color: #2c3e50;
  padding: 2vmin;
  border-radius: 1vmin;
}

.bottom-panel{
  display: flex;
  margin-top: 1vmin;
}

.file-management{
  display: flex;
  flex-direction: column;
  margin-top: 2vmin;
  margin-bottom: 2vmin;

}

.title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  text-align: center;
}

.links.back {
  margin-left: 0;
  margin-right: auto;
}
</style>
