/*
Copyright <YEAR> <COPYRIGHT HOLDER>

This software is Open Source and is under MIT license agreement

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the “Software”), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions
of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
OR OTHER DEALINGS IN THE SOFTWARE.
*/


package br.com.jcv.security.guardian.controller;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;

import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.service.SessionStateService;
import br.com.jcv.security.guardian.exception.SessionStateNotFoundException;
import br.com.jcv.security.guardian.constantes.SessionStateConstantes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* SessionStateCommoditieController - Controller for SessionState API
*
* @author SessionState
* @since Sun Oct 29 15:32:38 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/sessionstate")
public class SessionStateCommoditieController
{
     @Autowired private SessionStateService sessionstateService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<SessionStateDTO>> findAllSessionState() {
        try {
            List<SessionStateDTO> sessionstates = sessionstateService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (sessionstates.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sessionstates, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<SessionStateDTO>> findAllSessionState(@PathVariable("status") String status) {
        try {
            List<SessionStateDTO> sessionstates = sessionstateService.findAllByStatus(status);

            if (sessionstates.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sessionstates, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<SessionStateDTO>> findAllSessionStateByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<SessionStateDTO> response = sessionstateService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterSessionStateDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = sessionstateService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSessionState(@PathVariable("id") long id) {
        try {
            sessionstateService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(SessionStateNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createSessionState(@RequestBody @Valid SessionStateDTO sessionstateDTO) {
        try {
            SessionStateDTO sessionstateSaved = sessionstateService.salvar(sessionstateDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(sessionstateSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<SessionStateDTO> getSessionStateById(@PathVariable("id") Long id) {
      try {
           SessionStateDTO sessionstateDTO = sessionstateService.findById(id);

           if (sessionstateDTO != null) {
               return new ResponseEntity<>(sessionstateDTO, HttpStatus.OK);
           } else {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
      } catch (CommoditieBaseException e) {
          return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
          return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<SessionStateDTO> updateSessionState(@PathVariable("id") Long id, @RequestBody @Valid SessionStateDTO sessionstateDTO) {
        SessionStateDTO sessionstateData = sessionstateService.findById(id);

        if(sessionstateData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            sessionstateDTO.setId(id);
            sessionstateDTO.setDateUpdated(LocalDateTime.now());
            SessionStateDTO sessionstateSaved = sessionstateService.salvar(sessionstateDTO);
            return new ResponseEntity<>(sessionstateSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        SessionStateDTO sessionstateData = sessionstateService.findById(id);
        if (sessionstateData == null || !sessionstateService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("SessionState atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<SessionStateDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        SessionStateDTO sessionstateUpdated = sessionstateService.updateStatusById(id, status);
        return new ResponseEntity<>(sessionstateUpdated, HttpStatus.OK);
      } catch(SessionStateNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<SessionStateDTO> findSessionStateById(@RequestParam(SessionStateConstantes.ID) Long id) {
        try{
            SessionStateDTO sessionstateDTO = sessionstateService.findSessionStateByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(sessionstateDTO)
                ? new ResponseEntity<>(sessionstateDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SessionStateNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idToken")
    public ResponseEntity<SessionStateDTO> findSessionStateByIdToken(@RequestParam(SessionStateConstantes.IDTOKEN) UUID idToken) {
        try{
            SessionStateDTO sessionstateDTO = sessionstateService.findSessionStateByIdTokenAndStatus(idToken, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(sessionstateDTO)
                ? new ResponseEntity<>(sessionstateDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SessionStateNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idUserUUID")
    public ResponseEntity<SessionStateDTO> findSessionStateByIdUserUUID(@RequestParam(SessionStateConstantes.IDUSERUUID) UUID idUserUUID) {
        try{
            SessionStateDTO sessionstateDTO = sessionstateService.findSessionStateByIdUserUUIDAndStatus(idUserUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(sessionstateDTO)
                ? new ResponseEntity<>(sessionstateDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SessionStateNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<SessionStateDTO> findSessionStateByDateCreated(@RequestParam(SessionStateConstantes.DATECREATED) Date dateCreated) {
        try{
            SessionStateDTO sessionstateDTO = sessionstateService.findSessionStateByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(sessionstateDTO)
                ? new ResponseEntity<>(sessionstateDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SessionStateNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SessionState foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<SessionStateDTO> findSessionStateByDateUpdated(@RequestParam(SessionStateConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            SessionStateDTO sessionstateDTO = sessionstateService.findSessionStateByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(sessionstateDTO)
                ? new ResponseEntity<>(sessionstateDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SessionStateNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
