<script>
import Chat from '@/components/ProjectChat.vue'
import { ProjectControllerApi } from '@/api/apis/ProjectControllerApi';
import { Configuration } from '@/api/runtime';
import { UserControllerApi } from '@/api/apis/UserControllerApi'
import * as userUtils from "../user.js";

export default {
  components: {
    chat: Chat,
  },

  data() {
    return {
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
    };
  },

  methods: {
    init() {
      this.user = userUtils.getLoggedUser();
      this.projectId = Number(this.$route.params.projectId);
      this.getAllProjects();
      this.getAllUsers();
    },

    removeMember(memberId) {
      this.removeUser(Number(this.projectId), Number(memberId));
      this.members = this.members.filter(member => member.id !== memberId);
      this.updateSelectableUsers();
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
      } catch (error) {
        console.error("Błąd podczas pobierania użytkowników:", error);
        this.errorMessage = 'Nie udało się pobrać użytkowników.';
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

    async getAllProjects() {
      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const requestParams = { ownerId: this.user.loggedUser.id };
        const response = await projectControllerApi.getAllProjects(requestParams);
        this.projects = response;
        const found = this.projects.find(p => p.id === Number(this.projectId));
        if (found) {
          this.project = found;
          this.editedTitle = found.title;
          this.editedDescription = found.description;
          this.members = found.members;

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

    async removeUser(projectId, userId) {
      console.log('removeUserFromProject called with:', { projectId, userId });

      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      try {
        const requestBody = { projectId, userId };

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
  <div class="project-details" v-if="project">
    <h2 class="title">Szczegóły projektu</h2>
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
        <div class="value">{{this.project.completionDate}}</div>
      </div>
      <div class="row">
        <div class="label">Właściciel</div>
        <div class="value">{{ user.loggedUser.username }}</div>
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
                  v-if="hoveredMemberId === member.id"
                  @click="removeMember(member.id)"
              >
    ×
  </span>
            </li>
          </ul>
        </div>
        <div v-if="!toggleUserList" class="add-button" @click="showUserList()">+</div>

        <select style="height: fit-content" v-if="toggleUserList" v-model="selectedUserId" @change="handleSelectChange"   ref="userSelect">
          <option v-for="user in userList" :key="user.id" :value="user.id">
            {{ user.username }}
          </option>
        </select>


      </div>
    </div>

    <button
        @click="updateProject"
        :disabled="!titleChanged && !descriptionChanged"
    >
      Zapisz
    </button>

    <chat />
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
}

.member-item {
  display: flex;
  align-items: center;
  padding: 0.3rem 0.5rem;
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

.member-item:hover .remove-icon {
  display: inline;
}

.project-details {
  padding: 2rem;
}

.title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
</style>
