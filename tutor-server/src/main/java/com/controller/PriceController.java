package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.entity.Address;
import com.entity.Price;
import com.service.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/price")
public class PriceController {

    @Autowired
    private IPriceService priceService;

    @PostMapping(value = "/addPrice")
    public String addPrice(@RequestBody Price type) {
        int insert = priceService.getBaseMapper().insert(type);
        return insert+"";
    }

    @RequestMapping(value = "/getAllPrice" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllPrice() {
        List<Price> types = priceService.getBaseMapper().selectList(null);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deletePrice" , method = RequestMethod.GET)
    public ResponseEntity<?> deletePrice(@RequestParam("id") String id) {
        int info = priceService.getBaseMapper().deleteById(id);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updatePrice" , method = RequestMethod.POST)
    public ResponseEntity<?> updatePrice(@RequestBody Price type) {
        int info = priceService.getBaseMapper().updateById(type);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }
}
