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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + station;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FireStationsModel other = (FireStationsModel) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (station != other.station)
	    return false;
	return true;
    }
    

}
