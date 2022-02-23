package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "comment")
public class Comment {

    @TableId(value = "c_id",type = IdType.ASSIGN_UUID)
    private String c_id;
    private String c_order_id;
    private String c_teacher_id;
    private String c_user_id;
    private String c_user_name;
    private String c_user_image;
    private String c_level;
    private String c_content;
    @TableField(value = "time",fill = FieldFill.INSERT)
    private String time;
    private String c_user_show;

    public String getC_user_show() {
        return c_user_show;
    }

    public void setC_user_show(String c_user_show) {
        this.c_user_show = c_user_show;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "c_id='" + c_id + '\'' +
                ", c_order_id='" + c_order_id + '\'' +
                ", c_teacher_id='" + c_teacher_id + '\'' +
                ", c_user_id='" + c_user_id + '\'' +
                ", c_user_name='" + c_user_name + '\'' +
                ", c_user_image='" + c_user_image + '\'' +
                ", c_level='" + c_level + '\'' +
                ", c_content='" + c_content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_order_id() {
        return c_order_id;
    }

    public void setC_order_id(String c_order_id) {
        this.c_order_id = c_order_id;
    }

    public String getC_teacher_id() {
        return c_teacher_id;
    }

    public void setC_teacher_id(String c_teacher_id) {
        this.c_teacher_id = c_teacher_id;
    }

    public String getC_user_id() {
        return c_user_id;
    }

    public void setC_user_id(String c_user_id) {
        this.c_user_id = c_user_id;
    }

    public String getC_user_name() {
        return c_user_name;
    }

    public void setC_user_name(String c_user_name) {
        this.c_user_name = c_user_name;
    }

    public String getC_user_image() {
        return c_user_image;
    }

    public void setC_user_image(String c_user_image) {
        this.c_user_image = c_user_image;
    }

    public String getC_level() {
        return c_level;
    }

    public void setC_level(String c_level) {
        this.c_level = c_level;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
