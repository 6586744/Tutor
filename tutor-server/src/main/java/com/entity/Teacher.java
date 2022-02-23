package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "teacher")
public class Teacher {

    @TableId(value = "t_id", type = IdType.ASSIGN_UUID)
    private String t_id;
    private String t_name;
    private String t_sex;
    private String t_phone;
    private String t_password;
    private String t_show;
    private String t_email;
    private String t_address_id;
    private String t_address_name;
    private String t_baidu_address;
    private String t_remake;
    private String t_card;
    private String t_card_image;
    private String  t_credit;
    private String t_status;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    private String t_image;

    public String getT_image() {
        return t_image;
    }

    public void setT_image(String t_image) {
        this.t_image = t_image;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_sex() {
        return t_sex;
    }

    public void setT_sex(String t_sex) {
        this.t_sex = t_sex;
    }

    public String getT_phone() {
        return t_phone;
    }

    public void setT_phone(String t_phone) {
        this.t_phone = t_phone;
    }

    public String getT_password() {
        return t_password;
    }

    public void setT_password(String t_password) {
        this.t_password = t_password;
    }

    public String getT_show() {
        return t_show;
    }

    public void setT_show(String t_show) {
        this.t_show = t_show;
    }

    public String getT_email() {
        return t_email;
    }

    public void setT_email(String t_email) {
        this.t_email = t_email;
    }

    public String getT_address_id() {
        return t_address_id;
    }

    public void setT_address_id(String t_address_id) {
        this.t_address_id = t_address_id;
    }

    public String getT_address_name() {
        return t_address_name;
    }

    public void setT_address_name(String t_address_name) {
        this.t_address_name = t_address_name;
    }

    public String getT_baidu_address() {
        return t_baidu_address;
    }

    public void setT_baidu_address(String t_baidu_address) {
        this.t_baidu_address = t_baidu_address;
    }

    public String getT_remake() {
        return t_remake;
    }

    public void setT_remake(String t_remake) {
        this.t_remake = t_remake;
    }

    public String getT_card() {
        return t_card;
    }

    public void setT_card(String t_card) {
        this.t_card = t_card;
    }

    public String getT_card_image() {
        return t_card_image;
    }

    public void setT_card_image(String t_card_image) {
        this.t_card_image = t_card_image;
    }

    public String getT_credit() {
        return t_credit;
    }

    public void setT_credit(String t_credit) {
        this.t_credit = t_credit;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
