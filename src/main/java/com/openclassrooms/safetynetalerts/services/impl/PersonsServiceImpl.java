package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IPersonsService;

@Repository
public class PersonsServiceImpl implements IPersonsService {

    @Autowired
    DBRepository repository;

    @Override
    public List<PersonsModel> findAll() throws IOException {

	return repository.getPersons();

    }

}
