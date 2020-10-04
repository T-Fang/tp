package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Address;
import seedu.address.model.project.Name;
import seedu.address.model.project.Phone;
import seedu.address.model.project.Project;
import seedu.address.model.project.RepoUrl;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String name;
    private final String phone;
    private final String repoUrl;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedTask> occupied = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("repoUrl") String repoUrl, @JsonProperty("address") String address,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                              @JsonProperty("occupied") List<JsonAdaptedTask> occupied) {
        this.name = name;
        this.phone = phone;
        this.repoUrl = repoUrl;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (occupied != null) {
            this.occupied.addAll(occupied);
        }
    }

    /**
     * Converts a given {@code Project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        repoUrl = source.getRepoUrl().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        occupied.addAll(source.getTasks().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException {
        final List<Tag> projectTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            projectTags.add(tag.toModelType());
        }
        final List<Task> projectTasks = new ArrayList<>();
        for (JsonAdaptedTask task : occupied) {
            projectTasks.add(task.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (repoUrl == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, RepoUrl.class.getSimpleName()));
        }
        if (!RepoUrl.isValidRepoUrl(repoUrl)) {
            throw new IllegalValueException(RepoUrl.MESSAGE_CONSTRAINTS);
        }
        final RepoUrl modelRepoUrl = new RepoUrl(repoUrl);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(projectTags);
        final Set<Task> modelTasks = new HashSet<>(projectTasks);
        return new Project(modelName, modelPhone, modelRepoUrl, modelAddress,
                modelTags, new HashMap<>(), modelTasks);
    }

}
