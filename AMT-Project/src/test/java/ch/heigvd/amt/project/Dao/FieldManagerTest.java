package ch.heigvd.amt.project.Dao;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
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
public class FieldManagerTest {

    @EJB
    FieldManagerLocal fieldManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAField() throws SQLException, KeyNotFoundException {
        Field fieldCreated = fieldManager.create(Field.builder().size(42).build());
        Field fieldLoaded = fieldManager.findById(fieldCreated.getIdField());

        assertEquals(fieldCreated.getIdField(), fieldLoaded.getIdField());
        assertEquals(fieldCreated.getSize(), fieldLoaded.getSize());
    }

    @Test(expected = KeyNotFoundException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAField() throws KeyNotFoundException, SQLException {

        Field field = fieldManager.create(Field.builder().size(42).build());
        fieldManager.deleteById(field.getIdField());

        // Should throw expected exception (means deletion worked)
        fieldManager.findById(field.getIdField());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAField() throws DuplicateKeyException, KeyNotFoundException, SQLException {

        Field field = fieldManager.create(Field.builder().size(42).build());

        fieldManager.update(field.toBuilder().size(17).build());
        Field fieldModified = fieldManager.findById(field.getIdField());

        assertEquals(field.getIdField(), fieldModified.getIdField());
        assertNotEquals(field.getSize(), fieldModified.getSize());
    }
}