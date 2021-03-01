package com.openclassrooms.safetynetalerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.safetynetalerts.models.FireStationsModel;
import com.openclassrooms.safetynetalerts.models.PutFireStationsModel;

/**
 * L'interface IFireStationsService est le service qui permet de gérer la liste
 * des stations (récupérer/ajouter/modifier/supprimer) mais également de
 * retourner une liste des personnes couvertes selon le numéro de caserne entrée
 * en paramètre.
 * 
 * @author Dylan
 *
 */
public interface IFireStationsService {

    /**
     * Permet de récupérer la liste des stations enregistrées.
     * 
     * @return List la liste des casernes enregistrées.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    public List<FireStationsModel> findAll() throws IOException;

    /**
     * La méthode findPersonsByStationNumber permet de récupérer la liste des
     * personnes avec leurs informations(prénom, nom, adresse et numéro de
     * téléphone) couvertes par la caserne indiquée en paramètre, ainsi qu'un
     * décompte du nombre d'enfants et d'adultes.
     * 
     * @param station le numéro de la station que l'on souhaite vérifier.
     * @return hashmap avec une liste des personnes mineurs, une liste des personnes
     *         majeurs ainsi que le décompte du nombre d'enfants et d'adultes.
     * @throws IOException    si une erreur est rencontrée lors de la lecture des
     *                        données.
     * @throws ParseException si une erreur est rencontrée lors de l'analyse des
     *                        dates de naissance.
     */
    public HashMap<String, Object> findPersonsByStationNumber(int station) throws IOException, ParseException;

    /**
     * La méthode saveNewFireStation permet d'enregistrer une nouvelle station.
     * 
     * @param fireStation les infos de la nouvelle station à ajouter.
     * @return boolean true si l'ajout s'est executé avec succès ou false si l'ajout
     *         n'est pas validé.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean saveNewFireStation(FireStationsModel fireStation) throws IOException;

    /**
     * La méthode updateFireStation permet de mettre à jour une station.
     * 
     * @param fireStation les nouvelles infos de la station avec notamment l'ancien
     *                    numéro de la caserne à remplacer par le nouveau.
     * @return boolean true si la mis à jour s'est executée ou false si la mis à
     *         jour n'est pas validée.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean updateFireStation(PutFireStationsModel fireStation) throws IOException;

    /**
     * La méthode deleteFireStation permet de supprimer une station avec plusieurs
     * options, soit l'adresse et le numéro pour supprimer une station bien ciblée,
     * soit simplement par un numéro pour supprimer le mapping complet de cette
     * caserne, soit une adresse pour supprimée celle-ci du mapping des stations.
     * 
     * @param station le numéro de la caserne à supprimer.
     * @param address l'adresse à supprimer.
     * @return boolean true si la suppression est validée, false si la suppression
     *         n'est pas executée.
     * @throws IOException si une erreur est rencontrée lors de la lecture des
     *                     données.
     */
    boolean deleteFireStation(Optional<Integer> station, Optional<String> address) throws IOException;

}
