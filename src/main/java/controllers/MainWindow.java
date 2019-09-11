package controllers;

import duke.Duke;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Label statusLabel;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    public MainWindow() {
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing duke.Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        statusLabel.setText("is typing...");
        String response = duke.getResponse(input);
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //If it did not sleep, it is fine as this is only to simulate the bot as if it is fetching the data from elsewhere
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> {
            dialogContainer.getChildren().add(DialogBox.getDukeDialog(response, dukeImage));
            statusLabel.setText("Online");
        });
        new Thread(sleeper).start();
        userInput.clear();
    }
}
