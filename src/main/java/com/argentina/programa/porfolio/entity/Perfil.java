package com.argentina.programa.porfolio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "perfiles")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer dni;
    private String nombre;
    private String apellido;
    @Column(nullable=false, unique=true)
    private String email;
    private String telefono;
    private String foto;
    private String banner;
    private String acerca;
    private String ciudad;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "skill_id")
    private List<Skill> skills;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "worked_id")
    private List<Worked> workeds;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<Project>projects;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id")
    private List<Education>educations;

public Perfil(){
    skills=new ArrayList<>();
    workeds=new ArrayList<>();
    projects=new ArrayList<>();
    educations=new ArrayList<>();
}


}