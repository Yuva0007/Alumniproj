# Implementation Plan Checklist

## Original Question/Task

**Question:** <h1>Alumni Connect: Building a Mentorship and Networking Platform</h1>

<h2>Overview</h2>
<p>The Alumni Management System aims to connect former students (alumni) with each other and current students for mentorship and networking opportunities. You are tasked with developing a full-stack application that will allow alumni to register, create profiles, connect with other alumni, and offer mentorship to current students.</p>

<h2>Question Requirements</h2>

<h3>Frontend Requirements (React)</h3>

<h4>1. Alumni Profile Component</h4>
<p>Create a React component to display alumni profiles with the following requirements:</p>
<ul>
  <li>The component should be named <code>AlumniProfile</code> and accept a prop called <code>alumniData</code></li>
  <li>The component should display the following information from the alumniData prop:
    <ul>
      <li>Name (full name of the alumni)</li>
      <li>Graduation Year (year when the alumni graduated)</li>
      <li>Current Company (where the alumni currently works)</li>
      <li>Job Title (current position of the alumni)</li>
      <li>Skills (list of professional skills)</li>
      <li>Available for Mentorship (boolean indicating availability)</li>
    </ul>
  </li>
  <li>The component should have a "Connect" button that, when clicked, displays a message "Connection request sent to [alumni name]"</li>
  <li>If the alumni is available for mentorship, display a badge or label indicating "Available for Mentorship"</li>
</ul>

<p><b>Example alumniData prop:</b></p>
<pre>
{
  id: "1",
  name: "John Doe",
  graduationYear: 2018,
  currentCompany: "Tech Innovations Inc.",
  jobTitle: "Software Engineer",
  skills: ["Java", "React", "Spring Boot"],
  availableForMentorship: true
}
</pre>

<h4>2. Alumni Search Component</h4>
<p>Create a React component to search for alumni with the following requirements:</p>
<ul>
  <li>The component should be named <code>AlumniSearch</code></li>
  <li>Include a search input field with placeholder text "Search alumni by name, company, or skills"</li>
  <li>Include filter options for:
    <ul>
      <li>Graduation Year (dropdown with options: All, 2020-2023, 2015-2019, 2010-2014, Before 2010)</li>
      <li>Available for Mentorship (checkbox)</li>
    </ul>
  </li>
  <li>The component should maintain its own state for search text and filter selections</li>
  <li>When search text or filters change, the component should call a function prop named <code>onSearch</code> with the current search parameters</li>
</ul>

<h4>3. Alumni List Component</h4>
<p>Create a React component to display a list of alumni with the following requirements:</p>
<ul>
  <li>The component should be named <code>AlumniList</code> and accept a prop called <code>alumniList</code> (an array of alumni objects)</li>
  <li>For each alumni in the list, render an <code>AlumniProfile</code> component with the appropriate data</li>
  <li>If the alumniList is empty, display a message "No alumni found matching your search criteria"</li>
  <li>The component should include pagination controls if there are more than 5 alumni in the list</li>
</ul>

<h3>Backend Requirements (Spring Boot)</h3>

<h4>1. Alumni Entity</h4>
<p>Create a JPA entity for Alumni with the following attributes:</p>
<ul>
  <li><code>id</code> (Long): Primary key</li>
  <li><code>name</code> (String): Full name of the alumni</li>
  <li><code>graduationYear</code> (Integer): Year of graduation</li>
  <li><code>currentCompany</code> (String): Current company where the alumni works</li>
  <li><code>jobTitle</code> (String): Current job title</li>
  <li><code>skills</code> (String): Comma-separated list of skills</li>
  <li><code>availableForMentorship</code> (Boolean): Indicates if the alumni is available for mentorship</li>
  <li><code>email</code> (String): Email address of the alumni</li>
  <li><code>createdAt</code> (LocalDateTime): Timestamp when the record was created</li>
</ul>

<h4>2. Alumni Repository</h4>
<p>Create a Spring Data JPA repository for the Alumni entity with the following custom query methods:</p>
<ul>
  <li><code>findByNameContainingIgnoreCase</code>: Find alumni by name (case-insensitive)</li>
  <li><code>findByCurrentCompanyContainingIgnoreCase</code>: Find alumni by company name (case-insensitive)</li>
  <li><code>findBySkillsContainingIgnoreCase</code>: Find alumni by skills (case-insensitive)</li>
  <li><code>findByGraduationYearBetween</code>: Find alumni who graduated between two years</li>
  <li><code>findByAvailableForMentorship</code>: Find alumni available for mentorship</li>
</ul>

<h4>3. Alumni Service</h4>
<p>Create a service class for Alumni with the following methods:</p>
<ul>
  <li><code>getAllAlumni</code>: Retrieve all alumni records</li>
  <li><code>getAlumniById</code>: Retrieve an alumni by ID</li>
  <li><code>createAlumni</code>: Create a new alumni record</li>
  <li><code>searchAlumni</code>: Search alumni based on search text, graduation year range, and mentorship availability</li>
