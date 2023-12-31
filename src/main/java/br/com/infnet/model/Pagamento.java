package br.com.infnet.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Pagamento {
    private List<Produto> produtos;
    private LocalDate dataCompra;
    private Cliente cliente;

    public Pagamento(List<Produto> produtos, LocalDate dataCompra, Cliente cliente) {
        this.produtos = produtos;
        this.dataCompra = dataCompra;
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public BigDecimal calcularValorTotal() {
        return produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularValorTotalDouble(Double desconto) {
        BigDecimal valorTotalDouble = produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (desconto != null) {
            BigDecimal descontoPercentual = BigDecimal.valueOf(desconto / 100.0);
            BigDecimal valorDescontoAplicado = valorTotalDouble.multiply(descontoPercentual);
            valorTotalDouble = valorTotalDouble.subtract(valorDescontoAplicado);
        }

        return valorTotalDouble;
    }

    public int getQuantidadeProdutosVendidos() {
        return produtos.size();
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }
}

