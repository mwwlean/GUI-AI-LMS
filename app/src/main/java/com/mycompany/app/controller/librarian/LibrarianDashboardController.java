package com.mycompany.app.controller.librarian;

import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.mycompany.app.model.Book;
import com.mycompany.app.model.BookModel;
import com.mycompany.app.view.librarian.BookManagementPanel;
import com.mycompany.app.view.librarian.LibrarianDashboardView;
import com.mycompany.app.view.librarian.PendingRequestsPanel;

public class LibrarianDashboardController {

    private final LibrarianDashboardView dashboardView;
    private final PendingRequestsPanel requestsPanel;
    private final BookManagementPanel booksPanel;
    private List<Book> books;
    private final BookModel bookModel = new BookModel();

    public LibrarianDashboardController(LibrarianDashboardView dashboardView) {
        this.dashboardView = dashboardView;

        // Initialize panels
        requestsPanel = new PendingRequestsPanel();
        booksPanel = new BookManagementPanel();

        // Add panels to main dashboard
        dashboardView.mainPanel.add(requestsPanel, "requests");
        dashboardView.mainPanel.add(booksPanel, "books");

        // Load initial book list
        books = bookModel.getAllBooks();
        updateBookList();

        // Sidebar navigation
        dashboardView.btnRequests.addActionListener(e ->
                dashboardView.cardLayout.show(dashboardView.mainPanel, "requests"));
        dashboardView.btnBooks.addActionListener(e ->
                dashboardView.cardLayout.show(dashboardView.mainPanel, "books"));
        dashboardView.btnLogout.addActionListener(e -> logout());

        // Book Management Actions
        booksPanel.btnAdd.addActionListener(e -> addBook());
        booksPanel.btnDelete.addActionListener(e -> deleteBook());
        booksPanel.btnEdit.addActionListener(e -> editBook());
    }

    // 🔄 Refresh JTable
    private void updateBookList() {
        booksPanel.tableModel.setRowCount(0); // Clear rows first
        for (Book b : books) {
            String status = b.isAvailable() ? "Available" : "Borrowed";
            // Show only Title and Status in the table
            booksPanel.tableModel.addRow(new Object[]{b.getTitle(), status});
        }
    }

    // 🎨 Set consistent dialog style
    private void setDialogTheme() {
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", new Color(142, 1, 0)); // maroon text
        UIManager.put("Button.background", new Color(255, 193, 7)); // yellow button
        UIManager.put("Button.foreground", new Color(142, 1, 0)); // maroon button text
    }

    // ➕ Add a new book
    private void addBook() {
        setDialogTheme();
        String title = booksPanel.txtTitle.getText().trim();
        String author = booksPanel.txtAuthor.getText().trim();

        if (title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(dashboardView, "⚠️ Please fill in both Title and Author.");
            return;
        }

        // Check for duplicate title
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                JOptionPane.showMessageDialog(dashboardView, "⚠️ This book title already exists!");
                return;
            }
        }

        Book newBook = new Book(title, author, true);
        bookModel.addBook(newBook);
        books = bookModel.getAllBooks();
        updateBookList();

        JOptionPane.showMessageDialog(dashboardView, "✅ Book added successfully!");
        booksPanel.txtTitle.setText("");
        booksPanel.txtAuthor.setText("");
    }

    // 🗑 Delete selected book
    private void deleteBook() {
        setDialogTheme();
        int row = booksPanel.bookTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(dashboardView, "⚠️ Please select a book to delete.");
            return;
        }

        String title = (String) booksPanel.tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(
                dashboardView,
                "Are you sure you want to delete \"" + title + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            bookModel.deleteBook(title);
            books = bookModel.getAllBooks();
            updateBookList();
            JOptionPane.showMessageDialog(dashboardView, "🗑️ Book deleted successfully!");
        }
    }

    // ✏️ Edit selected book
    private void editBook() {
        setDialogTheme();
        int row = booksPanel.bookTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(dashboardView, "⚠️ Please select a book to edit.");
            return;
        }

        String oldTitle = (String) booksPanel.tableModel.getValueAt(row, 0);
        Book selectedBook = bookModel.getBookByTitle(oldTitle);

        if (selectedBook == null) {
            JOptionPane.showMessageDialog(dashboardView, "⚠️ Selected book not found in the database.");
            return;
        }

        // You can edit both Title and Author even if Author is not shown in the table
        String newTitle = JOptionPane.showInputDialog(dashboardView, "Enter new title:", selectedBook.getTitle());
        if (newTitle == null || newTitle.trim().isEmpty()) {
            JOptionPane.showMessageDialog(dashboardView, "⚠️ Title cannot be empty.");
            return;
        }

        String newAuthor = JOptionPane.showInputDialog(dashboardView, "Enter new author:", selectedBook.getAuthor());
        if (newAuthor == null || newAuthor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(dashboardView, "⚠️ Author cannot be empty.");
            return;
        }

        bookModel.updateBook(oldTitle, newTitle.trim(), newAuthor.trim());
        books = bookModel.getAllBooks();
        updateBookList();
        JOptionPane.showMessageDialog(dashboardView, "✏️ Book updated successfully!");
    }

    // 🚪 Logout
    private void logout() {
        setDialogTheme();
        JOptionPane.showMessageDialog(dashboardView, "👋 Logged out successfully!");
        System.exit(0);
    }
}
