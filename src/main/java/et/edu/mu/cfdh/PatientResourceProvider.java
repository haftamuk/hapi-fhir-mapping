package et.edu.mu.cfdh;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import et.edu.mu.cfdh.model.PatientEntity;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class PatientResourceProvider implements IResourceProvider {

    private static EntityManager em;

    private Map<String, Patient> patients = new HashMap<String, Patient>();

    /**
     * Constructor
     */
    public PatientResourceProvider() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("MYSQLDBLocal");
        em = emf.createEntityManager();
    }

    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }



    public Patient getPatientEntity(Long id) {
        PatientEntity patientEntity = em.find(PatientEntity.class, id);
        em.detach(patientEntity);
        return patientEntity.getPatient();
    }

    /**
     * Simple implementation of the "read" method
     */
    @Read()
    public Patient read(@IdParam IdType theId) {
        Patient patient = getPatientEntity(theId.getIdPartAsLong());
        if (patient == null) {
            throw new ResourceNotFoundException(theId);
        }
        return patient;
    }

    /*
     * TASKS:
     *
     * Start this project using the following command from the "hapi-fhirstarters-simple-server" directory:
     *    mvn jetty:run
     *
     * Try invoking the read operation:
     *    http://localhost:8080/Patient/1
     *
     * Examine the Server's CapabilityStatement:
     *    http://localhost:8080/metadata
     *
     * Task: Add a @Create method
     *    Hint: http://hapifhir.io/doc_rest_operations.html#Type_Level_-_Create
     *    Bigger Hint: Look at Hints.java
     */


}
