package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.repository.FireStationsRepository;
import com.openclassrooms.safetynetalerts.services.IFireStationsService;

@Repository
public class FireStationsServiceImpl implements IFireStationsService{

    @Autowired FireStationsRepository fireStationsRepository;
    
    @Override
    public List<FireStationsModel> findAll() throws IOException {
	
	return fireStationsRepository.findAll();
    }

}
