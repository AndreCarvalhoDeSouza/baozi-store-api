package com.baozistore.gerenciador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baozistore.gerenciador.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long>{

}
