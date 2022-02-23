package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.*;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ITeacherSubscribeService teacherSubscribeService;

    @Autowired
    private IUserSubscribeService userSubscribeService;

    @Autowired
    private IExpertService expertService;

    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody User teacher) {
        List<User> teachers = userService.getBaseMapper().selectList(new QueryWrapper<User>()
                .eq("u_phone", teacher.getU_phone())
        );

        if(teachers.size() > 0) {
            return "电话重复!";
        }
        int insert = userService.getBaseMapper().insert(teacher);
        return insert+"";
    }

    @RequestMapping(value = "/getAllUser" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllUser() {
        List<User> types = userService.getBaseMapper().selectList(null);
//        System.out.println(types);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser" , method = RequestMethod.GET)
    public ResponseEntity<?> deleteUser(@RequestParam("id") String id) {
        System.out.println(id);
        int info = userService.getBaseMapper().deleteById(id);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updateUser" , method = RequestMethod.POST)
    public String updateUser(@RequestBody User teacher) {
        int info = userService.getBaseMapper().updateById(teacher);
        return "1";
    }

    @GetMapping(value = "/getUserById")
    public String getUserById(@RequestParam("userId") String userId) {
        User user = userService.getBaseMapper().selectById(userId);
        return JSON.toJSONString(user);
    }

    @GetMapping(value = "/searchUser")
    public String searchUser(@RequestParam("t_name") String t_name,
                                @RequestParam("t_academic") String t_academic) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!t_name.equals("")) {
            wrapper.like("t_name", t_name);
        }
        if(!t_academic.equals("")) {
            wrapper.eq("t_academic", t_academic);
        }

        List<User> teachers = userService.getBaseMapper().selectList(wrapper);
        return JSON.toJSONString(teachers, SerializerFeature.WriteMapNullValue);
    }

    @PostMapping(value = "/queryUser")
    public String queryUser(@RequestBody Map map) {
        List list = userService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }

    @PostMapping(value = "/userLogin")
    public String userLogin(@RequestBody Map map) {
        System.out.println(map);
        List<User> list = userService.getBaseMapper().selectByMap(map);
        if(list.size() == 0) {
            return "0";
        }
        else {
            return JSON.toJSONString(list.get(0), SerializerFeature.WriteMapNullValue);
        }
    }

    /**
     * 通过用户的地址去推荐
     * @param address
     * @return
     */
    @GetMapping(value = "/findTeacherByAddress")
    public String findTeacherByAddress(@RequestParam("address") String address) {
        List<User> userList = userService.getBaseMapper().selectList(new QueryWrapper<User>()
                .eq("u_address_name", address)
                .eq("u_teachering", "0")
                .eq("u_authentication", "1"));//需要认证通过且未在教学中
        return JSON.toJSONString(userList);
    }

    @GetMapping(value = "/expertTeacherForOrder")
    public String expertTeacherForOrder(@RequestParam("userId") String userId) {
        List<Order> orderList = orderService.getBaseMapper().selectList(new QueryWrapper<Order>()
                .eq("o_status", "0")
                .eq("o_student_id", userId));
        ArrayList<User> userArrayList = new ArrayList<>();
        for (Order order : orderList) {
            List<Expert> expertList = expertService.getBaseMapper().selectList(new QueryWrapper<Expert>()
                    .eq("e_big_type_name", order.getO_type()));
            for (Expert expert : expertList) {
                List<User> users = userService.getBaseMapper().selectList(new QueryWrapper<User>()
                        .eq("u_id", expert.getE_teacher_id())
                        .eq("u_authentication", "1")
                        .eq("u_teachering", 0));
                if(users.size() > 0) {
                    userArrayList.add(users.get(0));
                }
            }
        }
        ArrayList<User> returnUserList = new ArrayList<>();
        System.out.println(userArrayList.size() + "    ===userArrayList");
        int index = 0;
        System.out.println(userArrayList);
        for (User user : userArrayList) {
            System.out.println(user);
            index = 0;
            for (User user1 : returnUserList) {
                System.out.println("?????===="  + user1);
                if(user.getU_id().equals(user1.getU_id())) {
                    index = 1;
                    break;
                }
            }
            if(index == 0
                    && "1".equals(user.getU_authentication())
                    && "0".equals(user.getU_teachering())) {
                returnUserList.add(user);
            }
        }
        System.out.println(returnUserList.size() + "    ===returnUserList");
        return JSON.toJSONString(returnUserList);
    }

    /**
     * 管理员分配老师接口,
     * 1.首先需要获取订单信息，然后获取相互预约的老师，进行匹配。优先级高的放在前面。
     * 2. 如果没有匹配信息。则学生预约的老师放在前面。老师预约它的放在后面。
     * 3. 如果都没有。则直接返回一个科目匹配的老师。
     * @param orderId
     * @return
     */
    @GetMapping(value = "/selectTeacherForOrder")
    public String selectTeacherForOrder(@RequestParam("orderId") String orderId) {
        Order order = orderService.getBaseMapper().selectById(orderId);
        List<TeacherSubscribe> teacherSubscribeList = teacherSubscribeService.getBaseMapper().selectList(new QueryWrapper<TeacherSubscribe>()
                .eq("s_order_id", orderId));
        List<UserSubscribe> userSubscribeList = userSubscribeService.getBaseMapper().selectList(new QueryWrapper<UserSubscribe>()
                .eq("s_order_id", orderId));
//        String str; str.equals("fd")
        ArrayList<User> userArrayList = new ArrayList<>(); //需要返回的教师id
        //需要注意，查询到的教师应该已经通过认证且未在教学中。
        ArrayList<String> userIdList = new ArrayList<>();//记录匹配到的教师id
        for (TeacherSubscribe teacherSubscribe : teacherSubscribeList) {
            for (UserSubscribe userSubscribe : userSubscribeList) {
                if(teacherSubscribe.getS_user_id().equals(userSubscribe.getS_user_id())) {
                    userIdList.add(teacherSubscribe.getS_user_id());
                    break;
                }
            }
        }
        for (String s : userIdList) { //进行匹配
            User user = userService.getBaseMapper().selectById(s);
            if("1".equals(user.getU_authentication()) && "0".equals(user.getU_teachering())) {
                userArrayList.add(user);
            }
        }
        for (UserSubscribe userSubscribe : userSubscribeList) {
            userArrayList.add(userService.getBaseMapper().selectById(userSubscribe.getS_user_id()));
        }
        for (TeacherSubscribe teacherSubscribe : teacherSubscribeList) {
            userArrayList.add(userService.getBaseMapper().selectById(teacherSubscribe.getS_user_id()));
        }

        //按擅长进行匹配。
        List<Expert> expertList = expertService.getBaseMapper().selectList(new QueryWrapper<Expert>()
                .eq("e_big_type_name", order.getO_type()));
        for (Expert expert : expertList) {
            User user = userService.getBaseMapper().selectById(expert.getE_teacher_id());
            userArrayList.add(user);
        }
        //这里最后检查一下是否有匹配的教师
        if(userArrayList.size() == 0) {
            return JSON.toJSONString(userService.getBaseMapper().selectList(null));
        }
        //去掉重复的教师
        ArrayList<User> returnUserList = new ArrayList<>();
        System.out.println(userArrayList.size() + "    ===userArrayList");
        int index = 0;
        for (User user : userArrayList) {
            System.out.println(user);
            index = 0;
            for (User user1 : returnUserList) {
                System.out.println("已存在教师====" + user1);
                if(user1.getU_id().equals(user.getU_id())) {
                    index = 1;
                    break;
                }
            }

            if(index == 0
                    && "1".equals(user.getU_authentication())
                    && "0".equals(user.getU_teachering())) {
                returnUserList.add(user);
            }
            System.out.println("~~~~~" + returnUserList);
        }
        System.out.println(returnUserList.size() + "    ===returnUserList");
        return JSON.toJSONString(returnUserList);
    }
}
