import java.util.*;

/**
 * Analisador léxico para a linguagem Pascal simplificada
 */
public class AnalisadorLexico {
    private String codigo;
    private int posicao;
    private int linha;
    private int coluna;
    private TabelaSimbolos tabelaSimbolos;
    private List<String> erros;
    private List<Token> tokens; // Palavras reservadas da linguagem Pascal
    private static final Map<String, TipoToken> PALAVRAS_RESERVADAS = new HashMap<>();
    static {
        PALAVRAS_RESERVADAS.put("program", TipoToken.PROGRAM);
        PALAVRAS_RESERVADAS.put("var", TipoToken.VAR);
        PALAVRAS_RESERVADAS.put("begin", TipoToken.BEGIN);
        PALAVRAS_RESERVADAS.put("end", TipoToken.END);
        PALAVRAS_RESERVADAS.put("integer", TipoToken.INTEGER);
        PALAVRAS_RESERVADAS.put("writeln", TipoToken.WRITELN);
        PALAVRAS_RESERVADAS.put("if", TipoToken.IF);
        PALAVRAS_RESERVADAS.put("then", TipoToken.THEN);
        PALAVRAS_RESERVADAS.put("else", TipoToken.ELSE);
        PALAVRAS_RESERVADAS.put("char", TipoToken.CHAR);
        PALAVRAS_RESERVADAS.put("write", TipoToken.WRITE);
        PALAVRAS_RESERVADAS.put("readln", TipoToken.READLN);
        PALAVRAS_RESERVADAS.put("div", TipoToken.DIV);
    }

    // Palavras reservadas com erros de escrita comuns
    private static final Map<String, String> PALAVRAS_ERRO = new HashMap<>();
    static {
        PALAVRAS_ERRO.put("progra", "program");
        PALAVRAS_ERRO.put("begi", "begin");
        PALAVRAS_ERRO.put("intege", "integer");
    }

    public AnalisadorLexico(String codigo) {
        this.codigo = codigo;
        this.posicao = 0;
        this.linha = 1;
        this.coluna = 1;
        this.tabelaSimbolos = new TabelaSimbolos();
        this.erros = new ArrayList<>();
        this.tokens = new ArrayList<>();
    }

    /**
     * Realiza a análise léxica completa do código
     */
    public void analisar() {
        while (posicao < codigo.length()) {
            Token token = proximoToken();
            if (token != null) {
                tokens.add(token);

                // Se for um identificador, adiciona à tabela de símbolos
                if (token.getTipo() == TipoToken.IDENTIFICADOR) {
                    tabelaSimbolos.adicionarSimbolo(token.getValor(), "indefinido", token.getLinha());
                }
            }
        }

        // Adiciona token EOF
        tokens.add(new Token(TipoToken.EOF, "", linha, coluna));

        // Realiza análise semântica básica
        analisarSemantica();
    }

