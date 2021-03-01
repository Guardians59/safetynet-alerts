package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.models.FloodAddressModel;
import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.FloodPersonModel;
import com.openclassrooms.safetynetalerts.models.FloodStationModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IFloodService;

/**
 * La classe FloodServiceImpl est l'implémentation de l'interface IFloodService.
 * 
 * @see IFloodService
 * @author Dylan
 *
 */
@Service
public class FloodServiceImpl implements IFloodService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(FloodServiceImpl.class);
    private int key;
    private int numberOfStationsFound;
    private int homesNumber;
    private boolean stationFound;

    @Override
    public HashMap<String, Object> findHomesServedByTheStation(List<Integer> station)
	    throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<FloodStationModel> listFloodStationModel = new ArrayList<>();
	List<FireStationsModel> listStationsModel = repository.getFireStations();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	List<MedicalRecordsModel> listMedicalRecordsModel = repository.getMedicalRecords();
	key = 0;
	stationFound = false;
	homesNumber = 0;
	Date dateNow = new Date();

	/**
	 * Nous utilisons une boucle forEach pour la liste des casernes entrées en
	 * paramètre, à chaque numéro nous initions une autre boucle dans la liste des
	 * casernes enregistrées pour vérifier qu'une caserne corresponde bien au numéro
	 * indiquée, nous récupérons l'adresse couverte par celle-ci et initions une
	 * troisième boucle dans la liste des personnes afin de trouver les personnes
	 * vivant à cette adresse, nous utilisons une dernière boucle afin de récupérer
	 * les dossiers médicaux de ces personnes et ajoutons ces résultats dans une
	 * liste. Chaque listAddressModel correspond à un foyer avec toutes les
	 * personnes comprenant leurs informations y vivant. Chaque
	 * listFloodStationModel correspond à la liste de tous les foyers couvert par la
	 * caserne. Nous renvoyons donc le résultat dans une hashmap où chaque station à
	 * son numéro et la liste de tous les foyers et personnes couvertes
	 * correspondante.
	 */
	station.forEach(stationNumber -> {
	    key = stationNumber.intValue();
	    numberOfStationsFound = 0;
	    FloodStationModel floodStationModel = new FloodStationModel();
	    ArrayList<FloodAddressModel> listAddressModel = new ArrayList<>();
	    ArrayList<PersonsModel> listPersonsCheckDuplicate = new ArrayList<>();

	    logger.debug("Search homes served by the station number " + key);
	    listStationsModel.forEach(fireStation -> {
		if (fireStation.getStation() == key) {

		    FloodAddressModel addressModel = new FloodAddressModel();
		    numberOfStationsFound++;
		    stationFound = true;
		    ArrayList<FloodPersonModel> listFloodPersonModel = new ArrayList<>();
		    listPersonsModel.forEach(person -> {
			if (person.getAddress().equals(fireStation.getAddress())
				&& !listPersonsCheckDuplicate.contains(person)) {
			    listPersonsCheckDuplicate.add(person);
			    addressModel.setAddress(person.getAddress());
			    addressModel.setCity(person.getCity());
			    addressModel.setZip(person.getZip());

			    FloodPersonModel personInfosFloodModel = new FloodPersonModel();
			    personInfosFloodModel.setLastName(person.getLastName());
			    personInfosFloodModel.setPhoneNumber(person.getPhone());

			    listMedicalRecordsModel.forEach(medicalRecords -> {
				if (medicalRecords.getFirstName().equals(person.getFirstName())
					&& medicalRecords.getLastName().equals(person.getLastName())) {

				    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				    Date birthDatePerson;

				    try {

					birthDatePerson = format.parse(medicalRecords.getBirthdate());

					double value = (dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0;
					int age = (int) value;
					personInfosFloodModel.setAge(age);
					personInfosFloodModel.setMedications(medicalRecords.getMedications());
					personInfosFloodModel.setAllergies(medicalRecords.getAllergies());
					listFloodPersonModel.add(personInfosFloodModel);

				    } catch (ParseException e) {
					logger.error("Error when parsing birthdate", e);
				    }

				}
			    });

			}

		    });
		    addressModel.setListPersonsInHome(listFloodPersonModel);
		    if (!addressModel.getListPersonsInHome().isEmpty()) {
			listAddressModel.add(addressModel);
			homesNumber++;
		    }
		}

	    });
	    floodStationModel.setStationNumber(key);
	    floodStationModel.setListHomes(listAddressModel);
	    if (!floodStationModel.getListHomes().isEmpty()) {
		listFloodStationModel.add(floodStationModel);
	    }

	    if (numberOfStationsFound == 0) {
		logger.error("There is no station corresponding to the number " + key);
	    } else if (numberOfStationsFound != 0 && listFloodStationModel.get(0).getListHomes().isEmpty()) {
		logger.info("There are no homes served by the station number " + key);
	    }

	});

	if (stationFound == true && !listFloodStationModel.get(0).getListHomes().isEmpty()) {
	    result.put("stations", listFloodStationModel);
	    logger.info(homesNumber + " homes covered found");
	}
	return result;
    }

}
