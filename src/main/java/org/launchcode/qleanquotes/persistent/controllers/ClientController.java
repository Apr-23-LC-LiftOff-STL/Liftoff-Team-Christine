package org.launchcode.qleanquotes.persistent.controllers;

import org.launchcode.qleanquotes.persistent.models.Client;
import org.launchcode.qleanquotes.persistent.models.House;
import org.launchcode.qleanquotes.persistent.models.data.ClientRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;


//needs clients templates
@Controller
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    //links to clients index
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddClientForm(Model model) {
        model.addAttribute(new Client());
        return "add";
    }


    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Client newClient,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "add";
        }

        //add a statement blocking duplicates
        clientRepository.findAll().forEach(client -> {
            if (Objects.equals(client.getName(), newClient.getName())) {
                errors.rejectValue("name", "invalid", "This client name already exists.");
            }
        });

        if (errors.hasErrors()) {
            return "add";
        }

        //add catch error in case database fails to save. Alert user that failed to save due to network error and to try again later.
        //you can test this error by temporarily setting savedEntity = null.
        //our error message isn't ideal at present, it shows under the house.name value rather than at the top as a global error. Struggling to fix it.
        Client savedEntity = clientRepository.save(newClient);
        if (savedEntity == null) {
            errors.rejectValue("name", "invalid", "We are having network issues, please try again later.");
        }

        if (errors.hasErrors()) {
            return "add";
        }


        clientRepository.save(newClient);
        return "redirect:";
    }

    @GetMapping("view/{clientId}")
    public String displayViewClient(Model model, @PathVariable int clientId) {
        Optional optClient = clientRepository.findById(clientId);
        if (optClient.isPresent()) {
            Client client = (Client) optClient.get();
            model.addAttribute("client", client);
            return "view";
        } else {
            return "redirect:../";
        }
    }
}
