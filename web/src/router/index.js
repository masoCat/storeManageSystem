import VueRouter from "vue-router";

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../components/LoginPage')
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router;