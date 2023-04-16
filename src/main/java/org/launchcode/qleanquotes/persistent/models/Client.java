package org.launchcode.qleanquotes.persistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends AbstractEntity {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<House> houses = new ArrayList<>();
    public Client() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
