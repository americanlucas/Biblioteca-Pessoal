# SPEC-10 — `CadastroLivroActivity` (inclusão e edição)

- **Camada:** T
- **Depende de:** SPEC-04, SPEC-07
- **Requisitos:** RF01, RF03, RF06, RT07, RT08
- **Peso na nota:** 1,0 pt (validação) + parte de 2,0 pts (edição)

## Objetivo
Um único formulário que **inclui** um livro novo ou **edita** um livro recebido pela Intent, com validação por campo.

## Requisitos
### Modo (no `onCreate`)
1. `txtCabecalho` recebe `@string/titulo_cadastro`.
2. `livroEdicao = (Livro) getIntent().getSerializableExtra("livroSelecionado");`
3. Se `livroEdicao != null` (**modo edição**):
   - preencher `edtTitulo`, `edtAutor`, `edtAno` (com `String.valueOf(ano)`) e `chkLido.setChecked(...)`;
   - `btnSalvar.setText(R.string.btn_atualizar)`.

### Validação (ao clicar no botão, RF01)
1. Ler os textos com `.trim()`.
2. Validar **todos** os campos antes de parar, para mostrar todos os erros de uma vez:
   - título vazio → `edtTitulo.setError(getString(R.string.erro_titulo_obrigatorio))`;
   - autor vazio → `erro_autor_obrigatorio`;
   - ano vazio → `erro_ano_obrigatorio` (conferir **antes** do `parseInt`);
   - ano fora de `1450..anoAtual` → `getString(R.string.erro_ano_invalido, anoAtual)`.
3. `anoAtual = Calendar.getInstance().get(Calendar.YEAR)`, nunca um número fixo.
4. Se algo for inválido, `return` sem gravar.

### Gravação
- **Edição:** atualizar os campos de `livroEdicao` (mantendo o `id`) e chamar `dao.atualizar(...)`.
- **Inclusão:** `new Livro(titulo, autor, ano, lido)` e chamar `dao.salvar(...)`.
- Mostrar o Toast de sucesso ou erro correspondente (`msg_salvo_*` / `msg_atualizado_*`).
- **Só em caso de sucesso:** `NavegacaoMenu.irPara(this, new Intent(this, ListaLivrosActivity.class))`.
- Em caso de falha, ficar na tela.

## Trechos-chave
```java
int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
boolean valido = true;

String textoAno = edtAno.getText().toString().trim();
int ano = 0;
if (textoAno.isEmpty()) {                    // ANTES do parseInt
    edtAno.setError(getString(R.string.erro_ano_obrigatorio));
    valido = false;
} else {
    ano = Integer.parseInt(textoAno);
    if (ano < 1450 || ano > anoAtual) {
        edtAno.setError(getString(R.string.erro_ano_invalido, anoAtual));
        valido = false;
    }
}
if (!valido) return;
```

## Arquivos
- Alterar: `CadastroLivroActivity.java`

## Critérios de aceite
- [ ] Salvar com tudo vazio mostra **3 erros** ao mesmo tempo e não grava.
- [ ] Título ou autor só com espaços contam como vazio.
- [ ] Ano 1449 ou ano atual + 1 mostram o erro de faixa; 1450 e o ano atual são aceitos.
- [ ] Não existe nenhum número de ano escrito direto no código (só o 1450 do limite inferior).
- [ ] No modo edição, os campos chegam preenchidos e o botão mostra "Atualizar" (vindo de `strings.xml`).
- [ ] A edição altera o registro existente; o `id` se mantém e nenhum livro é duplicado.
- [ ] Depois de salvar com sucesso, o app volta à Lista via `irPara`, sem `finish()` sozinho.
- [ ] Há Toast tanto no sucesso quanto na falha.

## Armadilhas
- `Integer.parseInt("")` lança `NumberFormatException` e o app fecha.
- `finish()` sozinho fecha o app, porque a Lista já foi encerrada pelo `irPara`.
- A forma `getSerializableExtra(String)` está marcada como obsoleta (deprecated) a partir da API 33, mas funciona. A alternativa é `getSerializableExtra("livroSelecionado", Livro.class)` com uma checagem de `Build.VERSION.SDK_INT >= 33`.
- 🔗 Validar na borda (entrada do usuário) antes de chegar ao banco é o mesmo princípio de validar o *schema* na entrada de um pipeline de dados: o erro aparece cedo e com mensagem clara.
