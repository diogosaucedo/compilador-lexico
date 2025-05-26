import java.io.*;
import java.nio.file.*;
import java.util.List;

/**
 * Classe principal do compilador léxico para Pascal
 */
public class CompiladorMain {
    public static void main(String[] args) {
        String nomeArquivo;

        // Permite escolher o arquivo via argumento da linha de comando
        if (args.length > 0) {
            nomeArquivo = args[0];
        } else {
            // Lista os arquivos disponíveis
            System.out.println("=== COMPILADOR LÉXICO PASCAL ===");
            System.out.println("Arquivos disponíveis para análise:");
            System.out.println("1. Programa_Fonte.txt (com erros)");
            System.out.println("2. Programa_Simples.txt (correto)");
            System.out.println("3. Programa_Correto.txt (correto e complexo)");
            System.out.println("\nAnalisando o arquivo padrão: Programa_Fonte.txt");
            System.out.println("Para analisar outro arquivo, execute: java CompiladorMain <nome_do_arquivo>");
            System.out.println();
            nomeArquivo = "Programa_Fonte.txt";
        }

        try {
            // Lê o arquivo fonte
            String codigoFonte = lerArquivo(nomeArquivo);

            System.out.println("=== COMPILADOR LÉXICO PASCAL ===");
            System.out.println("Arquivo: " + nomeArquivo);
            System.out.println("\nCódigo fonte:");
            System.out.println("----------------------------------------");
            System.out.println(codigoFonte);
            System.out.println("----------------------------------------");

            // Cria o analisador léxico
            AnalisadorLexico analisador = new AnalisadorLexico(codigoFonte);

            // Realiza a análise
            analisador.analisar();

            // Exibe os tokens encontrados
            exibirTokens(analisador.getTokens());

            // Exibe a tabela de símbolos
            analisador.getTabelaSimbolos().exibirTabela();

            // Exibe os erros encontrados
            exibirErros(analisador.getErros());

            // Gera relatório
            gerarRelatorio(analisador, nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    /**
     * Lê o conteúdo de um arquivo
     */
    private static String lerArquivo(String nomeArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(nomeArquivo)));
    }

    /**
     * Exibe os tokens encontrados
     */
    private static void exibirTokens(List<Token> tokens) {
        System.out.println("\n=== TOKENS ENCONTRADOS ===");
        System.out.printf("%-4s %-20s %-15s %-8s %-8s%n", "#", "Tipo", "Valor", "Linha", "Coluna");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            System.out.printf("%-4d %-20s %-15s %-8d %-8d%n",
                    i + 1,
                    token.getTipo(),
                    "'" + token.getValor() + "'",
                    token.getLinha(),
                    token.getColuna());
        }
        System.out.println();
    }

    /**
     * Exibe os erros encontrados
     */
    private static void exibirErros(List<String> erros) {
        System.out.println("\n=== RELATÓRIO DE ERROS ===");

        if (erros.isEmpty()) {
            System.out.println("Nenhum erro encontrado! ✓");
        } else {
            System.out.println("Total de erros encontrados: " + erros.size());
            System.out.println();

            for (int i = 0; i < erros.size(); i++) {
                System.out.println((i + 1) + ". " + erros.get(i));
            }
        }
        System.out.println();
    }

    /**
     * Gera um relatório detalhado da análise
     */
    private static void gerarRelatorio(AnalisadorLexico analisador, String nomeArquivo) {
        try {
            String nomeRelatorio = "relatorio_analise.txt";
            PrintWriter writer = new PrintWriter(nomeRelatorio);

            writer.println("=== RELATÓRIO DE ANÁLISE LÉXICA ===");
            writer.println("Arquivo analisado: " + nomeArquivo);
            writer.println("Data/Hora: " + java.time.LocalDateTime.now());
            writer.println();

            // Estatísticas
            List<Token> tokens = analisador.getTokens();
            List<String> erros = analisador.getErros();

            writer.println("=== ESTATÍSTICAS ===");
            writer.println("Total de tokens: " + tokens.size());
            writer.println("Total de erros: " + erros.size());
            writer.println("Total de símbolos na tabela: " + analisador.getTabelaSimbolos().getSimbolos().size());
            writer.println();

            // Tokens
            writer.println("=== TOKENS ENCONTRADOS ===");
            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                writer.printf("%d. %s - '%s' (linha %d, coluna %d)%n",
                        i + 1, token.getTipo(), token.getValor(),
                        token.getLinha(), token.getColuna());
            }
            writer.println();

            // Tabela de símbolos
            writer.println("=== TABELA DE SÍMBOLOS ===");
            writer.printf("%-15s %-10s %-8s %-10s%n", "Nome", "Tipo", "Linha", "Declarado");
            writer.println("------------------------------------------------");

            for (Simbolo simbolo : analisador.getTabelaSimbolos().getSimbolos()) {
                writer.printf("%-15s %-10s %-8d %-10s%n",
                        simbolo.getNome(),
                        simbolo.getTipo(),
                        simbolo.getLinha(),
                        simbolo.isDeclarado() ? "Sim" : "Não");
            }
            writer.println();

            // Erros
            writer.println("=== ERROS ENCONTRADOS ===");
            if (erros.isEmpty()) {
                writer.println("Nenhum erro encontrado!");
            } else {
                for (int i = 0; i < erros.size(); i++) {
                    writer.println((i + 1) + ". " + erros.get(i));
                }
            }

            writer.close();
            System.out.println("Relatório salvo em: " + nomeRelatorio);

        } catch (IOException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
