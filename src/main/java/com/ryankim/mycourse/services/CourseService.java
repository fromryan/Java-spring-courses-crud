package com.ryankim.mycourse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ryankim.mycourse.Repositories.CourseRepository;
import com.ryankim.mycourse.models.Course;

@Service
public class CourseService {
	private final CourseRepository courseRepo;	
	public CourseService(CourseRepository courseRepo) {
		this.courseRepo = courseRepo;
	}
//All courses	
	public List<Course> allCourses(){
		return courseRepo.findAll();
	}
//Create a course
	public Course createCourse(Course course) {
		return courseRepo.save(course);
	}
//Retrieve a course by id
	public Course findCourse(Long id) {
		Optional<Course> course = courseRepo.findById(id);
		if(course.isPresent()) {
			return course.get();
		} else {
			return null;
		}
	}
//Update a course
	public Course updateCourse(Course course) {
		return courseRepo.save(course);
	}
	
	public Course updateCourse(Long id, String courseName, String instructor, int capacity) {
		Optional<Course> course = courseRepo.findById(id);
		if(course.isPresent()) {
			course.get().setCourseName(courseName);
			course.get().setInstructor(instructor);
			course.get().setCapacity(capacity);
			return courseRepo.save(course.get());
		} else {
			return null;
		}
	}
//Delete a course
	public void deleteCourse(Long id) {
		courseRepo.deleteById(id);
	}
}
