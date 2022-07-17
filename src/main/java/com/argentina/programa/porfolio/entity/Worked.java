package com.argentina.programa.porfolio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "workeds")
public class Worked {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String puesto;
    private String empleador;
    private String localidad;
    private String descripcion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
}