    /**
     * Obtém o próximo token do código
     */
    private Token proximoToken() {
        pularEspacos();

        if (posicao >= codigo.length()) {
            return null;
        }

        char c = codigo.charAt(posicao);
        int linhaToken = linha;
        int colunaToken = coluna;

        // Números
        if (Character.isDigit(c)) {
            return lerNumero(linhaToken, colunaToken);
        }

        // Identificadores e palavras reservadas
        if (Character.isLetter(c)) {
            return lerIdentificadorOuPalavraReservada(linhaToken, colunaToken);
        } // Strings
        if (c == '\'') {
            return lerString(linhaToken, colunaToken);
        }

        // Símbolos especiais
        switch (c) {
            case ':':
                posicao++;
                coluna++;
                if (posicao < codigo.length() && codigo.charAt(posicao) == '=') {
                    posicao++;
                    coluna++;
                    return new Token(TipoToken.ATRIBUICAO_PASCAL, ":=", linhaToken, colunaToken);
                }
                return new Token(TipoToken.DOIS_PONTOS, ":", linhaToken, colunaToken);
            case '=':
                posicao++;
                coluna++;
                // Verifica se estamos na zona de declaração de variáveis ou em expressões
                // condicionais
                boolean estaEmCondicional = false;
                for (int i = tokens.size() - 1; i >= 0; i--) {
                    if (tokens.get(i).getTipo() == TipoToken.IF || tokens.get(i).getTipo() == TipoToken.THEN) {
                        estaEmCondicional = true;
                        break;
                    }
                }

                // Se não estiver em uma expressão condicional, é um erro de atribuição
                if (!estaEmCondicional) {
                    erros.add(String.format(
                            "ERRO (linha %d, coluna %d): Uso incorreto de '=' para atribuição. Use ':=' em Pascal.",
                            linhaToken, colunaToken));
                    return new Token(TipoToken.ATRIBUICAO_ERRO, "=", linhaToken, colunaToken);
                } else {
                    return new Token(TipoToken.IGUAL, "=", linhaToken, colunaToken);
                }

            case ';':
                posicao++;
                coluna++;
                return new Token(TipoToken.PONTO_VIRGULA, ";", linhaToken, colunaToken);

            case ',':
                posicao++;
                coluna++;
                return new Token(TipoToken.VIRGULA, ",", linhaToken, colunaToken);

            case '.':
                posicao++;
                coluna++;
                return new Token(TipoToken.PONTO, ".", linhaToken, colunaToken);

            case '+':
                posicao++;
                coluna++;
                return new Token(TipoToken.SOMA, "+", linhaToken, colunaToken);

            case '-':
                posicao++;
                coluna++;
                return new Token(TipoToken.SUBTRACAO, "-", linhaToken, colunaToken);

            case '*':
                posicao++;
                coluna++;
                return new Token(TipoToken.MULTIPLICACAO, "*", linhaToken, colunaToken);

            case '/':
                posicao++;
                coluna++;
                return new Token(TipoToken.DIVISAO, "/", linhaToken, colunaToken);

            case '<':
                posicao++;
                coluna++;
                if (posicao < codigo.length()) {
                    if (codigo.charAt(posicao) == '=') {
                        posicao++;
                        coluna++;
                        return new Token(TipoToken.MENOR_IGUAL, "<=", linhaToken, colunaToken);
                    } else if (codigo.charAt(posicao) == '>') {
                        posicao++;
                        coluna++;
                        return new Token(TipoToken.DIFERENTE, "<>", linhaToken, colunaToken);
                    }
                }
                return new Token(TipoToken.MENOR, "<", linhaToken, colunaToken);

            case '>':
                posicao++;
                coluna++;
                if (posicao < codigo.length() && codigo.charAt(posicao) == '=') {
                    posicao++;
                    coluna++;
                    return new Token(TipoToken.MAIOR_IGUAL, ">=", linhaToken, colunaToken);
                }
                return new Token(TipoToken.MAIOR, ">", linhaToken, colunaToken);

            case '(':
                posicao++;
                coluna++;
                return new Token(TipoToken.PARENTESE_ESQ, "(", linhaToken, colunaToken);

            case ')':
                posicao++;
                coluna++;
                return new Token(TipoToken.PARENTESE_DIR, ")", linhaToken, colunaToken);

            default:
                posicao++;
                coluna++;
                erros.add(String.format("ERRO (linha %d, coluna %d): Caractere inválido '%c'",
                        linhaToken, colunaToken, c));
                return new Token(TipoToken.ERRO, String.valueOf(c), linhaToken, colunaToken);
        }
    }

    /**
     * Lê um número do código
     */
    private Token lerNumero(int linhaToken, int colunaToken) {
        StringBuilder numero = new StringBuilder();

        while (posicao < codigo.length() && Character.isDigit(codigo.charAt(posicao))) {
            numero.append(codigo.charAt(posicao));
            posicao++;
            coluna++;
        }

        return new Token(TipoToken.NUMERO, numero.toString(), linhaToken, colunaToken);
    }

    /**
     * Lê um identificador ou palavra reservada
     */
    private Token lerIdentificadorOuPalavraReservada(int linhaToken, int colunaToken) {
        StringBuilder palavra = new StringBuilder();

        while (posicao < codigo.length() &&
                (Character.isLetterOrDigit(codigo.charAt(posicao)) || codigo.charAt(posicao) == '_')) {
            palavra.append(codigo.charAt(posicao));
            posicao++;
            coluna++;
        }

        String valor = palavra.toString().toLowerCase();

        // Verifica se é uma palavra reservada
        if (PALAVRAS_RESERVADAS.containsKey(valor)) {
            return new Token(PALAVRAS_RESERVADAS.get(valor), valor, linhaToken, colunaToken);
        }

        // Verifica se é uma palavra reservada com erro de escrita
        if (PALAVRAS_ERRO.containsKey(valor)) {
            String palavraCorreta = PALAVRAS_ERRO.get(valor);
            erros.add(String.format(
                    "ERRO (linha %d, coluna %d): Palavra reservada '%s' escrita incorretamente. Deveria ser '%s'",
                    linhaToken, colunaToken, valor, palavraCorreta));
            return new Token(TipoToken.PALAVRA_RESERVADA_ERRO, valor, linhaToken, colunaToken);
        }

        // É um identificador
        return new Token(TipoToken.IDENTIFICADOR, palavra.toString(), linhaToken, colunaToken);
    }

