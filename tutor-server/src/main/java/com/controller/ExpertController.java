package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Experience;
import com.entity.Expert;
import com.service.IExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/expert")
public class ExpertController {

    @Autowired
    private IExpertService expert_service;

    @PostMapping(value = "/addExpert")
    public String addExpert(@RequestBody Expert selection) {
        List<Expert> comments = expert_service.getBaseMapper().selectList(new QueryWrapper<Expert>()
                .eq("e_teacher_id", selection.getE_teacher_id()));
        if(comments.size() > 3) {
            return "最多添加四条!";
        }

        int insert = expert_service.getBaseMapper().insert(selection);
        return insert+"";
    }

    @RequestMapping(value = "/getAllExpert" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllExpert() {
        List<Expert> types = expert_service.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteExpert" , method = RequestMethod.GET)
    public ResponseEntity<?> deleteExpert(@RequestParam("id") String id) {
        int info = expert_service.getBaseMapper().deleteById(id);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updateExpert" , method = RequestMethod.POST)
    public String updateExpert(@RequestBody Expert teacher) {
        int info = expert_service.getBaseMapper().updateById(teacher);
        return "1";
    }


    @PostMapping(value = "/queryExpert")
    public String queryExpert(@RequestBody Map map) {
        List list = expert_service.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }



}
