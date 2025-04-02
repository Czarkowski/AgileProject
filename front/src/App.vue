<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>

{
  margin: 0;
  padding: 0;
}

html, body {
  height: 100%;
  background-color: #1B2A41;
}

#app {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #1B2A41;
}

body {
  font-family: 'Arial Black', sans-serif;
  height: 100%;
  color: white;
}

button {

  padding: 10px 20px;
  background-color: white;
  color: #1B2A41;
  font-family: 'Arial Black', sans-serif;
  border-radius: 10px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
}

button:active {
  transform: translateY(0);
  background-color: #4384b5;
}

input, select, textarea {
  padding: 10px 15px;
  font-family: 'Arial Black', sans-serif;
  border-radius: 8px;
  background-color: white;
  font-size: 16px;
  color: #1B2A41;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input, select, textarea:focus {
  border-color: #4384b5;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
  outline: none;
  color: #1B2A41;
}

input, select, textarea:placeholder {
  color: #aaa;
  font-family: 'Arial Black', sans-serif;
}

.links {
  color: #4384b5;
  text-decoration: underline;
  transition: color 0.3s ease;
}

.links:hover {
  color: #0056b3;
  cursor: pointer;
}

.links:focus {
  outline: none;
  text-decoration: underline;
}


a {
  color: #4384b5;
  text-decoration: underline;
  transition: color 0.3s ease;
}

a {
  color: #0056b3;
  cursor: pointer;
}

a {
  outline: none;
  text-decoration: underline;
}

ul, ol {
  margin: 0;
  padding: 0;
  list-style: none;
  color: white;
  background-color: #2c3e50;

}

li {
  margin-bottom: 10px;
  font-size: 16px;
  color: white;
}

form {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  background-color: #2c3e50;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

form label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: white;
}

table {
  border-collapse: collapse;
  width: 100%;
}
th, td {
  border: 1px solid black;
  padding: 8px;
  text-align: center;
}
th {
  background-color: #4384b5;
}
tr:hover{
  background-color: #2c3e50;
}

html {
  scrollbar-width: thin;
  scrollbar-color: #4384b5 #2c3e50;
}

::-webkit-scrollbar {
  width: 10px;
}

::-webkit-scrollbar-track {
  background: #2c3e50;
}

::-webkit-scrollbar-thumb {
  background: #2c3e50;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: #2c3e50;
}



</style>
