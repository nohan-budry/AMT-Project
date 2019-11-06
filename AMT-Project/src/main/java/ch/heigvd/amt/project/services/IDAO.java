package ch.heigvd.amt.project.services;


import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;

import java.util.List;

public interface IDAO<PK, E> {
  List<E> findAll();
  E create(E entity) throws DuplicateKeyException;
  E findByUser(PK id) throws KeyNotFoundException;
  E findById(PK id) throws KeyNotFoundException;
  void update(E entity) throws KeyNotFoundException;
  void deleteById(PK id) throws KeyNotFoundException;
}
