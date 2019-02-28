package com.cybr406.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ProfileEventHandler {

    Logger logger = LoggerFactory.getLogger(ProfileEventHandler.class);

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN') or #profile.email == authentication.principal.username")
    public void handleBeforeSave(Profile profile) {
        System.out.println("Save a profile");
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN') or #profile.email == authentication.principal.username")
    public void handleBeforeDelete(Profile profile) {
        System.out.println("Delete a profile");
    }

    @HandleAfterCreate
    public void handleAuthorCreated(Profile profile) {
        logger.info("Profile {} created.", profile.getEmail());
    }

}
