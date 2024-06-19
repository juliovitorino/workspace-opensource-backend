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


package br.com.jcv.reaction.corelayer.controller;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;

import br.com.jcv.reaction.corelayer.dto.ReactionDTO;
import br.com.jcv.reaction.corelayer.service.ReactionService;
import br.com.jcv.reaction.corelayer.exception.ReactionNotFoundException;
import br.com.jcv.reaction.corelayer.constantes.ReactionConstantes;

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
* ReactionCommoditieController - Controller for Reaction API
*
* @author Reaction
* @since Wed Jun 05 13:45:31 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/reaction")
public class ReactionCommoditieController
{
     @Autowired private ReactionService reactionService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<ReactionDTO>> findAllReaction() {
        try {
            List<ReactionDTO> reactions = reactionService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (reactions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reactions, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<ReactionDTO>> findAllReaction(@PathVariable("status") String status) {
        try {
            List<ReactionDTO> reactions = reactionService.findAllByStatus(status);

            if (reactions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reactions, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<ReactionDTO>> findAllReactionByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<ReactionDTO> response = reactionService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterReactionDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = reactionService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReaction(@PathVariable("id") long id) {
        try {
            reactionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(ReactionNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createReaction(@RequestBody @Valid ReactionDTO reactionDTO) {
        try {
            ReactionDTO reactionSaved = reactionService.salvar(reactionDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(reactionSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<ReactionDTO> getReactionById(@PathVariable("id") Long id) {
      try {
           ReactionDTO reactionDTO = reactionService.findById(id);

           if (reactionDTO != null) {
               return new ResponseEntity<>(reactionDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<ReactionDTO> updateReaction(@PathVariable("id") Long id, @RequestBody @Valid ReactionDTO reactionDTO) {
        ReactionDTO reactionData = reactionService.findById(id);

        if(reactionData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            reactionDTO.setId(id);
            reactionDTO.setDateUpdated(new Date());
            ReactionDTO reactionSaved = reactionService.salvar(reactionDTO);
            return new ResponseEntity<>(reactionSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        ReactionDTO reactionData = reactionService.findById(id);
        if (reactionData == null || !reactionService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Reaction atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ReactionDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        ReactionDTO reactionUpdated = reactionService.updateStatusById(id, status);
        return new ResponseEntity<>(reactionUpdated, HttpStatus.OK);
      } catch(ReactionNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<ReactionDTO> findReactionById(@RequestParam(ReactionConstantes.ID) Long id) {
        try{
            ReactionDTO reactionDTO = reactionService.findReactionByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactionDTO)
                ? new ResponseEntity<>(reactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "name")
    public ResponseEntity<ReactionDTO> findReactionByName(@RequestParam(ReactionConstantes.NAME) String name) {
        try{
            ReactionDTO reactionDTO = reactionService.findReactionByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactionDTO)
                ? new ResponseEntity<>(reactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "icon")
    public ResponseEntity<ReactionDTO> findReactionByIcon(@RequestParam(ReactionConstantes.ICON) String icon) {
        try{
            ReactionDTO reactionDTO = reactionService.findReactionByIconAndStatus(icon, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactionDTO)
                ? new ResponseEntity<>(reactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "tag")
    public ResponseEntity<ReactionDTO> findReactionByTag(@RequestParam(ReactionConstantes.TAG) String tag) {
        try{
            ReactionDTO reactionDTO = reactionService.findReactionByTagAndStatus(tag, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactionDTO)
                ? new ResponseEntity<>(reactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<ReactionDTO> findReactionByDateCreated(@RequestParam(ReactionConstantes.DATECREATED) Date dateCreated) {
        try{
            ReactionDTO reactionDTO = reactionService.findReactionByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactionDTO)
                ? new ResponseEntity<>(reactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<ReactionDTO> findReactionByDateUpdated(@RequestParam(ReactionConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            ReactionDTO reactionDTO = reactionService.findReactionByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reactionDTO)
                ? new ResponseEntity<>(reactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
