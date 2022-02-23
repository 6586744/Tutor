package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Order;
import com.entity.TeacherSubscribe;
import com.service.ITeacherSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/t/sub")
public class TeacherSubscribeController {

    @Autowired
    private ITeacherSubscribeService subscribeService;

    @PostMapping(value = "/addSubscribe")
    public String addOrder(@RequestBody TeacherSubscribe teacherSubscribe) {
        List<TeacherSubscribe> teacherSubscribes = subscribeService.getBaseMapper().selectList(new QueryWrapper<TeacherSubscribe>()
                .eq("s_user_id", teacherSubscribe.getS_user_id())
                .eq("s_order_id", teacherSubscribe.getS_order_id()));
        if(teacherSubscribes.size() > 0) {
            return "请勿重复添加！";
        }
        int insert = subscribeService.getBaseMapper().insert(teacherSubscribe);
        return insert + "";
//        return "";
    }

    @RequestMapping(value = "/getAllSubscribe" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrder() {
        List<TeacherSubscribe> types = subscribeService.getBaseMapper().selectList(null);
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
    public String updateOrder(@RequestBody TeacherSubscribe teacher) {
        int info = subscribeService.getBaseMapper().updateById(teacher);
        return "1";
    }

    @PostMapping(value = "/queryOrder")
    public String queryOrder(@RequestBody Map map) {
        List list = subscribeService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }

}
