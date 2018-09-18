package es.iessaladillo.pedrojoya.pr016.data.model;

public class Student {

    private final String name;
    private final int age;
    private final String level;
    private final String grade;

    public Student(String name, int age, String level, String grade) {
        this.name = name;
        this.age = age;
        this.level = level;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLevel() {
        return level;
    }

    public String getGrade() {
        return grade;
    }

}
