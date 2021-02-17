package com.openclassrooms.safetynetalerts.services.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.FireStationsPersonsModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.models.PutFireStationsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IFireStationsService;

@Repository
public class FireStationsServiceImpl implements IFireStationsService {

    @Autowired
    DBRepository repository;

    private static final Logger logger = LogManager.getLogger("FireStationsServiceImpl");
    private int numberOfStationsFound;
    private int numberOfMajorPerson;
    private int numberOfMinorPerson;

    @Override
    public List<FireStationsModel> findAll() throws IOException {
	List<FireStationsModel> listStations = repository.getFireStations();
	ArrayList<FireStationsModel> result = new ArrayList<>();
	listStations.forEach(stationRegister -> {
	    if (!result.contains(stationRegister)) {
		result.add(stationRegister);
	    }
	});

	return result;
    }

    @Override
    public HashMap<String, Object> findPersonsByStationNumber(int station) throws IOException, ParseException {

	HashMap<String, Object> result = new HashMap<>();
	ArrayList<PersonsModel> listPersons = new ArrayList<>();
	ArrayList<FireStationsPersonsModel> listPersonsMinor = new ArrayList<>();
	ArrayList<FireStationsPersonsModel> listPersonsMajor = new ArrayList<>();
	List<FireStationsModel> listStationsModel = repository.getFireStations();
	List<PersonsModel> listPersonsModel = repository.getPersons();
	List<MedicalRecordsModel> listMedicalModel = repository.getMedicalRecords();
	int key = station;
	Date dateNow = new Date();
	numberOfMajorPerson = 0;
	numberOfMinorPerson = 0;
	numberOfStationsFound = 0;

	logger.info("Search for people covered by the station number " + key);
	listStationsModel.forEach(fireStation -> {
	    if (fireStation.getStation() == key) {
		numberOfStationsFound++;

		listPersonsModel.forEach(person -> {

		    if (person.getAddress().equals(fireStation.getAddress()) && !listPersons.contains(person)) {

			listPersons.add(person);
			FireStationsPersonsModel fireStationsPersonsModel = new FireStationsPersonsModel();
			fireStationsPersonsModel.setFirstName(person.getFirstName());
			fireStationsPersonsModel.setLastName(person.getLastName());
			fireStationsPersonsModel.setAddress(person.getAddress());
			fireStationsPersonsModel.setCity(person.getCity());
			fireStationsPersonsModel.setZip(person.getZip());
			fireStationsPersonsModel.setPhone(person.getPhone());

			listMedicalModel.forEach(birthDate -> {

			    if (birthDate.getFirstName().equals(person.getFirstName())
				    && birthDate.getLastName().equals(person.getLastName())) {

				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				Date birthDatePerson;
				try {

				    birthDatePerson = format.parse(birthDate.getBirthdate());

				    if ((dateNow.getTime() - birthDatePerson.getTime()) / 31557600000.0 >= 19) {
					numberOfMajorPerson++;
					listPersonsMajor.add(fireStationsPersonsModel);
				    } else {
					numberOfMinorPerson++;
					listPersonsMinor.add(fireStationsPersonsModel);
				    }
				} catch (ParseException e) {
				    e.printStackTrace();
				}

			    }
			});

		    }
		});
	    }
	});

	if (numberOfStationsFound == 0) {
	    logger.error("There is no station corresponding to the number " + key);
	} else if (listPersons.isEmpty() && numberOfStationsFound != 0) {
	    logger.info("There is no person in the coverage area of ​​station number " + key);
	}

	if (!listPersonsMajor.isEmpty()) {
	    result.put("personsMajor", listPersonsMajor);

	} else if (listPersonsMajor.isEmpty() && numberOfStationsFound != 0 && !listPersons.isEmpty()) {
	    logger.info("There is no major person in the coverage area of ​​station number " + key);
	}
	if (!listPersonsMinor.isEmpty()) {
	    result.put("personsMinor", listPersonsMinor);

	} else if (listPersonsMinor.isEmpty() && numberOfStationsFound != 0 && !listPersons.isEmpty()) {
	    logger.info("There is no minor person in the coverage area of ​​station number " + key);
	}
	if (numberOfStationsFound != 0) {
	    result.put("numberOfMajorPerson", numberOfMajorPerson);
	    result.put("numberOfMinorPerson", numberOfMinorPerson);
	}
	return result;

    }

