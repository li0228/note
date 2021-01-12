package com.lhh.annotation;

/**
 * @author lihonghao
 * @data 2021/1/11 19:49
 */
public class Person {
    @MyAnno
    private String stra;
    private String strb;
    private String strc;

    public String getStra() {
        return stra;
    }

    public void setStra(String stra) {
        this.stra = stra;
    }

    public String getStrb() {
        return strb;
    }

    public void setStrb(String strb) {
        this.strb = strb;
    }

    public String getStrc() {
        return strc;
    }

    public void setStrc(String strc) {
        this.strc = strc;
    }

    @Override
    public String toString() {
        return "Person{" +
                "stra='" + stra + '\'' +
                ", strb='" + strb + '\'' +
                ", strc='" + strc + '\'' +
                '}';
    }

    public Person() {
    }

    public Person(String stra, String strb, String strc) {
        this.stra = stra;
        this.strb = strb;
        this.strc = strc;
    }
}
