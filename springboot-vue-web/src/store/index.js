import vue from 'vue'
import Vuex from  'vuex'
import router,{resetRouter} from "../router";
import createPersistedState from 'vuex-persistedstate'
vue.use(Vuex)

function addNewRouter(menuList) {
    console.log(menuList)
    let routes=router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if (routeItem.path === "/index"){
            menuList.forEach(menu=>{
                let childRoute={

                        path: '/'+menu.menuClick,
                        name: menu.menuName,
                        meta: {
                            title: menu.menuName
                        },
                        component: ()=>import('../components/'+menu.menuComponent)



                }
                routeItem.children.push(childRoute)

            })
        }
    })
    router.addRoutes(routes)
    
}

export  default new Vuex.Store({
    state:{
       menu:[]
    },
    mutations:{
        setMenu(state,menuList){
            state.menu=menuList
            addNewRouter(menuList)
        },
        setRouter(state,menuList){
            addNewRouter(menuList)
        }

    },
    getters:{
        getMenu(state){
            return state.menu
        }
    },
    // 保存数据到vuex
    plugins:[createPersistedState()]

})
