<template>
  <div class="booking-container">
    <h2 class="booking-title">Booking Details</h2>

    <div v-if="bookingData" class="booking-form">
      <div class="flight-details">
        <h3>Flight Information</h3>
        <div class="route-info">
          <div class="route-item">
            <strong>Departure:</strong>
            <p>{{ bookingData.departureCity }}</p>
            <p class="date-time">{{ bookingData.departureDate }} at {{ bookingData.departureTime }}</p>
          </div>
          <div class="route-item">
            <strong>Duration:</strong>
            <p>{{ formatDuration(bookingData.flightDuration) }}</p>
          </div>
          <div class="route-item">
            <strong>Destination:</strong>
            <p>{{ bookingData.arrivalCity }}</p>
            <p class="date-time">{{ bookingData.arrivalDate }} at {{ bookingData.arrivalTime }}</p>
          </div>
        </div>

        <div class="price-info">
          <div class="price-item">
            <p><strong>Tourist Class ({{ bookingData.touristClassPrice }}€)</strong></p>
            <p>Seats available: {{ bookingData.touristClassAvailableSeats }}</p>
          </div>
          <div class="price-item">
            <p><strong>Business Class ({{ bookingData.businessClassPrice }}€)</strong></p>
            <p>Seats available: {{ bookingData.businessClassAvailableSeats }}</p>
          </div>
          <div class="price-item">
            <p><strong>First Class ({{ bookingData.firstClassPrice }}€)</strong></p>
            <p>Seats available: {{ bookingData.firstClassAvailableSeats }}</p>
          </div>
        </div>
      </div>

      <h3 class="form-title">Complete Your Booking</h3>

      <div class="form-group">
        <label for="num-tickets">Number of Tickets:</label>
        <input type="number" id="num-tickets" v-model.number="numTickets" min="1" :max="maxSeats" @change="updateTicketPreferences" />
      </div>

      <div v-if="numTickets > 1" class="form-group">
        <label for="seats-together">
          <input type="checkbox" id="seats-together" v-model="seatsTogether" />
          Seats Together
        </label>
      </div>

      <div v-if="seatsTogether" class="preferences-container">
        <div class="preferences-column">
          <label>Seat Preferences (Applied to All Seats):</label>
          <div class="checkbox-group">
            <label>
              <input type="checkbox" v-model="sharedPreferences.window" />
              Near the Window
            </label>
            <label>
              <input type="checkbox" v-model="sharedPreferences.exit" />
              Near the Exit
            </label>
            <label>
              <input type="checkbox" v-model="sharedPreferences.extraLegroom" />
              Extra Leg Space
            </label>
          </div>
        </div>

        <div class="preferences-column">
          <label><strong>Select Class Type for All Seats:</strong></label>
          <select v-model="sharedPreferences.classType">
            <option value="Tourist class">Tourist Class (€{{ bookingData.touristClassPrice }})</option>
            <option value="Business class">Business Class (€{{ bookingData.businessClassPrice }})</option>
            <option value="First class">First Class (€{{ bookingData.firstClassPrice }})</option>
          </select>
        </div>
      </div>

      <div v-for="(preferences, index) in seatPreferences" :key="index" class="ticket-section">

        <div class="ticket-window">
          <h4>Ticket {{ index + 1 }}</h4>
          <div class="form-group-row">
            <div class="form-group left-side">
              <label :for="'name-' + index">Name:</label>
              <input type="text" :id="'name-' + index" v-model="passengers[index].name" placeholder="Enter passenger's name" />

              <label :for="'surname-' + index">Surname:</label>
              <input type="text" :id="'surname-' + index" v-model="passengers[index].surname" placeholder="Enter passenger's surname" />

              <label :for="'email-' + index">Email:</label>
              <input type="email" :id="'email-' + index" v-model="passengers[index].email" placeholder="Enter passenger's email" />

              <label :for="'phone-' + index">Phone:</label>
              <input type="tel" :id="'phone-' + index" v-model="passengers[index].phone" placeholder="Enter passenger's phone" />
            </div>


            <div v-if="!seatsTogether" class="preferences-column right-side">
              <label>Seat Preferences:</label>
              <div class="checkbox-group">
                <label>
                  <input type="checkbox" v-model="seatPreferences[index].window" />
                  Near the Window
                </label>
                <label>
                  <input type="checkbox" v-model="seatPreferences[index].exit" />
                  Near the Exit
                </label>
                <label>
                  <input type="checkbox" v-model="seatPreferences[index].extraLegroom" />
                  Extra Leg Space
                </label>
              </div>

              <label for="'classType-' + index">Seat Class:</label>
              <select v-model="seatPreferences[index].classType">
                <option value="Tourist class">Tourist Class (€{{ bookingData.touristClassPrice }})</option>
                <option value="Business class">Business Class (€{{ bookingData.businessClassPrice }})</option>
                <option value="First class">First Class (€{{ bookingData.firstClassPrice }})</option>
              </select>
            </div>
          </div>
        </div>
      </div>

      <div v-if="totalPrice" class="total-price">
        <h3>Total Price: {{ totalPrice }}€</h3>
      </div>

      <button @click="submitBooking">Continue booking</button>
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
      seatsTogether: false,
      sharedPreferences: {
        classType: 'Tourist class',
        window: false,
        exit: false,
        extraLegroom: false
      },
      seatPreferences: [ {
        classType: 'Tourist class',
        window: false,
        exit: false,
        extraLegroom: false,
      }],
      passengers: [
        {
          name: '',
          surname: '',
          email: '',
          phone: '',
        }
      ]
    };
  },
  computed: {
    totalPrice() {
      return this.seatPreferences.reduce((total, preferences) => {
        let pricePerTicket = 0;
        if (preferences.classType === 'Tourist class') {
          pricePerTicket = this.bookingData.touristClassPrice;
        } else if (preferences.classType === 'Business class') {
          pricePerTicket = this.bookingData.businessClassPrice;
        } else if (preferences.classType === 'First class') {
          pricePerTicket = this.bookingData.firstClassPrice;
        }
        return total + pricePerTicket;
      }, 0).toFixed(2);
    },
    maxSeats() {
      return this.bookingData && this.bookingData.touristClassAvailableSeats;
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
    updateTicketPreferences() {
      if (this.numTickets === 1) {
        this.seatsTogether = false;
      }
      if (this.seatsTogether) {
        this.seatPreferences = Array.from({ length: this.numTickets }, () => ({
          classType: this.sharedPreferences.classType,
          window: this.sharedPreferences.window,
          exit: this.sharedPreferences.exit,
          extraLegroom: this.sharedPreferences.extraLegroom,
        }));

        this.passengers = Array.from({ length: this.numTickets }, () => ({
          name: '',
          surname: '',
          email: '',
          phone: '',
        }));
      } else {
        this.seatPreferences = Array.from({ length: this.numTickets }, () => ({
          classType: 'Tourist class',
          window: false,
          exit: false,
          extraLegroom: false,
        }));

        this.passengers = Array.from({ length: this.numTickets }, () => ({
          name: '',
          surname: '',
          email: '',
          phone: '',
        }));
      }
    },
    submitBooking() {
      const seatPreferencesDTOList = this.seatsTogether
          ? [{
            flightId: this.flightId,
            seatClass: this.sharedPreferences.classType,
            closeToWindow: this.sharedPreferences.window,
            closeToExit: this.sharedPreferences.exit,
            extraLegSpace: this.sharedPreferences.extraLegroom,
            numberOfSeats: this.numTickets,
            ticketNumber: 1,
          }]
          : this.seatPreferences.map((preference, index) => ({
            flightId: this.flightId,
            seatClass: preference.classType,
            closeToWindow: preference.window,
            closeToExit: preference.exit,
            extraLegSpace: preference.extraLegroom,
            numberOfSeats: 1,
            ticketNumber: index + 1,
          }));
      axios.post('http://localhost:8080/api/seat', seatPreferencesDTOList)
          .then(response => {
            console.log("Recommended seats:", response.data);
            this.$router.push({
              name: 'recommended-seats',
              query: {
                seats: JSON.stringify(response.data),
                flightId: this.flightId,
                passengers: JSON.stringify(this.passengers)  // Pass passengers list
              }
            });
          })
          .catch(error => {
            console.error("Error fetching recommended seats:", error);
          });
    }
  }
};
</script>


