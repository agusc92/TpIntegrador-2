package TpIntegrador.repository;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.dto.EstudianteDTO;
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
        System.out.println("Estudiante insertado con exito");
    }

    public List<EstudianteDTO> estudiantesPorEdad(){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
        estudianteDTOS= em.createQuery(
                        "SELECT new TpIntegrador.dto.EstudianteDTO(e.dni,e.lu, e.nombre, e.apellido, e.edad, e.genero, e.ciudad) " +
                                "FROM Estudiante e " +
                                "ORDER BY e.edad",
                        EstudianteDTO.class)
                .getResultList();
        em.close();
        return estudianteDTOS;

    }

    public EstudianteDTO buscarPorLu(int lu){
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO estDTO;
        TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.lu = :nroLibreta", Estudiante.class);
        query.setParameter("nroLibreta", lu);
        Estudiante estudiante = query.getSingleResult();
        estDTO = new EstudianteDTO(estudiante.getDni(), estudiante.getLu(), estudiante.getNombre(),estudiante.getApellido(),estudiante.getEdad(),estudiante.getGenero(),estudiante.getCiudad());
        return estDTO;
    }

    public List<EstudianteDTO> buscarPorGenero(String genero){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = new ArrayList<>();
        estudiantes = em.createQuery(
                        "SELECT new TpIntegrador.dto.EstudianteDTO(e.dni, e.lu, e.nombre, e.apellido, e.edad, e.genero, e.ciudad) " +
                                "FROM Estudiante e WHERE e.genero = :genero",
                        EstudianteDTO.class
                ).setParameter("genero", genero)
                .getResultList();

        return estudiantes;
    }

    public List<EstudianteDTO> obtenerPorCiudad(String carrera, String ciudad){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = new ArrayList<>();
        estudiantes = em.createQuery(
                        "SELECT new TpIntegrador.dto.EstudianteDTO(ce.estudiante.dni, ce.estudiante.lu, ce.estudiante.nombre, ce.estudiante.apellido, ce.estudiante.edad, ce.estudiante.genero, ce.estudiante.ciudad) " +
                                "FROM Estudiante_Carrera ce " +
                                "WHERE ce.carrera.nombre = :nombreCarrera " +
                                "AND ce.estudiante.ciudad = :ciudad",
                        EstudianteDTO.class
                ).setParameter("nombreCarrera", carrera)
                .setParameter("ciudad", ciudad)
                .getResultList();


        return estudiantes;
    }
}
