package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.projections.ReportProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

    @Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<SummaryDTO> createSummary(String minDate, String maxDate) {

		LocalDate maxDateQuery = maxDate.isBlank() ?
				LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(maxDate);

		LocalDate minDateQuery = minDate.isBlank()
				? maxDateQuery.minusYears(1L) : LocalDate.parse(minDate);

		return repository.searchSummary(minDateQuery, maxDateQuery);
	}

	@Transactional(readOnly = true)
	public Page<ReportDTO> createReport(String minDate, String maxDate, String sellerName, Pageable pageable) {

		String sellerNameQuery = sellerName.isBlank() ? "" : sellerName;

		LocalDate maxDateQuery = maxDate.isBlank() ?
				LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(maxDate);

		LocalDate minDateQuery = minDate.isBlank()
				? maxDateQuery.minusYears(1L) : LocalDate.parse(minDate);

		Page<ReportProjection> projectionPage = repository.searchReport(minDateQuery,
				maxDateQuery, sellerNameQuery, pageable);

		return projectionPage.map(ReportDTO::new);
	}
}
