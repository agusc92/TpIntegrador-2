package TpIntegrador;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.modelo.Estudiante;
import TpIntegrador.repository.CarreraRepository;
import TpIntegrador.repository.EstudianteRepository;
import TpIntegrador.repository.Estudiante_CarreraRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        EstudianteRepository eR = new EstudianteRepository();
        CarreraRepository cR = new CarreraRepository();
        Estudiante_CarreraRepository eCR = new Estudiante_CarreraRepository();

        eR.insertarDesdeCSV("src/main/resources/estudiantes.csv");
        cR.insertarDesdeCSV("src/main/resources/carreras.csv");
        eCR.insertarDesdeCSV("src/main/resources/estudianteCarrera.csv");


        Estudiante e1 = new Estudiante("Agustin","La Battaglia",33,36798366,"masculino","necochea",534897);
        eR.insertar(e1);
        eCR.insertar(44708235,1,2022);

        List<Estudiante> estudiantes = eR.estudiantesPorEdad();

        for(Estudiante e : estudiantes){
            System.out.println(e);
        }

    }
}