package de.gmasil.demo.springnative.jpa;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryExtension {

    public List<User> findAllWithRole(String role);

    public Optional<User> findWithRolesByUsername(String username);
}
