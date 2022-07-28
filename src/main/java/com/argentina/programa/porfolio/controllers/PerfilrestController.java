package com.argentina.programa.porfolio.controllers;

import com.argentina.programa.porfolio.entity.Perfil;
import com.argentina.programa.porfolio.services.IPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Secured("ROLE_ADMIN")
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
        response.put("perfil", perfilNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
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
        response.put("perfil", perfilUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @Secured("ROLE_ADMIN")
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
    @Secured("ROLE_ADMIN")
    @PostMapping("/perfil/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Perfil perfil = perfilService.findById(id);
        if (!archivo.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString() + "-" + archivo.getOriginalFilename();
            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen " + nombreArchivo);
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnterior = perfil.getFoto();
            if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                    archivoFotoAnterior.delete();
                }
            }
            perfil.setFoto(nombreArchivo);
            perfilService.save(perfil);
            response.put("perfil", perfil);
            response.put("mensaje", "El Perfil ha sido actualizado con éxito! " + nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;
        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (!recurso.exists() && !recurso.isReadable()) {
            throw new RuntimeException("Error no se pudo cargar la imagen " + nombreFoto);
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }
}
