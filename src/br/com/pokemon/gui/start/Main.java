package br.com.pokemon.gui.start;

import br.com.pokemon.db.Base;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.gui.scenario.InicialScenario;
import br.com.pokemon.player.Jogador;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal responsável pela execução do programa
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        int quant = 0;
        List<Jogador> jogadores = new ArrayList<>();
        Base b = new Base();
        b.criarBase();
        Media sound = new Media(Paths.get("start.mp3").toUri().toString());
        AudioClip mediaPlayer = new AudioClip(sound.getSource());
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mediaPlayer.play();
        Scenario startScenario = new InicialScenario(jogadores, quant, mediaPlayer);
        Spawner.startScenario(startScenario, null);
    }
}