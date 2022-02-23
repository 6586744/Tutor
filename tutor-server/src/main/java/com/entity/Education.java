package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "t_education")
public class Education {

    @TableId(value = "e_id", type = IdType.ASSIGN_UUID)
    private String e_id;
    private String e_school;
    private String e_teacher_id;
    private String e_academic;
    private String e_start_time;
    private String e_end_time;
    private String e_certificate;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getE_school() {
        return e_school;
    }

    public void setE_school(String e_school) {
        this.e_school = e_school;
    }

    public String getE_teacher_id() {
        return e_teacher_id;
    }

    public void setE_teacher_id(String e_teacher_id) {
        this.e_teacher_id = e_teacher_id;
    }

    public String getE_academic() {
        return e_academic;
    }

    public void setE_academic(String e_academic) {
        this.e_academic = e_academic;
    }

    public String getE_start_time() {
        return e_start_time;
    }

    public void setE_start_time(String e_start_time) {
        this.e_start_time = e_start_time;
    }

    public String getE_end_time() {
        return e_end_time;
    }

    public void setE_end_time(String e_end_time) {
        this.e_end_time = e_end_time;
    }

    public String getE_certificate() {
        return e_certificate;
    }

    public void setE_certificate(String e_certificate) {
        this.e_certificate = e_certificate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
