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
import br.com.jcv.reminder.corelayer.service.ReminderService;
import br.com.jcv.reminder.infrastructure.constantes.ReminderConstantes;
import br.com.jcv.reminder.infrastructure.dto.ReminderDTO;
import br.com.jcv.reminder.infrastructure.exception.ReminderNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
* ReminderCommoditieController - Controller for Reminder API
*
* @author Reminder
* @since Sat May 11 18:10:51 BRT 2024
*/

@Slf4j
@RestController
@RequestMapping("/v1/api/reminder")
public class ReminderCommoditieController
{
     @Autowired private ReminderService reminderService;

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list")
    public ResponseEntity<List<ReminderDTO>> findAllReminder() {
        try {
            List<ReminderDTO> reminders = reminderService.findAllByStatus(GenericStatusEnums.ATIVO.getShortValue());

            if (reminders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reminders, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/list/{status}")
    public ResponseEntity<List<ReminderDTO>> findAllReminder(@PathVariable("status") String status) {
        try {
            List<ReminderDTO> reminders = reminderService.findAllByStatus(status);

            if (reminders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reminders, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/findAllByFilter")
    public ResponseEntity<List<ReminderDTO>> findAllReminderByFilter(@RequestBody RequestFilter filtro) {
        try{
            List<ReminderDTO> response = reminderService.findAllByFilter(filtro);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/pagefilter")
    public ResponseEntity<Map<String,Object>> pageFilterReminderDinamico(@RequestBody RequestFilter filtro) {
        try{
            Map<String,Object> responseFilter = reminderService.findPageByFilter(filtro);
            return new ResponseEntity<>(responseFilter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReminder(@PathVariable("id") long id) {
        try {
            reminderService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(ReminderNotFoundException ex) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PostMapping
    public ResponseEntity<Long> createReminder(@RequestBody @Valid ReminderDTO reminderDTO) {
        try {
            ReminderDTO reminderSaved = reminderService.salvar(reminderDTO);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(reminderSaved.getId())
                        .toUri();
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @GetMapping("/{id}")
    public ResponseEntity<ReminderDTO> getReminderById(@PathVariable("id") Long id) {
      try {
           ReminderDTO reminderDTO = reminderService.findById(id);

           if (reminderDTO != null) {
               return new ResponseEntity<>(reminderDTO, HttpStatus.OK);
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
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PutMapping("/{id}")
    public ResponseEntity<ReminderDTO> updateReminder(@PathVariable("id") Long id, @RequestBody @Valid ReminderDTO reminderDTO) {
        ReminderDTO reminderData = reminderService.findById(id);

        if(reminderData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            reminderDTO.setId(id);
            reminderDTO.setDateUpdated(new Date());
            ReminderDTO reminderSaved = reminderService.salvar(reminderDTO);
            return new ResponseEntity<>(reminderSaved, HttpStatus.OK);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") Long id) {
        ReminderDTO reminderData = reminderService.findById(id);
        if (reminderData == null || !reminderService.partialUpdate(id, updates)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Reminder atualizada");
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ReminderDTO> updateStatusById(
            @PathVariable("id") Long id, @PathVariable("status") String status) {
      try {
        ReminderDTO reminderUpdated = reminderService.updateStatusById(id, status);
        return new ResponseEntity<>(reminderUpdated, HttpStatus.OK);
      } catch(ReminderNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch(CommoditieBaseException e) {
        return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "id")
    public ResponseEntity<ReminderDTO> findReminderById(@RequestParam(ReminderConstantes.ID) Long id) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "idList")
    public ResponseEntity<ReminderDTO> findReminderByIdList(@RequestParam(ReminderConstantes.IDLIST) Long idList) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByIdListAndStatus(idList, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "title")
    public ResponseEntity<ReminderDTO> findReminderByTitle(@RequestParam(ReminderConstantes.TITLE) String title) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "note")
    public ResponseEntity<ReminderDTO> findReminderByNote(@RequestParam(ReminderConstantes.NOTE) String note) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByNoteAndStatus(note, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "tags")
    public ResponseEntity<ReminderDTO> findReminderByTags(@RequestParam(ReminderConstantes.TAGS) String tags) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByTagsAndStatus(tags, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "fullUrlImage")
    public ResponseEntity<ReminderDTO> findReminderByFullUrlImage(@RequestParam(ReminderConstantes.FULLURLIMAGE) String fullUrlImage) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByFullUrlImageAndStatus(fullUrlImage, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "url")
    public ResponseEntity<ReminderDTO> findReminderByUrl(@RequestParam(ReminderConstantes.URL) String url) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByUrlAndStatus(url, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "priority")
    public ResponseEntity<ReminderDTO> findReminderByPriority(@RequestParam(ReminderConstantes.PRIORITY) String priority) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByPriorityAndStatus(priority, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "flag")
    public ResponseEntity<ReminderDTO> findReminderByFlag(@RequestParam(ReminderConstantes.FLAG) String flag) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByFlagAndStatus(flag, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dueDate")
    public ResponseEntity<ReminderDTO> findReminderByDueDate(@RequestParam(ReminderConstantes.DUEDATE) Date dueDate) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByDueDateAndStatus(dueDate, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateCreated")
    public ResponseEntity<ReminderDTO> findReminderByDateCreated(@RequestParam(ReminderConstantes.DATECREATED) Date dateCreated) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 200, message = "Indica que o processo Reminder foi executado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu algum problema inesperado"),
    })
    @GetMapping(params = "dateUpdated")
    public ResponseEntity<ReminderDTO> findReminderByDateUpdated(@RequestParam(ReminderConstantes.DATEUPDATED) Date dateUpdated) {
        try{
            ReminderDTO reminderDTO = reminderService.findReminderByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
            return Objects.nonNull(reminderDTO)
                ? new ResponseEntity<>(reminderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ReminderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CommoditieBaseException e) {
            return new ResponseEntity(e.getMensagemResponse(), e.getHttpStatus());
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
