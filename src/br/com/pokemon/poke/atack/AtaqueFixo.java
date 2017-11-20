package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.Pokemon;

/**
 * Classe reponsavel por executar o ataque fixo
 */
public class AtaqueFixo extends Ataque {

    public AtaqueFixo(int id) {
        super(id);
    }

    @Override
    public void Efeito(Ataque ataque, Pokemon atacante, Pokemon enemy, String timeAtaque, String timeDefesa) {
        if (ataque.getPpAtual() < 1) {
            System.out.println("Não é possivel utilizar o ataque: " + ataque + "!");
        } else {
            ataque.setPpAtual(ataque.getPpAtual() - 1);
            boolean acerto = calculoAcerto(ataque.getAccuracy(), atacante.getModifierAtk(), enemy.getModifierEvasion(), atacante);
            if(acerto) {
                String tipoDano = ataque.getParameter();
                double dano = 0;
                if(tipoDano.equals("lvl")) {
                    dano = atacante.getLevel();
                } else {
                    dano = Double.parseDouble(tipoDano);
                }
                mensagemDeDano(atacante, enemy, dano, timeAtaque, timeDefesa);
            } else {
                System.out.println("O ataque " + ataque + " falhou!");
            }
        }
    }
}