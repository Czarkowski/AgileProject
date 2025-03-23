import { createRouter, createWebHistory } from 'vue-router';
import Login from './components/Login.vue';
import Test from './components/Test.vue';
import Register from "@/components/Register.vue";
import Projects from "@/components/Projects.vue";
import EditProject from "@/components/EditProject.vue";
import ProjectDetails from "@/components/ProjectDetails.vue";
import AddProject from "@/components/AddProject.vue";
import ProjectFiles from "@/components/ProjectFiles.vue";
import ProjectChat from "@/components/ProjectChat.vue";
import UserSearch from "@/components/UserSearch.vue";
import UserChat from "@/components/UserChat.vue";

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
    },

    {
        path: '/projects',
        name: 'Projects',
        component: Projects,
        children: [
            {
                path: ':projectId/edit',
                name: 'EditProject',
                component: EditProject,
                props: true,
            },
            {
                path: ':projectId/details',
                name: 'ProjectDetails',
                component: ProjectDetails,
                props: true,
            },
            {
                path: 'add-project',
                name: 'AddProject',
                component: AddProject,
                props: true,
            },
            {
                path: ':projectId/files',
                name: 'ProjectFiles',
                component: ProjectFiles,
                props: true,
            },
            {
                path: ':projectId/chat',
                name: 'ProjectChat',
                component: ProjectChat,
                props: true,
            },
        ]
    },

    {
        path: '/user-search',
        name: 'UserSearch',
        component: UserSearch,
    },

    {
        path: '/:userid/chat',
        name: 'UserChat',
        component: UserChat,
        props: true,
    },

];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
