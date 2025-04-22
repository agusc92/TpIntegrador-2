package TpIntegrador.repository;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.modelo.Carrera;
import TpIntegrador.modelo.Estudiante;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;

import java.io.FileReader;

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
}
