package de.gmasil.demo.springnative.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class RoleRepositoryExtensionImpl implements RoleRepositoryExtension {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findByNameOrCreate(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
        Root<Role> root = criteria.from(Role.class);
        criteria.where(builder.equal(root.get("name"), name));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            Role role = Role.builder().name(name).build();
            entityManager.persist(role);
            return role;
        }
    }
}
