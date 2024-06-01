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


package br.com.jcv.reaction.adapter.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.reaction.corelayer.service.ReactionEventService;
import br.com.jcv.reaction.infrastructure.constantes.ReactionEventConstantes;
import br.com.jcv.reaction.infrastructure.dto.ReactionEventDTO;
import br.com.jcv.reaction.infrastructure.exception.ReactionEventNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* ReactionEventCommoditieController - Controller for ReactionEvent API
*
* @author ReactionEvent
* @since Tue May 28 16:48:27 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/reactionevent")
public class ReactionEventCommoditieController
{
     @Autowired private ReactionEventService reactioneventService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<ReactionEventDTO>> findAllReactionEvent() {
        try {
            List<ReactionEventDTO> reactionevents = reactioneventService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (reactionevents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reactionevents, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<ReactionEventDTO>> findAllReactionEvent(@PathVariable("status") String status) {
        try {
            List<ReactionEventDTO> reactionevents = reactioneventService.findAllByStatus(status);

            if (reactionevents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reactionevents, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<ReactionEventDTO>> findAllReactionEventByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<ReactionEventDTO> response = reactioneventService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterReactionEventDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = reactioneventService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReactionEvent(@PathVariable("id") long id) {
        try {
            reactioneventService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(ReactionEventNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createReactionEvent(@RequestBody @Valid ReactionEventDTO reactioneventDTO) {
        try {
            ReactionEventDTO reactioneventSaved = reactioneventService.salvar(reactioneventDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(reactioneventSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<ReactionEventDTO> getReactionEventById(@PathVariable("id") Long id) {
      try {
           ReactionEventDTO reactioneventDTO = reactioneventService.findById(id);

           if (reactioneventDTO != null) {
               return new ResponseEntity<>(reactioneventDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<ReactionEventDTO> updateReactionEvent(@PathVariable("id") Long id, @RequestBody @Valid ReactionEventDTO reactioneventDTO) {
        ReactionEventDTO reactioneventData = reactioneventService.findById(id);

        if(reactioneventData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            reactioneventDTO.setId(id);
            reactioneventDTO.setDateUpdated(new Date());
            ReactionEventDTO reactioneventSaved = reactioneventService.salvar(reactioneventDTO);
            return new ResponseEntity<>(reactioneventSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        ReactionEventDTO reactioneventData = reactioneventService.findById(id);
        if (reactioneventData == null || !reactioneventService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("ReactionEvent atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ReactionEventDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        ReactionEventDTO reactioneventUpdated = reactioneventService.updateStatusById(id, status);
        return new ResponseEntity<>(reactioneventUpdated, HttpStatus.OK);
      } catch(ReactionEventNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<ReactionEventDTO> findReactionEventById(@RequestParam(ReactionEventConstantes.ID) Long id) {
        try{
            ReactionEventDTO reactioneventDTO = reactioneventService.findReactionEventByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactioneventDTO)
                ? new ResponseEntity<>(reactioneventDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionEventNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "reactionId")
    public ResponseEntity<ReactionEventDTO> findReactionEventByReactionId(@RequestParam(ReactionEventConstantes.REACTIONID) Long reactionId) {
        try{
            ReactionEventDTO reactioneventDTO = reactioneventService.findReactionEventByReactionIdAndStatus(reactionId, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactioneventDTO)
                ? new ResponseEntity<>(reactioneventDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionEventNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "externalItemUUID")
    public ResponseEntity<ReactionEventDTO> findReactionEventByExternalItemUUID(@RequestParam(ReactionEventConstantes.EXTERNALITEMUUID) Long externalItemUUID) {
        try{
            ReactionEventDTO reactioneventDTO = reactioneventService.findReactionEventByExternalItemUUIDAndStatus(externalItemUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactioneventDTO)
                ? new ResponseEntity<>(reactioneventDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionEventNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "externalAppUUID")
    public ResponseEntity<ReactionEventDTO> findReactionEventByExternalAppUUID(@RequestParam(ReactionEventConstantes.EXTERNALAPPUUID) Long externalAppUUID) {
        try{
            ReactionEventDTO reactioneventDTO = reactioneventService.findReactionEventByExternalAppUUIDAndStatus(externalAppUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactioneventDTO)
                ? new ResponseEntity<>(reactioneventDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionEventNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "externalUserUUID")
    public ResponseEntity<ReactionEventDTO> findReactionEventByExternalUserUUID(@RequestParam(ReactionEventConstantes.EXTERNALUSERUUID) Long externalUserUUID) {
        try{
            ReactionEventDTO reactioneventDTO = reactioneventService.findReactionEventByExternalUserUUIDAndStatus(externalUserUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactioneventDTO)
                ? new ResponseEntity<>(reactioneventDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionEventNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<ReactionEventDTO> findReactionEventByDateCreated(@RequestParam(ReactionEventConstantes.DATECREATED) Date dateCreated) {
        try{
            ReactionEventDTO reactioneventDTO = reactioneventService.findReactionEventByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactioneventDTO)
                ? new ResponseEntity<>(reactioneventDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionEventNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReactionEvent foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<ReactionEventDTO> findReactionEventByDateUpdated(@RequestParam(ReactionEventConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            ReactionEventDTO reactioneventDTO = reactioneventService.findReactionEventByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactioneventDTO)
                ? new ResponseEntity<>(reactioneventDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionEventNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
