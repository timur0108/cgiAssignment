<template>
  <div class="seat-map">
    <h2>Boeing 737-800 Seat Map</h2>
    <div class="seat-row" v-for="row in rows" :key="row" :style="getRowStyle(row)">
      <div class="seats left">
        <div v-for="seat in leftSeats" :key="'left-' + row + '-' + seat" class="seat">
          {{ seat }}
        </div>
      </div>

      <div class="aisle"></div>

      <div class="seats right">
        <div v-for="seat in rightSeats" :key="'right-' + row + '-' + seat" class="seat">
          {{ seat }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Boeing737SeatMap",
  data() {
    return {
      rows: Array.from({ length: 30 }, (_, i) => i + 1),
      leftSeats: ['A', 'B', 'C'],
      rightSeats: ['D', 'E', 'F'],
    };
  },
  methods: {
    getRowStyle(row) {

      if (row <= 3) {
        return { marginBottom: '20px' };
      } else if (row === 6 || row === 12 || row === 18 || row === 24) {
        return { marginBottom: '10px' };
      } else {
        return { marginBottom: '6px' };
      }
    }
  },
};
</script>

<style scoped>

.seat-map {
  padding-top: 450px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
  box-sizing: border-box;
  overflow: hidden;
  max-width: 100%;
  margin-top: 10px;
}

h2 {
  margin-bottom: 10px;
  font-size: 22px;
  font-weight: bold;
}

.seat-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr) auto repeat(3, 1fr);
  gap: 5px;
  justify-items: center;
  align-items: center;
  margin-bottom: 6px;
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
  width: 20px;
  height: 20px;
  background-color: #2a75bb;
  border-radius: 3px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 8px;
  font-weight: bold;
  color: white;
  margin: 1px;
  border: 1px solid #1e5b87;
}

.aisle {
  align-self: center;
  font-size: 12px;
  font-weight: bold;
  color: #1e5b87;
  margin: 0 8px;
  line-height: 20px;
  display: flex;
  justify-content: center;
  height: 20px;
  width: 20px;
}
</style>

