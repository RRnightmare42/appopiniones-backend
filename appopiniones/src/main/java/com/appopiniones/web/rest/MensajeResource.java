package com.appopiniones.web.rest;

import com.appopiniones.domain.Mensaje;
import com.appopiniones.service.MensajeService;
import com.appopiniones.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.appopiniones.domain.Mensaje}.
 */
@RestController
@RequestMapping("/api")
public class MensajeResource {

    private final Logger log = LoggerFactory.getLogger(MensajeResource.class);

    private static final String ENTITY_NAME = "mensaje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MensajeService mensajeService;

    public MensajeResource(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    /**
     * {@code POST  /mensajes} : Create a new mensaje.
     *
     * @param mensaje the mensaje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensaje, or with status {@code 400 (Bad Request)} if the mensaje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mensajes")
    public ResponseEntity<Mensaje> createMensaje(@Valid @RequestBody Mensaje mensaje) throws URISyntaxException {
        log.debug("REST request to save Mensaje : {}", mensaje);
        if (mensaje.getId() != null) {
            throw new BadRequestAlertException("A new mensaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mensaje result = mensajeService.save(mensaje);
        return ResponseEntity.created(new URI("/api/mensajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mensajes} : Updates an existing mensaje.
     *
     * @param mensaje the mensaje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensaje,
     * or with status {@code 400 (Bad Request)} if the mensaje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensaje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mensajes")
    public ResponseEntity<Mensaje> updateMensaje(@Valid @RequestBody Mensaje mensaje) throws URISyntaxException {
        log.debug("REST request to update Mensaje : {}", mensaje);
        if (mensaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mensaje result = mensajeService.save(mensaje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mensaje.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mensajes} : get all the mensajes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mensajes in body.
     */
    @GetMapping("/mensajes")
    public ResponseEntity<List<Mensaje>> getAllMensajes(Pageable pageable) {
        log.debug("REST request to get a page of Mensajes");
        Page<Mensaje> page = mensajeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mensajes/:id} : get the "id" mensaje.
     *
     * @param id the id of the mensaje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensaje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mensajes/{id}")
    public ResponseEntity<Mensaje> getMensaje(@PathVariable Long id) {
        log.debug("REST request to get Mensaje : {}", id);
        Optional<Mensaje> mensaje = mensajeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mensaje);
    }

    /**
     * {@code DELETE  /mensajes/:id} : delete the "id" mensaje.
     *
     * @param id the id of the mensaje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id) {
        log.debug("REST request to delete Mensaje : {}", id);
        mensajeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
