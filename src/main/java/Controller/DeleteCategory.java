package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BO.CategoryBO;

@WebServlet("/DeleteCategory")
public class DeleteCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryBO categoryBO = new CategoryBO();

	public DeleteCategory() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = (String) request.getParameter("id");
		boolean result;
		try {
			result = categoryBO.deleteCategory(id);
			System.out.println("Ket qua"+result);
			if (result == true) {
				request.setAttribute("errorString", "Đã xóa thành công");
			} else {
				request.setAttribute("errorString", "Lỗi cơ sở dữ liệu");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ManageCategory");
		dispatcher.forward(request, response);
		response.sendRedirect(request.getContextPath() + "/ManageCategory");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
