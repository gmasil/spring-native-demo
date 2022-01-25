package de.gmasil.demo.springnative.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryExtension {
}
