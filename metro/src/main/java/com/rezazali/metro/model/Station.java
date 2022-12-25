package com.rezazali.metro.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Station implements Parcelable {

    private int id;
    private int lineId;
    private int orderID;
    private int stationDuration;
    private String title;
    private String titleEnglish;
    private int crossLineId;
    private String addr;
    private int ticket;
    private int escalator;
    private int atm;
    private int taxi;
    private int bus;
    private int phone;
    private int water;
    private int lost;
    private int parking;
    private int elevator;
    private String latitude;
    private String longitude;

    public Station() {

    }


    protected Station(Parcel in) {
        id = in.readInt();
        lineId = in.readInt();
        orderID = in.readInt();
        stationDuration = in.readInt();
        title = in.readString();
        titleEnglish = in.readString();
        crossLineId = in.readInt();
        addr = in.readString();
        ticket = in.readInt();
        escalator = in.readInt();
        atm = in.readInt();
        taxi = in.readInt();
        bus = in.readInt();
        phone = in.readInt();
        water = in.readInt();
        lost = in.readInt();
        parking = in.readInt();
        elevator = in.readInt();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getStationDuration() {
        return stationDuration;
    }

    public void setStationDuration(int stationDuration) {
        this.stationDuration = stationDuration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public int getCrossLineId() {
        return crossLineId;
    }

    public void setCrossLineId(int crossLineId) {
        this.crossLineId = crossLineId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public int getEscalator() {
        return escalator;
    }

    public void setEscalator(int escalator) {
        this.escalator = escalator;
    }

    public int getAtm() {
        return atm;
    }

    public void setAtm(int atm) {
        this.atm = atm;
    }

    public int getTaxi() {
        return taxi;
    }

    public void setTaxi(int taxi) {
        this.taxi = taxi;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int getElevator() {
        return elevator;
    }

    public void setElevator(int elevator) {
        this.elevator = elevator;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(lineId);
        parcel.writeInt(orderID);
        parcel.writeInt(stationDuration);
        parcel.writeString(title);
        parcel.writeString(titleEnglish);
        parcel.writeInt(crossLineId);
        parcel.writeString(addr);
        parcel.writeInt(ticket);
        parcel.writeInt(escalator);
        parcel.writeInt(atm);
        parcel.writeInt(taxi);
        parcel.writeInt(bus);
        parcel.writeInt(phone);
        parcel.writeInt(water);
        parcel.writeInt(lost);
        parcel.writeInt(parking);
        parcel.writeInt(elevator);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }
}

