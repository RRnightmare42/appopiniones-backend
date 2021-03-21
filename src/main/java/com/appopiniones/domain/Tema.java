package com.appopiniones.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tema.
 */
@Entity
@Table(name = "tema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @NotNull
    @Size(min = 10, max = 200)
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "temas", allowSetters = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "tema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Mensaje> mensajes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Tema title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Tema description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public Tema category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Tema photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Tema photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Tema usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }

    public Tema mensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public Tema addMensaje(Mensaje mensaje) {
        this.mensajes.add(mensaje);
        mensaje.setTema(this);
        return this;
    }

    public Tema removeMensaje(Mensaje mensaje) {
        this.mensajes.remove(mensaje);
        mensaje.setTema(null);
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
        if (!(o instanceof Tema)) {
            return false;
        }
        return id != null && id.equals(((Tema) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tema{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
