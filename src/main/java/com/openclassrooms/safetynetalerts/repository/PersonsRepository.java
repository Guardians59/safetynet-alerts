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
import com.openclassrooms.safetynetalerts.models.DBModel;
import com.openclassrooms.safetynetalerts.models.PersonsModel;

/**
 * La classe PersonsRepository nous permet de récupérer les données des
 * personnes du fichier JSON.
 * 
 * @author Dylan
 *
 */
@Repository
public class PersonsRepository {

    private static Logger logger = LogManager.getLogger(PersonsRepository.class);

    /**
     * On se connecte à l'url de données, on utilise ObjectMapper avec notre classe
     * DB afin de choisir de récupérer simplement les données des personnes dans une
     * liste.
     * 
     * @return la liste des personnes présentes dans le fichier JSON.
     * @throws IOException si une erreur est rencontrée lors de la lecture du flux
     *                     d'entrée de données.
     */
    public List<PersonsModel> findAll() throws IOException {
	DBModel result = null;
	try {
	    URL url = new URL(
		    "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.connect();
	    InputStream inputStream = connection.getInputStream();

	    ObjectMapper mapper = new ObjectMapper();
	    result = mapper.readValue(inputStream, DBModel.class);

	} catch (IOException e) {
	    logger.error("Error when reading json file", e);
	}
	return result.getPersons();
    }
}
