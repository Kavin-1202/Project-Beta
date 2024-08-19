package com.ust.Survey_api.service;

import com.ust.Survey_api.exception.SetNotFoundException;
import com.ust.Survey_api.feign.AssessmentClient;
import com.ust.Survey_api.feign.FullResponse;
import com.ust.Survey_api.feign.SetNameDto;
import com.ust.Survey_api.model.Status;
import com.ust.Survey_api.model.Survey;
import com.ust.Survey_api.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceImplTest {

    @InjectMocks
    private ServiceImpl serviceImpl;

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private AssessmentClient assessmentClient;

    private Survey survey;
    private FullResponse fullResponse;
    private List<SetNameDto> setNameDtoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        survey = new Survey(1L, "Domain1", Status.PENDING, Arrays.asList("email@example.com"), "Company1", "Set1", "1,2");

        setNameDtoList = Arrays.asList(
                new SetNameDto(1L, "Question 1"),
                new SetNameDto(2L, "Question 2")
        );

        fullResponse = new FullResponse(1L, "Domain1", Status.PENDING, Arrays.asList("email@example.com"), "Company1", "Set1", setNameDtoList);
    }

    @Test
    void addSurvey_ValidSurvey_ReturnsFullResponse() {
        when(assessmentClient.getSet(anyString())).thenReturn(ResponseEntity.ok(setNameDtoList));
        when(surveyRepository.save(any(Survey.class))).thenReturn(survey);

        FullResponse response = serviceImpl.addSurvey(survey);

        assertNotNull(response);
        assertEquals("Domain1", response.getDomain());
        assertEquals(Status.PENDING, response.getStatus());
        assertEquals("Company1", response.getCompanyName());
    }

    @Test
    void addSurvey_SetNotFound_ThrowsException() {
        when(assessmentClient.getSet(anyString())).thenThrow(new RuntimeException("Set not found"));

        Exception exception = assertThrows(SetNotFoundException.class, () -> {
            serviceImpl.addSurvey(survey);
        });

        assertEquals("Set not found.", exception.getMessage());
    }

    @Test
    void getSurveys_ReturnsFullResponses() {
        when(surveyRepository.findAll()).thenReturn(Arrays.asList(survey));
        when(assessmentClient.getSet(anyString())).thenReturn(ResponseEntity.ok(setNameDtoList));

        List<FullResponse> responses = serviceImpl.getSurveys();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void getSurveyById_ValidId_ReturnsFullResponse() {
        when(surveyRepository.findBySurveyId(anyLong())).thenReturn(survey);
        when(assessmentClient.getSet(anyString())).thenReturn(ResponseEntity.ok(setNameDtoList));

        FullResponse response = serviceImpl.getSurveyById(1L);

        assertNotNull(response);
        assertEquals("Domain1", response.getDomain());
    }

    @Test
    void getSurveyById_InvalidId_ThrowsException() {
        when(surveyRepository.findBySurveyId(anyLong())).thenReturn(null);

        Exception exception = assertThrows(SetNotFoundException.class, () -> {
            serviceImpl.getSurveyById(1L);
        });

        assertEquals("Invalid survey id", exception.getMessage());
    }

    @Test
    void getSet_ValidSetName_ReturnsSetNameDtoList() {
        when(assessmentClient.getSet(anyString())).thenReturn(ResponseEntity.ok(setNameDtoList));

        List<SetNameDto> response = serviceImpl.getSet("Set1");

        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void getSet_InvalidSetName_ThrowsException() {
        when(assessmentClient.getSet(anyString())).thenThrow(new RuntimeException("Set not found"));

        Exception exception = assertThrows(SetNotFoundException.class, () -> {
            serviceImpl.getSet("InvalidSet");
        });

        assertEquals("Invalid setname", exception.getMessage());
    }
}
