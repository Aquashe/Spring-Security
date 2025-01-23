package com.thomas.Spring_Security.controller;

import com.thomas.Spring_Security.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    List<Student> students = new ArrayList<>(List.of(
            new Student(1,"ramu","java"),
            new Student(2,"Hari","python"),
            new Student(3,"ramanan",".Net")
    ));


    @GetMapping("csrf-token")
    public ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest request){
        return new ResponseEntity<>((CsrfToken) request.getAttribute("_csrf"),HttpStatus.OK);
    }

    @GetMapping("students")
    public ResponseEntity<List<Student>> getStudents(){
        return  new ResponseEntity<>(students , HttpStatus.OK);
    }

    @GetMapping("students/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") int studentId){
        for(Student stud : students){
            if(stud.getId() == studentId)
                return new ResponseEntity<>(stud,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("students")
    public ResponseEntity<?>addStudent(@RequestBody Student student){
        return new ResponseEntity<>(students.add(student),HttpStatus.CREATED);

    }
}
