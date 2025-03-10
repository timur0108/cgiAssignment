<template>
  <div class="about">
    <h1>About This Project</h1>
    <p>
      This project was created as part of an internship assessment at <strong>CGI</strong>.
    </p>
    <p>
      Developed by <strong>Timur Sirazitdinov</strong>
    </p>
  </div>
</template>

<script>
export default {
  name: "AboutView",
};
</script>

<style scoped>
.about {
  font-family: Arial, sans-serif;
  max-width: 600px;
  margin: auto;
  padding: 20px;
  text-align: center;
}

h1 {
  color: #333;
}

p {
  font-size: 18px;
  color: #555;
}
</style>
