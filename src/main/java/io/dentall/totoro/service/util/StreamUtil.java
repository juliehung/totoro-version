package io.dentall.totoro.service.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

public final class StreamUtil {

    // https://stackoverflow.com/a/48466883
    public static <T> Stream<T> asStream(final Collection<T> collection) {
        return Optional.ofNullable(collection).orElse(Collections.emptySet()).stream();
    }
}
