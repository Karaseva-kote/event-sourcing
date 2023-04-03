package ru.karaseva.sd.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.karaseva.sd.event.dao.EventDao;
import ru.karaseva.sd.event.dao.EventInMemoryDao;

@Configuration
public class EventSourcingConfiguration {
    @Bean
    public EventDao eventDao() {
        return new EventInMemoryDao();
    }
}
