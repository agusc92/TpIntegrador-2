package TpIntegrador.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReporteDTO {
    private String nombreCarrera;
    private Integer anio;
    private Long inscriptos;
    private Long graduados;

    public ReporteDTO(String nombreCarrera, Integer anio, Long cantidadInscriptos, Long cantidadEgresados) {
        this.nombreCarrera = nombreCarrera;
        this.anio = anio;
        this.inscriptos = cantidadInscriptos;
        this.graduados = cantidadEgresados;
    }

    @Override
    public String toString() {
        return "ReporteDTO{" +
                "nombreCarrera='" + nombreCarrera + '\'' +
                ", anio=" + anio +
                ", inscriptos=" + inscriptos +
                ", graduados=" + graduados +
                '}';
    }
}
