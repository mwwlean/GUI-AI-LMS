# 📌 Folder Purpose & Simple Situations

### 🏁 `App.java` — **Main Entry Point**
- **Purpose:** The starting point of the entire application.
- **Example:** Imagine turning on a computer — this is like pressing the power button.
- 🧑 *User Story:* “As a developer, I want to run the system by executing just one file (`Main.java`) so I can easily launch the whole application.”

---

### 🖼️ `app.view` — **GUI Layer (User Interface)**
- **Purpose:** Contains all Swing forms such as input fields, buttons, labels, and dialogs.
- **Example:** If the system is a greeting app, this is the **window where the user types their name and clicks Greet**.
- 🧑 *User Story:* “As a user, I want to see text boxes and buttons so I can interact with the system visually.”

---

### 🧠 `app.controller` — **Logic Layer**
- **Purpose:** Connects the GUI (`view`) with the data (`model`). Handles actions like button clicks.
- **Example:** Like a **receptionist** who takes user input from the front desk and passes it to the system behind the scenes.
- 🧑 *User Story:* “As a developer, I want my logic separate from the UI so I can change how the system works without affecting the interface.”

---

### 📄 `app.model` — **Data Models**
- **Purpose:** Defines the structure and behavior of data objects (e.g., `User` storing name).
- **Example:** Like a **name card** holding user information.
- 🧑 *User Story:* “As a developer, I want clear data structures so I can easily manage and pass data across the system.”

---

### 🪛 `resources` — **Utilities / Helpers**
- **Purpose:** Stores resources like images, icons, or text files for the app.
- **Example:** Like a **drawer of office supplies** that the app can use whenever needed.
- 🧑 *User Story:* “As a developer, I want reusable resources in one place to keep my project organized.”

---

### ⚙️ `pom.xml` — **Configuration & Build Settings**
- **Purpose:** Contains Maven project info, dependencies, and build instructions.
- **Example:** Like a **recipe book** telling the system how to build and run itself.
- 🧑 *User Story:* “As a developer, I want a single configuration file to manage dependencies and project settings.”

---

## 🧭 Recommended Project Flow

1. `Main.java` starts the program.  
2. GUI (`view`) displays the window with input and buttons.  
3. User types their name and clicks **Greet** → event is sent to `controller`.  
4. `controller` updates the `model` with the input data.  
5. `model` stores the data.  
6. `controller` tells the `view` to update the message label with the greeting.  

---

## 🚀 Example Scenario

- 👤 A user types **"Jan"** in the text box.  
- 🧠 `UserController.java` processes the input and updates the `User` model.  
- 📄 `User.java` holds the name "Jan".  
- ✅ `UserView.java` updates the label to display: **"Hello, Jan!"**.
