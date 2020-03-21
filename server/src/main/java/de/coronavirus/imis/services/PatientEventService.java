package de.coronavirus.imis.services;

import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.repositories.DoctorRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PatientEventService {

    private final PatientEventRepository patientEventRepository;
    private final PatientService patientRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final DoctorRepository doctorRepository;

    public PatientEventService(PatientEventRepository patientEventRepository, PatientService patientRepository,
                               LaboratoryRepository laboratoryRepository, DoctorRepository doctorRepository) {
        this.patientEventRepository = patientEventRepository;
        this.patientRepository = patientRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.doctorRepository = doctorRepository;
    }

    public void createInitialPatientEvent(Patient patient, Optional<Illness> illness,
                                          EventType eventType) {
        var concreteIllness = illness.orElse(Illness.CORONA);
        PatientEvent event = new PatientEvent()
                .setEventTimestamp(Timestamp.from(Instant.now()))
                .setEventType(eventType)
                .setIllness(concreteIllness)
                .setPatient(patient);
        patientEventRepository.save(event);
    }

    public void createLabTestEvent(Patient patient, LabTest labTest, Optional<Illness> illness) {
        var concreteIllness = illness.orElse(Illness.CORONA);
        PatientEvent event = new PatientEvent()
                .setEventTimestamp(Timestamp.from(Instant.now()))
                .setEventType(EventType.TEST_SUBMITTED_IN_PROGRESS)
                .setLabTest(labTest)
                .setIllness(concreteIllness)
                .setPatient(patient);
        patientEventRepository.saveAndFlush(event);
    }

    @Transactional
    public PatientEvent createScheduledEvent(String patientId, String labId, String doctorId) {
        final Patient patient = patientRepository.findPatientById(patientId).orElseThrow(PatientNotFoundException::new);
        final Laboratory laboratory = laboratoryRepository.findById(Long.valueOf(labId)).orElseGet(() -> {
            Laboratory lab = new Laboratory();
            lab.setId(Long.valueOf(labId));
            return laboratoryRepository.save(lab);
        });


        final Doctor doctor = doctorRepository.findById(doctorId).orElseGet(() ->
                doctorRepository.save(Doctor.builder().id(doctorId).build())
        );
        var event = new PatientEvent()
                .setEventTimestamp(Timestamp.from(Instant.now()))
                .setEventType(EventType.SCHEDULED_FOR_TESTING)
                .setResponsibleDoctor(doctor)
                .setPatient(patient);
        return patientEventRepository.save(event);

    }

    public List<PatientEvent> getAllForPatient(Patient patient) {
        return patientEventRepository.findAllByPatient(patient);
    }

    public List<PatientEvent> getForLabTest(LabTest labTest) {
        return patientEventRepository.findPatientEventByLabTest(labTest);
    }


}