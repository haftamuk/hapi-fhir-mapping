package et.edu.mu.cfdh;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import et.edu.mu.cfdh.model.ConditionEntity;
import et.edu.mu.cfdh.repository.ConditionEntityRepository;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.*;

public class ConditionResourceProvider implements IResourceProvider {

    private static EntityManager em;

    private Map<String, Condition> conditions = new HashMap<String, Condition>();
    List<Condition> list = new ArrayList<Condition>();

    @Autowired
    private ConditionEntityRepository conditionRepository;


    /**
     * Constructor
     */
    public ConditionResourceProvider() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("MYSQLDBLocal");
        em = emf.createEntityManager();

    }

    public Class<? extends IBaseResource> getResourceType() {
        return Condition.class;
    }

    public Condition getConditionEntity(Long id) {
        ConditionEntity conditionEntity = em.find(ConditionEntity.class, id);
        em.detach(conditionEntity);
        return conditionEntity.getCondition();
    }


    public List<Condition> getConditions(Long id) {
        List<ConditionEntity> result;
        list.clear();
        em.getTransaction().begin();
        TypedQuery<ConditionEntity> query = em.createQuery("from ConditionEntity c WHERE  c.subject = ?1", ConditionEntity.class);
        query.setParameter(1, id);

        result = query.getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < result.size(); i++) {
            Condition condition = result.get(i).getCondition();
            list.add(condition);
        }


        return list;
    }

    public List<Condition> getConditionByDates(Date date) {
        List<ConditionEntity> result;
        list.clear();
        em.getTransaction().begin();
        TypedQuery<ConditionEntity> query = em.createQuery("from ConditionEntity c WHERE  c.onsetDateTime >= ?1", ConditionEntity.class);
        query.setParameter(1, date);

        result = query.getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < result.size(); i++) {
            Condition condition = result.get(i).getCondition();
            list.add(condition);
        }
        return list;
    }

    public List<Condition> getAllConditions() {
        List<ConditionEntity> result;
        list.clear();
        em.getTransaction().begin();
        TypedQuery<ConditionEntity> query = em.createQuery("from ConditionEntity c", ConditionEntity.class);
        result = query.getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < result.size(); i++) {
            Condition condition = result.get(i).getCondition();
            list.add(condition);
        }
        return list;
    }

    /**
     * Simple implementation of the "read" method
     */
    @Read()
    public Condition read(@IdParam IdType theId) {
        Condition condition = getConditionEntity(theId.getIdPartAsLong());
        if (condition == null) {
            throw new ResourceNotFoundException(theId);
        }
        return condition;
    }


    @Search()
    public List<Condition> search(
            @OptionalParam(name = Condition.SP_SUBJECT) StringParam Subject,
            @OptionalParam(name = Condition.SP_ONSET_DATE) DateParam onsetDateTime
    ) {
        String subj = Subject != null ? Subject.getValue() : null;
        Date date = onsetDateTime != null ? onsetDateTime.getValue() : null;

        List<Condition> retVal;
        if (subj != null) {
            retVal = getConditions((long) Integer.parseInt(subj));
        } else if (date != null) {
            retVal = getConditionByDates(date);
        } else {
            retVal = getAllConditions();
        }

        return retVal;
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
