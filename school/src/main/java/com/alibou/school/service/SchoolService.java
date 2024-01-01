package com.alibou.school.service;

import com.alibou.school.entity.School;
import com.alibou.school.feign.StudentClient;
import com.alibou.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payload.FullSchoolResponse;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final StudentClient studentClient;

    public void saveSchool(School school) {
        schoolRepository.save(school);
    }

    public List<School> findAllSchool() {
        return schoolRepository.findAll();
    }

    public FullSchoolResponse findSchoolWithStudents(Integer schoolId) {
        var school = schoolRepository.findById(schoolId).orElse(School.builder().name("NOT_FOUND").email("NOT_FOUND").build());
        var student = studentClient.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder().name(school.getName()).email(school.getEmail()).students(student).build();
    }
}
