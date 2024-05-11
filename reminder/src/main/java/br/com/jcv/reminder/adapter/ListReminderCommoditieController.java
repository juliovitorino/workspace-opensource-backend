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
import br.com.jcv.reminder.corelayer.service.ListReminderService;
import br.com.jcv.reminder.infrastructure.constantes.ListReminderConstantes;
import br.com.jcv.reminder.infrastructure.dto.ListReminderDTO;
import br.com.jcv.reminder.infrastructure.exception.ListReminderNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* ListReminderCommoditieController - Controller for ListReminder API
*
* @author ListReminder
* @since Sat May 11 14:30:00 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/listreminder")
public class ListReminderCommoditieController
{
     @Autowired private ListReminderService listreminderService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<ListReminderDTO>> findAllListReminder() {
        try {
            List<ListReminderDTO> listreminders = listreminderService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (listreminders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listreminders, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<ListReminderDTO>> findAllListReminder(@PathVariable("status") String status) {
        try {
            List<ListReminderDTO> listreminders = listreminderService.findAllByStatus(status);

            if (listreminders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listreminders, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<ListReminderDTO>> findAllListReminderByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<ListReminderDTO> response = listreminderService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterListReminderDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = listreminderService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteListReminder(@PathVariable("id") long id) {
        try {
            listreminderService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(ListReminderNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createListReminder(@RequestBody @Valid ListReminderDTO listreminderDTO) {
        try {
            ListReminderDTO listreminderSaved = listreminderService.salvar(listreminderDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(listreminderSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<ListReminderDTO> getListReminderById(@PathVariable("id") Long id) {
      try {
           ListReminderDTO listreminderDTO = listreminderService.findById(id);

           if (listreminderDTO != null) {
               return new ResponseEntity<>(listreminderDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<ListReminderDTO> updateListReminder(@PathVariable("id") Long id, @RequestBody @Valid ListReminderDTO listreminderDTO) {
        ListReminderDTO listreminderData = listreminderService.findById(id);

        if(listreminderData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            listreminderDTO.setId(id);
            listreminderDTO.setDateUpdated(new Date());
            ListReminderDTO listreminderSaved = listreminderService.salvar(listreminderDTO);
            return new ResponseEntity<>(listreminderSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        ListReminderDTO listreminderData = listreminderService.findById(id);
        if (listreminderData == null || !listreminderService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("ListReminder atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ListReminderDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        ListReminderDTO listreminderUpdated = listreminderService.updateStatusById(id, status);
        return new ResponseEntity<>(listreminderUpdated, HttpStatus.OK);
      } catch(ListReminderNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<ListReminderDTO> findListReminderById(@RequestParam(ListReminderConstantes.ID) Long id) {
        try{
            ListReminderDTO listreminderDTO = listreminderService.findListReminderByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(listreminderDTO)
                ? new ResponseEntity<>(listreminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ListReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalApp")
    public ResponseEntity<ListReminderDTO> findListReminderByUuidExternalApp(@RequestParam(ListReminderConstantes.UUIDEXTERNALAPP) UUID uuidExternalApp) {
        try{
            ListReminderDTO listreminderDTO = listreminderService.findListReminderByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(listreminderDTO)
                ? new ResponseEntity<>(listreminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ListReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalUser")
    public ResponseEntity<ListReminderDTO> findListReminderByUuidExternalUser(@RequestParam(ListReminderConstantes.UUIDEXTERNALUSER) UUID uuidExternalUser) {
        try{
            ListReminderDTO listreminderDTO = listreminderService.findListReminderByUuidExternalUserAndStatus(uuidExternalUser, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(listreminderDTO)
                ? new ResponseEntity<>(listreminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ListReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "uuidExternalList")
    public ResponseEntity<ListReminderDTO> findListReminderByUuidExternalList(@RequestParam(ListReminderConstantes.UUIDEXTERNALLIST) UUID uuidExternalList) {
        try{
            ListReminderDTO listreminderDTO = listreminderService.findListReminderByUuidExternalListAndStatus(uuidExternalList, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(listreminderDTO)
                ? new ResponseEntity<>(listreminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ListReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "title")
    public ResponseEntity<ListReminderDTO> findListReminderByTitle(@RequestParam(ListReminderConstantes.TITLE) String title) {
        try{
            ListReminderDTO listreminderDTO = listreminderService.findListReminderByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(listreminderDTO)
                ? new ResponseEntity<>(listreminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ListReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<ListReminderDTO> findListReminderByDateCreated(@RequestParam(ListReminderConstantes.DATECREATED) Date dateCreated) {
        try{
            ListReminderDTO listreminderDTO = listreminderService.findListReminderByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(listreminderDTO)
                ? new ResponseEntity<>(listreminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ListReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo ListReminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<ListReminderDTO> findListReminderByDateUpdated(@RequestParam(ListReminderConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            ListReminderDTO listreminderDTO = listreminderService.findListReminderByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(listreminderDTO)
                ? new ResponseEntity<>(listreminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ListReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
