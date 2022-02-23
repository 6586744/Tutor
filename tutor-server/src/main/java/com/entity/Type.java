package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "type")
public class Type {

    @TableId(value = "t_id", type = IdType.ASSIGN_UUID)
    private String t_id;
    private String t_name;
    private String t_father_id;
    private String t_father_name;

    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    @Override
    public String toString() {
        return "Type{" +
                "t_id='" + t_id + '\'' +
                ", t_name='" + t_name + '\'' +
                ", t_father_id='" + t_father_id + '\'' +
                ", t_father_name='" + t_father_name + '\'' +
                ", time='" + time + '\'' +
                '}';
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

    public String getT_father_id() {
        return t_father_id;
    }

    public void setT_father_id(String t_father_id) {
        this.t_father_id = t_father_id;
    }

    public String getT_father_name() {
        return t_father_name;
    }

    public void setT_father_name(String t_father_name) {
        this.t_father_name = t_father_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
