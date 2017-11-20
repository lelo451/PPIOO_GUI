package br.com.pokemon.gui.start;

import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.gui.scenario.InicialScenario;
import br.com.pokemon.player.Jogador;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        int quant = 0;
        List<Jogador> jogadores = new ArrayList<>();
        Inicializacoes.carregar_tabelas();
        Scenario startScenario = new InicialScenario(jogadores, quant);
        Spawner.startScenario(startScenario, null);

    }
}
