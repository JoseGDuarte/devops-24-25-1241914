# Class Assignment 1: Technical Report

**Author:** José Manuel da Silva Gonçalves Pinheiro Duarte (1241914)<br>
**Programme:** SWitCH DEV<br>
**Course:** DevOps</br>

### Table of Contents

- [Introduction](#introduction)
- [Part 1](#part-1)
    - [Part 1.1](#part-1-1)
    - [Part 1.2](#part-1-2)
    - [Part 1.3](#part-1-3)
- [Part 2](#part-2)
- [Part 3](#part-3)
    - [Part 3.1](#part-3-1)
    - [Part 3.2](#part-3-2)
- [Conclusion](#conclusion)

### Introduction

This report details the Version Control with Git assignment for the DevOps discipline. The assignment is divided into three 
parts: Part 1, where we used a  basic version control without branches and was added a new parameter, and Part 2, 
implementing branching for new features and bug fixes. Part 3 covered a transformation of the basic tutorial, part 1, to Gradle.
In the results section will be detailed the evolution of the project  after the implementation of the new functionalities 
and resolving the issues.

**Part 1** was divided into three parts. The first, is intended to work without branches, the second applies the use
of branches and the third explores an alternative solution to Git.

**Part 2** follows the topic of using build tools with Gradle.

**Part 3** aims to convert the basic version of the Tutorial application to Gradle and an alternative to Gradle.

---
##  Part 1

### The Setup
Due to the nature of this project some configurations and changes needed to be made so that the application runs smoothly.
I will detail them and some solutions to problems I encountered.

- **Cloning the tutorial**

We were given access to a repository containing the Tutorial React.js and Spring Data REST application. This allowed us
to clone it and create a local copy using the following command:

```bash
git clone https://github.com/spring-guides/tut-react-and-spring-data-rest
```

- **Creating my repository**

I then created my own repository *devops-24-25-1241914* in GitHub.
Once my repository was created, I cloned it into the right folder on my computer using the command:<br>
```bash
git clone <repository-URL> "/Users/joseduarte/Documents/GitHub/DevOps"
```

- **Copying the 'basic' folder**

To be able to run the application, I had to copy the basic folder inside the first repository to my own.
I created the folder *CA1* and inside, the folder *part1*.

```bash
cp -r "/Users/joseduarte/Documents/GitHub/DevOps/tut-react-and-spring-data-rest/basic" "/Users/joseduarte/Documents/GitHub/DevOps/devops-24-25-1241902/CA1/part1"
 ```

- **Creating the .gitignore file**

Next, a .gitignore file was needed to showcase the list of files and directories to ignore when making a commit. I used 
this[ website](https://www.toptal.com/developers/gitignore/) to generate this file and then added it to my
repository.

- **Executing the application**

With everything set, I am now able to run the application through my repository whenever I need it. To do this, I need
to move to the *basic* folder and run the command ``/mvnw spring-boot:run``
After this, I need to enter [this url](http://localhost:8080/) to check the application.
In this part I had some problems building and running the project. These issues were regarding the node and npm versions and to be able
to run the application I needed to alter them to node version v16.20.0 and npm version 8.19.4.

- **Structuring and organising the project**

Throughout this project I will be using **Issues** feature in GitHub as a way to keep track of my tasks. These
issues will be linked to my commits. The few first commits were not linked to issues since the implementation of the issues were used
only a bit further in the project.
As seen by the following example, I created an issue regarding one of the main tasks for this project named *"Create an email field and respective 
tests - issue #1 "* and the message contained a direct reference to the number of that issue.

~~~bash
git commit -m "Create an email field and respective tests - closes issue #1 "
~~~

**Tags** will also play a very important part in the making of this project. They are used to mark specific points in
the repository's history. Using the following commands allow us to send add a tag to our project and then push it to the
remote repository:

~~~bash
git tag v1.0.0
git push origin v1.0.0
~~~

- **Starting**
  My first commit to the GitHub repository (*'First Commit'*) was meant to officially establish the version history of my assignments
  in a remote location and ,in that way, ensure an organised start. For this I used the command ``git push -u origin main``.

### Part 1-1

### Goals & Requirements
- Use of *tags* to keep track of the versions of the application;
- Develop a new feature to add a new field to the application (*Job Years*);
- Add unit tests for testing the creation of Employees and the validation of their attributes;
- Practice debugging both the server and the client.
- Use meaningful commits and tags.

### Development
As mentioned before, I began by copying the basic folder from the given repository and commit it. 
For this project, I am meant to follow the major.minor.revision pattern so this was tagged as **v1.1.0**. 

Regarding the development of the new feature, we were tasked with adding a new field *Job Years* to the Employee entity.

I will go through each class, detailing the changes and additions made, including new methods, attributes, and tests, to fulfil the project 
requirements.

- **Employee.java:**

- As requested, I added the jobYears field along with its getter and setter methods. I also 
  included validations in the setter methods. This was done to prevent invalid values, from being assigned and to avoid 
  errors. Both methods for jobYears can be found below and the setter method illustrates the updates
  made to the other setter methods:

~~~java
	public int getJobYears() {return jobYears;}

    public void setJobYears(int jobYears) {
  validateJobYears(jobYears);
  this.jobYears = jobYears;
}
~~~

It was also necessary to validate all attributes: First Name, Last Name, Description and Job Years. I made the same restrictions to First Name and Last Name since the 
principles were the same. In this case the string could not be empty or null, and also it should start with a capital letter and only contains letters. I also made the string 
have a size between 2 and 50 characters. Description followed the same principle with minor changes, it should not be bigger than 100 characters and can contain different characters.
JobYears could not be negative and can be bigger than 50.

~~~java
	private void validateFirstName(String firstName) throws IllegalArgumentException {
  if (firstName == null || firstName.trim().isEmpty()) {
    throw new IllegalArgumentException("First name must be a non-empty string.");
  }
  if (!firstName.matches("^[A-Z][a-z]*$")) {
    throw new IllegalArgumentException("First name must start with a capital letter and contain only letters.");
  }
  if (firstName.length() < 2 || firstName.length() > 50) {
    throw new IllegalArgumentException("First name must be between 2 and 50 characters.");
  }
}

private void validateLastName(String lastName) throws IllegalArgumentException {
  if (lastName == null || lastName.trim().isEmpty()) {
    throw new IllegalArgumentException("Last name must be a non-empty string.");
  }
  if (!lastName.matches("^[A-Z][a-z]*$")) {
    throw new IllegalArgumentException("Last name must start with a capital letter and contain only letters.");
  }
  if (lastName.length() < 2 || lastName.length() > 50) {
    throw new IllegalArgumentException("Last name must be between 2 and 50 characters.");
  }
}

private void validateDescription(String description) throws IllegalArgumentException {
  if (description == null || description.trim().isEmpty()) {
    throw new IllegalArgumentException("Description cannot be null or blank.");
  }
  if (description.length() > 100) {
    throw new IllegalArgumentException("Description must not exceed 100 characters.");
  }
}

private void validateJobYears(int jobYears) throws IllegalArgumentException {
  if (jobYears < 0) {
    throw new IllegalArgumentException("Job years cannot be negative.");
  }
  if (jobYears > 50) {
    throw new IllegalArgumentException("Job years must not exceed 50 years.");
  }
}
~~~ 
Finally, I added validation to the Employee constructor by calling the existing validation methods for each attribute. This makes sure that any invalid data is caught when creating
an Employee.

~~~java 
	public Employee(String firstName, String lastName, String description, int jobYears, String email, String jobTitle) {

  validateFirstName(firstName);
  validateLastName(lastName);
  validateDescription(description);
  validateJobYears(jobYears);

  this.firstName = firstName;
  this.lastName = lastName;
  this.description = description;
  this.jobYears = jobYears;
}
~~~

- **EmployeeTest.java**:

This class was added to the created test directory, as there were no tests for the Employee class.
The tests focus on verifying the functionality of the Employee constructor. 

~~~java
class EmployeeTest {

    @Test
    void testValidEmployee() {
        Employee employee = new Employee("Frodo", "Baggins", "A dedicated employee", 5);
        assertNotNull(employee);
        assertEquals("Frodo", employee.getFirstName());
        assertEquals("Baggins", employee.getLastName());
        assertEquals("A dedicated employee", employee.getDescription());
        assertEquals(5, employee.getJobYears());
    }
}
~~~ 

Each attribute had extensive tests made ensuring no invalidate values were accepted. The following tests will show an example of all tests were made.

~~~java
    @Test
void testFirstNameNull() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee(null, "Baggins", "A dedicated employee", 5);
  });
  assertEquals("First name must be a non-empty string.", exception.getMessage());
}

@Test
void testFirstNameBlank() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee("", "Baggins", "A dedicated employee", 5);
  });
  assertEquals("First name must be a non-empty string.", exception.getMessage());
}

@Test
void testFirstNameInvalidChars() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee("frodo", "Baggins", "A dedicated employee", 5, "Adventure Freelancer");
  });
  assertEquals("First name must start with a capital letter and contain only letters.", exception.getMessage());
}

@Test
void testFirstNameLength() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee("F", "Baggins", "A dedicated employee", 5);
  });
  assertEquals("First name must be between 2 and 50 characters.", exception.getMessage());
}
~~~ 


