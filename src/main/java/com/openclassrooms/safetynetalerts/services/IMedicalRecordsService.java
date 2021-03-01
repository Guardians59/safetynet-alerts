package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.util.List;

import com.openclassrooms.safetynetalerts.models.MedicalRecordsModel;

/**
 * L'interface IMedicalRecordsService est le service permettant de gérer la
 * liste des dossiers médicaux (récupérer/ajouter/modifier/supprimer).
 * 
 * @author Dylan
 *
 */
public interface IMedicalRecordsService {

    /**
     * Permet de récupérer la liste des dossiers médicaux enregistrés.
     * 
     * @return List la liste des dossiers médicaux enregistrés.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    public List<MedicalRecordsModel> findAll() throws IOException;

    /**
     * La méthode saveNewMedicalRecords permet d'ajouter un dossier médical.
     * 
     * @param newMedicalRecord les infos du nouveau dossier médical.
     * @return boolean true si l'ajout s'est executé avec succès ou false si l'ajout
     *         n'est pas validé.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean saveNewMedicalRecords(MedicalRecordsModel newMedicalRecord) throws IOException;

    /**
     * La méthode updateMedicalRecords permet de mettre à jour un dossier médical.
     * 
     * @param medicalRecord les nouvelles informations du dossier médical.
     * @return boolean true si la mis à jour s'est executée ou false si la mis à
     *         jour n'est pas validée.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean updateMedicalRecords(MedicalRecordsModel medicalRecord) throws IOException;

    /**
     * La méthode deleteMedicalRecords permet de supprimer un dossier médical avec
     * le prénom et nom indiqués en paramètre.
     * 
     * @param firstName prénom de la personne.
     * @param lastName  nom de la personne.
     * @return boolean true si la suppression est validée, false si la suppression
     *         ne s'est pas executée.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean deleteMedicalRecords(String firstName, String lastName) throws IOException;
    
}
