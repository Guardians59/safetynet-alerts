package com.openclassrooms.safetynetalerts.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PutFireStationsModel;
import com.openclassrooms.safetynetalerts.services.impl.FireStationsServiceImpl;

@RestController
@RequestMapping(value = "firestation")
public class FireStationsController {

    @Autowired
    FireStationsServiceImpl fireStationsServiceImpl;

    @GetMapping(value = "/get")
    public List<FireStationsModel> getFireStationsList() throws IOException {
	return fireStationsServiceImpl.findAll();
    }

    @GetMapping(value = "")
    public HashMap<String, Object> findByStationNumber(@RequestParam(value = "stationNumber") int station)
	    throws IOException, ParseException {
	return fireStationsServiceImpl.findPersonsByStationNumber(station);

    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addFireStation(@RequestBody FireStationsModel fireStation) throws IOException {
	var fireStationAdded = fireStationsServiceImpl.saveNewFireStation(fireStation);

	if (!fireStationAdded) {
	    return ResponseEntity.noContent().build();
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).body("Station number " + fireStation.getStation()
		    + " at the address " + fireStation.getAddress() + " created");

	}
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateFireStationNumber(@RequestBody PutFireStationsModel fireStation) throws IOException {
	var fireStationUpdated = fireStationsServiceImpl.updateFireStation(fireStation);
	if (!fireStationUpdated) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK)
		    .body("The address " + fireStation.getAddress() + " is now covered by the station number "
			    + fireStation.getNewStationNumber() + " instead of the station number "
			    + fireStation.getOldStationNumber());
	}

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteFireStation(
	    @RequestParam(value = "station", required = false) Optional<Integer> station,
	    @RequestParam(value = "address", required = false) Optional<String> address) throws IOException {

	var fireStationDeleted = fireStationsServiceImpl.deleteFireStation(station, address);
	if (!fireStationDeleted) {
	    return ResponseEntity.notFound().build();
	} else {
	    if (station.isPresent() && address.isPresent()) {
		return ResponseEntity.status(HttpStatus.OK).body("The address " + address.get()
			+ " covered by the station number " + station.get() + " has been deleted");
	    } else if (station.isPresent() && !address.isPresent()) {
		return ResponseEntity.status(HttpStatus.OK)
			.body("The station number " + station.get() + " has been deleted");
	    } else {
		return ResponseEntity.status(HttpStatus.OK)
			.body("The address " + address.get() + " has been deleted from covered addresses");
	    }
	}

    }
}
