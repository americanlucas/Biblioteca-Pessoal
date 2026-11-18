# SPEC-05 — Recursos: cores, textos, ícones e menu

- **Camada:** R
- **Depende de:** SPEC-01
- **Requisitos:** RT07
- **Onde:** Antigravity (XML) + **Android Studio** (Vector Asset)

## Objetivo
Centralizar todo texto e toda cor antes de qualquer tela existir. Assim nenhuma spec seguinte precisa escrever texto direto no código.

## Requisitos
### `res/values/colors.xml` (valores sugeridos, podem mudar)
| Nome | Uso | Sugestão |
|---|---|---|
| `cor_primaria` | cabeçalho, botão | `#3F51B5` |
| `cor_texto_cabecalho` | texto do cabeçalho | `#FFFFFF` |
| `cor_texto_secundario` | linha autor • ano | `#757575` |
| `cor_lido` | tag "Lido" | `#2E7D32` |
| `cor_nao_lido` | tag "Não lido" | `#E65100` |
| `cor_divisor` | separador dos itens | `#DDDDDD` |

### `res/values/strings.xml` (nomes exatos, usados nas próximas specs)
| Nome | Valor |
|---|---|
| `app_name` | Biblioteca Pessoal |
| `titulo_lista` | Meus Livros |
| `titulo_cadastro` | Cadastro de Livro |
| `titulo_resumo` | Resumo da Biblioteca |
| `menu_livros` / `menu_novo` / `menu_resumo` | Livros / Novo / Resumo |
| `hint_titulo` / `hint_autor` / `hint_ano` | Título do livro / Autor / Ano de publicação |
| `check_lido` | Já li este livro |
| `btn_salvar` / `btn_atualizar` | Salvar / Atualizar |
| `tag_lido` / `tag_nao_lido` | Lido / Não lido |
| `item_autor_ano` | `%1$s • %2$d` |
| `erro_titulo_obrigatorio` | Informe o título |
| `erro_autor_obrigatorio` | Informe o autor |
| `erro_ano_obrigatorio` | Informe o ano |
| `erro_ano_invalido` | O ano deve estar entre 1450 e %1$d |
| `msg_salvo_sucesso` / `msg_salvo_erro` | Livro salvo! / Erro ao salvar o livro |
| `msg_atualizado_sucesso` / `msg_atualizado_erro` | Livro atualizado! / Erro ao atualizar o livro |
| `msg_excluido_sucesso` / `msg_excluido_erro` | Livro excluído! / Erro ao excluir o livro |
| `dialog_excluir_titulo` | Excluir livro |
| `dialog_excluir_msg` | Deseja excluir \"%1$s\"? |
| `dialog_excluir` / `dialog_cancelar` | Excluir / Cancelar |
| `resumo_total` | Total de livros: %1$d |
| `resumo_lidos` | Livros lidos: %1$d |
| `resumo_nao_lidos` | Livros não lidos: %1$d |

### Ícones (Android Studio › `res/drawable` › New › Vector Asset)
- `ic_livros` (ex.: *menu_book*), `ic_novo` (ex.: *add*), `ic_resumo` (ex.: *bar_chart*)

### `res/menu/menu_bottom.xml`
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/nav_livros" android:icon="@drawable/ic_livros" android:title="@string/menu_livros" />
    <item android:id="@+id/nav_novo"   android:icon="@drawable/ic_novo"   android:title="@string/menu_novo" />
    <item android:id="@+id/nav_resumo" android:icon="@drawable/ic_resumo" android:title="@string/menu_resumo" />
</menu>
```

## Arquivos
- Alterar: `res/values/colors.xml`, `res/values/strings.xml`
- Criar: `res/drawable/ic_livros.xml`, `ic_novo.xml`, `ic_resumo.xml`, `res/menu/menu_bottom.xml`

## Critérios de aceite
- [ ] Todas as strings e cores da tabela existem, com os nomes exatos.
- [ ] As strings com valor usam marcador posicional (`%1$s`, `%1$d`), não concatenação.
- [ ] Aspas e apóstrofos estão escapados (`\"`, `\'`).
- [ ] O `menu_bottom.xml` tem 3 itens com os ids `nav_livros`, `nav_novo` e `nav_resumo`, e os títulos vêm de `@string`.
- [ ] Os 3 ícones existem em `res/drawable`.
- [ ] O projeto compila.

## Armadilhas
- Um apóstrofo sem escape no `strings.xml` quebra o build com uma mensagem pouco clara.
- 🔗 Placeholder posicional (`%1$d`) é o mesmo princípio de `f"{x}"` / `str.format` em Python e do i18n em geral: o texto fica no recurso, o código só injeta o valor.
