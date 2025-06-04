
<template>
  <div class="projects">
    <div class="links edit-account" @click="editAccount">Edytuj dane</div>
    <div class="links logout" @click="logOut">Wyloguj się</div>

    <div class="title">Lista projektów</div>


    <div class="projects-search">

      <input
          v-model="searchedName"
          placeholder="Szukaj projektu..."
          @keyup.enter="searchName"
      />

      <button @click="searchName">Szukaj</button>
      <div class="links" @click="goToAddProject">Dodaj projekt</div>
    </div>

    <div class="table-container" v-if="showTable">
      <table class="projects-table">
        <thead>
        <tr>
          <th>ID</th>
          <th>Nazwa</th>
          <th>Opis</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="project in filteredProjects" :key="project.id" @click="goToDetails(project.id)" class="clickable-row">
          <td>{{ project.id }}</td>
          <td>{{ project.title }}</td>
          <td>{{ project.description }}</td>
        </tr>
        <tr v-if="filteredProjects.length === 0">
          <td colspan="3" class="no-results">Brak projektów do wyświetlenia</td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>

</template>

<script>
import { ProjectControllerApi } from '@/api/apis/ProjectControllerApi';
import { Configuration } from '@/api/runtime';
import * as userUtils from "../user.js";
export default {
  name: "Projects",
  data() {
    return {
      showTable: true,
      user: undefined,
      searchedName: "",
      projects: [],
      filteredProjects: [],
    };
  },
  methods: {

    logOut() {
      userUtils.logout();
      this.$router.push('/');
    },

      async getAllProjects() {
        const configuration = new Configuration({ accessToken: this.user.token });
        const projectControllerApi = new ProjectControllerApi(configuration);

        try {
          const requestParams = {
            ownerId: this.user.loggedUser.id,
          };

          const response = await projectControllerApi.getAllProjects(requestParams);
          this.projects = response;
          this.filteredProjects = [...this.projects];
          console.error("Pobrane projekty:", this.projects);
        } catch (error) {
          console.error("Błąd podczas pobierania projektów:", error);
        }
    },
    
    editAccount() {
      this.$router.push('/edit-account')
    },

    searchName() {
      if (this.searchedName.trim() === '') {
        this.filteredProjects = [...this.projects];
      } else {
        this.filteredProjects = this.projects.filter((project) =>
            project.title.toLowerCase().includes(this.searchedName.toLowerCase())
        );
      }
      console.error(this.filteredProjects);
      this.showTable = false;
      this.$nextTick(() => {
        this.showTable = true;
      });
    },


    goToAddProject() {
      this.$router.push('/projects/add-project')
    },

    goToDetails(projectId) {
      this.$router.push(`/projects/${projectId}/details`);
    },

    init() {
      this.user = userUtils.getLoggedUser();
      console.error("user utils", userUtils.getLoggedUser());
      this.getAllProjects();
    }

  },

  mounted() {
    this.init();
    //this.filteredProjects = this.projects; // Domyślnie pokazujemy wszystkie projekty
  },
};
</script>


<style scoped>
.projects {
  display: flex;
  flex-direction: column;
  gap: 2vmin;
  width: 50vw;
}

.table-container{

}

.title{
  text-align: center;
  font-size: 24px;

}

.projects-search{
  display: flex;
  gap: 1vmin;
}

.links{
  margin-left: auto;
  align-self: center;
}

.table-container {
  overflow-x: auto;
  border: 1px solid #ddd;
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(44, 62, 80, 0.1);
}

.projects-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 320px;
}

.projects-table thead {
  background-color: #3498db;
  color: white;
  font-weight: 700;
}

.projects-table th,
.projects-table td {
  padding: 0.75rem 1rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
  vertical-align: middle;
  word-break: break-word;
}

.projects-table tbody tr:hover {
  background-color: #f0f8ff;
  cursor: pointer;
}

.clickable-row {
  transition: background-color 0.25s ease;
}

.no-results {
  text-align: center;
  padding: 1rem;
  color: #999;
  font-style: italic;
}


.links.logout{
  margin-left: 0;
  margin-right: auto;
}

.links.edit-account{
  margin-left: 0;
  margin-right: auto;
}


</style>
<script setup>
</script>