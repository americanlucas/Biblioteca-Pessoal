# SPEC-06 — Layouts das 3 telas + esqueleto das Activities

- **Camada:** T
- **Depende de:** SPEC-05
- **Requisitos:** RT06, RT07

## Objetivo
Criar as 3 telas visuais e as classes mínimas das Activities para o projeto compilar antes da `NavegacaoMenu` (SPEC-07).

## Requisitos
### Comum aos 3 layouts
- Raiz: `ConstraintLayout` com `android:id="@+id/main"` (usado pelo trecho de insets).
- Cabeçalho: `TextView` `txtCabecalho` no topo, com fundo `@color/cor_primaria` e texto `@color/cor_texto_cabecalho`.
- **Último elemento:** o `BottomNavigationView` com `id/bottomNav`, exatamente como no Anexo A do enunciado.

### `activity_lista_livros.xml`
- `RecyclerView` `recyclerLivros` com **width e height `0dp`**, preso entre o `txtCabecalho` e o `bottomNav` (RT06).

```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerLivros"
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintTop_toBottomOf="@id/txtCabecalho"
    app:layout_constraintBottom_toTopOf="@id/bottomNav"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent" />
```

### `activity_cadastro_livro.xml`
- `edtTitulo`, `edtAutor` e `edtAno` (`inputType="number"`, `maxLength="4"`), com hints vindos de `@string/hint_*`.
- `chkLido` (CheckBox) com o texto `@string/check_lido`.
- `btnSalvar` com o texto `@string/btn_salvar`.

### `activity_resumo.xml`
- `txtTotal`, `txtLidos` e `txtNaoLidos` (podem começar sem texto; são preenchidos na SPEC-11).

### Esqueleto das Activities
- Criar `CadastroLivroActivity` e `ResumoActivity` com apenas `setContentView(...)` e o trecho de insets.
- Registrar as duas no `AndroidManifest.xml` com `android:exported="false"`.
- Nas **3** Activities, ajustar os insets (bottom = 0):

```java
ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
    v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // bottomNav já trata a margem
    return insets;
});
```

## Arquivos
- Alterar: `activity_lista_livros.xml`, `ListaLivrosActivity.java` (só os insets), `AndroidManifest.xml`
- Criar: `activity_cadastro_livro.xml`, `activity_resumo.xml`, `CadastroLivroActivity.java`, `ResumoActivity.java`

## Fora de escopo
- Lógica de navegação, banco, adapter e validação.
- `adapter_livro.xml` (fica na SPEC-08).

## Critérios de aceite
- [ ] Os 3 layouts têm `@+id/main`, `txtCabecalho` e o `bottomNav` como último filho.
- [ ] O `RecyclerView` tem `layout_height="0dp"` e está preso ao cabeçalho e ao `bottomNav`.
- [ ] Nenhum `android:text`, `android:hint` ou cor escritos direto (o lint "Hardcoded string" não aparece).
- [ ] As 3 Activities aplicam os insets com bottom `0`.
- [ ] O Manifest tem as 3 Activities, e só a Lista é LAUNCHER.
- [ ] O app compila e abre a Lista com o menu inferior visível. O menu ainda não navega, e isso é o esperado.
