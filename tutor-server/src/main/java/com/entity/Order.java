package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "u_order")
public class Order {

    @TableId(value = "o_id", type = IdType.ASSIGN_UUID)
    private String o_id;
    private String o_student_id;
    private String o_student_name;
    private String o_student_image;
    private String o_name;
    private String o_type;
    private String o_sex;
    private String o_age;
    private String o_reamke;
    private String o_address;
    private String o_time;
    private String o_teacher_id;
    private String o_teacher_name;
    private String o_teacher_image;
    private String o_user_status;
    private String o_teacher_status;
    private String o_status;
    private String o_phone;
    private String o_price;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    private String o_teacher_phone;

    public String getO_teacher_phone() {
        return o_teacher_phone;
    }

    public void setO_teacher_phone(String o_teacher_phone) {
        this.o_teacher_phone = o_teacher_phone;
    }

    public String getO_phone() {
        return o_phone;
    }

    public void setO_phone(String o_phone) {
        this.o_phone = o_phone;
    }

    public String getO_price() {
        return o_price;
    }

    public void setO_price(String o_price) {
        this.o_price = o_price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "o_id='" + o_id + '\'' +
                ", o_student_id='" + o_student_id + '\'' +
                ", o_student_name='" + o_student_name + '\'' +
                ", o_student_image='" + o_student_image + '\'' +
                ", o_name='" + o_name + '\'' +
                ", o_type='" + o_type + '\'' +
                ", o_sex='" + o_sex + '\'' +
                ", o_age='" + o_age + '\'' +
                ", o_reamke='" + o_reamke + '\'' +
                ", o_address='" + o_address + '\'' +
                ", o_time='" + o_time + '\'' +
                ", o_teacher_id='" + o_teacher_id + '\'' +
                ", o_teacher_name='" + o_teacher_name + '\'' +
                ", o_teacher_image='" + o_teacher_image + '\'' +
                ", o_user_status='" + o_user_status + '\'' +
                ", o_teacher_status='" + o_teacher_status + '\'' +
                ", o_status='" + o_status + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public String getO_student_id() {
        return o_student_id;
    }

    public void setO_student_id(String o_student_id) {
        this.o_student_id = o_student_id;
    }

    public String getO_student_name() {
        return o_student_name;
    }

    public void setO_student_name(String o_student_name) {
        this.o_student_name = o_student_name;
    }

    public String getO_student_image() {
        return o_student_image;
    }

    public void setO_student_image(String o_student_image) {
        this.o_student_image = o_student_image;
    }

    public String getO_name() {
        return o_name;
    }

    public void setO_name(String o_name) {
        this.o_name = o_name;
    }

    public String getO_type() {
        return o_type;
    }

    public void setO_type(String o_type) {
        this.o_type = o_type;
    }

    public String getO_sex() {
        return o_sex;
    }

    public void setO_sex(String o_sex) {
        this.o_sex = o_sex;
    }

    public String getO_age() {
        return o_age;
    }

    public void setO_age(String o_age) {
        this.o_age = o_age;
    }

    public String getO_reamke() {
        return o_reamke;
    }

    public void setO_reamke(String o_reamke) {
        this.o_reamke = o_reamke;
    }

    public String getO_address() {
        return o_address;
    }

    public void setO_address(String o_address) {
        this.o_address = o_address;
    }

    public String getO_time() {
        return o_time;
    }

    public void setO_time(String o_time) {
        this.o_time = o_time;
    }

    public String getO_teacher_id() {
        return o_teacher_id;
    }

    public void setO_teacher_id(String o_teacher_id) {
        this.o_teacher_id = o_teacher_id;
    }

    public String getO_teacher_name() {
        return o_teacher_name;
    }

    public void setO_teacher_name(String o_teacher_name) {
        this.o_teacher_name = o_teacher_name;
    }

    public String getO_teacher_image() {
        return o_teacher_image;
    }

    public void setO_teacher_image(String o_teacher_image) {
        this.o_teacher_image = o_teacher_image;
    }

    public String getO_user_status() {
        return o_user_status;
    }

    public void setO_user_status(String o_user_status) {
        this.o_user_status = o_user_status;
    }

    public String getO_teacher_status() {
        return o_teacher_status;
    }

    public void setO_teacher_status(String o_teacher_status) {
        this.o_teacher_status = o_teacher_status;
    }

    public String getO_status() {
        return o_status;
    }

    public void setO_status(String o_status) {
        this.o_status = o_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
