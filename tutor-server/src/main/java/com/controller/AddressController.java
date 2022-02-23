package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.entity.Address;
import com.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @PostMapping(value = "/addAddress")
    public String addAddress(@RequestBody Address type) {
        int insert = addressService.getBaseMapper().insert(type);
        return insert+"";
    }

    @RequestMapping(value = "/getAllAddress" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllAddress() {
        List<Address> types = addressService.getBaseMapper().selectList(null);
//        System.out.println(types);
        String rtnUser = JSON.toJSONString(types, SerializerFeature.WriteMapNullValue);
        System.out.println(rtnUser);
        return new ResponseEntity<String>(rtnUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteAddress" , method = RequestMethod.GET)
    public ResponseEntity<?> deleteAddress(@RequestParam("id") String id) {
        System.out.println(id);
        int info = addressService.getBaseMapper().deleteById(id);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updateAddress" , method = RequestMethod.POST)
    public ResponseEntity<?> updateAddress(@RequestBody Address type) {
        int info = addressService.getBaseMapper().updateById(type);
        if( info == 1) {
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }
}
