package com.argentina.programa.porfolio.services;

import com.argentina.programa.porfolio.entity.Perfil;

import java.util.List;

public interface IPerfilService {
    public List<Perfil> findAll();

    public Perfil findById(Long id);

    public Perfil save(Perfil perfil);

    public void delete(Long id);
}
