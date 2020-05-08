package io.dentall.totoro.web.rest.vm;

import javax.validation.constraints.Size;

/**
 * View Model extending the UserV2VM, which is meant to be used in the user management UI.
 */
public class ManagedUserV2VM extends UserV2VM {

    private static final int PASSWORD_MIN_LENGTH = 1;
    private static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserV2VM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserV2VM{" +
            "} " + super.toString();
    }
}
