package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ScenarioScope
@Data
public class UserTestInfoHolder {

    // Latest user
    private User user;

    // Keep all users in scenario scope as list
    private List<User> users = new ArrayList<>();

    // Keep all users in scenario scope as map that key is a name
    private Map<String, User> userMap = new HashMap<>();
}
