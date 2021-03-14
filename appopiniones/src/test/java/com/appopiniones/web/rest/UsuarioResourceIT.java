package com.appopiniones.web.rest;

import com.appopiniones.AppopinionesApp;
import com.appopiniones.domain.Usuario;
import com.appopiniones.repository.UsuarioRepository;
import com.appopiniones.service.UsuarioService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@SpringBootTest(classes = AppopinionesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsuarioResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MESSAGES = 1;
    private static final Integer UPDATED_MESSAGES = 2;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .messages(DEFAULT_MESSAGES);
        return usuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .messages(UPDATED_MESSAGES);
        return usuario;
    }

    @BeforeEach
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();
        // Create the Usuario
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUsuario.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUsuario.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testUsuario.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testUsuario.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testUsuario.getMessages()).isEqualTo(DEFAULT_MESSAGES);
    }

    @Test
    @Transactional
    public void createUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario with an existing ID
        usuario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setUsername(null);

        // Create the Usuario, which fails.


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setPassword(null);

        // Create the Usuario, which fails.


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setCreationDate(null);

        // Create the Usuario, which fails.


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].messages").value(hasItem(DEFAULT_MESSAGES)));
    }
    
    @Test
    @Transactional
    public void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.messages").value(DEFAULT_MESSAGES));
    }
    @Test
    @Transactional
    public void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuario() throws Exception {
        // Initialize the database
        usuarioService.save(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .messages(UPDATED_MESSAGES);

        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuario)))
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUsuario.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUsuario.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testUsuario.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testUsuario.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testUsuario.getMessages()).isEqualTo(UPDATED_MESSAGES);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioService.save(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Delete the usuario
        restUsuarioMockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
