import Vue from 'vue'
import Router from 'vue-router'
import Button from '@/components/element/Button'
import Login from '@/components/Login'
import Home from '@/components/Home'
import SysDept from '@/components/sys/SysDept'
import SysUser from '@/components/sys/SysUser'
import SysDeptTree from '@/components/sys/SysDeptTree'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/',
      name: 'Home',
      component: Home,
      meta:{
        title:"系统管理"
      },
      children:[
        {
          path: '/button',
          name: 'Button',
          component: Button,
          meta:{
            title:"按钮管理"
          }
        },
        {
          path: '/sys/dept',
          name: 'SysDept',
          component: SysDept,
          meta:{
            title:"部门管理"
          },
          children:[
            {
              path: '/tree',
              name: 'SysDeptTree',
              component: SysDeptTree,
              meta:{
                title:"部门树"
              }
            },
          ]
        },
        {
          path: '/sys/user',
          name: 'SysUser',
          component: SysUser,
          meta:{
            title:"用户管理"
          }
        },
        // {
        //   path: '/jquery/table',
        //   name: 'JqueryTable',
        //   component: JqueryTable,
        //   meta:{
        //     title:"JQuery 表格"
        //   }
        // },
        // {
        //   path: '/tree',
        //   name: 'SysDeptTree',
        //   component: SysDeptTree,
        //   meta:{
        //     title:"部门树"
        //   }
        // },
      ]
    }
  ]
})
