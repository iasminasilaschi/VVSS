package LabAssiAsseProjectV02.src.service;

import LabAssiAsseProjectV02.src.domain.Student;
import LabAssiAsseProjectV02.src.domain.Tema;
import LabAssiAsseProjectV02.src.repository.NotaXMLRepo;
import LabAssiAsseProjectV02.src.repository.StudentXMLRepo;
import LabAssiAsseProjectV02.src.repository.TemaXMLRepo;
import LabAssiAsseProjectV02.src.validation.NotaValidator;
import LabAssiAsseProjectV02.src.validation.StudentValidator;
import LabAssiAsseProjectV02.src.validation.TemaValidator;
import LabAssiAsseProjectV02.src.validation.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ServiceTest {

    private Service service;

    @Before
    public void setUp() {
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
    public void testAddValidStudent() {
        Student student = new Student("id1", "name", 1, "email@mail.com");
        service.addStudent(student);
        Student foundStudent = service.findStudent("id1");
        Assert.assertEquals(foundStudent.getID(), student.getID());
        Assert.assertEquals(foundStudent.getNume(), student.getNume());
        Assert.assertEquals(foundStudent.getGrupa(), student.getGrupa());
        Assert.assertEquals(foundStudent.getEmail(), student.getEmail());
    }

    @Test
    public void testAddInvalidStudent() {
        Student student = new Student("", "", -1, "");

        try {
            service.addStudent(student);
        } catch (ValidationException ex) {
            Assert.assertEquals(ex.getMessage(), "Id incorect!");
        }

    }

    @Test
    public void testAddInvalidIDStudent(){
        Student student = new Student("", "Student name", 3, "mail@mail.com");

        try {
            service.addStudent(student);
        } catch (ValidationException ex) {
            Assert.assertEquals(ex.getMessage(), "Id incorect!");
        }
    }

    @Test
    public void testAddInvalidNameStudent(){
        Student student = new Student("123", "", 3, "mail@mail.com");

        try {
            service.addStudent(student);
        } catch (ValidationException ex) {
            Assert.assertEquals(ex.getMessage(), "Nume incorect!");
        }
    }

    @Test
    public void testAddInvalidEmailStudent(){
        Student student = new Student("123", "Student name", 3, "mail@mail.com");
        try {
            service.addStudent(student);
        } catch (ValidationException ex) {
            Assert.assertEquals(ex.getMessage(), "Email incorect!");
        }
    }

    @Test
    public void testAddInvalidGroupStudent(){
        Student student = new Student("123", "Student name", -12, "mail@mail.com");
        try {
            service.addStudent(student);
        } catch (ValidationException ex) {
            Assert.assertEquals(ex.getMessage(), "Grupa incorecta!");
        }
    }

    @Test
    public void testAddValidTema() {
        Tema tema = new Tema("1","tralala",3,5);

        service.addTema(tema);
        Tema foundTema = service.findTema("nr1");
        Assert.assertEquals(foundTema.getID(), tema.getID());
        Assert.assertEquals(foundTema.getDescriere(), tema.getDescriere());
        Assert.assertEquals(foundTema.getDeadline(), tema.getDeadline());
        Assert.assertEquals(foundTema.getPrimire(), tema.getPrimire());
    }

    @Test
    public void testAddInvalidIDTema() {
        Tema tema = new Tema("","tralala",3,5);
        try {
            service.addTema(tema);
        } catch (ValidationException ex) {
            Assert.assertEquals(ex.getMessage(), "Numar tema invalid!");
        }

    }

}
