package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.UserSubscribe;
import com.mapper.UserSubscribeMapper;
import com.service.IUserSubscribeService;
import org.springframework.stereotype.Service;

@Service
public class UserSubscribeServiceImpl extends ServiceImpl<UserSubscribeMapper, UserSubscribe> implements IUserSubscribeService {
}

