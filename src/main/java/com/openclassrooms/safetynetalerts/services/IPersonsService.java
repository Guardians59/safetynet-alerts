package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.safetynetalerts.models.PersonsModel;

public interface IPersonsService {

    public List<PersonsModel> findAll() throws IOException;
    
    public ArrayList<String> findEmail(String city) throws IOException;

}
