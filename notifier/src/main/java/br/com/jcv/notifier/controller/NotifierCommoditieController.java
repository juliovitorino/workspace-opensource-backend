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


package br.com.jcv.notifier.controller;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;

import br.com.jcv.notifier.dto.NotifierDTO;
import br.com.jcv.notifier.service.NotifierService;
import br.com.jcv.notifier.exception.NotifierNotFoundException;
import br.com.jcv.notifier.constantes.NotifierConstantes;

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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* NotifierCommoditieController - Controller for Notifier API
*
* @author Notifier
* @since Sat Dec 16 12:29:21 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/notifier")
public class NotifierCommoditieController
{
     @Autowired private NotifierService notifierService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<NotifierDTO>> findAllNotifier() {
        try {
            List<NotifierDTO> notifiers = notifierService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (notifiers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(notifiers, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<NotifierDTO>> findAllNotifier(@PathVariable("status") String status) {
        try {
            List<NotifierDTO> notifiers = notifierService.findAllByStatus(status);

            if (notifiers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(notifiers, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<NotifierDTO>> findAllNotifierByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<NotifierDTO> response = notifierService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterNotifierDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = notifierService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNotifier(@PathVariable("id") long id) {
        try {
            notifierService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(NotifierNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createNotifier(@RequestBody @Valid NotifierDTO notifierDTO) {
        try {
            NotifierDTO notifierSaved = notifierService.salvar(notifierDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(notifierSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<NotifierDTO> getNotifierById(@PathVariable("id") Long id) {
      try {
           NotifierDTO notifierDTO = notifierService.findById(id);

           if (notifierDTO != null) {
               return new ResponseEntity<>(notifierDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<NotifierDTO> updateNotifier(@PathVariable("id") Long id, @RequestBody @Valid NotifierDTO notifierDTO) {
        NotifierDTO notifierData = notifierService.findById(id);

        if(notifierData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            notifierDTO.setId(id);
            notifierDTO.setDateUpdated(new Date());
            NotifierDTO notifierSaved = notifierService.salvar(notifierDTO);
            return new ResponseEntity<>(notifierSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        NotifierDTO notifierData = notifierService.findById(id);
        if (notifierData == null || !notifierService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Notifier atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<NotifierDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        NotifierDTO notifierUpdated = notifierService.updateStatusById(id, status);
        return new ResponseEntity<>(notifierUpdated, HttpStatus.OK);
      } catch(NotifierNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<NotifierDTO> findNotifierById(@RequestParam(NotifierConstantes.ID) Long id) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "applicationUUID")
    public ResponseEntity<NotifierDTO> findNotifierByApplicationUUID(@RequestParam(NotifierConstantes.APPLICATIONUUID) UUID applicationUUID) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByApplicationUUIDAndStatus(applicationUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "userUUID")
    public ResponseEntity<NotifierDTO> findNotifierByUserUUID(@RequestParam(NotifierConstantes.USERUUID) UUID userUUID) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByUserUUIDAndStatus(userUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "type")
    public ResponseEntity<NotifierDTO> findNotifierByType(@RequestParam(NotifierConstantes.TYPE) String type) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByTypeAndStatus(type, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "title")
    public ResponseEntity<NotifierDTO> findNotifierByTitle(@RequestParam(NotifierConstantes.TITLE) String title) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "description")
    public ResponseEntity<NotifierDTO> findNotifierByDescription(@RequestParam(NotifierConstantes.DESCRIPTION) String description) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByDescriptionAndStatus(description, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "isReaded")
    public ResponseEntity<NotifierDTO> findNotifierByIsReaded(@RequestParam(NotifierConstantes.ISREADED) String isReaded) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByIsReadedAndStatus(isReaded, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<NotifierDTO> findNotifierByDateCreated(@RequestParam(NotifierConstantes.DATECREATED) Date dateCreated) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Notifier foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<NotifierDTO> findNotifierByDateUpdated(@RequestParam(NotifierConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            NotifierDTO notifierDTO = notifierService.findNotifierByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(notifierDTO)
                ? new ResponseEntity<>(notifierDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotifierNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
