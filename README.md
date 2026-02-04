# Funcionamento do Software – Algoritmo Apriori (Resumo)

## Visão Geral
O software implementa uma versão simplificada do algoritmo Apriori para mineração de regras de associação. Seu objetivo é identificar padrões de coocorrência entre itens em uma base de transações, utilizando os conceitos de suporte e confiança.

## Entrada de Dados
- Lê um arquivo texto (`transacoes.txt`)
- Cada linha representa uma transação
- Os itens são separados por vírgula

## Parâmetros
- **Suporte mínimo**: frequência mínima para um item ou combinação ser considerada relevante
- **Confiança mínima**: grau mínimo de associação entre dois itens

## Processamento
1. Lê e armazena todas as transações em memória
2. Conta a frequência de cada item individual
3. Filtra os itens que atingem o suporte mínimo (L1)
4. Gera regras de associação do tipo **A → B**
5. Calcula:
   - Suporte(A ∩ B)
   - Confiança(A → B)
6. Exibe apenas as regras que atendem aos limiares definidos

## Saída
- Total de transações analisadas
- Regras de associação válidas
- Valores de suporte e confiança em porcentagem

## Observações
- O algoritmo trabalha apenas com pares de itens
- Implementação voltada para fins didáticos e acadêmicos
- Não inclui otimizações avançadas do Apriori
