package br.com.jcv.treinadorpro.infrastructure.decoder;

public interface IPayloadLoginSocial {
    String getIss();
    String getAzp();
    String getAud();
    String getSub();
    String getEmail();
    String getEmailVerified();
    String getName();
    String getPicture();
    String getGivenName();
    String getFamilyName();
}
