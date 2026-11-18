# SPEC-07 — `NavegacaoMenu` (navegação centralizada)

- **Camada:** T
- **Depende de:** SPEC-06
- **Requisitos:** RT09
- **Peso na nota:** 1,5 pt

## Objetivo
Concentrar em uma única classe toda a configuração do menu inferior e toda a troca de telas, garantindo **uma tela aberta por vez**.

## Requisitos
1. Copiar a classe do **Anexo A** do enunciado para `NavegacaoMenu.java`, na raiz do pacote.
2. Completar o TODO do `configurar`:
   - `nav_novo` → `irPara(activity, new Intent(activity, CadastroLivroActivity.class))`
   - `nav_resumo` → `irPara(activity, new Intent(activity, ResumoActivity.class))`
3. Manter `setSelectedItemId(itemAtual)` **antes** de `setOnItemSelectedListener`.
4. Chamar no `onCreate` de cada Activity, depois do `setContentView`:
   - Lista → `NavegacaoMenu.configurar(this, R.id.nav_livros);`
   - Cadastro → `NavegacaoMenu.configurar(this, R.id.nav_novo);`
   - Resumo → `NavegacaoMenu.configurar(this, R.id.nav_resumo);`

## Trechos-chave
```java
public static void irPara(Activity origem, Intent intent) {
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    origem.startActivity(intent);
    origem.finish();              // encerra a atual: só existe 1 tela aberta
}
```

## Arquivos
- Criar: `NavegacaoMenu.java`
- Alterar: `ListaLivrosActivity.java`, `CadastroLivroActivity.java`, `ResumoActivity.java` (só a chamada a `configurar`)

## Critérios de aceite
- [ ] Os 3 itens do menu navegam corretamente entre as 3 telas.
- [ ] O item da tela atual aparece marcado.
- [ ] Tocar no item da tela atual não faz nada (não reabre a tela).
- [ ] Nenhuma Activity chama `startActivity` direto.
- [ ] O botão Voltar fecha o app em qualquer tela (é o comportamento esperado).

## Armadilhas
- Inverter a ordem (listener antes de `setSelectedItemId`) faz a tela reabrir sem parar.
- No modo **edição** do Cadastro, o item "Novo" já está marcado, então tocar nele não limpa o formulário. O enunciado não pede isso; anote caso o professor teste.
