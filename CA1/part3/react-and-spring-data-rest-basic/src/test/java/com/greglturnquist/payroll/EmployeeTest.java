package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testValidEmployee() {
        Employee employee = new Employee("John", "Doe", "A dedicated employee", 5,"frodolovesring@gmail.com","Adventure Freelancer");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("A dedicated employee", employee.getDescription());
        assertEquals(5, employee.getJobYears());
        assertEquals("frodolovesring@gmail.com", employee.getEmail());
        assertEquals("Adventure Freelancer", employee.getJobTitle());
    }

    @Test
    void testFirstNameNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(null, "Doe", "A dedicated employee", 5, "frodolovesring@gmail.com","Adventure Freelancer");
        });
        assertEquals("First name must be a non-empty string.", exception.getMessage());
    }

    @Test
    void testFirstNameBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", "Doe", "A dedicated employee", 5, "frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("First name must be a non-empty string.", exception.getMessage());
    }

    @Test
    void testFirstNameInvalidChars() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("john", "Doe", "A dedicated employee", 5, " frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("First name must start with a capital letter and contain only letters.", exception.getMessage());
    }

    @Test
    void testFirstNameLength() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("J", "Doe", "A dedicated employee", 5, "frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("First name must be between 2 and 50 characters.", exception.getMessage());
    }

    @Test
    void testLastNameNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", null, "A dedicated employee", 5, "frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Last name must be a non-empty string.", exception.getMessage());
    }

    @Test
    void testLastNameBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "", "A dedicated employee", 5, " frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Last name must be a non-empty string.", exception.getMessage());
    }

    @Test
    void testLastNameInvalidChars() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "doe", "A dedicated employee", 5, "frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Last name must start with a capital letter and contain only letters.", exception.getMessage());
    }

    @Test
    void testLastNameLength() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "D", "A dedicated employee", 5, " frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Last name must be between 2 and 50 characters.", exception.getMessage());
    }

    @Test
    void testDescriptionNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", null, 5, " frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Description cannot be null or blank.", exception.getMessage());
    }

    @Test
    void testDescriptionBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", "", 5, " frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Description cannot be null or blank.", exception.getMessage());
    }

    @Test
    void testDescriptionLength() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", "“Nature provides a sense of peace and tranquility, offering countless moments of beauty to those who take the time to appreciate it. From the towering trees in the forest to the vastness of the oceans, nature’s diversity is a reminder of the complexity and interconnectedness of life. The changing seasons bring a sense of renewal and growth, while also showcasing the delicate balance of the environment." , 5, "frodolovesring@gmail.com", "Adventure Freelancer"); // 101 chars
        });
        assertEquals("Description must not exceed 100 characters.", exception.getMessage());
    }

    @Test
    void testJobYearsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", "A dedicated employee", -1, "frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Job years cannot be negative.", exception.getMessage());
    }

    @Test
    void testJobYearsExceedsMax() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", "A dedicated employee", 51, "frodolovesring@gmail.com", "Adventure Freelancer");
        });
        assertEquals("Job years must not exceed 50 years.", exception.getMessage());
    }

    @Test
    void testJobYearsValid() {
        Employee employee = new Employee("John", "Doe", "A dedicated employee", 25, "frodolovesring@gmail.com", "Adventure Freelancer");
        assertNotNull(employee);
        assertEquals(25, employee.getJobYears());
    }

    @Test
    void testValidEmail() {
        Employee employee = new Employee("John", "Doe", "A dedicated employee", 25, "frodolovesring@gmail.com", "Adventure Freelancer");
        assertNotNull(employee);
        assertEquals("frodolovesring@gmail.com",employee.getEmail());
    }

    @Test
    void testEmailNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", "A dedicated employee", 33, null, "Adventure Freelancer");
        });
        assertEquals("Email cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testEmailEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", "A dedicated employee", 33, " ", "Adventure Freelancer");
        });
        assertEquals("Email cannot be null or empty.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            // Valid emails (expected = true)
            "'user@example.com', true",
            "'user.name@domain.io', true",
            "'user+test@gmail.com', true",

            // Invalid emails (expected = false)
            "'@example.com', false",
            "'user@@example.com', false",
            "'user@example.c', false",
            "'user@example.123', false",
            "'   ', false",
            "'', false",
            "null, false"
    })
    void testValidateEmail(String email, boolean isValid) {
        // Act & Assert: Depending on validity, check if the email is properly validated or throws an exception
        if (isValid) {
            // When valid, check that no exception is thrown
            assertDoesNotThrow(() -> new Employee("John", "Doe", "A dedicated employee", 25, email, "Adventure Freelancer"));
        } else {
            // When invalid, expect an exception with the proper error message
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new Employee("John", "Doe", "A dedicated employee", 25, email, "Adventure Freelancer");
            });
            // Adjust the expected message to match the validation for null/empty emails
            String expectedMessage = email == null || email.trim().isEmpty() ? "Email cannot be null or empty." : "Invalid email format: " + email;
            assertEquals(expectedMessage, exception.getMessage());
        }
    }


}