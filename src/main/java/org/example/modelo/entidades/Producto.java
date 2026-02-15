package org.example.modelo.entidades;

import lombok.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto implements Serializable {

    private int idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private double precioProducto;
    private int existencia;
    private Date createdAt; // En la foto aparece como createdAt (camelCase)
    private int idCategoria;

    // Relación de objeto: un Producto tiene una Categoria
    private Categoria categoria;
}