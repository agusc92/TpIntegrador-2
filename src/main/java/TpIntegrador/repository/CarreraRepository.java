package TpIntegrador.repository;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.dto.ReporteDTO;
import TpIntegrador.modelo.Carrera;
import TpIntegrador.modelo.Estudiante;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.FileReader;
import java.util.*;

public class CarreraRepository {
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setNombre(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));

                em.persist(carrera);



            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<ReporteDTO> obtenerInforme(){
        EntityManager em = JPAUtil.getEntityManager();
        List<ReporteDTO> reportes = new ArrayList<>();

        try { List<ReporteDTO> inscriptos = em.createQuery(
                "SELECT new TpIntegrador.dto.ReporteDTO(" +
                        "c.nombre, " +
                        "e.anio_inscripcion, " +
                        "COUNT(e), " +
                        "SUM(CASE WHEN e.anio_graduacion IS NOT NULL THEN 0 ELSE 0 END)) " +
                        "FROM Estudiante_Carrera e " +
                        "JOIN e.carrera c " +
                        "WHERE e.anio_inscripcion IS NOT NULL " +
                        "GROUP BY c.nombre, e.anio_inscripcion", ReporteDTO.class
        ).getResultList();

            List<ReporteDTO> egresados = em.createQuery(
                    "SELECT new TpIntegrador.dto.ReporteDTO(" +
                            "c.nombre, " +
                            "e.anio_graduacion, " +
                            "SUM(CASE WHEN e.anio_graduacion IS NOT NULL THEN 0 ELSE 0 END), " +
                            "COUNT(e)) " +
                            "FROM Estudiante_Carrera e " +
                            "JOIN e.carrera c " +
                            "WHERE e.anio_graduacion IS NOT NULL AND e.anio_graduacion > 0 " +
                            "GROUP BY c.nombre, e.anio_graduacion", ReporteDTO.class
            ).getResultList();
            Map<String, ReporteDTO> reporteMap = new HashMap<>();

// Procesar inscriptos
            for (ReporteDTO insc : inscriptos) {
                String clave = insc.getNombreCarrera() + "_" + insc.getAnio();
                reporteMap.putIfAbsent(clave, new ReporteDTO(insc.getNombreCarrera(), insc.getAnio(), 0L, 0L));
                reporteMap.get(clave).setInscriptos(insc.getInscriptos());
            }

// Procesar graduados
            for (ReporteDTO grad : egresados) {
                String clave = grad.getNombreCarrera() + "_" + grad.getAnio();
                reporteMap.putIfAbsent(clave, new ReporteDTO(grad.getNombreCarrera(), grad.getAnio(), 0L, 0L));
                reporteMap.get(clave).setGraduados(grad.getGraduados());
            }

// Convertir a lista ordenada
             reportes = new ArrayList<>(reporteMap.values());
            reportes.sort(Comparator
                    .comparing(ReporteDTO::getNombreCarrera)
                    .thenComparing(ReporteDTO::getAnio));

// Ordenar por carrera y año
            reportes.sort(Comparator
                    .comparing(ReporteDTO::getNombreCarrera)
                    .thenComparing(ReporteDTO::getAnio));


        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            em.close();
        }
        return reportes;
    }


}
