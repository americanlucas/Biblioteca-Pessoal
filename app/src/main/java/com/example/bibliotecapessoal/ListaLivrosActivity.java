package com.example.bibliotecapessoal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bibliotecapessoal.adapter.AdapterLivro;
import com.example.bibliotecapessoal.model.Livro;
import com.example.bibliotecapessoal.persistencia.LivroDAO;

import java.util.ArrayList;

public class ListaLivrosActivity extends AppCompatActivity {

    private LivroDAO dao;
    private AdapterLivro adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_livros);
        NavegacaoMenu.configurar(this, R.id.nav_livros);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // bottomNav já trata a margem
            return insets;
        });

        dao = new LivroDAO(this);

        // Começa vazio: os dados chegam no onResume
        adapter = new AdapterLivro(new ArrayList<>(), this);
        RecyclerView recyclerLivros = findViewById(R.id.recyclerLivros);
        recyclerLivros.setLayoutManager(new LinearLayoutManager(this));
        recyclerLivros.setAdapter(adapter);

        recyclerLivros.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerLivros,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == RecyclerView.NO_POSITION) return;
                        // Livro vem do adapter (RT04) e vai para o Cadastro só pela Intent (RT08)
                        Intent intent = new Intent(ListaLivrosActivity.this, CadastroLivroActivity.class);
                        intent.putExtra("livroSelecionado", adapter.getLivro(position));
                        NavegacaoMenu.irPara(ListaLivrosActivity.this, intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        if (position == RecyclerView.NO_POSITION) return;
                        confirmarExclusao(adapter.getLivro(position));
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega sempre que a tela volta ao primeiro plano (RT05)
        carregarLivros();
    }

    private void carregarLivros() {
        adapter.atualizarLista(dao.listarTodos());
    }

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
}
