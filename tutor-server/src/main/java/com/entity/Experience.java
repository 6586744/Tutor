package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "t_experience")
public class Experience {

    @TableId(value = "e_id", type = IdType.ASSIGN_UUID)
    private String e_id;
    private String e_teacher_id;
    private String e_address;
    private String e_start_time;
    private String e_end_time;
    private String e_remake;
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

    public String getE_address() {
        return e_address;
    }

    public void setE_address(String e_address) {
        this.e_address = e_address;
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

    public String getE_remake() {
        return e_remake;
    }

    public void setE_remake(String e_remake) {
        this.e_remake = e_remake;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