The following examples demonstrate tests for the setter methods of the Employee class attributes.For each attribute, I’ve included two tests:
one that uses valid values to ensure the setter updates the attribute correctly, and another that uses invalid values to verify that the appropriate exception is thrown.

Below is an example of a test for setting a valid value for job years, followed by a test that verifies invalid job years trigger the appropriate exception.

~~~java
   @Test
void testJobYearsNegative() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee("Frodo", "Baggins", "A dedicated employee", -1);
  });
  assertEquals("Job years cannot be negative.", exception.getMessage());
}

@Test
void testJobYearsExceedsMax() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee("Frodo", "Baggins", "A dedicated employee", 51);
  });
  assertEquals("Job years must not exceed 50 years.", exception.getMessage());
}

@Test
void testJobYearsValid() {
  Employee employee = new Employee("Frodo", "Baggins", "A dedicated employee", 25);
  assertNotNull(employee);
  assertEquals(25, employee.getJobYears());
}
~~~ 

- **DatabaseLoader.java:**

The class was modified to incorporate the jobYears field when saving sample employee data to the database. This update ensures that the employee records now include the
number of years each employee has worked, enabling the application to showcase this feature immediately upon startup.

~~~java
	@Override
	public void run(String... strings) throws Exception { // <4>
		this.repository.save(new Employee("Frodo", "Baggins", "A dedicated employee", 25));
	}
~~~

- **app.js:**

The React components were updated to include the new jobYears field, allowing users to view the number of years an employee has worked. The EmployeeList
and Employee components now display this information in a dedicated 'Job Years' column within the employee table, ensuring that the feature is visible right from the start.

~~~javascript
class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Description</th>
                                        <th>Job Years</th>
				</tr>
				{employees}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]

// tag::employee[]
class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
                                <td>{this.props.employee.jobYears}</td>>

			</tr>
		)
	}
}
~~~

**Debugging**

