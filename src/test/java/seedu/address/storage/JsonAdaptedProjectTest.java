package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProject.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Address;
import seedu.address.model.project.Email;
import seedu.address.model.project.Name;
import seedu.address.model.project.Phone;

public class JsonAdaptedProjectTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTask> VALID_TASKS = BENSON.getTasks().stream()
            .map(JsonAdaptedTask::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validProjectDetails_returnsProject() throws Exception {
        JsonAdaptedProject project = new JsonAdaptedProject(BENSON);
        assertEquals(BENSON, project.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, VALID_TASKS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, VALID_TASKS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, VALID_TASKS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, VALID_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_TAGS, VALID_TASKS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        invalidTags, new ArrayList<>());
        assertThrows(IllegalValueException.class, project::toModelType);
    }

    // TODO: invalidTasks

}
