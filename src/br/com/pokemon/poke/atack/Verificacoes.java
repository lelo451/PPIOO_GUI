package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.enuns.Tipo;
import br.com.pokemon.poke.enuns.Tipo2Atack;
import br.com.pokemon.poke.enuns.TipoAtack;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe responsável por verificar algumas condições durante o ataque
 */
class Verificacoes {

    /**
     * Metódo reponsável por determinar o "ataque" e "defesa" a ser utilizado no calculo de dano
     * @param ataque tipo do ataque
     * @param a variavel que recebera o "ataque"
     * @param d variavel que recebera o "defesa"
     * @param atk ataque do pokemon
     * @param defEnemy defesa do pokemon oponente
     * @param spe especial
     * @param speEnemy especial do pokemon oponente
     * @return retorna um vetor de double contento o "ataque" e "defesa"
     */
    Double[] verificaAtaque(Tipo ataque, double a, double d, double atk, double defEnemy, double spe, double speEnemy) {
        List<TipoAtack> atack = Stream.of(TipoAtack.values()).collect(Collectors.toList());
        List<Tipo2Atack> atack2 = Stream.of(Tipo2Atack.values()).collect(Collectors.toList());
        try {
            TipoAtack ataqueTipo1 = TipoAtack.valueOf(ataque.toString().toUpperCase());
            if(atack.contains(ataqueTipo1)) {
                a = atk;
                d = defEnemy;
            }
        } catch (Exception ignored) {}
        try {
            Tipo2Atack ataqueTipo2 = Tipo2Atack.valueOf(ataque.toString().toUpperCase());
            if (atack2.contains(ataqueTipo2)) {
                a = spe;
                d = speEnemy;
            }
        } catch (Exception ignored) {}
        return new Double[] {a, d};
    }

    /**
     * Faz o calculo de dano
     * @param l level
     * @param a ataque
     * @param power poder
     * @param d defesa
     * @param tipo1 tipo 1 do pokemon
     * @param tipo2 tipo 2 do pokemon
     * @param ataque tipo do ataque
     * @return retorna o dano
     */
    double calculo(double l, double a, int power, double d, Tipo tipo1, Tipo tipo2, Tipo ataque) {
        double dano = (l * a * (double) power / d / 50) + 2;
        if(ataque == tipo1 || ataque == tipo2) {
            dano *= 1.5;
        }
        return dano;
    }

    /**
     * verifica se o valor do "ataque" e "defesa" obedecem duas condições
     * @param a ataque
     * @param d defesa
     * @return retorna o valor de ataque e defesa nas condições
     */
    Double[] verificaValor(double a, double d) {
        if(a < 0) {
            a = 0.0;
        } else if(a > 255) {
            a =  255.0;
        }
        if(d < 0) {
            d = 0.0;
        } else if(d > 255) {
            d =  255.0;
        }
        return new Double[] {a,d};
    }

    /**
     * Metódo responsável por fazer o calculo de dano com os multiplicadores de dano
     * @param ataque ataque a ser realizado
     * @param tipo1enemy tipo 1 do pokemon oponente
     * @param tipo2enemy tipo 2 do pokemon oponente
     * @param dano dano
     * @return retorna o dano
     * @throws FileNotFoundException arquivo não encontrado
     */
    double calculoFinal(Tipo ataque, Tipo tipo1enemy, Tipo tipo2enemy, double dano) throws FileNotFoundException {
        Double multiplicador = calculaMultiplicador(ataque, tipo1enemy, tipo2enemy);
        dano *= multiplicador;
        int r = ThreadLocalRandom.current().nextInt(217, 256);
        return (dano * r) / 255;
    }

    /**
     * metódo responsável por calcular os multiplicadores de dano
     * @param ataque tipo do ataque
     * @param tipo1enemy tipo 1 do pokemon oponente
     * @param tipo2enemy tipo 2 do pokemon oponente
     * @return retorna o multiplicador final
     * @throws FileNotFoundException arquivo não encontrado
     */
    private double calculaMultiplicador(Tipo ataque, Tipo tipo1enemy, Tipo tipo2enemy) throws FileNotFoundException {
        AtackMultiplier atackMultiplier = new AtackMultiplier();
        double ans;
        ans = atackMultiplier.getMultiplier(ataque, tipo1enemy);
        if(tipo2enemy != null) {
            ans *= atackMultiplier.getMultiplier(ataque, tipo2enemy);
        }
        return ans;
    }



}
