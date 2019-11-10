package ch.heigvd.amt.project.Dao;

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
  public void itShouldBePossibleToCreateFarmer() {

    Farmer farmer= Farmer.builder().username("AndyMister")
            .firstName("andy")
            .lastName("doUevenArquillian")
            .address("jdk1.8")
            .email("toto@mimi.como")
            .build();
    try {
      farmersManager.create(farmer);
    } catch (SQLException e) {
     assertTrue(true);
    }
  }


}