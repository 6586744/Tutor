import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/login/Register'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },


  {
    path: '/index',
    name: 'index',
    component: Layout,
    redirect: '/index/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'home' }
    }]
  },

  {
    path: '/users',
    component: Layout,
    redirect: '/users/user',
    name: 'Users',
    meta: { title: '用户管理', icon: 'peoples' },
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/user/users'),
        meta: {requireAuth: true,title: '家长管理', icon: 'user' }
      },
      {
        path: '/previewUser/:userId',
        component: () => import('@/views/user/previewUser'),
        name: 'previewUser',
        hidden:true, //不显示在菜单当中。
      },
      {
        path: 'teacherManager',
        name: 'teacherManager',
        component: () => import('@/views/user/teacherManager'),
        meta: {requireAuth: true,title: '教师管理', icon: '老师' }
      },
    ]
  },

  {
    path: '/type',
    component: Layout,
    redirect: '/type/AllType',
    name: 'type',
    meta: { title: '类型管理', icon: 'table' },
    children: [
      {
        path: 'AllType',
        name: 'AllType',
        component: () => import('@/views/type/address'),
        meta: {requireAuth: true,title: '地址管理', icon: 'table' }
      },
      {
        path: 'academicManager',
        name: 'academicManager',
        component: () => import('@/views/type/type'),
        meta: {requireAuth: true,title: '科目管理', icon: '学院' }
      },
      // {
      //   path: 'price',
      //   name: 'price',
      //   component: () => import('@/views/price/price'),
      //   meta: {requireAuth: true,title: '价格管理', icon: '学院' }
      // },
    ]
  },


  {
    path: '/ac',
    component: Layout,
    redirect: '/ac/AllAnnouncement',
    name: 'bl',
    meta: { title: '公告管理', icon: 'table' },
    children: [
      {
        path: 'AllAnnoncement',
        name: 'AllAnnoncement',
        component: () => import('@/views/announcement/AllAnnouncement'),
        meta: {requireAuth: true,title: '公告列表', icon: 'table' }
      },
      {
        path: 'Md',
        name: 'Md',
        component: () => import('@/views/announcement/Md'),
        meta: {requireAuth: true,title: '写公告', icon: '学院' }
      },
      {
        path: 'MdEdit/:b_id',
        name: 'MdEdit',
        component: () => import('@/views/announcement/MdEdit'),
        hidden: false,
      },
      {
        path: 'PreviewAnnouncement/:b_id',
        name: 'PreviewAnnouncement',
        component: () => import('@/views/announcement/PreviewAnnouncement'),
        hidden: false,
      },
    ]
  },

  {
    path: '/warehouse',
    component: Layout,
    redirect: '/warehouse/curriculaManager',
    name: 'warehouse',
    meta: { title: '订单管理', icon: '课程' },
    children: [
      {
        path: 'curriculaManager',
        name: 'curriculaManager',
        component: () => import('@/views/order/order'),
        meta: {requireAuth: true,title: '订单管理', icon: '课程 (1)' }
      },
      {
        path: '/subscribeInfo/:orderId',
        name: 'subscribeInfo',
        component: () => import('@/views/order/subscribeInfo'),
        // meta: {requireAuth: true,title: '订单管理', icon: '课程 (1)' }
        hidden: true
      },
      {
        path: '/previewUser/:userId',
        component: () => import('@/views/user/previewUser'),
        name: 'previewUser',
        hidden:true, //不显示在菜单当中。
      },
    ]
  },


  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
