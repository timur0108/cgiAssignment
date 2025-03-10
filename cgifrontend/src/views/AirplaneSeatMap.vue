<template>
  <div class="seat-map">
    <h2>Boeing 737-800 Seat Map</h2>

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
                class="seat"
                :class="getSeatClass(seat)"
            >
              <span v-if="seat.isAvailable">{{ seat.seatNumber }}</span>
            </div>
          </div>

          <div class="aisle"></div>

          <div class="seats right">
            <div
                v-for="seat in row.rightSeats"
                :key="seat.id"
                class="seat"
                :class="getSeatClass(seat)"
            >
              <span v-if="seat.isAvailable">{{ seat.seatNumber }}</span>
            </div>
          </div>
        </div>
      </div>
      <p v-else>Loading seats...</p>
    </div>
  </div>
</template>



<script>
import axios from "axios";

export default {
  name: "Boeing737SeatMap",
  data() {
    return {
      seats: [],
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
        const response = await axios.get("http://backend:8080/api/seat/1");
        this.seats = response.data;
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
    getRowStyle(row) {
      if (row <= 3) {
        return { marginBottom: "20px" };
      } else if ([6, 12, 18, 24].includes(row)) {
        return { marginBottom: "10px" };
      } else {
        return { marginBottom: "6px" };
      }
    },
  },
  mounted() {
    this.fetchSeats();
  },
};
</script>

<style scoped>
.seat-map {
  padding: 30px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  box-sizing: border-box;
  overflow: hidden;
  max-width: 100%;
}

.plane-body {
  background-color: #e0e0e0;
  padding: 30px;
  border-radius: 60px / 30px;
  border: 4px solid #333;
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 450px;
  position: relative;
  overflow: hidden;
}

h2 {
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
  color: #000000;
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
  border-color: #000000;
}

.aisle {
  width: 20px;
}
</style>


