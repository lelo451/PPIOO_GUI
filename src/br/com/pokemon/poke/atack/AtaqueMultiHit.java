package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.Pokemon;

import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe reponsavel por executar o ataque multi hit
 */
public class AtaqueMultiHit extends Ataque {

    public AtaqueMultiHit(int id) {
        super(id);
    }

    @Override
    public void Efeito(Ataque ataque, Pokemon atacante, Pokemon enemy, String timeAtaque, String timeDefesa) throws FileNotFoundException {
        if (ataque.getPpAtual() < 1) {
            System.out.println("Não é possivel utilizar o ataque: " + ataque + "!");
        } else {
            ataque.setPpAtual(ataque.getPpAtual() - 1);
            boolean acerto = calculoAcerto(ataque.getAccuracy(), atacante.getModifierAtk(), enemy.getModifierEvasion(), atacante);
            if(acerto) {
                String[] parameter = ataque.getParameter().split("\t");
                int vezes = ThreadLocalRandom.current().nextInt(Integer.parseInt(parameter[0]) - 1, Integer.parseInt(parameter[1]) + 1);
                boolean critico = calculoCritico(atacante.getSpd());
                double dano = 0;
                for(int i = 0; i < vezes; i++) {
                    dano += calculoDano(ataque, atacante, enemy, critico, false);
                }
                mensagemDeDano(atacante, enemy, dano, timeAtaque, timeDefesa);
            } else {
                System.out.println("O ataque " + ataque + " falhou!");
            }
        }
    }
}
