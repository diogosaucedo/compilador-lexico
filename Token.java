/**
 * Classe que representa um token identificado durante a análise léxica
 */
public class Token {
    private TipoToken tipo;
    private String valor;
    private int linha;
    private int coluna;

    public Token(TipoToken tipo, String valor, int linha, int coluna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linha = linha;
        this.coluna = coluna;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    @Override
    public String toString() {
        return String.format("Token{tipo=%s, valor='%s', linha=%d, coluna=%d}",
                tipo, valor, linha, coluna);
    }
}
