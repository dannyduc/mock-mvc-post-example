package com.ingenuity;

import com.ingenuity.config.MvcConfig;
import com.ingenuity.todo.Todo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TodoControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void showTodoForm() throws Exception {
        mockMvc.perform(get("/todo/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todo/add"))
                .andExpect(forwardedUrl("/WEB-INF/views/todo/add.jsp"))
                .andExpect(model().attribute("todo", hasProperty("id", is(0))))
                .andExpect(model().attribute("todo", hasProperty("description", isEmptyOrNullString())))
                .andExpect(model().attribute("todo", hasProperty("title", isEmptyOrNullString())));
    }

    @Test
    public void addTodo() throws Exception {
        final Todo formObject = new Todo();
        formObject.setTitle("my title");
        formObject.setDescription("some description");

        mockMvc.perform(post("/todo/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(new RequestPostProcessor() {
                    @Override
                    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                        return IntegrationTestUtil.addParameters(formObject, request);
                    }
                })

        ).andDo(print())
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/todo/update/1"))
                .andExpect(redirectedUrl("/todo/update/1"))
        ;
    }

}
