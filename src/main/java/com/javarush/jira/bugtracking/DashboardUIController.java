package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.login.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping
public class DashboardUIController {

    private TaskService taskService;
    private UserBelongService belongService;

    @GetMapping("/") // index page
    public String getAll(Model model) {
        List<TaskTo> tasks = taskService.getAll();
        Map<SprintTo, List<TaskTo>> taskMap = tasks.stream()
                .collect(Collectors.groupingBy(TaskTo::getSprint));
        model.addAttribute("taskMap", taskMap);
        return "index";
    }

    //todo added method for adding tags for Task
    @PostMapping("/")
    public String addTag(@RequestParam("task_id_number") Long id, @RequestParam("tag") String tag){
        taskService.addTag(id, tag);
        return "redirect:/";
    }

    //todo added method for subscribing to a task
    @GetMapping("/subscribe/{taskId}")
    public String subscribeToTask(@AuthenticationPrincipal AuthUser user, @PathVariable Long taskId){
        belongService.subscribeToTask(user.getUser(), taskId);
        return "redirect:/";
    }
}
