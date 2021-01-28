package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.HashMap;

public interface ICommunityEmailService {
    
    public HashMap<String, Object> findEmail(String city) throws IOException;

}
