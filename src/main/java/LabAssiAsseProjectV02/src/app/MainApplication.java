package LabAssiAsseProjectV02.src.app;


import LabAssiAsseProjectV02.src.repository.NotaXMLRepo;
import LabAssiAsseProjectV02.src.repository.StudentXMLRepo;
import LabAssiAsseProjectV02.src.repository.TemaXMLRepo;
import LabAssiAsseProjectV02.src.service.Service;
import LabAssiAsseProjectV02.src.validation.NotaValidator;
import LabAssiAsseProjectV02.src.validation.StudentValidator;
import LabAssiAsseProjectV02.src.validation.TemaValidator;
import LabAssiAsseProjectV02.src.view.UI;


public class MainApplication {

    public static void main(String[] args) {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/main/java/LabAssiAsseProjectV02/fisiere/Studenti.xml";
        String filenameTema = "src/main/java/LabAssiAsseProjectV02/fisiere/Teme.xml";
        String filenameNota = "src/main/java/LabAssiAsseProjectV02/fisiere/Note.xml";

        //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        UI ui = new UI(service);
        ui.run();
    }

}
