# TASK NOTE API

## Key Features Implemented:
### 1. Authentication & Security

JWT-based authentication with register/login endpoints
Password encryption using BCrypt
Protected routes requiring authentication
User-specific data access (users can only see their own tasks/notes)

### 2. Task Management

Full CRUD operations for tasks
Task filtering by status
Due date support
Priority levels (LOW, MEDIUM, HIGH, URGENT)
Task status tracking (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)

### 3. Note Management

Add notes to tasks
Full CRUD operations for notes
Notes are tied to specific tasks and users

### 4.️ Security & Validation

Input validation with proper error messages
Global exception handling
JWT token validation
User authorization checks

### 5. Database

Enhanced entity relationships
Proper foreign key constraints
Automatic timestamp management
PostgreSQL support
```
API Endpoints:
Authentication

POST /api/auth/register - Register new user
POST /api/auth/login - Login user

Users

GET /api/users/me - Get current user info
GET /api/users - Get all users (admin)

Tasks

POST /api/tasks - Create task
GET /api/tasks - Get all tasks (with optional status filter)
GET /api/tasks/{id} - Get specific task
PUT /api/tasks/{id} - Update task
DELETE /api/tasks/{id} - Delete task

Notes

POST /api/tasks/{taskId}/notes - Add note to task
GET /api/tasks/{taskId}/notes - Get all notes for task
PUT /api/tasks/{taskId}/notes/{noteId} - Update note
DELETE /api/tasks/{taskId}/notes/{noteId} - Delete note

To get started:

Database Setup: Create a PostgreSQL database and update the connection details in application.yml
Run the Application: The API will be available at http://localhost:8080
Test Authentication:

Register: POST /api/auth/register
Login: POST /api/auth/login
Use the returned JWT token in the Authorization: Bearer <token> header for protected endpoints


Create Tasks: Use the JWT token to create and manage tasks and notes
```