package br.com.jcv.security.guardian.controller.v1.business.validateapplicationcode;

import java.util.UUID;

import br.com.jcv.commons.library.commodities.service.BusinessService;

/**
 * @deprecated Esta interface {@code ValidateApplicationCodeBusinessService} está obsoleta
 * e não deve mais ser utilizada.
 *
 * <p>
 * Motivo da depreciação: A validação de códigos de aplicação foi movida para um novo
 * fluxo centralizado em {@code ValidateAccountService} .
 * </p>
 *
 * <p>
 * Recomenda-se utilizar a nova abordagem para garantir compatibilidade com futuras versões.
 * </p>
 */
@Deprecated
public interface ValidateApplicationCodeBusinessService extends BusinessService<UUID, Boolean> {
}
