package com.example.bibliotecapessoal;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavegacaoMenu {

    // Marca o item da tela atual e define para onde cada item do menu leva
    public static void configurar(final Activity activity, final int itemAtual) {
        BottomNavigationView nav = activity.findViewById(R.id.bottomNav);
        nav.setSelectedItemId(itemAtual); // ANTES do listener, senão dispara uma navegação
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            // Abre a tela do item tocado, se não for a tela atual
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == itemAtual) {
                    return true;
                }
                if (id == R.id.nav_livros) {
                    irPara(activity, new Intent(activity, ListaLivrosActivity.class));
                } else if (id == R.id.nav_novo) {
                    irPara(activity, new Intent(activity, CadastroLivroActivity.class));
                } else if (id == R.id.nav_resumo) {
                    irPara(activity, new Intent(activity, ResumoActivity.class));
                }
                return true;
            }
        });
    }

    // Abre a tela de destino sem animação e encerra a atual
    public static void irPara(Activity origem, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        origem.startActivity(intent);
        origem.finish();
    }
}
