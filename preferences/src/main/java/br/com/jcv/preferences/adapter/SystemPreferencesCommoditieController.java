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
import br.com.jcv.preferences.corelayer.service.SystemPreferencesService;
import br.com.jcv.preferences.infrastructure.constantes.SystemPreferencesConstantes;
import br.com.jcv.preferences.infrastructure.dto.SystemPreferencesDTO;
import br.com.jcv.preferences.infrastructure.exception.SystemPreferencesNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* SystemPreferencesCommoditieController - Controller for SystemPreferences API
*
* @author SystemPreferences
* @since Mon Apr 29 15:32:51 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/systempreferences")
public class SystemPreferencesCommoditieController
{
     @Autowired private SystemPreferencesService systempreferencesService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<SystemPreferencesDTO>> findAllSystemPreferences() {
        try {
            List<SystemPreferencesDTO> systempreferencess = systempreferencesService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (systempreferencess.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(systempreferencess, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<SystemPreferencesDTO>> findAllSystemPreferences(@PathVariable("status") String status) {
        try {
            List<SystemPreferencesDTO> systempreferencess = systempreferencesService.findAllByStatus(status);

            if (systempreferencess.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(systempreferencess, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<SystemPreferencesDTO>> findAllSystemPreferencesByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<SystemPreferencesDTO> response = systempreferencesService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterSystemPreferencesDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = systempreferencesService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSystemPreferences(@PathVariable("id") long id) {
        try {
            systempreferencesService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(SystemPreferencesNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createSystemPreferences(@RequestBody @Valid SystemPreferencesDTO systempreferencesDTO) {
        try {
            SystemPreferencesDTO systempreferencesSaved = systempreferencesService.salvar(systempreferencesDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(systempreferencesSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<SystemPreferencesDTO> getSystemPreferencesById(@PathVariable("id") Long id) {
      try {
           SystemPreferencesDTO systempreferencesDTO = systempreferencesService.findById(id);

           if (systempreferencesDTO != null) {
               return new ResponseEntity<>(systempreferencesDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<SystemPreferencesDTO> updateSystemPreferences(@PathVariable("id") Long id, @RequestBody @Valid SystemPreferencesDTO systempreferencesDTO) {
        SystemPreferencesDTO systempreferencesData = systempreferencesService.findById(id);

        if(systempreferencesData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            systempreferencesDTO.setId(id);
            systempreferencesDTO.setDateUpdated(new Date());
            SystemPreferencesDTO systempreferencesSaved = systempreferencesService.salvar(systempreferencesDTO);
            return new ResponseEntity<>(systempreferencesSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        SystemPreferencesDTO systempreferencesData = systempreferencesService.findById(id);
        if (systempreferencesData == null || !systempreferencesService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("SystemPreferences atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<SystemPreferencesDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        SystemPreferencesDTO systempreferencesUpdated = systempreferencesService.updateStatusById(id, status);
        return new ResponseEntity<>(systempreferencesUpdated, HttpStatus.OK);
      } catch(SystemPreferencesNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<SystemPreferencesDTO> findSystemPreferencesById(@RequestParam(SystemPreferencesConstantes.ID) Long id) {
        try{
            SystemPreferencesDTO systempreferencesDTO = systempreferencesService.findSystemPreferencesByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(systempreferencesDTO)
                ? new ResponseEntity<>(systempreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SystemPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalApp")
    public ResponseEntity<SystemPreferencesDTO> findSystemPreferencesByUuidExternalApp(@RequestParam(SystemPreferencesConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp) {
        try{
            SystemPreferencesDTO systempreferencesDTO = systempreferencesService.findSystemPreferencesByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(systempreferencesDTO)
                ? new ResponseEntity<>(systempreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SystemPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "key")
    public ResponseEntity<SystemPreferencesDTO> findSystemPreferencesByKey(@RequestParam(SystemPreferencesConstantes.KEY) String key) {
        try{
            SystemPreferencesDTO systempreferencesDTO = systempreferencesService.findSystemPreferencesByKeyAndStatus(key, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(systempreferencesDTO)
                ? new ResponseEntity<>(systempreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SystemPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "preference")
    public ResponseEntity<SystemPreferencesDTO> findSystemPreferencesByPreference(@RequestParam(SystemPreferencesConstantes.PREFERENCE) String preference) {
        try{
            SystemPreferencesDTO systempreferencesDTO = systempreferencesService.findSystemPreferencesByPreferenceAndStatus(preference, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(systempreferencesDTO)
                ? new ResponseEntity<>(systempreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SystemPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<SystemPreferencesDTO> findSystemPreferencesByDateCreated(@RequestParam(SystemPreferencesConstantes.DATECREATED) Date dateCreated) {
        try{
            SystemPreferencesDTO systempreferencesDTO = systempreferencesService.findSystemPreferencesByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(systempreferencesDTO)
                ? new ResponseEntity<>(systempreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SystemPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo SystemPreferences foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<SystemPreferencesDTO> findSystemPreferencesByDateUpdated(@RequestParam(SystemPreferencesConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            SystemPreferencesDTO systempreferencesDTO = systempreferencesService.findSystemPreferencesByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(systempreferencesDTO)
                ? new ResponseEntity<>(systempreferencesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SystemPreferencesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
