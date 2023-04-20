package com.homework.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public ResponseEntity save(User user) {
        userRepository.save(user);
        if (userRepository.findById(user.getId()).isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }

    public ResponseEntity<User> getById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<String> delete(int id) {
        userRepository.deleteById(id);
        if (userRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    public ResponseEntity<Product> getPurchasedProductById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = restTemplate.getForObject("http://PRODUCT_SERVICE/api/v1/product/" + user.getProductId(), Product.class);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
