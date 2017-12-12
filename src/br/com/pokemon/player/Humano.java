package br.com.pokemon.player;

import br.com.pokemon.poke.Pokemon;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por executar as ações do jogador Humano
 */
public class Humano extends Jogador {

    public Humano(String nome, List<Pokemon> pokemons) {
        super(nome, pokemons);
    }

    @Override
    public boolean isMaquina() {
        return false;
    }
}
