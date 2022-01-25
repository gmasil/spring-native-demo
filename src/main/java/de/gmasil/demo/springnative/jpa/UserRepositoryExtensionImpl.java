package de.gmasil.demo.springnative.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class UserRepositoryExtensionImpl implements UserRepositoryExtension {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAllWithRole(String role) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        Join<Object, Object> roles = from.join("roles");
        criteria.where(builder.equal(roles.get("name"), role));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Optional<User> findWithRolesByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        // eagerly load roles
        from.fetch("roles");
        criteria.where(builder.equal(from.get("username"), username));
        List<User> resultList = entityManager.createQuery(criteria).getResultList();
        if (resultList.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(resultList.get(0));
    }
}
