package com.fwwb.easynote.models;

public class Note{
    private String title;
    private String note;
    private String date;
    private String time;
    private String location;

    public Note(String title,String note,String date,String time,String location){
        this.title=title;
        this.note=note;
        this.date=date;
        this.time=time;
        this.location=location;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note=note;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date=date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
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
