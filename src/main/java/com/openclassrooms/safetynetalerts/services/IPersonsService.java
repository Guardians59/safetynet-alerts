package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.List;

import com.openclassrooms.safetynetalerts.models.PersonsModel;

/**
 * L'interface IPersonsService est le service permettant de gérer la liste des
 * personnes (récupérer/ajouter/modifier/supprimer).
 * 
 * @author Dylan
 *
 */
public interface IPersonsService {

    /**
     * Permet de récupérer la liste des personnes enregistrées.
     * 
     * @return List la liste des personnes enregistrées.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    public List<PersonsModel> findAll() throws IOException;

    /**
     * La méthode saveNewPerson permet d'ajouter une personne.
     * 
     * @param newPerson les infos de la nouvelle personne.
     * @return boolean true si l'ajout s'est executé avec succès ou false si l'ajout
     *         n'est pas validé.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean saveNewPerson(PersonsModel newPerson) throws IOException;

    /**
     * La méthode updatePerson permet de mettre à jour une personne.
     * 
     * @param person les nouvelles informations de la personne.
     * @return boolean true si la mis à jour s'est executée ou false si la mis à
     *         jour n'est pas validée.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean updatePerson(PersonsModel person) throws IOException;

    /**
     * La méthode deletePerson permet de supprimer une personne avec le prénom et
     * nom indiqués en paramètre.
     * 
     * @param firstName prénom de la personne.
     * @param lastName  nom de la personne.
     * @return boolean true si la suppression est validée, false si la suppression
     *         ne s'est pas executée.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean deletePerson(String firstName, String lastName) throws IOException;

}
