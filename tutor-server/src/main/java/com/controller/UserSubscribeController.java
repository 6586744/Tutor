package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.TeacherSubscribe;
import com.entity.UserSubscribe;
import com.service.IUserSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/u/sub")
public class UserSubscribeController {

    @Autowired
    private IUserSubscribeService subscribeService;


    @PostMapping(value = "/addSubscribe")
    public String addOrder(@RequestBody UserSubscribe teacherSubscribe) {
        List<UserSubscribe> userSubscribes = subscribeService.getBaseMapper().selectList(new QueryWrapper<UserSubscribe>()
                .eq("s_user_id", teacherSubscribe.getS_user_id())
                .eq("s_order_id", teacherSubscribe.getS_order_id()));
        if ( userSubscribes.size() > 0) {
            return "请勿重复添加!";
        }
        int insert = subscribeService.getBaseMapper().insert(teacherSubscribe);
        return insert + "";
    }

    @RequestMapping(value = "/getAllSubscribe" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrder() {
        List<UserSubscribe> types = subscribeService.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

//    @GetMapping(value = "/getSubscribeById")
//    public String getOrderById(@RequestParam("id") String id) {
//        Order order = subscribeService.getBaseMapper().selectById(id);
//        return JSON.toJSONString(order);
//    }
    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteSubscribe" , method = RequestMethod.GET)
    public String  deleteOrder(@RequestParam("id") String id) {
        int info = subscribeService.getBaseMapper().deleteById(id);
        return "1";
    }

    @RequestMapping(value = "/updateSubscribe" , method = RequestMethod.POST)
    public String updateOrder(@RequestBody UserSubscribe teacher) {
        int info = subscribeService.getBaseMapper().updateById(teacher);
        return "1";
    }


    @PostMapping(value = "/queryOrder")
    public String queryOrder(@RequestBody Map map) {
        List list = subscribeService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }

}
