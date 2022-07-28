package com.argentina.programa.porfolio.services;

import com.argentina.programa.porfolio.entity.Usuario;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
}
