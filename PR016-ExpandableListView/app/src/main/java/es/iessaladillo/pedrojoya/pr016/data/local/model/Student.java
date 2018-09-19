package es.iessaladillo.pedrojoya.pr016.data.local.model;

public class Student {

    private final long id;
    private final String name;
    private final int age;
    private final long levelId;
    private final String grade;

    public Student(long id, String name, int age, long levelId, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.levelId = levelId;
        this.grade = grade;
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getLevel() {
        return levelId;
    }

    public String getGrade() {
        return grade;
    }

}
