package de.gmasil.demo.springnative.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class Auditable implements Serializable {

    @CreatedDate
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, updatable = true)
    private Date updatedAt;

    public abstract Long getId();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
