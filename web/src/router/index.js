import VueRouter from "vue-router";

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../components/LoginPage')
    }, {
        path: '/Index',
        name: 'HomePage',
        component: () => import('../components/HomePage')
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router;