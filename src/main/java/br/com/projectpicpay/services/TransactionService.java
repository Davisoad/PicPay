package br.com.projectpicpay.services;

import br.com.projectpicpay.dtos.TransactionDTO;
import br.com.projectpicpay.model.entities.transaction.Transaction;
import br.com.projectpicpay.model.entities.user.User;
import br.com.projectpicpay.model.repositories.TransactionRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {

    private final UserService userService;

    private final TransactionRepository repository;

    private final RestTemplate restTemplate;

    private final NotificationService notificationService;

    public TransactionService(UserService userService, TransactionRepository repository, RestTemplate restTemplate, NotificationService notificationService) {
        this.userService = userService;
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
    }

    public Transaction createTransaction(TransactionDTO transaction) {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        Boolean isAuthorize = this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorize) {
            throw new IllegalArgumentException("Transaction don't authorize");
        }

        Transaction newtransaction = new Transaction();
        newtransaction.setAmount(transaction.value());
        newtransaction.setSender(sender);
        newtransaction.setReceiver(receiver);
        newtransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        repository.save(newtransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "successful transaction");
        this.notificationService.sendNotification(receiver, "transaction received successfully");

        return newtransaction;
    }

    public Boolean authorizeTransaction(User user, BigDecimal value) {
        ResponseEntity<Map<String, String>> authorizationResponse = restTemplate.exchange(
                "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, String>>() {}
        );

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = Objects.requireNonNull(authorizationResponse.getBody()).get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else {
            return false;
        }
    }

    public List<Transaction> getAllTransaction() {
        return this.repository.findAll();
    }

}
