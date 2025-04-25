package TpIntegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class EstudianteDTO {
    private int dni;
    private int lu;
    private String nombre;
    private String apellido;
    private int edad;
    private String sexo;
    private String ciudad;

    public EstudianteDTO(int dni, int lu, String nombre, String apellido, int edad, String sexo, String ciudad) {
        this.dni = dni;
        this.lu = lu;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "dni=" + dni +
                ", lu=" + lu +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", sexo='" + sexo + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
