package io.dentall.totoro.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class DefaultJmsErrorHandler implements ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultJmsErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        log.error(t.getCause().getMessage());
        log.debug("jms detail error:", t);
    }
}
