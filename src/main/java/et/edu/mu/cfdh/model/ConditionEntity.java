package et.edu.mu.cfdh.model;

import org.hl7.fhir.r4.model.*;

import javax.persistence.*;
import java.util.Date;

/*
{doco
  "resourceType" : "Condition",
  // from Resource: id, meta, implicitRules, and language
  // from DomainResource: text, contained, extension, and modifierExtension
  "identifier" : [{ Identifier }], // External Ids for this condition
  "clinicalStatus" : { CodeableConcept }, // C? active | recurrence | relapse | inactive | remission | resolved
  "verificationStatus" : { CodeableConcept }, // C? unconfirmed | provisional | differential | confirmed | refuted | entered-in-error
  "category" : [{ CodeableConcept }], // problem-list-item | encounter-diagnosis
  "severity" : { CodeableConcept }, // Subjective severity of condition
  "code" : { CodeableConcept }, // Identification of the condition, problem or diagnosis
  "bodySite" : [{ CodeableConcept }], // Anatomical location, if relevant
  "subject" : { Reference(Patient|Group) }, // R!  Who has the condition?
  "encounter" : { Reference(Encounter) }, // Encounter created as part of
  // onset[x]: Estimated or actual date,  date-time, or age. One of these 5:
  "onsetDateTime" : "<dateTime>",
  "onsetAge" : { Age },
  "onsetPeriod" : { Period },
  "onsetRange" : { Range },
  "onsetString" : "<string>",
  // abatement[x]: When in resolution/remission. One of these 5:
  "abatementDateTime" : "<dateTime>",
  "abatementAge" : { Age },
  "abatementPeriod" : { Period },
  "abatementRange" : { Range },
  "abatementString" : "<string>",
  "recordedDate" : "<dateTime>", // Date record was first recorded
  "recorder" : { Reference(Practitioner|PractitionerRole|Patient|
   RelatedPerson) }, // Who recorded the condition
  "asserter" : { Reference(Practitioner|PractitionerRole|Patient|
   RelatedPerson) }, // Person who asserts this condition
  "stage" : [{ // Stage/grade, usually assessed formally
    "summary" : { CodeableConcept }, // C? Simple summary (disease specific)
    "assessment" : [{ Reference(ClinicalImpression|DiagnosticReport|Observation) }], // C? Formal record of assessment
    "type" : { CodeableConcept } // Kind of staging
  }],
  "evidence" : [{ // Supporting evidence
    "code" : [{ CodeableConcept }], // C? Manifestation/symptom
    "detail" : [{ Reference(Any) }] // C? Supporting information found elsewhere
  }],
  "note" : [{ Annotation }] // Additional information about the Condition
}
 */

@Entity
@Table(name = "CFDH_condition")
public class ConditionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "PatientID")
    private long subject; // Corresponds to patient reference with age and sex

    @Column(name = "VisitDate")
    private Date onsetDateTime; // Corresponds to visit date

    @Column(name = "PrimaryDiagnosis")
    private String code; // this is supposed to be a cod-able Concept


    public ConditionEntity(Long id, long subject, Date onsetDateTime, String code) {
        this.id = id;
        this.subject = subject;
        this.onsetDateTime = onsetDateTime;
        this.code = code;
    }

    public ConditionEntity() {

    }

    public Condition getCondition() {
        Condition condition = new Condition();
        condition.setId(String.valueOf(id));
        condition.addIdentifier().setSystem("http://mu.edu.et/SmartCare").setValue(id + "");
        condition.setOnset(new DateTimeType(onsetDateTime));

        Coding c = new Coding().setCode(code);
        c.setSystem("http://fhir.de/CodeSystem/dimdi/icd-10-gm");
        c.setVersion("2017");
        c.setCode(code);

        CodeableConcept cc = new CodeableConcept();
        cc.addCoding(c);

        condition.setCode(cc);
        Patient patient = new Patient();
        patient.setId(subject + "");
        condition.setSubject(new Reference(patient));
        return condition;
    }

    @Override
    public String toString() {
        System.out.println("{" +
                "resourceType='Condition'" +
                ", identifier=" + id +
                ", subject='" + subject + '\'' +
                ", onsetDateTime='" + new DateTimeType(onsetDateTime).getValueAsString() + '\'' +
                ", code='" + code + '\'' +
                '}');

        return "{" +
                "resourceType='Condition'" +
                ", identifier=" + id +
                ", subject='" + subject + '\'' +
                ", onsetDateTime='" + new DateTimeType(onsetDateTime).getValueAsString() + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSubject() {
        return subject;
    }

    public void setSubject(long subject) {
        this.subject = subject;
    }

    public Date getOnsetDateTime() {
        return onsetDateTime;
    }

    public void setOnsetDateTime(Date onsetDateTime) {
        this.onsetDateTime = onsetDateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
