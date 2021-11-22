package io.dentall.totoro.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.MailService;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.step_definitions.holders.UserTestInfoHolder;
import io.dentall.totoro.web.rest.UserResource;
import io.dentall.totoro.web.rest.vm.ManagedUserVM;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserStepDefinition extends AbstractStepDefinition {

    @Autowired
    private UserTestInfoHolder userTestInfoHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    private String userApiPath = "/api/users";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserResource resource = new UserResource(userService, userRepository, mailService, null, null);
        this.mvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Given("建立醫師")
    public void createUser() throws Exception {
        ExtendUser extendUser = new ExtendUser();
        extendUser.setNationalId(randomAlphabetic(10));
        ManagedUserVM newUser = new ManagedUserVM();
        newUser.setActivated(true);
        newUser.setEmail(randomAlphabetic(10) + "@dentall.io");
        newUser.setFirstName("firstname");
        newUser.setLastName("lastname");
        newUser.setLogin(randomAlphabetic(10));
        newUser.setPassword(randomAlphabetic(10));
        newUser.setExtendUser(extendUser);

        MockHttpServletRequestBuilder requestBuilder = post(userApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(newUser));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        User user = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), User.class);
        userTestInfoHolder.setUser(user);
    }

    @Given("確認醫師建立成功")
    public void checkUser() {
        User user = userTestInfoHolder.getUser();
        assertThat(user.getId()).isNotNull();
    }

}
