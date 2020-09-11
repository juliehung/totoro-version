package io.dentall.totoro.business.repository;

import java.util.Optional;

public interface RemappingDomainToTableDtoRepository {
    <T> Optional<T> findById(Long id, Class<T> clazz);
}
