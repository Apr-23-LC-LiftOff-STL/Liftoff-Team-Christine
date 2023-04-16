package org.launchcode.qleanquotes.persistent.controllers;

import org.launchcode.qleanquotes.persistent.models.Client;
import org.launchcode.qleanquotes.persistent.models.House;
import org.launchcode.qleanquotes.persistent.models.data.ClientRepository;
import org.launchcode.qleanquotes.persistent.models.data.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HouseController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private HouseRepository houseRepository;


    //sends info to houses templates (which doesnt exist yet), directs to houses/index
    @RequestMapping("houses")
    public String index(Model model) {
        model.addAttribute("houses", houseRepository.findAll());
        model.addAttribute("title", "Your House");
        return "index";
    }

    //links to houses/add template. I guess a user clicks 'add quote' and this control is triggered
    @GetMapping("add")
    public String displayAddHouseForm(Model model) {
        model.addAttribute("title", "Add House");
        model.addAttribute("employers", clientRepository.findAll());
        return "add";
    }

    //when user posts information, it triggers this controller
    @PostMapping("add")
    public String processAddHouseForm(@ModelAttribute @Valid House newHouse,
                                    Errors errors, Model model, @RequestParam int clientId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add House");
            return "add";
        }

        //I do not know how below functions or what it is doing
        Optional <Client> optClient = clientRepository.findById(clientId);
        if (optClient.isPresent()) {
            Client client = optClient.get();
            newHouse.setClient(client);
        }

        houseRepository.save(newHouse);

        //i forget where this leads.
        return "redirect:";
    }

    @GetMapping("view/{houseId}")
    public String displayViewHouse(Model model, @PathVariable int houseId) {
        Optional<House> optHouse = houseRepository.findById(houseId);
        if (optHouse.isPresent()) {
            model.addAttribute("house", optHouse.get());
            return "view";
        }
        return "view";
    }

}








