                            Password Manager
This Password Manager project is a secure and user-friendly application built with the Spring Boot framework.
It is designed to store and manage unique passwords so that you do not have to remember them.

How to run the server locally:

    1. Clone the git repository from the Github to your local machine. You can do this by "git clone <repo url>" command.

    2. Install java 21 from [Oracle official website](https://www.oracle.com/in/java/technologies/downloads/#jdk21-windows). Download according to your OS. It is required to compile and run the project.

    3. Install Gradle from [Gradle official website](https://gradle.org/install/#manually). Download the files according to your OS(click on "Binary-Only" to download). It is used for compiling, testing and packaging applications.

    4. Build gradle.

    ```bash
        gradle build
    ```
    Run this command.It compiles the project's source code, downloads necessary dependencies and builds the application.

    5. Bootrun with gradle.

    ```bash
        gradle bootRun 
    ```
    This command runs the Spring boot application. It starts the server locally on your machine.

