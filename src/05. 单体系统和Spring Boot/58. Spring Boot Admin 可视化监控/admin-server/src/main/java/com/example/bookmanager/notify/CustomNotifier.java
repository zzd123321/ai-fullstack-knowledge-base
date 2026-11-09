package com.example.bookmanager.notify;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomNotifier extends AbstractEventNotifier {

    private static final Logger log = LoggerFactory.getLogger(CustomNotifier.class);

    protected CustomNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {

        log.info("[自定义通知]type={}, instance={}, timestamp={}",
                event.getType(),
                instance.getRegistration().getName(),
                event.getTimestamp());
        return Mono.empty();
    }
}
