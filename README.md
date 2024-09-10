# Password Manager

Password Manager: A secure and user-friendly application built with the Spring Boot framework.
It is designed to store and manage unique passwords so that you do not have to remember them.

### How to run the server locally:

- Clone the git repository from the Github to your local machine. You can do this by

  ```
  git clone https://github.com/Atharva0418/pwm-backend.git
  ```

- Install java 21 from [Oracle official website](https://www.oracle.com/in/java/technologies/downloads/#jdk21-windows). Download according to your OS. It is required to compile and run the project.

- Install Gradle from [Gradle official website](https://gradle.org/install/#manually). Download the files according to your OS (click on "Binary-Only" to download). It is used for compiling, testing and packaging applications.

- To get it to live reload you need to have 2 terminals open. In first terminal, goto root of the project.

- Build gradle: Run the below command. It compiles the project's source code, downloads necessary dependencies and builds the application. Keep this terminal open as it looks for changes.

  ```
  gradle build --continuous
  ```

- Bootrun with gradle: Open second terminal, goto root of the project. Run the below command, it runs the Spring boot application. It starts the server locally on your machine.

  ```
  gradle bootRun
  ```
### How to setup MySql database:

- Install MySql(v8.0.39)(might change in the future) from [MySql official website](https://dev.mysql.com/downloads/mysql/)
Download according to your OS.

- Create a MySql server with username and password.

- Add the following in your .env file:

  - DB_URL=jdbc:mysql://localhost:<your_portnumber>/<your_database_name>
  - DB_USERNAME=<your_username>
  - DB_PASSWORD=<your_password>
  - DB_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

- Bootrun with gradle: Run the application with this command.

  ```
  gradle bootRun
  ```