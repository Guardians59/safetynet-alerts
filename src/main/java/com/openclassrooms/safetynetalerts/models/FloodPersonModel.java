package com.openclassrooms.safetynetalerts.models;

import java.util.List;

public class FloodPersonModel {

    private String lastName;
    private String phoneNumber;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public FloodPersonModel() {

    }

    public FloodPersonModel(String lastName, String phoneNumber, int age, List<String> medications, List<String> allergies) {

	this.lastName = lastName;
	this.phoneNumber = phoneNumber;
	this.age = age;
	this.medications = medications;
	this.allergies = allergies;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
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
	return "FloodPersonModel [lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", age=" + age + ", medications="
		+ medications + ", allergies=" + allergies + "]";
    }

}
