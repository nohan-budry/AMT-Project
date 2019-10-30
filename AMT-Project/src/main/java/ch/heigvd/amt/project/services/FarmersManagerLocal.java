package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.model.Farmer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface FarmersManagerLocal {

    public List<Farmer> findAllFarmers();

}
