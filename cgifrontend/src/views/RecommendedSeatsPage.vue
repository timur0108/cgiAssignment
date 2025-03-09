<template>
  <div class="seat-map-container">
    <div class="recommended-seats">
      <h1>Recommended Seats</h1>
      <div v-if="loading">
        <p>Loading your recommended seats...</p>
      </div>
      <div v-else>
        <div v-if="recommendedSeats.length > 0">
          <ul>
            <li v-for="(seat, index) in recommendedSeats" :key="index" class="seat-item">
              <div class="seat-info">
                <p><strong>Ticket {{ index + 1 }}:</strong></p>
                <p><strong>Class:</strong> {{ seat.classType }}</p>
                <p><strong>Seat number:</strong> {{ seat.seatNumber }}</p>
              </div>
              <div class="passenger-info">
                <p><strong>Name:</strong> {{ passengers[index].name }}</p>
                <p><strong>Surname:</strong> {{ passengers[index].surname }}</p>
                <p><strong>Email:</strong> {{ passengers[index].email }}</p>
                <p><strong>Phone:</strong> {{ passengers[index].phone }}</p>
              </div>
            </li>
          </ul>
        </div>
        <div v-else>
          <p>No recommended seats available. Please try again.</p>
        </div>
      </div>
    </div>

    <div class="seat-map">
      <h2>Seats</h2>
      <div class="legend">
        <div class="legend-item"><span class="seat first-class"></span> First Class</div>
        <div class="legend-item"><span class="seat business-class"></span> Business Class</div>
        <div class="legend-item"><span class="seat economy-class"></span> Economy Class</div>
        <div class="legend-item"><span class="seat unavailable"></span> Unavailable</div>
      </div>

      <div class="plane-body">
        <div v-if="seats.length">
          <div
              class="seat-row"
              v-for="(row, index) in groupedSeats"
              :key="index"
          >
            <div class="seats left">
              <div
                  v-for="seat in row.leftSeats"
                  :key="seat.id"
                  @mouseover="showTooltip(seat, $event)"
                  @mouseleave="hideTooltip"
                  :class="['seat', getSeatClass(seat), isRecommendedSeat(seat) ? 'recommended' : '']"
              >
                <span v-if="seat.isAvailable">{{ seat.seatNumber }}</span>
              </div>
            </div>

            <div class="aisle"></div>

            <div class="seats right">
              <div
                  v-for="seat in row.rightSeats"
                  :key="seat.id"
                  @mouseover="showTooltip(seat, $event)"
                  @mouseleave="hideTooltip"
                  :class="['seat', getSeatClass(seat), isRecommendedSeat(seat) ? 'recommended' : '']"
              >
                <span v-if="seat.isAvailable">{{ seat.seatNumber }}</span>
              </div>
            </div>
          </div>
        </div>
        <p v-else>Loading seats...</p>
      </div>
    </div>
    <div
        v-if="hoveredSeat"
        class="seat-tooltip"
        :style="{ top: tooltipY + 'px', left: tooltipX + 'px' }"
    >
      <p><strong>Seat:</strong> {{ hoveredSeat.seatNumber }}</p>
      <p><strong>Class:</strong> {{ hoveredSeat.classType }}</p>
      <p v-if="hoveredSeat.isAvailable"><strong>Status:</strong> Available</p>
      <p v-if="hoveredSeat.moreLegSpace">More space for legs</p>
      <p v-if="hoveredSeat.closeToExit">Near the exit</p>
    </div>
  </div>
</template>


<script>
import axios from "axios";

