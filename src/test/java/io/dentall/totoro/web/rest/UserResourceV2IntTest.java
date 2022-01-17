package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.Authority;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Specialist;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.SpecialistRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.security.AuthoritiesConstants;
import io.dentall.totoro.service.UserServiceV2;
import io.dentall.totoro.service.dto.UserDTO;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.web.rest.vm.ManagedUserV2VM;
import io.dentall.totoro.web.rest.vm.UserV2VM;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserResourceV2 REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class UserResourceV2IntTest {

    private static final String DEFAULT_LOGIN = "johndoe";
    private static final String UPDATED_LOGIN = "jhipster";

    private static final Long DEFAULT_ID = 1L;

    private static final String DEFAULT_PASSWORD = "passjohndoe";
    private static final String UPDATED_PASSWORD = "passjhipster";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String UPDATED_EMAIL = "jhipster@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";
    private static final String UPDATED_FIRSTNAME = "jhipsterFirstName";

    private static final String DEFAULT_LASTNAME = "doe";
    private static final String UPDATED_LASTNAME = "jhipsterLastName";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceV2 userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SpecialistRepository specialistRepository;

    private MockMvc restUserMockMvc;

    private MockMvc restIntegrationUserMockMvc;

    private User user;

    private Specialist s1;

    @Before
    public void setup() {
        UserResourceV2 userResource = new UserResourceV2(userService, userRepository);
        s1 = new Specialist();
        s1.setId(1L);
        s1.setName("TestSpecialist1");

        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();

        this.restIntegrationUserMockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    /**
     * Create a User.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the User entity.
     */
    public static User createEntity() {
        User user = new User();
        user.setLogin(DEFAULT_LOGIN + RandomStringUtils.randomAlphabetic(5));
        user.setPassword(RandomStringUtils.randomAlphabetic(60));
        user.setActivated(true);
        user.setEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);

        ExtendUser extendUser = ExtendUserResourceIntTest.createEntity();
        extendUser.setUser(user);
        user.setExtendUser(extendUser);

        return user;
    }

    @Before
    public void initTest() {
        user = createEntity();
        user.setLogin(DEFAULT_LOGIN);
        user.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the User
        ManagedUserV2VM managedUserVM = new ManagedUserV2VM();
        managedUserVM.setLogin(DEFAULT_LOGIN);
        managedUserVM.setPassword(DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DEFAULT_LASTNAME);
        managedUserVM.setEmail(DEFAULT_EMAIL);
        managedUserVM.setActivated(true);
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restUserMockMvc.perform(post("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isCreated());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUser.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        ManagedUserV2VM managedUserVM = new ManagedUserV2VM();
        managedUserVM.setId(1L);
        managedUserVM.setLogin(DEFAULT_LOGIN);
        managedUserVM.setPassword(DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DEFAULT_LASTNAME);
        managedUserVM.setEmail(DEFAULT_EMAIL);
        managedUserVM.setActivated(true);
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc.perform(post("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void createUserWithExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        ManagedUserV2VM managedUserVM = new ManagedUserV2VM();
        managedUserVM.setLogin(DEFAULT_LOGIN);// this login should already be used
        managedUserVM.setPassword(DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DEFAULT_LASTNAME);
        managedUserVM.setEmail("anothermail@localhost");
        managedUserVM.setActivated(true);
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        // Create the User
        restUserMockMvc.perform(post("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void createUserWithExistingEmail() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        ManagedUserV2VM managedUserVM = new ManagedUserV2VM();
        managedUserVM.setLogin("anotherlogin");
        managedUserVM.setPassword(DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DEFAULT_LASTNAME);
        managedUserVM.setEmail(DEFAULT_EMAIL);// this email should already be used
        managedUserVM.setActivated(true);
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        // Create the User
        restUserMockMvc.perform(post("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the users
        restUserMockMvc.perform(get("/api/v2/users?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void getUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get the user
        restUserMockMvc.perform(get("/api/v2/users/{login}", user.getLogin()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.login").value(user.getLogin()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    public void getNonExistingUser() throws Exception {
        restUserMockMvc.perform(get("/api/v2/users/unknown"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        specialistRepository.saveAndFlush(s1);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        UserV2VM user = new UserV2VM();
        user.setId(updatedUser.getId());
        user.setLogin(updatedUser.getLogin());
        user.setFirstName(UPDATED_FIRSTNAME);
        user.setLastName(UPDATED_LASTNAME);
        user.setEmail(UPDATED_EMAIL);
        HashSet ss = new HashSet<>(Arrays.asList(s1));
        user.setSpecialists(ss);

        restUserMockMvc.perform(put("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user)))
            .andExpect(status().isOk());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUser.getSpecialists()).isEqualTo(ss);
    }

    @Test
    @Transactional
    public void updatedUserWithNotExistSpecialist() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        specialistRepository.saveAndFlush(s1);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        // FE json object
        UserV2VM user = new UserV2VM();
        user.setId(updatedUser.getId());
        s1.setId(999L);
        HashSet ss = new HashSet<>(Arrays.asList(s1));
        user.setLogin(DEFAULT_LOGIN);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setEmail(DEFAULT_EMAIL);
        user.setSpecialists(ss);

        restUserMockMvc.perform(put("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user)))
            .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void updatedUserSpecialist() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        specialistRepository.saveAndFlush(s1);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        // FE json object
        UserV2VM user = new UserV2VM();
        user.setId(updatedUser.getId());
        HashSet ss = new HashSet<>(Arrays.asList(s1));
        user.setLogin(DEFAULT_LOGIN);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setEmail(DEFAULT_EMAIL);
        user.setSpecialists(ss);

        restUserMockMvc.perform(put("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user)))
            .andExpect(status().isOk());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUser.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUser.getSpecialists()).isEqualTo(ss);
    }

    @Test
    @Transactional
    public void updateUserLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        UserV2VM user = new UserV2VM();
        user.setId(updatedUser.getId());
        user.setLogin(UPDATED_LOGIN);
        user.setFirstName(UPDATED_FIRSTNAME);
        user.setLastName(UPDATED_LASTNAME);
        user.setEmail(UPDATED_EMAIL);

        restUserMockMvc.perform(put("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user)))
            .andExpect(status().isOk());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateUserExistingEmail() throws Exception {
        // Initialize the database with 2 users
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.setLogin("jhipster");
        anotherUser.setPassword(RandomStringUtils.randomAlphabetic(60));
        anotherUser.setActivated(true);
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setFirstName("java");
        anotherUser.setLastName("hipster");
        anotherUser.setImageUrl("");
        anotherUser.setLangKey("en");
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        UserV2VM user = new UserV2VM();
        user.setId(updatedUser.getId());
        user.setLogin(updatedUser.getLogin());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail("jhipster@localhost");// this email should already be used by anotherUser
        user.setActivated(updatedUser.getActivated());

        restUserMockMvc.perform(put("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void updateUserExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.setLogin("jhipster");
        anotherUser.setPassword(RandomStringUtils.randomAlphabetic(60));
        anotherUser.setActivated(true);
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setFirstName("java");
        anotherUser.setLastName("hipster");
        anotherUser.setImageUrl("");
        anotherUser.setLangKey("en");
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        UserV2VM user = new UserV2VM();
        user.setId(updatedUser.getId());
        user.setLogin("jhipster");// this login should already be used by anotherUser
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setActivated(updatedUser.getActivated());
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restUserMockMvc.perform(put("/api/v2/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Delete the user
        restUserMockMvc.perform(delete("/api/v2/users/{login}", user.getLogin())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void getAllAuthorities() throws Exception {
        restUserMockMvc.perform(get("/api/v2/users/authorities")
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(hasItems(AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN)));
    }

    @Test
    @Transactional
    public void getAllAuthoritiesWithUnauthorizedUser() throws Exception {
        restIntegrationUserMockMvc.perform(get("/api/v2/users/authorities")
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithMockUser
    public void getAllAuthoritiesWithForbiddenUser() throws Exception {
        restIntegrationUserMockMvc.perform(get("/api/v2/users/authorities")
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.MANAGER)
    public void getAllAuthoritiesWithManager() throws Exception {
        restIntegrationUserMockMvc.perform(get("/api/v2/users/authorities")
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testUserEquals() throws Exception {
        TestUtil.equalsVerifier(User.class);
        UserV2VM user1 = new UserV2VM();
        user1.setId(1L);
        UserV2VM user2 = new UserV2VM();
        user2.setId(user1.getId());
        assertThat(user1).isEqualTo(user2);
        user2.setId(2L);
        assertThat(user1).isNotEqualTo(user2);
        user1.setId(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    public void testUserDTOtoUserV2VM() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(DEFAULT_ID);
        userDTO.setLogin(DEFAULT_LOGIN);
        userDTO.setFirstName(DEFAULT_FIRSTNAME);
        userDTO.setLastName(DEFAULT_LASTNAME);
        userDTO.setEmail(DEFAULT_EMAIL);
        userDTO.setActivated(true);
        userDTO.setCreatedBy(DEFAULT_LOGIN);
        userDTO.setLastModifiedBy(DEFAULT_LOGIN);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        UserV2VM user = new UserV2VM(userDTO);
        assertThat(user.getId()).isEqualTo(DEFAULT_ID);
        assertThat(user.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(user.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(user.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(user.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(user.isActivated()).isEqualTo(true);
        assertThat(user.getCreatedBy()).isEqualTo(DEFAULT_LOGIN);
        assertThat(user.getLastModifiedBy()).isEqualTo(DEFAULT_LOGIN);
        assertThat(user.getAuthorities()).containsExactly(AuthoritiesConstants.USER);
    }

    @Test
    public void testAuthorityEquals() {
        Authority authorityA = new Authority();
        assertThat(authorityA).isEqualTo(authorityA);
        assertThat(authorityA).isNotEqualTo(null);
        assertThat(authorityA).isNotEqualTo(new Object());
        assertThat(authorityA.hashCode()).isEqualTo(0);
        assertThat(authorityA.toString()).isNotNull();

        Authority authorityB = new Authority();
        assertThat(authorityA).isEqualTo(authorityB);

        authorityB.setName(AuthoritiesConstants.ADMIN);
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityA.setName(AuthoritiesConstants.USER);
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityB.setName(AuthoritiesConstants.USER);
        assertThat(authorityA).isEqualTo(authorityB);
        assertThat(authorityA.hashCode()).isEqualTo(authorityB.hashCode());
    }

    @Test
    @Transactional
    public void testResetPassword() throws Exception {
        user.getExtendUser().setFirstLogin(false);
        userRepository.saveAndFlush(user);

        restUserMockMvc.perform(post("/api/v2/users/{login}/reset-password", user.getLogin()))
            .andExpect(status().isOk());

        User updatedUser = userRepository.findOneByLogin(user.getLogin()).orElse(null);
        assertThat(updatedUser.getExtendUser().isFirstLogin()).isTrue();
    }

    @Test
    @Transactional
    public void testGetAvatar() throws Exception {
        // Initialize the database with avatar
        byte[] avatar = new byte[2];
        user.getExtendUser().setAvatar(avatar);
        userRepository.saveAndFlush(user);

        restUserMockMvc.perform(get("/api/v2/users/{id}/avatar", user.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetAvatarUnknownUser() throws Exception {
        // Initialize the database with avatar
        byte[] avatar = new byte[2];
        user.getExtendUser().setAvatar(avatar);
        userRepository.saveAndFlush(user);

        restUserMockMvc.perform(get("/api/v2/users/{id}/avatar", 1001)
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());
    }
}
