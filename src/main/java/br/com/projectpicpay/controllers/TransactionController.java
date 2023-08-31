package br.com.projectpicpay.controllers;

import br.com.projectpicpay.dtos.TransactionDTO;
import br.com.projectpicpay.model.entities.transaction.Transaction;
import br.com.projectpicpay.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> postTransaction(@RequestBody TransactionDTO transaction) {
        return new ResponseEntity<>(this.transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        return new ResponseEntity<>(this.transactionService.getAllTransaction(), HttpStatus.OK);
    }
}
