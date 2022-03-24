package com.example.tourandtravelmanagement.Model;

public class Parks {
    private String pname,description,address,city,date,image,pid,time;

    public Parks() {

    }
    public Parks(String pname, String description, String address, String city, String date, String image, String pid, String time) {
        this.pname = pname;
        this.description = description;
        this.address = address;
        this.city = city;
        this.date = date;
        this.image = image;
        this.pid = pid;

    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }




}
