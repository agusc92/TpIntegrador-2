package TpIntegrador.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CarreraPorInscriptosDTO {
    private String nombre;
    private long inscriptos;

    public CarreraPorInscriptosDTO(String nombre, long inscriptos) {
        this.nombre = nombre;
        this.inscriptos = inscriptos;
    }

    @Override
    public String toString() {
        return "CarreraPorInscriptos{" +
                "nombre='" + nombre + '\'' +
                ", inscriptos=" + inscriptos +
                '}';
    }
}
