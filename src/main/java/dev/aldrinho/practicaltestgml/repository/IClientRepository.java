package dev.aldrinho.practicaltestgml.repository;

import dev.aldrinho.practicaltestgml.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

    List<Client> findClientsByBusinessIdContainsIgnoreCase(String businessId);
    List<Client> findClientsBySharedKeyContainsIgnoreCase(String sharedKey);
    List<Client> findClientsByEmail(String email);
    List<Client> findClientsByPhone(String phone);
    @Query("SELECT c FROM Client c WHERE c.dataAdded BETWEEN :startDate AND :endDate")
    List<Client> findByDateAddedBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
