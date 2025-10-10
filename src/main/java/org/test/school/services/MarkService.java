package org.test.school.services;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.test.school.dto.MarkRequest;
import org.test.school.dto.ResponseWrapper;
import org.test.school.repositories.models.Marks;
import org.test.school.repositories.repo.MarkRepository;
import org.test.school.repositories.repo.StudentRepository;
import org.test.school.repositories.repo.SubjectRepository;
import org.test.school.utils.exceptions.APIException;

import java.util.List;

@ApplicationScoped
public class MarkService {

    @Inject
    MarkRepository markRepository;
    @Inject
    StudentRepository studentRepository;
    @Inject
    SubjectRepository subjectRepository;

    public ResponseWrapper<Marks> addMark(MarkRequest markRequest) throws APIException {
        try{
            Marks marks = new Marks();
            marks.setStudent(studentRepository.findById(Long.valueOf(markRequest.getStudentId())));
            marks.setSubject(subjectRepository.findById(Long.valueOf(markRequest.getSubjectId())));
            marks.setMarks(markRequest.getMark());
            markRepository.persist(marks);
            ResponseWrapper<Marks> responseWrapper=new ResponseWrapper<>();
            responseWrapper.setData(marks);
            responseWrapper.setMessage("Successfully added mark");
            return responseWrapper;
        }catch (Exception e){
            throw new APIException(e.getMessage(),500);
        }
    }


    public ResponseWrapper<List<Marks>> getAllMarks(int page, int size) throws APIException {
        try{
            PanacheQuery<Marks> marks= markRepository.findAll();
            ResponseWrapper<List<Marks>> responseWrapper = new ResponseWrapper<>();
            if(size>=marks.count()){
                responseWrapper.setData(marks.list());
                responseWrapper.setMessage("Successfully retrieve data");
                responseWrapper.setTotalPage(String.valueOf(1));
                responseWrapper.setTotalRecords(String.valueOf(marks.count()));
            }else{
                 responseWrapper.setMessage("Successfully retrieve data");
                 responseWrapper.setData(marks.page(Page.of(page-1,size)).list());
                 responseWrapper.setTotalPage(String.valueOf(marks.pageCount()));
                 responseWrapper.setTotalRecords(String.valueOf(marks.count()));
            }

            return responseWrapper;
        }catch (Exception e){
              throw new APIException(e.getMessage(),400);
        }
    }
}
