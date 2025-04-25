package TpIntegrador;

import TpIntegrador.Factory.JPAUtil;
import TpIntegrador.dto.CarreraPorInscriptosDTO;
import TpIntegrador.dto.EstudianteDTO;
import TpIntegrador.dto.ReporteDTO;
import TpIntegrador.modelo.Carrera;
import TpIntegrador.modelo.Estudiante;
import TpIntegrador.modelo.Estudiante_Carrera;
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
        

//        System.out.println("a) dar de alta un estudiante");
//        Estudiante e1 = new Estudiante("Agustin","La Battaglia",33,36798366,"masculino","necochea",534897);
//        eR.insertar(e1);



//        System.out.println("b) matricular un estudiante en una carrera");
//        eCR.insertar(36798366,1,2022);


        System.out.println("//////////////////////////");
        System.out.println("c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple -> Edad");

        for(EstudianteDTO cPI: eR.estudiantesPorEdad())
            System.out.println(cPI);

        System.out.println("//////////////////////////");
        System.out.println("d) recuperar un estudiante, en base a su número de libreta universitaria.");

        System.out.println(eR.buscarPorLu(72976));

        System.out.println("//////////////////////////");
        System.out.println("e) recuperar todos los estudiantes, en base a su género.");


        for(EstudianteDTO cPI: eR.buscarPorGenero("male"))
            System.out.println(cPI);

        System.out.println("//////////////////////////");
        System.out.println("f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.");

        for(CarreraPorInscriptosDTO cPI: eCR.ordenarPorInscriptos())
        System.out.println(cPI);

        System.out.println("//////////////////////////");
        System.out.println("g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.");

        for(EstudianteDTO cPI: eR.obtenerPorCiudad("TUDAI","Rauch"))
            System.out.println(cPI);

        System.out.println("//////////////////////////");
        System.out.println("3) Generar un reporte de las carreras, que para cada carrera incluya información de los\n" +
                "inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar\n" +
                "los años de manera cronológica.");
        List<ReporteDTO> reportes = cR.obtenerInforme();

        for(ReporteDTO rT : reportes){
            System.out.println(rT);
        }


    }
}