import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Apriori {

    public static void main(String[] args) {
        // Configurações de Limiar
        String caminhoArquivo = "transacoes.txt";
        double suporteMinimo = 0.2; // 20%
        double confiancaMinima = 0.6; // 60%

        List<List<String>> transacoes = new ArrayList<>();

        // 1. Ler base de dados do disco
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty())
                    continue;
                List<String> itens = Arrays.stream(linha.split(","))
                        .map(i -> i.trim().toLowerCase())
                        .collect(Collectors.toList());
                transacoes.add(itens);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        int totalTransacoes = transacoes.size();
        System.out.println("Total de Transações: " + totalTransacoes);
        System.out.printf("Suporte Mínimo: %.0f%% | Confiança Mínima: %.0f%%\n\n",
                suporteMinimo * 100, confiancaMinima * 100);

        // 2. Contar suporte de itens individuais
        Map<String, Integer> contagemItens = new HashMap<>();
        for (List<String> t : transacoes) {
            for (String item : t) {
                contagemItens.put(item, contagemItens.getOrDefault(item, 0) + 1);
            }
        }

        // Filtrar itens frequentes (L1)
        Map<String, Integer> itensFrequentes = contagemItens.entrySet().stream()
                .filter(x -> (double) x.getValue() / totalTransacoes >= suporteMinimo)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 3. Gerar Regras de Associação (A -> B)
        System.out.println("Regras de Associação Encontradas:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s | %-10s | %-10s\n", "Regra", "Suporte", "Confiança");

        List<String> listaItens = new ArrayList<>(itensFrequentes.keySet());
        for (String itemA : listaItens) {
            for (String itemB : listaItens) {
                if (itemA.equals(itemB))
                    continue;

                // Calcular Suporte(A e B)
                long contagemAmbos = transacoes.stream()
                        .filter(t -> t.contains(itemA) && t.contains(itemB))
                        .count();

                double suporteAmbos = (double) contagemAmbos / totalTransacoes;

                if (suporteAmbos >= suporteMinimo) {
                    // Calcular Confiança(A -> B) = Suporte(A e B) / Suporte(A)
                    double confianca = (double) contagemAmbos / itensFrequentes.get(itemA);

                    if (confianca >= confiancaMinima) {
                        System.out.printf("%-20s | %-10.0f%% | %-10.0f%%\n",
                                itemA + " -> " + itemB, suporteAmbos * 100, confianca * 100);
                    }
                }
            }
        }
    }
}