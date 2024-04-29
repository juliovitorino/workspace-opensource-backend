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


package br.com.jcv.preferences.adapter;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
import br.com.jcv.preferences.corelayer.service.UserPreferencesService;
import br.com.jcv.preferences.infrastructure.constantes.UserPreferencesConstantes;
import br.com.jcv.preferences.infrastructure.dto.UserPreferencesDTO;
import br.com.jcv.preferences.infrastructure.exception.UserPreferencesNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* UserPreferencesCommoditieController - Controller for UserPreferences API
*
* @author UserPreferences
* @since Mon Apr 29 16:40:18 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/userpreferences")
public class UserPreferencesCommoditieController
{
     @Autowired private UserPreferencesService userpreferencesService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<UserPreferencesDTO>> findAllUserPreferences() {
        try {
            List<UserPreferencesDTO> userpreferencess = userpreferencesService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (userpreferencess.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userpreferencess, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<UserPreferencesDTO>> findAllUserPreferences(@PathVariable("status") String status) {
        try {
            List<UserPreferencesDTO> userpreferencess = userpreferencesService.findAllByStatus(status);

            if (userpreferencess.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userpreferencess, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<UserPreferencesDTO>> findAllUserPreferencesByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<UserPreferencesDTO> response = userpreferencesService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterUserPreferencesDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = userpreferencesService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserPreferences(@PathVariable("id") long id) {
        try {
            userpreferencesService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(UserPreferencesNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createUserPreferences(@RequestBody @Valid UserPreferencesDTO userpreferencesDTO) {
        try {
            UserPreferencesDTO userpreferencesSaved = userpreferencesService.salvar(userpreferencesDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(userpreferencesSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<UserPreferencesDTO> getUserPreferencesById(@PathVariable("id") Long id) {
      try {
           UserPreferencesDTO userpreferencesDTO = userpreferencesService.findById(id);

           if (userpreferencesDTO != null) {
               return new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<UserPreferencesDTO> updateUserPreferences(@PathVariable("id") Long id, @RequestBody @Valid UserPreferencesDTO userpreferencesDTO) {
        UserPreferencesDTO userpreferencesData = userpreferencesService.findById(id);

        if(userpreferencesData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            userpreferencesDTO.setId(id);
            userpreferencesDTO.setDateUpdated(new Date());
            UserPreferencesDTO userpreferencesSaved = userpreferencesService.salvar(userpreferencesDTO);
            return new ResponseEntity<>(userpreferencesSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        UserPreferencesDTO userpreferencesData = userpreferencesService.findById(id);
        if (userpreferencesData == null || !userpreferencesService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("UserPreferences atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<UserPreferencesDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        UserPreferencesDTO userpreferencesUpdated = userpreferencesService.updateStatusById(id, status);
        return new ResponseEntity<>(userpreferencesUpdated, HttpStatus.OK);
      } catch(UserPreferencesNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<UserPreferencesDTO> findUserPreferencesById(@RequestParam(UserPreferencesConstantes.ID) Long id) {
        try{
            UserPreferencesDTO userpreferencesDTO = userpreferencesService.findUserPreferencesByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userpreferencesDTO)
                ? new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalApp")
    public ResponseEntity<UserPreferencesDTO> findUserPreferencesByUuidExternalApp(@RequestParam(UserPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp) {
        try{
            UserPreferencesDTO userpreferencesDTO = userpreferencesService.findUserPreferencesByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userpreferencesDTO)
                ? new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalUser")
    public ResponseEntity<UserPreferencesDTO> findUserPreferencesByUuidExternalUser(@RequestParam(UserPreferencesConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser) {
        try{
            UserPreferencesDTO userpreferencesDTO = userpreferencesService.findUserPreferencesByUuidExternalUserAndStatus(uuidExternalUser, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userpreferencesDTO)
                ? new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "key")
    public ResponseEntity<UserPreferencesDTO> findUserPreferencesByKey(@RequestParam(UserPreferencesConstantes.KEY) String key) {
        try{
            UserPreferencesDTO userpreferencesDTO = userpreferencesService.findUserPreferencesByKeyAndStatus(key, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userpreferencesDTO)
                ? new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "preference")
    public ResponseEntity<UserPreferencesDTO> findUserPreferencesByPreference(@RequestParam(UserPreferencesConstantes.PREFERENCE) String preference) {
        try{
            UserPreferencesDTO userpreferencesDTO = userpreferencesService.findUserPreferencesByPreferenceAndStatus(preference, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userpreferencesDTO)
                ? new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<UserPreferencesDTO> findUserPreferencesByDateCreated(@RequestParam(UserPreferencesConstantes.DATECREATED) Date dateCreated) {
        try{
            UserPreferencesDTO userpreferencesDTO = userpreferencesService.findUserPreferencesByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userpreferencesDTO)
                ? new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo UserPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<UserPreferencesDTO> findUserPreferencesByDateUpdated(@RequestParam(UserPreferencesConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            UserPreferencesDTO userpreferencesDTO = userpreferencesService.findUserPreferencesByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(userpreferencesDTO)
                ? new ResponseEntity<>(userpreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
