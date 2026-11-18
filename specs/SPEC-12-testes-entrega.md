# SPEC-12 — Testes e empacotamento

- **Camada:** T (Testes)
- **Depende de:** todas
- **Onde:** emulador + revisão do código

## Objetivo
Provar no emulador cada critério da nota e entregar o `.zip` limpo, junto com o vídeo.

## Roteiro de teste (gravar o vídeo nesta ordem)
1. **Validação:** Novo → Salvar vazio → 3 erros aparecem. Ano `1449` → erro de faixa. Ano do próximo ano → erro.
2. **Cadastro:** incluir 3 livros, misturando lidos e não lidos, com títulos fora de ordem (ex.: "O Hobbit", "Clean Code", "Dom Casmurro"). Um Toast aparece em cada um.
3. **Listagem:** a ordem é alfabética e as tags Lido / Não lido têm cores diferentes.
4. **Edição:** clicar em um item → campos preenchidos e botão "Atualizar" → mudar algo → volta à lista atualizada, sem duplicar.
5. **Exclusão:** clique longo → Cancelar (nada muda) → clique longo → Excluir → Toast e o item some na hora.
6. **Resumo:** os números batem (lidos + não lidos = total).
7. **Navegação:** os 3 itens do menu funcionam em todas as telas, e Voltar fecha o app.

## Auditoria de código (antes de zipar)
Pedir ao agente uma busca no projeto por:
- [ ] `Toast.makeText(this, "` ou `setError("` → deve dar **0** resultados (RT07)
- [ ] `android:text="` ou `android:hint="` sem `@string` → 0
- [ ] `#` (cores) em layouts → 0
- [ ] `startActivity(` fora de `NavegacaoMenu` → 0 (RT09)
- [ ] `static` fora de constantes e de `NavegacaoMenu` → 0 (RT08)
- [ ] `for`/`while` em `ResumoActivity` ou em `contar` → 0 (RF05)
- [ ] `MainActivity` → 0

## Empacotamento
1. **Build › Clean Project** e fechar o Android Studio e o Antigravity.
2. Apagar as pastas `build/`, `app/build/` e `.gradle/`.
3. Decidir sobre `.agent/` e `specs/`: incluir mostra o processo SDD, mas confirme com o professor se é permitido.
4. Compactar a pasta do projeto em `.zip` e enviar junto com o vídeo.

## Critérios de aceite
- [ ] Os 7 passos do roteiro passam e estão no vídeo.
- [ ] A auditoria dá 0 em todos os itens.
- [ ] O `.zip` não tem as pastas `build` nem `.gradle`.
