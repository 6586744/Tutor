package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Education;
import com.entity.Experience;
import com.service.IExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "experience")
public class ExperienceController {

    @Autowired
    private IExperienceService experienceService;

    @PostMapping(value = "/addExperience")
    public String addExperience(@RequestBody Experience selection) {
        List<Experience> comments = experienceService.getBaseMapper().selectList(new QueryWrapper<Experience>()
                .eq("e_teacher_id", selection.getE_teacher_id()));
        if(comments.size() > 9) {
            return "最多添加十条!";
        }

        int insert = experienceService.getBaseMapper().insert(selection);
        return insert+"";
    }

    @RequestMapping(value = "/getAllExperience" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllExperience() {
        List<Experience> types = experienceService.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteExperience" , method = RequestMethod.GET)
    public ResponseEntity<?> deleteExperience(@RequestParam("id") String id) {
        int info = experienceService.getBaseMapper().deleteById(id);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updateExperience" , method = RequestMethod.POST)
    public String updateExperience(@RequestBody Experience teacher) {
        int info = experienceService.getBaseMapper().updateById(teacher);
        return "1";
    }


    @PostMapping(value = "/queryExperience")
    public String queryExperience(@RequestBody Map map) {
        List list = experienceService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }

}
