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
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.FireStationsPersonsModel;
import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;
import com.openclassrooms.safetynetalerts.models.PutFireStationsModel;
import com.openclassrooms.safetynetalerts.repository.DBRepository;
import com.openclassrooms.safetynetalerts.services.IFireStationsService;

/**
 * La classe FireStationsServiceImpl est l'implémentation de l'interface
 * IFireStationsService.
 * 
 * @see IFireStationsService
 * @author Dylan
 *
 */
@Service
public class FireStationsServiceImpl implements IFireStationsService {

    @Autowired
    DBRepository repository;

    private static Logger logger = LogManager.getLogger(FireStationsServiceImpl.class);
    private int numberOfStationsFound;
    private int numberOfMajorPerson;
    private int numberOfMinorPerson;

    @Override
    public List<FireStationsModel> findAll() throws IOException {
	List<FireStationsModel> listStations = repository.getFireStations();
	ArrayList<FireStationsModel> result = new ArrayList<>();
	numberOfStationsFound = 0;
	logger.debug("Search the list of registered stations");
	listStations.forEach(stationRegister -> {
	    if (!result.contains(stationRegister)) {
		result.add(stationRegister);
		numberOfStationsFound++;
	    }
	});
	logger.info(numberOfStationsFound + " stations found");
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

	logger.debug("Search for people covered by the station number " + key);

	/**
	 * On utilise une boucle forEach pour vérifier si le numéro entrée en paramètre
	 * correspond bien à une caserne enregistrée, si oui nous utilisons une autre
	 * boucle pour rechercher les personnes qui ont la même adresse que celles
	 * couvertes par la caserne, ensuite nous utilisons une dernière boucle dans les
	 * dossiers médicaux afin de récupérer la date de naissance des personnes pour
	 * calculer leurs âges et ainsi trier les personnes selon si ils sont mineurs ou
	 * majeurs pour afficher un décompte. Enfin on retourne les différentes listes,
	 * personsMinor et personsMajor avec les décomptes dans une hashmap.
	 */
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
				    logger.error("Error when parsing birthdate", e);
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
	    logger.info(numberOfMinorPerson + " person(s) minor and " + numberOfMajorPerson
		    + " person(s) major found in the coverage area of station number " + key);
	}
	return result;

    }

    @Override
    public boolean saveNewFireStation(FireStationsModel fireStation) throws IOException {

	List<FireStationsModel> list = repository.getFireStations();
	boolean result = false;

	/**
	 * Si les infos de la station sont valides nous vérifions si elle n'est pas déjà
	 * présente dans la liste, auquel cas nous l'ajoutons et indiquons true au
	 * boolean qui sera renvoyer, sinon nous laissons le boolean sur false pour
	 * indiquer que l'ajout n'est pas validé.
	 */
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

    @Override
    public boolean updateFireStation(PutFireStationsModel fireStation) throws IOException {
	List<FireStationsModel> list = repository.getFireStations();
	numberOfStationsFound = 0;
	boolean result = false;

	/**
	 * Nous vérifions que les infos de la station sont corrects, ensuite nous
	 * utilisons une boucle forEach afin de trouver la station en question avec
	 * l'adresse et son numéro à modifier, si nous trouvons un résultat nous
	 * modifions son numéro par le nouveau et indiquons true au boolean afin de
	 * confirmer la mis à jour, sinon le boolean reste sur false pour indiquer que
	 * la mis à jour n'est pas validée.
	 */
	if (fireStation.getAddress() != null && fireStation.getOldStationNumber() > 0
		&& fireStation.getNewStationNumber() > 0) {

	    logger.debug("Updating the station");

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
		logger.info("Update validated the address " + fireStation.getAddress()
			+ " is now covered by the station number " + fireStation.getNewStationNumber()
			+ " instead of the station number " + fireStation.getOldStationNumber());
		result = true;
	    } else {
		logger.error("Error encountered while updating the station");
	    }
	} else {
	    logger.error("Invalid informations, check that you have entered all the information correctly");
	}
	return result;
    }

    @Override
    public boolean deleteFireStation(Optional<Integer> station, Optional<String> address) throws IOException {
	List<FireStationsModel> list = repository.getFireStations();
	List<FireStationsModel> deleteList = new ArrayList<>();
	numberOfStationsFound = 0;
	boolean result = false;

	// Nous vérifions qu'au moins un paramètre est présent et qu'il est valide.
	if (station.isPresent() && station.get() > 0 || address.isPresent() && address.get() != null) {

	    /**
	     * Si les deux paramètres sont présents, nous utilisons une boucle forEach afin
	     * de retrouver la station qui correspond aux informations indiquées pour la
	     * supprimer, nous vérifions ensuite qu'elle n'est plus présente dans la liste,
	     * si tel est le cas nous indiquons true au boolean pour confirmer la
	     * suppression, sinon nous laissons false afin d'indiquer que la suppression ne
	     * s'est pas executée.
	     */
	    if (station.isPresent() && address.isPresent()) {
		logger.debug("Deleting the station with the address " + address.get() + " the station number "
			+ station.get());
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
	    }

	    /**
	     * Si seul le numéro de caserne est présent, nous utilisons une boucle forEach
	     * afin de retrouver les adresses couvertes par celle-ci, à chaque résultat nous
	     * l'ajoutons à la liste deleteList, nous supprimons ensuite toute les adresses
	     * de celle-ci contenu dans la liste de données, nous vérifions que la caserne à
	     * bien été supprimée afin d'indiquer true au boolean pour valider la
	     * suppression, sinon nous laissons false afin d'indiquer que la suppression ne
	     * s'est pas executée.
	     */
	    else if (station.isPresent() && !address.isPresent()) {
		logger.debug("Deleting the station with number " + station.get());
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

	    }

	    /**
	     * Si seul l'adresse est présente nous utilisons une boucle forEach afin de
	     * trouver toutes les casernes couvrant cette adresse, à chaque résultat nous
	     * l'ajoutons à la liste deleList, nous supprimons ensuite tous les mapping des
	     * casernes de cette liste contenu dans la liste de données, nous vérifions que
	     * l'adresse n'est plus présente dans la liste de données pour indiquer true au
	     * boolean afin de valider la suppression, sinon nous laissons false au boolean
	     * pour indiquer que la suppression ne s'est pas executée.
	     */
	    else if (address.isPresent() && !station.isPresent()) {
		logger.debug("Deleting the address " + address.get() + " of addresses covered by the stations");
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
