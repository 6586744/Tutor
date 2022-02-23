package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Experience;
import com.mapper.ExperienceMapper;
import com.service.IExperienceService;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl extends ServiceImpl<ExperienceMapper, Experience> implements IExperienceService {
}


