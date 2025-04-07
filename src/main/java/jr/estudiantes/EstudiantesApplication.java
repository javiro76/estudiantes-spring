package jr.estudiantes;

import jr.estudiantes.modelo.Estudiante;
import jr.estudiantes.servicio.EstudianteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class EstudiantesApplication implements CommandLineRunner {

    @Autowired
    private EstudianteServicio estudianteServicio;

    private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);

    String nl = System.lineSeparator();

    public static void main(String[] args) {
        logger.info("Iniciando la aplicación...");
        //levanta fabrica de spring
        SpringApplication.run(EstudiantesApplication.class, args);
        logger.info("Aplicacion finalizada!");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(nl + "Ejecutando metododo run de spring..." + nl);
        var salir = false;
        var consola = new Scanner(System.in);

        while (!salir) {
                showMenu();
                salir = executeOptions(consola);
                logger.info(nl);
        }// fin de while
    }


    private void showMenu() {
        logger.info(nl);
        logger.info("""
                *** Menú Aplicación Estudiantes ***
                1. Listar Estudiantes
                2. Buscar Estudiante
                3. Agregar Estudiante
                4. Modificar Estudiante
                5. Eliminar Estudiante
                6. Salir
                Elige una opción:""");
    }//fin de showMenu

    private boolean executeOptions(Scanner consola) {
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;

        switch (opcion) {
            case 1 -> {//Listar Estudiantes
                logger.info(nl + "Listado de Estudiantes: " + nl);
                List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
                estudiantes.forEach((estudiante -> logger.info(estudiante.toString() + nl )));
            }

            case 2 -> {// buscar estudiante por id
                logger.info("Introduce el id estudiante a buscar: ");
                int idEstudiante = Integer.parseInt(consola.nextLine());
                Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if(estudiante != null )
                    logger.info("Estudiante encontrado: " + estudiante + nl);
                else
                    logger.info("Estudiante no encontrado con id: " + idEstudiante + nl );
            }
//
            case 3 -> {// Agregar estudiante
                logger.info("Agregar estudiante: " + nl );
                logger.info("Nombre: ");
                String nombreEstudiante = consola.nextLine();
                logger.info("Apellido: ");
                String apellidoEstudiante = consola.nextLine();
                logger.info("Telefono: ");
                String telefonoEstudiante = consola.nextLine();
                logger.info("Email: ");
                String emailEstudiante = consola.nextLine();
                //crear el objeto estudiante sin id
                var estudiante = new Estudiante();
                estudiante.setNombre(nombreEstudiante);
                estudiante.setApellido(apellidoEstudiante);
                estudiante.setTelefono(telefonoEstudiante);
                estudiante.setEmail(emailEstudiante);
                estudianteServicio.guardarEstudiante(estudiante);
                logger.info("Estudiante agregado: " + estudiante + nl);

            }
//
            case 4 -> {//modificar estudiante
                logger.info("Modificar estudiante " + nl);
                logger.info("Introduce el id del estudiante a modificar: ");
                int idEstudiante = Integer.parseInt(consola.nextLine());
                //buscamos estudiante a modificar
                Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if(estudiante != null ) {
                    logger.info("Introduce el nombre del nuevo estudiante: ");
                    String nombreEstudiante = consola.nextLine();
                    logger.info("Introduce el apellido del nuevo estudiante: ");
                    String apellidoEstudiante = consola.nextLine();
                    logger.info("Introduce el telefono del nuevo estudiante: ");
                    String telefonoEstudiante = consola.nextLine();
                    logger.info("Introduce el email del nuevo estudiante: ");
                    String emailEstudiante = consola.nextLine();

                    estudiante.setNombre(nombreEstudiante);
                    estudiante.setApellido(apellidoEstudiante);
                    estudiante.setTelefono(telefonoEstudiante);
                    estudiante.setEmail(emailEstudiante);
                    estudianteServicio.guardarEstudiante(estudiante);
                    logger.info("Estudiante modificado: " + estudiante + nl);
                }
                else
                    logger.info("Estudiante no encontrado con id: " + idEstudiante + nl );


            }
//            case 5 -> {
//                System.out.println("Introduce el id del estudiante a eliminar: ");
//                int idEstudiante = Integer.parseInt(consola.nextLine());
//                var estudianteEliminar = new Estudiante(idEstudiante);
//                var eliminado = estudianteDAO.eliminarEstudiante(estudianteEliminar);
//                if (eliminado) {
//                    System.out.println("Estudiante eliminado: " + estudianteEliminar);
//                } else {
//                    System.out.println("No se eliminó estudiante: " + estudianteEliminar);
//                }
//            }
//            case 6 -> {
//                System.out.println("Hasta pronto!");
//                salir = true;
//            }
//            default -> System.out.println("Opción no recnocida: " + opcion);
        }//fin switch
        return salir;
    }//fin de executeOptions
}
