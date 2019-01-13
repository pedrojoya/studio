package es.iessaladillo.pedrojoya.pr194.data.mapper;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr194.data.model.Student;
import es.iessaladillo.pedrojoya.pr194.data.remote.dto.StudentDto;

// Transform remote StudentDto model into Student model.
@SuppressWarnings("WeakerAccess")
public class StudentMapper {

    public Student map(StudentDto studentDTO) {
        return new Student(studentDTO.getPhoto(), studentDTO.getName(), studentDTO.getAge(),
            studentDTO.getGrade(), studentDTO.getPhone(), studentDTO.isRepeater());
    }

    public List<Student> map(List<StudentDto> studentDTOs) {
        List<Student> result = new ArrayList<>();
        for (StudentDto studentDTO : studentDTOs) {
            result.add(map(studentDTO));
        }
        return result;
    }

}
