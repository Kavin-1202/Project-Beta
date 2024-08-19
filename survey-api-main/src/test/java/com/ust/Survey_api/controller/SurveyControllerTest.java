package com.ust.Survey_api.controller;

import com.ust.Survey_api.exception.SetNotFoundException;
import com.ust.Survey_api.feign.FullResponse;
import com.ust.Survey_api.feign.SetNameDto;
import com.ust.Survey_api.model.Status;
import com.ust.Survey_api.model.Survey;
import com.ust.Survey_api.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class SurveyControllerTest {

    @InjectMocks
    private SurveyController surveyController;

    @Mock
    private SurveyService surveyService;

    private MockMvc mockMvc;

    private Survey survey;
    private FullResponse fullResponse;
    private List<SetNameDto> setNameDtoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(surveyController).build();

        survey = new Survey(1L, "Domain1", Status.PENDING, Arrays.asList("email@example.com"), "Company1", "Set1", "1,2");

        setNameDtoList = Arrays.asList(
                new SetNameDto(1L, "Question 1"),
                new SetNameDto(2L, "Question 2")
        );

        fullResponse = new FullResponse(1L, "Domain1", Status.PENDING, Arrays.asList("email@example.com"), "Company1", "Set1", setNameDtoList);
    }

    @Test
    void addSurvey_ValidSurvey_ReturnsOk() throws Exception {
        when(surveyService.addSurvey(any(Survey.class))).thenReturn(fullResponse);

        mockMvc.perform(post("/survey")
                        .contentType("application/json")
                        .content("{\"domain\":\"Domain1\",\"status\":\"PENDING\",\"email\":[\"email@example.com\"],\"companyName\":\"Company1\",\"setName\":\"Set1\",\"questionId\":\"1,2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domain").value("Domain1"))
                .andExpect(jsonPath("$.companyName").value("Company1"));
    }

    @Test
    void addSurvey_SetNotFound_ThrowsException() throws Exception {
        when(surveyService.addSurvey(any(Survey.class))).thenThrow(new SetNotFoundException("Set name not found."));

        mockMvc.perform(post("/survey")
                        .contentType("application/json")
                        .content("{\"domain\":\"Domain1\",\"status\":\"PENDING\",\"email\":[\"email@example.com\"],\"companyName\":\"Company1\",\"setName\":\"Set1\",\"questionId\":\"1,2\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Set name not found."));
    }

    @Test
    void getSurveys_ReturnsListOfSurveys() throws Exception {
        when(surveyService.getSurveys()).thenReturn(Arrays.asList(fullResponse));

        mockMvc.perform(get("/surveys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].domain").value("Domain1"));
    }

    @Test
    void getSurveyById_ValidId_ReturnsSurvey() throws Exception {
        when(surveyService.getSurveyById(anyLong())).thenReturn(fullResponse);

        mockMvc.perform(get("/survey/surveyId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domain").value("Domain1"));
    }

    @Test
    void getSurveyById_InvalidId_ThrowsException() throws Exception {
        when(surveyService.getSurveyById(anyLong())).thenThrow(new SetNotFoundException("Invalid surveyId"));

        mockMvc.perform(get("/survey/surveyId/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid surveyId"));
    }

    @Test
    void getQuestionsBySetName_ValidSetName_ReturnsQuestions() throws Exception {
        when(surveyService.getSet(anyString())).thenReturn(setNameDtoList);

        mockMvc.perform(get("/survey/setName/Set1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question_description").value("Question 1"));
    }
}
