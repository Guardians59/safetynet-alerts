package com.openclassrooms.safetynetalerts.models;

import java.util.List;

public class MedicalRecordsModel {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    public MedicalRecordsModel() {

    }

    public MedicalRecordsModel(String firstName, String lastName, String birthdate, List<String> medications,
	    List<String> allergies) {

	this.firstName = firstName;
	this.lastName = lastName;
	this.birthdate = birthdate;
	this.medications = medications;
	this.allergies = allergies;
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

    public String getBirthdate() {
	return birthdate;
    }

    public void setBirthdate(String birthdate) {
	this.birthdate = birthdate;
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
	return "MedicalRecordsModel [firstName=" + firstName + ", lastName=" + lastName + ", birthdate=" + birthdate
		+ ", medications=" + medications + ", allergies=" + allergies + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((allergies == null) ? 0 : allergies.hashCode());
	result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((medications == null) ? 0 : medications.hashCode());
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
	MedicalRecordsModel other = (MedicalRecordsModel) obj;
	if (allergies == null) {
	    if (other.allergies != null)
		return false;
	} else if (!allergies.equals(other.allergies))
	    return false;
	if (birthdate == null) {
	    if (other.birthdate != null)
		return false;
	} else if (!birthdate.equals(other.birthdate))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	if (medications == null) {
	    if (other.medications != null)
		return false;
	} else if (!medications.equals(other.medications))
	    return false;
	return true;
    }

}
