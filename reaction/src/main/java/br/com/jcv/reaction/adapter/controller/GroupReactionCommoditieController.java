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
import br.com.jcv.reaction.corelayer.service.GroupReactionService;
import br.com.jcv.reaction.infrastructure.constantes.GroupReactionConstantes;
import br.com.jcv.reaction.infrastructure.dto.GroupReactionDTO;
import br.com.jcv.reaction.infrastructure.exception.GroupReactionNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* GroupReactionCommoditieController - Controller for GroupReaction API
*
* @author GroupReaction
* @since Tue May 28 16:22:55 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/groupreaction")
public class GroupReactionCommoditieController
{
     @Autowired private GroupReactionService groupreactionService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<GroupReactionDTO>> findAllGroupReaction() {
        try {
            List<GroupReactionDTO> groupreactions = groupreactionService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (groupreactions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(groupreactions, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<GroupReactionDTO>> findAllGroupReaction(@PathVariable("status") String status) {
        try {
            List<GroupReactionDTO> groupreactions = groupreactionService.findAllByStatus(status);

            if (groupreactions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(groupreactions, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<GroupReactionDTO>> findAllGroupReactionByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<GroupReactionDTO> response = groupreactionService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterGroupReactionDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = groupreactionService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroupReaction(@PathVariable("id") long id) {
        try {
            groupreactionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(GroupReactionNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createGroupReaction(@RequestBody @Valid GroupReactionDTO groupreactionDTO) {
        try {
            GroupReactionDTO groupreactionSaved = groupreactionService.salvar(groupreactionDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(groupreactionSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<GroupReactionDTO> getGroupReactionById(@PathVariable("id") Long id) {
      try {
           GroupReactionDTO groupreactionDTO = groupreactionService.findById(id);

           if (groupreactionDTO != null) {
               return new ResponseEntity<>(groupreactionDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<GroupReactionDTO> updateGroupReaction(@PathVariable("id") Long id, @RequestBody @Valid GroupReactionDTO groupreactionDTO) {
        GroupReactionDTO groupreactionData = groupreactionService.findById(id);

        if(groupreactionData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            groupreactionDTO.setId(id);
            groupreactionDTO.setDateUpdated(new Date());
            GroupReactionDTO groupreactionSaved = groupreactionService.salvar(groupreactionDTO);
            return new ResponseEntity<>(groupreactionSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        GroupReactionDTO groupreactionData = groupreactionService.findById(id);
        if (groupreactionData == null || !groupreactionService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("GroupReaction atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<GroupReactionDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        GroupReactionDTO groupreactionUpdated = groupreactionService.updateStatusById(id, status);
        return new ResponseEntity<>(groupreactionUpdated, HttpStatus.OK);
      } catch(GroupReactionNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<GroupReactionDTO> findGroupReactionById(@RequestParam(GroupReactionConstantes.ID) Long id) {
        try{
            GroupReactionDTO groupreactionDTO = groupreactionService.findGroupReactionByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupreactionDTO)
                ? new ResponseEntity<>(groupreactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "groupId")
    public ResponseEntity<GroupReactionDTO> findGroupReactionByGroupId(@RequestParam(GroupReactionConstantes.GROUPID) Long groupId) {
        try{
            GroupReactionDTO groupreactionDTO = groupreactionService.findGroupReactionByGroupIdAndStatus(groupId, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupreactionDTO)
                ? new ResponseEntity<>(groupreactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "reactionId")
    public ResponseEntity<GroupReactionDTO> findGroupReactionByReactionId(@RequestParam(GroupReactionConstantes.REACTIONID) Long reactionId) {
        try{
            GroupReactionDTO groupreactionDTO = groupreactionService.findGroupReactionByReactionIdAndStatus(reactionId, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupreactionDTO)
                ? new ResponseEntity<>(groupreactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<GroupReactionDTO> findGroupReactionByDateCreated(@RequestParam(GroupReactionConstantes.DATECREATED) Date dateCreated) {
        try{
            GroupReactionDTO groupreactionDTO = groupreactionService.findGroupReactionByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupreactionDTO)
                ? new ResponseEntity<>(groupreactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupReaction foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<GroupReactionDTO> findGroupReactionByDateUpdated(@RequestParam(GroupReactionConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            GroupReactionDTO groupreactionDTO = groupreactionService.findGroupReactionByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupreactionDTO)
                ? new ResponseEntity<>(groupreactionDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupReactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
