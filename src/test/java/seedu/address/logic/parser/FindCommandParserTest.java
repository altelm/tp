package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsPredicate;
import seedu.address.model.person.GenderMatchesPredicate;
import seedu.address.model.person.MembershipTypeMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixes_throwsParseException() {
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new PhoneContainsPredicate("91234567"));
        assertParseSuccess(parser, " p/91234567", expectedFindCommand);
    }

    @Test
    public void parse_validEmailArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new EmailContainsPredicate("alice@example.com"));
        assertParseSuccess(parser, " e/alice@example.com", expectedFindCommand);
    }

    @Test
    public void parse_validAddressArg_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Jurong", "West")));
        assertParseSuccess(parser, " a/Jurong West", expectedFindCommand);
    }

    @Test
    public void parse_validMembershipTypeArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new MembershipTypeMatchesPredicate("annual"));
        assertParseSuccess(parser, " m/annual", expectedFindCommand);
    }

    @Test
    public void parse_validGenderArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new GenderMatchesPredicate("F"));
        assertParseSuccess(parser, " g/F", expectedFindCommand);
    }

    @Test
    public void parse_multipleFields_doesNotThrow() {
        // Combined n/ and g/ — parse should succeed without throwing
        try {
            parser.parse(" n/Alice g/F");
        } catch (Exception e) {
            throw new AssertionError("Expected no exception but got: " + e.getMessage());
        }
    }
}
