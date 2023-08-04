package com.algaworks.algaworksapi.domain.model;

import com.algaworks.algaworksapi.domain.model.enums.StatusPedido;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @Column(columnDefinition = "datetime")
    @CreationTimestamp
    private OffsetDateTime dataConfirmacao;

    @Column(columnDefinition = "datetime")
    @CreationTimestamp
    private OffsetDateTime dataCancelamento;

    @Column(columnDefinition = "datetime")
    @CreationTimestamp
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Embedded
    private Endereco enderecoEntrega;

    @ManyToOne
    @JoinColumn(nullable = false, name = "usuario_cliente_id")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        this.subtotal = getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {
        setTaxaFrete(getRestaurante().getTaxaFrete());
    }

    public void atribuirPedidoAosItens() {
        getItens().forEach(item -> item.setPedido(this));
    }
}
