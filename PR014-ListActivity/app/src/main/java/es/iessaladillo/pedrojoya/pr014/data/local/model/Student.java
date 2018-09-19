package es.iessaladillo.pedrojoya.pr014.data.local.model;

@SuppressWarnings("CanBeFinal")
public class Student {

    private long id;
    private String name;
    private int age;
    private String level;
    private String grade;

    @SuppressWarnings("WeakerAccess")
    public Student(long id, String name, int age, String level, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.level = level;
        this.grade = grade;
    }

    public Student(String name, int age, String level, String grade) {
        this(0, name, age, level, grade);
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
