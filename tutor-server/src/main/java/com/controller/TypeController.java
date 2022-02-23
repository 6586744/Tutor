package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Expert;
import com.entity.Order;
import com.entity.Type;
import com.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/type")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    /**
     * @param type
     * @return
     */
    @PostMapping(value = "/addType")
    public String addType(@RequestBody Type type) {

        List<Type> types = typeService.getBaseMapper().selectList(new QueryWrapper<Type>()
                .eq("t_name", type.getT_name()).or()
                .eq("t_father_name", type.getT_father_name()));
        if(types.size() > 0) {
            return "名称不能重复";
        }

        int insert = typeService.getBaseMapper().insert(type);
        return insert + "";
    }

    @RequestMapping(value = "/getAllType" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllType() {
        List<Type> types = typeService.getBaseMapper().selectList(new QueryWrapper<Type>()
                .orderByAsc("t_name"));
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    /**
     * 传回来的数据，如果父id不为空，则说明为子级。直接删除
     * 如果父id为空。则需要删除对应的子级
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteType" , method = RequestMethod.GET)
    public String  deleteType(@RequestParam("id") String id) {
        Type type = typeService.getBaseMapper().selectById(id);
//        if(!type.getT_father_id().equals("")) {
            int info = typeService.getBaseMapper().deleteById(id);
            return "1";
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("t_father_id", id);
//        int i = typeService.getBaseMapper().deleteByMap(map);
//        int i1 = typeService.getBaseMapper().deleteById(id);
//        return (i + 1) + "";
    }

    @RequestMapping(value = "/updateType" , method = RequestMethod.POST)
    public String updateType(@RequestBody Type teacher) {
        int info = typeService.getBaseMapper().updateById(teacher);
        return "1";
    }


    @PostMapping(value = "/queryType")
    public String queryType(@RequestBody Map map) {
        List list = typeService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }
}
