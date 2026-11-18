# SPEC-09 — `ListaLivrosActivity` (listar, editar e excluir)

- **Camada:** T
- **Depende de:** SPEC-04, SPEC-07, SPEC-08
- **Requisitos:** RF02, RF03 (abrir a edição), RF04, RF06, RT04, RT05, RT08
- **Peso na nota:** parte de 1,5 pt (listagem) + parte de 2,0 pts (editar/excluir)

## Objetivo
Exibir os livros ordenados, abrir a edição com clique simples e excluir com clique longo e confirmação.

## Requisitos
1. Copiar o `RecyclerItemClickListener` do **Anexo B** para a raiz do pacote.
2. `onCreate`:
   - `txtCabecalho` recebe `@string/titulo_lista`;
   - `LivroDAO dao = new LivroDAO(this)`;
   - `adapter = new AdapterLivro(new ArrayList<>(), this)`;
   - `recyclerLivros.setLayoutManager(new LinearLayoutManager(this))` e `setAdapter(adapter)`;
   - `addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerLivros, ...))`;
   - `NavegacaoMenu.configurar(this, R.id.nav_livros)` (já existe desde a SPEC-07).
3. `onResume()` → `carregarLivros()` → `adapter.atualizarLista(dao.listarTodos())` (RT05).
4. **Clique simples:** `adapter.getLivro(position)` → `Intent` para `CadastroLivroActivity` com `putExtra("livroSelecionado", livro)` → `NavegacaoMenu.irPara(this, intent)`.
5. **Clique longo:** `AlertDialog` de confirmação. Se confirmar: `dao.excluir(livro)`, Toast de sucesso ou erro e `carregarLivros()`, **sem recriar a Activity**.
6. Ignorar a posição `RecyclerView.NO_POSITION` (-1).

## Trechos-chave
```java
private void confirmarExclusao(Livro livro) {
    new AlertDialog.Builder(this)
        .setTitle(R.string.dialog_excluir_titulo)
        .setMessage(getString(R.string.dialog_excluir_msg, livro.getTitulo()))
        .setPositiveButton(R.string.dialog_excluir, (dialog, which) -> {
            boolean ok = dao.excluir(livro);
            Toast.makeText(this, ok ? R.string.msg_excluido_sucesso : R.string.msg_excluido_erro,
                    Toast.LENGTH_SHORT).show();
            if (ok) carregarLivros();          // atualiza a lista sem recriar a tela
        })
        .setNegativeButton(R.string.dialog_cancelar, null)
        .show();
}
```

## Arquivos
- Criar: `RecyclerItemClickListener.java`
- Alterar: `ListaLivrosActivity.java`

## Fora de escopo
- Preencher o formulário de edição (fica na SPEC-10).

## Critérios de aceite
- [ ] Os dados são carregados no `onResume()`, não só no `onCreate`.
- [ ] O livro clicado vem de `adapter.getLivro(position)`, nunca de uma lista guardada na Activity.
- [ ] O clique simples abre o Cadastro **via `irPara`**, com o extra `"livroSelecionado"`.
- [ ] O clique longo mostra o diálogo; Cancelar não apaga nada; Excluir apaga, mostra o Toast e a lista atualiza na hora.
- [ ] O `AlertDialog` vem de `androidx.appcompat.app.AlertDialog`.
- [ ] Nenhuma variável `static` guarda livro ou lista.

## Armadilhas
- Usar o `RecyclerItemClickListener` do projeto Gestão de Contatos obriga a implementar `onItemClick(AdapterView...)`. Use a versão do Anexo B.
- Dentro da classe anônima do `RecyclerItemClickListener`, `this` aponta para o listener, não para a Activity. Use `ListaLivrosActivity.this` em `new Intent(...)`, `irPara(...)` e no `AlertDialog.Builder(...)`.
- Guardar `List<Livro> lista` na Activity e usar `lista.get(position)` contraria o RT04 e perde ponto.
