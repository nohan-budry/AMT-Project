package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.model.Field;

import javax.ejb.Local;

@Local
public interface FieldManagerLocal extends IDAO<String, Field> {

}
