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

import br.com.jcv.security.guardian.dto.UserRoleDTO;
import br.com.jcv.security.guardian.service.UserRoleService;
import br.com.jcv.security.guardian.exception.UserRoleNotFoundException;
import br.com.jcv.security.guardian.constantes.UserRoleConstantes;

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
* UserRoleCommoditieController - Controller for UserRole API
*
* @author UserRole
* @since Sat Nov 18 18:21:14 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/userrole")
public class UserRoleCommoditieController
{
     @Autowired private UserRoleService userroleService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<UserRoleDTO>> findAllUserRole() {
        try {
            List<UserRoleDTO> userroles = userroleService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (userroles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userroles, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<UserRoleDTO>> findAllUserRole(@PathVariable("status") String status) {
        try {
            List<UserRoleDTO> userroles = userroleService.findAllByStatus(status);

            if (userroles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userroles, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<UserRoleDTO>> findAllUserRoleByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<UserRoleDTO> response = userroleService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterUserRoleDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = userroleService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserRole(@PathVariable("id") long id) {
        try {
            userroleService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(UserRoleNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createUserRole(@RequestBody @Valid UserRoleDTO userroleDTO) {
        try {
            UserRoleDTO userroleSaved = userroleService.salvar(userroleDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(userroleSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDTO> getUserRoleById(@PathVariable("id") Long id) {
      try {
           UserRoleDTO userroleDTO = userroleService.findById(id);

           if (userroleDTO != null) {
               return new ResponseEntity<>(userroleDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDTO> updateUserRole(@PathVariable("id") Long id, @RequestBody @Valid UserRoleDTO userroleDTO) {
        UserRoleDTO userroleData = userroleService.findById(id);

        if(userroleData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            userroleDTO.setId(id);
            userroleDTO.setDateUpdated(LocalDateTime.now());
            UserRoleDTO userroleSaved = userroleService.salvar(userroleDTO);
            return new ResponseEntity<>(userroleSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        UserRoleDTO userroleData = userroleService.findById(id);
        if (userroleData == null || !userroleService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("UserRole atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<UserRoleDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        UserRoleDTO userroleUpdated = userroleService.updateStatusById(id, status);
        return new ResponseEntity<>(userroleUpdated, HttpStatus.OK);
      } catch(UserRoleNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<UserRoleDTO> findUserRoleById(@RequestParam(UserRoleConstantes.ID) Long id) {
        try{
            UserRoleDTO userroleDTO = userroleService.findUserRoleByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userroleDTO)
                ? new ResponseEntity<>(userroleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idRole")
    public ResponseEntity<UserRoleDTO> findUserRoleByIdRole(@RequestParam(UserRoleConstantes.IDROLE) Long idRole) {
        try{
            UserRoleDTO userroleDTO = userroleService.findUserRoleByIdRoleAndStatus(idRole, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userroleDTO)
                ? new ResponseEntity<>(userroleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idUser")
    public ResponseEntity<UserRoleDTO> findUserRoleByIdUser(@RequestParam(UserRoleConstantes.IDUSER) Long idUser) {
        try{
            UserRoleDTO userroleDTO = userroleService.findUserRoleByIdUserAndStatus(idUser, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userroleDTO)
                ? new ResponseEntity<>(userroleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<UserRoleDTO> findUserRoleByDateCreated(@RequestParam(UserRoleConstantes.DATECREATED) Date dateCreated) {
        try{
            UserRoleDTO userroleDTO = userroleService.findUserRoleByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userroleDTO)
                ? new ResponseEntity<>(userroleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserRole foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<UserRoleDTO> findUserRoleByDateUpdated(@RequestParam(UserRoleConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            UserRoleDTO userroleDTO = userroleService.findUserRoleByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userroleDTO)
                ? new ResponseEntity<>(userroleDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserRoleNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
