import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/Home.vue';
import AirplaneSeatMap from "@/views/AirplaneSeatMap.vue";

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
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
