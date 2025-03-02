<template>
  <div class="filter-bar">
    <div class="filter-section">
      <select v-model="selectedDepartureCity" class="filter-select">
        <option value="">Departure City</option>
        <option v-for="city in cities" :key="city" :value="city">{{ city }}</option>
      </select>

      <select v-model="selectedArrivalCity" class="filter-select">
        <option value="">Arrival City</option>
        <option v-for="city in cities" :key="city" :value="city">{{ city }}</option>
      </select>
    </div>

    <div class="filter-section">
      <div class="price-range">
        <input
            v-model="minPrice"
            type="number"
            class="filter-input"
            placeholder="Min Price"
            min="0"
        />
        <input
            v-model="maxPrice"
            type="number"
            class="filter-input"
            placeholder="Max Price"
            min="0"
        />
      </div>

      <div class="date-range">
        <input
            v-model="departureDate"
            type="date"
            class="filter-input"
            placeholder="Departure Date"
        />
        <input
            v-model="arrivalDate"
            type="date"
            class="filter-input"
            placeholder="Arrival Date"
        />
      </div>
    </div>

    <button @click="applyFilters" class="apply-filters-btn">Apply Filters</button>
    <button @click="clearFilters" class="clear-filters-btn">Clear Filters</button>
  </div>
</template>

<script>
export default {
  props: {
    cities: Array,
    onFilterChange: Function, // Function to handle filter updates
  },
  data() {
    return {
      selectedDepartureCity: "",
      selectedArrivalCity: "",
      minPrice: null,
      maxPrice: null,
      departureDate: "",
      arrivalDate: "",
    };
  },
  methods: {
    applyFilters() {
      // Emit the filter data back to the parent component (HomeView)
      this.onFilterChange({
        departureCity: this.selectedDepartureCity,
        arrivalCity: this.selectedArrivalCity,
        minPrice: this.minPrice,
        maxPrice: this.maxPrice,
        departureDate: this.departureDate,
        arrivalDate: this.arrivalDate,
      });
    },
    clearFilters() {
      this.selectedDepartureCity = "";
      this.selectedArrivalCity = "";
      this.minPrice = null;
      this.maxPrice = null;
      this.departureDate = "";
      this.arrivalDate = "";
      this.applyFilters(); // Trigger the parent method with empty filters
    },
  },
};
</script>

<style scoped>
.filter-bar {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f4f4f4;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.filter-section {
  display: flex;
  justify-content: space-between;
  gap: 15px;
  margin-bottom: 15px;
}

.filter-section .price-range,
.filter-section .date-range {
  display: flex;
  gap: 10px;
}

.filter-select,
.filter-input {
  padding: 8px;
  margin: 0;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 16px;
}

.price-range input,
.date-range input {
  width: 120px;
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
  margin-left: 10px;
}

.apply-filters-btn:hover,
.clear-filters-btn:hover {
  background-color: #115aec;
}

@media (max-width: 768px) {
  .filter-bar {
    flex-direction: column;
    align-items: stretch;
  }

  .filter-section {
    flex-direction: column;
    width: 100%;
  }

  .price-range,
  .date-range {
    flex-direction: column;
    gap: 5px;
  }
}
</style>

