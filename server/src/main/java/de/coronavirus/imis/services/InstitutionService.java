package de.coronavirus.imis.services;

import static de.coronavirus.imis.api.dto.CreateInstitutionDTO.fromRegisterRequest;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.RegisterUserRequest;
import de.coronavirus.imis.domain.Clinic;
import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.Institution;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.TestSite;
import de.coronavirus.imis.repositories.ClinicRepository;
import de.coronavirus.imis.repositories.DoctorRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.TestSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final LaboratoryRepository laboratoryRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final TestSiteRepository testSiteRepository;


    @Transactional
    public Doctor createDoctorInstitution(CreateInstitutionDTO institutionDTO) {
        var doctor = new Doctor();
        doctor.setName(institutionDTO.getName());
        doctor.setStreet(institutionDTO.getStreet());
        doctor.setHouseNumber(institutionDTO.getHouseNumber());
        doctor.setZip(institutionDTO.getZip());
        doctor.setCity(institutionDTO.getCity());
        doctor.setEmail(institutionDTO.getEmail());
        doctor.setPhoneNumber(institutionDTO.getPhoneNumber());
        doctor.setComment(institutionDTO.getComment());
       doctor.setId(UUID.randomUUID().toString());
        return this.doctorRepository.saveAndFlush(doctor);
    }

    @Transactional
    public Laboratory createLaboratoryInstitution(CreateInstitutionDTO institutionDTO) {
        var laboratory = new Laboratory();
        laboratory.setName(institutionDTO.getName());
        laboratory.setStreet(institutionDTO.getStreet());
        laboratory.setHouseNumber(institutionDTO.getHouseNumber());
        laboratory.setZip(institutionDTO.getZip());
        laboratory.setCity(institutionDTO.getCity());
        laboratory.setEmail(institutionDTO.getEmail());
        laboratory.setPhoneNumber(institutionDTO.getPhoneNumber());
        laboratory.setComment(institutionDTO.getComment());
        laboratory.setId(UUID.randomUUID().toString());
        return this.laboratoryRepository.saveAndFlush(laboratory);
    }

    // TODO remove after MVP and set UUID as generator in Institution and remove generated UUID in this service
    //      ask @jonathangpk
    @Transactional
    public Laboratory createLaboratoryInstitution(CreateInstitutionDTO institutionDTO, String id) {
        var laboratory = new Laboratory();
        laboratory.setName(institutionDTO.getName());
        laboratory.setStreet(institutionDTO.getStreet());
        laboratory.setHouseNumber(institutionDTO.getHouseNumber());
        laboratory.setZip(institutionDTO.getZip());
        laboratory.setCity(institutionDTO.getCity());
        laboratory.setEmail(institutionDTO.getEmail());
        laboratory.setPhoneNumber(institutionDTO.getPhoneNumber());
        laboratory.setComment(institutionDTO.getComment());
        laboratory.setId(id);

        return this.laboratoryRepository.saveAndFlush(laboratory);
    }

    @Transactional
    public Clinic createClinicInstitution(CreateInstitutionDTO institutionDTO) {
        var clinic = new Clinic();
        clinic.setName(institutionDTO.getName());
        clinic.setStreet(institutionDTO.getStreet());
        clinic.setHouseNumber(institutionDTO.getHouseNumber());
        clinic.setZip(institutionDTO.getZip());
        clinic.setCity(institutionDTO.getCity());
        clinic.setEmail(institutionDTO.getEmail());
        clinic.setPhoneNumber(institutionDTO.getPhoneNumber());
        clinic.setComment(institutionDTO.getComment());
        clinic.setId(UUID.randomUUID().toString());

        return this.clinicRepository.saveAndFlush(clinic);
    }

    @Transactional
    public TestSite createTestSiteInstitution(CreateInstitutionDTO institutionDTO) {
        var testSite = new TestSite();
        testSite.setName(institutionDTO.getName());
        testSite.setStreet(institutionDTO.getStreet());
        testSite.setHouseNumber(institutionDTO.getHouseNumber());
        testSite.setZip(institutionDTO.getZip());
        testSite.setCity(institutionDTO.getCity());
        testSite.setEmail(institutionDTO.getEmail());
        testSite.setPhoneNumber(institutionDTO.getPhoneNumber());
        testSite.setComment(institutionDTO.getComment());
        testSite.setId(UUID.randomUUID().toString());

        return this.testSiteRepository.saveAndFlush(testSite);
    }

    protected Institution createInstiution(RegisterUserRequest registerUserRequest) {
        var dto = fromRegisterRequest(registerUserRequest);
        Institution result = null;
        switch (dto.getInstitutionType()) {
            case CLINIC:
                result = createClinicInstitution(dto);
                break;
            case TEST_SITE:
                result = createTestSiteInstitution(dto);
                break;
            case LABORATORY:
                result = createLaboratoryInstitution(dto);
                break;
            case DOCTORS_OFFICE:
                result = createDoctorInstitution(dto);
                break;
            case GOVERNMENT_AGENCY:
                break;
        }
        return result;
    }
}
