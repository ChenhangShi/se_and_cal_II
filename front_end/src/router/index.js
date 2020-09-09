import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login.vue'

Vue.use(VueRouter)
const routes = [
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/',
        redirect: '/NJUSE'
    },
    {
        path: '/NJUSE',
        name: 'layout',
        redirect: '/hotel/hotelList',
        component: () => import('@/views/layout'),
        children: [
            {
                path: '/hotel/hotelList',
                name: 'hotelList',
                component: () => import('@/views/hotel/hotelList')
            },
            {
                path: '/hotel/hotelDetail/:hotelId',
                name: 'hotelDetail',
                component: () => import('@/views/hotel/hotelDetail')
            },
            {
                path: '/user/info/:userId',
                name: 'userInfo',
                component: () => import('@/views/user/info')
            },
            {
                //迭代3.3 在hotelManager的url上增加userId以区分
                path: '/hotelManager/manageHotel/:userId',
                name: 'manageHotel',
                component: () => import('@/views/hotelManager/manageHotel')
            },
            {
                path: '/admin/manageUser',
                name: 'manageUser',
                component: () => import('@/views/admin/manageUser')
            },
            {
                //迭代3.3新增，salesman的url
                path: '/salesman/salesStrategy',
                name: 'salesStrategy',
                component: () => import('@/views/salesman/salesStrategy')
            },
        ]
    },
]
const createRouter = () => new VueRouter({
    // mode: 'history', // require service support
    scrollBehavior: () => ({y: 0}),
    routes
})
const router = createRouter()

export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router
