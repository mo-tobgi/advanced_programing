# JavaFX Application

Welcome to the JavaFX Application! This README will guide you through the setup process to get the application running on your local machine.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK)**: Download and install the latest JDK from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).
- **JavaFX SDK**: Download the JavaFX SDK from [Gluon's official website](https://gluonhq.com/products/javafx/).
- **Integrated Development Environment (IDE)**: Use a suitable IDE like [Eclipse](https://www.eclipse.org/downloads/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

## Installation

### 1. Set Up JDK

1. Download and install the JDK.
2. Set up the environment variables:
   - **Windows**:
     1. Open System Properties (Right-click on My Computer -> Properties).
     2. Click on Advanced system settings.
     3. Click on Environment Variables.
     4. Under System variables, click New and add a variable `JAVA_HOME` with the path to your JDK.
     5. Edit the `Path` variable and add `%JAVA_HOME%\bin`.
   - **Mac/Linux**:
     1. Open a terminal window.
     2. Open the `.bash_profile` or `.bashrc` file in a text editor.
     3. Add the line `export JAVA_HOME=/path/to/your/jdk`.
     4. Add `export PATH=$JAVA_HOME/bin:$PATH`.

### 2. Set Up JavaFX SDK

1. Download the JavaFX SDK.
2. Extract the downloaded file to a desired location.
3. Set up the environment variables:
   - **Windows**:
     1. Add a new system variable `PATH_TO_FX` with the path to the `lib` directory of your JavaFX SDK.
   - **Mac/Linux**:
     1. Add `export PATH_TO_FX=/path/to/your/javafx-sdk/lib` to your `.bash_profile` or `.bashrc` file.

### 3. Clone the Repository

1. Open your terminal or command prompt.
2. Clone the repository using the following command:
   ```bash
   git clone https://github.com/mo-tobgi/advanced_programing.git
3. Navigate to the project directory:
   ```bash
   cd your-repo
### 4. Open the Project in Your IDE

#### Eclipse

1. Open Eclipse and go to `File -> Open Projects from File System...`.
2. Click on `Directory` and select the cloned project directory.
3. Click `Finish`.

#### IntelliJ IDEA

1. Open IntelliJ IDEA and go to `File -> Open`.
2. Select the cloned project directory and click `OK`.
3. If prompted to import the project, select `Import project from external model`.

### 5. Configure JavaFX in Your IDE

#### Eclipse

1. Right-click on the project and select `Properties`.
2. Go to `Java Build Path` and click on the `Libraries` tab.
3. Click `Add External JARs...` and navigate to the `lib` directory of your JavaFX SDK. Select all the `.jar` files.
4. Apply and close.

#### IntelliJ IDEA

1. Go to `File -> Project Structure`.
2. Select `Modules` and click the `Dependencies` tab.
3. Click the `+` button and select `JARs or directories`.
4. Navigate to the `lib` directory of your JavaFX SDK and select all the `.jar` files.
5. Apply and close.

### 6. Run the Application

1. Locate the main class of your application (usually contains the `main` method).
2. Right-click on the class file and select `Run` or `Debug` to start the application.

## YouTube Link

https://youtu.be/QHGTX6LIJEg

## Contributing

If you want to contribute to this project, please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
