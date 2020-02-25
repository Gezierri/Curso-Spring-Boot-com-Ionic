package com.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursomc.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	Cliente findByEmail(String email);
}
