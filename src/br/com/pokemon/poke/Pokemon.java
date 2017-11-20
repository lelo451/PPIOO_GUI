package br.com.pokemon.poke;

import br.com.pokemon.poke.atack.Ataque;
import br.com.pokemon.poke.enuns.Status;

import java.util.List;

public class Pokemon {

    private int level;
    private double hpAtual;
    private double hpMax;
    private double atk;
    private double def;
    private double spe;
    private double spd;
    private int modifierAccuracy;
    private int modifierEvasion;
    private int modifierAtk;
    private int modifierDef;
    private int modifierSpe;
    private int modifierSpd;
    private boolean confusion;
    private boolean flinch;
    private List<Ataque> ataques;
    private Status status;
    private Especie especie;

    public Pokemon(int level, List<Ataque> ataques, Especie especie) {
        this.level = level;
        this.hpAtual = especie.calcularAtributo(level, especie.getBaseHp(), true);
        this.hpMax = especie.calcularAtributo(level, especie.getBaseHp(), true);
        this.atk = especie.calcularAtributo(level, especie.getBaseAtk(), false);
        this.def = especie.calcularAtributo(level, especie.getBaseDef(), false);
        this.spe = especie.calcularAtributo(level, especie.getBaseSpe(), false);
        this.spd = especie.calcularAtributo(level, especie.getBaseSpd(), false);
        this.modifierAccuracy = 0;
        this.modifierEvasion = 0;
        this.modifierAtk = 0;
        this.modifierDef = 0;
        this.modifierSpe = 0;
        this.modifierSpd = 0;
        this.confusion = false;
        this.flinch = false;
        this.ataques = ataques;
        this.status = Status.OK;
        this.especie = especie;
    }

    public int getLevel() {
        return level;
    }

    public double getHpAtual() {
        return hpAtual;
    }

    public void setHpAtual(double hpAtual) {
        this.hpAtual = hpAtual;
    }

    public double getHpMax() {
        return hpMax;
    }

    public double getAtk() {
        return atk;
    }

    public double getDef() {
        return def;
    }

    public double getSpe() {
        return spe;
    }

    public double getSpd() {
        return spd;
    }

    public int getModifierAccuracy() {
        return modifierAccuracy;
    }

    public void setModifierAccuracy(int modifierAccuracy) {
        this.modifierAccuracy = modifierAccuracy;
    }

    public int getModifierEvasion() {
        return modifierEvasion;
    }

    public void setModifierEvasion(int modifierEvasion) {
        this.modifierEvasion = modifierEvasion;
    }

    public int getModifierAtk() {
        return modifierAtk;
    }

    public void setModifierAtk(int modifierAtk) {
        this.modifierAtk = modifierAtk;
    }

    public int getModifierDef() {
        return modifierDef;
    }

    public void setModifierDef(int modifierDef) {
        this.modifierDef = modifierDef;
    }

    public int getModifierSpe() {
        return modifierSpe;
    }

    public void setModifierSpe(int modifierSpe) {
        this.modifierSpe = modifierSpe;
    }

    public int getModifierSpd() {
        return modifierSpd;
    }

    public void setModifierSpd(int modifierSpd) {
        this.modifierSpd = modifierSpd;
    }

    public boolean isConfusion() {
        return confusion;
    }

    public void setConfusion(boolean confusion) {
        this.confusion = confusion;
    }

    public boolean isFlinch() {
        return flinch;
    }

    public void setFlinch(boolean flinch) {
        this.flinch = flinch;
    }

    public List<Ataque> getAtaques() {
        return ataques;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Especie getEspecie() {
        return especie;
    }

    public double valorAtributo(String atributo) {
        switch (atributo) {
            case "ATK":
                return atk * ((Math.max(2, 2 + Ataque.getModifier(modifierAtk)) / Math.max(2, 2 - Ataque.getModifier(modifierAtk))) * 0.01);
            case "DEF":
                return def * ((Math.max(2, 2 + Ataque.getModifier(modifierDef)) / Math.max(2, 2 - Ataque.getModifier(modifierDef))) * 0.01);
            case "SPE":
                return spe * ((Math.max(2, 2 + Ataque.getModifier(modifierSpe)) / Math.max(2, 2 - Ataque.getModifier(modifierSpe))) * 0.01);
            case "SPD":
                return spd * ((Math.max(2, 2 + Ataque.getModifier(modifierSpd)) / Math.max(2, 2 - Ataque.getModifier(modifierSpd))) * 0.01);
        }
        return 0;
    }
}
