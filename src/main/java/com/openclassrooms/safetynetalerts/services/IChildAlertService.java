package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public interface IChildAlertService {

    public HashMap<String, Object> findChildAtAddress(String address) 
	    throws IOException, ParseException;
}
