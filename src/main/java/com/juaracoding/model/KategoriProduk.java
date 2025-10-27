package com.juaracoding.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MstKategori")
public class KategoriProduk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Nama",length = 60,nullable = false,unique = true)
    private String nama;

//    @OneToMany
//    private List<Produk> produk;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy;
    @Column(name = "CreatedDate",nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;
}
