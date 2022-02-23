package com.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName(value = "price")
public class Price {

    @TableId(value = "p_id", type = IdType.ASSIGN_UUID)
    private String p_id;
    private String p_name;
    private String p_start_price;
    private String p_end_price;
    @TableField(value = "time", fill = FieldFill.INSERT)
    private String time;

    @Override
    public String toString() {
        return "Price{" +
                "p_id='" + p_id + '\'' +
                ", p_name='" + p_name + '\'' +
                ", p_start_price='" + p_start_price + '\'' +
                ", p_end_price='" + p_end_price + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_start_price() {
        return p_start_price;
    }

    public void setP_start_price(String p_start_price) {
        this.p_start_price = p_start_price;
    }

    public String getP_end_price() {
        return p_end_price;
    }

    public void setP_end_price(String p_end_price) {
        this.p_end_price = p_end_price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
