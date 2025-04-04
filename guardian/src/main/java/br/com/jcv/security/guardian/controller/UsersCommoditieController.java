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

import br.com.jcv.security.guardian.dto.UsersDTO;
import br.com.jcv.security.guardian.service.UsersService;
import br.com.jcv.security.guardian.exception.UsersNotFoundException;
import br.com.jcv.security.guardian.constantes.UsersConstantes;

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
import java.time.LocalDate;
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
* UsersCommoditieController - Controller for Users API
*
* @author Users
* @since Thu Nov 16 09:03:29 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/users")
public class UsersCommoditieController
{
     @Autowired private UsersService usersService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<UsersDTO>> findAllUsers() {
        try {
            List<UsersDTO> userss = usersService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (userss.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userss, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<UsersDTO>> findAllUsers(@PathVariable("status") String status) {
        try {
            List<UsersDTO> userss = usersService.findAllByStatus(status);

            if (userss.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userss, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<UsersDTO>> findAllUsersByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<UsersDTO> response = usersService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterUsersDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = usersService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUsers(@PathVariable("id") long id) {
        try {
            usersService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(UsersNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createUsers(@RequestBody @Valid UsersDTO usersDTO) {
        try {
            UsersDTO usersSaved = usersService.salvar(usersDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(usersSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUsersById(@PathVariable("id") Long id) {
      try {
           UsersDTO usersDTO = usersService.findById(id);

           if (usersDTO != null) {
               return new ResponseEntity<>(usersDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUsers(@PathVariable("id") Long id, @RequestBody @Valid UsersDTO usersDTO) {
        UsersDTO usersData = usersService.findById(id);

        if(usersData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            usersDTO.setId(id);
            usersDTO.setDateUpdated(LocalDateTime.now());
            UsersDTO usersSaved = usersService.salvar(usersDTO);
            return new ResponseEntity<>(usersSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        UsersDTO usersData = usersService.findById(id);
        if (usersData == null || !usersService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Users atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<UsersDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        UsersDTO usersUpdated = usersService.updateStatusById(id, status);
        return new ResponseEntity<>(usersUpdated, HttpStatus.OK);
      } catch(UsersNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<UsersDTO> findUsersById(@RequestParam(UsersConstantes.ID) Long id) {
        try{
            UsersDTO usersDTO = usersService.findUsersByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(usersDTO)
                ? new ResponseEntity<>(usersDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsersNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "name")
    public ResponseEntity<UsersDTO> findUsersByName(@RequestParam(UsersConstantes.NAME) String name) {
        try{
            UsersDTO usersDTO = usersService.findUsersByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(usersDTO)
                ? new ResponseEntity<>(usersDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsersNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "birthday")
    public ResponseEntity<UsersDTO> findUsersByBirthday(@RequestParam(UsersConstantes.BIRTHDAY) LocalDate birthday) {
        try{
            UsersDTO usersDTO = usersService.findUsersByBirthdayAndStatus(birthday, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(usersDTO)
                ? new ResponseEntity<>(usersDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsersNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<UsersDTO> findUsersByDateCreated(@RequestParam(UsersConstantes.DATECREATED) Date dateCreated) {
        try{
            UsersDTO usersDTO = usersService.findUsersByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(usersDTO)
                ? new ResponseEntity<>(usersDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsersNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Users foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<UsersDTO> findUsersByDateUpdated(@RequestParam(UsersConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            UsersDTO usersDTO = usersService.findUsersByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(usersDTO)
                ? new ResponseEntity<>(usersDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsersNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
