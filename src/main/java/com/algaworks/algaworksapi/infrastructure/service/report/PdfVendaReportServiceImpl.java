package com.algaworks.algaworksapi.infrastructure.service.report;

import com.algaworks.algaworksapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algaworksapi.domain.model.dto.VendaDiaria;
import com.algaworks.algaworksapi.domain.service.VendaQueryService;
import com.algaworks.algaworksapi.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class PdfVendaReportServiceImpl implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            List<VendaDiaria> vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}
