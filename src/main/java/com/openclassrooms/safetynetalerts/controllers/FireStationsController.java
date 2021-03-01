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
import com.openclassrooms.safetynetalerts.services.IFireStationsService;

/**
 * La classe FireStationsController permet de créer l'url firestation, plusieurs
 * fonctionnalités sont disponible sur celle-ci, récupérer la liste des
 * stations, recherche les personnes couvertes par un numéro de station,
 * ajouter/modifier/supprimer une station.
 * 
 * @author Dylan
 *
 */
@RestController
@RequestMapping(value = "firestation")
public class FireStationsController {

    @Autowired
    IFireStationsService fireStationsService;

    /**
     * Reçoit la liste des stations enregistrées.
     * 
     * @return une liste des stations enregistrées.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @GetMapping(value = "/get")
    public List<FireStationsModel> getFireStationsList() throws IOException {
	return fireStationsService.findAll();
    }

    /**
     * Reçoit la liste des personnes avec leurs informations(prénom, nom, adresse,
     * numéro de téléphone couvertes par le numéro de station indiquée en paramètre,
     * ainsi qu'un décompte du nombre d'enfants et d'adultes.
     * 
     * @param station le numéro de station que l'on souhaite vérifier.
     * @return hashmap avec la liste des personnes et le décompte des enfants et des
     *         adultes.
     * @throws IOException    si erreur rencontrée lors de la lecture des données.
     * @throws ParseException si erreur rencontrée lors de l'analyse des dates de
     *                        naissances.
     */
    @GetMapping(value = "")
    public HashMap<String, Object> findByStationNumber(@RequestParam(value = "stationNumber") int station)
	    throws IOException, ParseException {
	return fireStationsService.findPersonsByStationNumber(station);

    }

    /**
     * Ajout d'une station.
     * 
     * @param fireStation les informations de la station dans le body de la requête.
     * @return un message validant l'ajout avec le status created, ou le status
     *         noContent si l'ajout n'a pas abouti.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> addFireStation(@RequestBody FireStationsModel fireStation) throws IOException {
	var fireStationAdded = fireStationsService.saveNewFireStation(fireStation);

	if (!fireStationAdded) {
	    return ResponseEntity.noContent().build();
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).body("Station number " + fireStation.getStation()
		    + " at the address " + fireStation.getAddress() + " created");

	}
    }

    /**
     * Mets à jour une station.
     * 
     * @param fireStation les informations de la station indiquant l'adresse de la
     *                    celle-ci avec son ancien et nouveau numéro.
     * @return un message indiquant la validation de la mis à jour de la station
     *         avec le status ok, ou le statut notFound si la mis à jour n'a pas
     *         aboutie.
     * @throws IOException si une erreur est rencontrée lors de la lecture de
     *                     donnée.
     */
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateFireStationNumber(@RequestBody PutFireStationsModel fireStation) throws IOException {
	var fireStationUpdated = fireStationsService.updateFireStation(fireStation);
	if (!fireStationUpdated) {
	    return ResponseEntity.notFound().build();
	} else {
	    return ResponseEntity.status(HttpStatus.OK)
		    .body("The address " + fireStation.getAddress() + " is now covered by the station number "
			    + fireStation.getNewStationNumber() + " instead of the station number "
			    + fireStation.getOldStationNumber());
	}

    }

    /**
     * Supprime une station selon les paramètres indiqués, soit une station ciblée
     * avec l'adresse et le numéro, soit un numéro de station qui supprime son
     * mapping, soit une simple adresse qui est donc supprimée du mapping des
     * stations.
     * 
     * @param station le numéro de station.
     * @param address l'adresse de la station.
     * @return un message validant la suppression de la station avec le status ok,
     *         ou le status not found si la suppression n'a pas aboutie.
     * @throws IOException si une erreur est rencontrée lors de lecture des données.
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteFireStation(
	    @RequestParam(value = "station", required = false) Optional<Integer> station,
	    @RequestParam(value = "address", required = false) Optional<String> address) throws IOException {

	var fireStationDeleted = fireStationsService.deleteFireStation(station, address);
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
