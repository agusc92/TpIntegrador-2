package TpIntegrador.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Estudiante {

    @Id
    private int dni;

    @Column (nullable = false)
    private String nombre;

    @Column (nullable = false)
    private String apellido;

    @Column (nullable = false)
    private int edad;

    @Column
    private String genero;

    @Column
    private String ciudad;

    @Column (nullable = false)
    private int lu;

    @OneToMany (mappedBy = "estudiante")
    private List<Estudiante_Carrera> carreras;

    public Estudiante(String nombre, String apellido, int edad,int dni, String genero,String ciudad , int lu ) {
        this.lu = lu;
        this.ciudad = ciudad;
        this.genero = genero;
        this.edad = edad;
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.carreras = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                '}';
    }
}