After making sure the jobYears field was integrated, I ran the app using ``./mvnw spring-boot:run `` to test it live at `` http://localhost:8080/``  This helped me
check that everything was working as expected in the app and that the feature fit in smoothly with the rest of the functionality. I also did a quick code review to
ensure the data was being handled correctly on the server side and that jobYears was displaying properly on the client side.

**Finishing up**

Once everything was completed, I needed to commit these changes to the remote repository.

~~~bash
git add .
git commit -m "Added jobYears and respective tests"
git push
~~~
I also needed to add a new tag to indicate that this part was completed.

~~~bash
git tag v1.2.0 -m "Added jobYears and respective tests"
git push origin v1.2.0
~~~

After all this was completed and running properly, it was required to tag this part of the project. So, I added a new commit, where I cleaned some files, and marked it with the tag *ca1-part1.1*.

~~~bash
git add .
git commit -m "Clean up files"
git tag ca1-part1.1 -m "Clean up files"
git push
git push origin ca1-part1.1
~~~



### Part 1-2

### Goals & Requirements

- Implement a Git workflow using feature and bug-fix branches to ensure isolated development;
- Develop and test a new feature (email field) in a new branch;
- Create a second branch (fix-invalid-email) to address and validate proper email formats.
- Practice debugging both the server and the client.
- Merge completed and tested branches into the master branch.
- Use meaningful commits and tags.

## Enhancing Data Structure: Adding the JobTitle Parameter

At the beginning of Part 2, I noticed that a parameter called JobTitle was missing, which is crucial for providing better categorization within the 
system. This was addressed early on in this section by adding the JobTitle parameter to the relevant parts of the project.

The addition of this field improves the overall structure of the system, as it helps to clearly define roles, such as identifying whether a person 
is a teacher, administrator, or holds another specific job title. This enhancement makes the system more robust and accurate, ensuring that all 
necessary data is captured.

This change also demonstrates the importance of regularly reviewing and refining the system to ensure that all required information is properly 
represented. By adding JobTitle, we ensure that users or entities within the system are more easily categorized, improving both functionality and user experience.

The implementation and testing followed the same standards as the previous parameters, ensuring consistency and alignment with 
the existing structure. The process mirrored the approach used for other parameters, with careful attention to detail during 
both the implementation and the testing phases to ensure seamless integration and functionality.

### Development

This part focuses on implementing a branch-based development workflow, maintaining the main branch stable. Throughout this part I used two different branches
that helped me achieve this goal.

The project requirements included adding a new feature with corresponding tests and addressing bug fixes.

- #### Using the master branch

To start out this section, I used the command ``git branch`` to confirm that I was on the right branch, in this case
*main*.

- #### Developing new features
A new branch was created to isolate and manage all developments associated with the new email feature. To do this, I used a new command to create the *email-field* branch and in it, I was able to
add the email feature to the employee without working on the main branch. Here I'll showcase the commands I used, the first relates to the creation of a new branch. With this command, I
automatically move to this branch. However, I can always use the second command to check if I am on the right location.

~~~bash
git checkout -b email-field
git branch
~~~

- #### Integration and Testing of the Email Field
As stated before, adding the feature *email* mirrors the addition of *jobYears* in part 1.1.
To summarize, the email was added to the employee, I created a specific getter and a setter method and updated all the classes that contained information about the employee. In the
**Employee.java** I also added a method to validate this new field. At this point of the project, the only validation necessary was whether the String sent was null or blank. Therefore,
at this point, the method looked as such:

~~~java
private boolean isEmailInvalid(String email) {
		return email == null || email.isBlank();
	}
~~~

It was necessary to update all existing tests to accommodate the new email field, and I also added two new tests specifically
for the email setter method. The first test checks for a valid email whilst the second checks for invalid values.

~~~java
@Test
void testValidEmail() {
  Employee employee = new Employee("Frodo", "Baggins", "A dedicated employee", 25, "frodolovesring@gmail.com", "Adventure Freelancer");
  assertNotNull(employee);
  assertEquals("frodolovesring@gmail.com",employee.getEmail());
}

@Test
void testEmailNull() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee("Frodo", "Baggins", "A dedicated employee", 33, null, "Adventure Freelancer");
  });
  assertEquals("Email cannot be null or empty.", exception.getMessage());
}

@Test
void testEmailEmpty() {
  IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    new Employee("Frodo", "Baggins", "A dedicated employee", 33, " ", "Adventure Freelancer");
  });
  assertEquals("Email cannot be null or empty.", exception.getMessage());
}
~~~~

Finally, I debugged both the server and client components of the application as to detect and resolve any issues introduced
by the creation of the email field.

- #### Merging with the main

To merge changes from the email-field branch, I first committed the updates. 

Going back to the merging, I then pushed the email-field branch upstream with ```git push --set-upstream origin email-field```.
After switching to the main branch, I merged the email-field branch with the *--no-ff* option to maintain a clear commit history.
The updated main branch was then pushed using git push. Finally, I tagged the new version as v1.3.0 and pushed the tag to the remote repository.

~~~bash
#commit the changes
git add .
git commit -m "Added email field and tests"

#push the email-field branch upstream
git push --set-upstream origin email-field

#switch to main and merge the changes
git checkout main
git merge  --no-ff email-field

#push to update the main branch
git push

#tag
git tag v1.3.0
git push origin v1.3.0
~~~

- #### Creating a new branch for bug fixing

It was now necessary to create another branch to address the bug fix for email validation. This new branch was
created with the command ``git checkout -b fix-invalid-email`` similarly to the previous branch.

