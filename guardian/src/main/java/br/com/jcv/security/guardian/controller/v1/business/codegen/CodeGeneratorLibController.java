package br.com.jcv.security.guardian.controller.v1.business.codegen;

import br.com.jcv.codegen.codegenerator.dto.WritableCode;
import br.com.jcv.codegen.codegenerator.exception.CodeGeneratorFolderStructureNotFound;
import br.com.jcv.codegen.codegenerator.factory.codegen.ICodeGeneratorBatch;
import br.com.jcv.security.guardian.model.ApplicationUser;
import br.com.jcv.security.guardian.model.GApplication;
import br.com.jcv.security.guardian.model.Users;
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

//            List<WritableCode> BetCodes = generatorMainStream.generate(SessionState.class);
//            generatorMainStream.flushCode(BetCodes);
//
//            List<WritableCode> codes = generatorMainStream.generate(GApplication.class);
//            generatorMainStream.flushCode(codes);

            List<WritableCode> codes = generatorMainStream.generate(Users.class);
            generatorMainStream.flushCode(codes);

            List<WritableCode> auCode = generatorMainStream.generate(ApplicationUser.class);
            generatorMainStream.flushCode(auCode);

            return ResponseEntity.ok().build();
        } catch(CodeGeneratorFolderStructureNotFound e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
