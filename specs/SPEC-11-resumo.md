# SPEC-11 — `ResumoActivity` (estatísticas via SQL)

- **Camada:** T
- **Depende de:** SPEC-04, SPEC-07
- **Requisitos:** RF05, RT07

## Objetivo
Mostrar o total de livros, os lidos e os não lidos, contados **pelo banco**.

## Requisitos
1. `txtCabecalho` recebe `@string/titulo_resumo`.
2. No `onCreate`, criar o `LivroDAO` e preencher:
   - `txtTotal` ← `getString(R.string.resumo_total, dao.contar(null))`
   - `txtLidos` ← `getString(R.string.resumo_lidos, dao.contar(true))`
   - `txtNaoLidos` ← `getString(R.string.resumo_nao_lidos, dao.contar(false))`
3. Proibido chamar `listarTodos()` nesta tela.

## Arquivos
- Alterar: `ResumoActivity.java`

## Critérios de aceite
- [ ] Os 3 números batem com o banco (lidos + não lidos = total).
- [ ] Só `contar(...)` é usado; nada de `listarTodos()` nem de laço.
- [ ] Os textos vêm de `strings.xml`, com placeholder.
- [ ] Os números atualizam depois de cadastrar ou excluir (cada navegação recria a tela, então o `onCreate` basta).

## Armadilhas
- 🔗 `COUNT(*)` no banco em vez de `len(lista)` na memória: a agregação acontece onde o dado está. É a mesma lógica de preferir `GROUP BY` no SQL a trazer a tabela inteira para o pandas.
