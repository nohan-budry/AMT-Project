package ch.heigvd.amt.project.authentication;

import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class AuthenticationServiceTest {

  @EJB
  IAuthenticationService authenticationService;



  @Test
  public void hashPassword() {
    String password = "myvoiceismypassword";
    String hash = authenticationService.hashPassword(password);
    assertNotNull(hash);
    assertNotEquals("", hash);
    assertNotEquals(password, hash);
  }

  @Test
  public void checkPassword() {
    String password = "myvoiceismypassword";
    String hash = authenticationService.hashPassword(password);
    assertTrue(authenticationService.checkPassword(password, hash));
    assertFalse(authenticationService.checkPassword(password, password));
    assertFalse(authenticationService.checkPassword(hash, hash));
  }
}