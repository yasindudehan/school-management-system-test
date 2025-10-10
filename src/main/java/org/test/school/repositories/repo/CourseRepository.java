package org.test.school.repositories.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.test.school.repositories.models.Course;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@ApplicationScoped
public class CourseRepository implements PanacheRepository<Course> {

       public Set<Course> findByIds(List<Long> ids){
           return find("id in ?1", ids).list().stream().collect(Collectors.toSet());
       }
}
