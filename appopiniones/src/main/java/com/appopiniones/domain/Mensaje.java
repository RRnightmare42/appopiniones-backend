package com.appopiniones.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Mensaje.
 */
@Entity
@Table(name = "mensaje")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "body", length = 100, nullable = false)
    private String body;

    @ManyToOne
    @JsonIgnoreProperties(value = "mensajes", allowSetters = true)
    private Usuario usuario;

    @ManyToOne
    @JsonIgnoreProperties(value = "mensajes", allowSetters = true)
    private Tema tema;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public Mensaje body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Mensaje usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tema getTema() {
        return tema;
    }

    public Mensaje tema(Tema tema) {
        this.tema = tema;
        return this;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mensaje)) {
            return false;
        }
        return id != null && id.equals(((Mensaje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mensaje{" +
            "id=" + getId() +
            ", body='" + getBody() + "'" +
            "}";
    }
}
