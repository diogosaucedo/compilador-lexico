/**
 * Enumeração que define os tipos de tokens reconhecidos pelo analisador léxico
 */
public enum TipoToken {
    // Palavras reservadas
    PROGRAM,
    VAR,
    BEGIN,
    END,
    INTEGER,
    WRITELN,
    IF,
    THEN,
    ELSE,
    CHAR,
    WRITE,
    READLN,
    DIV,

    // Operadores e símbolos
    ATRIBUICAO_PASCAL, // :=
    ATRIBUICAO_ERRO, // =
    SOMA, // +
    SUBTRACAO, // -
    MULTIPLICACAO, // *
    DIVISAO, // /
    IGUAL, // =
    DIFERENTE, // <>
    MENOR, // <
    MAIOR, // >
    MENOR_IGUAL, // <=
    MAIOR_IGUAL, // >=
    PONTO_VIRGULA, // ;
    DOIS_PONTOS, // :
    VIRGULA, // ,
    PONTO, // .
    PARENTESE_ESQ, // (
    PARENTESE_DIR, // )

    // Literais
    NUMERO,
    STRING,
    IDENTIFICADOR,

    // Especiais
    EOF,
    ERRO,

    // Para palavras reservadas incorretas
    PALAVRA_RESERVADA_ERRO
}
