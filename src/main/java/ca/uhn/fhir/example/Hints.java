package ca.uhn.fhir.example;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hints implements IResourceProvider {
	private Map<String, Patient> myPatients = new HashMap<String, Patient>();
	private int myNextId = 2;

	/** Constructor */
	public Hints() {
		Patient pat1 = new Patient();
		pat1.setId("1");
		pat1.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7000135");
		pat1.addName().setFamily("Simpson2222220987654321Simpson2222220987654321").addGiven("HomerHomerHomerHomer").addGiven("JHomer");
		myPatients.put("1", pat1);
	}

	/** Simple implementation of the "read" method */
	@Read(version = false)
	public Patient read(@IdParam IdType theId) {
		Patient retVal = myPatients.get(theId.getIdPart());
		if (retVal == null) {
			throw new ResourceNotFoundException(theId);
		}
		return retVal;
	}

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Patient.class;
	}

	@Create
	public MethodOutcome create(@ResourceParam Patient thePatient) {
		// Give the resource the next sequential ID
		int id = myNextId++;
		thePatient.setId(new IdType(id));

		// Store the resource in memory
		myPatients.put(Integer.toString(id), thePatient);

		// Inform the server of the ID for the newly stored resource
		return new MethodOutcome().setId(thePatient.getIdElement());
	}

	@Search
	public List<Patient> search() {
		List<Patient> retVal = new ArrayList<Patient>();
		retVal.addAll(myPatients.values());
		return retVal;
	}

	@Search
	public List<Patient> search(@RequiredParam(name = Patient.SP_FAMILY) StringParam theParam) {
		List<Patient> retVal = new ArrayList<Patient>();

		// Loop through the patients looking for matches
		for (Patient next : myPatients.values()) {
			String familyName = next.getNameFirstRep().getFamily().toLowerCase();
			if (familyName.contains(theParam.getValue().toLowerCase()) == false) {
				continue;
			}
			retVal.add(next);
		}

		return retVal;
	}

}
