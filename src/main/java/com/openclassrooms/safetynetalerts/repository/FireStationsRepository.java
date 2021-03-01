package com.openclassrooms.safetynetalerts.repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.DB;
import com.openclassrooms.safetynetalerts.models.FireStationsModel;

/**
 * La classe FireStationsRepository nous permet de récupérer les données des
 * stations du fichier JSON.
 * 
 * @author Dylan
 *
 */
@Repository
public class FireStationsRepository {

    private static Logger logger = LogManager.getLogger(FireStationsRepository.class);

    /**
     * On se connecte à l'url de données, on utilise ObjectMapper avec notre classe
     * DB afin de choisir de récupérer simplement les données des stations dans une
     * liste.
     * 
     * @return la liste des stations présentes dans le fichier JSON.
     * @throws IOException si une erreur est rencontrée lors de la lecture du flux
     *                     d'entrée de données.
     */
    public List<FireStationsModel> findAll() throws IOException {
	DB result = null;
	try {
	    URL url = new URL(
		    "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.connect();
	    InputStream inputStream = connection.getInputStream();

	    ObjectMapper mapper = new ObjectMapper();
	    result = mapper.readValue(inputStream, DB.class);

	} catch (IOException e) {
	    logger.error("Error when reading json file", e);
	}
	return result.getFirestations();
    }

}
