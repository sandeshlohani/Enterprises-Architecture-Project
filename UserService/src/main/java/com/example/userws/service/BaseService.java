package com.example.userws.service;

import java.util.List;
import javax.naming.CannotProceedException;

/**
 * Base service to implement repeated CRUD operation
 *
 * @param <T>
 */
public interface BaseService<T> {

  /**
   * Persist the data in the database
   *
   * @param t to save
   */
  void save(T t) throws CannotProceedException;

  /**
   * Delete the specific object by id
   *
   * @param id of object
   */
  void deleteById(long id);

  /**
   * @return list of all objects
   */
  List<T> getAll();

  /**
   * Find the object by id
   *
   * @param id of object
   * @return t
   */
  T findById(long id);
}
