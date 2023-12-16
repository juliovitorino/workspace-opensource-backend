package br.com.jcv.security.guardian.controller.v1.business.scheduling.cleanoldersessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ThereWillBeOnlyOneSessionController {

    @Autowired private ThereWillBeOnlyOneSessionBusinessService cleanOldestSessionBusinessService;

    @Scheduled(cron = "0 */3 * * * *")
    public void executeCleanOldestSession() {
        cleanOldestSessionBusinessService.execute(UUID.randomUUID(), "schedule-clean-oldest-session");

    }
}
