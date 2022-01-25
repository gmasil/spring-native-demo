package de.gmasil.demo.springnative.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryExtension {
}
