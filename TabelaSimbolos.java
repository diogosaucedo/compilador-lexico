import java.util.*;

/**
 * Classe que gerencia a tabela de símbolos do compilador
 */
public class TabelaSimbolos {
    private Map<String, Simbolo> tabela;

    public TabelaSimbolos() {
        this.tabela = new HashMap<>();
    }

    /**
     * Adiciona um símbolo à tabela
     */
    public void adicionarSimbolo(String nome, String tipo, int linha) {
        if (!tabela.containsKey(nome)) {
            tabela.put(nome, new Simbolo(nome, tipo, linha));
        }
    }

    /**
     * Busca um símbolo na tabela
     */
    public Simbolo buscarSimbolo(String nome) {
        return tabela.get(nome);
    }

    /**
     * Verifica se um símbolo existe na tabela
     */
    public boolean existeSimbolo(String nome) {
        return tabela.containsKey(nome);
    }

    /**
     * Marca um símbolo como declarado
     */
    public void marcarComoDeclarado(String nome) {
        Simbolo simbolo = tabela.get(nome);
        if (simbolo != null) {
            simbolo.setDeclarado(true);
        }
    }

    /**
     * Define o tipo de um símbolo
     */
    public void definirTipo(String nome, String tipo) {
        Simbolo simbolo = tabela.get(nome);
        if (simbolo != null) {
            simbolo.setTipo(tipo);
        }
    }

    /**
     * Retorna todos os símbolos da tabela
     */
    public Collection<Simbolo> getSimbolos() {
        return tabela.values();
    }

    /**
     * Exibe a tabela de símbolos
     */
    public void exibirTabela() {
        System.out.println("\n=== TABELA DE SÍMBOLOS ===");
        System.out.printf("%-15s %-10s %-8s %-10s%n", "Nome", "Tipo", "Linha", "Declarado");
        System.out.println("------------------------------------------------");

        for (Simbolo simbolo : tabela.values()) {
            System.out.printf("%-15s %-10s %-8d %-10s%n",
                    simbolo.getNome(),
                    simbolo.getTipo(),
                    simbolo.getLinha(),
                    simbolo.isDeclarado() ? "Sim" : "Não");
        }
        System.out.println();
    }
}
