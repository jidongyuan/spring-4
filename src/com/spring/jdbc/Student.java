package com.spring.jdbc;

/**
 * Created by Administrator on 2016-12-01.
 */
public class Student {
    private Integer id;
    private String student_name;
    private String password;
    private Integer classid;


    private Class aClass;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", student_name='" + student_name + '\'' +
                ", password='" + password + '\'' +
                ", aClass=" + aClass +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
