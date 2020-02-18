package test;

import et.edu.mu.cfdh.model.ConditionEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class JpasTest {
	private static EntityManager em;

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("MYSQLDBLocal");
		em = emf.createEntityManager();

		initData();


	}

private static void initData(){
	em.getTransaction().begin();
//	PatientEntity patient = new PatientEntity((long)1, 28, "M");
	ConditionEntity condition = new ConditionEntity();
	condition.setCode("098");
	condition.setOnsetDateTime( new Date());
	condition.setSubject((long) 1);
	em.persist(condition);
	em.getTransaction().commit();
}


}