package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "address")
public class Address {

    @TableId(value = "a_id", type = IdType.ASSIGN_UUID)
    private String a_id;
    private String a_name;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
