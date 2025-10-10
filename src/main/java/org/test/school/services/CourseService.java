package org.test.school.services;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.test.school.dto.CourseRequest;
import org.test.school.dto.ResponseWrapper;
import org.test.school.repositories.models.Course;
import org.test.school.repositories.repo.CourseRepository;
import org.test.school.utils.exceptions.APIException;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class CourseService {

    @Inject
    CourseRepository courseRepository;

    @Transactional
    public ResponseWrapper<Course> addCourse(CourseRequest courseRequest) throws APIException {
        try{
            Course course=new Course();
            course.setName(courseRequest.getName());

            course.setStartDate(Date.valueOf( dateConvert(courseRequest.getStartDate()).toLocalDate()));
            course.setEndDate(Date.valueOf(dateConvert(courseRequest.getEndDate()).toLocalDate()));
            course.setCode(courseRequest.getCode());
            courseRepository.persist(course);
            ResponseWrapper<Course> responseWrapper = new ResponseWrapper<>();
            responseWrapper.setData(course);
            responseWrapper.setMessage("Successfully added course");
            return responseWrapper;
        }catch (ConstraintViolationException ex){
            if(ex.getErrorMessage().contains("code")){
                throw new APIException("Course code must be unique",400);
            }else if(ex.getErrorMessage().contains("name")){
                throw new APIException("Course name must be unique",400);
            }
            else{
                throw new APIException("Interal Server Error",500);
            }

        }

        catch (Exception ex){
             throw new APIException("Internal Server Error",500);
            
        }

    }

    private static  LocalDateTime dateConvert(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString.replace(" ", "T"));
    }

    public ResponseWrapper<List<Course>> getAllCourses(int page, int size) throws APIException {
        try {
            PanacheQuery<Course> courses= courseRepository.findAll();
            ResponseWrapper<List<Course>> responseWrapper = new ResponseWrapper<>();
            if(size>=courses.count()){
                responseWrapper.setData(courses.list());
                responseWrapper.setMessage("Successfully retrieve data");
                responseWrapper.setTotalPage(String.valueOf(1));
                responseWrapper.setTotalRecords(String.valueOf(courses.count()));
            }else{
                 responseWrapper.setMessage("Successfully retrieve data");
                 responseWrapper.setData(courses.page(Page.of(page-1,size)).list());
                 responseWrapper.setTotalPage(String.valueOf(courses.pageCount()));
                 responseWrapper.setTotalRecords(String.valueOf(courses.count()));
            }


            return responseWrapper;
        }catch (Exception e){
              throw new APIException(e.getMessage(),400);
        }
    }

    public ResponseWrapper<Course> getCourseById(int id) throws APIException {
        try{
            Course course = courseRepository.findById(Long.valueOf(id));
            if(Objects.nonNull(course)){
                ResponseWrapper<Course> responseWrapper = new ResponseWrapper<>();
                responseWrapper.setData(course);
                responseWrapper.setMessage("Successfully retrieve data");
                return responseWrapper;
            }else {
                throw new APIException("Course not found",400);
            }


        }catch (APIException ex){
             throw ex;
        }
        catch (Exception ex)
        {
            throw new APIException(ex.getMessage(),500);
        }
    }
}
