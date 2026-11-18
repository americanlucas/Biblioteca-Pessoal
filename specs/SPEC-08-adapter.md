# SPEC-08 — `AdapterLivro` + layout do item

- **Camada:** T
- **Depende de:** SPEC-02, SPEC-05
- **Requisitos:** RF02 (indicador visual), RT04

## Objetivo
Transformar a `List<Livro>` em itens visuais, com um indicador claro de **Lido** / **Não lido**.

## Requisitos
### `adapter_livro.xml`
- `ConstraintLayout` com `layout_height="wrap_content"`.
- `txtTitulo` (destaque), `txtAutorAno` (cor `cor_texto_secundario`) e `txtStatus` (a tag, alinhada à direita).
- Um divisor na base (`View` de 1dp com `@color/cor_divisor`).

### `AdapterLivro extends RecyclerView.Adapter<AdapterLivro.MyViewHolder>`
1. Recebe `List<Livro>` e `Context` no construtor.
2. ViewHolder interno com as 3 TextViews.
3. `onBindViewHolder`:
   - título;
   - autor e ano com `getString(R.string.item_autor_ano, autor, ano)`;
   - tag com o texto `tag_lido` / `tag_nao_lido` e a cor `cor_lido` / `cor_nao_lido`.
4. Métodos públicos (RT04):
   - `atualizarLista(List<Livro> novaLista)`: substitui a lista interna e chama `notifyDataSetChanged()`.
   - `getLivro(int position)`: devolve o livro da posição.

## Trechos-chave
```java
Livro livro = lista.get(position);
holder.txtTitulo.setText(livro.getTitulo());
holder.txtAutorAno.setText(context.getString(R.string.item_autor_ano, livro.getAutor(), livro.getAno()));
holder.txtStatus.setText(livro.isLido() ? R.string.tag_lido : R.string.tag_nao_lido);
holder.txtStatus.setTextColor(ContextCompat.getColor(context,
        livro.isLido() ? R.color.cor_lido : R.color.cor_nao_lido));
```

## Arquivos
- Criar: `res/layout/adapter_livro.xml`, `adapter/AdapterLivro.java`

## Fora de escopo
- Tratamento de cliques (fica na SPEC-09, via `RecyclerItemClickListener`).

## Critérios de aceite
- [ ] `atualizarLista` e `getLivro` são públicos, com essas assinaturas.
- [ ] O texto e a cor da tag mudam conforme `isLido()`.
- [ ] Nenhum texto ou cor escritos direto no adapter ou no item.
- [ ] O layout do item usa `wrap_content` na altura. Com `match_parent`, cada item ocuparia a tela inteira.
- [ ] O projeto compila.

## Armadilhas
- `setText(int)` recebe o **id do recurso**. Passar `livro.getAno()` direto para `setText` faz o Android procurar um recurso com esse número e dá crash (`Resources$NotFoundException`).
- 🔗 Adapter é o padrão de projeto *Adapter* (GoF): converte uma interface (lista de dados) em outra (views). É a mesma ideia de um "transformer" num pipeline de dados.
