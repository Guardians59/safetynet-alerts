package com.openclassrooms.safetynetalerts.models;

public class FireStationsPersonsModel {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;

    public FireStationsPersonsModel() {

    }

    public FireStationsPersonsModel(String firstName, String lastName, String address, String city, int zip,
	    String phone) {

	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.city = city;
	this.zip = zip;
	this.phone = phone;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
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

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    @Override
    public String toString() {
	return "FireStationsPersonsModel [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
		+ ", city=" + city + ", zip=" + zip + ", phone=" + phone + "]";
    }

}
