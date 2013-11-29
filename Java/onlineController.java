import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.DecimalFormat;

import beans.CartBean;

public class onlineController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		String strAction = request.getParameter("action");
		String strAction1 = request.getParameter("form");
		boolean flag=false;
		String type = "";

		pw.println("<html>");
		pw.println("<head>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("YOUR ORDER :");
		pw.println("<br>");

		
		if (strAction != null && !strAction.equals("")) {
			if (strAction.equals("add")) {
				if (strAction1 != null && !strAction1.equals("")) 
					type = strAction1;
				flag=addToCart(request, response, type);
				if(!flag)
					response.sendRedirect("../chip_orders.html");
				else{
				response.sendRedirect("../cart.jsp");}
			}
			if (strAction.equals("delete")) {
				deleteCart(request);
				response.sendRedirect("../cart.jsp");
			}

			
			
		}
	}

	public boolean addToCart(HttpServletRequest request,
			HttpServletResponse response, String type) throws ServletException, IOException {
		HttpSession session = request.getSession();

		boolean flag=false;
		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		// all price variables
		double fillingPrice = 0.0;
		double sidesPrice = 0.0;
		double drinksPrice = 0.0;
		double tacoPrice = 0.0;
		double total = 0.0;

		// filling / topping / rice / bean variables
		String filling = "";
		String toppings = "";
		String rice = "";
		String bean = "";

		String chicken = request.getParameter("chicken");
		String chickenx = request.getParameter("chicken_extra");
		String steak = request.getParameter("steak");
		String steakx = request.getParameter("steak_extra");
		String barbacoa = request.getParameter("barbacoa");
		String barbacoax = request.getParameter("barbacoa_extra");
		String carnitas = request.getParameter("carnitas");
		String carnitasx = request.getParameter("carnitas_extra");
		String veggie = request.getParameter("veggie");
		String guacamole = request.getParameter("guacamole");

		String veggies = request.getParameter("toppings1");
		String fresh_s = request.getParameter("toppings2");
		String corn_s = request.getParameter("toppings3");
		String green_s = request.getParameter("toppings4");
		String red_s = request.getParameter("toppings5");
		String cream = request.getParameter("toppings6");
		String cheese = request.getParameter("toppings7");
		String guacamole_t = request.getParameter("toppings8");
		String lettuce = request.getParameter("toppings9");

		// sides variables
		String sides = "";
		String chips = request.getParameter("chips");
		String c_g = request.getParameter("chips_guacamole");
		String c_fresh = request.getParameter("chips_fresh_tomato_salsa");
		String c_corn = request.getParameter("chips_roasted_chili_corn_salsa");
		String c_green = request.getParameter("chips_tomato_green_chili_salsa");
		String c_red = request.getParameter("chips_tomato_red_chili_salsa");

		// drinks variables
		String drinks = "";
		String water = request.getParameter("water");
		String n_apple = request.getParameter("nectar_apple");
		String n_peach = request.getParameter("nectar_peach");
		String n_orange = request.getParameter("nectar_orange");
		String n_cherry = request.getParameter("nectar_cherry");
		String i_blackberry = request.getParameter("izze_Blackberry");
		String i_clementine = request.getParameter("izze_clementine");
		String i_grape = request.getParameter("izze_grapefruit");
		String quantity ="";
		
		//taco types
		String tacoType = "";
		if(type.equals("Taco")) 
		{
			pw.println("inside");
		tacoType = request.getParameter("tacoType");
		pw.println("tacoType : "+tacoType);
		quantity = request.getParameter("quantity");
		pw.println("quantity : "+quantity);
		tacoType = tacoType+" - "+quantity; 
		}
		
		// database connection
		String url = "jdbc:mysql://localhost:3306/onlinesite";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, "root", "password");
			String query = "";
			st = con.createStatement();
			// fillings / toppings / rice / bean / filling price
			if (chicken != null) {
				flag=true;
				filling = filling + chicken + " ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ chicken + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (chickenx != null) {
				filling = filling + " (extra chicken) ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ chickenx + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (steak != null) {
				flag=true;
				filling = filling + "  " + steak + " ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ steak + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (steakx != null) {
				filling = filling + " (extra steak) ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ steakx + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (barbacoa != null) {
				flag=true;
				filling = filling + "  " + barbacoa + " ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ barbacoa + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (barbacoax != null) {
				filling = filling + " (extra barbacoa) ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ barbacoax + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (carnitas != null) {
				flag=true;
				filling = filling + "  " + carnitas + " ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ carnitas + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (carnitasx != null) {
				filling = filling + " (extra carnitas) ";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ carnitasx + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (veggie != null) {
				flag=true;
				filling = filling + "  " + veggie + "\n";
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ veggie + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
			}
			if (guacamole != null) {
				filling = filling + " (with guacamole) ";
			}
			//pw.println("Fillings are : " + filling);
		//	pw.println("<br>");
		//	pw.println("Rice option : " + request.getParameter("rice1"));
			rice = request.getParameter("rice1");
			//pw.println("<br>");
			//pw.println("Beans option : " + request.getParameter("bean1"));
			bean = request.getParameter("bean1");
			//pw.println("<br>");

			if (veggies != null)
				toppings = toppings + " Fajita Veggies ";
			if (fresh_s != null)
				toppings = toppings + " Fresh Tomato salsa sauce ";
			if (corn_s != null)
				toppings = toppings + " Fresh tomato corn salsa sauce ";
			if (green_s != null)
				toppings = toppings + " Green chili sauce ";
			if (red_s != null)
				toppings = toppings + " Red chili sauce ";
			if (cream != null)
				toppings = toppings + " Sour cream ";
			if (cheese != null)
				toppings = toppings + " Cheese ";
			if (guacamole_t != null) {
				Double price = 0.0;
				query = "select price from chipotle_menu where item = '"
						+ guacamole_t + "';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				fillingPrice = fillingPrice + price;
				toppings = toppings + " Extra Guacamole  ";
			}
			if (lettuce != null)
				toppings = toppings + " Lettuce, ";
			pw.println("Toppings are : ");
			pw.println("<br>");
			pw.println(toppings);
			pw.println("<br>");
			pw.println("Total cost for fillings is : " + fillingPrice);
			pw.println("<br>");
			//pw.println("</body>");
			//pw.println("</html>");

			//tacos
			if(!tacoType.equals("") && (chicken != null || veggie != null))
			{
				Double price = 0.0;
				query = "select price from chipotle_menu where item = 'taco_"+quantity+"_cv';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				tacoPrice = tacoPrice+price;
				pw.println("Taco : "+tacoType);
				pw.println("<br>");
				pw.println("taco Price : "+tacoPrice);
			}
			if(!tacoType.equals("") && (steak != null || barbacoa != null || carnitas != null))
			{
				Double price = 0.0;
				query = "select price from chipotle_menu where item = 'taco_"+quantity+"_bs';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				tacoPrice = tacoPrice+price;
				pw.println("Taco : "+tacoType);
				pw.println("<br>");
				pw.println("taco Price : "+tacoPrice);
			}
			
			// sides
			pw.println("<br>");
			pw.println("Sides are : ");
			pw.println("<br>");
			Double price = 0.0;
			query = "select price from chipotle_menu where item = 'chips';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double chips_p = Double.parseDouble(chips) * price;
			if (Integer.parseInt(chips) > 0)
				sides = sides + " Chips : " + chips + " \n";

			query = "select price from chipotle_menu where item = 'chips_guacamole';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double c_g_p = Double.parseDouble(c_g) * price;
			if (Integer.parseInt(c_g) > 0)
				sides = sides + " Chips with Guacamole : " + c_g + " \n";

			query = "select price from chipotle_menu where item = 'chips_fresh_tomato_salsa';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double c_fresh_p = Double.parseDouble(c_fresh) * price;
			if (Integer.parseInt(c_fresh) > 0)
				sides = sides + " Chips with Fresh Tomato Salsa sauce : "
						+ c_fresh + " \n";

			query = "select price from chipotle_menu where item = 'chips_roasted_chili_corn_salsa';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double c_corn_p = Double.parseDouble(c_corn) * price;
			if (Integer.parseInt(c_corn) > 0)
				sides = sides + " Chips with Tomato Corn salsa sauce : "
						+ c_corn + " \n";

			query = "select price from chipotle_menu where item = 'chips_tomato_green_chili_salsa';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double c_green_p = Double.parseDouble(c_green) * price;
			if (Integer.parseInt(c_green) > 0)
				sides = sides
						+ " Chips with Fresh Toamtilo green chili sauce : "
						+ c_green + " \n";

			query = "select price from chipotle_menu where item = 'chips_tomato_red_chili_salsa';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double c_red_p = Double.parseDouble(c_red) * price;
			if (Integer.parseInt(c_red) > 0)
				sides = sides + " Chips with Fresh Tomatilo red chili sauce : "
						+ c_red + " \n";

			sidesPrice = chips_p + c_g_p + c_fresh_p + c_corn_p + c_green_p
					+ c_red_p;
			pw.println(sides);
			pw.println("<br>");
			pw.println("total for sides is " + sidesPrice + "\n");
			pw.println("<br>");

			// sides

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, "root", "password");
			st = con.createStatement();
			query = "";
			query = "select price from chipotle_menu where item = 'water';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double water_p = Double.parseDouble(water) * price;
			if (Integer.parseInt(water) > 0)
				drinks = drinks + " Bottled Water : " + water + " - " + water_p
						+ " \n";

			query = "select price from chipotle_menu where item = 'nectar';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double n_apple_p = Double.parseDouble(n_apple) * price;
			Double n_peach_p = Double.parseDouble(n_peach) * price;
			Double n_orange_p = Double.parseDouble(n_orange) * price;
			Double n_cherry_p = Double.parseDouble(n_cherry) * price;

			if (Integer.parseInt(n_apple) > 0)
				drinks = drinks + " Nantucket nectar - Apple : " + n_apple + " \n";

			if (Integer.parseInt(n_peach) > 0)
				drinks = drinks + " Nantucket nectar - Peach : " + n_peach+ " \n";

			if (Integer.parseInt(n_orange) > 0)
				drinks = drinks + " Nantucket nectar - Orange : " + n_orange+ " \n";

			if (Integer.parseInt(n_cherry) > 0)
				drinks = drinks + " Nantucket nectar - Apple : " + n_cherry + " \n";

			query = "select price from chipotle_menu where item = 'izze';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				price = rs.getDouble("price");
			}
			Double i_blackberry_p = Double.parseDouble(i_blackberry) * price;
			Double i_clementine_p = Double.parseDouble(i_clementine) * price;
			Double i_grape_p = Double.parseDouble(i_grape) * price;

			if (Integer.parseInt(i_blackberry) > 0)
				drinks = drinks + " Izze Blackberry : " + i_blackberry + " \n";

			if (Integer.parseInt(i_clementine) > 0)
				drinks = drinks + " Izze Blackberry : " + i_clementine + " \n";

			if (Integer.parseInt(i_grape) > 0)
				drinks = drinks + " Izze Blackberry : " + i_grape + " \n";

			drinksPrice = water_p + n_apple_p + n_peach_p + n_orange_p
					+ n_cherry_p + i_blackberry_p + i_clementine_p + i_grape_p;

			pw.println("<br>");
			pw.println(drinks);
			pw.println("total for drinks is " + drinksPrice + "\n");
			total = fillingPrice + sidesPrice + drinksPrice+tacoPrice;
	        total= Double.parseDouble(new DecimalFormat("##.##").format(total));
			pw.println("<br>");
			pw.println("total  is "+total + "\n");
			if(!flag)
				return flag;
			// bean making part
			CartBean cartBean = null;

			Object objCartBean = session.getAttribute("cartChip");

			if (objCartBean != null) {
				cartBean = (CartBean) objCartBean;
			} else {
				cartBean = new CartBean();
				session.setAttribute("cartChip", cartBean);
			}

			cartBean.addCartItem(filling, rice, bean, toppings, sides, drinks, tacoType, type, total);

		}

		catch (Exception e) {
			e.printStackTrace();
		}
       return flag;
		
	}
	
	//delete from cart
	 protected void deleteCart(HttpServletRequest request) {
		  HttpSession session = request.getSession();
		  String strItemIndex = request.getParameter("itemIndex");
		  CartBean cartBean = null;
		  
		  Object objCartBean = session.getAttribute("cartChip");
		  if(objCartBean!=null) {
		   cartBean = (CartBean) objCartBean ;
		  } else {
		   cartBean = new CartBean();
		  }
		  cartBean.deleteCartItem(strItemIndex);
		 }
		  

}
