package de.gmasil.demo.springnative.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ROLE")
@Table(name = "ROLE")
public class Role implements GrantedAuthority {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = { CascadeType.DETACH, CascadeType.PERSIST })
    private Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return getName();
    }

    @Builder
    public Role(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        users.add(user);
        user.getRoles().add(this);
    }

    @PreRemove
    private void preRemove() {
        for (User user : users) {
            user.getRoles().remove(this);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
