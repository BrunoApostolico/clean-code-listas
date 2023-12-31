package br.com.infnet;

import br.com.infnet.model.Assinatura;
import br.com.infnet.model.Cliente;
import br.com.infnet.model.Pagamento;
import br.com.infnet.model.Produto;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("--- Exercicio 01 --- Criar produtos, clientes e pagamentos");
        System.out.println("--- Exercicio 02 --- Ordenar pela data da compra");
        System.out.println("--- Exercicio 03 --- Calcular a soma dos valores com optional e double");
        System.out.println("--- Exercicio 04 --- Calcular os valores de todos os pagamentos");
        System.out.println("--- Exercicio 05 --- Imprimir a quantidade de produtos vendidos");
        System.out.println("--- Exercicio 06 --- Criar um mapa de Clientes, produtos");
        System.out.println("--- Exercicio 07 --- Cliente que gastou mais");
        System.out.println("--- Exercicio 08 --- Quanto foi faturado por mês");
        System.out.println("--- Exercicio 09 --- Criação de 3 assinaturas");
        System.out.println("--- Exercicio 10 --- Imprima o tempo em meses de alguma assinatura ainda ativa.");
        System.out.println("--- Exercicio 11 --- Imprima o tempo de meses entre o start e end de todas assinaturas.");
        System.out.println("--- Exercicio 12 --- Calcule o valor pago em cada assinatura até o momento.");
        System.out.println("----------------------------------");

        // Criar produtos
        Produto produto1 = new Produto("Musica 1", Path.of("caminho/para/musica1.mp3"), BigDecimal.valueOf(10.00));
        Produto produto2 = new Produto("Video 1", Path.of("caminho/para/video1.mp4"), BigDecimal.valueOf(19.99));
        Produto produto3 = new Produto("Imagem 1", Path.of("caminho/para/imagem1.jpg"), BigDecimal.valueOf(5.50));

        // Criar clientes
        Cliente cliente1 = new Cliente("Bruno");
        Cliente cliente2 = new Cliente("Priscila");
        Cliente cliente3 = new Cliente("Eloah");

        // Criar uma lista de produtos para os pagamentos
        List<Produto> listaProdutos1 = Arrays.asList(produto1, produto2);
        List<Produto> listaProdutos2 = Collections.singletonList(produto3);
        List<Produto> listaProdutos3 = Arrays.asList(produto1, produto2, produto3);

        // Criar pagamentos com diferentes datas
        LocalDate hoje = LocalDate.now();
        LocalDate ontem = LocalDate.now().minusDays(1);
        LocalDate mesPassado = LocalDate.now().minusMonths(1);

        Pagamento pagamento1 = new Pagamento(listaProdutos1, hoje, cliente1);
        Pagamento pagamento2 = new Pagamento(listaProdutos2, ontem, cliente2);
        Pagamento pagamento3 = new Pagamento(listaProdutos3, mesPassado, cliente3);

        // Criar uma lista de pagamentos
        List<Pagamento> pagamentos = Arrays.asList(pagamento1, pagamento2, pagamento3);

        // Ordenar os pagamentos pela data de compra (Exercicio 2)
        Collections.sort(pagamentos, Comparator.comparing(Pagamento::getDataCompra));

        // Calcular e exibir o valor total de cada pagamento (Exercicio 3)
/*        for (Pagamento pagamento : pagamentos) {
            BigDecimal valorTotal = pagamento.calcularValorTotal(Optional.of(10.0)); // Exemplo de desconto de 10% usando Optional
            BigDecimal valorTotalDouble = pagamento.calcularValorTotalDouble(10.0); // Exemplo de desconto de 10% usando Double diretamente
            exibirInformacoesPagamento(pagamento, valorTotal, valorTotalDouble);
        }*/

        // Calcular o valor total de todos os pagamentos (Exercicio 4)
