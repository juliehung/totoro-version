package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the Status controller.
 *
 * @see StatusController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class StatusControllerTest {

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        StatusController statusController = new StatusController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(statusController)
            .setControllerAdvice(exceptionTranslator)
            .build();
    }

    @Test
    public void testStatus() throws Exception {
        mockMvc.perform(get("/api/greeting"))
            .andExpect(status().isOk());
    }
}

