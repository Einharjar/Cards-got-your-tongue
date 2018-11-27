import VueRouter from "vue-router";
import Home from '../components/home.vue'
import LoginPage from '../screens/login/LoginPage.vue'
import RegisterPage from '../screens/register/RegisterPage.vue'
import HomePage from '../screens/home/HomePage.vue'

const router = new VueRouter({
    mode:'history',
    routes: [
        {
            component: Home,
            name:'home',
            path: '/'
        },
        {
            component: LoginPage,
            name:'loginPage',
            path: '/loginPage'
        },
        {
            component: RegisterPage,
            name:'registerPage',
            path: '/registerPage'
        },
        {
            component: HomePage,
            name:'homePage',
            path: '/homePage'
        },
        // otherwise redirect to regular home
        {
            path: '*',
            redirect: '/'
        }

    ]
});
router.beforeEach((to, from, next) => {
    // redirect to login page if not logged in and trying to access a restricted page

    //TODO now every page is public, dont forget to change
    const publicPages = ['/loginPage', '/registerPage','/','/home', '/homePage'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    if (authRequired && !loggedIn) {
        return next('/loginPage');
    }

    next();
})

export default router



