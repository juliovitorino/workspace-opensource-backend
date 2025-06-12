package br.com.jcv.treinadorpro.shared;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataIntegrityViolationMapper {

    public String getFriendlyMessage(String stacktraceMessage) {
        String key = extractConstraintKey(stacktraceMessage);
        DataIntegrityViolationEnum dataIntegrityViolationEnum = DataIntegrityViolationEnum.fromKey(key);
        return dataIntegrityViolationEnum != null ? dataIntegrityViolationEnum.friendlyMessage : null;
    }

    /**
     * Localiza o termo "constraint [ xxxxx ]" em uma String e retorna a chave (xxxxx).
     *
     * @param text A String onde a busca será realizada.
     * @return A chave encontrada (xxxxx), ou null se o termo não for encontrado.
     */
    private String extractConstraintKey(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        // A expressão regular para encontrar "constraint [ xxxxx ]"
        // \\s* : zero ou mais espaços em branco (para flexibilidade)
        // \\[ : literalmente um colchete de abertura
        // ([^\\]]+) : grupo de captura que pega um ou mais caracteres que NÃO são um colchete de fechamento.
        // \\] : literalmente um colchete de fechamento
        String regex = "constraint\\s*\\[\\s*([^\\]]+?)\\s*\\]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            // O grupo 1 da expressão regular contém a chave (xxxxx)
            return matcher.group(1).trim(); // Remove espaços em branco extras da chave
        } else {
            return null; // Termo não encontrado
        }
    }

}
