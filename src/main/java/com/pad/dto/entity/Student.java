package com.pad.dto.entity;

import java.util.Objects;

public class Student {

    private final String name;
    private final String group;
    private final Integer age;

    public Student(String name, String group, Integer age) {
        this.name = name;
        this.group = group;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(group, student.group) &&
                Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, group, age);
    }
}
