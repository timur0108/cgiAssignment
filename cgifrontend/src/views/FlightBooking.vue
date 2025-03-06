<template>
  <div class="booking-container">
    <h2 class="booking-title">Booking Details</h2>

    <div v-if="bookingData" class="flight-details">
      <div class="route-info">
        <div class="route-item">
          <p><strong>Departure:</strong></p>
          <p>{{ bookingData.departureCity }}</p>
          <p>{{ bookingData.departureDate }} | {{ bookingData.departureTime }}</p>
        </div>
        <div class="route-item">
          <p><strong>Arrival:</strong></p>
          <p>{{ bookingData.arrivalCity }}</p>
          <p>{{ bookingData.arrivalDate }} | {{ bookingData.arrivalTime }}</p>
        </div>
      </div>

      <div class="duration-info">
        <p><strong>Duration:</strong> {{ formatDuration(bookingData.flightDuration) }}</p>
      </div>

      <div class="price-info">
        <div class="price-item">
          <p><strong>Tourist Class:</strong> {{ bookingData.touristClassPrice }}€</p>
          <p>Available Seats: {{ bookingData.touristClassAvailableSeats }}</p>
        </div>
        <div class="price-item">
          <p><strong>Business Class:</strong> {{ bookingData.businessClassPrice }}€</p>
          <p>Available Seats: {{ bookingData.businessClassAvailableSeats }}</p>
        </div>
        <div class="price-item">
          <p><strong>First Class:</strong> {{ bookingData.firstClassPrice }}€</p>
          <p>Available Seats: {{ bookingData.firstClassAvailableSeats }}</p>
        </div>
      </div>
    </div>

    <div v-else>
      <p>Loading booking details...</p>
    </div>

    <div v-if="bookingData" class="booking-form">
      <h3 class="form-title">Complete Your Booking</h3>
      <div class="form-row">
        <div class="form-group">
          <label for="num-tickets">Number of Tickets:</label>
          <input type="number" id="num-tickets" v-model="numTickets" min="1" :max="maxSeats" />
        </div>

        <div class="form-group">
          <label for="class-type">Select Class:</label>
          <select id="class-type" v-model="selectedClass">
            <option value="tourist">Tourist Class</option>
            <option value="business">Business Class</option>
            <option value="first">First Class</option>
          </select>
        </div>
      </div>

      <div class="form-group total-price">
        <p><strong>Total Price:</strong> €{{ totalPrice }}</p>
      </div>

      <button @click="submitBooking">Book Now</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      flightId: null,
      bookingData: null,
      numTickets: 1,
      selectedClass: 'tourist',
    };
  },
  computed: {
    totalPrice() {
      let pricePerTicket = 0;

      if (this.selectedClass === 'tourist') {
        pricePerTicket = this.bookingData.touristClassPrice;
      } else if (this.selectedClass === 'business') {
        pricePerTicket = this.bookingData.businessClassPrice;
      } else if (this.selectedClass === 'first') {
        pricePerTicket = this.bookingData.firstClassPrice;
      }

      return (pricePerTicket * this.numTickets).toFixed(2);
    },
    maxSeats() {
      if (this.selectedClass === 'tourist') {
        return this.bookingData.touristClassAvailableSeats;
      } else if (this.selectedClass === 'business') {
        return this.bookingData.businessClassAvailableSeats;
      } else if (this.selectedClass === 'first') {
        return this.bookingData.firstClassAvailableSeats;
      }
      return 0;
    },
  },
  created() {
    this.flightId = this.$route.query.flightId;
    this.fetchBookingDetails();
  },
  methods: {
    formatDuration(durationInSeconds) {
      const hours = Math.floor(durationInSeconds / 3600);
      const minutes = Math.floor((durationInSeconds % 3600) / 60);
      return `${hours}h ${minutes}m`;
    },
    async fetchBookingDetails() {
      try {
        const response = await axios.get(`http://localhost:8080/api/flight/${this.flightId}`);
        this.bookingData = response.data;
      } catch (error) {
        console.error("Error fetching booking details:", error);
      }
    },
    submitBooking() {
      alert('Booking successful!');
    }
  }
};
</script>

<style scoped>
.booking-container {
  max-width: 900px;
  margin: 30px auto;
  padding: 40px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.1);
  font-family: 'Roboto', sans-serif;
}

.booking-title {
  font-size: 32px;
  font-weight: 700;
  color: #2c3e50;
  text-align: center;
  margin-bottom: 30px;
}

.flight-details {
  margin-bottom: 40px;
}

.route-info {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.route-item {
  background-color: #ecf0f1;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ddd;
}

.route-item p {
  margin: 5px 0;
}

.route-item strong {
  color: #007bff;
  font-size: 18px;
}

.duration-info {
  background-color: #ecf0f1;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ddd;
  margin-top: 20px;
  font-size: 16px;
  color: #2c3e50;
}

.price-info {
  margin-top: 30px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.price-item {
  background-color: #ecf0f1;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ddd;
  font-size: 16px;
}

.price-item p {
  margin: 5px 0;
}

.booking-form {
  background-color: #ffffff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: none;
  margin-top: 40px;
}

.form-title {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 30px;
}

.form-row {
  display: flex;
  gap: 30px;
  margin-bottom: 25px;
}

.form-group {
  flex: 1;
}

.booking-form label {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
}

.booking-form input,
.booking-form select {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f7f7f7;
  transition: border-color 0.3s;
}

.booking-form input:focus,
.booking-form select:focus {
  border-color: #007bff;
  outline: none;
}

.total-price {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

button {
  width: 100%;
  padding: 14px;
  background-color: #007bff;
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 30px;
  transition: background-color 0.3s ease-in-out;
}

button:hover {
  background-color: #0056b3;
}

@media (max-width: 768px) {
  .booking-container {
    padding: 20px;
  }

  .booking-title {
    font-size: 28px;
  }

  .route-info {
    grid-template-columns: 1fr;
  }

  .price-info {
    grid-template-columns: 1fr;
  }

  .price-item {
    padding: 15px;
  }

  .booking-form {
    padding: 25px;
  }

  button {
    font-size: 16px;
    padding: 12px;
  }
}
</style>




