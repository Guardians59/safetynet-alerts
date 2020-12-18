package com.openclassrooms.safetynetalerts.models;

public class FireStationsModel {

    private String address;
    private int station;

    public FireStationsModel() {

    }

    public FireStationsModel(String address, int station) {

	this.address = address;
	this.station = station;

    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public int getStation() {
	return station;
    }

    public void setStation(int station) {
	this.station = station;
    }

    @Override
    public String toString() {
	return "FireStationsModel [address=" + address + ", station=" + station + "]";
    }

}
