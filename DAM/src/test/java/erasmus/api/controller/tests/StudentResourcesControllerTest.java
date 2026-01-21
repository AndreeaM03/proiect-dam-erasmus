package erasmus.api.controller.tests;

import erasmus.api.controller.StudentResourcesController;
import erasmus.domain.model.Student;
import erasmus.domain.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudentResourcesControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentResourcesController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetStudent() throws Exception {

        // studentul fake pe care îl returnează mock-ul
        Student s = new Student();
        s.setId("1");
        s.setFirstName("name");

        // configurăm mock-ul:
        when(studentRepository.findById("1"))
                .thenReturn(Optional.of(s));

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("name"));
    }
}
