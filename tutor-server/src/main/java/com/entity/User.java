package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "user")
public class User {

    @TableId(value = "u_id", type = IdType.ASSIGN_UUID)
    private String u_id;
    private String u_name;
    private String u_sex;
    private String u_phone;
    private String u_password;
    private String u_image;
    private String u_show;
    private String u_email;
    private String u_address_id;
    private String u_address_name;
    private String u_baidu_address;
    private String u_remake;
    private String u_status;

    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;
    private String u_role;
    private String u_credit;
    private String u_card;
    private String u_card_image;

    private String u_authentication;

    private String u_teachering;

    public String getU_teachering() {
        return u_teachering;
    }

    public void setU_teachering(String u_teachering) {
        this.u_teachering = u_teachering;
    }

    public String getU_authentication() {
        return u_authentication;
    }

    public void setU_authentication(String u_authentication) {
        this.u_authentication = u_authentication;
    }

    public String getU_role() {
        return u_role;
    }

    public void setU_role(String u_role) {
        this.u_role = u_role;
    }

    public String getU_credit() {
        return u_credit;
    }

    public void setU_credit(String u_credit) {
        this.u_credit = u_credit;
    }

    public String getU_card() {
        return u_card;
    }

    public void setU_card(String u_card) {
        this.u_card = u_card;
    }

    public String getU_card_image() {
        return u_card_image;
    }

    public void setU_card_image(String u_card_image) {
        this.u_card_image = u_card_image;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id='" + u_id + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_sex='" + u_sex + '\'' +
                ", u_phone='" + u_phone + '\'' +
                ", u_password='" + u_password + '\'' +
                ", u_image='" + u_image + '\'' +
                ", u_show='" + u_show + '\'' +
                ", u_email='" + u_email + '\'' +
                ", u_address_id='" + u_address_id + '\'' +
                ", u_address_name='" + u_address_name + '\'' +
                ", u_baidu_address='" + u_baidu_address + '\'' +
                ", u_remake='" + u_remake + '\'' +
                ", u_status='" + u_status + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_sex() {
        return u_sex;
    }

    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_image() {
        return u_image;
    }

    public void setU_image(String u_image) {
        this.u_image = u_image;
    }

    public String getU_show() {
        return u_show;
    }

    public void setU_show(String u_show) {
        this.u_show = u_show;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_address_id() {
        return u_address_id;
    }

    public void setU_address_id(String u_address_id) {
        this.u_address_id = u_address_id;
    }

    public String getU_address_name() {
        return u_address_name;
    }

    public void setU_address_name(String u_address_name) {
        this.u_address_name = u_address_name;
    }

    public String getU_baidu_address() {
        return u_baidu_address;
    }

    public void setU_baidu_address(String u_baidu_address) {
        this.u_baidu_address = u_baidu_address;
    }

    public String getU_remake() {
        return u_remake;
    }

    public void setU_remake(String u_remake) {
        this.u_remake = u_remake;
    }

    public String getU_status() {
        return u_status;
    }

    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
