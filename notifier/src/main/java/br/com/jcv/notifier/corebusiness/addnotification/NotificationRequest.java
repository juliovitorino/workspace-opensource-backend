package br.com.jcv.notifier.corebusiness.addnotification;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationRequest implements Serializable
{
    private UUID uuidExternalApp;
    private UUID uuidExternalUser;
    private String type;
    private String key;
    private String title;
    private String description;
    private String urlImage;
    private String iconClass;
    private String urlFollow;
    private String objectFree;
    private String seenIndicator;
}
