package br.com.projectpicpay.model.repositories;

import br.com.projectpicpay.model.entities.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
