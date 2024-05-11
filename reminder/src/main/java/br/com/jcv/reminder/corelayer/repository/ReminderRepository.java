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


package br.com.jcv.reminder.corelayer.repository;

import java.util.List;
import br.com.jcv.reminder.corelayer.model.Reminder;
import br.com.jcv.reminder.infrastructure.constantes.ReminderConstantes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.UUID;

/**
*
* ReminderRepository - Interface dos métodos de acesso aos dados da tabela tb_reminder
* Camada de dados Reminder - camada responsável SOMENTE pela acesso aos dados do sistema.
* Não é uma camada visível para outros dispositivos, como as camadas de apresentação e aplicação.
*
* Changelog:
*
* @autor Reminder
* @since Sat May 11 18:10:51 BRT 2024
*
*/
@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long>
{
    @Query(value = "SELECT * FROM tb_reminder WHERE  status = :status", nativeQuery = true)
    List<Reminder> findAllByStatus(@Param(ReminderConstantes.STATUS) String status);

@Query(value = "SELECT * FROM tb_reminder WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_reminder = :id) " +
        "AND (cast(:idList as BIGINT) IS NULL OR id_list = :idList) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:note as TEXT) IS NULL OR tx_note = :note) " +
        "AND (cast(:tags as TEXT) IS NULL OR tx_tags = :tags) " +
        "AND (cast(:fullUrlImage as TEXT) IS NULL OR tx_full_url_image = :fullUrlImage) " +
        "AND (cast(:url as TEXT) IS NULL OR tx_url = :url) " +
        "AND (cast(:priority as TEXT) IS NULL OR in_priority = :priority) " +
        "AND (cast(:flag as TEXT) IS NULL OR in_flag = :flag) " +
        "AND (cast(:dueDate as DATE) IS NULL OR to_char(dt_duedate, 'YYYY-MM-DD') = :dueDate) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
Page<Reminder> findReminderByFilter(Pageable pageable,
        @Param(ReminderConstantes.ID) Long id,
        @Param(ReminderConstantes.IDLIST) Long idList,
        @Param(ReminderConstantes.TITLE) String title,
        @Param(ReminderConstantes.NOTE) String note,
        @Param(ReminderConstantes.TAGS) String tags,
        @Param(ReminderConstantes.FULLURLIMAGE) String fullUrlImage,
        @Param(ReminderConstantes.URL) String url,
        @Param(ReminderConstantes.PRIORITY) String priority,
        @Param(ReminderConstantes.FLAG) String flag,
        @Param(ReminderConstantes.DUEDATE) String dueDate,
        @Param(ReminderConstantes.STATUS) String status,
        @Param(ReminderConstantes.DATECREATED) String dateCreated,
        @Param(ReminderConstantes.DATEUPDATED) String dateUpdated

        );

@Query(value = "SELECT * FROM tb_reminder WHERE 1=1 " +
        "AND (cast(:id as BIGINT) IS NULL OR id_reminder = :id) " +
        "AND (cast(:idList as BIGINT) IS NULL OR id_list = :idList) " +
        "AND (cast(:title as TEXT) IS NULL OR tx_title = :title) " +
        "AND (cast(:note as TEXT) IS NULL OR tx_note = :note) " +
        "AND (cast(:tags as TEXT) IS NULL OR tx_tags = :tags) " +
        "AND (cast(:fullUrlImage as TEXT) IS NULL OR tx_full_url_image = :fullUrlImage) " +
        "AND (cast(:url as TEXT) IS NULL OR tx_url = :url) " +
        "AND (cast(:priority as TEXT) IS NULL OR in_priority = :priority) " +
        "AND (cast(:flag as TEXT) IS NULL OR in_flag = :flag) " +
        "AND (cast(:dueDate as DATE) IS NULL OR to_char(dt_duedate, 'YYYY-MM-DD') = :dueDate) " +
        "AND (cast(:status as TEXT) IS NULL OR status = :status) " +
        "AND (cast(:dateCreated as DATE) IS NULL OR to_char(date_created, 'YYYY-MM-DD') = :dateCreated) " +
        "AND (cast(:dateUpdated as DATE) IS NULL OR to_char(date_updated 'YYYY-MM-DD') = :dateUpdated) " 

        , nativeQuery = true)
List<Reminder> findReminderByFilter(
        @Param(ReminderConstantes.ID) Long id,
        @Param(ReminderConstantes.IDLIST) Long idList,
        @Param(ReminderConstantes.TITLE) String title,
        @Param(ReminderConstantes.NOTE) String note,
        @Param(ReminderConstantes.TAGS) String tags,
        @Param(ReminderConstantes.FULLURLIMAGE) String fullUrlImage,
        @Param(ReminderConstantes.URL) String url,
        @Param(ReminderConstantes.PRIORITY) String priority,
        @Param(ReminderConstantes.FLAG) String flag,
        @Param(ReminderConstantes.DUEDATE) String dueDate,
        @Param(ReminderConstantes.STATUS) String status,
        @Param(ReminderConstantes.DATECREATED) String dateCreated,
        @Param(ReminderConstantes.DATEUPDATED) String dateUpdated

);

     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE id_reminder = :id AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdAndStatus(Long id, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE id_list = :idList AND status = :status ", nativeQuery = true)
     Long loadMaxIdByIdListAndStatus(Long idList, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_title = :title AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTitleAndStatus(String title, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_note = :note AND status = :status ", nativeQuery = true)
     Long loadMaxIdByNoteAndStatus(String note, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_tags = :tags AND status = :status ", nativeQuery = true)
     Long loadMaxIdByTagsAndStatus(String tags, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_full_url_image = :fullUrlImage AND status = :status ", nativeQuery = true)
     Long loadMaxIdByFullUrlImageAndStatus(String fullUrlImage, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_url = :url AND status = :status ", nativeQuery = true)
     Long loadMaxIdByUrlAndStatus(String url, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE in_priority = :priority AND status = :status ", nativeQuery = true)
     Long loadMaxIdByPriorityAndStatus(String priority, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE in_flag = :flag AND status = :status ", nativeQuery = true)
     Long loadMaxIdByFlagAndStatus(String flag, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE dt_duedate = :dueDate AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDueDateAndStatus(Date dueDate, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE date_created = :dateCreated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE date_updated = :dateUpdated AND status = :status ", nativeQuery = true)
     Long loadMaxIdByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Modifying
     @Query(value = "UPDATE tb_reminder SET id_list = :idList, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateIdListById(@Param("id") Long id, @Param(ReminderConstantes.IDLIST) Long idList);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET tx_title = :title, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateTitleById(@Param("id") Long id, @Param(ReminderConstantes.TITLE) String title);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET tx_note = :note, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateNoteById(@Param("id") Long id, @Param(ReminderConstantes.NOTE) String note);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET tx_tags = :tags, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateTagsById(@Param("id") Long id, @Param(ReminderConstantes.TAGS) String tags);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET tx_full_url_image = :fullUrlImage, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateFullUrlImageById(@Param("id") Long id, @Param(ReminderConstantes.FULLURLIMAGE) String fullUrlImage);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET tx_url = :url, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateUrlById(@Param("id") Long id, @Param(ReminderConstantes.URL) String url);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET in_priority = :priority, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updatePriorityById(@Param("id") Long id, @Param(ReminderConstantes.PRIORITY) String priority);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET in_flag = :flag, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateFlagById(@Param("id") Long id, @Param(ReminderConstantes.FLAG) String flag);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET dt_duedate = :dueDate, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateDueDateById(@Param("id") Long id, @Param(ReminderConstantes.DUEDATE) Date dueDate);
     @Modifying
     @Query(value = "UPDATE tb_reminder SET status = :status, dt_updated = current_timestamp  WHERE id_reminder = :id", nativeQuery = true)
     void updateStatusById(@Param("id") Long id, @Param(ReminderConstantes.STATUS) String status);


     long countByIdAndStatus(Long id, String status);
     long countByIdListAndStatus(Long idList, String status);
     long countByTitleAndStatus(String title, String status);
     long countByNoteAndStatus(String note, String status);
     long countByTagsAndStatus(String tags, String status);
     long countByFullUrlImageAndStatus(String fullUrlImage, String status);
     long countByUrlAndStatus(String url, String status);
     long countByPriorityAndStatus(String priority, String status);
     long countByFlagAndStatus(String flag, String status);
     long countByDueDateAndStatus(Date dueDate, String status);
     long countByDateCreatedAndStatus(Date dateCreated, String status);
     long countByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE id_reminder = :id AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByIdAndStatus(Long id, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE id_list = :idList AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByIdListAndStatus(Long idList, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_title = :title AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByTitleAndStatus(String title, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_note = :note AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByNoteAndStatus(String note, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_tags = :tags AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByTagsAndStatus(String tags, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_full_url_image = :fullUrlImage AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByFullUrlImageAndStatus(String fullUrlImage, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE tx_url = :url AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByUrlAndStatus(String url, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE in_priority = :priority AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByPriorityAndStatus(String priority, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE in_flag = :flag AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByFlagAndStatus(String flag, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE dt_duedate = :dueDate AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByDueDateAndStatus(Date dueDate, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE date_created = :dateCreated AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByDateCreatedAndStatus(Date dateCreated, String status);
    @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = (SELECT MAX(id_reminder) AS maxid FROM tb_reminder WHERE date_updated = :dateUpdated AND  status = :status) ", nativeQuery = true)
    Optional<Reminder> findByDateUpdatedAndStatus(Date dateUpdated, String status);


     @Query(value = "SELECT * FROM tb_reminder WHERE id_reminder = :id AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByIdAndStatus(Long id, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE id_list = :idList AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByIdListAndStatus(Long idList, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE tx_title = :title AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByTitleAndStatus(String title, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE tx_note = :note AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByNoteAndStatus(String note, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE tx_tags = :tags AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByTagsAndStatus(String tags, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE tx_full_url_image = :fullUrlImage AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByFullUrlImageAndStatus(String fullUrlImage, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE tx_url = :url AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByUrlAndStatus(String url, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE in_priority = :priority AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByPriorityAndStatus(String priority, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE in_flag = :flag AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByFlagAndStatus(String flag, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE dt_duedate = :dueDate AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByDueDateAndStatus(Date dueDate, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE date_created = :dateCreated AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByDateCreatedAndStatus(Date dateCreated, String status);
     @Query(value = "SELECT * FROM tb_reminder WHERE date_updated = :dateUpdated AND  status = :status ", nativeQuery = true)
     List<Reminder> findAllByDateUpdatedAndStatus(Date dateUpdated, String status);


    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE id_reminder = :id", nativeQuery = true)
    void deleteById(@Param(ReminderConstantes.ID) Long id);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE id_list = :idList", nativeQuery = true)
    void deleteByIdList(@Param(ReminderConstantes.IDLIST) Long idList);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE tx_title = :title", nativeQuery = true)
    void deleteByTitle(@Param(ReminderConstantes.TITLE) String title);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE tx_note = :note", nativeQuery = true)
    void deleteByNote(@Param(ReminderConstantes.NOTE) String note);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE tx_tags = :tags", nativeQuery = true)
    void deleteByTags(@Param(ReminderConstantes.TAGS) String tags);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE tx_full_url_image = :fullUrlImage", nativeQuery = true)
    void deleteByFullUrlImage(@Param(ReminderConstantes.FULLURLIMAGE) String fullUrlImage);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE tx_url = :url", nativeQuery = true)
    void deleteByUrl(@Param(ReminderConstantes.URL) String url);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE in_priority = :priority", nativeQuery = true)
    void deleteByPriority(@Param(ReminderConstantes.PRIORITY) String priority);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE in_flag = :flag", nativeQuery = true)
    void deleteByFlag(@Param(ReminderConstantes.FLAG) String flag);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE dt_duedate = :dueDate", nativeQuery = true)
    void deleteByDueDate(@Param(ReminderConstantes.DUEDATE) Date dueDate);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE status = :status", nativeQuery = true)
    void deleteByStatus(@Param(ReminderConstantes.STATUS) String status);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE date_created = :dateCreated", nativeQuery = true)
    void deleteByDateCreated(@Param(ReminderConstantes.DATECREATED) Date dateCreated);
    @Modifying
    @Query(value = "DELETE FROM tb_reminder WHERE date_updated = :dateUpdated", nativeQuery = true)
    void deleteByDateUpdated(@Param(ReminderConstantes.DATEUPDATED) Date dateUpdated);

}
