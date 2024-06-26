import VueRouter from "vue-router";

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../components/LoginPage')
    }, {
        path: '/Index',
        name: 'IndexPage',
        component: () => import('../components/IndexPage'),
        children: [
            {
                path: '/Home',
                name: 'HomePage',
                meta: {title: '首页'},
                component: () => import('../components/HomePage')
            },
            {
                path: '/Info',
                name: 'info',
                meta: {title: '通知'},
                component: () => import('../components/info/InfoManage')
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export function resetRouter() {
    router.matcher = new VueRouter({
        mode: 'history',
        routes: []
    }).matcher
}

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

export default router;