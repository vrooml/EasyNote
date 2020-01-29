package com.fwwb.easynote.models;

import java.io.Serializable;
import java.util.Calendar;

public class Note implements Serializable{
    private int id;
    private String title;
    private String note;
    private Calendar calendar;
    private String location;


    public Note(){
    }

    public Note(int id,String title,String note,Calendar calendar,String location){
        this.id=id;
        this.title=title;
        this.note=note;
        this.calendar=calendar;
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

    public Calendar getCalendar(){
        return calendar;
    }

    public void setCalendar(Calendar calendar){
        this.calendar=calendar;
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
