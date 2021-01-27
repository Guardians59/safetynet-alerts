package com.openclassrooms.safetynetalerts.models;

import java.util.List;

public class FloodStationModel {

    private int stationNumber;
    private List<FloodAddressModel> listHomes;

    public FloodStationModel() {

    }

    public FloodStationModel(int stationNumber, List<FloodAddressModel> listHomes) {

	this.stationNumber = stationNumber;
	this.listHomes = listHomes;
    }

    public int getStationNumber() {
	return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
	this.stationNumber = stationNumber;
    }

    public List<FloodAddressModel> getListHomes() {
	return listHomes;
    }

    public void setListHomes(List<FloodAddressModel> listHomes) {
	this.listHomes = listHomes;
    }

    @Override
    public String toString() {
	return "FloodStationModel [stationNumber=" + stationNumber + ", listHomes=" + listHomes + "]";
    }

}
