package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.HashMap;

public interface IPhoneAlertService {

    public HashMap<String, Object> findPhoneNumberPersonByTheStationNumber(int station) 
	    throws IOException;
}
