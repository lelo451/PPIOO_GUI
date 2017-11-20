package br.com.pokemon.gui.FXScenario;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Spawner {

    public static void startScenario(Scenario scenario, Controller parentController, Stage stage) {

        scenario.parentController = parentController;

        scenario.stage = stage;
        scenario.create();
        scenario.stage.show();

    }

    public static void startScenario(@NotNull Scenario scenario, @Nullable Controller parentController) {
        startScenario(scenario, parentController, null);
    }

    public static void startFeedbackScenario(@NotNull FeedbackScenario scenario, int requestCode, @Nullable Controller parentController, Stage stage, @NotNull FeedbackScenario.FeedbackListener listener) {
        scenario.listener = listener;
        scenario.parentController = parentController;
        scenario.requestCode = requestCode;
        scenario.stage = stage;
        scenario.create();
        scenario.stage.show();
    }

    public static void startFeedbackScenario(@NotNull FeedbackScenario scenario, int requestCode, @NotNull Controller parentController, @NotNull FeedbackScenario.FeedbackListener listener) {
        startFeedbackScenario(scenario, requestCode, parentController, null, listener);
    }

    public static void startFeedbackScenario(@NotNull FeedbackScenario scenario, int requestCode, @NotNull FeedbackScenario.FeedbackListener listener) {
        startFeedbackScenario(scenario, requestCode, null, listener);
    }

    public static void startFragment(@NotNull Fragment fragment, Controller parent, @NotNull Pane rootPane) {
        fragment.parentController = parent;
        rootPane.getChildren().clear();
        fragment.rootPane = rootPane;
        fragment.create();
    }

}
