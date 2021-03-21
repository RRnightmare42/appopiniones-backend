package com.appopiniones.service;

import com.appopiniones.domain.Mensaje;
import com.appopiniones.repository.MensajeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mensaje}.
 */
@Service
@Transactional
public class MensajeService {

    private final Logger log = LoggerFactory.getLogger(MensajeService.class);

    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    /**
     * Save a mensaje.
     *
     * @param mensaje the entity to save.
     * @return the persisted entity.
     */
    public Mensaje save(Mensaje mensaje) {
        log.debug("Request to save Mensaje : {}", mensaje);
        return mensajeRepository.save(mensaje);
    }

    /**
     * Get all the mensajes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Mensaje> findAll(Pageable pageable) {
        log.debug("Request to get all Mensajes");
        return mensajeRepository.findAll(pageable);
    }


    /**
     * Get one mensaje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Mensaje> findOne(Long id) {
        log.debug("Request to get Mensaje : {}", id);
        return mensajeRepository.findById(id);
    }

    /**
     * Delete the mensaje by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mensaje : {}", id);
        mensajeRepository.deleteById(id);
    }
}
