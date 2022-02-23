package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "user_subscribe")
public class UserSubscribe {

    @TableId(value = "s_id", type = IdType.ASSIGN_UUID)
    private String s_id;
    private String s_user_id;
    private String s_user_name;
    private String s_user_image;
    private String s_order_id;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    private String s_order_name;
    private String  s_order_address;

    public String getS_order_name() {
        return s_order_name;
    }

    public void setS_order_name(String s_order_name) {
        this.s_order_name = s_order_name;
    }

    public String getS_order_address() {
        return s_order_address;
    }

    public void setS_order_address(String s_order_address) {
        this.s_order_address = s_order_address;
    }

    @Override
    public String toString() {
        return "UserSubscribe{" +
                "s_id='" + s_id + '\'' +
                ", s_user_id='" + s_user_id + '\'' +
                ", s_user_name='" + s_user_name + '\'' +
                ", s_user_image='" + s_user_image + '\'' +
                ", s_order_id='" + s_order_id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_user_id() {
        return s_user_id;
    }

    public void setS_user_id(String s_user_id) {
        this.s_user_id = s_user_id;
    }

    public String getS_user_name() {
        return s_user_name;
    }

    public void setS_user_name(String s_user_name) {
        this.s_user_name = s_user_name;
    }

    public String getS_user_image() {
        return s_user_image;
    }

    public void setS_user_image(String s_user_image) {
        this.s_user_image = s_user_image;
    }

    public String getS_order_id() {
        return s_order_id;
    }

    public void setS_order_id(String s_order_id) {
        this.s_order_id = s_order_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