    public boolean saveNewFireStation(FireStationsModel fireStation) throws IOException {

	List<FireStationsModel> list = repository.getFireStations();
	boolean result = false;
	if (fireStation.getStation() > 0 && fireStation.getAddress() != null) {
	    logger.debug("Adding the new station");

	    if (!list.contains(fireStation)) {
		list.add(fireStation);
		logger.info("The station number " + fireStation.getStation() + " covering the address "
			+ fireStation.getAddress() + " was added successfully");
		result = true;
	    } else if (list.contains(fireStation)) {
		logger.error("The station number " + fireStation.getStation() + " covering the address "
			+ fireStation.getAddress() + " is already in the list");
	    } else {
		logger.error("Error encountered while adding the station");
	    }
	} else {
	    logger.error("Invalid station number, address");
	}
	return result;
    }

    public boolean updateFireStation(PutFireStationsModel fireStation) throws IOException {
	List<FireStationsModel> list = repository.getFireStations();
	numberOfStationsFound = 0;
	boolean result = false;

	if (fireStation.getAddress() != null && fireStation.getOldStationNumber() > 0
		&& fireStation.getNewStationNumber() > 0) {

	    list.forEach(stationRegister -> {
		if (stationRegister.getAddress().equals(fireStation.getAddress())
			&& stationRegister.getStation() == fireStation.getOldStationNumber()) {
		    stationRegister.setStation(fireStation.getNewStationNumber());
		    numberOfStationsFound++;
		}
	    });
	    if (numberOfStationsFound == 0) {
		logger.error("No station corresponds with the indicated address " + fireStation.getAddress());
	    } else if (numberOfStationsFound == 1) {
		logger.info("The station covering the address " + fireStation.getAddress() + " update validated");
		result = true;
	    } else {
		logger.error("Error encountered while updating the station");
	    }
	} else {
	    logger.error("Invalid informations, check that you have entered all the information correctly");
	}
	return result;
    }

    public boolean deleteFireStation(Optional<Integer> station, Optional<String> address)
	    throws IOException {
	List<FireStationsModel> list = repository.getFireStations();
	List<FireStationsModel> deleteList = new ArrayList<>();
	
	numberOfStationsFound = 0;
	boolean result = false;

	if (station.isPresent() && station.get() > 0 || address.isPresent() && address.get() != null) {
	    if (station.isPresent() && address.isPresent()) {
		FireStationsModel fireStationModel = new FireStationsModel();
		list.forEach(stationRegister -> {

		    if (stationRegister.getAddress().equals(address.get())
			    && stationRegister.getStation() == station.get()) {
			fireStationModel.setAddress(stationRegister.getAddress());
			fireStationModel.setStation(stationRegister.getStation());
			numberOfStationsFound++;
		    }
		});
		if (list.contains(fireStationModel)) {
		    list.remove(fireStationModel);
		}

		if (numberOfStationsFound == 0) {
		    logger.error("No firestation corresponds with the station number " + station.get() + " and address "
			    + address.get());

		} else if (!list.contains(fireStationModel)) {
		    logger.info("The station number " + station.get() + " covered address " + address.get()
			    + " have been deleted successfully");
		    result = true;
		} else {
		    logger.error("Error encountered while deleting the station");
		}
	    } else if (station.isPresent() && !address.isPresent()) {
		list.forEach(stationRegister -> {

		    if (stationRegister.getStation() == station.get()) {
			FireStationsModel fireStationModel = new FireStationsModel();
			fireStationModel.setAddress(stationRegister.getAddress());
			fireStationModel.setStation(stationRegister.getStation());
			deleteList.add(fireStationModel);
			numberOfStationsFound++;
		    }
		});
		
		if (list.containsAll(deleteList)) {
		    list.removeAll(deleteList);
		}
		if (numberOfStationsFound == 0) {
		    logger.error("No firestation corresponds with the station number " + station.get());
		} else if (!list.containsAll(deleteList)) {
		    logger.info("The station number " + station.get() + " has been successfully deleted");
		    result = true;
		} else {
		    logger.error("Error encountered while deleting the station");
		}

	    } else if (address.isPresent() && !station.isPresent()) {
		list.forEach(stationRegister -> {

		    if (stationRegister.getAddress().equals(address.get())) {
			FireStationsModel fireStationModel = new FireStationsModel();
			fireStationModel.setAddress(stationRegister.getAddress());
			fireStationModel.setStation(stationRegister.getStation());
			deleteList.add(fireStationModel);
			numberOfStationsFound++;

		    }
		});

		if (list.containsAll(deleteList)) {
		    list.removeAll(deleteList);
		}

		if (numberOfStationsFound == 0) {
		    logger.error("No firestation corresponds with the address " + address.get());
		} else if (!list.containsAll(deleteList)) {
		    logger.info("The address " + address.get() + " has been successfully deleted");
		    result = true;
		} else {
		    logger.error("Error encountered while deleting the station");
		}
	    } else {
		logger.error(
			"Error encountered while deleting the station, please enter a station number or an address covered by a station");
	    }
	}
	return result;
    }
}
