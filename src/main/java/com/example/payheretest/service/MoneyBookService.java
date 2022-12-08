package com.example.payheretest.service;

import com.example.payheretest.domain.entity.MoneyBook;
import com.example.payheretest.domain.entity.User;
import com.example.payheretest.domain.request.MoneyBookCreateRequest;
import com.example.payheretest.domain.request.MoneyBookUpdateRequest;
import com.example.payheretest.domain.response.MoneyBookResponse;
import com.example.payheretest.exception.InvalidMoneyBookException;
import com.example.payheretest.exception.NoSuchMoneyBookException;
import com.example.payheretest.repository.MoneyBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyBookService {

    private final MoneyBookRepository moneyBookRepository;
    private final UserService userService;


    @Transactional
    public MoneyBookResponse create(
            final String userEmail,
            final MoneyBookCreateRequest moneyBookCreateRequest
    ) {
        User user = userService.getByEmail(userEmail);
        MoneyBook moneyBook = MoneyBook.builder()
                .user(user)
                .money(moneyBookCreateRequest.getMoney())
                .memo(moneyBookCreateRequest.getMemo())
                .build();
        return moneyBookRepository.save(moneyBook).toResponse();
    }

    @Transactional
    public MoneyBookResponse patchUpdate(
            final String userEmail,
            final long moneyBookId,
            final MoneyBookUpdateRequest moneyBookUpdateRequest
    ) {
        MoneyBook moneyBook = moneyBookRepository.findByIdAndDeletedIsFalse(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new);
        checkMoneyBookEmailValid(userEmail, moneyBook);

        return moneyBook.patchUpdate(moneyBookUpdateRequest).toResponse();
    }


    @Transactional
    public MoneyBookResponse putUpdate(
            final String userEmail,
            final long moneyBookId,
            final MoneyBookUpdateRequest moneyBookUpdateRequest
    ) {
        MoneyBook moneyBook = moneyBookRepository.findByIdAndDeletedIsFalse(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new);
        checkMoneyBookEmailValid(userEmail, moneyBook);

        return moneyBook.putUpdate(moneyBookUpdateRequest).toResponse();
    }

    @Transactional
    public void delete(
            final String userEmail,
            long moneyBookId
    ) {
        MoneyBook moneyBook = moneyBookRepository.findByIdAndDeletedIsFalse(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new);
        checkMoneyBookEmailValid(userEmail, moneyBook);

        moneyBook.delete();
    }

    public List<MoneyBookResponse> list(final String userEmail) {
        return moneyBookRepository.findAllByUserEmailAndDeletedIsFalse(userEmail)
                .stream()
                .map(MoneyBook::toResponse)
                .collect(Collectors.toList());
    }

    public MoneyBookResponse get(
            final String userEmail,
            final Long moneyBookId
    ) {
        MoneyBook moneyBook = moneyBookRepository.findByIdAndDeletedIsFalse(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new);
        checkMoneyBookEmailValid(userEmail, moneyBook);

        return moneyBook.toResponse();
    }

    private void checkMoneyBookEmailValid(
            final String userEmail,
            final MoneyBook moneyBook
    ) throws InvalidMoneyBookException {
        if (!moneyBook.getUser().getEmail().equals(userEmail)) {
            throw new InvalidMoneyBookException();
        }
    }
}
