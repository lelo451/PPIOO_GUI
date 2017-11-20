package br.com.pokemon.gui.visible;

import br.com.pokemon.poke.Especie;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class ObservableEspecie {

    private Especie especie;

    public ObservableEspecie(Especie especie) {
        this.especie = especie;
    }

    public Especie getEspecie() {
        return especie;
    }

    public StringProperty especieProperty() {
        return new SimpleStringProperty(especie.getNome());
    }

    public static List<ObservableEspecie> observableEspeciesList(List<Especie> list) {
        List<ObservableEspecie> observableEspecies = new ArrayList<>();
        for(Especie e : list)
            observableEspecies.add(new ObservableEspecie(e));
        return observableEspecies;
    }
}
