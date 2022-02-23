package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Teacher;
import com.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @PostMapping(value = "/addTeacher")
    public String addType(@RequestBody Teacher teacher) {
        List<Teacher> teachers = teacherService.getBaseMapper().selectList(new QueryWrapper<Teacher>()
                .eq("t_phone", teacher.getT_phone())
        );

        if(teachers.size() > 0) {
            return "电话重复!";
        }
        int insert = teacherService.getBaseMapper().insert(teacher);
        return insert+"";
    }

    @RequestMapping(value = "/getAllTeacher" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllTeacher() {
        List<Teacher> types = teacherService.getBaseMapper().selectList(null);
//        System.out.println(types);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteTeacher" , method = RequestMethod.GET)
    public ResponseEntity<?> deleteTeacher(@RequestParam("id") String id) {
        System.out.println(id);
        int info = teacherService.getBaseMapper().deleteById(id);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updateTeacher" , method = RequestMethod.POST)
    public String updateTeacher(@RequestBody Teacher teacher) {
        int info = teacherService.getBaseMapper().updateById(teacher);
        return "1";
    }

    @GetMapping(value = "/searchTeacher")
    public String searchTeacher(@RequestParam("t_name") String t_name,
                                @RequestParam("t_academic") String t_academic) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if(!t_name.equals("")) {
            wrapper.like("t_name", t_name);
        }
        if(!t_academic.equals("")) {
            wrapper.eq("t_academic", t_academic);
        }

        List<Teacher> teachers = teacherService.getBaseMapper().selectList(wrapper);
        return JSON.toJSONString(teachers, SerializerFeature.WriteMapNullValue);
    }

    @PostMapping(value = "/queryTeacher")
    public String queryTeacher(@RequestBody Map map) {
        List list = teacherService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }

    @PostMapping(value = "/teacherLogin")
    public String userLogin(@RequestBody Map map) {
        System.out.println(map);
        List<Teacher> list = teacherService.getBaseMapper().selectByMap(map);
        if(list.size() == 0) {
            return "0";
        }
        else {
            return JSON.toJSONString(list.get(0), SerializerFeature.WriteMapNullValue);
        }
    }
}
