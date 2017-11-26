package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.Pokemon;

import java.io.FileNotFoundException;

/**
 * Classe reponsavel por executar o ataque charge
 */
public class AtaqueCharge extends Ataque {

    public AtaqueCharge(int id) {
        super(id);
    }

    @Override
    public String Efeito(Ataque ataque, Pokemon atacante, Pokemon enemy, String timeAtaque, String timeDefesa) throws FileNotFoundException {
        String ans = " ";
        if (ataque.getPpAtual() < 1) {
            System.out.println("Não é possivel utilizar o ataque: " + ataque + "!");
            ans = "Não é possivel utilizar o ataque: " + ataque + "!\n";
        } else {
            ataque.setPpAtual(ataque.getPpAtual() - 1);
            boolean acerto = calculoAcerto(ataque.getAccuracy(), atacante.getModifierAtk(), enemy.getModifierEvasion(), atacante);
            if(acerto) {
                double dano = calculoDano(ataque, atacante, enemy, false, true);
                dano *= 1.75;
                mensagemDeDano(atacante, enemy, dano, timeAtaque, timeDefesa);
            } else {
                System.out.println("O ataque " + ataque + " falhou!");
                ans = "O ataque " + ataque + " falhou!\n";
            }
        }
        return ans;
    }
}
