package io.dentall.totoro.web.rest;

import io.dentall.totoro.domain.ExtendUser;

public class ExtendUserResourceIntTest {

    private static final Boolean DEFAULT_FIRST_LOGIN = true;
    private static final Boolean UPDATED_FIRST_LOGIN = false;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtendUser createEntity() {
        ExtendUser extendUser = new ExtendUser()
            .firstLogin(DEFAULT_FIRST_LOGIN);
        return extendUser;
    }
}
