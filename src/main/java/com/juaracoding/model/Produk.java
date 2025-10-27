package com.juaracoding.model;


import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "MstProduk")
public class Produk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Nama",length = 60,nullable = false,unique = true)
    private String nama;

    /** change script when migration */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKategoriProduk",foreignKey = @ForeignKey(name = "fk-to-kategori",foreignKeyDefinition = "FOREIGN KEY ([IDKategoriProduk]) REFERENCES [projectz].[MstKategori] ([ID]) ON UPDATE SET NULL"))
    private KategoriProduk kategoriProduk;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;
    @Column(name = "CreatedDate",nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;
}
