package ssvv.example;


import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import ssvv.example.domain.Student;
import ssvv.example.repository.NotaXMLRepo;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.repository.TemaXMLRepo;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.ValidationException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    public static Service service;

    @BeforeAll
    public static void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/fisiere/Studenti.xml";
        String filenameTema = "src/fisiere/Teme.xml";
        String filenameNota = "src/fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addStudent_ValidOperation() {
        String idStudent = "test";
        String numeStudent = "Ana";
        int grupa = 934;
        String email = "anaaa@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assertFalse(true);
        }

        assert(service.findStudent(idStudent) != null);
    }

    @Test
    public void addStudent_ErrorGroup_ThrowError() {
        String idStudent = "90";
        String numeStudent = "Dan";
        int grupa = -9;
        String email = "dani@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
            assert(false);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assert(true);
        }
    }

}