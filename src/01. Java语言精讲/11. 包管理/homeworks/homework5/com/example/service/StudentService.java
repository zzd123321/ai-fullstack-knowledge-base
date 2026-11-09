package com.example.service;

import com.example.model.Student;
import java.util.List;

/**
 * 学生服务类，提供成绩统计功能
 */
public class StudentService {

    /**
     * 计算所有学生的平均成绩
     * @param students 学生列表
     * @return 平均分，若列表为空返回 0.0
     */
    public static double getAverageScore(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Student s : students) {
            sum += s.getScore();
        }
        return (double) sum / students.size();
    }

    /**
     * 查找成绩最高的学生
     * @param students 学生列表
     * @return 成绩最高的学生对象，若列表为空返回 null
     */
    public static Student getTopStudent(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return null;
        }
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getScore() > top.getScore()) {
                top = s;
            }
        }
        return top;
    }
}