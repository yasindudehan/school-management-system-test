package org.test.school.repositories.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.test.school.repositories.models.Subject;

@ApplicationScoped
public class SubjectRepository implements PanacheRepository<Subject> {
}
