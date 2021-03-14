package com.appopiniones.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.appopiniones.web.rest.TestUtil;

public class TemaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tema.class);
        Tema tema1 = new Tema();
        tema1.setId(1L);
        Tema tema2 = new Tema();
        tema2.setId(tema1.getId());
        assertThat(tema1).isEqualTo(tema2);
        tema2.setId(2L);
        assertThat(tema1).isNotEqualTo(tema2);
        tema1.setId(null);
        assertThat(tema1).isNotEqualTo(tema2);
    }
}
