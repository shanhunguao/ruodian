package com.ncu.springboot.biz.entity;

import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2020/3/20 9:46
 * ip地址 转物理地址
 */
public class Address implements Serializable {

    private String status;
    private String info;
    private String infocode;
    //    省份名称
    private String province;
    //    城市名称
    private String city;
    //城市的adcode编码
    private String adcode;
    //    所在城市矩形区域范围
    private String rectangle;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getRectangle() {
        return rectangle;
    }

    public void setRectangle(String rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public String toString() {
        return "Address{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", infocode='" + infocode + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", adcode='" + adcode + '\'' +
                ", rectangle='" + rectangle + '\'' +
                '}';
    }
}
