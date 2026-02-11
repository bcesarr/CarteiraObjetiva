package com.carteiraobjetiva.carteiraobjetiva.repository;

import com.carteiraobjetiva.carteiraobjetiva.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}