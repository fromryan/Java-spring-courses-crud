package com.ryankim.mycourse.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ryankim.mycourse.models.Course;
import com.ryankim.mycourse.models.User;
import com.ryankim.mycourse.services.CourseService;
import com.ryankim.mycourse.services.UserService;
import com.ryankim.mycourse.validator.UserValidator;

@Controller
public class AppController {
	private final CourseService courseSer;
	private final UserService userSer;
	private final UserValidator userVal;
	public AppController(CourseService courseSer, UserService userSer, UserValidator userVal) {
		this.courseSer = courseSer;
		this.userSer = userSer;
		this.userVal = userVal;
	}

//Auth
	@GetMapping("/")
	public String auth(@ModelAttribute("user") User user) {
		return "auth.jsp";
	}
	
	@PostMapping("/registration")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {	
		userVal.validate(user, result);
		if(result.hasErrors()) {
			return "auth.jsp";
		}
		User newUser = userSer.registerUser(user);
		model.addAttribute("registered", "You have registered successfully! Please log in.");
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute("user") User user, @RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
		if(!userSer.authenticateUser(email, password)) {
			model.addAttribute("error", "Invalid login.");
			return "login.jsp";
		} else {
			User u = userSer.findByEmail(email);
			session.setAttribute("userId", u.getId());
			return "redirect:/courses";
		}
	}

//Show all
	@GetMapping("/courses")
	public String dashboard(HttpSession session, Model model, Long id) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		User user = userSer.findUserById(userId);
		model.addAttribute("thisUser", user);
		
		List<Course> courses = courseSer.allCourses();
		model.addAttribute("courses", courses);
		model.addAttribute("course", new Course());
		
		return "dashboard.jsp";
	}

//Log Out (Invalidating session)
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
//Join & Drop courses
	@PostMapping("/courses/join")
	public String joinCourse(@RequestParam(value="courseId") Long courseId, @RequestParam(value="userId") Long userId) {
		Course course = courseSer.findCourse(courseId);
		User user = userSer.findUserById(userId);
		course.getUsers().add(user);
		courseSer.createCourse(course);
		return "redirect:/courses";
	}	
	@PostMapping("/courses/drop")
	public String dropCourse(@RequestParam(value="courseId") Long courseId, @RequestParam(value="userId") Long userId) {
		Course course = courseSer.findCourse(courseId);
		User user = userSer.findUserById(userId);
		course.getUsers().remove(user);
		courseSer.createCourse(course);
		return "redirect:/courses";
	}
	
//Create	
	@GetMapping("/courses/new")
	public String newCourse(@ModelAttribute("course") Course course) {
		return "newCourse.jsp";
	}
	@PostMapping("/courses/creating")
	public String createCourse(@Valid @ModelAttribute("course") Course course, BindingResult result) {
		if(result.hasErrors()) {
			return "newCourse.jsp";
		} else {
			courseSer.createCourse(course);
			return "redirect:/courses";
		}
	}

//Show
	@GetMapping("/courses/{id}")
	public String showCourse(@PathVariable("id") Long id, Model model) {
		Course course = courseSer.findCourse(id);
		model.addAttribute("course", course);
		List<User> students = course.getUsers();
		model.addAttribute("students", students);
		model.addAttribute("totalEnrolled", students.size());
		return "showCourse.jsp";
	}

//Edit
	@GetMapping("/courses/{id}/edit")
	public String editCourse(@PathVariable("id") Long id, Model model) {
		Course course = courseSer.findCourse(id);
		model.addAttribute("course", course);
		return "editCourse.jsp";
	}
	@RequestMapping(value="/courses/{id}/updating", method=RequestMethod.PUT)
	public String updateCourse(@Valid @ModelAttribute("course") Course course, BindingResult result) {
		if(result.hasErrors()) {
			return "editCourse.jsp";
		} else {
			courseSer.updateCourse(course);
			return "redirect:/courses";
		}
	}

//Delete
	@RequestMapping(value="/courses/{id}/delete", method=RequestMethod.DELETE)
	public String destroyCourse(@PathVariable("id") Long id) {
		courseSer.deleteCourse(id);
		return "redirect:/courses";	
	}
	
}
