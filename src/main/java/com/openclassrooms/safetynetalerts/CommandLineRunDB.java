package com.openclassrooms.safetynetalerts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.FireStationsRepository;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordsRepository;
import com.openclassrooms.safetynetalerts.repository.PersonsRepository;
@Component
public class CommandLineRunDB implements CommandLineRunner {
    
    @Autowired
    FireStationsRepository fireStationsRepository;
    @Autowired
    PersonsRepository personsRepository;
    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;
    
    public static List<FireStationsModel> stations;
    public static List<PersonsModel> persons;
    public static List<MedicalRecordsModel> medical;

    @Override
    public void run(String... args) throws Exception {
	 stations = fireStationsRepository.findAll();
	 persons = personsRepository.findAll();
	 medical = medicalRecordsRepository.findAll();
	
    }

}
