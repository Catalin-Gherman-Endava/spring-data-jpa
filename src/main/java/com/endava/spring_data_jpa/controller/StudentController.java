package com.endava.spring_data_jpa.controller;

import com.endava.spring_data_jpa.domain.Course;
import com.endava.spring_data_jpa.model.StudentDTO;
import com.endava.spring_data_jpa.repos.CourseRepository;
import com.endava.spring_data_jpa.service.StudentService;
import com.endava.spring_data_jpa.util.CustomCollectors;
import com.endava.spring_data_jpa.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseRepository courseRepository;

    public StudentController(final StudentService studentService,
            final CourseRepository courseRepository) {
        this.studentService = studentService;
        this.courseRepository = courseRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("coursesValues", courseRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Course::getId, Course::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("student") final StudentDTO studentDTO) {
        return "student/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("student") @Valid final StudentDTO studentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/add";
        }
        studentService.create(studentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("student.create.success"));
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("student", studentService.get(id));
        return "student/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("student") @Valid final StudentDTO studentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/edit";
        }
        studentService.update(id, studentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("student.update.success"));
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        studentService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("student.delete.success"));
        return "redirect:/students";
    }

}
