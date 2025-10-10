package org.test.school.repositories.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.test.school.repositories.models.Marks;

@ApplicationScoped
public class MarkRepository implements PanacheRepository<Marks> {
}
