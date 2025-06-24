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
import ChatTest from "@/components/ChatTest.vue";
import AccountDetails from './components/AccountDetails.vue';
import { getLoggedUser } from './user.js';
import { refreshTokenIfNeeded } from './user.js';


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
        component: Projects
    },
    {
        path: '/projects/:projectId/edit',
        name: 'EditProject',
        component: EditProject,
        props: true
    },
    {
        path: '/projects/:projectId/details',
        name: 'ProjectDetails',
        component: ProjectDetails,
        props: true
    },
    {
        path: '/projects/add-project',
        name: 'AddProject',
        component: AddProject
    },
    {
        path: '/projects/:projectId/files',
        name: 'ProjectFiles',
        component: ProjectFiles,
        props: true
    },
    {
        path: '/projects/:projectId/chat',
        name: 'ProjectChat',
        component: ProjectChat,
        props: true
    },
    {
        path: '/user-search',
        name: 'UserSearch',
        component: UserSearch
    },
    {
        path: '/:userid/chat',
        name: 'UserChat',
        component: UserChat,
        props: true
    },
    {
        path: '/chat-test/:projectId',
        name: 'ChatTest',
        component: ChatTest,
        props: true
    },
    {
        path: '/edit-account',
        name: 'EditAccount',
        component: AccountDetails
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach(async (to, from, next) => {
   if(to.path === '/'){
       return next();
   }

   try {
    await refreshTokenIfNeeded();
    const loggedUser = getLoggedUser();

    if (!loggedUser) {
        return next('/');
    }
    next();
    } catch (error) {
       console.error("access error", this.loggedUser);
    next('/');
}

});

export default router;