    /**
     * Lê uma string do código (conteúdo entre aspas simples)
     */
    private Token lerString(int linhaToken, int colunaToken) {
        StringBuilder texto = new StringBuilder();
        posicao++; // Pula a primeira aspa
        coluna++;

        boolean fechouString = false;

        while (posicao < codigo.length()) {
            char c = codigo.charAt(posicao);

            if (c == '\'') {
                // Verifica se é uma aspa escapada ('') que representa uma aspa dentro da string
                if (posicao + 1 < codigo.length() && codigo.charAt(posicao + 1) == '\'') {
                    texto.append(c);
                    posicao += 2; // Pula as duas aspas
                    coluna += 2;
                } else {
                    // É o fim da string
                    posicao++;
                    coluna++;
                    fechouString = true;
                    break;
                }
            } else if (c == '\n' || c == '\r') {
                // String não foi fechada antes da quebra de linha
                erros.add(String.format(
                        "ERRO (linha %d, coluna %d): String não foi fechada antes da quebra de linha",
                        linhaToken, colunaToken));
                return new Token(TipoToken.ERRO, texto.toString(), linhaToken, colunaToken);
            } else {
                texto.append(c);
                posicao++;
                coluna++;
            }
        }

        if (!fechouString) {
            erros.add(String.format(
                    "ERRO (linha %d, coluna %d): String não foi fechada até o fim do arquivo",
                    linhaToken, colunaToken));
            return new Token(TipoToken.ERRO, texto.toString(), linhaToken, colunaToken);
        }

        return new Token(TipoToken.STRING, texto.toString(), linhaToken, colunaToken);
    }

    /**
     * Pula espaços em branco e quebras de linha
     */
    private void pularEspacos() {
        while (posicao < codigo.length()) {
            char c = codigo.charAt(posicao);
            if (c == ' ' || c == '\t') {
                posicao++;
                coluna++;
            } else if (c == '\n' || c == '\r') {
                posicao++;
                linha++;
                coluna = 1;
                if (c == '\r' && posicao < codigo.length() && codigo.charAt(posicao) == '\n') {
                    posicao++; // Pula o \n após \r
                }
            } else {
                break;
            }
        }
    }

    /**
     * Realiza análise semântica básica
     */
    private void analisarSemantica() {
        // Analisa declarações de variáveis
        analisarDeclaracoes();

        // Verifica uso de variáveis não declaradas
        verificarVariaveisNaoDeclaradas();
    }

    /**
     * Analisa as declarações de variáveis
     */
    private void analisarDeclaracoes() {
        boolean dentroSecaoVar = false;
        List<String> variaveisDeclaradas = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            if (token.getTipo() == TipoToken.VAR) {
                dentroSecaoVar = true;
                continue;
            }

            if (token.getTipo() == TipoToken.BEGIN) {
                dentroSecaoVar = false;
                continue;
            }

            if (dentroSecaoVar && token.getTipo() == TipoToken.IDENTIFICADOR) {
                variaveisDeclaradas.add(token.getValor());

                // Procura o tipo após os dois pontos
                int j = i + 1;
                while (j < tokens.size() && tokens.get(j).getTipo() != TipoToken.DOIS_PONTOS) {
                    if (tokens.get(j).getTipo() == TipoToken.IDENTIFICADOR) {
                        variaveisDeclaradas.add(tokens.get(j).getValor());
                    }
                    j++;
                }

                if (j < tokens.size() - 1) {
                    Token tipoToken = tokens.get(j + 1);
                    String tipo = "indefinido";

                    if (tipoToken.getTipo() == TipoToken.INTEGER) {
                        tipo = "integer";
                    } else if (tipoToken.getTipo() == TipoToken.CHAR) {
                        tipo = "char";
                    } else if (tipoToken.getTipo() == TipoToken.PALAVRA_RESERVADA_ERRO &&
                            tipoToken.getValor().equals("intege")) {
                        tipo = "integer";
                    }

                    // Define o tipo para todas as variáveis coletadas
                    for (String var : variaveisDeclaradas) {
                        tabelaSimbolos.definirTipo(var, tipo);
                        // Marca as variáveis como declaradas
                        tabelaSimbolos.marcarComoDeclarado(var);
                    }
                }

                i = j + 1; // Pula para depois do tipo
                variaveisDeclaradas.clear();
            }
        }
    }

    /**
     * Verifica uso de variáveis não declaradas
     */
    private void verificarVariaveisNaoDeclaradas() {
        boolean aposBegin = false;

        for (Token token : tokens) {
            if (token.getTipo() == TipoToken.BEGIN) {
                aposBegin = true;
                continue;
            }

            if (aposBegin && token.getTipo() == TipoToken.IDENTIFICADOR) {
                Simbolo simbolo = tabelaSimbolos.buscarSimbolo(token.getValor());
                if (simbolo == null || !simbolo.isDeclarado()) {
                    erros.add(String.format("ERRO (linha %d, coluna %d): Variável '%s' não foi declarada",
                            token.getLinha(), token.getColuna(), token.getValor()));
                }
            }
        }
    }

    // Getters
    public List<String> getErros() {
        return erros;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public TabelaSimbolos getTabelaSimbolos() {
        return tabelaSimbolos;
    }
}
