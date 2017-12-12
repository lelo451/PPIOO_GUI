package br.com.pokemon.poke.atack;

import br.com.pokemon.db.Base;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.enuns.Status;
import br.com.pokemon.poke.enuns.Tipo;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe responsável por executar as ações quando um ataque é realizado
 */
public abstract class Ataque {

    private int id;
    private String nome;
    private double ppMax;
    private double ppAtual;
    private int power;
    private int accuracy;
    private Tipo tipo;
    private String classe;
    private String parameter;

    public Ataque(int id) {
        Base b = new Base();
        List<String> ataqueinfo = b.getData(id, false);
        this.id = id;
        this.nome = ataqueinfo.get(1);
        this.ppMax = Double.parseDouble(ataqueinfo.get(3));
        this.ppAtual = Double.parseDouble(ataqueinfo.get(3));
        this.power = Integer.parseInt(ataqueinfo.get(4));
        this.accuracy = Integer.parseInt(ataqueinfo.get(5));
        this.tipo = Tipo.valueOf(ataqueinfo.get(2).toUpperCase());
        this.classe = ataqueinfo.get(6);
        this.parameter = ataqueinfo.get(7);
    }

    /**
     * Metódo responsável por calcular se o ataque a ser executado vai ser critico ou não
     * @param spd velocidade do pokemon
     * @return verdadeiro ou falso
     */
    boolean calculoCritico(double spd) {
        Random random = new Random();
        if(random.nextInt(100) < ((spd / 512) * 100)) {
            System.out.println("Ataque critico!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metódo responsável por calcular se o ataque realizado vai acertar o pokemon inimigo
     * @param accuracy precisão do pokemor
     * @param modifier_accuracy mdificador de precisão do pokemon atacante
     * @param modifier_evasion modificador de evasão do pokemon inimigo
     * @param atacante pokemon que está realizando o ataque
     * @return
     */
    boolean calculoAcerto(int accuracy, int modifier_accuracy, int modifier_evasion, Pokemon atacante) {
        if (atacante.getStatus().equals(Status.FROZEN) || atacante.getStatus().equals(Status.SLEEP) || atacante.isFlinch()) {
            System.out.println("Ataque do " + atacante.getEspecie().getNome() + " Cancelado Devido ao Status " + (atacante.isFlinch() ? "FLINCH" : atacante.getStatus()));
            return false;
        } else {
            int modifierAtacante = getModifier(modifier_accuracy);
            int modifierEnemy = getModifier(modifier_evasion);
            int prob = accuracy * getModifier(modifierAtacante / modifierEnemy);
            if(atacante.getStatus().equals(Status.PARALYSIS)) {
                System.out.println("Probabilidade de Acerto do " + atacante.getEspecie().getNome() + " Reduzida em 25% Devido ao Status PARALYSIS");
                prob -= 25;
            }
            Random random = new Random();
            return (random.nextInt(100) < prob);
        }
    }

    /**
     * Metódo responsável por calcular o valor dos modificadores
     * @param modify modificador de -6 a 6
     * @return modificador em porcentagem
     */
    public static int getModifier(int modify) {
        final Map<Integer, Integer> modifier = new HashMap<>();
        modifier.put(-6, 33);
        modifier.put(-5, 37);
        modifier.put(-4, 43);
        modifier.put(-3, 50);
        modifier.put(-2, 60);
        modifier.put(-1, 75);
        modifier.put(0, 100);
        modifier.put(1, 133);
        modifier.put(2, 166);
        modifier.put(3, 200);
        modifier.put(4, 233);
        modifier.put(5, 266);
        modifier.put(6, 300);
        return modifier.get(modify);
    }

    /**
     * Metódo responsável por exibir a mensagem de dano quando um ataque ocorre
     * @param atacante pokemon atacante
     * @param enemy pokemon oponente
     * @param dano dano a ser aplicado
     * @param timeAtaque nome do time que está realizando o ataque
     * @param timeDefesa nome do time que está realizando a defesa
     */
    public static String mensagemDeDano(Pokemon atacante, Pokemon enemy, double dano, String timeAtaque, String timeDefesa) {
        DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
        String ans = " ";
        if(atacante.isConfusion()) {
            if(new Random().nextInt(100) < 50) {
                atacante.setHpAtual(atacante.getHpAtual() - dano);
                System.out.printf("%s do Time %s Causou %.2f Nele Mesmo Devido ao Status CONFUSION\n", atacante.getEspecie().getNome(), timeAtaque, dano, enemy.getEspecie().getNome());
                ans = atacante.getEspecie().getNome() + " Do Time " + timeAtaque + " Causou " + df.format(dano) + " Nele Mesmo Devido ao Status CONFUSION\n";
            } else {
                if(enemy.getHpAtual() - dano <= 0.0) {
                    enemy.setHpAtual(0);
                    enemy.setStatus(Status.FAINTED);
                } else {
                    enemy.setHpAtual(enemy.getHpAtual() - dano);
                }
                System.out.printf("%s do Time %s Causou %.2f em %s do Time %s\n", atacante.getEspecie().getNome(), timeAtaque, dano, enemy.getEspecie().getNome(), timeDefesa);
                ans = atacante.getEspecie().getNome()  + " Do Time " + timeAtaque + " Causou " + df.format(dano) + " No Pokemon " + enemy.getEspecie().getNome() + " Do Time " + timeDefesa + " !\n";
            }
        } else {
            if(enemy.getHpAtual() - dano <= 0.0) {
                enemy.setHpAtual(0);
                enemy.setStatus(Status.FAINTED);
            } else {
                enemy.setHpAtual(enemy.getHpAtual() - dano);
            }
            System.out.printf("%s do Time %s Causou %.2f em %s do Time %s\n", atacante.getEspecie().getNome(), timeAtaque, dano, enemy.getEspecie().getNome(), timeDefesa);
            ans = atacante.getEspecie().getNome() + " Do Time " + timeAtaque + " Causou " + df.format(dano) + " No Pokemon " + enemy.getEspecie().getNome() + " Do Time " + timeDefesa + "!\n";
        }
        return ans;
    }

    /**
     * Metódo responsável por executar o efeito de cada ataque
     * @param ataque ataque a ser executado
     * @param atacante pokemon atacante
     * @param enemy pokemon oponente
     * @param timeAtaque time realizando o ataque
     * @param TimeDefesa time realizando a defesa
     * @throws FileNotFoundException
     */
    public abstract String Efeito(Ataque ataque, Pokemon atacante, Pokemon enemy, String timeAtaque, String TimeDefesa) throws FileNotFoundException;

    /**
     * Metódo responsável por calcular o dano a ser infligido por um pokemon
     * @param ataque ataque a ser realizado
     * @param atacante pokemon atacante
     * @param enemy pokemon oponente
     * @param critico se é ataque multhit
     * @param normal qualquer ataque que não seja ultihit
     * @return retorna o dano a ser aplicado
     * @throws FileNotFoundException arquivo não encontrado
     */
    public double calculoDano(Ataque ataque, Pokemon atacante, Pokemon enemy, boolean critico, boolean normal) throws FileNotFoundException {
        double a = 0, d = 0, l = atacante.getLevel();
        Verificacoes v = new Verificacoes();
        Double[] atks = v.verificaAtaque(ataque.getTipo(), a, d, atacante.getAtk(), enemy.getDef(), atacante.getSpe(), enemy.getSpe());
        a = atks[0];
        d = atks[1];
        if(calculoCritico(atacante.getSpd()) && normal) {
            l *= 2;
        }
        if(critico) {
            l *= 2;
        }
        if(atacante.getStatus().equals(Status.BURN)) {
            a /= 2;
            System.out.println("Ataque do " + atacante.getEspecie().getNome() + " Reduzido em 50% Devido ao Status de BURN\n");
        }
        Double[] valores = v.verificaValor(a, d);
        a = valores[0];
        d = valores[1];
        double dano = v.calculo(l, a, power, d, atacante.getEspecie().getTipo1(), atacante.getEspecie().getTipo2(), ataque.getTipo());
        return v.calculoFinal(ataque.getTipo(), enemy.getEspecie().getTipo1(), enemy.getEspecie().getTipo2(), dano);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPpMax() {
        return ppMax;
    }

    public double getPpAtual() {
        return ppAtual;
    }

    public void setPpAtual(double ppAtual) {
        this.ppAtual = ppAtual;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getClasse() {
        return classe;
    }

    public String getParameter() {
        return parameter;
    }
}
