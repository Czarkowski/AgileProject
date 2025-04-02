
<template>
  <div class="projects">
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

    <div class="table-container">
    <div v-if="showTable">
    <table border="1">
      <thead>
      <tr>
        <th style="width: 0.5vmin;">ID</th>
        <th style="width: 31.8vmin;">Nazwa</th>
        <th style="width: 73.5vmin;">Opis</th>
        <th style="width: 13vmin;" >Utworzony</th>
        <th style="width: 13vmin;">Data oddania</th>
        <th style="background-color: #2c3e50;"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="project in filteredProjects" :key="project.id"  @click="goToDetails(project.id)">
        <td style="width: 4.5vmin;">{{ project.id }}</td>
        <td style="text-align: left; width: 35vmin; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ project.name }}</td>
        <td style="text-align: left; width: 70vmin; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ project.details }}</td>
        <td style="width: 20vmin;">{{ project.created_at }}</td>
        <td style="width: 20vmin;">{{ project.due_date }}</td>
      </tr>
      </tbody>
    </table>
      </div>
    </div>

  </div>

</template>

<script>
export default {
  name: "Projects",
  data() {
    return {
      showTable: true,
      searchedName: "",
      projects: [
        {
          id: 1,
          name: "System rezerwacji",
          details: "Aplikacja webowa do rezerwacji wizyt w salonie fryzjerskim.",
          created_at: "2024-01-10",
          due_date: "2024-06-15",
        },
        {
          id: 2,
          name: "Sklep internetowy",
          details: "Platforma e-commerce do sprzedaży odzieży online.",
          created_at: "2024-02-05",
          due_date: "2024-08-20",
        },
        {
          id: 3,
          name: "Aplikacja mobilna fitness",
          details: "Aplikacja na Androida i iOS do monitorowania aktywności fizycznej.",
          created_at: "2024-03-12",
          due_date: "2024-09-30",
        },
        {
          id: 1,
          name: "System rezerwacji",
          details: "Aplikacja webowa do rezerwacji wizyt w salonie fryzjerskim.",
          created_at: "2024-01-10",
          due_date: "2024-06-15",
        },
        {
          id: 2,
          name: "Sklep internetowy",
          details: "Platforma e-commerce do sprzedaży odzieży online.",
          created_at: "2024-02-05",
          due_date: "2024-08-20",
        },
        {
          id: 3,
          name: "Aplikacja mobilna fitness",
          details: "Aplikacja na Androida i iOS do monitorowania aktywności fizycznej.",
          created_at: "2024-03-12",
          due_date: "2024-09-30",
        },
        {
          id: 1,
          name: "System rezerwacji",
          details: "Aplikacja webowa do rezerwacji wizyt w salonie fryzjerskim.",
          created_at: "2024-01-10",
          due_date: "2024-06-15",
        },
        {
          id: 2,
          name: "Sklep internetowy",
          details: "Platforma e-commerce do sprzedaży odzieży online.",
          created_at: "2024-02-05",
          due_date: "2024-08-20",
        },
        {
          id: 3,
          name: "Aplikacja mobilna fitness",
          details: "Aplikacja na Androida i iOS do monitorowania aktywności fizycznej.",
          created_at: "2024-03-12",
          due_date: "2024-09-30",
        },
        {
          id: 1,
          name: "System rezerwacji",
          details: "Aplikacja webowa do rezerwacji wizyt w salonie fryzjerskim.",
          created_at: "2024-01-10",
          due_date: "2024-06-15",
        },
        {
          id: 2,
          name: "Sklep internetowy",
          details: "Platforma e-commerce do sprzedaży odzieży online.",
          created_at: "2024-02-05",
          due_date: "2024-08-20",
        },
        {
          id: 3,
          name: "Aplikacja mobilna fitness",
          details: "Aplikacja na Androida i iOS do monitorowania aktywności fizycznej.",
          created_at: "2024-03-12",
          due_date: "2024-09-30",
        },
        {
          id: 1,
          name: "System rezerwacji",
          details: "Aplikacja webowa do rezerwacji wizyt w salonie fryzjerskim.",
          created_at: "2024-01-10",
          due_date: "2024-06-15",
        },
        {
          id: 2,
          name: "Sklep internetowy",
          details: "Platforma e-commerce do sprzedaży odzieży online.",
          created_at: "2024-02-05",
          due_date: "2024-08-20",
        },



      ],
      filteredProjects: [],
    };
  },
  methods: {

    logOut(){
      //todo
    },

    searchName() {
      if (this.searchedName.trim() === '') {
        // Resetujemy filteredProjects do wszystkich projektów
        this.filteredProjects = [...this.projects];
      } else {
        // Filtrujemy projekty na podstawie nazwy
        this.filteredProjects = this.projects.filter((project) =>
            project.name.toLowerCase().includes(this.searchedName.toLowerCase())
        );
      }
      console.error(this.filteredProjects);
      this.showTable = false;
      this.$nextTick(() => {
        this.showTable = true;
      });
    },


    goToAddProject(){
      this.$router.push('/projects/add-project')
    },

    goToDetails(projectId) {
      this.$router.push(`/projects/${projectId}/details`);
    }
  },
  mounted() {
    this.filteredProjects = this.projects; // Domyślnie pokazujemy wszystkie projekty
  },
};
</script>


<style scoped>
.projects {
  display: flex;
  flex-direction: column;
  gap: 2vmin;
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

table tbody { height:530px; overflow-y:scroll; display:block; }
table thead { display:block; }

.links.logout{
  margin-left: 0;
  margin-right: auto;
}
</style>
<script setup>
</script>