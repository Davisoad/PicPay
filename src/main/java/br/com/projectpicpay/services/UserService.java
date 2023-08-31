package br.com.projectpicpay.services;

import br.com.projectpicpay.dtos.UserDTO;
import br.com.projectpicpay.model.entities.user.User;
import br.com.projectpicpay.model.entities.user.UserType;
import br.com.projectpicpay.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void validateTransaction(User sender, BigDecimal amount)  {
        if(sender.getUserType() == UserType.MERCHANT) {
            throw new IllegalArgumentException("Merchant-type user don't to authorized to accomplish a transaction");
        }
        if(sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Amount insufficient");
        }
    }

    public User findUserById(Long id) {
        return this.repository.findUserById(id).orElseThrow(() -> new IllegalArgumentException("User don't found"));
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        return saveUser(newUser);
    }

    public User actualizarUser(Long id, UserDTO data) {
        User newUser = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        newUser.setFirstName(data.firstName());
        newUser.setLastName(data.lastName());
        newUser.setDocument(data.document());
        newUser.setPassword(data.password());
        newUser.setBalance(data.balance());
        newUser.setEmail(data.email());

        return saveUser(newUser);
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
 }