I created the fix-invalid-email branch to address bugs in email validation and ensure that any email sent contains
an "@" sign. This fix involved enhancing the Employee class with validation logic to enforce the correct email format.
I went back to the method *'validateEmail'* and added the new requirement:

~~~java
private String validateEmail(String email) {
  if (email == null || email.trim().isEmpty()) {
    throw new IllegalArgumentException("Email cannot be null or empty.");
  }

  if (!email.matches(("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
    throw new IllegalArgumentException("Invalid email format: " + email);
  }
  return email.trim();
}
~~~

Though I could have used something like *email.contains("@")* to do this verification, I decided to go a little further and
create a regex to ensure that the email abides normal email rules. This is a more detailed and strict approach that can
catch more errors. 

~~~java
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
  // Act & Assert
  if (isValid) {
    // When valid, check that no exception is thrown
    assertDoesNotThrow(() -> new Employee("Frodo", "Baggins", "A dedicated employee", 25, email, "Adventure Freelancer"));
  } else {
    // When invalid, expect an exception with the proper error message
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Employee("Frodo", "Baggins", "A dedicated employee", 25, email, "Adventure Freelancer");
    });
    String expectedMessage = email == null || email.trim().isEmpty() ? "Email cannot be null or empty." : "Invalid email format: " + email;
    assertEquals(expectedMessage, exception.getMessage());
  }
}
~~~

- #### Wrapping up

I repeated the previous steps mentioned regarding testing and validating this new update. I merged everything with the main
branch using the steps aforementioned and added the tag **v1.3.1** (which indicates a minor fix) to this commit.
This version update shows the ongoing improvements in the application's functionality and reliability.

Once everything was finished, this part of the assignment was sent to the repository with the tag **ca1-part1.2**.

### **The Results**

- **Implementation**

At the end of these two parts of the Class Assignment 1, the application looked as follows:

![part1](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2016.11.39.png)
While the first three fields (First Name, Last Name, and Description) were already part of the model, I added the
**Job Years** and **Email** sections in this project. 

These additions provide a more complete and detailed view of what makes up an Employee.

- **Branches**
  The branches created can still be found in the repository using the command ``git branch`` and the * symbol indicates
  which branch the user is on at the moment.

![branches.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2015.34.37.png)

- **Tags**
  Using the command ``git tag`` I am able to see which tags I have used for this project. This allows me to organise
  myself and this assignment whilst marking specific points of its history as significant. Below is an example of all the
  tags I have used so far:

![tags.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2015.35.32.png)

- **Issues**

*Issues* was the perfect tool to keep me organised and to keep track of what needed to be done. During the first part
of this assignment (part1.1) I closed all the issues manually. However, I then learned that I can close them automatically
using the commit messages. This approach is much simpler and easier to see the history of the problem and how it was
solved. For this assignment I am also tagging every issue with 'CA1' as to help me organise my work.
The picture below exemplifies some of the issues I created for this project.

![issues.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2015.36.28.png)

---
This section gives a clear view of how the application evolved, with new features added, branches used for development,
and milestones marked with tags. The visuals provided show how version control works and how the project grew over time.
Using issues also helped keep everything organised and easy to follow, making sure all the changes are well-documented.


### Part 1-3
#### Alternative to Git

As an alternative technological solution for version control, I looked into Mercurial.

In this section, I am going to analyse how Mercurial compares to Git regarding their version control features and how Mercurial
could have been used to solve the requirements of this assignment.

**Comparing Mercurial and Git**

| Feature                     | Mercurial                                                                                                                                                   | Git                                                                                                                                               |
|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| History and Revision        | Mercurial tracks history with SHA-1 hashes in a linear manner and enforces stricter history integrity.                                                      | Git assigns hash IDs for efficient history tracking and allows history rewriting with commands such as rebase.                                    |
| Branching and Merging       | Mercurial provides named and anonymous branches, supporting easy merging but with fewer flexible tools. Named branches are permanent and cannot be removed. | Git allows lightweight branching, enabling easy creation, merging, and deletion of branches, which can be recreated effortlessly.                 |
| Architecture                | Mercurial is a distributed system where each user has a complete copy of the repository.                                                                    | Git also follows a distributed model, where every user possesses a full repository and its complete history.                                      |
| Collaboration Features      | Mercurial does not have built-in support for pull requests, but external tools like Bitbucket and Source can facilitate collaboration.                      | Git includes native pull request functionality and has a more extensive ecosystem of collaboration tools, widely adopted in open-source projects. |
| Performance and Scalability | Mercurial is efficient but may experience performance limitations with very large repositories.                                                             | Git is optimized for handling large repositories and is well-suited for teams managing frequent, high-volume changes.                             |
| Ease of Use                 | Mercurial is simpler for beginners due to its more intuitive command set, although it offers fewer advanced features.                                       | Git has a steeper learning curve but provides more flexibility and power once mastered.                                                           |


**Using Mercurial in the Assignment**

If I had chosen Mercurial for version control in this assignment, the following steps would have been taken:

- **Repository setup and Import:**

To create a Mercurial repository for the project, I initialized it and added the necessary files. I then configured a remote repository and pushed the changes. 
The process is similar to Git, but Mercurial treats every clone as a full repository, making distributed development easier to manage.

~~~bash
# Create a new Mercurial repository
mkdir /path/to/devops-24-25-1241914
cd /path/to/devops-24-25-1241914
hg init

# Copy the 'basic' folder into the new repository (assuming extraction)
cp -r /path/to/TutorialReactSpringDataREST/basic .

# Add files to Mercurial tracking
hg add

# Commit the initial files
hg commit -m "Initial commit"
~~~

- **Feature Development and Branch Management:**

