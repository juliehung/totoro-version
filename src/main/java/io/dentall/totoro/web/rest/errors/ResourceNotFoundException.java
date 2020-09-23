package io.dentall.totoro.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(ErrorConstants.PATIENT_NOT_FOUND_TYPE, msg + " not found in resource", Status.NOT_FOUND);
    }
}
