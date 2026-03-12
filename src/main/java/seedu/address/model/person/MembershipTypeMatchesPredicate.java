package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code MembershipType} matches the given value (case-insensitive).
 */
public class MembershipTypeMatchesPredicate implements Predicate<Person> {
    private final String membershipType;

    public MembershipTypeMatchesPredicate(String membershipType) {
        this.membershipType = membershipType;
    }

    @Override
    public boolean test(Person person) {
        return person.getMembershipType().value.equalsIgnoreCase(membershipType);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MembershipTypeMatchesPredicate)) {
            return false;
        }

        MembershipTypeMatchesPredicate otherMembershipTypeMatchesPredicate = (MembershipTypeMatchesPredicate) other;
        return membershipType.equals(otherMembershipTypeMatchesPredicate.membershipType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("membershipType", membershipType).toString();
    }
}
