package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} contains the given substring.
 */
public class PhoneContainsPredicate implements Predicate<Person> {
    private final String phone;

    public PhoneContainsPredicate(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean test(Person person) {
        return person.getPhone().value.contains(phone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsPredicate)) {
            return false;
        }

        PhoneContainsPredicate otherPhoneContainsPredicate = (PhoneContainsPredicate) other;
        return phone.equals(otherPhoneContainsPredicate.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phone", phone).toString();
    }
}
