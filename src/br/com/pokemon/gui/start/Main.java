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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        int quant = 0;
        List<Jogador> jogadores = new ArrayList<>();
        Inicializacoes.carregar_tabelas();
        Media sound = new Media(Paths.get("start.mp3").toUri().toString());
        AudioClip mediaPlayer = new AudioClip(sound.getSource());
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mediaPlayer.play();
        Scenario startScenario = new InicialScenario(jogadores, quant, mediaPlayer);
        Spawner.startScenario(startScenario, null);
    }
}