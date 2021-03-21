package com.appopiniones.repository;

import com.appopiniones.domain.Mensaje;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mensaje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
}
