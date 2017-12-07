package br.com.pokemon.player;

import br.com.pokemon.player.enuns.Acao;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.*;
import br.com.pokemon.poke.enuns.Status;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe responsável por instaciar o jogador que vai controlar o time
 */
public abstract class Jogador {

    static Scanner sc = new Scanner(System.in);

    private String nome;
    private Acao acao;
    private List<Pokemon> pokemons = new ArrayList<>();

    /**
     * Construtor do jogador
     *
     * @param nome     nome do jogador, que vai ser do time
     * @param pokemons pokemons do jogador
     */
    public Jogador(String nome, List<Pokemon> pokemons) {
        this.nome = nome;
        this.pokemons = pokemons;
    }

    public String getNome() {
        return nome;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    /**
     * Metódo responsavel por veridicar se o jogador é controlodado pelo computadore
     * @return retorna verdade se sim caso contrário retorna falso
     */
    public abstract boolean isMaquina();
}