import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/Home.vue';
import AirplaneSeatMap from "@/views/AirplaneSeatMap.vue";
import FlightBooking from "@/views/FlightBooking.vue";

const routes = [
    {
        path: '/',
        name: 'HomeView',
        component: HomeView
    },
    {
        path: '/plane',
        name: 'AriplaneSeatMap',
        component: AirplaneSeatMap
    },
    {
        path: '/booking',
        name: 'FlightBooking',
        component: FlightBooking
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
