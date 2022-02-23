package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.Comment;
import com.entity.Type;
import com.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    ICommentService commentService;

    @PostMapping(value = "/addComment")
    public String addComment(@RequestBody Comment type) {

        List<Comment> types = commentService.getBaseMapper().selectList(new QueryWrapper<Comment>()
                .eq("c_order_id", type.getC_order_id()).or());
        if(types.size() > 1000) {
            return "最多添加1000条";
        }

        int insert = commentService.getBaseMapper().insert(type);
        return insert + "";

    }

    @RequestMapping(value = "/getAllComment" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllComment() {
        List<Comment> types = commentService.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    /**
     * 传回来的数据，如果父id不为空，则说明为子级。直接删除
     * 如果父id为空。则需要删除对应的子级
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteComment" , method = RequestMethod.GET)
    public String  deleteComment(@RequestParam("id") String id) {
        int info = commentService.getBaseMapper().deleteById(id);
        return "1";
    }

    @RequestMapping(value = "/updateComment" , method = RequestMethod.POST)
    public String updateComment(@RequestBody Comment teacher) {
        int info = commentService.getBaseMapper().updateById(teacher);
        return "1";
    }


    @PostMapping(value = "/queryComment")
    public String queryComment(@RequestBody Map map) {
        List list = commentService.getBaseMapper().selectByMap(map);
        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }
}
