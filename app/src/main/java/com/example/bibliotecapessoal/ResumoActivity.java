package com.example.bibliotecapessoal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bibliotecapessoal.persistencia.LivroDAO;

public class ResumoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumo);
        NavegacaoMenu.configurar(this, R.id.nav_resumo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // bottomNav já trata a margem
            return insets;
        });

        // Contagens feitas pelo banco com COUNT(*), sem carregar a lista (RF05)
        LivroDAO dao = new LivroDAO(this);
        TextView txtTotal = findViewById(R.id.txtTotal);
        TextView txtLidos = findViewById(R.id.txtLidos);
        TextView txtNaoLidos = findViewById(R.id.txtNaoLidos);
        txtTotal.setText(getString(R.string.resumo_total, dao.contar(null)));
        txtLidos.setText(getString(R.string.resumo_lidos, dao.contar(true)));
        txtNaoLidos.setText(getString(R.string.resumo_nao_lidos, dao.contar(false)));
    }
}
