package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE (obj.date BETWEEN :minDate AND :maxDate)" +
            "GROUP BY obj.seller.name")
    List<SummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate);
}
