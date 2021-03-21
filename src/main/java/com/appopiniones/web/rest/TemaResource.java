package com.appopiniones.web.rest;

import com.appopiniones.domain.Tema;
import com.appopiniones.service.TemaService;
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
 * REST controller for managing {@link com.appopiniones.domain.Tema}.
 */
@RestController
@RequestMapping("/api")
public class TemaResource {

    private final Logger log = LoggerFactory.getLogger(TemaResource.class);

    private static final String ENTITY_NAME = "tema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemaService temaService;

    public TemaResource(TemaService temaService) {
        this.temaService = temaService;
    }

    /**
     * {@code POST  /temas} : Create a new tema.
     *
     * @param tema the tema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tema, or with status {@code 400 (Bad Request)} if the tema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/temas")
    public ResponseEntity<Tema> createTema(@Valid @RequestBody Tema tema) throws URISyntaxException {
        log.debug("REST request to save Tema : {}", tema);
        if (tema.getId() != null) {
            throw new BadRequestAlertException("A new tema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tema result = temaService.save(tema);
        return ResponseEntity.created(new URI("/api/temas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /temas} : Updates an existing tema.
     *
     * @param tema the tema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tema,
     * or with status {@code 400 (Bad Request)} if the tema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/temas")
    public ResponseEntity<Tema> updateTema(@Valid @RequestBody Tema tema) throws URISyntaxException {
        log.debug("REST request to update Tema : {}", tema);
        if (tema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tema result = temaService.save(tema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tema.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /temas} : get all the temas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of temas in body.
     */
    @GetMapping("/temas")
    public ResponseEntity<List<Tema>> getAllTemas(Pageable pageable) {
        log.debug("REST request to get a page of Temas");
        Page<Tema> page = temaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /temas/:id} : get the "id" tema.
     *
     * @param id the id of the tema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/temas/{id}")
    public ResponseEntity<Tema> getTema(@PathVariable Long id) {
        log.debug("REST request to get Tema : {}", id);
        Optional<Tema> tema = temaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tema);
    }

    /**
     * {@code DELETE  /temas/:id} : delete the "id" tema.
     *
     * @param id the id of the tema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/temas/{id}")
    public ResponseEntity<Void> deleteTema(@PathVariable Long id) {
        log.debug("REST request to delete Tema : {}", id);
        temaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
