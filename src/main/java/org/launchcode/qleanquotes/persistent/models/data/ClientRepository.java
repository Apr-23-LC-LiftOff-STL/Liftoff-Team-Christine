package org.launchcode.qleanquotes.persistent.models.data;


import org.launchcode.qleanquotes.persistent.models.Client;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
}