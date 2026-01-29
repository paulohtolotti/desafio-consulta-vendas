package com.devsuperior.dsmeta.dto;

public class SummaryDTO {

    private String selerName;
    private Double total;

    public SummaryDTO() {

    }

    public SummaryDTO(String selerName, Double total) {
        this.selerName = selerName;
        this.total = total;
    }

    public String getSelerName() {
        return selerName;
    }

    public Double getTotal() {
        return total;
    }
}
