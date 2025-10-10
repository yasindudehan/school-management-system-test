package org.test.school.repositories.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false,unique = true)
    private String name;
    @Column
    private Date startDate;
    private Date endDate;

    @Column(name="code", nullable = false,unique = true)
    private String code;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<Student> students;

}
