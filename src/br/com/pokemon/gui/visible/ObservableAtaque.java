package br.com.pokemon.gui.visible;

import br.com.pokemon.poke.atack.Ataque;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class ObservableAtaque {

    private Ataque ataque;

    public ObservableAtaque(Ataque ataque) {
        this.ataque = ataque;
    }

    public Ataque getAtaque() {
        return ataque;
    }

    public StringProperty ataqueProperty() {
        return new SimpleStringProperty(ataque.getNome().replace("_", " "));
    }

    public static List<ObservableAtaque> observableAtaquesList(List<Ataque> list) {
        List<ObservableAtaque> observableAtaques = new ArrayList<>();
        for(Ataque a : list)
            observableAtaques.add(new ObservableAtaque(a));
        return observableAtaques;
    }
}
