package com.openclassrooms.safetynetalerts.models;

import java.util.List;

public class PersonInfoModel {

    private String lastName;
    private String address;
    private String city;
    private int zip;
    private int age;
    private String mail;
    private List<String> medications;
    private List<String> allergies;

    public PersonInfoModel() {

    }

    public PersonInfoModel(String lastName, String address, String city, int zip, int age, String mail,
	    List<String> medications, List<String> allergies) {

	this.lastName = lastName;
	this.address = address;
	this.city = city;
	this.zip = zip;
	this.setAge(age);
	this.mail = mail;
	this.medications = medications;
	this.allergies = allergies;
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

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    public String getMail() {
	return mail;
    }

    public void setMail(String mail) {
	this.mail = mail;
    }

    public List<String> getMedications() {
	return medications;
    }

    public void setMedications(List<String> medications) {
	this.medications = medications;
    }

    public List<String> getAllergies() {
	return allergies;
    }

    public void setAllergies(List<String> allergies) {
	this.allergies = allergies;
    }

    @Override
    public String toString() {
	return "PersonInfoModel [lastName=" + lastName + ", address=" + address + ", city=" + city + ", zip=" + zip
		+ ", age=" + age + ", mail=" + mail + ", medications=" + medications + ", allergies=" + allergies + "]";
    }

}
