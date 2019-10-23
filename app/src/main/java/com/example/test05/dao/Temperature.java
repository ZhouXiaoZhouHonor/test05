package com.example.test05.dao;

import java.io.Serializable;

//用来存储天气情况
public class Temperature implements Serializable {//必须实现接口才能完成序列化
    private static final long serialVersionUID = -7060210544600464481L;
    private String city_name;
    private int tm1,tm2;//tm1表示最高温度，tm2表示最低温度
    private String wind;//表示风的情况
    private String state_Detailed;
    private int tem_state1,tem_state2;

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public int getTm1() {
        return tm1;
    }

    public void setTm1(int tm1) {
        this.tm1 = tm1;
    }

    public int getTm2() {
        return tm2;
    }

    public void setTm2(int tm2) {
        this.tm2 = tm2;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_Detailed() {
        return state_Detailed;
    }

    public void setState_Detailed(String state_Detailed) {
        this.state_Detailed = state_Detailed;
    }

    public int getTem_state1() {
        return tem_state1;
    }

    public void setTem_state1(int tem_state) {
        this.tem_state1 = tem_state;
    }

    public int getTem_state2() {
        return tem_state2;
    }

    public void setTem_state2(int tem_state2) {
        this.tem_state2 = tem_state2;
    }

}