export default {
  name: "SeatMapWithRecommendations",
  data() {
    return {
      hoveredSeat: null,
      tooltipX: 0,
      tooltipY: 0,
      passengers: [],
      seats: [],
      flightId: "",
      recommendedSeats: [],
      loading: true,
    };
  },
  computed: {
    groupedSeats() {
      const grouped = {};

      this.seats.forEach((seat) => {
        const rowNumber = parseInt(seat.seatNumber.match(/\d+/)[0], 10);

        if (!grouped[rowNumber]) {
          grouped[rowNumber] = { rowNumber, leftSeats: [], rightSeats: [] };
        }

        if (["A", "B", "C"].includes(seat.seatNumber.slice(-1))) {
          grouped[rowNumber].leftSeats.push(seat);
        } else {
          grouped[rowNumber].rightSeats.push(seat);
        }
      });

      return Object.values(grouped).sort((a, b) => a.rowNumber - b.rowNumber);
    },
  },
  methods: {
    async fetchSeats() {
      try {
        console.log("flightId " + this.flightId);
        const response = await axios.get(`http://localhost:8080/api/seat/${this.flightId}`);
        this.seats = response.data;
        this.loading = false;
      } catch (error) {
        console.error("Error fetching seats:", error);
      }
    },
    getSeatClass(seat) {
      if (!seat.isAvailable) return "unavailable";

      switch (seat.classType) {
        case "First class":
          return "first-class";
        case "Business class":
          return "business-class";
        default:
          return "economy-class";
      }
    },
    showTooltip(seat, event) {
      if (!seat.isAvailable) return;

      this.hoveredSeat = seat;
      this.tooltipX = event.target.getBoundingClientRect().left + window.scrollX ;
      this.tooltipY = event.target.getBoundingClientRect().top + window.scrollY - 115;
    },
    hideTooltip() {
      this.hoveredSeat = null;
    },
    isRecommendedSeat(seat) {
      return this.recommendedSeats.some(
          (recommended) => recommended.seatNumber === seat.seatNumber
      );
    },
    async fetchRecommendedSeats() {
      const seatsData = this.$route.query.seats;

      if (seatsData) {
        this.recommendedSeats = [].concat(...JSON.parse(seatsData));
      }
    },
  },
  mounted() {
    this.fetchSeats();
    this.fetchRecommendedSeats();
  },
  created() {
    const { seats, flightId, passengers } = this.$route.query;
    this.flightId = flightId;

    if (seats) {
      this.recommendedSeats = [].concat(...JSON.parse(seats));
    }

    if (passengers) {
      this.passengers = JSON.parse(passengers);
      console.log("Passengers:", this.passengers);
    }

    console.log("Recommended Seats:", this.recommendedSeats);
    console.log("Flight ID:", this.flightId);
  }
};
</script>

<style scoped>
.seat-map-container {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}
.recommended-seats {
  width: 40%;
}
.seat-tooltip p {
  margin: 2px 0;
  padding: 0;
}
.seat-tooltip {
  position: absolute;
  width: 125px;
  height: 80px;
  background-color: white;
  border: 1px solid black;
  padding: 5px;
  border-radius: 5px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
  white-space: normal;
  line-height: 1.1;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  text-align: center;
  font-size: min(12px, 2vw);
  overflow: hidden;
}
.seat-map {
  width: 60%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.plane-body {
  background-color: #e0e0e0;
  padding: 30px;
  border-radius: 60px / 30px;
  border: 4px solid #333;
  display: flex;
  flex-direction: column;
  align-items: center;
}

h2 {
  .seat-map-container {
    position: relative;
  }
  .tooltip {
    position: absolute;
    background: rgba(0, 0, 0, 0.8);
    color: white;
    padding: 8px;
    border-radius: 4px;
    font-size: 14px;
    white-space: nowrap;
    pointer-events: none;
  }
  margin-bottom: 10px;
  font-size: 22px;
  font-weight: bold;
}

.legend {
  display: flex;
  gap: 15px;
  margin-bottom: 15px;
}

.legend-item {
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: bold;
}

.legend-item .seat {
  width: 20px;
  height: 20px;
  margin-right: 5px;
}

.seat-row {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 6px;
  padding: 5px 10px;
}

.seats {
  display: flex;
  justify-content: center;
}

.left {
  justify-content: flex-start;
}

.right {
  justify-content: flex-end;
}

.seat {
  width: 30px;
  height: 30px;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: bold;
  color: #000;
  margin: 1px;
  border: 1px solid black;
}

.first-class {
  background-color: #ffd700;
  border-color: #b8860b;
}

.business-class {
  background-color: #1e90ff;
  border-color: #104e8b;
}

.economy-class {
  background-color: #e4dbdb;
  border-color: #505050;
}

.unavailable {
  background-color: #595656;
  border-color: #000;
}
.seat-info, .passenger-info {
  width: 48%;
}
.passenger-info {
  text-align: right;
}
.seat-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.recommended {
  border: 2px solid #ff6347;
  box-shadow: 0 0 10px rgba(255, 99, 71, 0.7);
}
h2 {
  margin-bottom: 10px;
  font-size: 22px;
  font-weight: bold;
}
.aisle {
  width: 20px;
}
</style>