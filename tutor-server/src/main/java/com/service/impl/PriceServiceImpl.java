package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Price;
import com.mapper.PriceMapper;
import com.service.IPriceService;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl extends ServiceImpl<PriceMapper, Price> implements IPriceService {
}

