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


package br.com.jcv.bei.adapter;

import java.net.URI;
import java.time.LocalDate;
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

import br.com.jcv.bei.corelayer.service.EconomicIndexService;
import br.com.jcv.bei.infrastructure.constantes.EconomicIndexConstantes;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.bei.infrastructure.exception.EconomicIndexNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* EconomicIndexCommoditieController - Controller for EconomicIndex API
*
* @author EconomicIndex
* @since Thu May 23 16:41:19 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/economicindex")
public class EconomicIndexCommoditieController
{
     @Autowired private EconomicIndexService economicindexService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<EconomicIndexDTO>> findAllEconomicIndex() {
        try {
            List<EconomicIndexDTO> economicindexs = economicindexService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (economicindexs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(economicindexs, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<EconomicIndexDTO>> findAllEconomicIndex(@PathVariable("status") String status) {
        try {
            List<EconomicIndexDTO> economicindexs = economicindexService.findAllByStatus(status);

            if (economicindexs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(economicindexs, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<EconomicIndexDTO>> findAllEconomicIndexByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<EconomicIndexDTO> response = economicindexService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterEconomicIndexDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = economicindexService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEconomicIndex(@PathVariable("id") long id) {
        try {
            economicindexService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EconomicIndexNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createEconomicIndex(@RequestBody @Valid EconomicIndexDTO economicindexDTO) {
        try {
            EconomicIndexDTO economicindexSaved = economicindexService.salvar(economicindexDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(economicindexSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<EconomicIndexDTO> getEconomicIndexById(@PathVariable("id") Long id) {
      try {
           EconomicIndexDTO economicindexDTO = economicindexService.findById(id);

           if (economicindexDTO != null) {
               return new ResponseEntity<>(economicindexDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<EconomicIndexDTO> updateEconomicIndex(@PathVariable("id") Long id, @RequestBody @Valid EconomicIndexDTO economicindexDTO) {
        EconomicIndexDTO economicindexData = economicindexService.findById(id);

        if(economicindexData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            economicindexDTO.setId(id);
            economicindexDTO.setDateUpdated(new Date());
            EconomicIndexDTO economicindexSaved = economicindexService.salvar(economicindexDTO);
            return new ResponseEntity<>(economicindexSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        EconomicIndexDTO economicindexData = economicindexService.findById(id);
        if (economicindexData == null || !economicindexService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("EconomicIndex atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<EconomicIndexDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        EconomicIndexDTO economicindexUpdated = economicindexService.updateStatusById(id, status);
        return new ResponseEntity<>(economicindexUpdated, HttpStatus.OK);
      } catch(EconomicIndexNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<EconomicIndexDTO> findEconomicIndexById(@RequestParam(EconomicIndexConstantes.ID) Long id) {
        try{
            EconomicIndexDTO economicindexDTO = economicindexService.findEconomicIndexByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexDTO)
                ? new ResponseEntity<>(economicindexDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "economicIndex")
    public ResponseEntity<EconomicIndexDTO> findEconomicIndexByEconomicIndex(@RequestParam(EconomicIndexConstantes.ECONOMICINDEX) String economicIndex) {
        try{
            EconomicIndexDTO economicindexDTO = economicindexService.findEconomicIndexByEconomicIndexAndStatus(economicIndex, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexDTO)
                ? new ResponseEntity<>(economicindexDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "bacenSerieCode")
    public ResponseEntity<EconomicIndexDTO> findEconomicIndexByBacenSerieCode(@RequestParam(EconomicIndexConstantes.BACENSERIECODE) String bacenSerieCode) {
        try{
            EconomicIndexDTO economicindexDTO = economicindexService.findEconomicIndexByBacenSerieCodeAndStatus(bacenSerieCode, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexDTO)
                ? new ResponseEntity<>(economicindexDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "lastDateValue")
    public ResponseEntity<EconomicIndexDTO> findEconomicIndexByLastDateValue(@RequestParam(EconomicIndexConstantes.LASTDATEVALUE) LocalDate lastDateValue) {
        try{
            EconomicIndexDTO economicindexDTO = economicindexService.findEconomicIndexByLastDateValueAndStatus(lastDateValue, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexDTO)
                ? new ResponseEntity<>(economicindexDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<EconomicIndexDTO> findEconomicIndexByDateCreated(@RequestParam(EconomicIndexConstantes.DATECREATED) Date dateCreated) {
        try{
            EconomicIndexDTO economicindexDTO = economicindexService.findEconomicIndexByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexDTO)
                ? new ResponseEntity<>(economicindexDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndex foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<EconomicIndexDTO> findEconomicIndexByDateUpdated(@RequestParam(EconomicIndexConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            EconomicIndexDTO economicindexDTO = economicindexService.findEconomicIndexByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexDTO)
                ? new ResponseEntity<>(economicindexDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
