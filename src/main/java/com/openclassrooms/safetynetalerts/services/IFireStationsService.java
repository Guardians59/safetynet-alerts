package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.List;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;

public interface IFireStationsService {
    
    public List<FireStationsModel> findAll() throws IOException;
    
}
