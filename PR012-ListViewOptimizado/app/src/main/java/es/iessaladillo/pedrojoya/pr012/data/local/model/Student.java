package es.iessaladillo.pedrojoya.pr012.data.local.model;

@SuppressWarnings("unused")
public class Student {

    private long id;
    private final int photo;
    private final String name;
    private final int age;
    private final String level;
    private final String grade;
    private final boolean repeater;

    @SuppressWarnings("WeakerAccess")
    public Student(long id, int photo, String name, int age, String level,
                   String grade, boolean repeater) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.age = age;
        this.level = level;
        this.grade = grade;
        this.repeater = repeater;
    }

    public Student(int photo, String name, int age, String level,
            String grade, boolean repeater) {
        this(0, photo, name, age, level, grade, repeater);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPhoto() {
        return photo;
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

    public boolean isRepeater() {
        return repeater;
    }

}
