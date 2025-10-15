package org.test.school.services;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.test.school.dto.ResponseWrapper;
import org.test.school.dto.StudentRequest;
import org.test.school.repositories.models.Course;
import org.test.school.repositories.models.Student;
import org.test.school.repositories.repo.CourseRepository;
import org.test.school.repositories.repo.StudentRepository;
import org.test.school.utils.exceptions.APIException;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
@ApplicationScoped
public class StudentService {


    @Inject
    StudentRepository studentRepository;
    @Inject
    CourseRepository courseRepository;

    public ResponseWrapper<List<Student>> getAllStudents(int page,int size) throws APIException {
        try {
            PanacheQuery<Student> students= studentRepository.findAll();
            ResponseWrapper<List<Student>> responseWrapper = new ResponseWrapper<>();
            if(size>=students.count()){
                responseWrapper.setData(students.list());
                responseWrapper.setMessage("Successfully retrieve data");
                responseWrapper.setTotalPage(String.valueOf(1));
                responseWrapper.setTotalRecords(String.valueOf(students.count()));
            }else{
                 responseWrapper.setMessage("Successfully retrieve data");
                 responseWrapper.setData(students.page(Page.of(page-1,size)).list());
                 responseWrapper.setTotalPage(String.valueOf(students.pageCount()));
                 responseWrapper.setTotalRecords(String.valueOf(students.count()));
            }


            return responseWrapper;
        }catch (Exception e){
              throw new APIException(e.getMessage(),400);
        }
    }

    @Transactional
    public ResponseWrapper<Student> registerStudent(StudentRequest studentRequest) throws APIException {
                try{
                       Student student = new Student();
                       student.setName(studentRequest.getName());
                       student.setEmail(studentRequest.getEmail());
                       student.setPhone(studentRequest.getPhone());
                       student.setAddress(studentRequest.getAddress());
                       student.setDob(Date.valueOf(studentRequest.getDob()));
                       student.setEnrollDate(new Date(System.currentTimeMillis()));
                       student.setCourses(courseRepository.findByIds(studentRequest.getCourseIds()));
                       studentRepository.persist(student);
                       ResponseWrapper<Student> responseWrapper = new ResponseWrapper<>();
                       responseWrapper.setData(student);
                       responseWrapper.setMessage("successfully registered");
                       return responseWrapper;
                }catch (ConstraintViolationException ex) {
                    if (ex.getMessage().contains("duplicate key value violates unique constraint")) {
                        throw new APIException("Email already exists", 400);
                    } else {
                        throw new APIException("Internal Server Error", 500);
                    }
                }catch (Exception ex){
                       throw new APIException(ex.getMessage(),500);
                }

    }

    public ResponseWrapper<Student> getStudentById(int id) throws APIException {
        try{
            Student student = studentRepository.findById((long) id);
            if(Objects.nonNull(student)){
                ResponseWrapper<Student> responseWrapper = new ResponseWrapper<>();
                responseWrapper.setData(student);
                responseWrapper.setMessage("Successfully retrieve data");
                return responseWrapper;
            }else {
                throw new APIException("Student not found",400);
            }


        }catch (APIException ex){
             throw ex;
        }
        catch (Exception ex)
        {
            throw new APIException(ex.getMessage(),500);
        }
    }

    public ResponseWrapper<Student> enrollStudent(Long studentID, Long courseID) throws APIException {
        try{
            Student student = studentRepository.findById(studentID);
            if(Objects.isNull(student)){
                throw new APIException("student is not found",400);
            }
            Course course = courseRepository.findById(courseID);
            if(Objects.isNull(course)){
                throw new APIException("course is not found",400);
            }
            student.getCourses().add(course);
            studentRepository.persist(student);
            ResponseWrapper<Student> responseWrapper = new ResponseWrapper<>();
            responseWrapper.setData(student);
            responseWrapper.setMessage("Successfully enrolled student");
            return responseWrapper;
        }catch (APIException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new APIException("Internal Server Error",500);
        }
    }
}

