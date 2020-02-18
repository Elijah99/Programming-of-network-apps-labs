import java.util.regex.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SimpleServlet extends HttpServlet {
    //создаем строку, в которой будем искать слово

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");/* Устанавливает тип контента ответа, отправляемого клиенту, если ответ еще не фиксировался.*/
        request.setCharacterEncoding("UTF-8"); /* Устанавливает кодировку символов (набор символов MIME) ответа, отправляемого клиенту, например, UTF-8.*/
        PrintWriter out = response.getWriter();/*Возвращает в PrintWriter объект, который может отправить символьный текст клиенту.*/
        String find=request.getParameter("Find");/* возвращает значение параметров в форматеString */

        int temp = Integer.parseInt(find);

        String redStyle = "<style>\n" +
                "    body { \n" +
                "     font-family: Arial, sans-serif;\n" +
                "    }\n" +
                "    .letter { \n" +
                "     color: red; /* Красный цвет символов */ \n" +
                "     font-size: 200%; /* Размер шрифта в процентах */ \n" +
                "     font-family: serif;  \n" +
                "     position: relative; /* Относительное позиционирование */ \n" +
                "    }\n" +
                "  </style> ";

        String blueStyle = "<style>\n" +
                "    body { \n" +
                "     font-family: Arial, sans-serif;\n" +
                "    }\n" +
                "    .letter { \n" +
                "     color: blue; /* Синий цвет символов */ \n" +
                "     font-size: 200%; /* Размер шрифта в процентах */ \n" +
                "     font-family: serif;  \n" +
                "     position: relative; /* Относительное позиционирование */ \n" +
                "    }\n" +
                "  </style> ";

        String greenStyle = "<style>\n" +
                "    body { \n" +
                "     font-family: Arial, sans-serif;\n" +
                "    }\n" +
                "    .letter { \n" +
                "     color: green; /* Зеленый цвет символов */ \n" +
                "     font-size: 200%; /* Размер шрифта в процентах */ \n" +
                "     font-family: serif;  \n" +
                "     position: relative; /* Относительное позиционирование */ \n" +
                "    }\n" +
                "  </style> ";

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Text Redactor Server</title>");
            if(temp>0) out.println(redStyle);
            else if(temp == 0) out.println(greenStyle);
                else out.println(blueStyle);
            out.println("</head>");
            out.println("<body>");
            out.println("<center>Your temperature:  <span class=\"letter\">" + find + "</span></center>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
