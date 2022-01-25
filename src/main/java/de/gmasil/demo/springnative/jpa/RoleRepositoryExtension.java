package de.gmasil.demo.springnative.jpa;

public interface RoleRepositoryExtension {

    public Role findByNameOrCreate(String name);
}
