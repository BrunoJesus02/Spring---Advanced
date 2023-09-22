package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algaworksapi.domain.model.dto.VendaDiaria;
import com.algaworks.algaworksapi.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
            public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);
    }
}