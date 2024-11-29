package com.example.dao;

import com.example.model.Student;
import jakarta.persistence.*;

import java.util.List;

public class StudentDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public StudentDAO() {
        emf = Persistence.createEntityManagerFactory("studentPU");
        em = emf.createEntityManager();
    }

    public void create(Student student) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(student);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Student read(Long id) {
        return em.find(Student.class, id);
    }

    public void update(Student student) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(student);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Student> findAll() {
        return em.createNamedQuery("Student.findAll", Student.class).getResultList();
    }

    public List<Student> findByMajor(String major) {
        return em.createNamedQuery("Student.findByMajor", Student.class)
                .setParameter("major", major)
                .getResultList();
    }

    public List<Student> findByAgeRange(int minAge, int maxAge) {
        return em.createNamedQuery("Student.findByAgeRange", Student.class)
                .setParameter("minAge", minAge)
                .setParameter("maxAge", maxAge)
                .getResultList();
    }

    public void close() {
        em.close();
        emf.close();
    }
}