package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.PersonsRepository;
import com.openclassrooms.safetynetalerts.services.IPersonsService;

@Repository
public class PersonsServiceImpl implements IPersonsService {

    @Autowired
    PersonsRepository personsRepository;

    @Override
    public List<PersonsModel> findAll() throws IOException {

	return personsRepository.findAll();

    }

    @Override
    public ArrayList<String> findEmail(String city) throws IOException {

	ArrayList<String> result = new ArrayList<>();
	List<PersonsModel> list = personsRepository.findAll();

	list.forEach(person -> {
	    if (person.getCity().equals(city)) {
		result.add(person.getEmail());
	    }
	});

	return result;
    }

}
