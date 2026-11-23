package com.example.bibliotecapessoal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bibliotecapessoal.model.Livro;
import com.example.bibliotecapessoal.persistencia.LivroDAO;

import java.util.Calendar;

public class CadastroLivroActivity extends AppCompatActivity {

    private EditText edtTitulo;
    private EditText edtAutor;
    private EditText edtAno;
    private CheckBox chkLido;
    private LivroDAO dao;
    private Livro livroEdicao;   // null = modo inclusão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_livro);
        NavegacaoMenu.configurar(this, R.id.nav_novo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // bottomNav já trata a margem
            return insets;
        });

        edtTitulo = findViewById(R.id.edtTitulo);
        edtAutor = findViewById(R.id.edtAutor);
        edtAno = findViewById(R.id.edtAno);
        chkLido = findViewById(R.id.chkLido);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        dao = new LivroDAO(this);

        livroEdicao = lerLivroDaIntent();
        if (livroEdicao != null) {
            // Modo edição: formulário chega preenchido
            edtTitulo.setText(livroEdicao.getTitulo());
            edtAutor.setText(livroEdicao.getAutor());
            edtAno.setText(String.valueOf(livroEdicao.getAno()));
            chkLido.setChecked(livroEdicao.isLido());
            btnSalvar.setText(R.string.btn_atualizar);
        }

        btnSalvar.setOnClickListener(v -> salvar());
    }

    // API 33+ tem a versão tipada; a antiga continua valendo nas anteriores
    private Livro lerLivroDaIntent() {
        Intent intent = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return intent.getSerializableExtra("livroSelecionado", Livro.class);
        }
        return (Livro) intent.getSerializableExtra("livroSelecionado");
    }

    private void salvar() {
        String titulo = edtTitulo.getText().toString().trim();
        String autor = edtAutor.getText().toString().trim();
        String textoAno = edtAno.getText().toString().trim();
        boolean lido = chkLido.isChecked();

        // Ano atual calculado, nunca fixo no código
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        boolean valido = true;

        // Valida todos os campos antes de parar, para mostrar todos os erros juntos
        if (titulo.isEmpty()) {
            edtTitulo.setError(getString(R.string.erro_titulo_obrigatorio));
            valido = false;
        }
        if (autor.isEmpty()) {
            edtAutor.setError(getString(R.string.erro_autor_obrigatorio));
            valido = false;
        }
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

        boolean ok;
        if (livroEdicao != null) {
            // Altera o próprio objeto recebido para manter o id e não duplicar
            livroEdicao.setTitulo(titulo);
            livroEdicao.setAutor(autor);
            livroEdicao.setAno(ano);
            livroEdicao.setLido(lido);
            ok = dao.atualizar(livroEdicao);
            Toast.makeText(this, ok ? R.string.msg_atualizado_sucesso : R.string.msg_atualizado_erro,
                    Toast.LENGTH_SHORT).show();
        } else {
            ok = dao.salvar(new Livro(titulo, autor, ano, lido));
            Toast.makeText(this, ok ? R.string.msg_salvo_sucesso : R.string.msg_salvo_erro,
                    Toast.LENGTH_SHORT).show();
        }

        // Só volta à Lista se gravou; na falha o usuário continua no formulário
        if (ok) {
            NavegacaoMenu.irPara(this, new Intent(this, ListaLivrosActivity.class));
        }
    }
}
