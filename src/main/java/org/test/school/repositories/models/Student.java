package org.test.school.repositories.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="full_name", nullable = false)
    private String name;
    @Column(name="email", nullable = false,unique = true)
    private String email;
    @Column(name="date_of_birth")
    private Date dob;
    @Column(name="enroll_date")
    private Date enrollDate;
    private String phone;
    private String address;
    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    private Set<Course> courses;

    @OneToMany(mappedBy = "student")
    private Set<Marks> marks;
}
