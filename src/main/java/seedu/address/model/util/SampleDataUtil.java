package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.GenerateMemberIds;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MemberStatus;
import seedu.address.model.person.MembershipJoinDate;
import seedu.address.model.person.MembershipType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(GenerateMemberIds.generateNextId(), new Name("Alex Yeoh"), new Phone("87438807"), 
                new Gender("M"), new DateOfBirth("02-02-2002"), new MemberStatus("valid"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new MembershipType("annual"), new MembershipJoinDate("11-Mar-2026"), getTagSet("friends")),
            new Person(GenerateMemberIds.generateNextId(), new Name("Bernice Yu"), new Phone("99272758"),
                new Gender("M"), new DateOfBirth("02-02-2002"), new MemberStatus("valid"),
                new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new MembershipType("monthly"), new MembershipJoinDate("11-Mar-2026"),
                getTagSet("colleagues", "friends")),
            new Person(GenerateMemberIds.generateNextId(), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Gender("F"), new DateOfBirth("02-02-2002"), new MemberStatus("valid"),
                new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new MembershipType("Monthly"), new MembershipJoinDate("11-Mar-2026"), getTagSet("neighbours")),
            new Person(GenerateMemberIds.generateNextId(), new Name("David Li"), new Phone("91031282"),
                new Gender("M"), new DateOfBirth("02-02-2002"), new MemberStatus("valid"),
                new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new MembershipType("Annual"), new MembershipJoinDate("11-Mar-2026"), getTagSet("family")),
            new Person(GenerateMemberIds.generateNextId(), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Gender("M"), new DateOfBirth("02-02-2002"), new MemberStatus("valid"),
                new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                new MembershipType("Annual"), new MembershipJoinDate("11-Mar-2026"), getTagSet("classmates")),
            new Person(GenerateMemberIds.generateNextId(), new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Gender("M"), new DateOfBirth("02-02-2002"), new MemberStatus("valid"),
                new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new MembershipType("Monthly"), new MembershipJoinDate("11-Mar-2026"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
