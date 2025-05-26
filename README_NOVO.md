# Compilador Léxico Pascal

## 📋 Visão Geral

Este projeto implementa um **compilador léxico** para a linguagem Pascal simplificada, desenvolvido em Java para a disciplina de Compiladores. O sistema realiza análise léxica completa, identificando tokens, detectando erros de sintaxe e gerando relatórios detalhados.

### 🎯 Objetivos

- Realizar análise léxica de programas Pascal
- Identificar palavras-chave, operadores e identificadores
- Detectar e reportar erros léxicos e semânticos básicos
- Gerenciar uma tabela de símbolos
- Gerar relatórios detalhados da análise

## 🏗️ Arquitetura do Sistema

O compilador está organizado em classes com responsabilidades bem definidas:

### Classes Principais

| Classe             | Responsabilidade                                          |
| ------------------ | --------------------------------------------------------- |
| `CompiladorMain`   | Ponto de entrada, coordena a análise e gera relatórios    |
| `AnalisadorLexico` | Núcleo da análise léxica, tokenização e detecção de erros |
| `Token`            | Representa um token com tipo, valor e posição             |
| `TipoToken`        | Enumeração com todos os tipos de tokens suportados        |
| `TabelaSimbolos`   | Gerencia identificadores e suas propriedades              |
| `Simbolo`          | Representa um símbolo na tabela                           |

### 🔤 Tokens Suportados

- **Palavras Reservadas**: `program`, `var`, `begin`, `end`, `integer`, `writeln`
- **Operadores**: `+`, `-`, `*`, `/`, `:=`, `=`, `<>`, `<`, `>`, `<=`, `>=`
- **Delimitadores**: `;`, `,`, `.`, `(`, `)`, `:`
- **Literais**: números inteiros, strings
- **Identificadores**: nomes de variáveis e funções

### 🚨 Detecção de Erros

O compilador identifica diversos tipos de erros:

- Palavras reservadas mal escritas (`progra` → `program`)
- Identificadores não declarados
- Símbolos não reconhecidos
- Strings não fechadas
- Uso de operadores inválidos

## 📂 Estrutura do Projeto

```
compilador-lexico/
├── src/
│   ├── AnalisadorLexico.java    # Analisador léxico principal
│   ├── CompiladorMain.java      # Classe principal
│   ├── Token.java               # Representação de tokens
│   ├── TipoToken.java          # Tipos de tokens
│   ├── TabelaSimbolos.java     # Tabela de símbolos
│   └── Simbolo.java            # Representação de símbolos
├── exemplos/
│   ├── Programa_Fonte.txt      # Programa com erros (objetivo principal)
│   ├── Programa_Simples.txt    # Exemplo simples correto
│   └── Programa_Correto.txt    # Exemplo complexo correto
├── scripts/
│   ├── compilar_e_executar.bat # Script de compilação e execução
│   └── testar_todos.bat        # Script para testar todos os exemplos
└── README.md
```

## 🚀 Como Usar

### Pré-requisitos

- **Java 8+** instalado
- **Variável PATH** configurada para o Java

### Compilação

```bash
# Compilar todas as classes
javac *.java

# Ou usar o script (Windows)
compilar_e_executar.bat
```

### Execução

#### Análise do arquivo padrão

```bash
java CompiladorMain
```

#### Análise de arquivo específico

```bash
java CompiladorMain <nome_do_arquivo>

# Exemplos:
java CompiladorMain Programa_Fonte.txt
java CompiladorMain Programa_Simples.txt
java CompiladorMain Programa_Correto.txt
```

#### Executar todos os testes

```bash
# Windows
testar_todos.bat

# Manual
java CompiladorMain Programa_Fonte.txt
java CompiladorMain Programa_Simples.txt
java CompiladorMain Programa_Correto.txt
```

## 📊 Relatórios Gerados

O compilador gera saídas detalhadas:

### 1. Saída no Console

- Lista de tokens encontrados
- Tabela de símbolos
- Relatório de erros
- Estatísticas da análise

