package ru.karaseva.sd.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karaseva.sd.event.dao.EventDao;
import ru.karaseva.sd.event.model.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
public class TurnstileController {
    private final EventDao dao;

    public TurnstileController(EventDao dao) {
        this.dao = dao;
    }

    @GetMapping("/turnstile")
    public String turnstile() {
        return "turnstile";
    }

    @GetMapping("/turnstile/subscription_expired")
    public String subscriptionExpired() {
        return "subscription_expired";
    }

    @PostMapping("/turnstile/enter")
    public String enter(@RequestParam("subId") int subId) {
        List<Event> subInfo = dao.getEvents().stream().filter(event -> event.subId == subId).toList();
        Optional<Event> lastProlong = subInfo.stream()
                .filter(event -> event.eventType instanceof ProlongSubscription).findFirst();
        if (lastProlong.isPresent()) {
            Event last = lastProlong.get();
            Instant endTime = last.instant.plus(((ProlongSubscription) last.eventType).duration);
            if (Instant.now().isBefore(endTime)) {
                dao.addEvent(new Event(subId, new Enter()));
                return "redirect:/turnstile";
            }
        }
        return "redirect:/turnstile/subscription_expired";
    }

    @PostMapping("/turnstile/exit")
    public String exit(@RequestParam("subId") int subId) {
        dao.addEvent(new Event(subId, new Exit()));
        return "redirect:/turnstile";
    }
}
