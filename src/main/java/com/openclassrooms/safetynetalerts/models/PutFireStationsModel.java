package com.openclassrooms.safetynetalerts.models;

public class PutFireStationsModel {

    private String address;
    private int oldStationNumber;
    private int newStationNumber;

    public PutFireStationsModel() {

    }

    public PutFireStationsModel(String address, int oldStationNumber, int newStationNumber) {

	this.address = address;
	this.oldStationNumber = oldStationNumber;
	this.newStationNumber = newStationNumber;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public int getOldStationNumber() {
	return oldStationNumber;
    }

    public void setOldStationNumber(int oldStationNumber) {
	this.oldStationNumber = oldStationNumber;
    }

    public int getNewStationNumber() {
	return newStationNumber;
    }

    public void setNewStationNumber(int newStationNumber) {
	this.newStationNumber = newStationNumber;
    }

    @Override
    public String toString() {
	return "PutFireStationsModel [address=" + address + ", oldStationNumber=" + oldStationNumber
		+ ", newStationNumber=" + newStationNumber + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + newStationNumber;
	result = prime * result + oldStationNumber;
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
	PutFireStationsModel other = (PutFireStationsModel) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (newStationNumber != other.newStationNumber)
	    return false;
	if (oldStationNumber != other.oldStationNumber)
	    return false;
	return true;
    }

}
