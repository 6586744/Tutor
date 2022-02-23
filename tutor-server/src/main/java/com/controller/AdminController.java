package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping(value = "/adminLogin")
    public String adminLogin(@RequestBody Map admin) {

        List list = adminService.getBaseMapper().selectByMap(admin);

        if(list.size() == 0) {
            return "0";
        }
        return JSON.toJSONString(list.get(0), SerializerFeature.WriteMapNullValue);
    }
}
