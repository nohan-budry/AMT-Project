package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.model.Field;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface FieldManagerLocal extends IDAO<Integer, Field> {}
