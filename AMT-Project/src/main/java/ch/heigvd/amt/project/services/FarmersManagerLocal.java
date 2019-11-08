package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Farmer;

import javax.ejb.Local;

@Local
public interface FarmersManagerLocal extends IDAO<String, Farmer> {
    Farmer findByUser(String username) throws KeyNotFoundException;
    Farmer login(String username, String password);
}
