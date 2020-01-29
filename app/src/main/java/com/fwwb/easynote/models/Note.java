package com.fwwb.easynote.models;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Calendar;

public class Note extends LitePalSupport{
    private int id;
    private String title=null;
    private String note=null;
    private int year;
    private int month;
    private int day;
    private String Time=null;
    private String location=null;


    public Note(){
    }

    public Note(int id,String title,String note,int year,int month,int day,String time,String location){
        this.id=id;
        this.title=title;
        this.note=note;
        this.year=year;
        this.month=month;
        this.day=day;
        Time=time;
        this.location=location;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note=note;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year=year;
    }

    public int getMonth(){
        return month;
    }

    public void setMonth(int month){
        this.month=month;
    }

    public int getDay(){
        return day;
    }

    public void setDay(int day){
        this.day=day;
    }

    public String getTime(){
        return Time;
    }

    public void setTime(String time){
        Time=time;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location=location;
    }
}
