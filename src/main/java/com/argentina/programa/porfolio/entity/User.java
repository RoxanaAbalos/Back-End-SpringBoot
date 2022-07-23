package com.argentina.programa.porfolio.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 60)
    private String password;
    private Boolean enabled;
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Role> roles;
}