### 2. Arquivo de Relatório (`relatorio_analise.txt`)

- Análise completa com timestamps
- Estatísticas detalhadas
- Lista completa de tokens
- Tabela de símbolos formatada
- Descrição detalhada de todos os erros

### Exemplo de Saída

```
=== COMPILADOR LÉXICO PASCAL ===
Arquivo: Programa_Fonte.txt

=== TOKENS ENCONTRADOS ===
#    Tipo                 Valor           Linha    Coluna
------------------------------------------------------------
1    PALAVRA_RESERVADA_ERRO 'progra'       1        1
2    IDENTIFICADOR        'Soma'          1        8
3    PONTO_VIRGULA        ';'             1        12
...

=== TABELA DE SÍMBOLOS ===
Nome            Tipo       Linha    Declarado
------------------------------------------------
Soma            indefinido 1        Não
x               integer    4        Sim
...

=== RELATÓRIO DE ERROS ===
1. ERRO (linha 1, coluna 1): Palavra reservada 'progra' escrita incorretamente. Deveria ser 'program'
2. ERRO (linha 5, coluna 11): Palavra reservada 'intege' escrita incorretamente. Deveria ser 'integer'
...
```

## 🧪 Exemplos de Teste

### Programa com Erros (`Programa_Fonte.txt`)

```pascal
progra Soma;        // Erro: 'progra' → 'program'

var
  x,y,z: intege;    // Erro: 'intege' → 'integer'
begi                // Erro: 'begi' → 'begin'
  y=1;              // Erro: usar ':=' em vez de '='
  z=1;
  i=0;              // Erro: 'i' não declarado
  x:=y+z+i;
  writeln (x);
end.
```

### Programa Correto Simples (`Programa_Simples.txt`)

```pascal
program SomaSimples;

var
  a, b, soma: integer;

begin
  a := 10;
  b := 20;
  soma := a + b;
  writeln('A soma de ', a, ' e ', b, ' eh: ', soma);
end.
```

## 🎓 Propósito Educacional

Este projeto foi desenvolvido para demonstrar:

1. **Análise Léxica**: Como quebrar código fonte em tokens
2. **Detecção de Erros**: Identificação de problemas sintáticos e semânticos
3. **Gerenciamento de Símbolos**: Organização de identificadores e seus tipos
4. **Arquitetura Modular**: Separação clara de responsabilidades
5. **Boas Práticas**: Código limpo, documentado e testável

## 📝 Requisitos Atendidos

- ✅ Análise léxica completa
- ✅ Identificação de palavras-chave
- ✅ Reconhecimento de operadores de atribuição
- ✅ Gerenciamento de identificadores e tipagens
- ✅ Tabela de símbolos funcional
- ✅ Detecção e relatório de erros
- ✅ Análise do arquivo `Programa_Fonte.txt`
- ✅ Relatórios detalhados

## 🔧 Desenvolvimento

Para modificar ou estender o compilador:

1. **Adicionar novos tokens**: Edite `TipoToken.java` e `AnalisadorLexico.java`
2. **Novos tipos de erro**: Implemente em `AnalisadorLexico.analisarSemantica()`
3. **Palavras reservadas**: Adicione no mapa `PALAVRAS_RESERVADAS`
4. **Relatórios**: Customize os métodos de saída em `CompiladorMain.java`

---

**Desenvolvido para**: Disciplina de Compiladores - UFMT  
**Linguagem**: Java  
**Ano**: 2025

## Descrição Original do Trabalho

Fazer um compilador utilizando apenas as técnicas de análise léxica e
identificar as palavras chaves, sinais de atribuição, os id's e a tipagem dos id's
da linguagem de programação Pascal. Os dados devem estar armazenados em
uma tabela de símbolos. Verificar se há erros no programa fonte e identificando
uma por uma os erros até que não haja mais erros no programa fonte. Todos os
erros devem ser relatados e colocados no relatório. Por exemplo, id não
identificado, palavra reservada não encontrado, entre outros. O arquivo para
resolução do trabalho é o Programa_Fonte.txt.
