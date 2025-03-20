import { createRouter, createWebHistory } from 'vue-router';
import Login from './components/Login.vue';
import Test from './components/Test.vue';
import Register from "@/components/Register.vue";

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },

    {
        path: '/test',
        name: 'Test',
        component: Test
    },

    {
        path: '/register',
        name: 'Register',
        component: Register
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
