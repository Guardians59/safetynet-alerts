package com.openclassrooms.safetynetalerts.repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.DB;
import com.openclassrooms.safetynetalerts.models.PersonsModel;

@Repository
public class PersonsRepository {

    public List<PersonsModel> findAll() throws IOException {
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
	    e.printStackTrace();
	}
	return result.getPersons();
    }

}
