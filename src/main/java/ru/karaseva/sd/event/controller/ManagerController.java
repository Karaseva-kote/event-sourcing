package ru.karaseva.sd.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karaseva.sd.event.dao.EventDao;
import ru.karaseva.sd.event.model.Event;
import ru.karaseva.sd.event.model.NewSubscription;
import ru.karaseva.sd.event.model.ProlongSubscription;

import java.time.Duration;
import java.util.List;

@Controller
public class ManagerController {
    private final EventDao dao;
    private List<Event> showedSubInfo;
    private int prolongedSubId;

    public ManagerController(EventDao dao) {
        this.dao = dao;
    }

    @GetMapping("/manager")
    public String managerAction() {
        return "manager";
    }

    @GetMapping("/manager/subscription_info")
    public String subscriptionInfo(ModelMap modelMap) {
        modelMap.addAttribute("events", showedSubInfo);
        return "subscription_info";
    }

    @GetMapping("/manager/subscription_prolong")
    public String subscriptionProlong(ModelMap modelMap) {
        modelMap.addAttribute("subId", prolongedSubId);
        return "subscription_prolong";
    }

    @GetMapping("/manager/new_subscription")
    public String newSubscription(ModelMap modelMap) {
        Event newSub = new Event(new NewSubscription());
        dao.addEvent(newSub);
        modelMap.addAttribute("subId", newSub.subId);
        return "new_subscription";
    }

    @PostMapping("/manager/get_subscription_info")
    public String subscriptionInfo(@RequestParam("subId") int subId) {
        showedSubInfo = dao.getEvents().stream().filter(e -> e.subId == subId).toList();
        return "redirect:/manager/subscription_info";
    }

    @PostMapping("/manager/prolong_subscription")
    public String prolongSubscription(@RequestParam("subId") int subId) {
        prolongedSubId = subId;
        return "redirect:/manager/subscription_prolong";
    }

    @PostMapping("/manager/prolong_subscription_days")
    public String prolongSubscriptionDays(@RequestParam("days") int days) {
        dao.addEvent(new Event(prolongedSubId, new ProlongSubscription(Duration.ofDays(days))));
        return "redirect:/manager/";
    }
}
