package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

//    Add an index method that responds at /employers with a list
//    of all employers in the database. This method should use the template employers/index.
//    To figure out the name of the model attribute you should use to pass employers into the view,
//    review this template.
    @GetMapping("")
//    @GetMapping("/employers")
    public String index(Model model) {
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {
        //add a statement blocking duplicates
        employerRepository.findAll().forEach(employer -> {
            if (Objects.equals(employer.getName(), newEmployer.getName())) {
                errors.rejectValue("name", "invalid", "This employer name already exists.");
            }
        });

        if (errors.hasErrors()) {
            return "employers/add";
        }

        //add catch error in case database fails to save. Alert user that failed to save due to network error and to try again later.
        //PS: you can test this error by temporarily setting savedEntity = null.
        //our error message isn't ideal at present, it shows under the name field rather than at the top as a global error. Struggling to fix it.
        Employer savedEntity = employerRepository.save(newEmployer);
        if (savedEntity == null) {
            errors.rejectValue("name", "invalid", "We are having network issues, please try again later.");
        }

        if (errors.hasErrors()) {
            return "employers/add";
        }

        return "redirect:";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {
        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }
}
