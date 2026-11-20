/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Artefacto")
public class Artefacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Lob
    private String descripcion;
    
    @Column(name = "anio_creacion")
    private int anioCreacion;
    
    @Column(length = 100)
    private String procedencia;

    public Artefacto() {
    }

    public Artefacto(int id, String nombre, String descripcion, int anioCreacion, String procedencia) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.anioCreacion = anioCreacion;
        this.procedencia = procedencia;
    }

    public Artefacto(String nombre, String descripcion, int anioCreacion, String procedencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.anioCreacion = anioCreacion;
        this.procedencia = procedencia;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getAnioCreacion() { return anioCreacion; }
    public void setAnioCreacion(int anioCreacion) { this.anioCreacion = anioCreacion; }
    public String getProcedencia() { return procedencia; }
    public void setProcedencia(String procedencia) { this.procedencia = procedencia; }

    @Override
    public String toString() {
        return "Artefacto{" + "id=" + id + ", nombre='" + nombre + '\'' + '}';
    }
}
