package ru.vk.sladkiipirojok.questionnairerest.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = "/create-polls.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/drop-polls.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PollingControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PollingController pollingController;

    @Test
    @SneakyThrows
    void Get_NoSorted_BadRequestAndThrownUnsortedException() throws Exception {
        this.mockMvc.perform(get("/api/polls"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sort").value(TestData.UNSORTED_ERROR_MESSAGE));
    }

    @Test
    @SneakyThrows
    void Get_ErrorFieldForSorted_BadRequestAndThrownUncorrectedError() throws Exception {
        this.mockMvc.perform(get("/api/polls?sort=test,DESC"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sort").value(TestData.UNCORRECTED_FIELD_ERROR_MESSAGE));
    }

    @Test
    @SneakyThrows
    void Post_CorrectUser_UserId() {
        this.mockMvc.perform(post("/api/polls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.POST_USER))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
}