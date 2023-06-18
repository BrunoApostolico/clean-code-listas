package br.com.infnet.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

    public class Assinatura {
        private BigDecimal mensalidade;
        private LocalDate begin;
        private Optional<LocalDate> end;
        private Cliente cliente;

        public Assinatura(BigDecimal mensalidade, LocalDate begin, Optional<LocalDate> end, Cliente cliente) {
            this.mensalidade = mensalidade;
            this.begin = begin;
            this.end = end;
            this.cliente = cliente;
        }

        @Override
        public String toString() {
            String status = end.isPresent() ? "Encerrada" : "Ativa";
            return "Assinatura [Mensalidade: " + mensalidade + ", Inicio: " + begin + ", Fim: " + end.orElse(null) + ", Status: " + status + ", Cliente: " + cliente.getNome() + "]";
        }

        public BigDecimal getMensalidade() {
            return mensalidade;
        }

        public LocalDate getBegin() {
            return begin;
        }

        public Optional<LocalDate> getEnd() {
            return end;
        }

        public Cliente getCliente() {
            return cliente;
        }
}


