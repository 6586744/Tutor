package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "blog")
public class Blog {

    @TableId(value = "b_id", type = IdType.ASSIGN_UUID)
    private String b_id;
    private String b_user_id;
    private String b_user_name;
    private String b_title;
    private String b_image;
    private String b_content;
    private String b_show;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    @Override
    public String toString() {
        return "Blog{" +
                "b_id='" + b_id + '\'' +
                ", b_user_id='" + b_user_id + '\'' +
                ", b_user_name='" + b_user_name + '\'' +
                ", b_title='" + b_title + '\'' +
                ", b_image='" + b_image + '\'' +
                ", b_content='" + b_content + '\'' +
                ", b_show='" + b_show + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getB_user_id() {
        return b_user_id;
    }

    public void setB_user_id(String b_user_id) {
        this.b_user_id = b_user_id;
    }

    public String getB_user_name() {
        return b_user_name;
    }

    public void setB_user_name(String b_user_name) {
        this.b_user_name = b_user_name;
    }

    public String getB_title() {
        return b_title;
    }

    public void setB_title(String b_title) {
        this.b_title = b_title;
    }

    public String getB_image() {
        return b_image;
    }

    public void setB_image(String b_image) {
        this.b_image = b_image;
    }

    public String getB_content() {
        return b_content;
    }

    public void setB_content(String b_content) {
        this.b_content = b_content;
    }

    public String getB_show() {
        return b_show;
    }

    public void setB_show(String b_show) {
        this.b_show = b_show;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
