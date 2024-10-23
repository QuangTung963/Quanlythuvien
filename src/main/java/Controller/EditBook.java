package Controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import BO.BookBO;
import BO.CategoryBO;
import Bean.Book;
import Bean.Category;

@MultipartConfig
@WebServlet("/EditBook")
public class EditBook extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryBO categoryBO = new CategoryBO();
    private BookBO bookBO = new BookBO();

    public EditBook() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("User") == null) {
            String errorString = "Bạn cần đăng nhập trước";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            String id = request.getParameter("id");

            Category category = null;

            String errorString = null;
            Book book = new Book();
            ArrayList<Category> list = null;
            try {
                book = bookBO.findBook(id);
                list = categoryBO.listCategory();

            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (request.getAttribute("errorString") != null) {
                errorString = (String) request.getAttribute("errorString");
            }
            request.setAttribute("book", book);
            request.setAttribute("categoryList", list);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/edit_book.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String category_id = request.getParameter("category");
        String count = request.getParameter("count");
        String tacgia = request.getParameter("tacgia");
        String nhaxuatban = request.getParameter("nhaxuatban"); // Lấy giá trị nhà xuất bản từ request
        String fileName = request.getParameter("image_str");
        Part file = request.getPart("fileImage");
        String errorString = null;

        // Code xử lý upload file ảnh...

        Category category = new Category();
        try {
            category = categoryBO.findCategory(category_id);
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setCategory(category);
        book.setAmount(count);
        book.setImage(fileName);
        book.setTacgia(tacgia);
        book.setNhaxuatban(nhaxuatban); // Gán giá trị nhà xuất bản cho sách

        try {
            int result = bookBO.updateBook(book);
            if (result == 1) {
                errorString = "Chỉnh sửa thành công";
            } else {
                errorString = "Chỉnh sửa thất bại";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        doGet(request, response);
    }


    private String extractfilename(Part file) {
        String cd = file.getHeader("content-disposition");
        String[] items = cd.split(";");
        for (String string : items) {
            if (string.trim().startsWith("filename")) {
                return string.substring(string.indexOf("=") + 2, string.length() - 1);
            }
        }
        return "";
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }
}
