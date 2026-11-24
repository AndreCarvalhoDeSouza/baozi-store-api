package com.baozistore.gerenciador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baozistore.gerenciador.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
