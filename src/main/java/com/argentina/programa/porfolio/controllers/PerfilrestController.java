package com.argentina.programa.porfolio.controllers;

import com.argentina.programa.porfolio.entity.Perfil;
import com.argentina.programa.porfolio.services.IPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/porfolio")
public class PerfilrestController {
    @Autowired
    private IPerfilService perfilService;

    @GetMapping("/perfil")
    public List<Perfil> index(){
        return perfilService.findAll();
    }


    @GetMapping("/perfil/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Perfil perfil=null;
        Map<String, Object> response = new HashMap<>();
        try {
            perfil = perfilService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (perfil == null) {
            response.put("mensaje", "Perfil ID: ".concat(id.toString().concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Perfil>(perfil, HttpStatus.OK);
    }

    @PostMapping("/perfil")
    public ResponseEntity<?> create(@RequestBody Perfil perfil) {
        Perfil perfilNew =null;
        Map<String, Object> response = new HashMap<>();
        try {
            perfilNew = perfilService.save(perfil);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Perfil creado con éxito!");
        response.put("competencia", perfilNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @PutMapping("/perfil/{id}")
    public ResponseEntity<?> update (@RequestBody Perfil perfil ,@PathVariable Long id){

        Perfil perfilActual = perfilService.findById(id);
        Perfil perfilUpdate= null;
        Map<String, Object> response = new HashMap<>();

        if (perfilActual == null) {
            response.put("mensaje", "Error nose puedo editar ,Perfil ID:"
                    .concat(id.toString().concat("no existe en labase de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            perfilActual=perfil;
            perfilUpdate = perfilService.save(perfilActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Perfil ha sido actualizado con exito!");
        response.put("competencia", perfilUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/perfil/{id}")
    public ResponseEntity<?>  delete(@PathVariable Long id) {
        Map<String,Object> response = new HashMap<>();
        try {
            perfilService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al Eliminar el Perfil en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Perfil ha sido eliminado con exito!");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
}
