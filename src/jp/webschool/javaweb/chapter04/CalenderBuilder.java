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



}
