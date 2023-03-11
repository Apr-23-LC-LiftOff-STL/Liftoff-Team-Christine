package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "My Jobs");
        return "index";
    }


    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

//    add code inside of this method to select the employer object that has been chosen to
//    be affiliated with the new job. You will need to select the employer using the request
//    parameter you’ve added to the method.
    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        //added in part 3, struggling to understand
        //review Optional class plz
        Optional <Employer> optEmployer = employerRepository.findById(employerId);
            if (optEmployer.isPresent()) {
                Employer employer = optEmployer.get();
                newJob.setEmployer(employer);
            }

            //is below (List<Skill>) bit casting the skill Ids somehow? what would that mean?
            List<Skill> skillList = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillList);

        jobRepository.save(newJob);

        return "redirect:";
    }

    //from EmployerController...
//    Optional optEmployer = employerRepository.findById(employerId);
//        if (optEmployer.isPresent()) {
//        Employer employer = (Employer) optEmployer.get();
//        model.addAttribute("employer", employer);
//        return "employers/view";
//    } else {
//        return "redirect:../";
//    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            model.addAttribute("job", optJob.get());
            return "view";
        }
        return "view";
    }


}
