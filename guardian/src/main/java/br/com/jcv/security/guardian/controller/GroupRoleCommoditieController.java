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

import br.com.jcv.security.guardian.dto.GroupRoleDTO;
import br.com.jcv.security.guardian.service.GroupRoleService;
import br.com.jcv.security.guardian.exception.GroupRoleNotFoundException;
import br.com.jcv.security.guardian.constantes.GroupRoleConstantes;

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
* GroupRoleCommoditieController - Controller for GroupRole API
*
* @author GroupRole
* @since Sat Nov 18 18:21:13 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/grouprole")
public class GroupRoleCommoditieController
{
     @Autowired private GroupRoleService grouproleService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<GroupRoleDTO>> findAllGroupRole() {
        try {
            List<GroupRoleDTO> grouproles = grouproleService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (grouproles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(grouproles, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<GroupRoleDTO>> findAllGroupRole(@PathVariable("status") String status) {
        try {
            List<GroupRoleDTO> grouproles = grouproleService.findAllByStatus(status);

            if (grouproles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(grouproles, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<GroupRoleDTO>> findAllGroupRoleByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<GroupRoleDTO> response = grouproleService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterGroupRoleDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = grouproleService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroupRole(@PathVariable("id") long id) {
        try {
            grouproleService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(GroupRoleNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createGroupRole(@RequestBody @Valid GroupRoleDTO grouproleDTO) {
        try {
            GroupRoleDTO grouproleSaved = grouproleService.salvar(grouproleDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(grouproleSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<GroupRoleDTO> getGroupRoleById(@PathVariable("id") Long id) {
      try {
           GroupRoleDTO grouproleDTO = grouproleService.findById(id);

           if (grouproleDTO != null) {
               return new ResponseEntity<>(grouproleDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<GroupRoleDTO> updateGroupRole(@PathVariable("id") Long id, @RequestBody @Valid GroupRoleDTO grouproleDTO) {
        GroupRoleDTO grouproleData = grouproleService.findById(id);

        if(grouproleData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            grouproleDTO.setId(id);
            grouproleDTO.setDateUpdated(new Date());
            GroupRoleDTO grouproleSaved = grouproleService.salvar(grouproleDTO);
            return new ResponseEntity<>(grouproleSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        GroupRoleDTO grouproleData = grouproleService.findById(id);
        if (grouproleData == null || !grouproleService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("GroupRole atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<GroupRoleDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        GroupRoleDTO grouproleUpdated = grouproleService.updateStatusById(id, status);
        return new ResponseEntity<>(grouproleUpdated, HttpStatus.OK);
      } catch(GroupRoleNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<GroupRoleDTO> findGroupRoleById(@RequestParam(GroupRoleConstantes.ID) Long id) {
        try{
            GroupRoleDTO grouproleDTO = grouproleService.findGroupRoleByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(grouproleDTO)
                ? new ResponseEntity<>(grouproleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idRole")
    public ResponseEntity<GroupRoleDTO> findGroupRoleByIdRole(@RequestParam(GroupRoleConstantes.IDROLE) Long idRole) {
        try{
            GroupRoleDTO grouproleDTO = grouproleService.findGroupRoleByIdRoleAndStatus(idRole, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(grouproleDTO)
                ? new ResponseEntity<>(grouproleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idGroup")
    public ResponseEntity<GroupRoleDTO> findGroupRoleByIdGroup(@RequestParam(GroupRoleConstantes.IDGROUP) Long idGroup) {
        try{
            GroupRoleDTO grouproleDTO = grouproleService.findGroupRoleByIdGroupAndStatus(idGroup, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(grouproleDTO)
                ? new ResponseEntity<>(grouproleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<GroupRoleDTO> findGroupRoleByDateCreated(@RequestParam(GroupRoleConstantes.DATECREATED) Date dateCreated) {
        try{
            GroupRoleDTO grouproleDTO = grouproleService.findGroupRoleByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(grouproleDTO)
                ? new ResponseEntity<>(grouproleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GroupRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<GroupRoleDTO> findGroupRoleByDateUpdated(@RequestParam(GroupRoleConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            GroupRoleDTO grouproleDTO = grouproleService.findGroupRoleByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(grouproleDTO)
                ? new ResponseEntity<>(grouproleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GroupRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
