package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;

public interface IFireStationsService {
    
    public List<FireStationsModel> findAll() throws IOException;
    
    public ArrayList<PersonsModel> findByStationNumber(int station) throws IOException;
    
}
