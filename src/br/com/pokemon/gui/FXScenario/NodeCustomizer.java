package br.com.pokemon.gui.FXScenario;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

public class NodeCustomizer {

    static class Delta { double x, y; }

    private NodeCustomizer() {}

    public static void makeDraggable(Node node) {

        final Delta dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> {
            dragDelta.x = node.getScene().getWindow().getX() - mouseEvent.getScreenX();
            dragDelta.y = node.getScene().getWindow().getY() - mouseEvent.getScreenY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.getScene().getWindow().setX(mouseEvent.getScreenX() + dragDelta.x);
            node.getScene().getWindow().setY(mouseEvent.getScreenY() + dragDelta.y);
        });

    }

    public static void makeDraggableWhenNotMaximized(Node node) {
        final Delta dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> {
            if ( !((Stage)node.getScene().getWindow()).isMaximized() ) {
                dragDelta.x = node.getScene().getWindow().getX() - mouseEvent.getScreenX();
                dragDelta.y = node.getScene().getWindow().getY() - mouseEvent.getScreenY();
            }
        });

        node.setOnMouseDragged(mouseEvent -> {
            if ( !((Stage)node.getScene().getWindow()).isMaximized() ) {
                node.getScene().getWindow().setX(mouseEvent.getScreenX() + dragDelta.x);
                node.getScene().getWindow().setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });
    }

    public static void makeMovable(Node node) {

        final Delta dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = node.getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = node.getLayoutY() - mouseEvent.getSceneY();
            node.setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(mouseEvent -> node.setCursor(Cursor.HAND));
        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
            node.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
        });
        node.setOnMouseEntered(mouseEvent -> node.setCursor(Cursor.HAND));

    }

    public static void setUpMenuBar(@NotNull Scenario scenario, @NotNull Pane menuBar, Button btExit, Button btMaximize, Button btHide) {

        makeDraggableWhenNotMaximized(menuBar);

        if (btExit != null)
            btExit.setOnAction(event -> scenario.finish());

        if (btMaximize != null) {
            btMaximize.setOnAction(event -> scenario.getStage().setMaximized(!scenario.getStage().isMaximized()));
            menuBar.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    scenario.getStage().setMaximized(!scenario.getStage().isMaximized() && scenario.hasUndecoratedStyle);
                    if (!scenario.getStage().isMaximized()) {
                        ((Pane)scenario.getStage().getScene().getRoot()).setPadding(new Insets(20,20,20,20));
                        ((Pane)scenario.getStage().getScene().getRoot()).setBackground(new Background(new BackgroundFill(
                                Paint.valueOf("#FFFFFF"),
                                new CornerRadii(1f),
                                new Insets(20,20,20,20))));
                    } else {
                        ((Pane)scenario.getStage().getScene().getRoot()).setPadding(new Insets(0,0,0,0));
                        ((Pane)scenario.getStage().getScene().getRoot()).setBackground(new Background(new BackgroundFill(
                                Paint.valueOf("#FFFFFF"),
                                new CornerRadii(1f),
                                new Insets(0,0,0,0))));
                    }
                }
            });
        }

        if (btHide != null) {
            //btHide.setOnAction(event -> scenario.stage.);
        }
    }

    public static void setUpUndecoratedScenario(Scenario scenario) {
        scenario.getStage().initStyle(StageStyle.UNDECORATED);
        scenario.getStage().initStyle(StageStyle.TRANSPARENT);
        scenario.getStage().getScene().setFill(Color.TRANSPARENT);
        scenario.getStage().getScene().getRoot().setEffect(new DropShadow());
        ((Pane)scenario.getStage().getScene().getRoot()).setPadding(new Insets(20,20,20,20));
        ((Pane)scenario.getStage().getScene().getRoot()).setBackground(new Background(new BackgroundFill(
                Paint.valueOf("#FFFFFF"),
                new CornerRadii(1f),
                new Insets(20,20,20,20))));
        //ResizeHelper.addResizeListener(scenario.stage);
    }
}
