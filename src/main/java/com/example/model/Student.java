package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
@NamedQueries({
        @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
        @NamedQuery(name = "Student.findByMajor", query = "SELECT s FROM Student s WHERE s.major = :major"),
        @NamedQuery(name = "Student.findByAgeRange", query = "SELECT s FROM Student s WHERE s.age BETWEEN :minAge AND :maxAge")
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String major;

    public Student() {}

    public Student(String name, int age, String major) {
        this.name = name;
        this.age = age;
        this.major = major;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
}
