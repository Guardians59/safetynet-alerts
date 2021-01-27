package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public interface IFireService {

    public HashMap<String, Object> findPersonsAndFireStationAtTheAddress(String address) 
	    throws IOException, ParseException;
}
