package com.openclassrooms.safetynetalerts.models;

import java.util.List;

public class FloodAddressModel {

    private String address;
    private String city;
    private int zip;
    private List<FloodPersonModel> listPersonsInHome;

    public FloodAddressModel() {

    }

    public FloodAddressModel(String address, String city, int zip, List<FloodPersonModel> listPersonsInHome) {

	this.address = address;
	this.city = city;
	this.zip = zip;
	this.listPersonsInHome = listPersonsInHome;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public int getZip() {
	return zip;
    }

    public void setZip(int zip) {
	this.zip = zip;
    }

    public List<FloodPersonModel> getListPersonsInHome() {
	return listPersonsInHome;
    }

    public void setListPersonsInHome(List<FloodPersonModel> listPersonsInHome) {
	this.listPersonsInHome = listPersonsInHome;
    }

    @Override
    public String toString() {
	return "FloodAddressModel [address=" + address + ", city=" + city + ", zip=" + zip + ", listPersonsInHome="
		+ listPersonsInHome + "]";
    }

}