Creating a new branch in Mercurial is straightforward. After setting up the repository, I could run the following command to create a branch for feature development:

~~~bash
#Creating a new branch: email-field
hg branch email-field
~~~

- **Committing and Tagging:**

After implementing the required modifications, I committed them to the feature branch. Mercurial also enables tagging to mark stable versions. 
Unlike Git, Mercurial automatically commits tags, eliminating the need for additional steps.

~~~bash
#Committing the changes
hg commit -m "Added email field"

#Pushing the new branch to the remote repository
hg push

#Tagging
hg tag v1.0.0

#Pushing to the remote repository
hg push --tags
~~~

- **Merging Features and Wrapping up**

Once the feature was completed and tested, it could be merged back into the default branch (Mercurial’s equivalent of the main branch). 
To do this, I would switch to the default branch and merge the feature branch.

~~~bash
#Moving to the default branch
hg update default

#Merging the email-field branch
hg merge email-field

#Committing the merge
hg commit -m "Merging email-field"

#Pushing to the remote repository
hg push
~~~

To summarize, Mercurial serves as a viable alternative to Git, offering essential features like branching, merging, tagging stable releases, and remote repository management. 
Its user-friendly approach makes it a solid choice for version control, especially for those new to distributed systems, ensuring a smoother experience without
overwhelming complexity.

--- 
### **Concluding**

Using the Version Control with Git assignment in **Part1.1** provided me with a strong foundation in version control concepts.
I worked with the main branch, committed updates, and tagged versions, which helped me develop essential skills for managing project modifications efficiently.

**Part1.2** introduced me to branching, enabling me to work on new features and bug fixes independently. This approach kept the project history well-organized, 
ensured changes remained isolated, and allowed for seamless reintegration into the main branch.

In the **Final Results**, I saw how these techniques are utilized in real-world software development. I implemented new features, maintained stable versions, 
and prepared the project for deployment. Git made it easy to track my modifications and ensured a streamlined development workflow.

While exploring an **Alternative to Git**, I found that Mercurial serves as a solid option, providing many similar tools. It is more straightforward and user-friendly 
while still being a viable alternative. Working with both systems gave me a deeper insight into version control and their significance in development processes.

---
##  Part 2

### The Setup

This project documents the work completed for the DevOps course assignment, where I explored Gradle as a build tool. The assignment involved a series of tasks designed 
to provide practical experience with Gradle, starting with fundamental concepts like setup and progressing to more advanced functionalities, such as defining tasks
and executing unit tests.
After configuring the environment, the report details the development of the Gradle Basic Demo, a multithreaded chat server. This section outlines the process of building 
the server, launching it, and establishing connections with multiple clients.
I began by creating a new directory for the assignment, named /CA2/part2, and cloned the example application from the provided Bitbucket repository. This repository 
contained a preconfigured build.gradle file and included the Gradle Wrapper to ensure a consistent build environment. 

### Gradle Basic Demo
Once everything was set up properly, I studied the ReadMe file attached to the example application I copied before. This example also provided
a Gradle Basic Demo that allowed me to explore a multithreaded chat server. In order to make the most out of this practical exercise, I executed
the following steps:

- **Build Process:**

To start out, I needed to prepare the demo for execution. I executed the command ``./gradlew build`` from the root directory. This compiled the source
code and packaged it into an executable .jar file.

- **Server Startup:**

The next step was to launch the chat server. For this, I used the command ``  java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001``
and, as seen in the image, it started to run and wait for client connections.

- **Client Connections:**

Now that the Server was running, I needed to initiate the client side. For this, I ran the command ``` ./gradlew runClient```
to ensure that each client would be connected to localhost on port 59001. Once this was ready, a chatbox popped up prompting
the Client to write their name.

Since this server can easily manage multiple clients, I created a second client using another terminal window and tested
a conversation between the two Clients.

![Chat1.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2016.51.24.png)
![Chat2.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2016.52.07.png)

And whilst the Client side was running, the Server side kept updating whenever a new Client used the application:

![server.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2016.53.19.png)
![serverfinal.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2016.53.36.png)

### Adding a new Task

Once I finished the Demo provided, it was time to start working on the application and improve it with the required tasks.

The first task was added to avoid the need for manual command-line input whenever we wanted the server to start. For this, I
added a ``runServer`` to the file ``build.gradle``. This improved the development process by streamlining the server startup.
Now, it is possible to launch the chat server directly with the Gradle command ``./gradlew runServer.``

The *runServer* task is defined as a JavaExec type to run Java applications. It has a dependency on the classes task
to ensure that all necessary classes are compiled before starting the server. Additionally, it’s set up to run the ChatServerApp main class on port 59001.

~~~groovy
task runServer(type: JavaExec, dependsOn: classes) {
    group = "DevOps"
    description = "Launches the chat server on port 59001"

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'
}
~~~

After this update, I needed to execute the task using ``./gradlew runServer`` in the command line.The terminal provided
immediate confirmation of the task’s success with the server running as expected. This addition improved the development
workflow by reducing the steps needed to start the server, and thus, simplifying the process.

Once everything was running smoothly, I commited the changed to the repository.
~~~bash 
git commit -m "Added part2"
~~~ 

### Adding a unit test

In order to ensure the App class's functionality, I added a unit test. This test was provided beforehand, and it was placed
in a new directory created with the command ``mkdir -p src/test/java/basic_dem ``. This  test verifies that the App
class generates a non-null greeting message.

~~~java
package basic_demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
~~~

To confirm that the test environment was set up correctly, I included the JUnit dependency in the build.gradle
file, as it is necessary for running the unit tests. This addition ensures that the project correctly identifies and
runs JUnit tests without any issues.

