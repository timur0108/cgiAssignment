import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/Home.vue';
import AirplaneSeatMap from "@/views/AirplaneSeatMap.vue";
import FlightBooking from "@/views/FlightBooking.vue";
import RecommendedSeatsPage from "@/views/RecommendedSeatsPage.vue";

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
    },
    {
        path: '/recommended-seats',
        name: 'recommended-seats',
        component: RecommendedSeatsPage,
        props: route => ({ seats: JSON.parse(route.query.seats) })
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
