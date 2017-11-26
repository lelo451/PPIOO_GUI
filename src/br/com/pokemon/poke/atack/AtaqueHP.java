package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.enuns.Status;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe reponsavel por executar o ataque hp
 */
public class AtaqueHP extends Ataque {

    public AtaqueHP(int id) {
        super(id);
    }

    @Override
    public String Efeito(Ataque ataque, Pokemon atacante, Pokemon enemy, String timeAtaque, String timeDefesa) throws FileNotFoundException {
        DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
        StringBuilder ans = new StringBuilder();
        if (ataque.getPpAtual() < 1) {
            System.out.println("Não é possivel utilizar o ataque: " + ataque + "!");
            ans.append("Não é possivel utilizar o ataque: ").append(ataque).append("!\n");
        } else {
            ataque.setPpAtual(ataque.getPpAtual() - 1);
            boolean acerto = calculoAcerto(ataque.getAccuracy(), atacante.getModifierAtk(), enemy.getModifierEvasion(), atacante);
            if(acerto) {
                double dano = calculoDano(ataque, atacante, enemy, false, true);
                String[] parameter = ataque.getParameter().split("\t");
                double heal = 0;
                if(parameter[0].equals("dano")) {
                    heal = dano * Double.parseDouble(parameter[1]);
                } else {
                    heal = atacante.getHpMax() * Double.parseDouble(parameter[1]);
                }
                ans.append(mensagemDeDano(atacante, enemy, dano, timeAtaque, timeDefesa));
                if(!atacante.getStatus().equals(Status.FAINTED)) {
                    if (atacante.getHpAtual() + heal > atacante.getHpMax()) {
                        atacante.setHpAtual(atacante.getHpMax());
                    } else {
                        atacante.setHpAtual(atacante.getHpAtual() + heal);
                    }
                    if (heal > 0) {
                        System.out.printf("%s do Time %s Curou %.2f\n", atacante.getEspecie().getNome(), timeAtaque, heal);
                        ans.append(atacante.getEspecie().getNome()).append(" Do Time ").append(timeAtaque).append(" Curou ").append(df.format(heal)).append("!\n");
                    } else {
                        System.out.printf("%s do Time %s Consumiu %.2f\n", atacante.getEspecie().getNome(), timeAtaque, heal);
                        ans.append(atacante.getEspecie().getNome()).append(" Do Time ").append(timeAtaque).append(" Consumiu ").append(df.format(heal)).append("!\n");
                    }
                }
            } else {
                System.out.println("O ataque " + ataque + " falhou!");
                ans.append("O ataque ").append(ataque).append(" falhou!\n");
            }
        }
        return ans.toString();
    }
}
