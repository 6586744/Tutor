import Vue from 'vue'
import VueRouter from "vue-router";
import Login from "../views/Login";
import Register from "../views/Register";
import NavMenu from "../components/user/NavMenu";
import Error from "../views/Error";
import PreviewBlog from "../components/common/PreviewBlog";
import BlogList from "../components/user/BlogList";
import UserInfo from "../components/user/UserInfo";
import myIndex from "../components/user/my/myIndex";
import editorPassword from "../components/user/my/editorPassword";
import education from "../components/user/my/education";
import experience from "../components/user/my/experience";
import expert from "../components/user/my/expert";
import previewUserInfo from "../components/user/my/previewUserInfo/previewUserInfo";
import userAddOrder from "../components/user/my/userAddOrder";
import newOrder from "../components/user/newOrder";
import findTeacher from "../components/user/findTeacher";
import teacherInfo from "../components/user/teacherInfo";
import userOrder from "../components/user/my/userOrder";
import teacherSubscribe from "../components/user/my/teacherSubscribe";
import teacherOrder from "../components/user/my/teacherOrder";
import indexOne from "../components/user/index";
import testBaiduMap from "../components/user/testBaiduMap";
import price from "../components/user/price";
import video from "../components/user/video";
import userCheckSubscribe from "../components/user/my/userCheckSubscribe";
import teacherCheckSubscribe from "../components/user/my/teacherCheckSubscribe";

Vue.use(VueRouter);

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new VueRouter({
  //mode : 'history',
  routes:[

    {
      path: "/userHome",
      redirect: '/Login'
    },
    {
      path:'/Login',
      name:'Login',
      component:Login
    },
    {
      path:'/Register',
      name: 'Register',
      component:Register
    },
    {
      path: '',
      redirect: '/index'
    },
    {
      path: '*',
      component: Error
    },

    {
      path: '/index',
      name: 'NavMenu',
      component: NavMenu,
      redirect:'/indexOne',
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/indexOne',
          props : true,
          name: 'indexOne',
          component: indexOne,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/testBaiduMap',
          props : true,
          name: 'testBaiduMap',
          component: testBaiduMap,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/price',
          props : true,
          name: 'price',
          component: price,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/video',
          props: true,
          name: 'video',
          component: video,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/BlogList',
          props : true,
          name: 'BlogList',
          component: BlogList,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/newOrder',
          props : true,
          name: 'newOrder',
          component: newOrder,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/findTeacher',
          props : true,
          name: 'findTeacher',
          component: findTeacher,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/teacherInfo/:userId',
          props : true,
          name: 'teacherInfo',
          component: teacherInfo,
          meta: {
            requireAuth: true
          },
        },
        {
          path:'/myBlog/:b_id',
          props: true,
          name:'myBlog',
          meta: {
            requireAuth: true
          },
          component:PreviewBlog
        },
        {
          path: '/myIndex',
          props : true,
          name: 'myIndex',
          component: myIndex,
          meta: {
            requireAuth: true
          },
          children:[
            {
              path: '/teacherCheckSubscribe',
              props : true,
              name: 'teacherCheckSubscribe',
              component: teacherCheckSubscribe,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/teacherInfo1/:userId',
              props : true,
              name: 'teacherInfo1',
              component: teacherInfo,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/userCheckSubscribe/:orderId',
              name: 'userCheckSubscribe',
              component: userCheckSubscribe,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/teacherSubscribe',
              name: 'teacherSubscribe',
              component: teacherSubscribe,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/teacherOrder',
              name: 'teacherOrder',
              component: teacherOrder,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/userOrder',
              name: 'userOrder',
              component: userOrder,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/userAddOrder',
              name: 'userAddOrder',
              component: userAddOrder,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/expert',
              name: 'expert',
              component: expert,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/previewUserInfo',
              name: 'previewUserInfo',
              component: previewUserInfo,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/education',
              name: 'education',
              component: education,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/experience',
              name: 'experience',
              component: experience,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/editorPassword',
              name: 'editorPassword',
              component: editorPassword,
              meta: {
                requireAuth: true
              },
            },
            {
              path: '/userInfo',
              name: 'userInfo',
              component: UserInfo,
              meta: {
                requireAuth: true
              },
            },

          ]
        },
      ]
    },

  ]
});
