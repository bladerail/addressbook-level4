package seedu.address;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.UserPerson;
import seedu.address.model.util.SampleUserPersonUtil;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.UserProfileStorage;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING_ADDRESSBOOK = TestUtil
            .getFilePathInSandboxFolder("sampleData.xml");
    //@@author bladerail
    public static final String SAVE_LOCATION_FOR_TESTING_USERPROFILE = TestUtil
            .getFilePathInSandboxFolder("sampleUserProfile.xml");
    //@@author
    public static final String APP_TITLE = "Test App";

    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected static final String ADDRESS_BOOK_NAME = "Test";
    protected Supplier<ReadOnlyAddressBook> initialDataSupplier = () -> null;
    protected String saveFileLocationAddressBook = SAVE_LOCATION_FOR_TESTING_ADDRESSBOOK;
    //@@author bladerail
    protected String saveFileLocationUserProfile = SAVE_LOCATION_FOR_TESTING_USERPROFILE;
    //@@author

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyAddressBook> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocationAddressBook = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableAddressBook(this.initialDataSupplier.get()),
                    this.saveFileLocationAddressBook);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setAddressBookFilePath(saveFileLocationAddressBook);
        //@@author bladerail
        userPrefs.setUserProfileFilePath(saveFileLocationUserProfile);
        //@@author
        userPrefs.setAddressBookName(ADDRESS_BOOK_NAME);
        return userPrefs;
    }

    //@@author bladerail
    @Override
    protected UserPerson initUserPerson(UserProfileStorage storage) {
        UserPerson userPerson = super.initUserPerson(storage);
        userPerson.setName(SampleUserPersonUtil.getDefaultSamplePerson().getName());
        userPerson.setPhone(SampleUserPersonUtil.getDefaultSamplePerson().getPhone());
        userPerson.setEmail(SampleUserPersonUtil.getDefaultSamplePerson().getEmail());
        userPerson.setAddress(SampleUserPersonUtil.getDefaultSamplePerson().getAddress());
        userPerson.setWebLinks(SampleUserPersonUtil.getDefaultSamplePerson().getWebLinks());

        return userPerson;

    }
    //@@author

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public AddressBook readStorageAddressBook() {
        try {
            return new AddressBook(storage.readAddressBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the AddressBook format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public String getStorageSaveLocation() {
        return storage.getAddressBookFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getAddressBook()), new UserPrefs(), new UserPerson());
        ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
