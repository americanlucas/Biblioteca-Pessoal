# SPEC-03 — `DbHelper`

- **Camada:** P
- **Depende de:** SPEC-02
- **Requisitos:** RT03

## Objetivo
Criar e versionar o banco SQLite, com os nomes centralizados em constantes e a migração para a v2 já escrita.

## Requisitos
1. `DbHelper extends SQLiteOpenHelper`, com um construtor que recebe `Context`.
2. Constantes `public static final` (nomes exatos):

| Constante | Valor |
|---|---|
| `NOME_DB` | `"biblioteca.db"` |
| `VERSAO` | `1` |
| `TABELA_LIVROS` | `"livros"` |
| `COLUNA_ID` | `"id"` |
| `COLUNA_TITULO` | `"titulo"` |
| `COLUNA_AUTOR` | `"autor"` |
| `COLUNA_ANO` | `"ano"` |
| `COLUNA_LIDO` | `"lido"` |
| `COLUNA_NOTA` | `"nota"` (usada só na migração) |

3. O `onCreate` cria a tabela da **v1** (sem `nota`):
   - `id INTEGER PRIMARY KEY AUTOINCREMENT`
   - `titulo TEXT NOT NULL`
   - `autor TEXT NOT NULL`
   - `ano INTEGER`
   - `lido INTEGER DEFAULT 0`
4. `onUpgrade`: quando `oldVersion < 2`, rodar `ALTER TABLE ... ADD COLUMN nota INTEGER`. **Nunca** usar `DROP TABLE`.
5. Toda SQL é montada com as constantes, sem nomes de tabela ou coluna escritos direto na string.

## Trechos-chave
```java
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Migração incremental: preserva os dados existentes
    if (oldVersion < 2) {
        db.execSQL("ALTER TABLE " + TABELA_LIVROS
                + " ADD COLUMN " + COLUNA_NOTA + " INTEGER");
    }
}
```

## Arquivos
- Criar: `persistencia/DbHelper.java`

## Fora de escopo
- Subir `VERSAO` para 2 (a entrega é na v1).
- Usar `nota` em qualquer outro lugar.

## Critérios de aceite
- [ ] As 9 constantes existem, com os nomes e valores da tabela.
- [ ] `VERSAO == 1`.
- [ ] O `CREATE TABLE` tem as 5 colunas, os tipos e as restrições pedidas.
- [ ] O `onUpgrade` tem o `if (oldVersion < 2)` com o `ALTER TABLE` e nenhum `DROP`.
- [ ] O projeto compila.

## Armadilhas
- O `onCreate` roda **uma única vez** por instalação. Se mudar o `CREATE`, desinstale o app do emulador antes de testar.
- O `onUpgrade` só roda quando `VERSAO` aumenta, então na v1 ele nunca é chamado. Mesmo assim, a nota depende de ele estar correto.
- Ponto de reflexão (fora da entrega): numa instalação nova já na v2, só o `onCreate` roda. Por isso, ao subir a versão, o `CREATE` também precisaria incluir `nota`.
