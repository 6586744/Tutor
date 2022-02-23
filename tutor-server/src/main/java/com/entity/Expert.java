package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "t_expert")
public class Expert {

    @TableId(value = "e_id", type = IdType.ASSIGN_UUID)
    private String e_id;
    private String e_teacher_id;
    private String e_big_type_id;
    private String e_big_type_name;
    private String e_type_id;
    private String e_type_name;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getE_teacher_id() {
        return e_teacher_id;
    }

    public void setE_teacher_id(String e_teacher_id) {
        this.e_teacher_id = e_teacher_id;
    }

    public String getE_big_type_id() {
        return e_big_type_id;
    }

    public void setE_big_type_id(String e_big_type_id) {
        this.e_big_type_id = e_big_type_id;
    }

    public String getE_big_type_name() {
        return e_big_type_name;
    }

    public void setE_big_type_name(String e_big_type_name) {
        this.e_big_type_name = e_big_type_name;
    }

    public String getE_type_id() {
        return e_type_id;
    }

    public void setE_type_id(String e_type_id) {
        this.e_type_id = e_type_id;
    }

    public String getE_type_name() {
        return e_type_name;
    }

    public void setE_type_name(String e_type_name) {
        this.e_type_name = e_type_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
