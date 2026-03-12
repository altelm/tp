package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Email} contains the given substring (case-insensitive).
 */
public class EmailContainsPredicate implements Predicate<Person> {
    private final String email;

    public EmailContainsPredicate(String email) {
        this.email = email;
    }

    @Override
    public boolean test(Person person) {
        return person.getEmail().value.toLowerCase().contains(email.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsPredicate)) {
            return false;
        }

        EmailContainsPredicate otherEmailContainsPredicate = (EmailContainsPredicate) other;
        return email.equals(otherEmailContainsPredicate.email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("email", email).toString();
    }
}
