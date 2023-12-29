package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Optional;

public class ATM extends Application {

    private Account account;

    public ATM(Account account) {
        this.account = account;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM-Interface");

        // Create a label for the title
        Label titleLabel = new Label("ATM");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        titleLabel.setUnderline(true);

        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button checkBalanceButton = new Button("Check Balance");
        Button exitButton = new Button("Exit");

        withdrawButton.setOnAction(e -> showWithdrawDialog());
        depositButton.setOnAction(e -> showDepositDialog());
        checkBalanceButton.setOnAction(e -> showBalanceDialog());
        exitButton.setOnAction(e -> primaryStage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(withdrawButton, depositButton);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(titleLabel, buttonBox, checkBalanceButton, exitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void showWithdrawDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdraw");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the amount to withdraw:");

        processTransaction(dialog);
    }

    private void showDepositDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the amount to deposit:");

        processTransaction(dialog);
    }

    private void showBalanceDialog() {
        showAlert("Your current balance is: ₹" + account.getBalance());
    }

    private void processTransaction(TextInputDialog dialog) {
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            try {
                double transactionAmount = Double.parseDouble(amount);
                if (dialog.getTitle().equals("Withdraw") && account.getBalance() < transactionAmount) {
                    showAlert("Insufficient funds.");
                } else {
                    if (dialog.getTitle().equals("Withdraw")) {
                        account.withdraw(transactionAmount);
                    } else {
                        account.deposit(transactionAmount);
                    }
                    showAlert("Transaction successful. Your balance is now: ₹" + account.getBalance());
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter a valid number.");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATM");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
