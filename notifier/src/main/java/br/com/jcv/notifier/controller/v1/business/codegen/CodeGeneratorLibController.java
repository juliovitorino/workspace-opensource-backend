package br.com.jcv.notifier.controller.v1.business.codegen;

import br.com.jcv.codegen.codegenerator.dto.WritableCode;
import br.com.jcv.codegen.codegenerator.exception.CodeGeneratorFolderStructureNotFound;
import br.com.jcv.codegen.codegenerator.factory.codegen.ICodeGeneratorBatch;
import br.com.jcv.notifier.model.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/codegen")
public class CodeGeneratorLibController {

    @Autowired
    private @Qualifier("CodeGeneratorMainStreamInstance") ICodeGeneratorBatch generatorMainStream;
    @GetMapping("/model")
    public ResponseEntity generateCode() {
        try {

            List<WritableCode> BetCodes = generatorMainStream.generate(Notifier.class);
            generatorMainStream.flushCode(BetCodes);


            return ResponseEntity.ok().build();
        } catch(CodeGeneratorFolderStructureNotFound e) {
            return ResponseEntity.badRequest().build();
        }
    }
}