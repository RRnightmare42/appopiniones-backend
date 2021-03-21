package com.appopiniones.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(min = 8, max = 20)
    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "messages")
    private Integer messages;

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Tema> temas = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Mensaje> mensajes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Usuario username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Usuario password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Usuario photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Usuario photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Usuario creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getMessages() {
        return messages;
    }

    public Usuario messages(Integer messages) {
        this.messages = messages;
        return this;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }

    public Set<Tema> getTemas() {
        return temas;
    }

    public Usuario temas(Set<Tema> temas) {
        this.temas = temas;
        return this;
    }

    public Usuario addTema(Tema tema) {
        this.temas.add(tema);
        tema.setUsuario(this);
        return this;
    }

    public Usuario removeTema(Tema tema) {
        this.temas.remove(tema);
        tema.setUsuario(null);
        return this;
    }

    public void setTemas(Set<Tema> temas) {
        this.temas = temas;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }

    public Usuario mensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public Usuario addMensaje(Mensaje mensaje) {
        this.mensajes.add(mensaje);
        mensaje.setUsuario(this);
        return this;
    }

    public Usuario removeMensaje(Mensaje mensaje) {
        this.mensajes.remove(mensaje);
        mensaje.setUsuario(null);
        return this;
    }

    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return id != null && id.equals(((Usuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", messages=" + getMessages() +
            "}";
    }
}
