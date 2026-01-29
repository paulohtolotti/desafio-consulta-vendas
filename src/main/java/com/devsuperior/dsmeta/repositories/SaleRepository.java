package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.projections.ReportProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE (obj.date BETWEEN :minDate AND :maxDate) " +
            "GROUP BY obj.seller.name")
    List<SummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate);

    @Query(value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name as sellerName " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
            "WHERE (tb_sales.date BETWEEN :minDate AND :maxDate) AND " +
            "UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))",
            nativeQuery = true,
    countQuery = "SELECT COUNT(*) FROM tb_sales")
    Page<ReportProjection> searchReport(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);
}
