package TpIntegrador.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_carrera;

    @Column (nullable = false)
    private String nombre;

    @Column
    private int duracion;

    @OneToMany (mappedBy = "carrera")
    private List<Estudiante_Carrera> carreras;
}
