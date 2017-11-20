package br.com.pokemon.gui.start;

import br.com.pokemon.db.Base;

import java.io.FileNotFoundException;

public class Inicializacoes {

    public static void carregar_tabelas() throws FileNotFoundException {
        Base b = new Base();
        b.criarBase();
    }

}
