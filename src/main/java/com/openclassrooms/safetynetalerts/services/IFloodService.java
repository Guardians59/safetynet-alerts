package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface IFloodService {
    
    public HashMap<String, Object> findHomesServedByTheStation (List<Integer> station)
	    throws IOException, ParseException;

}
