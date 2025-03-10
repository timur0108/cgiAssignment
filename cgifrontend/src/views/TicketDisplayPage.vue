<template>
  <div class="ticket-display">
    <h1>Your Tickets</h1>
    <div v-for="ticket in tickets" :key="ticket.id" class="ticket">
      <div class="ticket-header">
        <h2>Flight: {{ ticket.flightNumber }}</h2>
        <p>{{ ticket.departureCity }} → {{ ticket.arrivalCity }}</p>
      </div>
      <div class="ticket-body">
        <p><strong>Passenger:</strong> {{ ticket.passengerName }} {{ ticket.passengerSurname }}</p>
        <p><strong>Email:</strong> {{ ticket.passengerEmail }}</p>
        <p><strong>Phone:</strong> {{ ticket.passengerPhone }}</p>
        <p><strong>Seat Number:</strong> {{ ticket.seatNumber }}</p>
        <p><strong>Class Type:</strong> {{ ticket.classType }}</p>
        <p><strong>Price:</strong> ${{ ticket.price }}</p>
      </div>
      <div class="ticket-footer">
        <p><strong>Departure:</strong> {{ ticket.departureDate }} {{ ticket.departureTime }}</p>
        <p><strong>Arrival:</strong> {{ ticket.arrivalDate }} {{ ticket.arrivalTime }}</p>
        <p><strong>Flight Duration:</strong> {{ formatDuration(ticket.flightDuration) }} minutes</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tickets: [],
    };
  },
  methods: {
    formatDuration(durationInSeconds) {
      const hours = Math.floor(durationInSeconds / 3600);
      const minutes = Math.floor((durationInSeconds % 3600) / 60);
      return `${hours}h ${minutes}m`;
    }
  },
  created() {
    const ticketsQuery = this.$route.query.tickets;

    if (ticketsQuery) {
      try {
        this.tickets = JSON.parse(ticketsQuery);
      } catch (error) {
        console.error("Error parsing tickets:", error);
      }
    } else {
      console.warn("No tickets found in query parameters.");
    }
  }
};
</script>

<style scoped>
.ticket-display {
  font-family: Arial, sans-serif;
  padding: 20px;
}

.ticket {
  border: 1px solid #ddd;
  padding: 20px;
  margin: 20px 0;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.ticket-header {
  border-bottom: 1px solid #ddd;
  margin-bottom: 15px;
}

.ticket-header h2 {
  margin: 0;
  font-size: 20px;
}

.ticket-body,
.ticket-footer {
  margin-top: 10px;
}

.ticket-body p,
.ticket-footer p {
  margin: 5px 0;
}
</style>
