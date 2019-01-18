package io.dentall.totoro.service.util;

import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.UserRepository;
import org.springframework.cache.CacheManager;

import java.util.Objects;

public final class CacheUtil {

    public static void clearUserCaches(User user, CacheManager cacheManager) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }
}
