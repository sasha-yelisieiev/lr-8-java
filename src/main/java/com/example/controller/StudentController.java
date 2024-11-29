package com.example.controller;

import com.example.beans.StatefulStudent;
import com.example.beans.UserCounter;
import com.example.calculation.CalculationBean;
import com.example.model.Student;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mvc.Controller;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Named
@Controller
@SessionScoped
public class StudentController implements Serializable {
    @EJB
    private StatefulStudent statefulStudent;

    @EJB
    private UserCounter userCounter;

    @Inject
    private CalculationBean calculationBean;

    @Inject
    private BeanManager beanManager;

    private List<Student> students;
    private Student newStudent;
    private String selectedMajor;
    private int topStudentsLimit = 5;
    private List<String> studentFullNames;
    private int minAge;
    private int maxAge;
    private int calculationValue;
    private int calculationResult;
    private String calculationBeanMetadata;

    public StudentController() {
    }

    @PostConstruct
    public void init() {
        newStudent = new Student();
        getAllStudents();
        getCalculationBeanMetadata();
    }

    public List<Student> getStudents() {
        if (students == null) {
            getAllStudents();
        }
        return students;
    }

    public Student getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(Student newStudent) {
        this.newStudent = newStudent;
    }

    public String getSelectedMajor() {
        return selectedMajor;
    }

    public void setSelectedMajor(String selectedMajor) {
        this.selectedMajor = selectedMajor;
    }

    public int getTopStudentsLimit() {
        return topStudentsLimit;
    }

    public void setTopStudentsLimit(int topStudentsLimit) {
        this.topStudentsLimit = topStudentsLimit;
    }

    public List<String> getStudentFullNames() {
        return studentFullNames;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getActiveUsers() {
        return userCounter.getActiveUsers();
    }

    public int getCalculationValue() {
        return calculationValue;
    }

    public void setCalculationValue(int calculationValue) {
        this.calculationValue = calculationValue;
    }

    public int getCalculationResult() {
        return calculationResult;
    }

    public String getCalculationMetadata() {
        return calculationBeanMetadata;
    }

    public void performAddition() {
        calculationBean.add(calculationValue);
        calculationResult = calculationBean.getResult();
    }

    public void performSubtraction() {
        calculationBean.subtract(calculationValue);
        calculationResult = calculationBean.getResult();
    }

    private void getAllStudents() {
        students = statefulStudent.getAllStudents();
    }

    public void addStudentWithExecuteUpdate() {
        statefulStudent.insertStudentWithExecuteUpdate(newStudent.getName(), newStudent.getAge(), newStudent.getMajor());
        newStudent = new Student();
        getAllStudents();
    }

    public void removeStudent(Student student) {
        statefulStudent.removeStudent(student.getId());
        getAllStudents();
    }

    public void updateStudent(Student student) {
        statefulStudent.updateStudent(student);
        getAllStudents();
    }

    public void loadTopStudents() {
        students = statefulStudent.getTopStudents(topStudentsLimit);
    }

    public void loadStudentFullNames() {
        studentFullNames = statefulStudent.getStudentFullNames();
    }

    public void findStudentsByMajorIgnoreCase() {
        students = statefulStudent.findStudentsByMajorIgnoreCase(selectedMajor);
    }

    public void filterByAgeRange() {
        students = statefulStudent.getAllStudents().stream()
                .filter(s -> s.getAge() >= minAge && s.getAge() <= maxAge)
                .toList();
    }

    public void resetFilter() {
        selectedMajor = null;
        topStudentsLimit = 0;
        minAge = 0;
        maxAge = 0;
        getAllStudents();
    }

    private void getCalculationBeanMetadata() {
        Set<Bean<?>> beans = beanManager.getBeans(CalculationBean.class);
        if (!beans.isEmpty()) {
            Bean<?> bean = beans.iterator().next();
            calculationBeanMetadata = "Bean Name: " + (bean.getName() != null ? bean.getName() : "Unnamed")
                    + ", Bean Scope: " + bean.getScope().getName();
        } else {
            calculationBeanMetadata = "No metadata found for CalculationBean";
        }
    }
}
