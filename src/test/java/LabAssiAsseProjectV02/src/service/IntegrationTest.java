package LabAssiAsseProjectV02.src.service;

import LabAssiAsseProjectV02.src.domain.Nota;
import LabAssiAsseProjectV02.src.domain.Student;
import LabAssiAsseProjectV02.src.domain.Tema;
import LabAssiAsseProjectV02.src.repository.NotaXMLRepo;
import LabAssiAsseProjectV02.src.repository.StudentXMLRepo;
import LabAssiAsseProjectV02.src.repository.TemaXMLRepo;
import LabAssiAsseProjectV02.src.validation.NotaValidator;
import LabAssiAsseProjectV02.src.validation.StudentValidator;
import LabAssiAsseProjectV02.src.validation.TemaValidator;
import LabAssiAsseProjectV02.src.validation.ValidationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

@RunWith(JUnit4.class)
public class IntegrationTest {

    private static Service service;

    @BeforeClass
    public static void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/main/java/LabAssiAsseProjectV02/fisiere/Studenti.xml";
        String filenameTema = "src/main/java/LabAssiAsseProjectV02/fisiere/Teme.xml";
        String filenameNota = "src/main/java/LabAssiAsseProjectV02/fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    }

    @Test
    public void testAddStudent() {
        Student student = new Student("id1", "name", 1, "email@mail.com");
        service.addStudent(student);
        Student foundStudent = service.findStudent("id1");
        Assert.assertEquals(foundStudent.getID(), student.getID());
        Assert.assertEquals(foundStudent.getNume(), student.getNume());
        Assert.assertEquals(foundStudent.getGrupa(), student.getGrupa());
        Assert.assertEquals(foundStudent.getEmail(), student.getEmail());
    }

    @Test
    public void testAddAssignment() {
        Tema tema = new Tema("nr1", "tralala", 14, 1);

        service.addTema(tema);
        Tema foundTema = service.findTema("nr1");
        Assert.assertEquals(foundTema.getID(), tema.getID());
        Assert.assertEquals(foundTema.getDescriere(), tema.getDescriere());
        Assert.assertEquals(foundTema.getDeadline(), tema.getDeadline());
        Assert.assertEquals(foundTema.getPrimire(), tema.getPrimire());
    }

    @Test
    public void testAddGrade() {
        Student student = new Student("id1", "name", 1, "email@mail.com");
        service.addStudent(student);

        Tema tema = new Tema("nr1", "tralala", 14, 1);

        service.addTema(tema);

        Nota nota = new Nota("n1", "id1", "nr1", 8, LocalDate.of(2020,9,11));
        service.addNota(nota, "Great job!");
        Nota foundNota = service.findNota("n1");
        Assert.assertEquals(foundNota.getID(), nota.getID());
        Assert.assertEquals(foundNota.getIdStudent(), nota.getIdStudent());
        Assert.assertEquals(foundNota.getIdTema(), nota.getIdTema());
        Assert.assertEquals(foundNota.getNota(), nota.getNota(), 0.0);
        Assert.assertEquals(foundNota.getData(), nota.getData());


    }

    @Test
    public void testIntegration() {
        testAddStudent();
        testAddAssignment();
        Nota nota = new Nota("n1", "id1", "nr1", 8,  LocalDate.of(2020,9,11));
        service.addNota(nota, "Great job!");
        Nota foundNota = service.findNota("n1");
        Assert.assertEquals(foundNota.getID(), nota.getID());
        Assert.assertEquals(foundNota.getIdStudent(), nota.getIdStudent());
        Assert.assertEquals(foundNota.getIdTema(), nota.getIdTema());
        Assert.assertEquals(foundNota.getNota(), nota.getNota(), 0.0);
        Assert.assertEquals(foundNota.getData(), nota.getData());
    }

    @After
    public void cleanup() {

        if (service.findStudent("id1") != null)
            service.deleteStudent("id1");
        if (service.findTema("nr1") != null)
            service.deleteTema("nr1");
        if (service.findNota("n1") != null)
            service.deleteNota("n1");
    }

}
