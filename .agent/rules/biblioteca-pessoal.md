# Regras do Projeto — Biblioteca Pessoal (Android · Java + SQLite)

Regras que valem para **todas** as specs em `specs/`. Em caso de conflito, estas regras vencem.

## Stack
- Linguagem **Java** (sem Kotlin).
- Persistência com **SQLite nativo** (`SQLiteOpenHelper`). Proibido: Room, ViewModel, LiveData, Fragments, Navigation Component, bibliotecas extras.
- UI com Views XML: `ConstraintLayout`, `RecyclerView`, `BottomNavigationView` (Material).
- Pacote raiz: `com.example.bibliotecapessoal`.

## Estrutura obrigatória (não criar nem mover arquivos fora dela)
```
com.example.bibliotecapessoal
├── ListaLivrosActivity.java           (LAUNCHER)
├── CadastroLivroActivity.java
├── ResumoActivity.java
├── NavegacaoMenu.java
├── RecyclerItemClickListener.java
├── model/Livro.java
├── persistencia/DbHelper.java | ILivroDAO.java | LivroDAO.java
└── adapter/AdapterLivro.java

res/menu/menu_bottom.xml
res/values/strings.xml | colors.xml
res/layout/activity_lista_livros.xml | activity_cadastro_livro.xml | activity_resumo.xml | adapter_livro.xml
```

## Regras invioláveis
1. **Recursos externalizados (RT07):** nenhum texto visível nem cor escritos direto em XML ou Java. Layouts usam `@string/...` e `@color/...`; o Java usa `getString(R.string...)` e `ContextCompat.getColor(...)`. Isso vale também para Toast, `setError`, AlertDialog, o texto "Atualizar" e os títulos do menu.
2. **Sem estado estático compartilhado (RT08):** proibido `static` para guardar dados entre telas. O `Livro` vai entre telas só por Intent (`putExtra` / `getSerializableExtra`), com a chave `"livroSelecionado"`.
   - Permitido: constantes `public static final` (DbHelper) e os métodos estáticos utilitários de `NavegacaoMenu`.
3. **Navegação centralizada (RT09):** toda troca de tela usa `NavegacaoMenu.irPara(...)`. Nunca chamar `startActivity` direto nas Activities. Nunca chamar `finish()` sozinho para "voltar".
4. **Contagens só via SQL (RF05):** usar `SELECT COUNT(*)`. Proibido contar com laço sobre a lista.
5. **Feedback (RF06):** toda inclusão, edição ou exclusão mostra um Toast de sucesso **ou** de falha.
6. **Insets:** em todas as Activities, o padding inferior do `setOnApplyWindowInsetsListener` é `0`.
7. **Nomes exatos:** classes, métodos, ids e chaves definidos nas specs não podem ser renomeados.

## Convenções
- Comentários de código em **português**, curtos (1 linha explicando o "porquê").
- Ids de view em camelCase com prefixo do tipo (`edtTitulo`, `txtStatus`, `btnSalvar`, `chkLido`).
- Nomes de strings e cores em snake_case, como estão definidos na SPEC-05.

## Fluxo SDD (como o agente deve trabalhar)
1. Implementar **uma spec por vez**, só na ordem do `specs/README.md`.
2. Alterar **apenas** os arquivos listados em "Arquivos" da spec atual. Se outro arquivo precisar mudar, parar e perguntar.
3. Ao terminar, responder com: (a) lista de arquivos criados ou alterados; (b) o checklist de **Critérios de aceite** marcado; (c) qualquer desvio da spec e o motivo.
4. Não começar a próxima spec sem aprovação do usuário.
