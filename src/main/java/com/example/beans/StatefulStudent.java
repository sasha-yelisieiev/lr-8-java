package com.example.beans;

import com.example.model.Student;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateful
public class StatefulStudent {
    @PersistenceContext(unitName = "studentPU")
    private EntityManager em;

    public void updateStudent(Student student) {
        em.merge(student);
    }

    public void removeStudent(Long id) {
        Student student = em.find(Student.class, id);
        if (student != null) {
            em.remove(student);
        }
    }

    public Student findStudent(Long id) {
        return em.find(Student.class, id);
    }

    public List<Student> getAllStudents() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public int insertStudentWithExecuteUpdate(String name, int age, String major) {
        Query query = em.createNativeQuery("INSERT INTO students (name, age, major) VALUES (?, ?, ?)");
        query.setParameter(1, name);
        query.setParameter(2, age);
        query.setParameter(3, major);
        return query.executeUpdate();
    }

    public List<Student> getTopStudents(int limit) {
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s ORDER BY s.age DESC", Student.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<String> getStudentFullNames() {
        return em.createQuery("SELECT CONCAT(s.name, ' (', s.major, ')') FROM Student s", String.class).getResultList();
    }

    public List<Student> findStudentsByMajorIgnoreCase(String major) {
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE LOWER(s.major) = LOWER(:major)", Student.class);
        query.setParameter("major", major);
        return query.getResultList();
    }
}
