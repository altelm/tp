package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Gender} matches the given value (case-insensitive).
 */
public class GenderMatchesPredicate implements Predicate<Person> {
    private final String gender;

    public GenderMatchesPredicate(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean test(Person person) {
        return person.getGender().gender.equalsIgnoreCase(gender);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GenderMatchesPredicate)) {
            return false;
        }

        GenderMatchesPredicate otherGenderMatchesPredicate = (GenderMatchesPredicate) other;
        return gender.equals(otherGenderMatchesPredicate.gender);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("gender", gender).toString();
    }
}
