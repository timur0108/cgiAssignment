<script>
import axios from "axios";
import FlightCard from "@/components/FlightCard.vue";
import FilterBar from "@/components/FilterBar.vue";

export default {
  name: "HomeView",
  components: {FilterBar, FlightCard },
  data() {
    return {
      flights: [],
      loading: false,
      error: null,
      currentPage: 0,
      pageSize: 10,
      totalPages: 0,
      isLastPage: false,
    };
  },

  mounted() {
    this.fetchFlights();
  },

  methods: {
    async fetchFlights() {
      this.loading = true;
      try {
        const response = await axios.get("http://localhost:8080/api/flight/all", {
          params: {
            page: this.currentPage,
            size: this.pageSize,
          },
        });
        this.flights = response.data.content;
        this.totalPages = response.data.totalPages;
        this.isLastPage = response.data.last;
      } catch (err) {
        this.error = "Failed to load flights.";
      } finally {
        this.loading = false;
      }
    },

    nextPage() {
      if (!this.isLastPage) {
        this.currentPage++;
        this.fetchFlights();
      }
    },

    previousPage() {
      if (this.currentPage > 0) {
        this.currentPage--;
        this.fetchFlights();
      }
    },
  },
};
</script>

<template>
  <div class="container">
    <FilterBar :cities="cities" :onFilterChange="fetchFlights" />

    <h2 class="title">Available Flights</h2>

    <div v-if="loading" class="loading">Loading flights...</div>
    <div v-if="error" class="error">{{ error }}</div>

    <div class="grid">
      <FlightCard v-for="flight in flights" :key="flight.id" :flight="flight" />
    </div>

    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 0" class="btn">Previous</button>
      <span class="page-indicator">Page {{ currentPage + 1 }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="isLastPage" class="btn">Next</button>
    </div>
  </div>
</template>

<style scoped>

.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
  justify-content: center;
  margin-top: 20px;
}


.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
  gap: 15px;
}

.page-indicator {
  font-size: 16px;
  font-weight: bold;
  color: #555;
}

.btn {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
}

.btn:hover {
  background-color: #0056b3;
}

.btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.loading {
  font-size: 18px;
  color: #ff8c00;
  margin-bottom: 15px;
}

.error {
  font-size: 16px;
  color: red;
  margin-bottom: 15px;
}
</style>
