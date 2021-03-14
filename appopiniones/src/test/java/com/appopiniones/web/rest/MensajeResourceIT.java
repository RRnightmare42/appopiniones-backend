package com.appopiniones.web.rest;

import com.appopiniones.AppopinionesApp;
import com.appopiniones.domain.Mensaje;
import com.appopiniones.repository.MensajeRepository;
import com.appopiniones.service.MensajeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MensajeResource} REST controller.
 */
@SpringBootTest(classes = AppopinionesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MensajeResourceIT {

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMensajeMockMvc;

    private Mensaje mensaje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensaje createEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
            .body(DEFAULT_BODY);
        return mensaje;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensaje createUpdatedEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
            .body(UPDATED_BODY);
        return mensaje;
    }

    @BeforeEach
    public void initTest() {
        mensaje = createEntity(em);
    }

    @Test
    @Transactional
    public void createMensaje() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();
        // Create the Mensaje
        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isCreated());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate + 1);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getBody()).isEqualTo(DEFAULT_BODY);
    }

    @Test
    @Transactional
    public void createMensajeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();

        // Create the Mensaje with an existing ID
        mensaje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBodyIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajeRepository.findAll().size();
        // set the field null
        mensaje.setBody(null);

        // Create the Mensaje, which fails.


        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isBadRequest());

        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMensajes() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get all the mensajeList
        restMensajeMockMvc.perform(get("/api/mensajes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)));
    }
    
    @Test
    @Transactional
    public void getMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", mensaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mensaje.getId().intValue()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY));
    }
    @Test
    @Transactional
    public void getNonExistingMensaje() throws Exception {
        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMensaje() throws Exception {
        // Initialize the database
        mensajeService.save(mensaje);

        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // Update the mensaje
        Mensaje updatedMensaje = mensajeRepository.findById(mensaje.getId()).get();
        // Disconnect from session so that the updates on updatedMensaje are not directly saved in db
        em.detach(updatedMensaje);
        updatedMensaje
            .body(UPDATED_BODY);

        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMensaje)))
            .andExpect(status().isOk());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getBody()).isEqualTo(UPDATED_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingMensaje() throws Exception {
        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMensaje() throws Exception {
        // Initialize the database
        mensajeService.save(mensaje);

        int databaseSizeBeforeDelete = mensajeRepository.findAll().size();

        // Delete the mensaje
        restMensajeMockMvc.perform(delete("/api/mensajes/{id}", mensaje.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
