package TpIntegrador.repository;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.dto.CarreraPorInscriptosDTO;
import TpIntegrador.modelo.Carrera;
import TpIntegrador.modelo.Estudiante;
import TpIntegrador.modelo.Estudiante_Carrera;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Estudiante_CarreraRepository {

    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante_Carrera eC = new Estudiante_Carrera();

                Estudiante estudiante = em.find(Estudiante.class, Integer.parseInt(linea[1]));
                eC.setEstudiante(estudiante);

                Carrera carrera = em.find(Carrera.class, Integer.parseInt(linea[2]));
                eC.setCarrera(carrera);

                eC.setAnio_inscripcion(Integer.parseInt((linea[3])));
                eC.setAnio_graduacion(Integer.parseInt((linea[4])));
                eC.setAntiguedad(Integer.parseInt((linea[5])));


                em.persist(eC);



            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void insertar(int estudiante,int carrera,int fecha){
        EntityManager em = JPAUtil.getEntityManager();
        Estudiante_Carrera eC = new Estudiante_Carrera();
        try{
        em.getTransaction().begin();

        eC.setAnio_graduacion(0);
        eC.setAnio_inscripcion(fecha);
        eC.setAntiguedad(1);

        Estudiante estudiante1 = em.find(Estudiante.class,estudiante);
        Carrera carrera1 = em.find(Carrera.class , carrera);

        if(carrera1!= null && estudiante1 != null){
            eC.setCarrera(carrera1);
            eC.setEstudiante(estudiante1);
            em.persist(eC);
            em.getTransaction().commit();
            System.out.println("Estudiante matriculado con exito");
        }else {
            System.out.println("no se encontro la carrera o el estudiante");
        }
    } catch (Exception e) {
        System.out.println("Error al matricular estudiante" + e.getMessage());
    } finally {
        em.close();
    }




    }

    public List<CarreraPorInscriptosDTO> ordenarPorInscriptos(){
        EntityManager em = JPAUtil.getEntityManager();
        List<CarreraPorInscriptosDTO> resultados = new ArrayList<>();
        try{
           resultados = em.createQuery(
                "SELECT new TpIntegrador.dto.CarreraPorInscriptosDTO(ce.carrera.nombre, COUNT(ce.estudiante)) " +
                        "FROM Estudiante_Carrera ce " +
                        "GROUP BY ce.carrera.nombre " +
                        "ORDER BY COUNT(ce.estudiante) DESC",
                CarreraPorInscriptosDTO.class
        ).getResultList();
    } catch (Exception e) {
        System.out.println("Error al obtener carreras por inscriptos" + e.getMessage());
    } finally {
        em.close();
    }

        return resultados;
    }


}
