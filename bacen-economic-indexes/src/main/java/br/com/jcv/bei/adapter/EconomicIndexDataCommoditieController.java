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

import br.com.jcv.bei.corelayer.service.EconomicIndexDataService;
import br.com.jcv.bei.infrastructure.constantes.EconomicIndexDataConstantes;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDataDTO;
import br.com.jcv.bei.infrastructure.exception.EconomicIndexDataNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* EconomicIndexDataCommoditieController - Controller for EconomicIndexData API
*
* @author EconomicIndexData
* @since Thu May 23 17:29:29 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/economicindexdata")
public class EconomicIndexDataCommoditieController
{
     @Autowired private EconomicIndexDataService economicindexdataService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<EconomicIndexDataDTO>> findAllEconomicIndexData() {
        try {
            List<EconomicIndexDataDTO> economicindexdatas = economicindexdataService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (economicindexdatas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(economicindexdatas, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<EconomicIndexDataDTO>> findAllEconomicIndexData(@PathVariable("status") String status) {
        try {
            List<EconomicIndexDataDTO> economicindexdatas = economicindexdataService.findAllByStatus(status);

            if (economicindexdatas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(economicindexdatas, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<EconomicIndexDataDTO>> findAllEconomicIndexDataByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<EconomicIndexDataDTO> response = economicindexdataService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterEconomicIndexDataDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = economicindexdataService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEconomicIndexData(@PathVariable("id") long id) {
        try {
            economicindexdataService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EconomicIndexDataNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createEconomicIndexData(@RequestBody @Valid EconomicIndexDataDTO economicindexdataDTO) {
        try {
            EconomicIndexDataDTO economicindexdataSaved = economicindexdataService.salvar(economicindexdataDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(economicindexdataSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<EconomicIndexDataDTO> getEconomicIndexDataById(@PathVariable("id") Long id) {
      try {
           EconomicIndexDataDTO economicindexdataDTO = economicindexdataService.findById(id);

           if (economicindexdataDTO != null) {
               return new ResponseEntity<>(economicindexdataDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<EconomicIndexDataDTO> updateEconomicIndexData(@PathVariable("id") Long id, @RequestBody @Valid EconomicIndexDataDTO economicindexdataDTO) {
        EconomicIndexDataDTO economicindexdataData = economicindexdataService.findById(id);

        if(economicindexdataData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            economicindexdataDTO.setId(id);
            economicindexdataDTO.setDateUpdated(new Date());
            EconomicIndexDataDTO economicindexdataSaved = economicindexdataService.salvar(economicindexdataDTO);
            return new ResponseEntity<>(economicindexdataSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        EconomicIndexDataDTO economicindexdataData = economicindexdataService.findById(id);
        if (economicindexdataData == null || !economicindexdataService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("EconomicIndexData atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<EconomicIndexDataDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        EconomicIndexDataDTO economicindexdataUpdated = economicindexdataService.updateStatusById(id, status);
        return new ResponseEntity<>(economicindexdataUpdated, HttpStatus.OK);
      } catch(EconomicIndexDataNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<EconomicIndexDataDTO> findEconomicIndexDataById(@RequestParam(EconomicIndexDataConstantes.ID) Long id) {
        try{
            EconomicIndexDataDTO economicindexdataDTO = economicindexdataService.findEconomicIndexDataByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexdataDTO)
                ? new ResponseEntity<>(economicindexdataDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexDataNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "economicIndexId")
    public ResponseEntity<EconomicIndexDataDTO> findEconomicIndexDataByEconomicIndexId(@RequestParam(EconomicIndexDataConstantes.ECONOMICINDEXID) Long economicIndexId) {
        try{
            EconomicIndexDataDTO economicindexdataDTO = economicindexdataService.findEconomicIndexDataByEconomicIndexIdAndStatus(economicIndexId, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexdataDTO)
                ? new ResponseEntity<>(economicindexdataDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexDataNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "indexDate")
    public ResponseEntity<EconomicIndexDataDTO> findEconomicIndexDataByIndexDate(@RequestParam(EconomicIndexDataConstantes.INDEXDATE) LocalDate indexDate) {
        try{
            EconomicIndexDataDTO economicindexdataDTO = economicindexdataService.findEconomicIndexDataByIndexDateAndStatus(indexDate, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexdataDTO)
                ? new ResponseEntity<>(economicindexdataDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexDataNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "indexValue")
    public ResponseEntity<EconomicIndexDataDTO> findEconomicIndexDataByIndexValue(@RequestParam(EconomicIndexDataConstantes.INDEXVALUE) Double indexValue) {
        try{
            EconomicIndexDataDTO economicindexdataDTO = economicindexdataService.findEconomicIndexDataByIndexValueAndStatus(indexValue, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexdataDTO)
                ? new ResponseEntity<>(economicindexdataDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexDataNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<EconomicIndexDataDTO> findEconomicIndexDataByDateCreated(@RequestParam(EconomicIndexDataConstantes.DATECREATED) Date dateCreated) {
        try{
            EconomicIndexDataDTO economicindexdataDTO = economicindexdataService.findEconomicIndexDataByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexdataDTO)
                ? new ResponseEntity<>(economicindexdataDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexDataNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo EconomicIndexData foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<EconomicIndexDataDTO> findEconomicIndexDataByDateUpdated(@RequestParam(EconomicIndexDataConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            EconomicIndexDataDTO economicindexdataDTO = economicindexdataService.findEconomicIndexDataByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(economicindexdataDTO)
                ? new ResponseEntity<>(economicindexdataDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EconomicIndexDataNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
