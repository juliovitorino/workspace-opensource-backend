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


package br.com.jcv.reminder.adapter;

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
import br.com.jcv.reminder.corelayer.service.ReminderListService;
import br.com.jcv.reminder.infrastructure.constantes.ReminderListConstantes;
import br.com.jcv.reminder.infrastructure.dto.ReminderListDTO;
import br.com.jcv.reminder.infrastructure.exception.ReminderListNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* ReminderListCommoditieController - Controller for ReminderList API
*
* @author ReminderList
* @since Sat May 11 17:44:44 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/reminderlist")
public class ReminderListCommoditieController
{
     @Autowired private ReminderListService reminderlistService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<ReminderListDTO>> findAllReminderList() {
        try {
            List<ReminderListDTO> reminderlists = reminderlistService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (reminderlists.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reminderlists, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<ReminderListDTO>> findAllReminderList(@PathVariable("status") String status) {
        try {
            List<ReminderListDTO> reminderlists = reminderlistService.findAllByStatus(status);

            if (reminderlists.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reminderlists, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<ReminderListDTO>> findAllReminderListByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<ReminderListDTO> response = reminderlistService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterReminderListDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = reminderlistService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReminderList(@PathVariable("id") long id) {
        try {
            reminderlistService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(ReminderListNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createReminderList(@RequestBody @Valid ReminderListDTO reminderlistDTO) {
        try {
            ReminderListDTO reminderlistSaved = reminderlistService.salvar(reminderlistDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(reminderlistSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<ReminderListDTO> getReminderListById(@PathVariable("id") Long id) {
      try {
           ReminderListDTO reminderlistDTO = reminderlistService.findById(id);

           if (reminderlistDTO != null) {
               return new ResponseEntity<>(reminderlistDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<ReminderListDTO> updateReminderList(@PathVariable("id") Long id, @RequestBody @Valid ReminderListDTO reminderlistDTO) {
        ReminderListDTO reminderlistData = reminderlistService.findById(id);

        if(reminderlistData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            reminderlistDTO.setId(id);
            reminderlistDTO.setDateUpdated(new Date());
            ReminderListDTO reminderlistSaved = reminderlistService.salvar(reminderlistDTO);
            return new ResponseEntity<>(reminderlistSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        ReminderListDTO reminderlistData = reminderlistService.findById(id);
        if (reminderlistData == null || !reminderlistService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("ReminderList atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ReminderListDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        ReminderListDTO reminderlistUpdated = reminderlistService.updateStatusById(id, status);
        return new ResponseEntity<>(reminderlistUpdated, HttpStatus.OK);
      } catch(ReminderListNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<ReminderListDTO> findReminderListById(@RequestParam(ReminderListConstantes.ID) Long id) {
        try{
            ReminderListDTO reminderlistDTO = reminderlistService.findReminderListByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderlistDTO)
                ? new ResponseEntity<>(reminderlistDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderListNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalApp")
    public ResponseEntity<ReminderListDTO> findReminderListByUuidExternalApp(@RequestParam(ReminderListConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp) {
        try{
            ReminderListDTO reminderlistDTO = reminderlistService.findReminderListByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderlistDTO)
                ? new ResponseEntity<>(reminderlistDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderListNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalUser")
    public ResponseEntity<ReminderListDTO> findReminderListByUuidExternalUser(@RequestParam(ReminderListConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser) {
        try{
            ReminderListDTO reminderlistDTO = reminderlistService.findReminderListByUuidExternalUserAndStatus(uuidExternalUser, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderlistDTO)
                ? new ResponseEntity<>(reminderlistDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderListNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalList")
    public ResponseEntity<ReminderListDTO> findReminderListByUuidExternalList(@RequestParam(ReminderListConstantes.UUIDEXTERNALLIST) UUID uuidExternalList) {
        try{
            ReminderListDTO reminderlistDTO = reminderlistService.findReminderListByUuidExternalListAndStatus(uuidExternalList, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderlistDTO)
                ? new ResponseEntity<>(reminderlistDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderListNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "title")
    public ResponseEntity<ReminderListDTO> findReminderListByTitle(@RequestParam(ReminderListConstantes.TITLE) String title) {
        try{
            ReminderListDTO reminderlistDTO = reminderlistService.findReminderListByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderlistDTO)
                ? new ResponseEntity<>(reminderlistDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderListNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<ReminderListDTO> findReminderListByDateCreated(@RequestParam(ReminderListConstantes.DATECREATED) Date dateCreated) {
        try{
            ReminderListDTO reminderlistDTO = reminderlistService.findReminderListByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderlistDTO)
                ? new ResponseEntity<>(reminderlistDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderListNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ReminderList foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<ReminderListDTO> findReminderListByDateUpdated(@RequestParam(ReminderListConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            ReminderListDTO reminderlistDTO = reminderlistService.findReminderListByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderlistDTO)
                ? new ResponseEntity<>(reminderlistDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderListNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
