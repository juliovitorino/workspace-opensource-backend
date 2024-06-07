package br.com.jcv.brcities.adapter.controller.v1.business.codegen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcv.brcities.corelayer.model.City;
import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.codegen.codegenerator.dto.WritableCode;
import br.com.jcv.codegen.codegenerator.exception.CodeGeneratorFolderStructureNotFound;
import br.com.jcv.codegen.codegenerator.factory.codegen.ICodeGeneratorBatch;

@RestController
@RequestMapping("/v1/api/codegen")
public class CodeGeneratorLibController {

    @Autowired
    private @Qualifier("CodeGeneratorMainStreamInstance") ICodeGeneratorBatch generatorMainStream;
    @GetMapping("/model")
    public ResponseEntity generateCode() {
        try {

            List<WritableCode> one = generatorMainStream.generate(CityUf.class);
            generatorMainStream.flushCode(one);

            return ResponseEntity.ok().build();
        } catch(CodeGeneratorFolderStructureNotFound e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
