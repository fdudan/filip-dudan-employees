# Getting Started

### Local development setup
1. Clone the project locally.
2. In your IDE add the pom.xml as a Maven project
3. Set the JDK to Java 11
4. Make sure the annotation processing is turned on.
5. The run configuration should be picked up automatically. If needed manually add run configuration for main class:
com.sirma.task.employees.app.EmployeesApplication
with classpath: filip-dudan-employees
6. In the application.properties file configure port and context.

## Usage

* This application is used to find the pair of employees that have worked as a team for the longest time at the same projects.
This information is listed under "The longest working colleagues together on all projects".

* On the main page the application also lists all pairs that worked together on different projects, shown in format Employee #1, Employee #2, Project ID, Days worked on the project.

* The delete button on the main page deletes all entries from the memory.

* The upload button on the main page redirects to the upload page where the user can upload a file that contains entries
for the employees in format "EmpID, ProjectID, DateFrom, DateTo". The Dates in the file and the pattern **MUST MATCH** 
in order for the parsing to give valid dates. See more at the [SimpleDateFormat documentation](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)

The main page is mapped to "/".