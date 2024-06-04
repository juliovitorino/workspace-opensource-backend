package br.com.jcv.brcities.businesslayer.uf;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.brcities.adapter.controller.v1.business.uf.UfResponse;
import br.com.jcv.brcities.corelayer.service.UfService;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListAllFederationUnitBusinessServiceImpl implements ListAllFederationUnitBusinessService {
    @Autowired private UfService ufService;

    @Override
    public List<UfResponse> execute(UUID processId, Boolean aBoolean) {
        return ufService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue())
                .stream()
                .map( uf -> {
                    UfResponse response = new UfResponse();
                    response.setUf(uf.getNickname());
                    response.setName(uf.getName());
                    return response;
                }).collect(Collectors.toList());
    }
}
