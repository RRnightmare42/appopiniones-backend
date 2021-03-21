package com.appopiniones.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.appopiniones.web.rest.TestUtil;

public class MensajeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mensaje.class);
        Mensaje mensaje1 = new Mensaje();
        mensaje1.setId(1L);
        Mensaje mensaje2 = new Mensaje();
        mensaje2.setId(mensaje1.getId());
        assertThat(mensaje1).isEqualTo(mensaje2);
        mensaje2.setId(2L);
        assertThat(mensaje1).isNotEqualTo(mensaje2);
        mensaje1.setId(null);
        assertThat(mensaje1).isNotEqualTo(mensaje2);
    }
}
