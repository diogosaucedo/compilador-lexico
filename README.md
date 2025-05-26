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

- **Palavras Reservadas**: `program`, `var`, `begin`, `end`, `integer`, `writeln`, `if`, `then`, `else`, `char`, `write`, `readln`, `div`
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
- Uso de operadores inválidos (como `=` em vez de `:=` para atribuição)

## 📂 Estrutura do Projeto

```
compilador-lexico/
├── AnalisadorLexico.java    # Analisador léxico principal
├── CompiladorMain.java      # Classe principal
├── Token.java               # Representação de tokens
├── TipoToken.java           # Tipos de tokens
├── TabelaSimbolos.java      # Tabela de símbolos
├── Simbolo.java             # Representação de símbolos
├── Programa_Fonte.txt       # Programa com erros (objetivo principal)
├── Programa_Simples.txt     # Exemplo simples correto
├── Programa_Correto.txt     # Exemplo complexo correto
├── compilar_e_executar.bat  # Script de compilação e execução
├── testar_todos.bat         # Script para testar todos os exemplos
└── README.md                # Documentação do projeto
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
```

## 📝 Requisitos Atendidos

- ✅ Análise léxica completa
- ✅ Identificação de palavras-chave
- ✅ Reconhecimento de operadores de atribuição
- ✅ Gerenciamento de identificadores e tipagens
- ✅ Tabela de símbolos funcional
- ✅ Detecção e relatório de erros
- ✅ Análise do arquivo `Programa_Fonte.txt`
- ✅ Relatórios detalhados

---

**Desenvolvido para**: Disciplina de Compiladores - UFMT  
**Linguagem**: Java  
**Ano**: 2025
