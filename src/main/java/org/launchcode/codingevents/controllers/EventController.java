package org.launchcode.codingevents.controllers;

import org.apache.coyote.Request;
import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute Event newEvent) {
        EventData.add(newEvent);
        return "redirect:";
    }
    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required = false) int[] eventIds){
        if (eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }
         return "redirect:";
    }
    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {
        Event eventToEdit = EventData.getById(eventId);
        String title = "Edit Event " + eventToEdit.getName() + " (id=" + eventToEdit.getId() + ")";
        model.addAttribute("title", title);
        model.addAttribute("event", eventToEdit);
        return "events/edit";
    }
    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description) {
        Event edited = EventData.getById(eventId);
        edited.setName(name);
        edited.setDescription(description);
        return "redirect:";
    }
}
