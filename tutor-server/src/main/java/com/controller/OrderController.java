package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.*;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;
    @Autowired
    private ITeacherSubscribeService teacherSubscribeService;
    @Autowired
    private IUserSubscribeService userSubscribeService;

    @Autowired
    private IExpertService  expertService;


    @PostMapping(value = "/addOrder")
    public String addOrder(@RequestBody Order order) {
        int insert = orderService.getBaseMapper().insert(order);
        return insert + "";
//        return "";
    }

    @RequestMapping(value = "/getAllOrder" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrder() {
        List<Order> types = orderService.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @GetMapping(value = "/getOrderById")
    public String getOrderById(@RequestParam("id") String id) {
        Order order = orderService.getBaseMapper().selectById(id);
        return JSON.toJSONString(order);
    }
    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteOrder" , method = RequestMethod.GET)
    public String  deleteOrder(@RequestParam("id") String id) {
        int info = orderService.getBaseMapper().deleteById(id);
        List<TeacherSubscribe> teacherSubscribeList = teacherSubscribeService.getBaseMapper().selectList(new QueryWrapper<TeacherSubscribe>()
                .eq("s_order_id", id));
        //删除订单，应该删除对应的预约
        for (TeacherSubscribe teacherSubscribe : teacherSubscribeList) {
            teacherSubscribeService.getBaseMapper().deleteById(teacherSubscribe.getS_id());
        }
        List<UserSubscribe> userSubscribes = userSubscribeService.getBaseMapper().selectList(new QueryWrapper<UserSubscribe>()
                .eq("s_order_id", id));
        for (UserSubscribe userSubscribe : userSubscribes) {
            userSubscribeService.getBaseMapper().deleteById(userSubscribe.getS_id());
        }
        return "1";
    }

    @RequestMapping(value = "/updateOrder" , method = RequestMethod.POST)
    public String updateOrder(@RequestBody Order teacher) {
        int info = orderService.getBaseMapper().updateById(teacher);
        return "1";
    }

    @PostMapping(value = "/addOrderTeacher")
    public String addOrderTeacher(@RequestBody Order order) {
        User user = new User();
        System.out.println(order.getO_teacher_id() +" 教师id");
        user = userService.getBaseMapper().selectById(order.getO_teacher_id());
        if(order.getO_teacher_name().equals("")) { //取消订单
            user.setU_teachering("0");
            order.setO_teacher_id("");
        }
        else {
            user.setU_teachering("1");
            order.setO_teacher_phone(user.getU_phone());//设置电话号码
        }
        int i = userService.getBaseMapper().updateById(user);
        int info = orderService.getBaseMapper().updateById(order);
        return i + "";
    }

    @PostMapping(value = "/queryOrder")
    public String queryOrder(@RequestBody Map map) {
        List list = orderService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }

    @PostMapping(value = "/deposit")
    public String deposit(@RequestBody Order order) {
        int info = orderService.getBaseMapper().updateById(order);
        Order orderInfo = orderService.getBaseMapper().selectById(order.getO_id());
        //设置为教学中，且删除预约表中的数据
        if("1".equals(orderInfo.getO_user_status()) && "1".equals(orderInfo.getO_teacher_status())) {
            orderInfo.setO_status("2"); //设置为教学中
            List<TeacherSubscribe> teacherSubscribeList = teacherSubscribeService.getBaseMapper().selectList(new QueryWrapper<TeacherSubscribe>()
                    .eq("s_order_id", order.getO_id()));
            for (TeacherSubscribe teacherSubscribe : teacherSubscribeList) {
                teacherSubscribeService.getBaseMapper().deleteById(teacherSubscribe.getS_id());
            }
            List<UserSubscribe> userSubscribes = userSubscribeService.getBaseMapper().selectList(new QueryWrapper<UserSubscribe>()
                    .eq("s_order_id", order.getO_id()));
            for (UserSubscribe userSubscribe : userSubscribes) {
                userSubscribeService.getBaseMapper().deleteById(userSubscribe.getS_id());
            }
        }
        int i = orderService.getBaseMapper().updateById(orderInfo);
        return i + "";
    }

    @PostMapping(value = "/updateOrderStatus3")
    public String updateOrderStatus3(@RequestBody Order order) {
        orderService.getBaseMapper().updateById(order);
        User user = userService.getBaseMapper().selectById(order.getO_teacher_id());
        user.setU_teachering("0");
        userService.getBaseMapper().updateById(user);
        return "1";
    }

    /**
     * 最新订单
     * @return
     */
   @GetMapping(value = "/newOrder")
   public String newOrder() {
       List<Order> orderList = orderService.getBaseMapper().selectList(new QueryWrapper<Order>()
               .eq("o_status", "0")
               .orderByDesc("time"));
       return JSON.toJSONString(orderList);
   }

    /**
     * 订单获取同一区域
     * @param address
     * @return
     */
   @GetMapping(value = "/getAddressOrder")
   public String getAddressOrder(@RequestParam("address") String address) {
       List<Order> orderList = orderService.getBaseMapper().selectList(new QueryWrapper<Order>()
               .eq("o_address", address)
               .eq("o_status", "0"));
       return JSON.toJSONString(orderList);
   }

    /**
     * 获取订单的擅长推荐 应该从老师的角度来获取
     * @param userId
     * @return
     */
   @GetMapping(value = "/expertOrder")
    public String expertOrder(@RequestParam("userId") String userId) {
       List<Expert> expertList = expertService.getBaseMapper().selectList(new QueryWrapper<Expert>().eq("e_teacher_id", userId));
       if(expertList.size() == 0) {
           return JSON.toJSONString(orderService.getBaseMapper().selectList(new QueryWrapper<Order>()
                   .eq("o_status", "0")));
       }
       ArrayList<Order> returnList = new ArrayList<>();
       for (Expert expert : expertList) {
           List<Order> orderList = orderService.getBaseMapper().selectList(new QueryWrapper<Order>()
                   .eq("o_type", expert.getE_big_type_name())
                   .eq("o_status", "0"));
           for (Order order : orderList) {
               returnList.add(order);
           }
       }
       if(returnList.size() == 0) {
           return JSON.toJSONString(orderService.getBaseMapper().selectList(new QueryWrapper<Order>()
                   .eq("o_status", "0")));
       }
       return JSON.toJSONString(returnList);
   }

   @PostMapping(value = "/findOrder")
   public String findOrder(@RequestBody Map map) {
       QueryWrapper<Order> wrapper = new QueryWrapper<>();
       if(!StringUtils.isEmpty(map.get("o_id"))) {
           wrapper.eq("o_id", map.get("o_id"));
       }
       if(!StringUtils.isEmpty(map.get("o_student_name"))) {
           wrapper.like("o_student_name", map.get("o_student_name"));
       }
       if(!StringUtils.isEmpty(map.get("o_type"))) {
           wrapper.eq("o_type", map.get("o_type"));
       }
       if(!StringUtils.isEmpty(map.get("o_status"))) {
           wrapper.eq("o_status", map.get("o_status"));
       }
       List<Order> orderList = orderService.getBaseMapper().selectList(wrapper);
       return JSON.toJSONString(orderList);
   }
}
