## 📌 Folder Purpose & Simple Situations

### 🏁 `app` — **Main Entry Point**
- **Purpose:** The starting point of the entire application.
- **Example:** Imagine turning on a computer — this is like pressing the power button.
- 🧑 *User Story:* “As a developer, I want to run the system by executing just one file (`App.java`) so I can easily launch the whole application.”

---

### 🖼️ `app.view` — **GUI Layer (User Interface)**
- **Purpose:** Contains all Swing forms such as login screens, dashboards, dialogs, and pop-ups.
- **Example:** If the system is a restaurant, this is the **front counter** where customers interact.
- 🧑 *User Story:* “As a user, I want to see buttons and forms so I can interact with the system visually.”

---

### 🧠 `app.controller` — **Logic Layer**
- **Purpose:** Connects the GUI (`view`) with the data (`model`). Handles actions like button clicks and form submissions.
- **Example:** Like the cashier who takes your order and sends it to the kitchen.
- 🧑 *User Story:* “As a developer, I want my logic to be separate from the UI so I can change how the system works without breaking the design.”

---

### 📄 `app.model` — **Data Models**
- **Purpose:** Defines the structure and behavior of data objects (e.g., `User`, `Product`, `Order`).
- **Example:** Like a **book record card** containing title, author, and ISBN.
- 🧑 *User Story:* “As a developer, I want well-defined models so I can easily pass and manage data across the system.”

---

### 🪛 `app.util` — **Utilities / Helpers**
- **Purpose:** Contains reusable helper functions like validation, formatting, date/time handling, and file utilities.
- **Example:** Like a **kitchen knife** used in many recipes — one tool, many uses.
- 🧑 *User Story:* “As a developer, I want common functions in one place to avoid repeating the same code everywhere.”

---

### ⚙️ `app.config` — **Configuration & Settings**
- **Purpose:** Stores app-wide settings and constants (e.g., app name, version info, or database connection string if applicable).
- **Example:** Like a **menu board** in a restaurant — one place to check all important info.
- 🧑 *User Story:* “As a developer, I want a single location to manage important settings so I don’t have to search through multiple files.”

---

## 🧭 Recommended Project Flow

1. `App.java` starts the program.
2. GUI (`view`) displays the forms.
3. User interacts with the UI → event is passed to the `controller`.
4. `controller` processes the action and communicates with `model`.
5. Common logic is handled by `util`.
6. Configurations and constants are stored in `config`.

---

## 🚀 Example Scenario

- 👤 A user clicks **Login** in `LoginView.java` (inside `view`).
- 🧠 `AuthController.java` (inside `controller`) processes the login request.
- 📄 `User.java` (inside `model`) holds the user data structure.
- ✅ If credentials are valid → the app opens `DashboardView.java`.