package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Education;
import com.mapper.EducationMapper;
import com.service.IEducationService;
import org.springframework.stereotype.Service;

@Service
public class EducationServiceImpl extends ServiceImpl<EducationMapper, Education> implements IEducationService {
}

