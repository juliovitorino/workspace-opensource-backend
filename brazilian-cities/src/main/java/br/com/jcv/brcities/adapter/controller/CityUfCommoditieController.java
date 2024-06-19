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


package br.com.jcv.brcities.adapter.controller;

import java.net.URI;
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

import br.com.jcv.brcities.corelayer.service.CityUfService;
import br.com.jcv.brcities.infrastructure.constantes.CityUfConstantes;
import br.com.jcv.brcities.infrastructure.dto.CityUfDTO;
import br.com.jcv.brcities.infrastructure.exception.CityUfNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* CityUfCommoditieController - Controller for CityUf API
*
* @author CityUf
* @since Wed Jun 05 13:47:11 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/cityuf")
public class CityUfCommoditieController
{
     @Autowired private CityUfService cityufService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<CityUfDTO>> findAllCityUf() {
        try {
            List<CityUfDTO> cityufs = cityufService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (cityufs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cityufs, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<CityUfDTO>> findAllCityUf(@PathVariable("status") String status) {
        try {
            List<CityUfDTO> cityufs = cityufService.findAllByStatus(status);

            if (cityufs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cityufs, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<CityUfDTO>> findAllCityUfByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<CityUfDTO> response = cityufService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterCityUfDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = cityufService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCityUf(@PathVariable("id") long id) {
        try {
            cityufService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(CityUfNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createCityUf(@RequestBody @Valid CityUfDTO cityufDTO) {
        try {
            CityUfDTO cityufSaved = cityufService.salvar(cityufDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(cityufSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<CityUfDTO> getCityUfById(@PathVariable("id") Long id) {
      try {
           CityUfDTO cityufDTO = cityufService.findById(id);

           if (cityufDTO != null) {
               return new ResponseEntity<>(cityufDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<CityUfDTO> updateCityUf(@PathVariable("id") Long id, @RequestBody @Valid CityUfDTO cityufDTO) {
        CityUfDTO cityufData = cityufService.findById(id);

        if(cityufData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            cityufDTO.setId(id);
            cityufDTO.setDateUpdated(new Date());
            CityUfDTO cityufSaved = cityufService.salvar(cityufDTO);
            return new ResponseEntity<>(cityufSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        CityUfDTO cityufData = cityufService.findById(id);
        if (cityufData == null || !cityufService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("CityUf atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<CityUfDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        CityUfDTO cityufUpdated = cityufService.updateStatusById(id, status);
        return new ResponseEntity<>(cityufUpdated, HttpStatus.OK);
      } catch(CityUfNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<CityUfDTO> findCityUfById(@RequestParam(CityUfConstantes.ID) Long id) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "cityId")
    public ResponseEntity<CityUfDTO> findCityUfByCityId(@RequestParam(CityUfConstantes.CITYID) Long cityId) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByCityIdAndStatus(cityId, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "ufId")
    public ResponseEntity<CityUfDTO> findCityUfByUfId(@RequestParam(CityUfConstantes.UFID) String ufId) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByUfIdAndStatus(ufId, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "ddd")
    public ResponseEntity<CityUfDTO> findCityUfByDdd(@RequestParam(CityUfConstantes.DDD) String ddd) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByDddAndStatus(ddd, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "latitude")
    public ResponseEntity<CityUfDTO> findCityUfByLatitude(@RequestParam(CityUfConstantes.LATITUDE) Double latitude) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByLatitudeAndStatus(latitude, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "longitude")
    public ResponseEntity<CityUfDTO> findCityUfByLongitude(@RequestParam(CityUfConstantes.LONGITUDE) Double longitude) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByLongitudeAndStatus(longitude, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<CityUfDTO> findCityUfByDateCreated(@RequestParam(CityUfConstantes.DATECREATED) Date dateCreated) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo CityUf foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<CityUfDTO> findCityUfByDateUpdated(@RequestParam(CityUfConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            CityUfDTO cityufDTO = cityufService.findCityUfByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(cityufDTO)
                ? new ResponseEntity<>(cityufDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CityUfNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
