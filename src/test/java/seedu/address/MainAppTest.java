package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class MainAppTest {

    private static final String VALID_PERSON_JSON = """
            {
              "persons" : [ {
                "name" : "Janice",
                "phone" : "82345678",
                "gender" : "M",
                "dateOfBirth" : "22-04-2021",
                "email" : "janice@gmail.com",
                "type" : "Monthly",
                "remark" : "",
                "id" : "M029",
                "emergencyContact" : "85671234",
                "joinDate" : "01-03-2026",
                "expiryDate" : "01-04-2026"
              } ]
            }
            """;
    private static final String MISSING_EXPIRY_DATE_JSON = """
            {
              "persons" : [ {
                "name" : "Janice",
                "phone" : "82345678",
                "gender" : "M",
                "dateOfBirth" : "22-04-2021",
                "email" : "janice@gmail.com",
                "type" : "Monthly",
                "remark" : "",
                "id" : "M029",
                "emergencyContact" : "85671234",
                "joinDate" : "01-03-2026"
              } ]
            }
            """;
    private static final String DUPLICATE_PERSONS_JSON = """
            {
              "persons" : [ {
                "name" : "Janice",
                "phone" : "82345678",
                "gender" : "M",
                "dateOfBirth" : "22-04-2021",
                "email" : "janice@gmail.com",
                "type" : "Monthly",
                "remark" : "",
                "id" : "M029",
                "emergencyContact" : "85671234",
                "joinDate" : "01-03-2026",
                "expiryDate" : "01-04-2026"
              }, {
                "name" : "Janice",
                "phone" : "82345678",
                "gender" : "M",
                "dateOfBirth" : "22-04-2021",
                "email" : "janice@gmail.com",
                "type" : "Monthly",
                "remark" : "",
                "id" : "M029",
                "emergencyContact" : "85671234",
                "joinDate" : "01-03-2026",
                "expiryDate" : "01-04-2026"
              } ]
            }
            """;
    private static final String DUPLICATE_FIELD_JSON = """
            {
              "persons" : [ {
                "name" : "Janice",
                "phone" : "82345678",
                "gender" : "M",
                "dateOfBirth" : "22-04-2021",
                "email" : "janice@gmail.com",
                "type" : "Monthly",
                "remark" : "",
                "id" : "M029",
                "emergencyContact" : "85671234",
                "joinDate" : "01-03-2026",
                "expiryDate" : "01-04-2026",
                "expiryDate" : "01-05-2026"
              } ]
            }
            """;
    private static final String MISSING_REMARK_JSON = """
            {
              "persons" : [ {
                "name" : "Janice",
                "phone" : "82345678",
                "gender" : "M",
                "dateOfBirth" : "22-04-2021",
                "email" : "janice@gmail.com",
                "type" : "Monthly",
                "id" : "M029",
                "emergencyContact" : "85671234",
                "joinDate" : "01-03-2026",
                "expiryDate" : "01-04-2026"
              } ]
            }
            """;

    @TempDir
    public Path testFolder;

    @Test
    public void initModelManager_invalidField_replacesCorruptedFileWithEmptyAddressBook() throws Exception {
        for (Map.Entry<String, String> invalidField : invalidFieldValues().entrySet()) {
            Path addressBookFile = testFolder.resolve(invalidField.getKey() + "-addressbook.json");
            Path userPrefsFile = testFolder.resolve(invalidField.getKey() + "-userPrefs.json");
            Files.writeString(addressBookFile, VALID_PERSON_JSON.replace(
                    fieldNameValue(invalidField.getKey()), invalidField.getValue()));

            StorageManager storage = new StorageManager(
                    new JsonAddressBookStorage(addressBookFile),
                    new JsonUserPrefsStorage(userPrefsFile));

            Model model = new MainApp().initModelManager(storage, new UserPrefs());

            assertTrue(model.getAddressBook().getPersonList().isEmpty(), invalidField.getKey());
            assertTrue(storage.readAddressBook().isPresent(), invalidField.getKey());
            assertEquals(0, storage.readAddressBook().get().getPersonList().size(), invalidField.getKey());
        }
    }

    @Test
    public void initModelManager_missingExpiryDate_replacesCorruptedFileWithEmptyAddressBook() throws Exception {
        Path addressBookFile = testFolder.resolve("missing-expiry-addressbook.json");
        Path userPrefsFile = testFolder.resolve("missing-expiry-userPrefs.json");
        Files.writeString(addressBookFile, MISSING_EXPIRY_DATE_JSON);

        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(addressBookFile),
                new JsonUserPrefsStorage(userPrefsFile));

        MainApp mainApp = new MainApp();
        Model model = mainApp.initModelManager(storage, new UserPrefs());

        assertTrue(model.getAddressBook().getPersonList().isEmpty());
        assertTrue(storage.readAddressBook().isPresent());
        assertEquals(0, storage.readAddressBook().get().getPersonList().size());
        assertTrue(mainApp.getAddressBookLoadFailureMessage().contains("invalid"));
    }

    @Test
    public void initModelManager_duplicatePersons_replacesCorruptedFileWithEmptyAddressBook() throws Exception {
        Path addressBookFile = testFolder.resolve("duplicate-persons-addressbook.json");
        Path userPrefsFile = testFolder.resolve("duplicate-persons-userPrefs.json");
        Files.writeString(addressBookFile, DUPLICATE_PERSONS_JSON);

        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(addressBookFile),
                new JsonUserPrefsStorage(userPrefsFile));

        MainApp mainApp = new MainApp();
        Model model = mainApp.initModelManager(storage, new UserPrefs());

        assertTrue(model.getAddressBook().getPersonList().isEmpty());
        assertTrue(storage.readAddressBook().isPresent());
        assertEquals(0, storage.readAddressBook().get().getPersonList().size());
        assertTrue(mainApp.getAddressBookLoadFailureMessage().contains("invalid"));
    }

    @Test
    public void initModelManager_duplicateField_replacesCorruptedFileWithEmptyAddressBook() throws Exception {
        Path addressBookFile = testFolder.resolve("duplicate-field-addressbook.json");
        Path userPrefsFile = testFolder.resolve("duplicate-field-userPrefs.json");
        Files.writeString(addressBookFile, DUPLICATE_FIELD_JSON);

        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(addressBookFile),
                new JsonUserPrefsStorage(userPrefsFile));

        MainApp mainApp = new MainApp();
        Model model = mainApp.initModelManager(storage, new UserPrefs());

        assertTrue(model.getAddressBook().getPersonList().isEmpty());
        assertTrue(storage.readAddressBook().isPresent());
        assertEquals(0, storage.readAddressBook().get().getPersonList().size());
        assertTrue(mainApp.getAddressBookLoadFailureMessage().contains("invalid"));
    }

    @Test
    public void initModelManager_missingRemark_replacesCorruptedFileWithEmptyAddressBook() throws Exception {
        Path addressBookFile = testFolder.resolve("missing-remark-addressbook.json");
        Path userPrefsFile = testFolder.resolve("missing-remark-userPrefs.json");
        Files.writeString(addressBookFile, MISSING_REMARK_JSON);

        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(addressBookFile),
                new JsonUserPrefsStorage(userPrefsFile));

        MainApp mainApp = new MainApp();
        Model model = mainApp.initModelManager(storage, new UserPrefs());

        assertTrue(model.getAddressBook().getPersonList().isEmpty());
        assertTrue(storage.readAddressBook().isPresent());
        assertEquals(0, storage.readAddressBook().get().getPersonList().size());
        assertTrue(mainApp.getAddressBookLoadFailureMessage().contains("invalid"));
    }

    private static Map<String, String> invalidFieldValues() {
        return Map.ofEntries(
                Map.entry("name", "\"\""),
                Map.entry("phone", "\"123\""),
                Map.entry("gender", "\"X\""),
                Map.entry("dateOfBirth", "\"31-02-2021\""),
                Map.entry("email", "\"not-an-email\""),
                Map.entry("type", "\"Weekly\""),
                Map.entry("id", "\"BAD\""),
                Map.entry("emergencyContact", "\"123\""),
                Map.entry("joinDate", "\"not-a-date\""),
                Map.entry("expiryDate", "\"31-02-2026\""));
    }

    private static String fieldNameValue(String fieldName) {
        return switch (fieldName) {
        case "name" -> "\"name\" : \"Janice\"";
        case "phone" -> "\"phone\" : \"82345678\"";
        case "gender" -> "\"gender\" : \"M\"";
        case "dateOfBirth" -> "\"dateOfBirth\" : \"22-04-2021\"";
        case "email" -> "\"email\" : \"janice@gmail.com\"";
        case "type" -> "\"type\" : \"Monthly\"";
        case "id" -> "\"id\" : \"M029\"";
        case "emergencyContact" -> "\"emergencyContact\" : \"85671234\"";
        case "joinDate" -> "\"joinDate\" : \"01-03-2026\"";
        case "expiryDate" -> "\"expiryDate\" : \"01-04-2026\"";
        default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        };
    }
}