~~~groovy
testImplementation 'junit:junit:4.12'
~~~

Next, I executed the command ``./gradlew test`` and I was able to see that the test passed successfully.

### Adding a Copy type task

The next step was to introduce a new task of type **Copy** in the build.gradle file, which serves to create a backup of the source code. This task duplicates the contents 
of the src directory into a designated backup location within the project, ensuring a reliable recovery point in case of unforeseen issues during development. 
Having this backup is essential for maintaining an up-to-date snapshot of the codebase, especially before implementing significant modifications or updates.

~~~groovy
task backup(type: Copy) {
    group = "DevOps"
    description = "Copies the sources of the application to a backup folder"

    from 'src'
    into 'backup'
}
~~~
To verify the task was working correctly, I executed the command associated with it. I ran ```./gradlew backup```
from the command line and confirmed its successful execution. The output shows that the source code was properly
copied to the backup location, demonstrating that the task effectively protects the project’s code.

This also resulted in the creation of a new folder named backup in my directory (**backup**), confirming that the
backup process was completed properly. Adding the backup task to the Gradle build script has made the project more
resilient by making it easy and reliable to back up the code.

![part2backup folder.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2017.00.38.png)

Once everything was finished, I sent my changes to the repository with the message:

~~~bash
git commit -m "Added task copyToBackup - closes #3"
~~~

### Adding a Zip type task

The last task was to create a new Zip task to package the project's source code into a compressed .zip file.
This task makes it easier to zip up the src directory, which is handy for backups or sharing the project. It's an
important step for archiving project versions or preparing the code for distribution.

~~~groovy
task archive(type: Zip) {
    group = "DevOps"
    description = "Creates a zip archive of the source code"

    from 'src'
    archiveFileName.set('src_backup.zip')
    destinationDirectory.set(layout.buildDirectory.dir("archives"))
}
~~~~

Once the zip task was set up, I ran it with ```./gradlew zip.``` The terminal output confirmed the task ran smoothly,
indicating that the src directory had been successfully compressed into a ZIP file.

The success of this task is evident not only in the image above but also by the presence of the ZIP file in my directory.

![part2gradlewArchiveResult.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2016.57.56.png)

This final requisite was commited to the repository with the command:

~~~bash
git commit -m "Added task archive - closes #4"
~~~

### Concluding

Completing this project has given me a much deeper understanding of Gradle and its practical applications in software development. 
Throughout the various tasks, I experienced firsthand how versatile and powerful Gradle is as a build tool.

This project demonstrated how Gradle can streamline key development tasks such as building applications, executing tests, and managing 
files. These capabilities are essential for maintaining an efficient and organized workflow, reinforcing Gradle’s value in real-world 
projects.

With the implementation of tasks like **runServer**, **backup**, and **archive**, I saw how effortlessly Gradle’s functionality can be expanded. 
These tasks not only simplified development but also enhanced the project's resilience and facilitated its distribution.

Incorporating unit testing into the build process further emphasized the importance of automated testing, showcasing how Gradle 
seamlessly integrates testing into the development pipeline. This reinforced the critical role of testing in maintaining high code 
quality.

Overall, this project has greatly improved my understanding of Gradle and its role in modern development workflows. The skills and 
insights gained will undoubtedly be valuable for future projects, helping me work more efficiently and reliably.


---
##  Part 3

### Part 3-1

For the third part of Class Assignment 1, I am focusing on implementing Gradle as a build automation tool. The aim was to migrate a 
Spring Boot application from Maven to Gradle and then explore its features to understand how they integrate into the software 
development lifecycle.

In this section, I will explain and demonstrate the steps I followed to achieve the objectives of this project.

### The Setup

In order to transition smoothly from a Maven-based structure to a Gradle-based one, I needed to ensure my project had
the necessary tools. The first part was to create a new branch (*tut-basic-gradle*). This branch was meant to keep the setup and 
changes organised and separate from the main project.

