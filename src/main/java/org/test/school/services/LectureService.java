package org.test.school.services;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.test.school.dto.LectureRequest;
import org.test.school.dto.ResponseWrapper;
import org.test.school.repositories.models.Lecturer;
import org.test.school.repositories.repo.LecturerRepository;
import org.test.school.utils.exceptions.APIException;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class LectureService {


     @Inject
     LecturerRepository lecturerRepository;

     @Transactional
    public ResponseWrapper<Lecturer> addLecture( LectureRequest lectureRequest) throws APIException {
        try{
                Lecturer lecturer=new Lecturer();
                lecturer.setName(lectureRequest.getName());
                lecturer.setEmail(lectureRequest.getEmail());
                lecturer.setPhone(lectureRequest.getPhoneNumber());
                lecturer.setJoinDate(Date.valueOf(lectureRequest.getJoinDate()));
                lecturerRepository.persist(lecturer);
                ResponseWrapper<Lecturer> responseWrapper = new ResponseWrapper<>();
                responseWrapper.setData(lecturer);
                responseWrapper.setMessage("Successfully added lecture");
                return responseWrapper;
        }catch (ConstraintViolationException ex){
            if(ex.getMessage().contains("duplicate key value violates unique constraint")){
                throw new APIException("Email already exists",400);
            }else{
                throw new APIException("Internal Server Error",500);
            }
        }
        catch (Exception ex){
            throw new APIException("Internal Server Error",500);
        }
    }

    public ResponseWrapper<List<Lecturer>> getAllLectures(int page, int size) throws APIException {
         try{
             PanacheQuery<Lecturer> lectures= lecturerRepository.findAll();
             ResponseWrapper<List<Lecturer>> responseWrapper = new ResponseWrapper<>();
             if(size>=lectures.count()){
                 responseWrapper.setData(lectures.list());
                 responseWrapper.setMessage("Successfully retrieve data");
                 responseWrapper.setTotalPage(String.valueOf(1));
                 responseWrapper.setTotalRecords(String.valueOf(lectures.count()));
             }else{
                  responseWrapper.setMessage("Successfully retrieve data");
                  responseWrapper.setData(lectures.page(Page.of(page-1,size)).list());
                  responseWrapper.setTotalPage(String.valueOf(lectures.pageCount()));
                  responseWrapper.setTotalRecords(String.valueOf(lectures.count()));
             }
             return responseWrapper;
         }catch (Exception ex){
             throw new APIException("Internal Server Error",500);
         }
    }

    public ResponseWrapper<Lecturer> getLectureById(int id) throws APIException {
         try{
             Lecturer lecturer= lecturerRepository.findById(Long.valueOf(id));
             if(Objects.nonNull(lecturer)) {
                 ResponseWrapper<Lecturer> responseWrapper = new ResponseWrapper<>();
                 responseWrapper.setData(lecturer);
                 responseWrapper.setMessage("Successfully retrieve data");
                 return responseWrapper;
             }else {
                 throw new APIException("Lecture not found",400);
             }
         }catch (Exception ex){
             throw new APIException("Internal Server Error",500);
         }
    }
}
