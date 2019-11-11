package ch.heigvd.amt.project.Dao;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.services.FarmersManagerLocal;
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
public class farmerManagerTest {

  @EJB
  FarmersManagerLocal farmersManager;


  @Test
  @Transactional(TransactionMode.ROLLBACK)
  public void itShouldBePossibleToCreateFarmer()throws DuplicateKeyException, SQLException  {

    Farmer farmer= Farmer.builder().username("AndyMister")
            .firstName("andy")
            .lastName("doUevenArquillian")
            .address("javaland")
            .email("toto@mimi.como")
            .build();

  farmersManager.create(farmer);

  }


  @Test
  @Transactional(TransactionMode.ROLLBACK)
  public void itShouldBePossibleToCreateAndRetrieveAFarmerViaTheFarmerDAO() throws DuplicateKeyException, KeyNotFoundException, SQLException {
    Farmer farmer= Farmer.builder().username("AndyMister")
            .firstName("andy")
            .lastName("doUevenArquillian")
            .address("javaland")
            .email("toto@mimi.como")
            .build();

    Farmer farmerCreated=farmersManager.create(farmer);
    Farmer farmerLoadedWithId=farmersManager.findByUser(farmerCreated.getUsername());
    Farmer farmerLoaded=Farmer.builder().username(farmerLoadedWithId.getUsername())
            .firstName(farmerLoadedWithId.getFirstName())
            .lastName(farmerLoadedWithId.getLastName())
            .address(farmerLoadedWithId.getAddress())
            .email(farmerLoadedWithId.getEmail())
            .build();

    assertEquals(farmer, farmerCreated);
    assertEquals(farmer, farmerLoaded);
    assertSame(farmer, farmerCreated);
    assertNotSame(farmer, farmerLoaded);


  }

  @Test
  @Transactional(TransactionMode.ROLLBACK)
  public void itShouldBePossibleToDeleteAFarmer() throws DuplicateKeyException, KeyNotFoundException, SQLException {
    Farmer farmer= Farmer.builder().username("AndyMister")
            .firstName("andy")
            .lastName("doUevenArquillian")
            .address("javaland")
            .email("toto@mimi.como")
            .build();

    Farmer farmerCreated=farmersManager.create(farmer);
    Farmer farmerLoadedWithId=farmersManager.findByUser(farmerCreated.getUsername());
    assertEquals(farmer, farmerCreated);
    farmersManager.deleteById(String.valueOf(farmerLoadedWithId.getIdFarmer()));

    boolean hasThrown = false;
    try {
      farmerLoadedWithId = farmersManager.findByUser(farmerCreated.getUsername());
    } catch (KeyNotFoundException e) {
      hasThrown = true;
    }
    assertTrue(hasThrown);
  }

  @Test
  @Transactional(TransactionMode.ROLLBACK)
  public void itShouldBePossibleToUpdateAFarmer() throws DuplicateKeyException, KeyNotFoundException, SQLException {
    Farmer farmer= Farmer.builder().username("AndyMister")
            .firstName("andy")
            .lastName("doUevenArquillian")
            .address("javaland")
            .email("toto@mimi.como")
            .build();

    farmersManager.create(farmer);
    Farmer farmerCreated=farmersManager.findByUser(farmer.getUsername());
    Farmer farmerModified=Farmer.builder()
            .idFarmer(farmerCreated.getIdFarmer())
            .username(farmerCreated.getUsername())
            .firstName("Jack")
            .lastName("Erian")
            .address(farmerCreated.getAddress())
            .email(farmerCreated.getEmail())
            .build();

   farmersManager.update(farmerModified);

    Farmer farmerModifiedInDB = farmersManager.findByUser(farmer.getUsername());

    assertEquals(farmerModified, farmerModifiedInDB);
    assertNotEquals(farmerCreated, farmerModifiedInDB);

  }


}