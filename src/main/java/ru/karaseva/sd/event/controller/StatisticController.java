package ru.karaseva.sd.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.karaseva.sd.event.dao.EventDao;
import ru.karaseva.sd.event.model.Enter;
import ru.karaseva.sd.event.model.Event;
import ru.karaseva.sd.event.model.Exit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StatisticController {
    private final EventDao dao;

    public StatisticController(EventDao dao) {
        this.dao = dao;
    }

    @GetMapping("/statistic")
    public String statistic(ModelMap modelMap) {
        statPerDay(modelMap);
        avgDuration(modelMap);
        avgFrequency(modelMap);
        return "statistic";
    }

    private void statPerDay(ModelMap modelMap) {
        long secondInDay = 24 * 60 * 60;
        Map<String, Integer> entersByDay = dao.getEvents().stream()
                .filter(e -> e.eventType instanceof Enter)
                .collect(Collectors.groupingBy(event -> event.instant.getEpochSecond() / secondInDay))
                .values().stream()
                .collect(Collectors.toMap(entry -> entry.get(0).getFormattedInstant(), List::size));
        modelMap.addAttribute("entersByDay", entersByDay);
    }

    private void avgDuration(ModelMap modelMap) {
        Map<Integer, List<Event>> userVisits = dao.getEvents().stream()
                .filter(e -> e.eventType instanceof Enter || e.eventType instanceof Exit)
                .sorted(Comparator.comparing(e -> e.instant)).collect(Collectors.groupingBy(Event::getSubId));
        List<Long> avgUsers = userVisits.values().stream().map(events -> {
            List<Event> enters = events.stream().filter(e -> e.eventType instanceof Enter).toList();
            List<Event> exits = events.stream().filter(e -> e.eventType instanceof Exit).toList();
            List<Long> durations = new ArrayList<>();
            for (int i = 0; i < enters.size(); i++) {
                durations.add((exits.get(i).instant.getEpochSecond() - enters.get(i).instant.getEpochSecond()));
            }
            return durations.stream().mapToLong(e -> e).sum() / durations.size();
        }).toList();
        long result = avgUsers.stream().mapToLong(e -> e).sum() / avgUsers.size();
        modelMap.addAttribute("avgDuration", result);
    }

    private void avgFrequency(ModelMap modelMap) {
        Map<Integer, List<Event>> userVisits = dao.getEvents().stream()
                .filter(e -> e.eventType instanceof Enter || e.eventType instanceof Exit)
                .sorted(Comparator.comparing(e -> e.instant)).collect(Collectors.groupingBy(Event::getSubId));
        List<Integer> avgUsers = userVisits.values().stream().map(events -> {
            List<Event> enters = events.stream().filter(e -> e.eventType instanceof Enter).toList();
            return enters.size();
        }).toList();
        long result = avgUsers.stream().mapToInt(e -> e).sum() / avgUsers.size();
        modelMap.addAttribute("avgFrequency", result);
    }
}
