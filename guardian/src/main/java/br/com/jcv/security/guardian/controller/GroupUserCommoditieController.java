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

import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.service.GroupUserService;
import br.com.jcv.security.guardian.exception.GroupUserNotFoundException;
import br.com.jcv.security.guardian.constantes.GroupUserConstantes;

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
* GroupUserCommoditieController - Controller for GroupUser API
*
* @author GroupUser
* @since Sat Nov 18 18:21:13 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/groupuser")
public class GroupUserCommoditieController
{
     @Autowired private GroupUserService groupuserService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<GroupUserDTO>> findAllGroupUser() {
        try {
            List<GroupUserDTO> groupusers = groupuserService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (groupusers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(groupusers, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<GroupUserDTO>> findAllGroupUser(@PathVariable("status") String status) {
        try {
            List<GroupUserDTO> groupusers = groupuserService.findAllByStatus(status);

            if (groupusers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(groupusers, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<GroupUserDTO>> findAllGroupUserByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<GroupUserDTO> response = groupuserService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterGroupUserDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = groupuserService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroupUser(@PathVariable("id") long id) {
        try {
            groupuserService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(GroupUserNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createGroupUser(@RequestBody @Valid GroupUserDTO groupuserDTO) {
        try {
            GroupUserDTO groupuserSaved = groupuserService.salvar(groupuserDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(groupuserSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<GroupUserDTO> getGroupUserById(@PathVariable("id") Long id) {
      try {
           GroupUserDTO groupuserDTO = groupuserService.findById(id);

           if (groupuserDTO != null) {
               return new ResponseEntity<>(groupuserDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<GroupUserDTO> updateGroupUser(@PathVariable("id") Long id, @RequestBody @Valid GroupUserDTO groupuserDTO) {
        GroupUserDTO groupuserData = groupuserService.findById(id);

        if(groupuserData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            groupuserDTO.setId(id);
            groupuserDTO.setDateUpdated(LocalDateTime.now());
            GroupUserDTO groupuserSaved = groupuserService.salvar(groupuserDTO);
            return new ResponseEntity<>(groupuserSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        GroupUserDTO groupuserData = groupuserService.findById(id);
        if (groupuserData == null || !groupuserService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("GroupUser atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<GroupUserDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        GroupUserDTO groupuserUpdated = groupuserService.updateStatusById(id, status);
        return new ResponseEntity<>(groupuserUpdated, HttpStatus.OK);
      } catch(GroupUserNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<GroupUserDTO> findGroupUserById(@RequestParam(GroupUserConstantes.ID) Long id) {
        try{
            GroupUserDTO groupuserDTO = groupuserService.findGroupUserByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupuserDTO)
                ? new ResponseEntity<>(groupuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idUser")
    public ResponseEntity<GroupUserDTO> findGroupUserByIdUser(@RequestParam(GroupUserConstantes.IDUSER) Long idUser) {
        try{
            GroupUserDTO groupuserDTO = groupuserService.findGroupUserByIdUserAndStatus(idUser, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupuserDTO)
                ? new ResponseEntity<>(groupuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idGroup")
    public ResponseEntity<GroupUserDTO> findGroupUserByIdGroup(@RequestParam(GroupUserConstantes.IDGROUP) Long idGroup) {
        try{
            GroupUserDTO groupuserDTO = groupuserService.findGroupUserByIdGroupAndStatus(idGroup, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupuserDTO)
                ? new ResponseEntity<>(groupuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<GroupUserDTO> findGroupUserByDateCreated(@RequestParam(GroupUserConstantes.DATECREATED) Date dateCreated) {
        try{
            GroupUserDTO groupuserDTO = groupuserService.findGroupUserByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupuserDTO)
                ? new ResponseEntity<>(groupuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<GroupUserDTO> findGroupUserByDateUpdated(@RequestParam(GroupUserConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            GroupUserDTO groupuserDTO = groupuserService.findGroupUserByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(groupuserDTO)
                ? new ResponseEntity<>(groupuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
