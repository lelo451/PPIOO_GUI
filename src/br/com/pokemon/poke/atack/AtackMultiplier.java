package br.com.pokemon.poke.atack;

import br.com.pokemon.poke.enuns.Tipo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe responsavel por obter o multiplicador de dano baseado no tipo de ataque
 */
public class AtackMultiplier {

    /**
     * Metódo responsável por calcular o multiplicador
     * @param tipoAtaque tipo de ataque
     * @param oponente tipo do oponente
     * @return retorna um multiplicador
     * @throws FileNotFoundException arquivo não encontrado
     */
    public double getMultiplier(Tipo tipoAtaque, Tipo oponente) throws FileNotFoundException {
        String[][] multiplier = new String[16][16];
        File file = new File("multiplicadoresAtaque.csv");
        int j = 1;
        Scanner sc = new Scanner(file);
        List<String> header = Stream.of(sc.nextLine().split(",")).collect(Collectors.toList());
        while (true) {
            try {
                List<String> header2 = Stream.of(sc.nextLine().split(",")).collect(Collectors.toList());
                for(int i = 1; i < header2.size(); i++) {
                    multiplier[j][i] = header2.get(i);
                }
                j++;
            } catch (Exception e) {
                break;
            }
        }
        return Double.parseDouble(multiplier[find(header, tipoAtaque.toString())][find(header, oponente.toString())]);
    }

    /**
     * Metódo responsável por localizar o tipo de ataque na matriz com os multiplicadores
     * @param array lista com todos os tipos possiveis
     * @param value tipo do ataque
     * @return
     */
    private int find(List<String> array, String value) {
        for(int i=0; i< array.size(); i++) {
            if (array.get(i).toLowerCase().equals(value.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

}
