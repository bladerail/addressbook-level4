package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEB_LINK;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(ReadOnlyPerson person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(ReadOnlyPerson person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        person.getEmail().stream().forEach(
            s -> sb.append(PREFIX_EMAIL + s.value + " ")
        );
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        person.getWebLinks().stream().forEach(
            s -> sb.append(PREFIX_WEB_LINK + s.toStringWebLink() + " ")
        );

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetailsForEdit(ReadOnlyPerson person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        person.getEmail().stream().forEach(
            s -> sb.append(PREFIX_EMAIL + s.value + " ")
        );
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_ADD_TAG + s.tagName + " ")
        );
        person.getWebLinks().stream().forEach(
            s -> sb.append(PREFIX_WEB_LINK + s.toStringWebLink() + " ")
        );

        return sb.toString();
    }
}
