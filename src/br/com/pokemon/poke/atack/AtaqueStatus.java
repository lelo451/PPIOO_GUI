package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.enuns.Status;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Classe reponsavel por executar o ataque status
 */
public class AtaqueStatus extends Ataque {

    public AtaqueStatus(int id) {
        super(id);
    }

    @Override
    public String Efeito(Ataque ataque, Pokemon atacante, Pokemon enemy, String timeAtaque, String timeDefesa) throws FileNotFoundException {
        String ans = " ";
        if (ataque.getPpAtual() < 1) {
            System.out.println("Não é possivel utilizar o ataque: " + ataque.getNome() + "!");
            ans = "Não é possivel utilizar o ataque: " + ataque.getNome() + "!\n";
        } else {
            ataque.setPpAtual(ataque.getPpAtual() - 1);
            boolean acerto = calculoAcerto(ataque.getAccuracy(), atacante.getModifierAtk(), enemy.getModifierEvasion(), atacante);
            if(acerto) {
                double dano = calculoDano(ataque, atacante, enemy, false, true);
                String[] parameter = ataque.getParameter().split("\t");
                int porcentagem = Integer.parseInt(parameter[1]);
                boolean apply = new Random().nextInt(100) < porcentagem;
                switch (parameter[0]) {
                    case "Flinch":
                        if (apply)
                            enemy.setFlinch(true);
                        break;
                    case "Confusion":
                        if (apply)
                            enemy.setConfusion(true);
                        break;
                    default:
                        if (apply)
                            enemy.setStatus(Status.valueOf(parameter[0].toUpperCase()));
                        break;
                }
                ans = mensagemDeDano(atacante, enemy, dano, timeAtaque, timeDefesa);
            } else {
                System.out.println("O ataque " + ataque.getNome() + " falhou!");
                ans = "O ataque " + ataque.getNome() + " falhou!\n";
            }
        }
        return ans;
    }
}