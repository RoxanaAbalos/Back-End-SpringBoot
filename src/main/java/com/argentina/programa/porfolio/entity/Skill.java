package com.argentina.programa.porfolio.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String habilidad;
    private String nivel;
    private Long porciento;
}