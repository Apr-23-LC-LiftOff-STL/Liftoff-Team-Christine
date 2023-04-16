package org.launchcode.qleanquotes.persistent.controllers;

import org.launchcode.qleanquotes.persistent.models.Client;
import org.launchcode.qleanquotes.persistent.models.data.ClientRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.validation.Valid;
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
