# SPEC-01 — Criação e configuração do projeto

- **Camada:** M (base)
- **Depende de:** —
- **Onde:** Android Studio (o template e o Refactor só existem na IDE, não no Claude Code)

## Objetivo
Criar o projeto Android vazio, já com a Activity LAUNCHER no nome certo e a estrutura de pacotes pronta.-

## Requisitos
1. Novo projeto com o template **Empty Views Activity**.
2. Nome: `Biblioteca Pessoal` · Pacote: `com.example.bibliotecapessoal` · Linguagem: **Java**.
3. Renomear `MainActivity` → `ListaLivrosActivity` por **Refactor › Rename** (atualiza o Manifest e o layout junto).
4. Renomear o layout `activity_main.xml` → `activity_lista_livros.xml` (também por Refactor › Rename).
5. Conferir no `app/build.gradle(.kts)` a dependência `com.google.android.material:material`. Se alterar algo, clicar em **Sync Now**.
6. Criar os pacotes vazios `model`, `persistencia` e `adapter` em `java/com/example/bibliotecapessoal`.

## Arquivos
- Gerados pelo template e alterados pelo Refactor: `ListaLivrosActivity.java`, `activity_lista_livros.xml`, `AndroidManifest.xml`.

## Fora de escopo
- Qualquer lógica, layout ou recurso.

## Critérios de aceite
- [ ] O `AndroidManifest.xml` declara `.ListaLivrosActivity` com `MAIN` / `LAUNCHER`.
- [ ] Não existe mais nenhuma referência a `MainActivity` nem a `activity_main`.
- [ ] O tema do app é Material3 / MaterialComponents (necessário para o `BottomNavigationView`).
- [ ] Os pacotes `model`, `persistencia` e `adapter` existem.
- [ ] O app compila e abre uma tela vazia no emulador.

## Armadilhas
- Renomear o arquivo na mão (sem Refactor) deixa o Manifest apontando para uma classe que não existe, e o app fecha ao abrir.
- O template usa `Theme.Material3.DayNight.NoActionBar`, ou seja, sem barra de título. O cabeçalho de cada tela será um `TextView` (SPEC-06).
