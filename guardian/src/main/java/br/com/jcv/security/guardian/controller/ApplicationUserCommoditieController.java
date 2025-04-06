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

import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.service.ApplicationUserService;
import br.com.jcv.security.guardian.exception.ApplicationUserNotFoundException;
import br.com.jcv.security.guardian.constantes.ApplicationUserConstantes;

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
* ApplicationUserCommoditieController - Controller for ApplicationUser API
*
* @author ApplicationUser
* @since Thu Nov 16 09:03:29 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/applicationuser")
public class ApplicationUserCommoditieController
{
     @Autowired private ApplicationUserService applicationuserService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<ApplicationUserDTO>> findAllApplicationUser() {
        try {
            List<ApplicationUserDTO> applicationusers = applicationuserService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (applicationusers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(applicationusers, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<ApplicationUserDTO>> findAllApplicationUser(@PathVariable("status") String status) {
        try {
            List<ApplicationUserDTO> applicationusers = applicationuserService.findAllByStatus(status);

            if (applicationusers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(applicationusers, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<ApplicationUserDTO>> findAllApplicationUserByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<ApplicationUserDTO> response = applicationuserService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterApplicationUserDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = applicationuserService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteApplicationUser(@PathVariable("id") long id) {
        try {
            applicationuserService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(ApplicationUserNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createApplicationUser(@RequestBody @Valid ApplicationUserDTO applicationuserDTO) {
        try {
            ApplicationUserDTO applicationuserSaved = applicationuserService.salvar(applicationuserDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(applicationuserSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUserDTO> getApplicationUserById(@PathVariable("id") Long id) {
      try {
           ApplicationUserDTO applicationuserDTO = applicationuserService.findById(id);

           if (applicationuserDTO != null) {
               return new ResponseEntity<>(applicationuserDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationUserDTO> updateApplicationUser(@PathVariable("id") Long id, @RequestBody @Valid ApplicationUserDTO applicationuserDTO) {
        ApplicationUserDTO applicationuserData = applicationuserService.findById(id);

        if(applicationuserData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            applicationuserDTO.setId(id);
            applicationuserDTO.setDateUpdated(LocalDateTime.now());
            ApplicationUserDTO applicationuserSaved = applicationuserService.salvar(applicationuserDTO);
            return new ResponseEntity<>(applicationuserSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        ApplicationUserDTO applicationuserData = applicationuserService.findById(id);
        if (applicationuserData == null || !applicationuserService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("ApplicationUser atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ApplicationUserDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        ApplicationUserDTO applicationuserUpdated = applicationuserService.updateStatusById(id, status);
        return new ResponseEntity<>(applicationuserUpdated, HttpStatus.OK);
      } catch(ApplicationUserNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserById(@RequestParam(ApplicationUserConstantes.ID) Long id) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idUser")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByIdUser(@RequestParam(ApplicationUserConstantes.IDUSER) Long idUser) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByIdUserAndStatus(idUser, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "email")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByEmail(@RequestParam(ApplicationUserConstantes.EMAIL) String email) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByEmailAndStatus(email, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "encodedPassPhrase")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByEncodedPassPhrase(@RequestParam(ApplicationUserConstantes.ENCODEDPASSPHRASE) String encodedPassPhrase) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByEncodedPassPhraseAndStatus(encodedPassPhrase, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "externalAppUserUUID")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByExternalAppUserUUID(@RequestParam(ApplicationUserConstantes.EXTERNALAPPUSERUUID) UUID externalAppUserUUID) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByExternalAppUserUUIDAndStatus(externalAppUserUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "urlTokenActivation")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByUrlTokenActivation(@RequestParam(ApplicationUserConstantes.URLTOKENACTIVATION) String urlTokenActivation) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByUrlTokenActivationAndStatus(urlTokenActivation, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "activationCode")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByActivationCode(@RequestParam(ApplicationUserConstantes.ACTIVATIONCODE) String activationCode) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByActivationCodeAndStatus(activationCode, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dueDateActivation")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByDueDateActivation(@RequestParam(ApplicationUserConstantes.DUEDATEACTIVATION) Date dueDateActivation) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByDueDateActivationAndStatus(dueDateActivation, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByDateCreated(@RequestParam(ApplicationUserConstantes.DATECREATED) Date dateCreated) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ApplicationUser foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<ApplicationUserDTO> findApplicationUserByDateUpdated(@RequestParam(ApplicationUserConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            ApplicationUserDTO applicationuserDTO = applicationuserService.findApplicationUserByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(applicationuserDTO)
                ? new ResponseEntity<>(applicationuserDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ApplicationUserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
