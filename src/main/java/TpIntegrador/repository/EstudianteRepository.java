package TpIntegrador.repository;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.modelo.Estudiante;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepository {

    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante estudiante = new Estudiante();
                estudiante.setDni(Integer.parseInt(linea[0]));
                estudiante.setNombre(linea[1]);
                estudiante.setApellido(linea[2]);
                estudiante.setEdad(Integer.parseInt(linea[3]));
                estudiante.setGenero(linea[4]);
                estudiante.setCiudad(linea[5]);
                estudiante.setLu(Integer.parseInt(linea[6]));

                em.persist(estudiante);

            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void insertar(Estudiante estudiante){

            EntityManager em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
    }

    public List<Estudiante> estudiantesPorEdad(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante> personas = em.createQuery(
                        "SELECT e FROM Estudiante e ORDER BY e.edad", Estudiante.class)
                .getResultList();
        em.close();
        return personas;
    }

    public Estudiante buscarPorLu(int lu){
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.lu = :nroLibreta", Estudiante.class);
        query.setParameter("nroLibreta", lu);
        Estudiante estudiante = query.getSingleResult();
        return estudiante
        ;
    }

    public List<Estudiante> buscarPorGenero(String genero){
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class);
        query.setParameter("genero", genero); // o "M", "No Binario", etc.
        List<Estudiante> estudiantes = query.getResultList();

        return estudiantes
                ;
    }

    public List<Estudiante> obtenerPorCiudad(String carrera, String ciudad){
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Estudiante> query = em.createQuery(
                "SELECT ce.estudiante " +
                        "FROM Estudiante_Carrera ce " +
                        "WHERE ce.carrera.nombre = :nombreCarrera " +
                        "AND ce.estudiante.ciudad = :ciudad", Estudiante.class);

        query.setParameter("nombreCarrera", carrera);
        query.setParameter("ciudad", ciudad);

        List<Estudiante> estudiantes = query.getResultList();
        return estudiantes;
    }
}
