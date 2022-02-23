package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Comment;
import com.mapper.CommentMapper;
import com.service.ICommentService;
import org.springframework.stereotype.Service;

@Service
public class ICommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
}


