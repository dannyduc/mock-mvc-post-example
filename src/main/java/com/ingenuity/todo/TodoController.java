package com.ingenuity.todo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TodoController {

    public static Integer idCounter = 1;
    private static final Map<Integer, Todo> todoDb = new HashMap<Integer, Todo>();

    @RequestMapping(value = "/todo/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        Todo formObject = new Todo();
        model.addAttribute("todo", formObject);

        return "todo/add";
    }

    @RequestMapping(value = "/todo/add", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute Todo todo, RedirectAttributes attributes) {

        synchronized (todoDb) {
            int id = idCounter++;
            todo.setId(id);
            todoDb.put(id, todo);
        }

        return createRedirectViewPath("/todo/update/" + todo.getId());
    }

    @RequestMapping(value = "/todo/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Integer id, Model model) {

        Todo formObject = null;
        synchronized (todoDb) {
            formObject = todoDb.get(id);
        }

        model.addAttribute("todo", formObject);

        return "todo/update";
    }

    @RequestMapping(value = "/todo/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Integer id, @Valid @ModelAttribute Todo formTodo, Model model) {

        synchronized (todoDb) {
            todoDb.put(id, formTodo);
        }

        model.addAttribute("todo", formTodo);

        return "todo/update";
    }

    private String createRedirectViewPath(String requestMapping) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(requestMapping);
        return redirectViewPath.toString();
    }
}
