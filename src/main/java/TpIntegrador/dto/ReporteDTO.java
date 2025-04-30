package TpIntegrador.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReporteDTO {
    private String nombreCarrera;
    private Integer anio;
    private Long inscriptos;
    private long graduados;

    public ReporteDTO(String nombreCarrera, Integer anio, Long cantidadInscriptos, long cantidadEgresados) {
        this.nombreCarrera = nombreCarrera;
        this.anio = anio;
        this.inscriptos = cantidadInscriptos;
        this.graduados = cantidadEgresados;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "nombreCarrera='" + nombreCarrera + '\'' +
                ", anio=" + anio +
                ", inscriptos=" + inscriptos +
                ", graduados=" + graduados +
                '}';
    }
}
