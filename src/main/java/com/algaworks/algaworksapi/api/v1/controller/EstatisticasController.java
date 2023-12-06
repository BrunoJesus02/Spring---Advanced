package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algaworksapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algaworksapi.domain.model.dto.VendaDiaria;
import com.algaworks.algaworksapi.domain.service.VendaQueryService;
import com.algaworks.algaworksapi.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

    @Autowired
    private VendaReportService vendaReportService;

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private LinksGenerator linksGenerator;

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {}

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticasModel estatisticas() {
        var estatisticasModel = new EstatisticasModel();

        estatisticasModel.add(linksGenerator.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticasModel;
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                               @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                               @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffSet);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }
}
