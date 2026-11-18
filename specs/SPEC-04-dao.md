# SPEC-04 — `ILivroDAO` + `LivroDAO`

- **Camada:** P
- **Depende de:** SPEC-03
- **Requisitos:** RT01, RT02, RF02 (ordenação), RF05
- **Peso na nota:** 2,0 pts (o maior junto com a SPEC-09/10)

## Objetivo
Isolar todo o acesso ao banco atrás de um contrato. As Activities nunca usam `SQLiteDatabase` direto.

## Requisitos
### Interface `ILivroDAO` (exatamente estes 5 métodos)
```java
boolean salvar(Livro livro);
boolean atualizar(Livro livro);
boolean excluir(Livro livro);
List<Livro> listarTodos();
int contar(Boolean lido);   // null = total | true = lidos | false = não lidos
```

### Classe `LivroDAO implements ILivroDAO`
1. Construtor `LivroDAO(Context context)`: cria o `DbHelper` e guarda `escreve = getWritableDatabase()` e `le = getReadableDatabase()`.
2. **Conversão boolean ↔ INTEGER** explícita:
   - gravar: `livro.isLido() ? 1 : 0`
   - ler: `cursor.getInt(idx) == 1`
3. **Validação de retorno (RT02):**
   - `salvar`: `insert(...) != -1`
   - `atualizar`: `update(...) > 0`, com `WHERE id = ?`
   - `excluir`: `delete(...) > 0`, com `WHERE id = ?`
4. `salvar` e `atualizar` não gravam `id` no `ContentValues`.
5. `listarTodos`: `SELECT * ... ORDER BY titulo ASC` e montar a `List<Livro>` com o `id` preenchido.
6. `contar`: só `SELECT COUNT(*)`. Se `lido == null`, sem `WHERE`; senão, `WHERE lido = ?`.
7. Todo `Cursor` é fechado (`cursor.close()`).
8. Erros de SQL são capturados com `try/catch` e o método devolve `false` (não derruba o app).

## Trechos-chave
```java
@Override
public int contar(Boolean lido) {
    String sql = "SELECT COUNT(*) FROM " + DbHelper.TABELA_LIVROS;
    String[] args = null;
    if (lido != null) {                       // null = total geral
        sql += " WHERE " + DbHelper.COLUNA_LIDO + " = ?";
        args = new String[]{ lido ? "1" : "0" };
    }
    Cursor cursor = le.rawQuery(sql, args);
    int total = cursor.moveToFirst() ? cursor.getInt(0) : 0;
    cursor.close();
    return total;
}
```
```java
// Leitura do boolean a partir do INTEGER
livro.setLido(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUNA_LIDO)) == 1);
```

## Arquivos
- Criar: `persistencia/ILivroDAO.java`, `persistencia/LivroDAO.java`

## Fora de escopo
- Toasts e mensagens (ficam nas Activities).
- Coluna `nota`.

## Critérios de aceite
- [ ] A interface tem **exatamente** os 5 métodos, com as assinaturas acima.
- [ ] `insert` é comparado com `-1`; `update` e `delete` com `> 0`.
- [ ] `contar` usa `COUNT(*)`, sem nenhum laço contando itens.
- [ ] `listarTodos` ordena por `titulo ASC`.
- [ ] As conversões boolean ↔ int estão explícitas na gravação e na leitura.
- [ ] Nenhum `Cursor` fica aberto.
- [ ] Todas as SQLs usam as constantes do `DbHelper`.
- [ ] O projeto compila.

## Armadilhas
- `contar(Boolean)` com B maiúsculo: o `boolean` primitivo não aceita `null`.
- `ORDER BY titulo` diferencia maiúsculas de minúsculas ("a" vem depois de "Z"). Opcional: `COLLATE NOCASE`.
- `update` e `delete` precisam de `livro.getId().toString()` em `whereArgs`. Se o `id` for `null`, dá NullPointerException.
