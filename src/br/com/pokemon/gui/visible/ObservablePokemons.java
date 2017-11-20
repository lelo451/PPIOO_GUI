package br.com.pokemon.gui.visible;

import br.com.pokemon.poke.Pokemon;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class ObservablePokemons {

    private Pokemon pokemon;

    public ObservablePokemons(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public StringProperty pokemonProperty() {
        return new SimpleStringProperty(pokemon.getEspecie().getNome());
    }

    public static List<ObservablePokemons> observablePokemonsList(List<Pokemon> list) {
        List<ObservablePokemons> observablePokemons = new ArrayList<>();
        for(Pokemon p : list)
            observablePokemons.add(new ObservablePokemons(p));
        return observablePokemons;
    }
}
