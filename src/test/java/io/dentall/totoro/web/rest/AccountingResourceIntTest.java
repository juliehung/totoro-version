package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.repository.AccountingRepository;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountingResource REST controller.
 *
 * @see AccountingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class AccountingResourceIntTest {

    private static final Double DEFAULT_REGISTRATION_FEE = 1D;
    private static final Double UPDATED_REGISTRATION_FEE = 2D;

    private static final Double DEFAULT_PARTIAL_BURDEN = 1D;
    private static final Double UPDATED_PARTIAL_BURDEN = 2D;

    private static final Double DEFAULT_BURDEN_COST = 1D;
    private static final Double UPDATED_BURDEN_COST = 2D;

    private static final Double DEFAULT_DEPOSIT = 1D;
    private static final Double UPDATED_DEPOSIT = 2D;

    private static final Double DEFAULT_OWN_EXPENSE = 1D;
    private static final Double UPDATED_OWN_EXPENSE = 2D;

    private static final Double DEFAULT_OTHER = 1D;
    private static final Double UPDATED_OTHER = 2D;

    private static final String DEFAULT_PATIENT_IDENTITY = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_IDENTITY = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_REASON = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    @Autowired
    private AccountingRepository accountingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAccountingMockMvc;

    private Accounting accounting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountingResource accountingResource = new AccountingResource(accountingRepository);
        this.restAccountingMockMvc = MockMvcBuilders.standaloneSetup(accountingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounting createEntity(EntityManager em) {
        Accounting accounting = new Accounting()
            .registrationFee(DEFAULT_REGISTRATION_FEE)
            .partialBurden(DEFAULT_PARTIAL_BURDEN)
            .burdenCost(DEFAULT_BURDEN_COST)
            .deposit(DEFAULT_DEPOSIT)
            .ownExpense(DEFAULT_OWN_EXPENSE)
            .other(DEFAULT_OTHER)
            .patientIdentity(DEFAULT_PATIENT_IDENTITY)
            .discountReason(DEFAULT_DISCOUNT_REASON)
            .discount(DEFAULT_DISCOUNT);
        return accounting;
    }

    @Before
    public void initTest() {
        accounting = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccounting() throws Exception {
        int databaseSizeBeforeCreate = accountingRepository.findAll().size();

        // Create the Accounting
        restAccountingMockMvc.perform(post("/api/accountings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounting)))
            .andExpect(status().isCreated());

        // Validate the Accounting in the database
        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeCreate + 1);
        Accounting testAccounting = accountingList.get(accountingList.size() - 1);
        assertThat(testAccounting.getRegistrationFee()).isEqualTo(DEFAULT_REGISTRATION_FEE);
        assertThat(testAccounting.getPartialBurden()).isEqualTo(DEFAULT_PARTIAL_BURDEN);
        assertThat(testAccounting.getBurdenCost()).isEqualTo(DEFAULT_BURDEN_COST);
        assertThat(testAccounting.getDeposit()).isEqualTo(DEFAULT_DEPOSIT);
        assertThat(testAccounting.getOwnExpense()).isEqualTo(DEFAULT_OWN_EXPENSE);
        assertThat(testAccounting.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testAccounting.getPatientIdentity()).isEqualTo(DEFAULT_PATIENT_IDENTITY);
        assertThat(testAccounting.getDiscountReason()).isEqualTo(DEFAULT_DISCOUNT_REASON);
        assertThat(testAccounting.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createAccountingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountingRepository.findAll().size();

        // Create the Accounting with an existing ID
        accounting.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountingMockMvc.perform(post("/api/accountings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounting)))
            .andExpect(status().isBadRequest());

        // Validate the Accounting in the database
        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRegistrationFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingRepository.findAll().size();
        // set the field null
        accounting.setRegistrationFee(null);

        // Create the Accounting, which fails.

        restAccountingMockMvc.perform(post("/api/accountings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounting)))
            .andExpect(status().isBadRequest());

        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPatientIdentityIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingRepository.findAll().size();
        // set the field null
        accounting.setPatientIdentity(null);

        // Create the Accounting, which fails.

        restAccountingMockMvc.perform(post("/api/accountings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounting)))
            .andExpect(status().isBadRequest());

        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingRepository.findAll().size();
        // set the field null
        accounting.setDiscountReason(null);

        // Create the Accounting, which fails.

        restAccountingMockMvc.perform(post("/api/accountings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounting)))
            .andExpect(status().isBadRequest());

        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountings() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        // Get all the accountingList
        restAccountingMockMvc.perform(get("/api/accountings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounting.getId().intValue())))
            .andExpect(jsonPath("$.[*].registrationFee").value(hasItem(DEFAULT_REGISTRATION_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].partialBurden").value(hasItem(DEFAULT_PARTIAL_BURDEN.doubleValue())))
            .andExpect(jsonPath("$.[*].burdenCost").value(hasItem(DEFAULT_BURDEN_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].deposit").value(hasItem(DEFAULT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].ownExpense").value(hasItem(DEFAULT_OWN_EXPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.doubleValue())))
            .andExpect(jsonPath("$.[*].patientIdentity").value(hasItem(DEFAULT_PATIENT_IDENTITY.toString())))
            .andExpect(jsonPath("$.[*].discountReason").value(hasItem(DEFAULT_DISCOUNT_REASON.toString())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAccounting() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        // Get the accounting
        restAccountingMockMvc.perform(get("/api/accountings/{id}", accounting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accounting.getId().intValue()))
            .andExpect(jsonPath("$.registrationFee").value(DEFAULT_REGISTRATION_FEE.doubleValue()))
            .andExpect(jsonPath("$.partialBurden").value(DEFAULT_PARTIAL_BURDEN.doubleValue()))
            .andExpect(jsonPath("$.burdenCost").value(DEFAULT_BURDEN_COST.doubleValue()))
            .andExpect(jsonPath("$.deposit").value(DEFAULT_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.ownExpense").value(DEFAULT_OWN_EXPENSE.doubleValue()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.doubleValue()))
            .andExpect(jsonPath("$.patientIdentity").value(DEFAULT_PATIENT_IDENTITY.toString()))
            .andExpect(jsonPath("$.discountReason").value(DEFAULT_DISCOUNT_REASON.toString()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccounting() throws Exception {
        // Get the accounting
        restAccountingMockMvc.perform(get("/api/accountings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccounting() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        int databaseSizeBeforeUpdate = accountingRepository.findAll().size();

        // Update the accounting
        Accounting updatedAccounting = accountingRepository.findById(accounting.getId()).get();
        // Disconnect from session so that the updates on updatedAccounting are not directly saved in db
        em.detach(updatedAccounting);
        updatedAccounting
            .registrationFee(UPDATED_REGISTRATION_FEE)
            .partialBurden(UPDATED_PARTIAL_BURDEN)
            .burdenCost(UPDATED_BURDEN_COST)
            .deposit(UPDATED_DEPOSIT)
            .ownExpense(UPDATED_OWN_EXPENSE)
            .other(UPDATED_OTHER)
            .patientIdentity(UPDATED_PATIENT_IDENTITY)
            .discountReason(UPDATED_DISCOUNT_REASON)
            .discount(UPDATED_DISCOUNT);

        restAccountingMockMvc.perform(put("/api/accountings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccounting)))
            .andExpect(status().isOk());

        // Validate the Accounting in the database
        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeUpdate);
        Accounting testAccounting = accountingList.get(accountingList.size() - 1);
        assertThat(testAccounting.getRegistrationFee()).isEqualTo(UPDATED_REGISTRATION_FEE);
        assertThat(testAccounting.getPartialBurden()).isEqualTo(UPDATED_PARTIAL_BURDEN);
        assertThat(testAccounting.getBurdenCost()).isEqualTo(UPDATED_BURDEN_COST);
        assertThat(testAccounting.getDeposit()).isEqualTo(UPDATED_DEPOSIT);
        assertThat(testAccounting.getOwnExpense()).isEqualTo(UPDATED_OWN_EXPENSE);
        assertThat(testAccounting.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testAccounting.getPatientIdentity()).isEqualTo(UPDATED_PATIENT_IDENTITY);
        assertThat(testAccounting.getDiscountReason()).isEqualTo(UPDATED_DISCOUNT_REASON);
        assertThat(testAccounting.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingAccounting() throws Exception {
        int databaseSizeBeforeUpdate = accountingRepository.findAll().size();

        // Create the Accounting

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountingMockMvc.perform(put("/api/accountings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounting)))
            .andExpect(status().isBadRequest());

        // Validate the Accounting in the database
        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccounting() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        int databaseSizeBeforeDelete = accountingRepository.findAll().size();

        // Get the accounting
        restAccountingMockMvc.perform(delete("/api/accountings/{id}", accounting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Accounting> accountingList = accountingRepository.findAll();
        assertThat(accountingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accounting.class);
        Accounting accounting1 = new Accounting();
        accounting1.setId(1L);
        Accounting accounting2 = new Accounting();
        accounting2.setId(accounting1.getId());
        assertThat(accounting1).isEqualTo(accounting2);
        accounting2.setId(2L);
        assertThat(accounting1).isNotEqualTo(accounting2);
        accounting1.setId(null);
        assertThat(accounting1).isNotEqualTo(accounting2);
    }
}
