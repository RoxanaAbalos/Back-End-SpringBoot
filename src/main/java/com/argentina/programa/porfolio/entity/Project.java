package com.argentina.programa.porfolio.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String titulo;
    private String descripcion;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String link;
    private String imagen;
    private String git;
}