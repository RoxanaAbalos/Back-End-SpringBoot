package com.argentina.programa.porfolio.dao;

import com.argentina.programa.porfolio.entity.Perfil;
import org.springframework.data.repository.CrudRepository;

public interface IPerfilDao extends CrudRepository<Perfil,Long> {
}
