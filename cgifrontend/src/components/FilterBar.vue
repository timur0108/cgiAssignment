<template>
  <div class="filter-bar">
    <div class="filter-section">
      <select v-model="selectedDepartureCity" class="filter-select">
        <option value="">Departure City</option>
        <option v-for="city in departureCities" :key="city" :value="city">{{ city }}</option>
      </select>

      <select v-model="selectedArrivalCity" class="filter-select">
        <option value="">Arrival City</option>
        <option v-for="city in arrivalCities" :key="city" :value="city">{{ city }}</option>
      </select>

      <div class="filter-section narrow">
        <select v-model="sortOption" class="filter-select narrow-input">
          <option value="">Sort By</option>
          <option value="price_asc">Price (Low to High)</option>
          <option value="price_desc">Price (High to Low)</option>
          <option value="duration_asc">Flight Duration (Low to High)</option>
          <option value="duration_desc">Flight Duration (High to Low)</option>
        </select>
      </div>
    </div>

    <div class="filter-section">
      <input v-model="minPrice" type="number" class="filter-input" placeholder="Min Price" min="0" />
      <input v-model="maxPrice" type="number" class="filter-input" placeholder="Max Price" min="0" />
    </div>

    <div class="filter-section">
      <label class="filter-label">Departure Date:</label>
      <input v-model="departureDate" type="date" class="filter-input" />
      <label class="filter-label">Arrival Date:</label>
      <input v-model="arrivalDate" type="date" class="filter-input" />
    </div>

    <div class="filter-section">
      <select v-model="selectedClass" class="filter-select">
        <option value="Tourist class">Tourist class</option>
        <option value="Business class">Business class</option>
        <option value="First class">First class</option>
      </select>
    </div>

    <div class="filter-section">
      <input v-model.number="maxDuration" type="number" class="filter-input" placeholder="Max Flight Duration (hours)" min="0" />
    </div>

    <div class="button-group">
      <button @click="applyFilters" class="apply-filters-btn">Apply Filters</button>
      <button @click="clearFilters" class="clear-filters-btn">Clear Filters</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    onFilterChange: Function,
  },
  data() {
    return {
      arrivalCities: [],
      departureCities: [],
      selectedDepartureCity: "",
      selectedArrivalCity: "",
      minPrice: null,
      maxPrice: null,
      departureDate: "",
      arrivalDate: "",
      sortOption: "",
      selectedClass: "Tourist class",
      maxDuration: null,
    };
  },
  mounted() {
    this.fetchArrivalCities();
    this.fetchDepartureCities();
  },
  computed: {
    sortBy() {
      if (this.sortOption.startsWith("price")) return "price";
      if (this.sortOption.startsWith("duration")) return "duration";
      return "";
    },
    sortOrder() {
      if (this.sortOption.endsWith("asc")) return "asc";
      if (this.sortOption.endsWith("desc")) return "desc";
      return "";
    },
  },
  methods: {
    applyFilters() {
      this.onFilterChange({
        departureCity: this.selectedDepartureCity,
        arrivalCity: this.selectedArrivalCity,
        minPrice: this.minPrice,
        maxPrice: this.maxPrice,
        departureDate: this.departureDate,
        arrivalDate: this.arrivalDate,
        sortBy: this.sortBy,
        sortOrder: this.sortOrder,
        selectedClass: this.selectedClass,
        maxDuration: this.maxDuration ? this.maxDuration * 60 * 60 : null,
      });
    },
    clearFilters() {
      this.selectedDepartureCity = "";
      this.selectedArrivalCity = "";
      this.minPrice = null;
      this.maxPrice = null;
      this.departureDate = "";
      this.arrivalDate = "";
      this.sortOption = "";
      this.selectedClass = "Tourist class";
      this.maxFlightDuration = null;
      this.applyFilters();
    },
    async fetchDepartureCities() {
      try {
        const response = await axios.get("http://localhost:8080/api/flight/departureCity");
        this.departureCities = response.data;
      } catch (error) {
        console.log("Failed to fetch departure cities");
      }
    },
    async fetchArrivalCities() {
      try {
        const response = await axios.get("http://localhost:8080/api/flight/arrivalCity");
        this.arrivalCities = response.data;
      } catch (error) {
        console.log("Failed to fetch arrival cities");
      }
    },
  },
};
</script>


<style scoped>
.filter-bar {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f4f4f4;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.filter-section {
  display: flex;
  gap: 15px;
  width: 100%;
  align-items: center;
}

.narrow {
  max-width: 200px;
  display: flex;
  align-items: center;
}

.narrow-input {
  width: 100px;
}

.filter-label {
  font-size: 16px;
  font-weight: bold;
}

.filter-select,
.filter-input {
  padding: 8px;
  margin: 0;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 16px;
  width: 100%;
}

.button-group {
  grid-column: span 2;
  display: flex;
  gap: 10px;
  justify-content: flex-start;
  margin-top: 20px;
}

.apply-filters-btn,
.clear-filters-btn {
  background-color: #3073e7;
  color: white;
  padding: 8px 16px;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.apply-filters-btn:hover,
.clear-filters-btn:hover {
  background-color: #115aec;
}

@media (max-width: 768px) {
  .filter-bar {
    grid-template-columns: 1fr;
  }

  .filter-section {
    flex-direction: column;
    gap: 10px;
  }

  .button-group {
    flex-direction: column;
    width: 100%;
  }
}
</style>





