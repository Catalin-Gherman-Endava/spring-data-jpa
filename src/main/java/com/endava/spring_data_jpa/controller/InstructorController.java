package com.endava.spring_data_jpa.controller;

import com.endava.spring_data_jpa.model.InstructorDTO;
import com.endava.spring_data_jpa.service.InstructorService;
import com.endava.spring_data_jpa.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(final InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("instructors", instructorService.findAll());
        return "instructor/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("instructor") final InstructorDTO instructorDTO) {
        return "instructor/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("instructor") @Valid final InstructorDTO instructorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "instructor/add";
        }
        instructorService.create(instructorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("instructor.create.success"));
        return "redirect:/instructors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("instructor", instructorService.get(id));
        return "instructor/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("instructor") @Valid final InstructorDTO instructorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "instructor/edit";
        }
        instructorService.update(id, instructorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("instructor.update.success"));
        return "redirect:/instructors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = instructorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            instructorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("instructor.delete.success"));
        }
        return "redirect:/instructors";
    }

}
