package com.example.main;

import com.example.model.Student;
import com.example.service.StudentService;

import java.util.ArrayList;
import java.util.List;

/**
 * 主程序入口
 */
public class Application {

    public static void main(String[] args) {
        // 创建学生列表
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20, 85));
        students.add(new Student("Bob", 22, 92));
        students.add(new Student("Charlie", 19, 78));
        students.add(new Student("Diana", 21, 95));
        students.add(new Student("Eve", 20, 88));

        // 打印所有学生信息
        System.out.println("=== 学生信息 ===");
        for (Student s : students) {
            System.out.println(s);
        }

        // 计算平均分
        double avg = StudentService.getAverageScore(students);
        System.out.printf("\n平均成绩: %.2f\n", avg);

        // 查找最高分学生
        Student top = StudentService.getTopStudent(students);
        if (top != null) {
            System.out.println("最高分学生: " + top);
        } else {
            System.out.println("没有学生数据");
        }
    }
}