package com.example.milestone.models;

//import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

//@Entity
//@Table(name = "task")
public class Tasks {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)

    private Long id;
    private String title;
    private String description;
    private String status;
}
