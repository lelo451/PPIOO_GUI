package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.Pokemon;

import java.io.FileNotFoundException;

public class AtaqueComum extends Ataque {
    public AtaqueComum(int i) {
        super(i);
    }

    @Override
    public void Efeito(Ataque ataque, Pokemon atacante, Pokemon enemy, String timeAtaque, String timeDefesa) throws FileNotFoundException {
        if (ataque.getPpAtual() < 1) {
            System.out.println("Não é possivel utilizar o ataque: " + ataque + "!");
        } else {
            ataque.setPpAtual(ataque.getPpAtual() - 1);
            boolean acerto = calculoAcerto(ataque.getAccuracy(), atacante.getModifierAtk(), enemy.getModifierEvasion(), atacante);
            if(acerto) {
                double dano = calculoDano(ataque, atacante, enemy, false, true);
                mensagemDeDano(atacante, enemy, dano, timeAtaque, timeDefesa);
            } else {
                System.out.println("O ataque " + ataque + " falhou!");
            }
        }
    }
}
