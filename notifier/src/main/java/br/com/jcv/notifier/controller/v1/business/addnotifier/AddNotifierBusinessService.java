package br.com.jcv.notifier.controller.v1.business.addnotifier;

import br.com.jcv.commons.library.commodities.service.BusinessService;
import br.com.jcv.notifier.controller.ControllerGenericResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface AddNotifierBusinessService extends BusinessService<AddNotifierRequest, ControllerGenericResponse> {

}
