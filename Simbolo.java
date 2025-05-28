/**
 * Classe que representa um símbolo na tabela de símbolos
 * @author Diogo Ferreira Saucedo
 */
public class Simbolo {
    private String nome;
    private String tipo;
    private int linha;
    private boolean declarado;

    public Simbolo(String nome, String tipo, int linha) {
        this.nome = nome;
        this.tipo = tipo;
        this.linha = linha;
        this.declarado = false;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLinha() {
        return linha;
    }

    public boolean isDeclarado() {
        return declarado;
    }

    public void setDeclarado(boolean declarado) {
        this.declarado = declarado;
    }

    @Override
    public String toString() {
        return String.format("Simbolo{nome='%s', tipo='%s', linha=%d, declarado=%s}",
                nome, tipo, linha, declarado);
    }
}