<style scoped>
.booking-container {
  max-width: 800px;
  margin: auto;
  padding: 30px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  font-family: 'Arial', sans-serif;
}
.booking-title {
  text-align: center;
  color: #2c3e50;
  font-size: 24px;
  margin-bottom: 20px;
}
.flight-details, .booking-form {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 10px;
  margin-bottom: 15px;
}
.route-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;

}
.route-item {
  flex: 1;
  text-align: center;
  background: #e9ecef;
  padding: 12px;
  margin: 5px;
  border-radius: 8px;
  font-size: 14px;
}
.price-info {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 15px;
}
.price-item {
  flex: 1;
  text-align: center;
  background: #e9ecef;
  padding: 12px;
  margin: 5px;
  border-radius: 8px;
  font-size: 14px;
}
.preferences-container select {
  font-size: 14px;
  padding: 6px;
  margin-top: 5px;
  border-radius: 6px;
  border: 1px solid #ddd;
}
.preferences-container label {
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  flex: 1 1 100%;
}
.preferences-container {
  width: 80%;
  max-width: 800px;
  padding: 15px;
  background: #f0f0f0;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin: 20px auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  gap: 15px;
}
.form-group-row {
  display: flex;
  justify-content: space-around;
  gap: 50px;
}
.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin: 8px;
}
.form-group label {
  font-weight: bold;
  font-size: 14px;
}
.ticket-section {
  background: #fff;
  padding: 5px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
.ticket-window {
  display: flex;
  flex-direction: column;
  align-content: center;
  min-width: inherit;
}
.preferences-column {
  display: flex;
  gap: 10px;
  flex-direction: column;
  align-items: center;
}
.preferences-column > label {
  font-weight: bold;
}
.checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: flex-start;
  flex-wrap: wrap;
}
.form-group input,
.form-group select {
  width: 100%;
  padding: 6px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
}
.left-side {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 50%;
}
.right-side {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 50%;
  gap: 20px;
}

.right-side > label {
  font-weight: bold;
}
button {
  width: 100%;
  padding: 12px;
  margin-top: 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
}
button:hover {
  background-color: #2980b9;
}
</style>



