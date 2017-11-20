package br.com.pokemon.player;

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

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public abstract int escolherComando(Jogador jogador);

    /**
     * Metódo responsável por trocar os pokemons
     * @param jogador Jogador que está trocando os pokemons
     */
    public void trocarPokemon(Jogador jogador) {
        if (pokemons.size() == 1) {
            System.out.println("Não é Possível Trocar de Pokemon");
        } else {
            List<Pokemon> pokemons = jogador.getPokemons();
            int choice = 0;
            System.out.println("========== Jogador " + jogador.getNome() + " ==========");
            System.out.println("========== Pokemon Atual: " + pokemons.get(0).getEspecie().getNome() + " ==========");
            System.out.println("========== Trocar Pokemon ==========");
            for (int i = 1; i < pokemons.size(); i++) {
                System.out.println(i + ") " + pokemons.get(i).getEspecie().getNome());
            }
            System.out.print("Escolha: ");
            while (choice < 1 || choice > pokemons.size()) {
                choice = validaInput(choice);
            }
            Collections.swap(pokemons, 0, choice);
            System.out.println("O Pokemon " + pokemons.get(choice).getEspecie().getNome() + " foi Trocado por " + pokemons.get(0).getEspecie().getNome() + " do Time " + jogador.getNome());
        }
    }

    /**
     * Metódo responsável por verificar se o pokemon escolhido para troca já se encontra fora de combate
     *
     * @param choice   posição do pokemon escolhido pelo usuário
     * @param pokemons lista de pokemons
     * @return retorna um booleano se o pokemon está fora de combate e falso caso contrário
     */
    private boolean verificaFaint(int choice, List<Pokemon> pokemons) {
        if (pokemons.get(choice).getStatus().equals(Status.FAINTED)) {
            System.out.println("O Pokemon " + pokemons.get(choice).getEspecie().getNome() + " já se Encontra Fora de Batalha!\nPor Favor Selecione um Pokemon Válido");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metódo utilizado quando se utiliza o charge atack para executar os dois ataques do oponente
     *
     * @param jogador  jogador atual
     * @param atacante pokemon atacante
     * @param enemy    pokemon inimigo
     * @param proximo  próximo jogador
     * @throws FileNotFoundException arquivo não encontrado
     */
    public void secondTime(Jogador jogador, Pokemon atacante, Pokemon enemy, Jogador proximo) throws FileNotFoundException {
        int comando = jogador.escolherComando(jogador);
        if (comando == 0) {
            jogador.trocarPokemon(jogador);
        } else {
            jogador.usarAtaque(jogador, atacante, enemy, proximo);
        }
    }

    /**
     * Metodo que utiliza o ataque escolhido pelo usuario
     *
     * @param jogador  jogador atual
     * @param atacante pokemon atacante
     * @param enemy    pokemon inimigo
     * @param proximo  próximo jogador
     * @throws FileNotFoundException arquivo não encontrado
     */
    public void usarAtaque(Jogador jogador, Pokemon atacante, Pokemon enemy, Jogador proximo) throws FileNotFoundException {
        int choice = 0;
        if (!jogador.isMaquina()) {
            System.out.println("========== Jogador " + jogador.getNome() + " ==========");
            imprimeInformacoesPokemon(atacante);
            System.out.println("========== Ataques ==========");
            for (int i = 0; i < atacante.getAtaques().size(); i++) {
                System.out.println((i + 1) + ") " + atacante.getAtaques().get(i).getNome() + " - " + atacante.getAtaques().get(i).getPpAtual() + " Vezes Restantes");
            }
            System.out.print("Escolha: ");
            while (choice < 1 || choice > atacante.getAtaques().size()) {
                choice = validaInput(choice);
            }
            escolha(choice - 1, atacante, enemy, jogador, proximo);
        } else {
            System.out.println("========== Jogador " + jogador.getNome() + " ==========");
            imprimeInformacoesPokemon(atacante);
            if(pokemons.size() > 1) {
                choice = ThreadLocalRandom.current().nextInt(1, pokemons.size());
            } else {
                choice = 1;
            }
            escolha(choice - 1, atacante, enemy, jogador, proximo);
        }
        String tipoAtaque = atacante.getAtaques().get(choice - 1).getClasse();
        Ataque atk = atacante.getAtaques().get(choice - 1);
        switch (tipoAtaque) {
            case "charge":
                System.out.println("O Jogador " + jogador.getNome() + " Irá Fazer um Charge Atack\ne por Isso Atacará no Próximo Turno!");
                secondTime(proximo, enemy, atacante, jogador);
                secondTime(proximo, enemy, atacante, jogador);
                System.out.println("========== Jogador " + jogador.getNome() + " ==========");
                atk.Efeito(atk, atacante, enemy, jogador.getNome(), proximo.getNome());
                break;
            case "comum":
                atk.Efeito(atk, atacante, enemy, jogador.getNome(), proximo.getNome());
                break;
            case "fixo":
                atk.Efeito(atk, atacante, enemy, jogador.getNome(), proximo.getNome());
                break;
            case "hp":
                atk.Efeito(atk, atacante, enemy, jogador.getNome(), proximo.getNome());
                break;
            case "modifier":
                atk.Efeito(atk, atacante, enemy, jogador.getNome(), proximo.getNome());
                break;
            case "multihit":
                atk.Efeito(atk, atacante, enemy, jogador.getNome(), proximo.getNome());
                break;
            case "status":
                atk.Efeito(atk, atacante, enemy, jogador.getNome(), proximo.getNome());
                break;
        }
    }

    /**
     * Imprime as informações do pokemon
     *
     * @param atacante pokemon atacante
     */
    private void imprimeInformacoesPokemon(Pokemon atacante) {
        System.out.println("\n========== POKEMON ==========");
        System.out.println("========== Nome: " + atacante.getEspecie().getNome() + " ==========");
        System.out.println("========== LVL: " + atacante.getLevel() + " ==========");
        System.out.printf("========== HP: %.2f/%.2f ==========\n", atacante.getHpAtual(), atacante.getHpMax());
        System.out.println("========== ATK: " + atacante.getAtk() + " ==========");
        System.out.println("========== DEF: " + atacante.getDef() + " ==========");
        System.out.println("========== SPD: " + atacante.getSpd() + " ==========");
        System.out.println("========== SPE: " + atacante.getSpe() + " ==========");
        System.out.println("========== Status: " + atacante.getStatus() + " ==========");
        System.out.println("========== Flinch: " + (atacante.isFlinch() ? "FLINCH" : "") + " ==========");
        System.out.println("========== Confuso: " + (atacante.isConfusion() ? "CONFUSION" : "") + " ==========");
        System.out.println("============================\n");
    }

    /**
     * Verifica se o ataque escolhido pode ser utilizado ainda
     *
     * @param choice  posição do ataque escolhido
     * @param pk      pokemon a realizar o ataque
     * @param enemy   pokemon inimigo
     * @param jogador jogador atual
     * @param proximo próximo jogador
     * @throws FileNotFoundException arquivo não encontrado
     */
    private void escolha(int choice, Pokemon pk, Pokemon enemy, Jogador jogador, Jogador proximo) throws FileNotFoundException {
        if (pk.getAtaques().get(choice).getPpAtual() < 1) {
            System.out.println("Não é possivel utilizar o ataque: " + pk.getAtaques().get(choice).getNome() + "!");
            usarAtaque(jogador, pk, enemy, proximo);
        }
    }

    /**
     * Metódo responsavel por veridicar se o jogador é controlodado pelo computadore
     *
     * @return retorna verdade se sim caso contrário retorna falso
     */
    public abstract boolean isMaquina();

    public static int validaInput(int input) {
        try {
            input = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Insira um Valor Válido!");
            sc.nextLine();
            input = 0;
        }
        return input;
    }
}