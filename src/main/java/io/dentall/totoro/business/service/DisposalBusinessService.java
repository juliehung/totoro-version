package io.dentall.totoro.business.service;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.Todo;
import io.dentall.totoro.repository.DisposalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class DisposalBusinessService {

    private final DisposalRepository disposalRepository;

    public DisposalBusinessService(DisposalRepository disposalRepository) {
        this.disposalRepository = disposalRepository;
    }

    @Transactional(readOnly = true)
    public List<Todo> findTodosBetweenDisposalCreatedDate(Instant start, Instant end) {
        try (Stream<Disposal> disposals = disposalRepository.findWithTodoByCreatedDateBetween(start, end)) {
            return disposals
                .map(Disposal::getTodo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }
    }
}
