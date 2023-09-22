package com.algaworks.algaworksapi.domain.service;

import com.algaworks.algaworksapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algaworksapi.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);

}
