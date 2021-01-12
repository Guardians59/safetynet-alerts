package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;

public interface IFireStationsService {
    
    public List<FireStationsModel> findAll() throws IOException;
    
    public HashMap<String, Object> findPersonsByStationNumber(int station) throws IOException, ParseException;
    
}
