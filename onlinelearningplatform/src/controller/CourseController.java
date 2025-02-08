package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import exception.InvalidCredentialsException;
import exception.QuizNotFoundException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import model.Course;
import model.Lesson;
import model.Question;
import model.User;
import model.Quiz;
import service.CourseService;
import service.EnrollmentService;
import service.LessonService;
import service.QuizService;
import service.UserService;

public class CourseController {
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final QuizService quizService;
    private final LessonService lessonService;
    private final UserService userService;
    private final BufferedReader br;
    private boolean registered = false;

    public CourseController() {
        this.courseService = new CourseService();
        this.enrollmentService = new EnrollmentService();
        this.quizService = new QuizService();
        this.lessonService = new LessonService();
        this.userService = new UserService();
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() throws NumberFormatException, IOException, SQLException, UserAlreadyExistsException, QuizNotFoundException, InvalidCredentialsException, UserNotFoundException {
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(br.readLine());
            
            if (!registered && choice != 1 && choice != 2 && choice != 0) {
                System.out.println("Please login first by choosing option 1.");
                continue;
            }

            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    registerUser();
                    break;
                case 3:
                    viewCourses();
                    break;
                case 4:
                    enrollInCourse();
                    break;
                case 5:
                    viewProgress();
                    break;
                case 6:
                    takeQuiz();
                    break;

                case 7:
                    takeLesson();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--- Course Management Menu ---");
        if (!registered) {
            System.out.println("1. Login");
            System.out.println("2. Register User");
        } else {
            System.out.println("3. View Courses");
            System.out.println("4. Enroll in Course");
            System.out.println("5. View Progress");
            System.out.println("6. Take Quiz");
            System.out.println("7. Take Lesson");
        }
        System.out.println("0. Exit");
    }

    private void loginUser() throws IOException, SQLException, InvalidCredentialsException, UserNotFoundException {
        System.out.print("Enter Email: ");
        String email = br.readLine();
        System.out.print("Enter Password: ");
        String password = br.readLine();

        User user = userService.loginUser(email, password);
        if (user != null) {
            registered = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void registerUser() throws IOException, SQLException, UserAlreadyExistsException {
        System.out.print("Enter User ID: ");
        int userId = Integer.parseInt(br.readLine());
        System.out.print("Enter Name: ");
        String name = br.readLine();
        System.out.print("Enter Email: ");
        String email = br.readLine();
        System.out.print("Enter Password: ");
        String password = br.readLine();
        System.out.print("Enter Progress: ");
        String progress = br.readLine();

        userService.registerUser(new User(userId, name, email, password, progress));
        registered = true;
        System.out.println("User registered successfully!");
    }

    private void viewCourses() throws IOException, SQLException {
        System.out.print("Enter the type of course to view (1 for Basic, 2 for Advanced): ");
        int courseType = Integer.parseInt(br.readLine());

        List<Course> courses = courseService.getCoursesByType(courseType);

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                System.out.println("Course ID: " + course.getCourseId());
                System.out.println("Title: " + course.getCourseName());
                System.out.println("Description: " + course.getDescription());
                System.out.println("Level: " + course.getLevel());
                System.out.println("Duration: " + course.getDuration() + " hours");
                course.getCourseDetails();
                System.out.println("-----------------------------------");
            }
        }
    }

    private void enrollInCourse() throws IOException, SQLException {
        System.out.print("Enter Course ID to enroll in: ");
        int courseId = Integer.parseInt(br.readLine());
        enrollmentService.enrollUserInCourse(userService.getCurrentUserId(), courseId);
        System.out.println("User enrolled in the course successfully!");
    }

    private void viewProgress() throws IOException, SQLException {
        int userId = userService.getCurrentUserId();
        List<Course> enrolledCourses = enrollmentService.getUserEnrollments(userId);

        if (enrolledCourses.isEmpty()) {
            System.out.println("No enrollments found for the given user.");
        } else {
            for (Course course : enrolledCourses) {
                int lessonsCompleted = courseService.getLessonsCompleted(userId, course.getCourseId());
                int quizzesCompleted = courseService.getQuizzesCompleted(userId, course.getCourseId());
                System.out.println("Course: " + course.getCourseName());
                System.out.println("Lessons Completed: " + lessonsCompleted);
                System.out.println("Quizzes Completed: " + quizzesCompleted);
                System.out.println("-----------------------------------");
            }
        }
    }

    private void takeLesson() throws IOException, SQLException {
//        int userId = userService.getCurrentUserId();
        
        // Ask user to enter courseId and lessonId
        System.out.print("Enter Course ID for the lesson: ");
        int courseId = Integer.parseInt(br.readLine());

        System.out.print("Enter Lesson ID to view: ");
        int lessonId = Integer.parseInt(br.readLine());

        // Get lesson details based on courseId and lessonId
        Lesson lesson = lessonService.getLessonById(lessonId);

        // Check if the lesson exists for the given course
        if (lesson != null && lesson.getCourseId() == courseId) {
            System.out.println("Lesson '" + lesson.getLessonTitle() + "' found in course " + courseId);
            System.out.println("Lesson Content: " + lesson.getContent());
            System.out.println("Lesson completed successfully!");
        } else {
            System.out.println("Lesson not found for the provided Course ID or Lesson ID.");
        }
    }


    private void takeQuiz() throws IOException, SQLException, QuizNotFoundException {
        int userId = userService.getCurrentUserId();
        List<Quiz> quizzes = quizService.getAllQuizzes();
        
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("\n--- Available Quizzes ---");
        for (Quiz quiz : quizzes) {
            System.out.println("Quiz ID: " + quiz.getQuizId());
            System.out.println("Title: " + quiz.getQuizTitle());
            System.out.println("-----------------------------------");
        }

        System.out.print("Enter Quiz ID to take: ");
        int quizId = Integer.parseInt(br.readLine());

        // Fetch questions for the selected quiz
        List<Question> questions = quizService.getQuestionsForQuiz(quizId);

        if (questions.isEmpty()) {
            System.out.println("No questions found for this quiz.");
            return;
        }

        // Display questions and capture answers
        int score = 0;
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            System.out.println("A. " + question.getOptionA());
            System.out.println("B. " + question.getOptionB());
            System.out.println("C. " + question.getOptionC());
            System.out.println("D. " + question.getOptionD());

            System.out.print("Enter your answer (A, B, C, D): ");
            char answer = br.readLine().toUpperCase().charAt(0);

            if (answer == question.getCorrectOption()) {
                score++;
            }
        }

        // Submit the quiz result
        quizService.submitQuiz(userId, quizId, score);
        System.out.println("Quiz completed successfully! Your score: " + score);
    }

    
}
