<script>
import axios from "axios";
import FlightCard from "@/components/FlightCard.vue";
import FilterBar from "@/components/FilterBar.vue";

export default {
  name: "HomeView",
  components: { FilterBar, FlightCard },
  data() {
    return {
      flights: [],
      loading: false,
      error: null,
      currentPage: 0,
      pageSize: 10,
      totalPages: 0,
      isLastPage: false,
      filters: {
        departureCity: "",
        arrivalCity: "",
        minPrice: null,
        maxPrice: null,
        departureDate: "",
        arrivalDate: "",
        sortBy: "",
        sortOrder: "",
        selectedClass: "",
        maxDuration: "",
      },
    };
  },
  mounted() {
    this.fetchFlights();
    console.log("mounted");
    console.log(this.flights);
    console.log("fd")
  },

  methods: {
    goToPage(pageNumber) {
      if (pageNumber >= 0 && pageNumber < this.totalPages) {
        this.currentPage = pageNumber;
        this.fetchFlights();
      }
    },
    async fetchFlights() {
      this.loading = true;
      console.log("gf")
      console.log(this.filters);
      try {
        console.log("sort by " + this.filters.sortBy);
        console.log("sort order " + this.filters.sortOrder);
        const response = await axios.get("http://localhost:8080/api/flight", {
          params: {
            page: this.currentPage,
            size: this.pageSize,
            ...this.filters.departureCity && { departureCity: this.filters.departureCity },
            ...this.filters.arrivalCity && { arrivalCity: this.filters.arrivalCity },
            ...this.filters.maxPrice !== null && { maxPrice: this.filters.maxPrice },
            ...this.filters.minPrice !== null && { minPrice: this.filters.minPrice },
            ...this.filters.departureDate && { departureDate: this.filters.departureDate },
            ...this.filters.arrivalDate && { arrivalDate: this.filters.arrivalDate },
            ...this.filters.sortOrder && { sortDirection: this.filters.sortOrder },
            ...this.filters.sortBy && { sortBy: this.filters.sortBy },
            ...this.filters.maxDuration && { maxDuration: this.filters.maxDuration },
            selectedClass: this.filters.selectedClass,
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

    updateFilters(newFilters) {
      this.filters = newFilters;
      this.currentPage = 0;
      this.fetchFlights();
    },
  },
};

</script>

<template>
  <div class="container">
    <FilterBar :onFilterChange="updateFilters" />

    <h2 class="title">Available Flights</h2>

    <div v-if="loading" class="loading">Loading flights...</div>
    <div v-if="error" class="error">{{ error }}</div>

    <div class="grid">
      <FlightCard v-for="flight in flights" :key="flight.id" :flight="flight" />
    </div>

    <div class="pagination">
      <div class="page-buttons-group">
        <button @click="previousPage" :disabled="currentPage === 0" class="btn-arrow">«</button>
        <button v-if="totalPages > 1" @click="goToPage(0)" :disabled="currentPage === 0" class="btn-page">1</button>
        <button v-if="totalPages > 2" @click="goToPage(1)" :disabled="currentPage === 1" class="btn-page">2</button>
        <button v-if="totalPages > 3" @click="goToPage(2)" :disabled="currentPage === 2" class="btn-page">3</button>
      </div>

      <span v-if="totalPages > 3" class="ellipsis">...</span>

      <div class="page-buttons-group">
        <button v-if="totalPages > 3" @click="goToPage(totalPages - 1)" :disabled="isLastPage" class="btn-page">{{ totalPages }}</button>
        <button @click="nextPage" :disabled="isLastPage" class="btn-arrow">»</button>
      </div>
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
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
  gap: 10px;
}

.page-buttons-group {
  display: flex;
  gap: 5px;
}

.btn-page {
  background-color: #f0f0f0;
  color: #333;
  padding: 5px 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
  min-width: 30px;
  text-align: center;
}

.btn-page:hover {
  background-color: #ddd;
}

.btn-page:disabled {
  background-color: #f9f9f9;
  color: #ccc;
  cursor: not-allowed;
  border-color: #f1f1f1;
}

.btn-arrow {
  background-color: #f0f0f0;
  color: #333;
  padding: 5px 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
}

.btn-arrow:hover {
  background-color: #ddd;
}

.btn-arrow:disabled {
  background-color: #f9f9f9;
  color: #ccc;
  cursor: not-allowed;
  border-color: #f1f1f1;
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

.ellipsis {
  font-size: 16px;
  font-weight: bold;
  color: #555;
  padding: 0 5px;
}

.page-buttons-group {
  display: flex;
  gap: 5px;
}

.btn-page {
  background-color: #f0f0f0;
  color: #333;
  padding: 5px 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
  min-width: 30px;
  text-align: center;
}

.btn-page:hover {
  background-color: #ddd;
}

.btn-page:disabled {
  background-color: #f9f9f9;
  color: #ccc;
  cursor: not-allowed;
  border-color: #f1f1f1;
}

.btn-arrow {
  background-color: #f0f0f0;
  color: #333;
  padding: 5px 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
}

.btn-arrow:hover {
  background-color: #ddd;
}

.btn-arrow:disabled {
  background-color: #f9f9f9;
  color: #ccc;
  cursor: not-allowed;
  border-color: #f1f1f1;
}
</style>