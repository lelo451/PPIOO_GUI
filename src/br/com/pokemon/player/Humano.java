package br.com.pokemon.player;

import br.com.pokemon.poke.Pokemon;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por executar as ações do jogador Humano
 */
public class Humano extends Jogador {

    static Scanner sc = new Scanner(System.in);

    public Humano(String nome, List<Pokemon> pokemons) {
        super(nome, pokemons);
    }

    @Override
    public int escolherComando(Jogador jogador) {
        int choice = 0;
        System.out.println("========== Jogador " + jogador.getNome() + " ==========");
        System.out.println("========== Pokemon: " + jogador.getPokemons().get(0) + " ==========");
        System.out.println("========== Comandos ==========");
        System.out.println("1) Trocar Pokemons");
        System.out.println("2) Usar um Ataque");
        System.out.print("Escolha: ");
        while (choice < 1 || choice > 2) {
            choice = validaInput(choice);
        }
        return choice - 1;
    }

    @Override
    public boolean isMaquina() {
        return false;
    }
}