</ul>

<h4>4. Alumni Controller</h4>
<p>Create a REST controller for Alumni with the following endpoints:</p>
<ul>
  <li><code>GET /api/alumni</code>: Get all alumni records
    <ul>
      <li>Response: List of alumni objects</li>
      <li>Status code: 200 OK</li>
    </ul>
  </li>
  <li><code>GET /api/alumni/{id}</code>: Get alumni by ID
    <ul>
      <li>Response: Alumni object if found</li>
      <li>Status code: 200 OK if found, 404 Not Found if not found</li>
      <li>Error message format: {"message": "Alumni with ID {id} not found"}</li>
    </ul>
  </li>
  <li><code>POST /api/alumni</code>: Create a new alumni record
    <ul>
      <li>Request body: Alumni object without ID</li>
      <li>Response: Created alumni object with ID</li>
      <li>Status code: 201 Created</li>
      <li>Validation: All fields except availableForMentorship are required</li>
      <li>Error message format for validation failures: {"message": "Field {fieldName} is required"}</li>
    </ul>
  </li>
  <li><code>GET /api/alumni/search</code>: Search alumni based on query parameters
    <ul>
      <li>Query parameters:
        <ul>
          <li><code>query</code> (optional): Search text for name, company, or skills</li>
          <li><code>startYear</code> (optional): Start year for graduation year range</li>
          <li><code>endYear</code> (optional): End year for graduation year range</li>
          <li><code>mentorshipAvailable</code> (optional): Filter by mentorship availability (true/false)</li>
        </ul>
      </li>
      <li>Response: List of matching alumni objects</li>
      <li>Status code: 200 OK</li>
    </ul>
  </li>
</ul>

<p>Note: The application will use MySQL as the backend database.</p>

<h3>Integration Requirements</h3>

<h4>1. Connect Frontend and Backend</h4>
<p>Integrate the React frontend with the Spring Boot backend:</p>
<ul>
  <li>The <code>AlumniList</code> component should fetch data from the <code>GET /api/alumni</code> endpoint</li>
  <li>The <code>AlumniSearch</code> component should send search parameters to the <code>GET /api/alumni/search</code> endpoint</li>
  <li>Implement proper error handling for API requests</li>
</ul>

<h4>2. Main Application Component</h4>
<p>Create a main <code>App</code> component that:</p>
<ul>
  <li>Renders the <code>AlumniSearch</code> component at the top</li>
  <li>Renders the <code>AlumniList</code> component below the search</li>
  <li>Manages the state for the alumni list and passes it to the <code>AlumniList</code> component</li>
  <li>Handles the search functionality by calling the appropriate API endpoint when search parameters change</li>
</ul>

<h3>Example Scenarios</h3>

<h4>Scenario 1: Viewing All Alumni</h4>
<p>When a user first loads the application, they should see a list of all alumni retrieved from the <code>GET /api/alumni</code> endpoint. Each alumni should be displayed using the <code>AlumniProfile</code> component.</p>

<h4>Scenario 2: Searching for Alumni</h4>
<p>When a user enters "Java" in the search field and selects "2015-2019" from the graduation year dropdown, the application should call the <code>GET /api/alumni/search?query=Java&startYear=2015&endYear=2019</code> endpoint and update the <code>AlumniList</code> component with the results.</p>

<h4>Scenario 3: Connecting with an Alumni</h4>
<p>When a user clicks the "Connect" button on an alumni profile, they should see a message "Connection request sent to [alumni name]" displayed on the screen.</p>

<h4>Scenario 4: Creating a New Alumni Record</h4>
<p>The backend should support creating a new alumni record through the <code>POST /api/alumni</code> endpoint. The request should validate that all required fields are present.</p>

**Created:** 2025-07-28 18:38:19
**Total Steps:** 14

## Detailed Step Checklist

### Step 1: Read and analyze backend dependencies from pom.xml
- [x] **Status:** ✅ Completed
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/pom.xml
- **Description:** Ensure the backend has required dependencies for JPA, web, validation, Lombok, and MySQL to support upcoming entity and repository implementations.

### Step 2: Implement Alumni Entity class with required attributes
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/src/main/java/com/examly/springapp/model/Alumni.java
- **Description:** Defines the Alumni data model reflecting all required fields and validation constraints for JPA/Hibernate.

### Step 3: Implement AlumniRepository interface with custom search query methods
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/src/main/java/com/examly/springapp/repository/AlumniRepository.java
- **Description:** Provides all data access methods, supporting custom queries required for search and filtering.

### Step 4: Implement AlumniService class handling business logic for alumni operations
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/src/main/java/com/examly/springapp/service/AlumniService.java
- **Description:** Implements core business logic, validation, and search/filtering features for alumni information.

