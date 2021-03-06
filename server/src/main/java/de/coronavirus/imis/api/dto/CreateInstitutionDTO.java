package de.coronavirus.imis.api.dto;

import lombok.Data;

import de.coronavirus.imis.domain.InstitutionType;

@Data
public class CreateInstitutionDTO {
    private InstitutionType institutionType;
    private String name;
    private String street;
    private String houseNumber;
    private String zip;
    private String city;
    private String email;
    private String phoneNumber;
    private String comment;
    private String id;
    private RegisterUserRequest user;
}
