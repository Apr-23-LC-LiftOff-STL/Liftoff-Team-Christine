package org.launchcode.techjobs.persistent.models;

import javax.persistence.*;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Job extends AbstractEntity {

    //set relationships to other models
    @ManyToOne
    private Employer employer;

//    Update your Job model class to fit its many-to-many relationship with skills.
    //single String skills needs to be a list of Skill objects
    @ManyToMany
    private List<Skill> skills = new ArrayList<>();

    //constructors
    public Job() {
    }

    public Job(Employer anEmployer, List<Skill> someSkills) {
        super();
        this.employer = anEmployer;
        this.skills = someSkills;
    }

    // Getters and setters.

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
