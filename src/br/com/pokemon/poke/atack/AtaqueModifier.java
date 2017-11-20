package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.Pokemon;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Classe reponsavel por executar o ataque modifier
 */
public class AtaqueModifier extends Ataque {

    public AtaqueModifier(int id) {
        super(id);
    }

    private void applyModifier(Pokemon pk, String modify, int quant) {
        switch (modify) {
            case "ATK":
                pk.setModifierAtk(verifyModify(pk.getModifierAtk() + quant));
                printMessage(pk, modify, quant);
                break;
            case "DEF":
                pk.setModifierDef(verifyModify(pk.getModifierAtk() + quant));
                printMessage(pk, modify, quant);
                break;
            case "SPE":
                pk.setModifierSpe(verifyModify(pk.getModifierAtk() + quant));
                printMessage(pk, modify, quant);
                break;
            case "SPD":
                pk.setModifierSpd(verifyModify(pk.getModifierAtk() + quant));
                printMessage(pk, modify, quant);
                break;
            case "Evasion":
                pk.setModifierEvasion(verifyModify(pk.getModifierAtk() + quant));
                printMessage(pk, modify, quant);
                break;
            case "Accuracy":
                pk.setModifierAccuracy(verifyModify(pk.getModifierAtk() + quant));
                printMessage(pk, modify, quant);
                break;
        }
    }

    private int verifyModify(int i) {
        if(i > 6) {
            return 6;
        } else if (i < -6) {
            return -6;
        } else {
            return i;
        }
    }

    private void printMessage(Pokemon pk, String modify, int quant) {
        System.out.println("O " + modify + " de " + pk.getEspecie().getNome() + " foi modificado em " + quant);
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
                boolean apply = new Random().nextInt(100) < Integer.parseInt(parameter[2]);
                int modifier = Integer.parseInt(parameter[1]);
                if(apply) {
                    if(modifier < 0) {
                        applyModifier(enemy, parameter[0], modifier);
                    } else {
                        applyModifier(atacante, parameter[0], modifier);
                    }
                }
                double dano = calculoDano(ataque, atacante, enemy,false, true);
                mensagemDeDano(atacante, enemy, dano, timeAtaque, timeDefesa);
            } else {
                System.out.println("O ataque " + ataque + " falhou!");
            }
        }
    }
}