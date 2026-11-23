# Specs — Biblioteca Pessoal

Mapa: **M → P → R → T → T** (Modelo → Persistência → Recursos → Telas → Testes)

| # | Spec | Camada | Depende de | Requisitos | Onde fazer |
|---|---|---|---|---|---|
| 01 | [Projeto](specs/SPEC-01-projeto.md) | M | — | — | **Android Studio** |
| 02 | [Model Livro](specs/SPEC-02-model-livro.md) | M | 01 | RT03 (tipos) | Claude Code |
| 03 | [DbHelper](specs/SPEC-03-dbhelper.md) | P | 02 | RT03 | Claude Code |
| 04 | [DAO](specs/SPEC-04-dao.md) | P | 03 | RT01, RT02, RF05 | Claude Code |
| 05 | [Recursos](specs/SPEC-05-recursos.md) | R | 01 | RT07 | Claude Code + **Android Studio** (ícones) |
| 06 | [Layouts e esqueleto das telas](specs/SPEC-06-layouts-esqueleto.md) | T | 05 | RT06, RT07 | Claude Code |
| 07 | [NavegacaoMenu](specs/SPEC-07-navegacao-menu.md) | T | 06 | RT09 | Claude Code |
| 08 | [AdapterLivro](specs/SPEC-08-adapter.md) | T | 02, 05 | RF02, RT04 | Claude Code |
| 09 | [ListaLivrosActivity](specs/SPEC-09-lista-livros.md) | T | 04, 07, 08 | RF02, RF04, RF06, RT05, RT08 | Claude Code |
| 10 | [CadastroLivroActivity](specs/SPEC-10-cadastro-livro.md) | T | 04, 07 | RF01, RF03, RF06, RT08 | Claude Code |
| 11 | [ResumoActivity](specs/SPEC-11-resumo.md) | T | 04, 07 | RF05 | Claude Code |
| 12 | [Testes e entrega](specs/SPEC-12-testes-entrega.md) | T | todas | todos | Emulador |

> **Ordem diferente do enunciado:** os layouts e o esqueleto das Activities (06) vêm **antes** da NavegacaoMenu (07). A `NavegacaoMenu` usa `R.id.bottomNav` e as classes das 3 Activities; sem elas, o projeto não compila entre um passo e outro.

## Como usar no Claude Code
1. As regras do projeto ficam em `CLAUDE.md` na raiz. O Claude Code carrega esse arquivo sozinho em toda sessão aberta nesta pasta.
2. Abra o Claude Code na raiz do projeto Android (`claude`). Para conferir se as regras foram carregadas, use `/memory`.
3. Para cada spec, use um prompt como:

```
Implemente @specs/SPEC-0X-....md seguindo as regras do projeto.
Mostre o plano antes de editar. Ao final, liste os arquivos alterados
e marque os critérios de aceite.
```

4. Revise o diff, rode o app (Android Studio ou Gradle) e só então siga para a próxima spec.

## Mapa de pontuação
| Critério | Pontos | Specs |
|---|---|---|
| Modelo + DbHelper | 1,0 | 02, 03 |
| DAO + COUNT | 2,0 | 04 |
| Validação (setError) | 1,0 | 10 |
| Listagem + indicador | 1,5 | 06, 08, 09 |
| Editar / excluir | 2,0 | 09, 10 |
| Navegação inferior | 1,5 | 07 |
| Organização + recursos | 1,0 | 05 + todas |