/*        BigDecimal valorTotalPagamentos = BigDecimal.ZERO;
        for (Pagamento pagamento : pagamentos) {
            BigDecimal valorTotal = pagamento.calcularValorTotal(Optional.of(10.0)); // Exemplo de desconto de 10% usando Optional
            BigDecimal valorTotalDouble = pagamento.calcularValorTotalDouble(10.0); // Exemplo de desconto de 10% usando Double diretamente
            exibirInformacoesPagamento(pagamento, valorTotal, valorTotalDouble);
            valorTotalPagamentos = valorTotalPagamentos.add(valorTotal);
        }

        System.out.println("Valor Total de todos os pagamentos: " + valorTotalPagamentos);*/

        // Imprimir a quantidade de produtos vendidos (Exercicio 5)
        System.out.println("Pagamentos ordenados pela data de compra:");
        BigDecimal valorTotalPagamentos = BigDecimal.ZERO;
        for (Pagamento pagamento : pagamentos) {
            BigDecimal valorTotal = pagamento.calcularValorTotal();
            BigDecimal valorTotalDouble = pagamento.calcularValorTotalDouble(10.0); // Exemplo de desconto de 10% usando Double diretamente

            int quantidadeProdutos = pagamento.getQuantidadeProdutosVendidos();

            exibirInformacoesPagamento(pagamento, valorTotal, valorTotalDouble, quantidadeProdutos);
            valorTotalPagamentos = valorTotalPagamentos.add(valorTotal);
        }

        System.out.println("Valor Total de todos os pagamentos: " + valorTotalPagamentos);
        System.out.println("----------------------------------");

        // Criar o mapa de Cliente para List<Produto> (Exercicio 6)
        Map<String, List<Produto>> mapaClienteProdutos = new HashMap<>();

        // Associar os produtos a cada cliente no mapa
        for (Pagamento pagamento : pagamentos) {
            String nomeCliente = pagamento.getCliente().getNome();
            List<Produto> produtosCliente = mapaClienteProdutos.getOrDefault(nomeCliente, new ArrayList<>());
            produtosCliente.addAll(pagamento.getProdutos());
            mapaClienteProdutos.put(nomeCliente, produtosCliente);
        }

        // Imprimir o mapa de Cliente para List<Produto>
        for (Map.Entry<String, List<Produto>> entry : mapaClienteProdutos.entrySet()) {
            String nomeCliente = entry.getKey();
            List<Produto> produtosCliente = entry.getValue();

            System.out.println("Cliente do Mapa: " + nomeCliente);
            System.out.println("Produtos comprados:");
            for (Produto produto : produtosCliente) {
                System.out.println("- " + produto.getNome());
            }
            System.out.println("----------------------------------");
        }

        // Encontrar o cliente que gastou mais (Exercicio 7)
        Map<Cliente, BigDecimal> gastosPorCliente = new HashMap<>();
        for (Pagamento pagamento : pagamentos) {
            Cliente cliente = pagamento.getCliente();
            BigDecimal valorPago = gastosPorCliente.getOrDefault(cliente, BigDecimal.ZERO);
            valorPago = valorPago.add(pagamento.calcularValorTotal());
            gastosPorCliente.put(cliente, valorPago);
        }

        Cliente clienteComMaiorGasto = Collections.max(gastosPorCliente.entrySet(), Map.Entry.comparingByValue()).getKey();
        BigDecimal maiorGasto = gastosPorCliente.get(clienteComMaiorGasto);

        System.out.println("Cliente que gastou mais: " + clienteComMaiorGasto.getNome());
        System.out.println("Valor gasto: " + maiorGasto);
        System.out.println("----------------------------------");

        // Calcular o valor total gasto por mês (Exercício 8)
        Map<Integer, BigDecimal> faturamentoPorMes = new HashMap<>();
        for (Pagamento pagamento : pagamentos) {
            int mes = pagamento.getDataCompra().getMonthValue();
            BigDecimal valorPago = faturamentoPorMes.getOrDefault(mes, BigDecimal.ZERO);
            valorPago = valorPago.add(pagamento.calcularValorTotal());
            faturamentoPorMes.put(mes, valorPago);
        }

        System.out.println("Faturamento por mês:");
        for (Map.Entry<Integer, BigDecimal> entry : faturamentoPorMes.entrySet()) {
            int mes = entry.getKey();
            BigDecimal valorFaturado = entry.getValue();
            System.out.println("Mês " + mes + ": " + valorFaturado);
        }

        // Criação das assinaturas (Exercício 9)
        Assinatura assinatura1 = new Assinatura(new BigDecimal("99.98"), LocalDate.now().minusMonths(3), Optional.empty(), cliente1);
        Assinatura assinatura2 = new Assinatura(new BigDecimal("99.98"), LocalDate.now().minusMonths(6), Optional.of(LocalDate.now().minusMonths(1)), cliente2);
        Assinatura assinatura3 = new Assinatura(new BigDecimal("99.98"), LocalDate.now().minusMonths(2), Optional.of(LocalDate.now().minusDays(10)), cliente3);

        // Imprimindo as assinaturas
        System.out.println("----------------------------------");
        System.out.println("Assinaturas:");
        System.out.println(assinatura1);
        System.out.println(assinatura2);
        System.out.println(assinatura3);

        // Impressão do tempo em meses das assinaturas ativas (Exercicio 10)
        System.out.println("----------------------------------");
        System.out.println("Tempo de meses das assinaturas ativas:");
        if (assinatura1.isAtiva()) {
            System.out.println("Assinatura 1: " + assinatura1.getTempoEmMesesAtiva() + " meses");
        }
        if (assinatura2.isAtiva()) {
            System.out.println("Assinatura 2: " + assinatura2.getTempoEmMesesAtiva() + " meses");
        }
        if (assinatura3.isAtiva()) {
            System.out.println("Assinatura 3: " + assinatura3.getTempoEmMesesAtiva() + " meses");
        }

        System.out.println("----------------------------------");

        // Impressão do tempo em meses entre start e end de todas as assinaturas (Exercício 11)
        System.out.println("Tempo de meses entre o início e o fim de todas as assinaturas:");
        System.out.println("Assinatura 1: " + assinatura1.getTempoEntreInicioEFimEmMeses() + " meses");
        System.out.println("Assinatura 2: " + assinatura2.getTempoEntreInicioEFimEmMeses() + " meses");
        System.out.println("Assinatura 3: " + assinatura3.getTempoEntreInicioEFimEmMeses() + " meses");
        System.out.println("----------------------------------");

        // Impressão do valor pago em cada assinatura até o momento
        System.out.println("Valor pago em cada assinatura até o momento:");
        System.out.println("Assinatura 1: " + assinatura1.getValorPagoAteMomento());
        System.out.println("Assinatura 2: " + assinatura2.getValorPagoAteMomento());
        System.out.println("Assinatura 3: " + assinatura3.getValorPagoAteMomento());
        System.out.println("----------------------------------");


    }

    public static void exibirInformacoesPagamento(Pagamento pagamento, BigDecimal valorTotal, BigDecimal valorTotalDouble, int quantidadeProdutos) {
        System.out.println("Cliente: " + pagamento.getCliente().getNome());
        System.out.println("Data da Compra: " + pagamento.getDataCompra());
        System.out.println("Valor Total Optional: " + valorTotal);
        System.out.println("Valor Total Double: " + valorTotalDouble);
        System.out.println("Produtos comprados:");
        for (Produto produto : pagamento.getProdutos()) {
            System.out.println("- " + produto.getNome());
        }
        System.out.println("Quantidade de produtos vendidos: " + quantidadeProdutos);
        System.out.println("----------------------------------");
    }
}