package es.iessaladillo.pedrojoya.pr012.data.local.model;

public class Student {

    private final int photo;
    private final String name;
    private final int age;
    private final String level;
    private final String grade;
    private final boolean repeater;

    public Student(int photo, String name, int age, String level,
                   String grade, boolean repeater) {
        this.photo = photo;
        this.name = name;
        this.age = age;
        this.level = level;
        this.grade = grade;
        this.repeater = repeater;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (photo != student.photo) return false;
        if (age != student.age) return false;
        if (repeater != student.repeater) return false;
        if (!name.equals(student.name)) return false;
        //noinspection SimplifiableIfStatement
        if (!level.equals(student.level)) return false;
        return grade.equals(student.grade);
    }

    @Override
    public int hashCode() {
        int result = photo;
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        result = 31 * result + level.hashCode();
        result = 31 * result + grade.hashCode();
        result = 31 * result + (repeater ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "photo=" + photo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", level='" + level + '\'' +
                ", grade='" + grade + '\'' +
                ", repeater=" + repeater +
                '}';
    }

}
