package jp.webschool.javaweb.chapter04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalenderBuilder
 */
public class CalenderBuilder extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalenderBuilder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));

		String title = year + "年" + month + "月";
		String css = "/w04method/calender.css";
		int[] calenderArray = buildCalenderArray(year, month -1);
		StringBuilder calenderTable = buildCalenderTable(calenderArray);
		StringBuilder html = buildHtml(title, css, calenderTable);

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(html);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));

		String title = year + "年" + month + "月";
		String css = "/w04method/calender.css";
		int[] calenderArray = buildCalenderArray(year, month -1);
		StringBuilder calenderTable = buildCalenderTable(calenderArray);
		StringBuilder html = buildHtml(title, css, calenderTable);

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(html);

	}

	private int[] buildCalenderArray(int year, int month){
		Calendar calendar = Calendar.getInstance();

		//指定された月の1日の曜日
		calendar.set(year, month, 1);
		int currentMonthFirstWeek = calendar.get(Calendar.DAY_OF_WEEK);

		//指定された月の月末
		calendar.set(year, month + 1, 0);
		int currentMonthLastDay = calendar.get(Calendar.DATE);

		//前月の末日
		calendar.set(year, month, 0);
		int previousMonthLastDay = calendar.get(Calendar.DATE);

		int[] calenderArray = new int[42];
		int calendarIndex = 0;
		int bias = 35;

		for(int i = 0; i < currentMonthFirstWeek - 1; i++, calendarIndex++){
			calenderArray[calendarIndex] = previousMonthLastDay - currentMonthFirstWeek + i + 2 + bias;
		}

		for(int i = 0; i < currentMonthLastDay; i++, calendarIndex++){
			calenderArray[calendarIndex] = i + 1;
		}

		for(int i = 0; calendarIndex % 7 != 0; i++, calendarIndex++){
			calenderArray[calendarIndex] = i + 1 + bias;
		}

		return calenderArray;

	}

	private StringBuilder buildCalenderTable(int[] calenderArray){
		StringBuilder sb = new StringBuilder();
		String[] dayOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		int bias = 35;

		sb.append("<table>");
		sb.append("<tr>");

		for(int i = 0; i < dayOfWeek.length; i++){

			if(i == 0){
				sb.append("<th class=\"sun\">" + dayOfWeek[i] + "</th>");
			} else if(i == 6){
				sb.append("<th class=\"sat\">" + dayOfWeek[i] + "</th>");
			} else {
				sb.append("<th>" + dayOfWeek[i] + "</th>");
			}

		}
		sb.append("</tr>");

		for(int i = 0; i < calenderArray.length && calenderArray[i] != 0;){
		sb.append("<tr>");
		for(int j = 0; j < 7; i++, j++){
			if(calenderArray[i] > bias) {
				sb.append("<td class=\"otherMonth\">" + (calenderArray[i] - bias) + "</td>");
			} else if(i == 0 || i % 7 == 0){
				sb.append("<td class=\"sun\">" + calenderArray[i] + "</td>");
			} else if((i + 1) % 7 == 0){
				sb.append("<td class=\"sat\">" + calenderArray[i] + "</td>");
			} else {
				sb.append("<td>" + calenderArray[i] + "</td>");

			}
		}

		sb.append("</tr>");
		}


/*		for(int i = 0; i < calenderArray.length && calenderArray[i] != 0;){
			sb.append("<tr>");
			for(int j = 0; j < 7; i++, j++){
				if(calenderArray[i] > bias){
				sb.append("<td class=\"otherMonth\">" + (calenderArray[i] - bias) + "</td>");
				} else {
					sb.append("<td>" + calenderArray[i] + "</td>");
				}
			}
			sb.append("</tr>");
		}*/
		sb.append("</table>");

		return sb;

	}

	private StringBuilder buildHtml(String title, String css, StringBuilder contents){
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		sb.append("<title>" + title  + "</title>");
		sb.append("<link href=\"" + css + "\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<h1>" + title + "</h1>");
		sb.append(contents);
		sb.append("</body>");
		sb.append("</html>");

		return sb;

	}



}
