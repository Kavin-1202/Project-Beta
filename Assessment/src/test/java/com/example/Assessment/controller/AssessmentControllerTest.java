package com.example.Assessment.controller;

import com.example.Assessment.Exception.SetNotFoundException;
import com.example.Assessment.dto.Optionsdto;
import com.example.Assessment.dto.Questionsdto;
import com.example.Assessment.dto.SetNameDto;
import com.example.Assessment.dto.Setdto;
import com.example.Assessment.model.Questions;
import com.example.Assessment.model.Set;
import com.example.Assessment.model.Status;
import com.example.Assessment.service.ServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Assessmentcontroller.class)
public class AssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceImplementation assessmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSets() throws Exception {
        // Setup
        Set set = new Set();
        set.setSetId(1L);
        List<Set> sets = Collections.singletonList(set);
        when(assessmentService.getAllSets()).thenReturn(sets);

        // Execute & Verify
        mockMvc.perform(get("/assessments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].setId").value(1));
    }

    @Test
    void testCreateSet() throws Exception {
        // Setup
        Setdto setDto = new Setdto();
        setDto.setSetName("Test Set");
        setDto.setDomain("Test Domain");

        Set set = new Set();
        set.setSetId(1L);
        set.setSetName("Test Set");
        set.setDomain("Test Domain");
        when(assessmentService.createSet(any(Setdto.class))).thenReturn(set);

        // Execute & Verify
        mockMvc.perform(post("/assessment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(setDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.setName").value("Test Set"));
    }

    @Test
    void testGetSet() throws Exception {
        // Setup
        SetNameDto setNameDto = new SetNameDto();
        setNameDto.setQuestion_id(1L);
        setNameDto.setQuestion_description("Sample Question");
        List<SetNameDto> setNameDtos = Collections.singletonList(setNameDto);
        when(assessmentService.getSet("Test Set")).thenReturn(setNameDtos);

        // Execute & Verify
        mockMvc.perform(get("/assessment/Test Set"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].question_description").value("Sample Question"));
    }

    @Test
    void testUpdateQuestion() throws Exception {
        // Setup
        Questionsdto questionsdto = new Questionsdto();
        questionsdto.setQuestion_description("Updated Question");

        Questions question = new Questions();
        question.setQuestion_id(1L);
        question.setQuestion_description("Updated Question");

        when(assessmentService.getSetById(anyLong())).thenReturn(new Set());
        when(assessmentService.updateQuestion(anyLong(), anyLong(), any(Questionsdto.class))).thenReturn(question);

        // Execute & Verify
        mockMvc.perform(put("/assessment/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionsdto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.question_description").value("Updated Question"));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        // Setup
        when(assessmentService.deleteQuestion(anyLong(), anyLong())).thenReturn(true);

        // Execute & Verify
        mockMvc.perform(delete("/assessment/1/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully."));
    }

    @Test
    void testHandleSetNotFoundException() throws Exception {
        // Setup
        when(assessmentService.getSet("NonExistentSet")).thenThrow(new SetNotFoundException("Set not found."));

        // Execute & Verify
        mockMvc.perform(get("/assessment/NonExistentSet"))
                .andExpect(status().isOk())
                .andExpect(content().string("Set not found."));
    }
}
