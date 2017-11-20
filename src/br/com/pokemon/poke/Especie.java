package br.com.pokemon.poke;

import br.com.pokemon.db.Base;
import br.com.pokemon.poke.enuns.Tipo;

import java.util.List;

public class Especie {

    private int id;
    private String nome;
    private double baseHp;
    private double baseAtk;
    private double baseDef;
    private double baseSpe;
    private double baseSpd;
    private Tipo tipo1;
    private Tipo tipo2;

    public Especie(int id) {
        Base b = new Base();
        List<String> poke = b.getData(id, true);
        this.id = id;
        this.nome = poke.get(1);
        this.baseHp = Double.parseDouble(poke.get(4));
        this.baseAtk = Double.parseDouble(poke.get(5));
        this.baseDef = Double.parseDouble(poke.get(6));
        this.baseSpe = Double.parseDouble(poke.get(7));
        this.baseSpd = Double.parseDouble(poke.get(8));
        this.tipo1 = Tipo.valueOf(poke.get(2).toUpperCase());
        if(!poke.get(3).isEmpty()) {
            this.tipo2 = Tipo.valueOf(poke.get(3).toUpperCase());
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getBaseHp() {
        return baseHp;
    }

    public double getBaseAtk() {
        return baseAtk;
    }

    public double getBaseDef() {
        return baseDef;
    }

    public double getBaseSpe() {
        return baseSpe;
    }

    public double getBaseSpd() {
        return baseSpd;
    }

    public Tipo getTipo1() {
        return tipo1;
    }

    public Tipo getTipo2() {
        return tipo2;
    }

    public double calcularAtributo(int level, double base, boolean hp) {
        if (hp) {
            return 2 * baseHp * level / 100 + level + 10;
        }else {
            return 2 * base * level / 100 + 5;
        }
    }
}