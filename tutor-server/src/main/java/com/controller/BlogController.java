package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Blog;
import com.entity.Type;
import com.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @PostMapping(value = "/addBlog")
    public String addBlog(@RequestBody Blog blog) {

        int insert = blogService.getBaseMapper().insert(blog);
        return insert + "";
    }

    @RequestMapping(value = "/getAllBlog" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllBlog() {
        List<Blog> types = blogService.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    /**
     * 传回来的数据，如果父id不为空，则说明为子级。直接删除
     * 如果父id为空。则需要删除对应的子级
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteBlog" , method = RequestMethod.GET)
    public String  deleteType(@RequestParam("id") String id) {
        int info = blogService.getBaseMapper().deleteById(id);
        return "1";
    }

    @RequestMapping(value = "/updateBlog" , method = RequestMethod.POST)
    public String updateBlog(@RequestBody Blog teacher) {
        int info = blogService.getBaseMapper().updateById(teacher);
        return "1";
    }


    @PostMapping(value = "/queryBlog")
    public String queryBlog(@RequestBody Map map) {
        List list = blogService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }
}
