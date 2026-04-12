package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        Rectangle clip = new Rectangle(personListView.getHeight(), personListView.getWidth());
        clip.widthProperty().bind(personListView.widthProperty());
        clip.heightProperty().bind(personListView.heightProperty().add(20));
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        clip.setY(-20);
        personListView.setClip(clip);

        personListView.setOnKeyPressed(event -> {
            KeyCode mapped = null;
            switch (event.getCode()) {
            case LEFT:
                mapped = KeyCode.UP;
                break;
            case RIGHT:
                mapped = KeyCode.DOWN;
                break;
            default:
                break;
            }
            if (mapped != null) {
                Event.fireEvent(personListView, new KeyEvent(
                        KeyEvent.KEY_PRESSED, "", "", mapped,
                        event.isShiftDown(), event.isControlDown(),
                        event.isAltDown(), event.isMetaDown()));
                event.consume();
            }
        });
    }

    /**
     * Returns the {@code ListView} that displays the persons.
     *
     * @return the list view for persons
     */
    public ListView<Person> getListView() {
        return personListView;
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        PersonListViewCell() {
            addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                if (!isEmpty() && isSelected()) {
                    getListView().getSelectionModel().clearSelection();
                    event.consume();
                }
            });
        }

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Selects the given {@code Person} in the UI list and scrolls to it.
     *
     * @param person the person to select
     */
    public void selectPerson(Person person) {
        personListView.getSelectionModel().clearSelection();
        personListView.getSelectionModel().select(person);
        personListView.scrollTo(person);
    }

}
