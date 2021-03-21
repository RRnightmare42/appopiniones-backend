package com.appopiniones.web.rest;

import com.appopiniones.AppopinionesApp;
import com.appopiniones.domain.Tema;
import com.appopiniones.repository.TemaRepository;
import com.appopiniones.service.TemaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TemaResource} REST controller.
 */
@SpringBootTest(classes = AppopinionesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TemaResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private TemaService temaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemaMockMvc;

    private Tema tema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tema createEntity(EntityManager em) {
        Tema tema = new Tema()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .category(DEFAULT_CATEGORY)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return tema;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tema createUpdatedEntity(EntityManager em) {
        Tema tema = new Tema()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return tema;
    }

    @BeforeEach
    public void initTest() {
        tema = createEntity(em);
    }

    @Test
    @Transactional
    public void createTema() throws Exception {
        int databaseSizeBeforeCreate = temaRepository.findAll().size();
        // Create the Tema
        restTemaMockMvc.perform(post("/api/temas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isCreated());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeCreate + 1);
        Tema testTema = temaList.get(temaList.size() - 1);
        assertThat(testTema.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTema.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTema.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testTema.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testTema.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temaRepository.findAll().size();

        // Create the Tema with an existing ID
        tema.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemaMockMvc.perform(post("/api/temas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = temaRepository.findAll().size();
        // set the field null
        tema.setTitle(null);

        // Create the Tema, which fails.


        restTemaMockMvc.perform(post("/api/temas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = temaRepository.findAll().size();
        // set the field null
        tema.setDescription(null);

        // Create the Tema, which fails.


        restTemaMockMvc.perform(post("/api/temas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = temaRepository.findAll().size();
        // set the field null
        tema.setCategory(null);

        // Create the Tema, which fails.


        restTemaMockMvc.perform(post("/api/temas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemas() throws Exception {
        // Initialize the database
        temaRepository.saveAndFlush(tema);

        // Get all the temaList
        restTemaMockMvc.perform(get("/api/temas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tema.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }
    
    @Test
    @Transactional
    public void getTema() throws Exception {
        // Initialize the database
        temaRepository.saveAndFlush(tema);

        // Get the tema
        restTemaMockMvc.perform(get("/api/temas/{id}", tema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tema.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }
    @Test
    @Transactional
    public void getNonExistingTema() throws Exception {
        // Get the tema
        restTemaMockMvc.perform(get("/api/temas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTema() throws Exception {
        // Initialize the database
        temaService.save(tema);

        int databaseSizeBeforeUpdate = temaRepository.findAll().size();

        // Update the tema
        Tema updatedTema = temaRepository.findById(tema.getId()).get();
        // Disconnect from session so that the updates on updatedTema are not directly saved in db
        em.detach(updatedTema);
        updatedTema
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restTemaMockMvc.perform(put("/api/temas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTema)))
            .andExpect(status().isOk());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeUpdate);
        Tema testTema = temaList.get(temaList.size() - 1);
        assertThat(testTema.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTema.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTema.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTema.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTema.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTema() throws Exception {
        int databaseSizeBeforeUpdate = temaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemaMockMvc.perform(put("/api/temas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTema() throws Exception {
        // Initialize the database
        temaService.save(tema);

        int databaseSizeBeforeDelete = temaRepository.findAll().size();

        // Delete the tema
        restTemaMockMvc.perform(delete("/api/temas/{id}", tema.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
