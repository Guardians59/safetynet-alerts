package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public interface IPersonInfoService {
    
    public HashMap<String, Object> findPersonInfoByName(String firstName, String lastName)
	    throws IOException, ParseException;

}
