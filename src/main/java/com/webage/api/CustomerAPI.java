package com.webage.api;

import com.webage.domain.Customer;
import com.webage.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerAPI {

    @Autowired
    CustomersRepository repo;
    @GetMapping
    public Iterable<Customer> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{customerId}")
    public Optional<Customer> getCustomerById(@PathVariable("customerId") long id){
        return repo.findById(id);
    }
    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uri) {
        if(newCustomer.getId() !=0 ||
        newCustomer.getName() == null ||
        newCustomer.getEmail()== null) {
            //reject
            return ResponseEntity.badRequest().build();
        }
        newCustomer=repo.save(newCustomer);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id").buildAndExpand(newCustomer.getId()).toUri();
        ResponseEntity<?> response= ResponseEntity.created(location).build();
        return response;
    }
    @PutMapping("/{customerId}")
    public ResponseEntity<?> putCustomer(@RequestBody Customer newCustomer,
                                         @PathVariable("customerId") long customerId) {
        if (newCustomer.getId()!=customerId
                || newCustomer.getName()==null
                || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }
        newCustomer=repo.save(newCustomer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@RequestBody Customer newCustomer,
                                         @PathVariable("customerId") long customerId) {
        if (newCustomer.getId()!=customerId
                || newCustomer.getName()==null
                || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }
        repo.delete(newCustomer);
        return ResponseEntity.ok().build();
    }
}
