package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.config.PGContainer;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;

import static com.javarush.jira.login.internal.web.UserTestData.GUEST_MAIL;
import static com.javarush.jira.login.internal.web.UserTestData.guest;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    ProfileMapper mapper;
    @Autowired
    ProfileRepository repository;
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        postgreSQLContainer = PGContainer.getInstance();
        postgreSQLContainer.start();
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    private static final String REST_URL = "/api/profile";

    @Test
    @WithUserDetails(GUEST_MAIL)
    void stubMethodGet() throws Exception {
        //given

        //when
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
        //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(GUEST_MAIL)
    void stubMethodUpdate() throws Exception {
        //given
        ProfileTo profileTo = mapper.toTo(repository.getOrCreate(guest.id()));
        String json = JsonUtil.writeValue(profileTo);
        //when
        perform(MockMvcRequestBuilders.put(REST_URL)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
        //then
                .andDo(print())
                .andExpect(status().isNoContent());

    }
}