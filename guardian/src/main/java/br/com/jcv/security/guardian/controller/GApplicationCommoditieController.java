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

import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.service.GApplicationService;
import br.com.jcv.security.guardian.exception.GApplicationNotFoundException;
import br.com.jcv.security.guardian.constantes.GApplicationConstantes;

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
* GApplicationCommoditieController - Controller for GApplication API
*
* @author GApplication
* @since Wed Nov 15 11:12:12 BRT 2023
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/gapplication")
public class GApplicationCommoditieController
{
     @Autowired private GApplicationService gapplicationService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<GApplicationDTO>> findAllGApplication() {
        try {
            List<GApplicationDTO> gapplications = gapplicationService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (gapplications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(gapplications, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<GApplicationDTO>> findAllGApplication(@PathVariable("status") String status) {
        try {
            List<GApplicationDTO> gapplications = gapplicationService.findAllByStatus(status);

            if (gapplications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(gapplications, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<GApplicationDTO>> findAllGApplicationByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<GApplicationDTO> response = gapplicationService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterGApplicationDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = gapplicationService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGApplication(@PathVariable("id") long id) {
        try {
            gapplicationService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(GApplicationNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createGApplication(@RequestBody @Valid GApplicationDTO gapplicationDTO) {
        try {
            GApplicationDTO gapplicationSaved = gapplicationService.salvar(gapplicationDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(gapplicationSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<GApplicationDTO> getGApplicationById(@PathVariable("id") Long id) {
      try {
           GApplicationDTO gapplicationDTO = gapplicationService.findById(id);

           if (gapplicationDTO != null) {
               return new ResponseEntity<>(gapplicationDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<GApplicationDTO> updateGApplication(@PathVariable("id") Long id, @RequestBody @Valid GApplicationDTO gapplicationDTO) {
        GApplicationDTO gapplicationData = gapplicationService.findById(id);

        if(gapplicationData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            gapplicationDTO.setId(id);
            gapplicationDTO.setDateUpdated(LocalDateTime.now());
            GApplicationDTO gapplicationSaved = gapplicationService.salvar(gapplicationDTO);
            return new ResponseEntity<>(gapplicationSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        GApplicationDTO gapplicationData = gapplicationService.findById(id);
        if (gapplicationData == null || !gapplicationService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("GApplication atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<GApplicationDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        GApplicationDTO gapplicationUpdated = gapplicationService.updateStatusById(id, status);
        return new ResponseEntity<>(gapplicationUpdated, HttpStatus.OK);
      } catch(GApplicationNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<GApplicationDTO> findGApplicationById(@RequestParam(GApplicationConstantes.ID) Long id) {
        try{
            GApplicationDTO gapplicationDTO = gapplicationService.findGApplicationByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(gapplicationDTO)
                ? new ResponseEntity<>(gapplicationDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GApplicationNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "name")
    public ResponseEntity<GApplicationDTO> findGApplicationByName(@RequestParam(GApplicationConstantes.NAME) String name) {
        try{
            GApplicationDTO gapplicationDTO = gapplicationService.findGApplicationByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(gapplicationDTO)
                ? new ResponseEntity<>(gapplicationDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GApplicationNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "externalCodeUUID")
    public ResponseEntity<GApplicationDTO> findGApplicationByExternalCodeUUID(@RequestParam(GApplicationConstantes.EXTERNALCODEUUID) UUID externalCodeUUID) {
        try{
            GApplicationDTO gapplicationDTO = gapplicationService.findGApplicationByExternalCodeUUIDAndStatus(externalCodeUUID, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(gapplicationDTO)
                ? new ResponseEntity<>(gapplicationDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GApplicationNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<GApplicationDTO> findGApplicationByDateCreated(@RequestParam(GApplicationConstantes.DATECREATED) Date dateCreated) {
        try{
            GApplicationDTO gapplicationDTO = gapplicationService.findGApplicationByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(gapplicationDTO)
                ? new ResponseEntity<>(gapplicationDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GApplicationNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo GApplication foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<GApplicationDTO> findGApplicationByDateUpdated(@RequestParam(GApplicationConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            GApplicationDTO gapplicationDTO = gapplicationService.findGApplicationByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(gapplicationDTO)
                ? new ResponseEntity<>(gapplicationDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GApplicationNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
