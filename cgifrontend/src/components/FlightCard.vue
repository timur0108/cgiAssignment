<script>
export default {
  props: {
    flight: Object
  }
};
</script>

<template>
  <div class="card">
    <h3 class="card-title">
      ✈️ {{ flight.departureCity }} → {{ flight.arrivalCity }}
    </h3>

    <div class="card-details">
      <p>🔢 <strong>Flight number:</strong> {{ flight.flightNumber }}</p>
      <p>📅 <strong>Departure:</strong> {{ flight.departureDate }} at {{ flight.departureTime }}</p>
      <p>📍 <strong>Arrival:</strong> {{ flight.arrivalDate }} at {{ flight.arrivalTime }}</p>
      <p class="price">💰 <strong>Price:</strong> ${{ flight.price }}</p>
      <p class="seats">🪑 <strong>Seats Available:</strong> {{ flight.availableSeatsNumber }}</p>
    </div>

    <button class="book-btn">Book Now</button>
  </div>
</template>

<style scoped>
.card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border: 1px solid #ddd;
  transition: box-shadow 0.3s ease-in-out;
  max-width: 350px;
  min-height: 280px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.card-title {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.card-details p {
  font-size: 14px;
  color: #555;
  margin: 5px 0;
}

.price {
  font-size: 16px;
  font-weight: bold;
  color: #27ae60;
  margin-top: 10px;
}

.seats {
  font-size: 15px;
  font-weight: bold;
  color: #007bff;
}

.book-btn {
  width: 100%;
  background: #007bff;
  color: white;
  border: none;
  padding: 10px;
  font-size: 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
  margin-top: 15px;
}

.book-btn:hover {
  background: #0056b3;
}

</style>

