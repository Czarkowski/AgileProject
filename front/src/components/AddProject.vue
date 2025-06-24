<template>
  <div class="container">

    <div class="title">Nowy projekt</div>

  <div class="add-project">

    <label for="name">Nazwa</label>
    <input id="name" class="project-name" v-model="projectName" maxlength="100"/>
    <label for="details">Opis</label>
    <textarea id="details" v-model="projectDetails" maxlength="1000"></textarea>

    <!--<div style="display: flex; flex-direction: row; gap: 2vmin;">
    <label for="turnedIn">Oddany?</label>
    <input class="checkbox" type="checkbox" id="turnedIn" v-model="turnedIn" :true-value="1" :false-value="0" @change="handleTurnedInChange()" />
    </div>

    <div v-if="turnedIn">
      <input class="calendar" type="date" v-model="turnedInDate">
    </div>-->

    <div v-if="this.showError" class="error">{{this.errorMessage}}</div>

    <button @click="addProject()">Zapisz</button>


  </div>

    <div class="links back" @click="goBack">Powrót</div>

  </div>
</template>

<script lang="ts">
import { ProjectAddRequestBody } from '@/api/models/ProjectAddRequestBody';
import { ProjectControllerApi } from '@/api/apis/ProjectControllerApi';
import { Configuration } from '@/api/runtime';
import * as userUtils from "../user.js";

export default {
  name: "AddProject",

  data() {
    return {
      projectName: '',
      projectDetails: '',
      projectAddDate: undefined,
      projectId: undefined,
      errorMessage: 'Nazwa jest wymagana.',
      showError: false,
      turnedIn: 0,
      turnedInDate: undefined,
      user: undefined,
    };
  },

  methods: {
    addProject() {
      if (this.projectName === '') {
        this.showError = true;
        return;
      }

      this.showError = false;

      const projectRequestBody: ProjectAddRequestBody = {
        description: this.projectDetails,
        title: this.projectName,
        ownerId: this.user.loggedUser.id,
      };

      const configuration = new Configuration({ accessToken: this.user.token });
      const projectControllerApi = new ProjectControllerApi(configuration);

      projectControllerApi.addProject({ projectAddRequestBody: projectRequestBody })
          .then(() => {
            this.projectAddDate = new Date();
            alert("Utworzono projekt: " + this.projectName);
            console.log(this.turnedIn, this.turnedInDate);

            this.projectName = '';
            this.projectDetails = '';
            this.projectAddDate = undefined;
          })
          .catch((error) => {
            console.error("Błąd podczas dodawania projektu:", error);
          });
    },

    goBack() {
      this.$router.push('/projects/');
    },

    handleTurnedInChange() {
      if (this.turnedIn === 0) {
        this.turnedInDate = undefined;
      }
    },

    init() {
      this.user = userUtils.getLoggedUser();
      console.error("user utils", userUtils.getLoggedUser());
      console.error("logged user:" ,this.user);
    }
  },

  mounted() {
    this.init();
  }
};
</script>


<style scoped>
.add-project {
  width: 100vmin;
  display: flex;
  flex-direction: column;
  gap: 2vmin;
  background-color: #2c3e50;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin: 20px auto;
  padding: 20px;
}


.title{
  text-align: center;
  font-size: 24px;

}
textarea{
  width: 100%;
  height: 20vmin;
}

.links.back{
  margin-left: 0;
  margin-right: auto;
}

.error{
  color:darkred;
}
.checkbox{
  box-shadow: none;
  width: fit-content;
}

</style>