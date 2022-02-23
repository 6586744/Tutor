package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Education;
import com.service.IEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/education")
public class EducationController {

    @Autowired
    IEducationService educationService;

    @PostMapping(value = "/addEducation")
    public String addEducation(@RequestBody Education selection) {
        List<Education> comments = educationService.getBaseMapper().selectList(new QueryWrapper<Education>()
                .eq("e_teacher_id", selection.getE_teacher_id()));
        if(comments.size() > 3) {
            return "最多添加四条!";
        }

        int insert = educationService.getBaseMapper().insert(selection);
        return insert+"";
    }

    @RequestMapping(value = "/getAllEducation" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllEducation() {
        List<Education> types = educationService.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteEducation" , method = RequestMethod.GET)
    public ResponseEntity<?> deleteEducation(@RequestParam("id") String id) {
        int info = educationService.getBaseMapper().deleteById(id);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updateEducation" , method = RequestMethod.POST)
    public String updateEducation(@RequestBody Education teacher) {
        int info = educationService.getBaseMapper().updateById(teacher);
        return "1";
    }


    @PostMapping(value = "/queryEducation")
    public String queryEducation(@RequestBody Map map) {
        List list = educationService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }
}
