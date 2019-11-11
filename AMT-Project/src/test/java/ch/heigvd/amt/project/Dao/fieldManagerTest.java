package ch.heigvd.amt.project.Dao;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.model.Field;
import ch.heigvd.amt.project.services.FieldManagerLocal;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class fieldManagerTest {

  @EJB
  FieldManagerLocal fieldManager;


  @Test
  @Transactional(TransactionMode.ROLLBACK)
  public void itShouldBePossibleToCreateField()throws  SQLException  {

    Field field= Field.builder()
            .size(35)
            .build();

    fieldManager.create(field);
  }


  @Test
  @Transactional(TransactionMode.ROLLBACK)
  public void itShouldBePossibleToCreateAndRetrieveAFieldViaTheFarmerDAO() throws  KeyNotFoundException, SQLException {
    Field field= Field.builder()
            .size(35)
            .build();

//    Field fieldCreated=fieldManager.create(field);
//    assertEquals(fieldCreated.getIdField(),0);
//    fieldManager.findById(String.valueOf(fieldCreated.getIdField()));
    //Field fieldLoaded=fieldManager.findById(String.valueOf(fieldCreated.getIdField()));
    //assertEquals(fieldCreated.getIdField(),fieldLoaded.getIdField());

//    Farmer farmerLoaded=Farmer.builder().username(farmerLoadedWithId.getUsername())
//            .firstName(farmerLoadedWithId.getFirstName())
//            .lastName(farmerLoadedWithId.getLastName())
//            .address(farmerLoadedWithId.getAddress())
//            .email(farmerLoadedWithId.getEmail())
//            .build();

//    assertEquals(field, fieldCreated);
//    assertEquals(field, fieldLoaded);
//    assertSame(field, fieldCreated);
//    assertNotSame(field, fieldLoaded);


  }
//
//  @Test
//  @Transactional(TransactionMode.ROLLBACK)
//  public void itShouldBePossibleToDeleteAFarmer() throws DuplicateKeyException, KeyNotFoundException, SQLException {
//    Farmer farmer= Farmer.builder().username("AndyMister")
//            .firstName("Andy")
//            .lastName("Moreno")
//            .address("Javaland")
//            .email("toto@mimi.com")
//            .build();
//
//    Farmer farmerCreated=farmersManager.create(farmer);
//    Farmer farmerLoadedWithId=farmersManager.findByUser(farmerCreated.getUsername());
//    assertEquals(farmer, farmerCreated);
//    farmersManager.deleteById(String.valueOf(farmerLoadedWithId.getIdFarmer()));
//
//    boolean hasThrown = false;
//    try {
//      farmerLoadedWithId = farmersManager.findByUser(farmerCreated.getUsername());
//    } catch (KeyNotFoundException e) {
//      hasThrown = true;
//    }
//    assertTrue(hasThrown);
//  }
//
//  @Test
//  @Transactional(TransactionMode.ROLLBACK)
//  public void itShouldBePossibleToUpdateAFarmer() throws DuplicateKeyException, KeyNotFoundException, SQLException {
//    Farmer farmer= Farmer.builder().username("AndyMister")
//            .firstName("Andy")
//            .lastName("Moreno")
//            .address("Javaland")
//            .email("toto@mimi.com")
//            .build();
//
//    farmersManager.create(farmer);
//    Farmer farmerCreated=farmersManager.findByUser(farmer.getUsername());
//    Farmer farmerModified=farmerCreated.toBuilder().firstName("Jack").lastName("Londra").build();;
//
//   farmersManager.update(farmerModified);
//
//    Farmer farmerModifiedInDB = farmersManager.findByUser(farmer.getUsername());
//
//    assertEquals(farmerModified, farmerModifiedInDB);
//    assertNotEquals(farmerCreated, farmerModifiedInDB);
//
//  }
//
//
}