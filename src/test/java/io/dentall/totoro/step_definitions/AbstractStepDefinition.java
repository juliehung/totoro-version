package io.dentall.totoro.step_definitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public abstract class AbstractStepDefinition {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    protected ExceptionTranslator exceptionTranslator;

    @Autowired
    protected MappingJackson2HttpMessageConverter jacksonMessageConverter;

    protected MockMvc mvc;

}
