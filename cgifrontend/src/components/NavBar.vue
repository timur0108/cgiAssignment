<template>
  <nav class="navbar">
    <div class="logo">
      <a href="/">project</a>
    </div>
    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/about">About</a></li>
    </ul>
  </nav>
</template>

<script>
export default {

};
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #578dff;
  padding: 10px 20px;
  color: white;
}

.navbar .logo a {
  font-size: 24px;
  text-decoration: none;
  color: white;
  font-weight: bold;
}

.nav-links {
  list-style: none;
  display: flex;
  gap: 15px;
  margin: 0;
  padding: 0;
}

.nav-links li {
  display: inline;
}

.nav-links a {
  text-decoration: none;
  color: white;
  font-size: 18px;
  transition: color 0.3s ease;
}

.nav-links a:hover {
  color: #f0a500;
}
</style>
