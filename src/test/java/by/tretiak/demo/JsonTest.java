package by.tretiak.demo;

import by.tretiak.demo.model.pojo.UserSignupRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@org.springframework.boot.test.autoconfigure.json.JsonTest
public class JsonTest {

    @Autowired
    private JacksonTester<UserSignupRequest> json;

    @Test
    public void testDeserialize() throws Exception {
        String userSignUpJsonRequest = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"12345\",\n" +
                "    \"roles\": [\n" +
                "        \"ROLE_ADMIN\"\n" +
                "    ]\n" +
                "}";
        UserSignupRequest result = this.json.parseObject(userSignUpJsonRequest);
        boolean testUserName = result.getUsername().equals("admin");
        boolean testPassword = result.getPassword().equals("12345");
        Assertions.assertTrue(testUserName);
        Assertions.assertTrue(testPassword);
    }

}
