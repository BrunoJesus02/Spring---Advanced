package com.algaworks.algaworksapi.domain.service;

import com.algaworks.algaworksapi.domain.filter.VendaDiariaFilter;
import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
}
