Requirements

Java Version
Java Development Kit (JDK): 17 or higher

Maven Version
Apache Maven: 3.9.6 or higher

How the Project Works
TestNG Framework:

The project uses the TestNG framework for running tests:
1) Test classes and methods are annotated with @Test.
2) Test data (e.g., inputs and expected outputs) are stored in a JSON file located at : src/test/resources/test_data.json . The JSON file is read dynamically during test execution.

Reports:
Test results are saved in HTML files for easy viewing.

Prioritization of Test Cases (High/Medium/Low):
In TestNG, prioritization is implemented using the priority attribute in the @Test annotation.

How Priorities Are Assigned:
Each test method is assigned a priority to determine its execution order.
Higher Priority (Lower Number):
Test cases validating critical inputs (e.g., non-trivial inputs like "23", Longer inputs like "567") are assigned High Priority (1).
Medium Priority (Higher Number):
Test cases validating moderately critical inputs (e.g., longer inputs like "567"). assigned Priority (2)
Low Priority (Highest Number):
Test cases validating edge cases (e.g., empty input ). assigned Priority (3)

The JSON file contains a mix of valid and invalid cases. The invalid cases are designed to intentionally cause some test cases to fail. This ensures the robustness of the test framework and validates that the system correctly handles unexpected or incorrect inputs.
These intentional failures are helpful for identifying areas that need improvement or verifying error-handling mechanisms.


Commands to Run the Tests:
mvn test -DsuiteXmlFile=testng.xml

Viewing the Results:

1. Default TestNG Report:
TestNG generates a default HTML report after test execution.
Location: test-output/index.html
How to View: Open test-output/index.html in a browser.

2. Enhanced Report (ExtentReports)
If configured, an enhanced HTML report will also be generated using ExtentReports.
Location: test-output/ExtentReport.html
How to View: Open test-output/ExtentReport.html in a browser to see:
Detailed logs for each test case.
Pass/Fail/Skip status.
Inputs, expected, and actual results (if logged).


