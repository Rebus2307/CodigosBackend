package org.example.modelo.entidades;

import lombok.*;
import java.io.Serializable;
import java.util.Date; // Necesario para el campo create_at

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria implements Serializable {

    private int idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;
    private Date create_at; // Incluido según el diagrama relacional

}