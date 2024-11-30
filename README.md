# **Tondeuse Simulation Project**

This project simulates the movement of one or more robotic lawn mowers (`Tondeuse`) on a rectangular grid. The mowers can receive commands (`A`, `D`, `G`) to move forward or rotate, and the application validates and processes these commands, displaying the final positions and orientations.

---

## **Features**
- Simulate multiple robotic lawn mowers on a grid.
- Accept user inputs for grid dimensions, initial mower positions, orientations, and commands.
- Validate inputs to ensure commands and orientations are correct.
- Visualize results in a browser with charts and detailed final positions.
- Handle errors gracefully and display meaningful messages to users.

---

## **Prerequisites**

Before running the project, ensure you have the following installed:

1. **Java Development Kit (JDK)**:
   - Version: 8 or later
   - [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

2. **Scala**:
   - Version: 2.13.x
   - [Download Scala](https://www.scala-lang.org/download/)

3. **SBT (Scala Build Tool)**:
   - Version: 1.5.0 or later
   - [Download SBT](https://www.scala-sbt.org/download.html)

4. **Node.js and npm** (Optional if running front-end builds):
   - [Download Node.js](https://nodejs.org/)

---

## **Setup Instructions**

### 1. **Clone the Repository**
Clone this repository to your local machine:
```bash
git https://github.com/aidamounir/toudeuse-scala.git
cd tondeuse-simulation
```

### 2. **Install Dependencies**
Ensure you have all dependencies installed:
```bash
sbt update
```

### 3. **Run the Application**
Run the Play Framework server:
```bash
sbt run
```

The application will be available at:
```
http://localhost:9000
```

---

## **Application Usage**

1. **Access the Application**:
   Open your browser and go to:
   ```
   http://localhost:9000
   ```

2. **Input Parameters**:
   - Enter the grid dimensions (`Max X` and `Max Y`).
   - Add one or more `Tondeuse` entries:
     - Provide the initial position (e.g., `1 2 N`).
     - Enter a sequence of commands (e.g., `AADADG`).

3. **Run Simulation**:
   - Click **Run Simulation** to process the commands.
   - View the final positions in the **Final Positions** section and the paths in the chart.

---

## **Error Handling**

- If invalid commands or orientations are provided, the application will display an error message in a popup.
- Examples of errors:
  - Invalid command: `Invalid command: X. Only 'A', 'D', and 'G' are allowed.`
  - Invalid orientation: `Invalid orientation: Z. Only 'N', 'W', 'E', 'S' are allowed.`

---

## **Folder Structure**

```
tondeuse-simulation/
├── app/
│   ├── controllers/       # Application controllers
│   ├── models/            # Core business logic for Tondeuse
│   ├── views/             # HTML templates
├── conf/
│   ├── application.conf   # Play Framework configurations
│   ├── routes             # HTTP route mappings
├── public/
│   ├── stylesheets/       # CSS files
│   ├── javascripts/       # JavaScript files
│   ├── images/            # Static images
├── project/               # SBT project files
├── test/                  # Test cases
├── build.sbt              # SBT build definition
└── README.md              # Project documentation
```

---

## **Testing**

### **Run Unit Tests**
The project includes unit tests to validate the logic for `Tondeuse`. Run the tests with:
```bash
sbt test
```

### **Test Cases**
1. **Valid Input**:
   - Grid: `5 x 5`
   - Mowers:
     - `1 2 N`, Commands: `AADADG`
     - `3 3 E`, Commands: `AAGADA`

2. **Invalid Commands**:
   - Commands: `AAX`
   - Expected: Error message in the response.

3. **Invalid Orientation**:
   - Orientation: `Z`
   - Expected: `Invalid orientation: Z. Only 'N', 'W', 'E', 'S' are allowed.`

---

## **Known Issues**

- If the server crashes unexpectedly, ensure that all dependencies are correctly installed.
- If no chart appears, check the JavaScript console for errors and ensure your browser supports modern ES6 features.
