package TpIntegrador.repository;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.dto.ReporteDTO;
import TpIntegrador.modelo.Carrera;
import TpIntegrador.modelo.Estudiante;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

                //em.flush();

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
        try {
            reportes= em.createQuery("SELECT new TpIntegrador.dto.ReporteDTO(" +
                    "ce.carrera.nombre, " +
                    "COALESCE(ce.anio_inscripcion, ce.anio_graduacion), " +
                    "SUM(CASE WHEN ce.anio_inscripcion IS NOT NULL THEN 1 ELSE 0 END), " +
                    "SUM(CASE WHEN ce.anio_graduacion IS NOT NULL THEN 1 ELSE 0 END)) " +
                    "FROM Estudiante_Carrera ce " +
                    "GROUP BY ce.carrera.nombre, COALESCE(ce.anio_inscripcion, ce.anio_graduacion) " +
                    "ORDER BY ce.carrera.nombre, COALESCE(ce.anio_inscripcion, ce.anio_graduacion)",ReporteDTO.class).getResultList();

        }catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            em.close();
        }
        return reportes;
    }

    /*

                "SELECT ce.carrera.nombre, ce.anio_inscripcion, COUNT(ce)"+
                "FROM Estudiante_Carrera ce GROUP BY ce.carrera.nombre, ce.anio_inscripcion ORDER BY ce.carrera.nombre, ce.anio_inscripcion", Object[].class);

        TypedQuery<Object[]> query2 = em.createQuery(
                "SELECT ce.carrera.nombre, ce.anio_graduacion, COUNT(ce) FROM Estudiante_Carrera ce WHERE ce.anio_graduacion IS NOT NULL GROUP BY ce.carrera.nombre, ce.anio_graduacion ORDER BY ce.carrera.nombre, ce.anio_graduacion", Object[].class);
     */
}
