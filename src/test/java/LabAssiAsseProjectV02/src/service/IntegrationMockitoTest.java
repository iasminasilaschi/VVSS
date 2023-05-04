package LabAssiAsseProjectV02.src.service;

import LabAssiAsseProjectV02.src.domain.Nota;
import LabAssiAsseProjectV02.src.domain.Student;
import LabAssiAsseProjectV02.src.domain.Tema;
import LabAssiAsseProjectV02.src.repository.*;
import LabAssiAsseProjectV02.src.validation.NotaValidator;
import LabAssiAsseProjectV02.src.validation.StudentValidator;
import LabAssiAsseProjectV02.src.validation.TemaValidator;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class IntegrationMockitoTest {


    private static Service service;

    @Mock
    StudentXMLRepo studentXMLRepository = mock(StudentXMLRepo.class);

    @Mock
    TemaXMLRepo temaXMLRepository = mock(TemaXMLRepo.class);

    @Mock
    NotaXMLRepo notaXMLRepository = mock(NotaXMLRepo.class);
//
//    @Mock
//    StudentFileRepository studentFileRepository;
//
//    @Mock
//    TemaFileRepository temaFileRepository;
//
//    @Mock
//    NotaFileRepository notaFileRepository;

    @Mock
    NotaValidator notaValidator = mock(NotaValidator.class);;

    @Mock
    StudentValidator studentValidator= mock(StudentValidator.class);;

    @Mock
    TemaValidator temaValidator = mock(TemaValidator.class);

    @Before
    public void setUp() {
//        StudentValidator studentValidator = new StudentValidator();
//        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/main/java/LabAssiAsseProjectV02/fisiere/Studenti.xml";
        String filenameTema = "src/main/java/LabAssiAsseProjectV02/fisiere/Teme.xml";
        String filenameNota = "src/main/java/LabAssiAsseProjectV02/fisiere/Note.xml";

        Student student = new Student("id1", "name", 1, "email@mail.com");
        Tema tema = new Tema("nr1", "tralala", 14, 1);
        Nota nota = new Nota("n1", "id1", "nr1", 8, LocalDate.of(2020,9,11));


//        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
//        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
//        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
//        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

        when(studentXMLRepository.findOne("id1")).thenReturn(student);
        doNothing().when(this.studentValidator).validate(student);
        when(studentXMLRepository.save(student)).thenReturn(student);
        when(temaXMLRepository.findOne("nr1")).thenReturn(tema);
        doNothing().when(this.temaValidator).validate(tema);
        when(temaXMLRepository.save(tema)).thenReturn(tema);
        when(notaXMLRepository.findOne("n1")).thenReturn(nota);
        doNothing().when(notaValidator).validate(nota);
        when(notaXMLRepository.save(nota)).thenReturn(nota);

        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    }

    @Test
    public void testAddStudentMockito() {
        Student student = new Student("id1", "name", 1, "email@mail.com");
        service.addStudent(student);
        Student foundStudent = service.findStudent("id1");
        Assert.assertEquals(foundStudent.getID(), student.getID());
        Assert.assertEquals(foundStudent.getNume(), student.getNume());
        Assert.assertEquals(foundStudent.getGrupa(), student.getGrupa());
        Assert.assertEquals(foundStudent.getEmail(), student.getEmail());
    }

    @Test
    public void testAddAssignmentMockito() {
        Tema tema = new Tema("nr1", "tralala", 14, 1);

        service.addTema(tema);
        Tema foundTema = service.findTema("nr1");
        Assert.assertEquals(foundTema.getID(), tema.getID());
        Assert.assertEquals(foundTema.getDescriere(), tema.getDescriere());
        Assert.assertEquals(foundTema.getDeadline(), tema.getDeadline());
        Assert.assertEquals(foundTema.getPrimire(), tema.getPrimire());
    }

    @Test
    public void testAddGradeMockito() {
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
    public void testIntegrationMockito() {
        testAddStudentMockito();
        testAddAssignmentMockito();
        Nota nota = new Nota("n1", "id1", "nr1", 8,  LocalDate.of(2020,9,11));
        service.addNota(nota, "Great job!");
        Nota foundNota = service.findNota("n1");
        Assert.assertEquals(foundNota.getID(), nota.getID());
        Assert.assertEquals(foundNota.getIdStudent(), nota.getIdStudent());
        Assert.assertEquals(foundNota.getIdTema(), nota.getIdTema());
        Assert.assertEquals(foundNota.getNota(), nota.getNota(), 0.0);
        Assert.assertEquals(foundNota.getData(), nota.getData());
    }

//    @After
//    public void cleanup() {
//
//        if (service.findStudent("id1") != null)
//            service.deleteStudent("id1");
//        if (service.findTema("nr1") != null)
//            service.deleteTema("nr1");
//        if (service.findNota("n1") != null)
//            service.deleteNota("n1");
//    }
}
