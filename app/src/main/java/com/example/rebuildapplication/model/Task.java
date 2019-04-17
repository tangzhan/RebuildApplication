package com.example.rebuildapplication.model;


import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Task extends LitePalSupport {


    private int id;//主键
    private float important_level;//重要程度
    private String title;//标题
    private String detail;//详情
    private User user;
    private Date set_time;//开始时间
    private Date deadline;//结束时间
    private int days;//花费时间
    private int task_state;//1表示已完成，0表示未完成
    private long mark;//外键


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public long getMark() {
        return mark;
    }

    public void setMark(long mark) {
        this.mark = mark;
    }



    public float getImportant_level() {
        return important_level;
    }

    public void setImportant_level(float important_level) {
        this.important_level = important_level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSet_time() {
        return set_time;
    }

    public void setSet_time(Date set_time) {
        this.set_time = set_time;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getTask_state() {
        return task_state;
    }

    public void setTask_state(int task_state) {
        this.task_state = task_state;
    }
}
