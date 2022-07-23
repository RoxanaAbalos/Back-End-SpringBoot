package com.argentina.programa.porfolio.services;

import com.argentina.programa.porfolio.dao.IPerfilDao;
import com.argentina.programa.porfolio.entity.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IPerfilImpl implements IPerfilService {
    @Autowired
    private IPerfilDao perfilDao;
    @Override
    public List<Perfil> findAll() {
        return (List<Perfil>) perfilDao.findAll();
    }

    @Override
    public Perfil findById(Long id) {
        return perfilDao.findById(id).orElse(null);
    }

    @Override
    public Perfil save(Perfil perfil) {
        return perfilDao.save(perfil);
    }

    @Override
    public void delete(Long id) {
        perfilDao.deleteById(id);
    }
}
