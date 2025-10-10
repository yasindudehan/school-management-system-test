package org.test.school.repositories.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name="lecturers")
@Getter
@Setter
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="full_name", nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    private String phone;
    @Column(name="join_date", nullable = false)
    private Date joinDate;
}