### Step 5: Implement AlumniController REST controller with required endpoints
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/src/main/java/com/examly/springapp/controller/AlumniController.java
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/src/main/resources/application.properties
- **Description:** Exposes REST endpoints that serve alumni data, support searching and creation, while implementing all contract and error scenarios as per requirements.

### Step 6: Implement backend JUnit test cases for service and controller
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/src/test/java/com/examly/springapp/service/AlumniServiceTest.java
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/springapp/src/test/java/com/examly/springapp/controller/AlumniControllerTest.java
- **Description:** Tests all backend functionalities with both unit and integration style tests for maximum coverage and as per specified test case requirements.

### Step 7: Compile and run backend (Spring Boot) tests
- [x] **Status:** ✅ Completed
- **Description:** Verifies backend code compiles and passes all the required JUnit tests.

### Step 8: Read and analyze frontend dependencies from package.json
- [x] **Status:** ✅ Completed
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/package.json
- **Description:** Gets the frontend context and ensures correct versioning/dependency support for new component creation and test integration.

### Step 9: Implement AlumniProfile component (React) with badge, connect button, message display
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/components/AlumniProfile.js
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/App.css
- **Description:** Builds reusable alumni profile card with required interactivity and cues; supports test cases for rendering and connect behavior.

### Step 10: Implement AlumniSearch component (React) with search input and filters
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/components/AlumniSearch.js
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/App.css
- **Description:** Allows users to filter and search alumni; manages local state and communicates search changes to parent. Directly supports state/filter test cases.

### Step 11: Implement AlumniList component (React) to display profile list with pagination
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/components/AlumniList.js
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/App.css
- **Description:** Displays paginated alumni profiles and handles edge/empty states, supporting key test cases for list rendering and pagination.

### Step 12: Integrate components and backend API in main App.js
- [x] **Status:** ✅ Completed
- **Files to modify:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/App.js
- **Description:** Assembles the full frontend experience, manages API data flow, and wires together implemented components. Ensures app loads alumni and responds to searches as described.

### Step 13: Implement React (Jest) test cases for all components
- [x] **Status:** ✅ Completed
- **Files to create:**
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/components/AlumniProfile.test.js
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/components/AlumniSearch.test.js
  - /home/coder/project/workspace/question_generation_service/solutions/b84114ae-07cd-4fda-9704-9398ad7f8110/reactapp/src/components/AlumniList.test.js
- **Description:** Implements Jest/RTL test cases for all required frontend user stories and prop scenarios, matching described test cases exactly for coverage and interaction verification.

### Step 14: Build, lint, and test React application (frontend)
- [x] **Status:** ✅ Completed
- **Description:** Finalizes the frontend build ensuring code correctness, style, and passing tests for all React components.

## Completion Status

| Step | Status | Completion Time |
|------|--------|----------------|
| Step 1 | ✅ Completed | 2025-07-28 18:38:30 |
| Step 2 | ✅ Completed | 2025-07-28 18:39:01 |
| Step 3 | ✅ Completed | 2025-07-28 18:39:17 |
| Step 4 | ✅ Completed | 2025-07-28 18:39:35 |
| Step 5 | ✅ Completed | 2025-07-28 18:40:05 |
| Step 6 | ✅ Completed | 2025-07-28 18:40:41 |
| Step 7 | ✅ Completed | 2025-07-28 18:42:37 |
| Step 8 | ✅ Completed | 2025-07-28 18:43:24 |
| Step 9 | ✅ Completed | 2025-07-28 18:43:46 |
| Step 10 | ✅ Completed | 2025-07-28 18:44:21 |
| Step 11 | ✅ Completed | 2025-07-28 18:45:00 |
| Step 12 | ✅ Completed | 2025-07-28 18:45:36 |
| Step 13 | ✅ Completed | 2025-07-28 18:45:43 |
| Step 14 | ✅ Completed | 2025-07-28 18:47:51 |

## Notes & Issues

### Errors Encountered
- Step 7: Build failed at test phase. The error is due to ambiguity/incompatible types for any(Alumni.class). The class imports both org.mockito.ArgumentMatchers.any and org.hamcrest.Matchers.any creating ambiguity. Need to only import ArgumentMatchers.any for Mockito usage in AlumniControllerTest.

### Important Decisions
- Step 14: React app built, linted, and all Jest tests for AlumniProfile, AlumniSearch, and AlumniList pass successfully.

### Next Actions
- Begin implementation following the checklist
- Use `update_plan_checklist_tool` to mark steps as completed
- Use `read_plan_checklist_tool` to check current status

### Important Instructions
- Don't Leave any placeholders in the code.
- Do NOT mark compilation and testing as complete unless EVERY test case is passing. Double-check that all test cases have passed successfully before updating the checklist. If even a single test case fails, compilation and testing must remain incomplete.
- Do not mark the step as completed until all the sub-steps are completed.

---
*This checklist is automatically maintained. Update status as you complete each step using the provided tools.*