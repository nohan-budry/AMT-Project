package ch.heigvd.amt.project.datastore.exceptions;

public class DuplicateKeyException extends Exception {
  public DuplicateKeyException(String message) {
    super(message);
  }
}
