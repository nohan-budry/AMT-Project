package ch.heigvd.amt.notes.business;

import ch.heigvd.amt.project.authentification.IAuthenticationService;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
public class AuthenticationServiceTest {

  @EJB
  IAuthenticationService authenticationService;

  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

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