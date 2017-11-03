package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;
import java.util.logging.Filter;

import seedu.address.commons.util.StringUtil;

//@@author AngularJiaSheng
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name, Phone Address, email, tag or weblink} matches all of the keywords given.
 */
public class FilterKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public FilterKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        String combinedReferenceList = person.getAsOneString();

        return !keywords.isEmpty() && keywords.stream().allMatch(keyword
                -> StringUtil.containsWordIgnoreCase(combinedReferenceList, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((FilterKeywordsPredicate) other).keywords)); // state check
    }

}
