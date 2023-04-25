import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/Login";
import Index from "../components/Index";
import HPage from "../views/HPage";
import Test1 from "../components/test/Test1";
import Test2 from "../components/test/Test2";
import Test3 from "../components/test/Test3";
import VueRouter from "vue-router";
import AdminManage from "../components/admin/AdminManage";
import UserManage from "../components/user/UserManage"
Vue.use(Router)
export default new Router({
    routes: [{
        path: '/',
        name: 'Login',
        component: Login
    },
        {
            path: '/test1',
            name: 'Test1',
            component: Test1
        },
        {
            path: '/test2',
            name: 'Test2',
            component: Test3
        },
        {
            path: '/test3',
            name: 'Test3',
            component: Test3
        },



        {
            path: '/index',
            name: 'Index',
            component: Index,
            children:[
                {
                    path: '/hPage',
                    name: 'HPage',
                    meta:{
                        title:'首页'

                    },
                    component: HPage
                },
                // {
                //     path: '/admin',
                //     name: 'Admin',
                //     meta: {
                //         title: "管理员管理"
                //     },
                //     component: AdminManage
                //
                // },
                // {
                //     path: '/user',
                //     name: 'User',
                //     meta: {
                //         title: "用户管理"
                //     },
                //     component: UserManage
                //
                // }


            ]
        }
        ]
})
// 去掉重复路由
export function resetRouter(){
    router.matcher=new VueRouter({
        mode:'history',
        routes:[]
    }).matcher
}
// 重写push方法，解决相同路径跳转报错
 const VueRouterPush = VueRouter.prototype.push
 VueRouter.prototype.push= function push (to){
    return VueRouterPush.call(this,to).catch(err=>err)
 }




