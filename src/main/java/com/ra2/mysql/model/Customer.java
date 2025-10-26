package com.ra2.mysql.model;

import java.time.LocalDateTime;

public class Customer {

    private int id; 
    private String nombre;
    private String descr; 
    private int age; 
    private String course; 
    private LocalDateTime dataCreated; 
    private LocalDateTime dataUpdated; 

    public Customer() {
        this.dataCreated = LocalDateTime.now();
        this.dataUpdated = LocalDateTime.now();
    }

    public Customer(String nombre, String descr, int age, String course) {
        this.nombre = nombre;
        this.descr = descr;
        this.age = age;
        this.course = course;
        this.dataCreated = LocalDateTime.now();
        this.dataUpdated = LocalDateTime.now();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.dataUpdated = LocalDateTime.now();
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
        this.dataUpdated = LocalDateTime.now();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        this.dataUpdated = LocalDateTime.now();
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
        this.dataUpdated = LocalDateTime.now();
    }

    public LocalDateTime getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(LocalDateTime dataCreated) {
        this.dataCreated = dataCreated;
    }

    public LocalDateTime getDataUpdated() {
        return dataUpdated;
    }

    public void setDataUpdated(LocalDateTime dataUpdated) {
        this.dataUpdated = dataUpdated;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descr='" + descr + '\'' +
                ", age=" + age +
                ", course='" + course + '\'' +
                ", dataCreated=" + dataCreated +
                ", dataUpdated=" + dataUpdated +
                '}';
    }
}
