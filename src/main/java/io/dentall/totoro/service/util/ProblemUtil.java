package io.dentall.totoro.service.util;

import io.dentall.totoro.web.rest.errors.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public final class ProblemUtil extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ProblemUtil(String message, Status status) {
        super(ErrorConstants.DEFAULT_TYPE, message, status);
    }
}
