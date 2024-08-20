package com.example.Assessment.service;

import com.example.Assessment.Exception.SetNotFoundException;
import com.example.Assessment.dto.Optionsdto;
import com.example.Assessment.dto.Questionsdto;
import com.example.Assessment.dto.SetNameDto;
import com.example.Assessment.dto.Setdto;
import com.example.Assessment.model.Options;
import com.example.Assessment.model.Questions;
import com.example.Assessment.model.Set;
import com.example.Assessment.model.Status;
import com.example.Assessment.repository.Optionsrepository;
import com.example.Assessment.repository.Questionsrepository;
import com.example.Assessment.repository.Setrepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceImplementationTest {

    @Mock
    private Optionsrepository optionsrepository;

    @Mock
    private Questionsrepository questionsRepository;

    @Mock
    private Setrepository setrepository;

    @InjectMocks
    private ServiceImplementation serviceImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSets() {
        // Setup
        Set set = new Set();
        set.setSetId(1L);
        List<Set> sets = Collections.singletonList(set);
        when(setrepository.findAll()).thenReturn(sets);

        // Execute
        List<Set> result = serviceImplementation.getAllSets();

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getSetId());
        verify(setrepository, times(1)).findAll();
    }

    @Test
    void testCreateSet() {
        // Setup
        Setdto setDto = new Setdto();
        setDto.setSetName("Test Set");
        setDto.setDomain("Test Domain");

        Questionsdto questionDto = new Questionsdto();
        questionDto.setQuestion_description("Sample Question");

        Optionsdto optionDto = new Optionsdto();
        optionDto.setAnswer("Sample Answer");
        optionDto.setSuggestion("Sample Suggestion");

        questionDto.setOptionsdtoList(Collections.singletonList(optionDto));
        setDto.setQuestionList(Collections.singletonList(questionDto));

        Set set = new Set();
        set.setSetId(1L);
        set.setSetName("Test Set");
        set.setDomain("Test Domain");
        set.setCreatedby("Suresh");
        set.setUpdated_by("Suresh");
        set.setStatus(Status.PENDING);
        set.setCreatedTimestamp(new Date());
        set.setUpdatedTimestamp(new Date());

        Questions question = new Questions();
        question.setQuestion_id(1L);
        question.setSetId(1L);
        question.setQuestion_description("Sample Question");

        Options option = new Options();
        option.setOptionid(1L);
        option.setQuestion_id(1L);
        option.setAnswer("Sample Answer");
        option.setSuggestion("Sample Suggestion");

        question.setOptions(Collections.singletonList(option));
        set.setQuestionList(Collections.singletonList(question));

        when(setrepository.save(any(Set.class))).thenReturn(set);

        // Execute
        Set result = serviceImplementation.createSet(setDto);

        // Verify
        assertNotNull(result);
        assertEquals("Test Set", result.getSetName());
        assertEquals("Test Domain", result.getDomain());
        verify(setrepository, times(1)).save(any(Set.class));
    }

    @Test
    void testUpdateQuestion() {
        // Setup
        Long setId = 1L;
        Long questionId = 1L;

        Set set = new Set();
        set.setSetId(setId);
        set.setQuestionList(new ArrayList<>());

        Questions existingQuestion = new Questions();
        existingQuestion.setQuestion_id(questionId);
        existingQuestion.setSetId(setId);
        existingQuestion.setQuestion_description("Old Description");
        existingQuestion.setOptions(new ArrayList<>());

        when(setrepository.findById(setId)).thenReturn(Optional.of(set));
        when(questionsRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));
        when(questionsRepository.save(any(Questions.class))).thenReturn(existingQuestion);

        Questionsdto questionDto = new Questionsdto();
        questionDto.setOptionsdtoList(Collections.singletonList(new Optionsdto()));

        // Execute
        Questions result = serviceImplementation.updateQuestion(setId, questionId, questionDto);

        // Verify
        assertNotNull(result);
        assertEquals(questionId, result.getQuestion_id());
        verify(questionsRepository, times(1)).save(any(Questions.class));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        // Setup
        Long setId = 1L;
        Long questionId = 1L;

        Set set = new Set();
        set.setSetId(setId);
        Questions question = new Questions();
        question.setQuestion_id(questionId);
        set.setQuestionList(new ArrayList<>(Collections.singletonList(question)));

        // Mock the repository responses
        when(setrepository.findById(setId)).thenReturn(Optional.of(set));
        doNothing().when(questionsRepository).deleteById(questionId);
        when(setrepository.save(any(Set.class))).thenReturn(set);

        // Execute
        boolean result = serviceImplementation.deleteQuestion(setId, questionId);

        // Verify
        assertTrue(result);
        verify(setrepository, times(1)).save(set);
        verify(questionsRepository, times(1)).deleteById(questionId);
    }


    @Test
    void testGetSetById() {
        // Setup
        Long setId = 1L;
        Set set = new Set();
        set.setSetId(setId);

        when(setrepository.findById(setId)).thenReturn(Optional.of(set));

        // Execute
        Set result = serviceImplementation.getSetById(setId);

        // Verify
        assertNotNull(result);
        assertEquals(setId, result.getSetId());
        verify(setrepository, times(1)).findById(setId);
    }

    @Test
    void testGetSetNotFound() {
        // Setup
        String setName = "NonExistentSet";
        when(setrepository.findBySetNameIgnoreCase(setName)).thenReturn(Optional.empty());

        // Execute & Verify
        SetNotFoundException thrown = assertThrows(SetNotFoundException.class, () -> {
            serviceImplementation.getSet(setName);
        });
        assertEquals("Set not found.", thrown.getMessage());
    }
}
