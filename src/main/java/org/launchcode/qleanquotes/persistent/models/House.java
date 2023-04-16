package org.launchcode.qleanquotes.persistent.models;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class House extends AbstractEntity{

    //fields
    @NotBlank
    @NotNull(message = "The field must not be null")
    @Min(value = 0, message = "The field must be greater than or equal to 0")
    @Max(value = 75000, message = "No it's not.")
    private Integer squareFeet;


    @NotBlank
    @NotNull(message = "The field must not be null")
    @Min(value = 0, message = "The field must be greater than or equal to 0")
    @Max(value = 100, message = "We ain't qleaning that.")
    private Integer numberOfRooms;


    //set relationships to other models
    @ManyToOne
    private Client client;

    //constructors
    public House() {
    }

    public House(Integer squareFeet, Integer numberOfRooms, Client aClient) {
        super();
        this.squareFeet = squareFeet;
        this.numberOfRooms = numberOfRooms;
        this.client = aClient;
    }

    // Getters and setters.

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(Integer squareFeet) {
        this.squareFeet = squareFeet;
    }

}
