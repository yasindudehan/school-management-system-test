package org.test.school.services;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.test.school.dto.ResponseWrapper;
import org.test.school.dto.SubjectRequest;
import org.test.school.repositories.models.Subject;
import org.test.school.repositories.repo.CourseRepository;
import org.test.school.repositories.repo.LecturerRepository;
import org.test.school.repositories.repo.SubjectRepository;
import org.test.school.utils.exceptions.APIException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class SubjectService {

    @Inject
    SubjectRepository subjectRepository;
    @Inject
    CourseRepository courseRepository;
    @Inject
    LecturerRepository lectureRepository;
    public ResponseWrapper<Subject> addSubject( SubjectRequest subjectRequest) throws APIException {
        try{
            Subject subject=new Subject();
            subject.setName(subjectRequest.getName());
            subject.setCode(subjectRequest.getCode());
            subject.setCourse(courseRepository.findById(subjectRequest.getCourseId()));
            subject.setLecturer(lectureRepository.findById(subjectRequest.getLectureId()));
            subject.setCredit(subjectRequest.getCredit());
            subjectRepository.persist(subject);
            ResponseWrapper<Subject> responseWrapper = new ResponseWrapper<>();
            responseWrapper.setData(subject);
            responseWrapper.setMessage("Successfully added subject");
            return responseWrapper;
        }catch (Exception ex){
            throw new APIException(ex.getMessage(),500);
        }
    }

    public ResponseWrapper<List<Subject>> getAllSubjects(int page, int size) throws APIException {
        try{
             PanacheQuery<Subject> subjects= subjectRepository.findAll();
            ResponseWrapper<List<Subject>> responseWrapper = new ResponseWrapper<>();
             if(size>=subjects.count()){

                 responseWrapper.setData(subjects.list());
                 responseWrapper.setMessage("Successfully retrieve data");
                 responseWrapper.setTotalPage(String.valueOf(1));
                 responseWrapper.setTotalRecords(String.valueOf(subjects.count()));
                 return responseWrapper;


             }else{
                responseWrapper.setData(subjects.page(Page.of(page-1,size)).list());
                responseWrapper.setMessage("Successfully retrieve data");
                responseWrapper.setTotalPage(String.valueOf(subjects.pageCount()));
                responseWrapper.setTotalRecords(String.valueOf(subjects.count()));
                return responseWrapper;
             }
        }catch (Exception ex)
        {
            throw new APIException(ex.getMessage(),500);
        }
    }

    public ResponseWrapper<Subject> getSubjectById(int id) throws APIException {
        try{
            Subject subject = subjectRepository.findById(Long.valueOf(id));
            if(Objects.nonNull(subject)){
                ResponseWrapper<Subject> responseWrapper = new ResponseWrapper<>();
                responseWrapper.setData(subject);
                responseWrapper.setMessage("Successfully retrieve data");
                return responseWrapper;
            }else {
                throw new APIException("Subject not found",400);
            }

        }catch (APIException ex){
            throw ex;
        }catch (Exception ex)
        {
            throw new APIException("Internal Server Error",500);
        }
    }
}
