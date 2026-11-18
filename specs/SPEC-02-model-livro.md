# SPEC-02 — Model `Livro`

- **Camada:** M
- **Depende de:** SPEC-01
- **Requisitos:** Seção 3 do enunciado, RT08 (Serializable)

## Objetivo
Criar a entidade `Livro`, que circula entre as camadas e entre as telas (via Intent).

## Requisitos
| Atributo | Tipo Java | Regra |
|---|---|---|
| `id` | `Long` | `null` enquanto o livro não foi salvo |
| `titulo` | `String` | obrigatório |
| `autor` | `String` | obrigatório |
| `ano` | `int` | 1450 até o ano atual (validado na SPEC-10) |
| `lido` | `boolean` | `true` = lido |

1. A classe `implements Serializable`.
2. Dois construtores: um **vazio** e um **completo** sem `id` (`titulo, autor, ano, lido`).
3. Getters e setters de todos os atributos. O getter do boolean se chama **`isLido()`**.

## Trechos-chave
```java
package com.example.bibliotecapessoal.model;

import java.io.Serializable;

public class Livro implements Serializable {
    private Long id;          // Long (objeto) para aceitar null antes de salvar
    private String titulo;
    private String autor;
    private int ano;
    private boolean lido;
    // construtores, getters e setters...
}
```

## Arquivos
- Criar: `model/Livro.java`

## Fora de escopo
- Validação dos campos (fica na Activity, SPEC-10).
- Campo `nota` (só existe no banco v2, SPEC-03).

## Critérios de aceite
- [ ] Os 5 atributos são `private`, com os tipos exatos da tabela.
- [ ] `implements Serializable`.
- [ ] Existem o construtor vazio e o completo.
- [ ] O getter do boolean é `isLido()`, não `getLido()`.
- [ ] O projeto compila.
