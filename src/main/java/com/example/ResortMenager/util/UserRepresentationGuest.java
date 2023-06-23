package com.example.ResortMenager.util;

import com.example.ResortMenager.DTO.GuestDTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class UserRepresentationGuest extends UserRepresentation {
    public UserRepresentationGuest(GuestDTO guestDTO, Integer size) {
        super.setUsername(guestDTO.getName()+"_"+guestDTO.getSurrname()+(size+1));
        super.setEnabled(true);
        super.setEmail(guestDTO.getEmail());
        super.setFirstName(guestDTO.getName());
        super.setLastName(guestDTO.getSurrname());
        List<CredentialRepresentation> credentials = new ArrayList<>();
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType("password");
        credentialRepresentation.setValue(guestDTO.getPassword());
        credentialRepresentation.setTemporary(false);
        credentials.add(credentialRepresentation);
        super.setCredentials(credentials);
    }
}
