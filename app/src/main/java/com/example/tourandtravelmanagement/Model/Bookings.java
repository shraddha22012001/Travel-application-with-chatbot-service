package com.example.tourandtravelmanagement.Model;

public class Bookings {
    private String name,phone,address,fromdate,nodays,checkintime,date,time,rooms,childs,adults,hotelname,hoteladdress;

    public Bookings(){

    }
    public Bookings(String name,String phone,String address,String fromdate,String nodays,String checkintime,String date,String time,String rooms,String childs,String adults,String hotelname,String hoteladdress){
          this.name=name;
          this.phone=phone;
          this.address=address;
          this.rooms=rooms;
          this.adults=adults;
          this.childs=childs;
          this.fromdate=fromdate;
          this.nodays=nodays;
          this.checkintime=checkintime;
          this.date=date;
          this.time=time;
          this.hotelname=hotelname;
          this.hoteladdress=hoteladdress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getNodays() {
        return nodays;
    }

    public void setNodays(String nodays) {
        this.nodays = nodays;
    }

    public String getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(String checkintime) {
        this.checkintime = checkintime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getChilds() {
        return childs;
    }

    public void setChilds(String childs) {
        this.childs = childs;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getHoteladdress() {
        return hoteladdress;
    }

    public void setHoteladdress(String hoteladdress) {
        this.hoteladdress = hoteladdress;
    }
}
