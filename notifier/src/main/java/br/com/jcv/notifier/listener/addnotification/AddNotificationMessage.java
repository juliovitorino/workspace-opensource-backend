package br.com.jcv.notifier.listener.addnotification;

import br.com.jcv.notifier.controller.v1.business.addnotifier.AddNotifierRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNotificationMessage implements Serializable {
    private UUID processId;
    private AddNotifierRequest addNotificationRequest;
}
