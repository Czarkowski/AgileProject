<script>
import Chat from '@/components/ProjectChat.vue'
import { ProjectControllerApi } from '@/api/apis/ProjectControllerApi';
import { Configuration } from '@/api/runtime';
import * as userUtils from "../user.js";

export default {
  components: {
    chat: Chat,
  },

  data() {
    return {
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
    };
  },

  methods: {
    init() {
      this.user = userUtils.getLoggedUser();
      this.projectId = Number(this.$route.params.projectId);
      this.getAllProjects();
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
        }
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
          <input class="checkbox" type="checkbox" id="turnedIn" v-model="turnedIn" :true-value="1" :false-value="0" />
        </div>
      </div>
      <div class="row">
        <div class="label">Właściciel</div>
        <div class="value">{{ user.loggedUser.username }}</div>
      </div>
      <div class="row">
        <div class="label">Członkowie</div>
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

.value {
  flex: 1;
  font-family: 'Arial Black', sans-serif;

}

.project-details {
  padding: 2rem;
}

.title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
</style>
