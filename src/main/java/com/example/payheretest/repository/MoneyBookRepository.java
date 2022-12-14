package com.example.payheretest.repository;

import com.example.payheretest.domain.entity.MoneyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoneyBookRepository extends JpaRepository<MoneyBook, Long> {

    Optional<MoneyBook> findByIdAndDeletedIsFalse(Long id);

    List<MoneyBook> findAllByUserEmailAndDeletedIsFalse(String  email);

}
