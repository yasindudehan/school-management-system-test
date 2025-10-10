package org.test.school.repositories.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     @Column(name="name", nullable = false)
     private String name;
     @Column(unique = true,nullable = false)
     private String code;
     private String credit;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="course_id")
     private Course course;
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="lecturer_id")
     private Lecturer lecturer;

     @OneToMany(mappedBy = "subject")
     private Set<Marks> marks;

}
