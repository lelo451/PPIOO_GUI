package br.com.pokemon.player;

import br.com.pokemon.poke.Pokemon;

import java.util.List;

/**
 * Classe responsável por executar as ações do jogador Máquina
 */
public class Maquina extends Jogador {

    public Maquina(String nome, List<Pokemon> pokemons) {
        super(nome, pokemons);
    }

    @Override
    public boolean isMaquina() {
        return true;
    }
}
