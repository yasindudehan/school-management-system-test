package org.test.school.repositories.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.test.school.repositories.models.Lecturer;

@ApplicationScoped
public class LecturerRepository implements PanacheRepository<Lecturer> {
}