For the next step, I headed to the [spring initializr](https://start.spring.io/) website so that I could start a new
Gradle Spring Boot project with all the dependencies needed (Rest Repositories, Thymeleaf, JPA, H2).

The generated .zip file was then downloaded and added to my *CA1/Part3* folder as it contained a foundation for the
application. After downloading and unzipping this folder in my repository, I ran the command ``./gradlew tasks`` to
confirm the setup and check all the available Gradle tasks.

~~~bash
#headed to the Downloads folder
cd ~/Downloads

#moved the generated zip folder from Downloads to CA1/Part3
mv react-and-spring-data-basic.zip C:\Users\danie\Desktop\SWitCH\2_Semestre\DevOps\devops-24-25-1241902\CA1\part3 

#went back to ca1/part3 and unzipped the folder
unzip react-and-spring-data-basic.zip

#ran this command to confirm the setup
./gradlew tasks
~~~

The image above demonstrates some of the many tasks and functionalities that can be executed using Gradle.

Another key aspect of the initial setup was integrating existing code into the Gradle project structure. The code to be integrated 
had already been used in Part 1 of this assignment, which focused on the basic tutorial.

Since this was a new build management system, the process required careful attention to ensure all components functioned correctly. 
Throughout the development of this project, several compilation errors arose, and these necessary adjustments are outlined below.

- **Replacing the Source Directory:**

The first step was to delete the original *src* folder as to allow for the integration of the new codebase. 
I copied the *src* folder from the basic folder of the tutorial to this project.

- **Including Additional Configuration Files:**

For the configuration to succeed, I needed to copy the files *webpack.config.js* and *package.json* to the root
of the new directory.

- **Removing Redundant Directories:**

Then, I needed to remove the *src/main/resources/static/built* directory. Since this file is automatically generated by
Webpack during the build process, it should not be included to prevent conflicts and redundancy

- **Adjusting Import Statements:**

In the *Employee.class*, I needed to adjust the import statements so that they could fit the transition from Java EE
to Jakarta EE. To achieve that I updated *javax.persistence* to *jakarta.persistence*.

- **Configuring Package Manager:**

The *package.json* file needed to be updated to specify a fixed version of the package manager by adding
```"packageManager": "npm@9.6.7"```. With this addition, the project can use the same version of the
package manager across various environments.

### Configuring Frontend Plugin

After making all the necessary adjustments, I was able to execute the command ./gradlew bootRun to compile the application and start 
the backend. At this stage, accessing http://localhost:8080/ displayed an empty page, indicating that the frontend part of the project 
was still not ready. This behavior was expected, as the Gradle setup was missing the required plugin to manage the frontend code.

To resolve this issue, I needed to configure the appropriate plugin. In this section, I will walk through the steps I followed to 
complete this configuration.

As I was working with Java 17, I needed to add a new plugin block to my *build.gradle* file.

``id "org.siouan.frontend-jdk17" version "8.0.0"``

Also in the *build.gradle* file, I also added script commands to ensure proper handling of frontend assets.
These configurations were tailored to the specific *Node.js* version, enabling the inclusion of scripts for
assembling, cleaning, and verifying the frontend.

~~~groovy
frontend {
nodeVersion = "16.20.2"
assembleScript = "run build"
cleanScript = "run clean"
checkScript = "run check"
}
~~~
The *package.json* also suffered some changes. I added the following script to handle the execution of
Webpack and other frontend-related tasks.

~~~json
{
  "scripts": {
    "webpack": "webpack",
    "build": "npm run webpack",
    "check": "echo Checking frontend",
    "clean": "echo Cleaning frontend",
    "lint": "echo Linting frontend",
    "test": "echo Testing frontend"
  }
}
~~~

As soon as these adjustments were added, I ran the command ``./gradlew build`` to confirm that the
project was built successfully. This also ensured that the tasks related to the frontend were also
executed and the frontend code generated.

The next step was to run the command ``./gradlew bootRun`` and access the application at http://localhost:8080/.
As shown in the image below, the page is now fully functional and no longer empty.

![part3localhost.png](Images/Captura%20de%20ecr%C3%A3%202025-04-02%2C%20%C3%A0s%2017.02.58.png)

This ensured that the Gradle plugin successfully handled the frontend resources during the build. The setup illustrates how seamlessly frontend build management 
integrates into the Gradle environment, simplifying the handling of more complex full-stack development workflows.

After completing everything, I committed the changes and merged this branch into the main branch to ensure smooth functionality.

### Adding a Copy Type Task

To improve file management, especially for distribution, I defined a custom Gradle task called **copyJar**.

This task copies the .jar file created by the bootJar task from the output directory to a dist folder at the project’s root. 
This ensures that only the correct, fully assembled .jar file is included for distribution, reducing errors and ensuring deployments 
always have the latest build.

The task also depends on the bootJar task, meaning the copy operation occurs only once bootJar has finished successfully. 
This guarantees a well-structured and dependable build process.

~~~groovy
tasks.register('copyJar', Copy) {
 dependsOn bootJar
 from bootJar.outputs
 into file("dist")
}
~~~

After running the command ``./gradlew copyJar`` , the .jar file was successfully copied to the dist directory. 
This confirmed that the task correctly identifies and moves the right file, ensuring its proper integration into the build process 
and preparing it for distribution.

### Adding a Cleanup Task

To streamline file management, especially for cleaning up, I defined the cleanWebpack task.

This task deletes all files generated by Webpack in the src/main/resources/static/built directory. 
It keeps the build environment clean by ensuring that only the essential files are included in each build, 
preventing leftover or obsolete files from causing issues.

Additionally, a message is printed at the start of the task (println "Starting to delete Webpack build files (robotic voice)") to notify that the 
Webpack build files are being deleted.

The task is set to run automatically before Gradle’s standard clean task, integrating it into the regular cleanup process.

~~~groovy
tasks.register('cleanWebpack', Delete) {
 doFirst {
  println "Starting to delete Webpack build files (robotic voice)"
 }
 delete 'src/main/resources/static/built'
}
clean.dependsOn cleanWebpack
~~~

Once I executed the command ``./gradlew cleanWebpack`` I successfully deleted the contents of the
src/main/resources/static/built directory. A message, “Starting to delete Webpack build files (robotic voice)”, was printed at the start, confirming that 
the cleanup task had begun. This confirmed that the cleanup process works as expected, keeping the build environment tidy.

By adding the **copyJar** and **cleanWebpack** tasks, I improved both the efficiency and reliability of the build and deployment processes.

### Concluding

While Gradle is a fast and flexible build tool, I decided to compare it with Ant. Apache Ant is one of the earliest build tools for Java, 
known for its XML-based scripting and task-based execution model. Gradle, by contrast, follows a declarative approach and is known for its 
speed and flexibility, whereas Ant requires explicit scripting for tasks, making it more manual but highly customizable.

In this section, I’ll compare Ant and Gradle in terms of build automation features, including how each handles extensions like plugins and 
custom tasks. Additionally, I’ll explore how Ant could achieve the same results as Gradle for this assignment if I had used it instead.

--- 
### Part 3-2
####  Alternative to Gradle

While Gradle is a fast and flexible build tool, I decided to compare it with Ant. Apache Ant is one of the earliest build tools for Java, known for its XML-based scripting and task-based execution model. Gradle, by contrast, follows a declarative approach and is known for its speed and flexibility, whereas Ant requires explicit scripting for tasks, making it more manual but highly customizable.

In this section, I’ll compare Ant and Gradle in terms of build automation features, including how each handles extensions like plugins and custom tasks. Additionally, I’ll explore how Ant could achieve the same results as Gradle for this assignment if I had used it instead.


**Comparing Maven and Gradle**

| Feature                           | Ant                                                                                        | Gradle                                                                     |
|-----------------------------------|:-------------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
| Build Lifecycle                   | Ant does not have a predefined lifecycle, relies on manual task definitions	              | Gradle uses a structured lifecycle with tasks and dependencies             |
| Build Script                      | Ant uses XML build scripts (build.xml)	                                                  | Gradle uses Groovy/Kotlin DSL (build.gradle, build.gradle.kts)	           |
| Configuration Model               | Ant is fully imperative (explicit scripting required)                                      | Gradle uses a declarative and imperative model                             |
| Default Build Tool                | Ant is designed for Java projects but requires manual dependency management.               | Gradle is commonly used for Java, Kotlin, and Android projects             |
| Dependency Management             | Ant does not have built-in dependency management (requires Apache Ivy or manual handling)  | Gradle has built-in dependency management using Ivy and Maven repositories | 
| Extensibility                     | Ant is extensible via custom scripts and external libraries                                | Gradle supports plugins and scripting for extensions                       |
| Integration with IDEs             | Ant is supported in IntelliJ, Eclipse, and NetBeans                                        | Gradle is well-supported in IntelliJ, Eclipse, and VS Code                 |
| Learning Curve                    | Ant has a steep learning curve due to manual scripting	                                  | Gradle has a steep learning curve because of Groovy/Kotlin                 |
| Parallel Execution                | Ant does not have built-in parallel execution                                              | Gradle supports parallel and incremental builds                            |
| Performance	                     | Ant is slower due to lack of incremental build support                                     | Gradle is faster with incremental builds and caching                       |
| Plugin System                     | Ant requires external plugins or manual scripting                                          | Gradle has a powerful plugin ecosystem                                     |
| Popularity                        | Ant is still used in legacy projects but is less common today                              | Gradle is widely used in modern projects                                   |
| Support for Multi-Project Builds  | Ant requires manual configuration for multi-project builds                                 | Gradle has built-in multi-project support                                  |


**Implementing the Assignment with Maven**

To replicate the setup and functionality of Gradle, I needed to configure Apache Ant for the Spring Boot application. Since Ant doesn’t have 
built-in dependency management, I integrated Apache Ivy to handle dependencies.

Ant requires manual configuration for tasks, so I had to define build tasks for compiling Java code, managing dependencies, and handling frontend assets. 
Here’s how I configured Ant for the project:

- **Project Setup:**

Using Ant, I needed to manually configure the **build.xml** file to handle key aspects of the Spring Boot application. I had to integrate Apache Ivy to manage dependencies for
REST, Thymeleaf, JPA, and H2, just like with Gradle. The **ivy.xml** file would look like this:

~~~xml
<dependencies>
  <dependency org="org.springframework.boot" name="spring-boot-starter-web" rev="latest.release" />
  <dependency org="org.springframework.boot" name="spring-boot-starter-thymeleaf" rev="latest.release" />
  <dependency org="org.springframework.boot" name="spring-boot-starter-data-jpa" rev="latest.release" />
  <dependency org="com.h2database" name="h2" rev="latest.release" />
  <dependency org="org.springframework.boot" name="spring-boot-starter-test" rev="latest.release" conf="test->default" />
</dependencies>
~~~

- **Frontend Integration:**

I added a task in build.xml to install Node.js and npm manually and run the frontend build process since Ant does not have built-in frontend plugin support.

~~~xml
<target name="install-node">
  <exec executable="npm">
    <arg value="install" />
  </exec>
</target>

<target name="build-frontend" depends="install-node">
<exec executable="npm">
  <arg value="run" />
  <arg value="build" />
</exec>
</target>
~~~

- **Copy JAR Task:**

To copy the generated .jar file into a distribution folder after the build process, I defined a task in build.xml:

~~~xml
<target name="copy-jar">
  <copy file="dist/myapp.jar" todir="output/" />
</target>

~~~

- **Delete Webpack Files Task:**

To remove the webpack-generated files during the build process, I added a clean task:

~~~xml
<target name="clean">
  <delete>
    <fileset dir="dist/webpack-output" includes="**/*" />
  </delete>
</target>
~~~

With these configurations, I successfully mirrored the Gradle setup using Ant for the Spring Boot application. By configuring Ant to manage dependencies through Ivy, handling
frontend assets manually, and defining tasks for JAR copying and cleanup, I achieved similar results to Gradle.

This process highlighted the differences between Ant and Gradle that I mentioned before. While Ant offers flexibility and control, it requires more manual configuration compared to Gradle.


---

**Concluding**

In this section, I explored migrating a Spring Boot application from Maven to Gradle and compared Gradle with Ant. 
Gradle’s flexibility and ability to manage dependencies, handle frontend tools, and automate tasks made it ideal for this assignment. 
Ant, while more manual, provides similar capabilities but with more effort required for configuration.

This experience has helped me understand the importance of choosing the right tool based on the project’s needs and requirements, 
as well as the skills of the team working on it.

---

### Conclusion
This DevOps project deepened my understanding of version control and build tools. I got hands-on experience with Git, 
explored Mercurial as an alternative, and learned to use Gradle for task automation. I also migrated a tutorial app to Gradle 
and compared it with Ant, gaining valuable insights into how these tools improve development efficiency and project management.