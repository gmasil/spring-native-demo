package de.gmasil.demo.springnative.service;

import java.lang.invoke.MethodHandles;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import de.gmasil.demo.springnative.jpa.Role;
import de.gmasil.demo.springnative.jpa.RoleRepository;
import de.gmasil.demo.springnative.jpa.User;

@Service
public class InitializeAdminService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String INITIAL_ADMIN_USER_MSG = "Initialized admin user\n\n" //
            + "#####################################\n" //
            + "Initial admin user\n" //
            + "Username: {}\n" //
            + "Password: {}\n" //
            + "#####################################\n";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepo;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initAdminUser() {
        if (!userService.hasUsers()) {
            User user = User.builder().username("admin").password("admin").build();
            Role role = roleRepo.findByNameOrCreate("ADMIN");
            user.addRole(role);
            userService.encodePassword(user);
            userService.save(user);
            LOG.info(INITIAL_ADMIN_USER_MSG, "admin", "admin");
            // check
            user = userService.findByUsername("admin").get();
            if (user.getCreatedAt() == null) {
                throw new IllegalStateException("admin created at is null");
            }
        }
    }
}
