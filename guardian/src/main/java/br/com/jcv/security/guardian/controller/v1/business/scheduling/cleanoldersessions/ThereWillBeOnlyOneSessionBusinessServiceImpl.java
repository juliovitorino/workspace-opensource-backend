package br.com.jcv.security.guardian.controller.v1.business.scheduling.cleanoldersessions;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.security.guardian.controller.v1.business.ControllerGenericResponse;
import br.com.jcv.security.guardian.repository.SessionStateRepository;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class ThereWillBeOnlyOneSessionBusinessServiceImpl extends AbstractGuardianBusinessService
        implements ThereWillBeOnlyOneSessionBusinessService {
    @Autowired private SessionStateRepository repository;
    @Override
    @Transactional(transactionManager="transactionManager",
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Throwable.class
    )
    public ControllerGenericResponse execute(UUID processId, String s) {

        List<String> sessionList = repository.findAllOldestSessionByStatus(GenericStatusEnums.ATIVO.getShortValue());

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for(String userItem : sessionList) {
            executorService.submit(() -> {
                    UUID userUUID = UUID.fromString(userItem);
                    Long maxSessionId = repository.loadMaxIdByIdUserUUIDAndStatus(userUUID, GenericStatusEnums.ATIVO.getShortValue());
                    repository.deleteByIdUserUUIDAndPreviousSessionId(userUUID,maxSessionId);
            });
        }

        executorService.shutdown();

        return ControllerGenericResponse.builder()
                .response(MensagemResponse.builder()
                        .msgcode("GRDN-1653")
                        .mensagem("Your job has been processed using threads.")
                        .build())
                .build();
    }
}
