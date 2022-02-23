package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Expert;
import com.mapper.ExpertMapper;
import com.service.IExpertService;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceImpl extends ServiceImpl<ExpertMapper, Expert> implements IExpertService {
}


