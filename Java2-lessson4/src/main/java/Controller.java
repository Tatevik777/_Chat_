import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField inputField;

    @FXML
    private ListView<String> listView;
    private final ObservableList<String> wordlist= FXCollections
            .observableArrayList("Привет", "Я","Тата");

    @FXML
    public void initialize() {
        listView.setItems(wordlist);
    }

    public void addWordToList(){
        String word=inputField.getText();
        if (word.isBlank()) {
            System.out.println("Пустышка!");

            return;
        }
        listView.getItems().add(word);
        inputField.clear();
    }
}

