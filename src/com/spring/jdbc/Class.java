package com.spring.jdbc;

/**
 * Created by Administrator on 2016-12-01.
 */
public class Class {
    private String classname;
    private Integer classid;

    @Override
    public String toString() {
        return "Class{" +
                "classname='" + classname + '\'' +
                ", classid=" + classid +
                '}';
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }
}
