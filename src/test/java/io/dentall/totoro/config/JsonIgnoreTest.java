package io.dentall.totoro.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.Patient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
@TestPropertySource("/config/json-ignore.properties")
public class JsonIgnoreTest {

    @Autowired
    ObjectMapper mapper;

    @Test
    public void testEmptyOrNullIgnoreGlobally() throws IOException {
        String name = "TEST";
        String json = mapper.writeValueAsString(new Patient().name(name));
        JsonNode node = mapper.readTree(json);
        assertThat(node.get("name").asText()).isEqualTo(name);
        assertThat(node.findPath("id").isMissingNode()).isTrue();
    }
}
