package br.com.pokemon.gui.start;

import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.gui.scenario.InicialScenario;
import br.com.pokemon.player.Jogador;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
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
        String musicPath = "out/production/Pokemon_GUI/start.mp3";
        File soundFile = new File(musicPath);
        Media sound = new Media(Paths.get(musicPath).toUri().toString());
        AudioClip mediaPlayer = new AudioClip(sound.getSource());
        mediaPlayer.play();
    }
}