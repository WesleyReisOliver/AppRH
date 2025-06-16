package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    Funcionario findById(long id);

    //busca
    Funcionario findByNome(String nome);
}