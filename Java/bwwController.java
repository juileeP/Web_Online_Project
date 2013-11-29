import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Calendar;

import beans.CartBeanBww;

public class bwwController extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
        boolean flag = false;
		String strAction = request.getParameter("action");
		
		String type = "";

		pw.println("<html>");
		pw.println("<head>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("YOUR ORDER :");
		pw.println("<br>");

		if (strAction != null && !strAction.equals("")) {
			if (strAction.equals("addSharables")) {
				type = "SHARABLES ";
				flag = addSharables(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
				response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addSides")) {
				type = "SIDES ";
				flag = addSides(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
				response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addSalads")) {
				type = "SALADS ";
				flag = addSalads(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addBurgers")) {
				type = "BURGERS ";
				flag = addBurgers(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addSandwiches")) {
				type = "SANDWICHES ";
				flag = addSandwiches(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addFlatbreads")) {
				type = "FLATBREADS ";
				flag = addFlatbreads(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addDesserts")) {
				type = "DESSERTS ";
				flag = addDesserts(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addKids")) {
				type = "KIDS' MEALS ";
				flag = addKids(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addTenders")) {
				type = "TENDERS/ POPCORN SHRIMPS ";
				flag = addTenders(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addWraps")) {
				type = "WRAPS & BUFFALITOS ";
				flag = addWraps(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("addWings")) {
				type = "";
				String form = request.getParameter("form");
				pw.println(form);
				flag = addWings(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else
			    response.sendRedirect("../wings.html");
			}
			if (strAction.equals("addCombo")) {
				type = "WINGS COMBO ";
				flag = addCombo(request, response, type);
				if(!flag) response.sendRedirect("../bww_order.html");
				else 
			   response.sendRedirect("../cart.jsp");
			}
			if (strAction.equals("delete")) {
				deleteCart(request);
				response.sendRedirect("../cart.jsp");
			}
		}
	}
	public boolean addCombo(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "Wings Combo ";
			double total = 0.0;
			boolean flag=true;
			
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//var
     		String subcheck = request.getParameter("subcheck");
     		String sub = request.getParameter("sub");
     		
     		 try {

        			Class.forName("com.mysql.jdbc.Driver").newInstance();
        			con = DriverManager.getConnection(url, "root", "password");
        			String query = "";
        			double price = 0.0;
        			st = con.createStatement();
        			query = "select price from bww_menu where type='combo'";
      				rs = st.executeQuery(query);
      				while (rs.next()) {
      					price = rs.getDouble("price");
      					//pw.println(price);
      				}
      				total = total + price;
      				if(subcheck != null)
      				{
      			  			items = items+" Substitute Fries with "+sub;
      			  			//pw.print(sub);
      							query = "select price from bww_menu where item = '"+sub+"' AND type='substitutes';";
      							//pw.print(query);
      							rs = st.executeQuery(query);
      							while (rs.next()) {
      								price = rs.getDouble("price");
      							}
      							total = total + price;
      							pw.println("total "+total);
      				}
      				total= Double.parseDouble(new DecimalFormat("##.##").format(total));
      				if(!flag) return flag;
      				CartBeanBww cartBean = null;
      				Object objCartBean = session.getAttribute("cart");

      				if (objCartBean != null) {
      					cartBean = (CartBeanBww) objCartBean;
      				} else {
      					cartBean = new CartBeanBww();
      					session.setAttribute("cart", cartBean);
      				}
      				cartBean.addCartItem(type, items, total);
     		 }
     		 catch(Exception e)
     		 {
     			 e.printStackTrace();
     		 }
     		return flag;
	}
	public boolean addWings(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag=false;
			String form = request.getParameter("form");
			//pw.println(form);
			if(form.equals("hand")) {type = "HAND SPUN WINGS ";}
		    if(form.equals("traditional")) {type = "BONELESS WINGS ";}
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//calendar - to get tuesdays and Thursdays
     		Calendar cal = Calendar.getInstance(); 
    		int day = cal.get(Calendar.DAY_OF_WEEK); 
    		
     		//main
     		String quantity = request.getParameter("quantity");
     		String option = request.getParameter("hand_sauce");
     		String sauce = request.getParameter("sauce");
     		String seasoning = request.getParameter("seasoning");
     		String onside = request.getParameter("onside");
     		String radio = request.getParameter("radio");
     		String carrots_celery = request.getParameter("carrots_celery");
     		String sideof = request.getParameter("carrots_celery");
     		String sidesauce = request.getParameter("sidesauce");
     		
     		 try {

       			Class.forName("com.mysql.jdbc.Driver").newInstance();
       			con = DriverManager.getConnection(url, "root", "password");
       			String query = "";
       			st = con.createStatement();
     		
     		if(!quantity.equals("select"))
     		{
     			flag=true;
  				double price = 0.0;
  				if(form.equals("hand")) 
  				{
  					if(day==3 || day==5)
  					{
  					price = Integer.parseInt(quantity)*0.60;
  					}
  					else {	
  				query = "select price from bww_menu where item = '"+quantity+"' AND type='hand_spun'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				}
  				total = total + price;
  				items = items+quantity+" Hand-spun Wings ";
  				}
  				if(form.equals("traditional"))
  				{
  					if(day==3 || day==5)
  					{
  					price = Integer.parseInt(quantity)*0.60;
  					}
  					else {
  					query = "select price from bww_menu where item = '"+quantity+"' AND type='boneless'";
  	  				rs = st.executeQuery(query);
  	  				while (rs.next()) {
  	  					price = rs.getDouble("price");
  	  				}
  					}
  	  				total = total + price;	
  	  			items = items+quantity+" Traditional Wings ";
  				}
  				
  				if(option != null) {
  				if(option.equals("none"))
  				{items=items+"With no sauce / seasoning ";}
  				else if(option.equals("sauce"))
  				{items = items+"sauce - "+sauce+" ";}
  				else
  				{items = items+" seasoning - "+seasoning+" ";}
  				}
  				if(onside != null){ items = items+" (sauce on side) ";}
  				
  				
  				if(radio != null) 
  				{
  					items = items+"and "+radio;
  					if(day==3 || day==5)
  					{
  						if(radio.equals("Both")){
  							total=total+1.20;
  						}
  						else {total=total+0.60;}   
  					}
  					else
  					{
  						if(radio.equals("Both")){
  					  total = total+0.60;
  					}
  				}
  				if(carrots_celery != null)
  				{
  					items = items+" and carrots & celery ";
  					if(day==3 || day==5)
  					{
  						total = total+0.60;
  					}
  				}
  				}
  				if(sideof != null)
  				{
  			    items = items+" Side of sauce -";
  			    if(sidesauce != null) items = items+sidesauce+" ";
  			    total=total+0.6;
  				}
     		}
     		total= Double.parseDouble(new DecimalFormat("##.##").format(total));	
     		if(!flag) return flag;
  			CartBeanBww cartBean = null;
			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}
			cartBean.addCartItem(type, items, total);
    	    }
    	    catch(Exception e)
    	    {e.printStackTrace();}
    	    
     	  return flag;  
	}
	public boolean addWraps(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag=false;
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//main
     		String chicken = request.getParameter("wraps1");
     		String chicken_southwest = request.getParameter("wraps2");
     		String chicken_ranch = request.getParameter("wraps3");
     		String steak = request.getParameter("wraps4");
     	    String fish = request.getParameter("wraps5");
     	    String buffalitos = request.getParameter("wraps6");
     	    
     	    //sauces, etc
     	    String radio = request.getParameter("radio1");
     	    String f1 = request.getParameter("sub1");
     	    String s1 = request.getParameter("sub_sauce1");
     	    String sauce1 = request.getParameter("sauce1");
     	    String f2 = request.getParameter("sub2");
    	    String s2 = request.getParameter("sub_sauce2");
    	    String sauce2 = request.getParameter("sauce2");
    	    String f3 = request.getParameter("sub3");
     	    String s3 = request.getParameter("sub_sauce3");
     	    String sauce3 = request.getParameter("sauce3");
     	    String f4 = request.getParameter("sub4");
    	    String s4 = request.getParameter("sub_sauce4");
    	    String sauce4 = request.getParameter("sauce4");
    	    String f5 = request.getParameter("sub5");
     	    String s5 = request.getParameter("sub_sauce5");
     	    String sauce5 = request.getParameter("sauce5");
     	    String fish_sauce = request.getParameter("fish_sauce");
     	    String f6 = request.getParameter("sub6");
    	    String s6 = request.getParameter("sub_sauce6");
    	    String sauce6 = request.getParameter("sauce6");
    	    String buffalito_sauce = request.getParameter("buffalito_sauce");
    	    
    	    try {

      			Class.forName("com.mysql.jdbc.Driver").newInstance();
      			con = DriverManager.getConnection(url, "root", "password");
      			String query = "";
      			st = con.createStatement();

  			if (chicken != null) {
  				flag=true;
  				double price = 0.0;
  				items = items+"Chicken Wrap - ";
  				if(f1 != null) items = items+" Substitute Chips n salsa with Fries ";
  				if(s1 != null){
  			    items = items+" Substitute chips with Sauce - ";
  			    if(sauce1 != null) items = items+sauce1+" ";
  			    total=total+0.6;
  				}			
  				query = "select price from bww_menu where item = 'chicken' AND type='wraps'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (chicken_southwest != null) {
  				flag=true;
  				double price = 0.0;
  				items = items+"Southwest Chicken Wrap ("+radio+")- ";
  				if(f2 != null) items = items+" Substitute Chips n salsa with Fries ";
  				if(s2 != null){
  			    items = items+" Substitute chips with Sauce - ";
  			    if(sauce2 != null) items = items+sauce2+" ";
  			    total=total+0.6;
  				}			
  				query = "select price from bww_menu where item = 'chicken_southwest' AND type='wraps'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (chicken_ranch  != null) {
  				flag=true;
  				double price = 0.0;
  				items = items+"Buffalo Ranch Chicken Wrap - ";
  				if(f3 != null) items = items+" Substitute Chips n salsa with Fries ";
  				if(s3 != null){
  			    items = items+" Substitute chips with Sauce - ";
  			    if(sauce3 != null) items = items+sauce3+" ";
  			    total=total+0.6;
  				}			
  				query = "select price from bww_menu where item = 'chicken_ranch' AND type='wraps'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (steak != null) {
  				flag=true;
  				double price = 0.0;
  				items = items+"Pepper Jack Steak Wrap - ";
  				if(f4 != null) items = items+" Substitute Chips n salsa with Fries ";
  				if(s4 != null){
  			    items = items+" Substitute chips with Sauce - ";
  			    if(sauce4 != null) items = items+sauce4+" ";
  			    total=total+0.6;
  				}			
  				query = "select price from bww_menu where item = 'steak' AND type='wraps'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (fish != null) {
  				flag=true;
  				double price = 0.0;
  				items = items+"2 Fish Tacos (sauce- "+fish_sauce+") ";
  				if(f5 != null) items = items+" Substitute Chips n salsa with Fries ";
  				if(s5 != null){
  			    items = items+" Substitute chips with Sauce - ";
  			    if(sauce5 != null) items = items+sauce5+" ";
  			  total=total+0.6;
  				}			
  				query = "select price from bww_menu where item = 'fish' AND type='wraps'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (buffalitos != null) {
  				flag=true;
  				double price = 0.0;
  				items = items+"2 Grilled Chicken Buffalitos (sauce - "+buffalito_sauce+") ";
  				if(f6 != null) items = items+" Substitute Chips n salsa with Fries ";
  				if(s6 != null){
  			    items = items+" Substitute chips with Sauce - ";
  			    if(sauce6 != null) items = items+sauce6+" ";
  			  total=total+0.6;
  				}			
  				query = "select price from bww_menu where item = 'buffalitos' AND type='wraps'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
  			if(!flag) return flag;
  			CartBeanBww cartBean = null;
			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}
			cartBean.addCartItem(type, items, total);
    	    }
    	    catch(Exception e)
    	    {e.printStackTrace();}
    	    
     	  return flag;  
	}
	
	public boolean addTenders(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag= false;
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//main
     		String naked_4 = request.getParameter("tenders1");
     		String naked_6 = request.getParameter("tenders2");
     		String chicken_4 = request.getParameter("tenders3");
     		String chicken_6 = request.getParameter("tenders4");
     		String popcorn_shrimp = request.getParameter("tenders5");
     		String tenders_shrimp = request.getParameter("tenders6");
     		
     		//sauces and others
     		String d1 = request.getParameter("dipping1");
     		String n1 = request.getParameter("no1");
     		String o1 = request.getParameter("onside1");
     		String s1 = request.getParameter("sideof1");
     		String sauce1 = request.getParameter("sauce1");
     		String d2 = request.getParameter("dipping2");
     		String n2 = request.getParameter("no2");
     		String o2 = request.getParameter("onside2");
     		String s2 = request.getParameter("sideof2");
     		String sauce2 = request.getParameter("sauce2");
     		String d3 = request.getParameter("dipping3");
     		String n3 = request.getParameter("no3");
     		String o3 = request.getParameter("onside3");
     		String s3 = request.getParameter("sideof3");
     		String sauce3 = request.getParameter("sauce3");
     		String d4 = request.getParameter("dipping4");
     		String n4 = request.getParameter("no4");
     		String o4 = request.getParameter("onside4");
     		String s4 = request.getParameter("sideof4");
     		String sauce4 = request.getParameter("sauce4");
     		String d5 = request.getParameter("dipping5");
     		String n5 = request.getParameter("no5");
     		String o5 = request.getParameter("onside5");
     		String s5 = request.getParameter("sideof5");
     		String sauce5 = request.getParameter("sauce5");
     		String d6 = request.getParameter("dipping6");
     		String n6 = request.getParameter("no6");
     		String o6 = request.getParameter("onside6");
     		String s6 = request.getParameter("sideof6");
     		String sauce6 = request.getParameter("sauce6");
     		String substitute = request.getParameter("substitute");
     		String sub = request.getParameter("sub");
     		
     		try {

      			Class.forName("com.mysql.jdbc.Driver").newInstance();
      			con = DriverManager.getConnection(url, "root", "password");
      			String query = "";
      			st = con.createStatement();

  			if (naked_4 != null) {
  				flag=true;
  				items = items+"4 Naked Tenders with - ";
  				if(!d1.equals("select")) items = items+d1+" ";
  				if(n1 != null) items = items+" no sauce ";
  				if(o1 != null) items = items+" sauce on side ";
  				if(s1 != null) { total = total+0.6;
  					items = items+" and side of sauce "+sauce1+" ";}
  				double price = 0.0;
  				query = "select price from bww_menu where item = '4_naked'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (naked_6 != null) {
  				flag=true;
  				items = items+"6 Naked Tenders with - ";
  				if(!d2.equals("select")) items = items+d2+" ";
  				if(n2 != null) items = items+" no sauce ";
  				if(o2 != null) items = items+" sauce on side ";
  				if(s2 != null) { total = total+0.6;
  					items = items+" and side of sauce "+sauce2+" ";}
  				double price = 0.0;
  				query = "select price from bww_menu where item = '6_naked'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (chicken_4 != null) {
  				flag=true;
  				items = items+"4 Chicken Tenders with - ";
  				if(!d3.equals("select")) items = items+d3+" ";
  				if(n3 != null) items = items+" no sauce ";
  				if(o3 != null) items = items+" sauce on side ";
  				if(s3 != null) { total = total+0.6;
  					items = items+" and side of sauce "+sauce3+" ";}
  				double price = 0.0;
  				query = "select price from bww_menu where item = '4_chicken'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (chicken_6 != null) {
  				flag=true;
  				items = items+"4 Naked Tenders with - ";
  				if(!d4.equals("select")) items = items+d4+" ";
  				if(n4 != null) items = items+" no sauce ";
  				if(o4 != null) items = items+" sauce on side ";
  				if(s4 != null) { total = total+0.6;
  					items = items+" and side of sauce "+sauce4+" ";}
  				double price = 0.0;
  				query = "select price from bww_menu where item = '6_chicken'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (popcorn_shrimp != null) {
  				flag=true;
  				items = items+"Popcorn Shrimp with - ";
  				if(!d5.equals("select")) items = items+d5+" ";
  				if(n5 != null) items = items+" no sauce ";
  				if(o5 != null) items = items+" sauce on side ";
  				if(s5 != null) { total = total+0.6;
  					items = items+" and side of sauce "+sauce5+" ";}
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'popcorn_shrimp'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (tenders_shrimp != null) {
  				flag=true;
  				items = items+"Chicken tenders & Popcorn Shrimps with - ";
  				if(!d6.equals("select")) items = items+d5+" ";
  				if(n6 != null) items = items+" no sauce ";
  				if(o6 != null) items = items+" sauce on side ";
  				if(s6 != null) { total = total+0.6;
  					items = items+" and side of sauce "+sauce6+" ";}
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'tenders_shrimp'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if(substitute != null)
  			{
  			items = items+" Substitute Fries with "+sub;
  			double price = 0.0;
				query = "select price from bww_menu where item = '"+sub+"';";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
  			}
  			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
  			if(!flag) return flag;
  			CartBeanBww cartBean = null;
			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}
			cartBean.addCartItem(type, items, total);
             }
     		catch(Exception e)
     		{e.printStackTrace();}
     		
     		return flag;
	}
	
	public boolean addKids(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag= false;
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//main
     		 String cheeseburger_slammer = request.getParameter("kids1");
             String boneless_wings = request.getParameter("kids2");
          //   String traditional_wings = request.getParameter("kids3");
             String chicken_tenders = request.getParameter("kids4");
             String hot_dog_slammer = request.getParameter("kids3");
             String naked_tenders = request.getParameter("kids5");
             String mac_cheese = request.getParameter("kids6");
             String corn_dogs = request.getParameter("kids7");
             
             //sauces
             String option = request.getParameter("slammer_radio");
             String s2 = request.getParameter("sauce2");
             String s3 = request.getParameter("sauce3");
             String s4 = request.getParameter("sauce4");
             String s5 = request.getParameter("sauce5");
             String sides = request.getParameter("kids_sides");
             
             try {

      			Class.forName("com.mysql.jdbc.Driver").newInstance();
      			con = DriverManager.getConnection(url, "root", "password");
      			String query = "";
      			st = con.createStatement();

  			if (cheeseburger_slammer != null) {
  				flag=true;
  				items = items+"Cheeseburger Slammer - ";
  				if(option != null) items = items+option+" ";
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'cheeseburger_slammer' AND type='kids'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (boneless_wings != null) {
  				flag=true;
  				items = items+"Boneless wings (sauce - "+s2+") ";
  				if(option != null) items = items+option+" ";
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'boneless_wings' AND type='kids'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (hot_dog_slammer != null) {
  				flag=true;
  				items = items+"Mini Hot Dog Slammer (sauce - "+s3+") ";
  				if(option != null) items = items+option+" ";
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'hot_dog_slammer' AND type='kids'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (chicken_tenders != null) {
  				flag=true;
  				items = items+"Chicken Tenders (sauce - "+s4+") ";
  				if(option != null) items = items+option+" ";
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'chicken_tenders' AND type='kids'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (naked_tenders != null) {
  				flag=true;
  				items = items+"Naked tenders  (sauce - "+s5+") ";
  				if(option != null) items = items+option+" ";
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'naked_tenders' AND type='kids'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (mac_cheese != null) {
  				flag=true;
  				items = items+"Mac n Cheese ";
  				if(option != null) items = items+option+" ";
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'mac_cheese' AND type='kids'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if (corn_dogs != null) {
  				flag=true;
  				items = items+"Mini Corn Dogs ";
  				if(option != null) items = items+option+" ";
  				double price = 0.0;
  				query = "select price from bww_menu where item = 'corn_dogs' AND type='kids'";
  				rs = st.executeQuery(query);
  				while (rs.next()) {
  					price = rs.getDouble("price");
  				}
  				total = total + price;
  			    }
  			if(sides != null)
  			{
  				items = items+" \n With side of "+sides;
  			}
  			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
  			if(!flag) return flag;
  			CartBeanBww cartBean = null;

			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}

			cartBean.addCartItem(type, items, total);
             }
             catch(Exception e)
             {
            	 e.printStackTrace();
             }
             return flag;
	}
	
	public boolean addDesserts(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag = false;
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//main
     		String choc_fudge_cake = request.getParameter("dessert1");
     		String ice_cream = request.getParameter("dessert2");
     		String option = request.getParameter("dessert_radio");
     		String cheesecake_bites = request.getParameter("dessert3");
     		String cinnamon_squares = request.getParameter("dessert4");
     		String dessert_nachos = request.getParameter("dessert5");
     		
     		 try {

     			Class.forName("com.mysql.jdbc.Driver").newInstance();
     			con = DriverManager.getConnection(url, "root", "password");
     			String query = "";
     			st = con.createStatement();

 			if (choc_fudge_cake != null) {
 				flag=true;
 				items = items+"Chocolate Fudge Cake ";
 				double price = 0.0;
 				query = "select price from bww_menu where item = 'choc_fudge_cake'";
 				rs = st.executeQuery(query);
 				while (rs.next()) {
 					price = rs.getDouble("price");
 				}
 				total = total + price;
 			    }
 			if (ice_cream != null) {
 				flag=true;
 				items = items+"Ice cream with : ";
 				if(option != null) { items = items+option; }
 				double price = 0.0;
 				query = "select price from bww_menu where item = 'ice_cream'";
 				rs = st.executeQuery(query);
 				while (rs.next()) {
 					price = rs.getDouble("price");
 				}
 				total = total + price;
 			    }
 			if (cheesecake_bites != null) {
 				flag=true;
 				items = items+"Cheesecake bites ";
 				double price = 0.0;
 				query = "select price from bww_menu where item = 'cheesecake_bites'";
 				rs = st.executeQuery(query);
 				while (rs.next()) {
 					price = rs.getDouble("price");
 				}
 				total = total + price;
 			    }
 			if (cinnamon_squares != null) {
 				flag=true;
 				items = items+"Cinnamon Squares ";
 				double price = 0.0;
 				query = "select price from bww_menu where item = 'cinnamon_squares'";
 				rs = st.executeQuery(query);
 				while (rs.next()) {
 					price = rs.getDouble("price");
 				}
 				total = total + price;
 			    }
 			if (dessert_nachos != null) {
 				flag=true;
 				items = items+"Dessert Nachos ";
 				double price = 0.0;
 				query = "select price from bww_menu where item = 'dessert_nachos'";
 				rs = st.executeQuery(query);
 				while (rs.next()) {
 					price = rs.getDouble("price");
 				}
 				total = total + price;
 			    }
 			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
 			if(!flag) return flag;
 			CartBeanBww cartBean = null;

			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}

			cartBean.addCartItem(type, items, total);
			
     		 }
     		 catch(Exception e)
     		 {
     			 e.printStackTrace();
     		 }
     	return flag;	
     		
	}
	public boolean addFlatbreads(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag= false;
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//main
     		String buffalo_chicken = request.getParameter("flatbread2");
     		String garlic = request.getParameter("flatbread1");
     	    String spinach_artichoke = request.getParameter("flatbread3");
     	    String italian_pepperoni = request.getParameter("flatbread4");
     	    
     	   try {

    			Class.forName("com.mysql.jdbc.Driver").newInstance();
    			con = DriverManager.getConnection(url, "root", "password");
    			String query = "";
    			st = con.createStatement();

			if (garlic != null) {
				flag=true;
				items = items+"Parmesan Garlic Ckn. Flatbread ";
				double price = 0.0;
				query = "select price from bww_menu where item = 'garlic' AND type='flatbread'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			    }
			if (buffalo_chicken != null) {
				flag=true;
				items = items+"Buffalo Chicken Flatbread ";
				double price = 0.0;
				query = "select price from bww_menu where item = 'buffalo_chicken' AND type='flatbread'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			    }
			if (spinach_artichoke != null) {
				flag=true;
				items = items+"Spinach Artichoke Ckn. Flatbread ";
				double price = 0.0;
				query = "select price from bww_menu where item = 'spinach_artichoke' AND type='flatbread'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			    }
			if (italian_pepperoni != null) {
					flag=true;
					items = items+"Spinach Artichoke Ckn. Flatbread ";
					double price = 0.0;
					query = "select price from bww_menu where item = 'italian_pepperoni' AND type='flatbread'";
					rs = st.executeQuery(query);
					while (rs.next()) {
						price = rs.getDouble("price");
					}
					total = total + price;
				    }
			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
			if(!flag) return flag;
			CartBeanBww cartBean = null;

			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}

			cartBean.addCartItem(type, items, total);
			
     	   }
     	   catch(Exception e)
     	   {
     		   e.printStackTrace();
     	   }
     	    
     	   return flag; 
	}
	
	public boolean addSandwiches(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag=false;
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//main items
     		String buffalo_ranch = request.getParameter("sandwiches0");
     		String jerk_chicken = request.getParameter("sandwiches1");
     		String pork = request.getParameter("sandwiches2");
     		String chicken = request.getParameter("sandwiches3");
     		
     		//others
     		String radio = request.getParameter("chk");
     		String s1 = request.getParameter("sauce1");
     		String s2 = request.getParameter("sauce2");
     		String s3 = request.getParameter("sauce3");
     		String s4 = request.getParameter("sauce4");
     		
     		//toppings-1
     		String t1 = request.getParameter("Topping11");
     		String t2 = request.getParameter("Topping12");
     		String t3 = request.getParameter("Topping13");
     		String t4 = request.getParameter("Topping14");
     		String t5 = request.getParameter("Topping15");
     		String t6 = request.getParameter("Topping16");
     		String t7 = request.getParameter("Topping17");
     		String t8 = request.getParameter("Topping18");
     		String t9 = request.getParameter("Topping19");
     		String t10 = request.getParameter("Topping110");
     		String t11 = request.getParameter("Topping111");
     		String t12 = request.getParameter("Topping112");
     		String t13 = request.getParameter("Topping113");
     		String t14 = request.getParameter("Topping114");
     		String t15 = request.getParameter("Topping115");
     		
     		String t21 = request.getParameter("Topping21");
     		String t22 = request.getParameter("Topping22");
     		String t23 = request.getParameter("Topping23");
     		String t24 = request.getParameter("Topping24");
     		String t25 = request.getParameter("Topping25");
     		String t26 = request.getParameter("Topping26");
     		String t27 = request.getParameter("Topping27");
     		String t28 = request.getParameter("Topping28");
     		String t29 = request.getParameter("Topping29");
     		String t210 = request.getParameter("Topping210");
     		String t211 = request.getParameter("Topping211");
     		String t212 = request.getParameter("Topping212");
     		String t213 = request.getParameter("Topping213");
     		String t214 = request.getParameter("Topping214");
     		String t215 = request.getParameter("Topping215");
     		
     		String t31 = request.getParameter("Topping31");
     		String t32 = request.getParameter("Topping32");
     		String t33 = request.getParameter("Topping33");
     		String t34 = request.getParameter("Topping34");
     		String t35 = request.getParameter("Topping35");
     		String t36 = request.getParameter("Topping36");
     		String t37 = request.getParameter("Topping37");
     		String t38 = request.getParameter("Topping38");
     		String t39 = request.getParameter("Topping39");
     		String t310 = request.getParameter("Topping310");
     		String t311 = request.getParameter("Topping311");
     		String t312 = request.getParameter("Topping312");
     		String t313 = request.getParameter("Topping313");
     		String t314 = request.getParameter("Topping314");
     		String t315 = request.getParameter("Topping315");
     		
     		String t41 = request.getParameter("Topping41");
     		String t42 = request.getParameter("Topping42");
     		String t43 = request.getParameter("Topping43");
     		String t44 = request.getParameter("Topping44");
     		String t45 = request.getParameter("Topping45");
     		String t46 = request.getParameter("Topping46");
     		String t47 = request.getParameter("Topping47");
     		String t48 = request.getParameter("Topping48");
     		String t49 = request.getParameter("Topping49");
     		String t410 = request.getParameter("Topping410");
     		String t411 = request.getParameter("Topping411");
     		String t412 = request.getParameter("Topping412");
     		String t413 = request.getParameter("Topping413");
     		String t414 = request.getParameter("Topping414");
     		String t415 = request.getParameter("Topping415");
     		try {

     			Class.forName("com.mysql.jdbc.Driver").newInstance();
     			con = DriverManager.getConnection(url, "root", "password");
     			String query = "";
     			st = con.createStatement();

 			if (buffalo_ranch != null) {
 				flag=true;
				items = items+"Buffalo Ranch Chicken - Sauce : "+s1+"with toppings - ";
				double price = 0.0;
				query = "select price from bww_menu where item = 'buffalo_ranch'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
		
				if(t1 != null) {items = items+"American ";}
				if(t2 != null) {items = items+"Cheddar ";}
				if(t3 != null) {items = items+"Pepper Jack ";}
				if(t4 != null) {items = items+"Swiss ";}
				if(t5 != null) {items = items+"Queso ";}
				if(t6 != null) {items = items+"Lettuce ";}
				if(t7 != null) {items = items+"Tomato ";}
				if(t8 != null) {items = items+"Onion ";}
				if(t9 != null) {items = items+"Jalapeno ";}
				if(t10 != null) {items = items+"Pico ";}
				if(t11 != null) {items = items+"Pickles ";}
				if(t12 != null) { items = items+"Baccon ";
				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t13 != null) { items = items+"Chili ";
				query = "select price from bww_menu where item = 'chili' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t14 != null) { items = items+"Pork ";
				query = "select price from bww_menu where item = 'pork' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t15 != null) { items = items+"Steak ";
				query = "select price from bww_menu where item = 'steak' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				
			    }
 			if (jerk_chicken != null) {
 				flag=true;
				items = items+"Jerk Chicken - Sauce : "+s2+"with toppings - ";
				double price = 0.0;
				query = "select price from bww_menu where item = 'jerk_chicken'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				if(t21 != null) {items = items+"American ";}
				if(t22 != null) {items = items+"Cheddar ";}
				if(t23 != null) {items = items+"Pepper Jack ";}
				if(t24 != null) {items = items+"Swiss ";}
				if(t25 != null) {items = items+"Queso ";}
				if(t26 != null) {items = items+"Lettuce ";}
				if(t27 != null) {items = items+"Tomato ";}
				if(t28 != null) {items = items+"Onion ";}
				if(t29 != null) {items = items+"Jalapeno ";}
				if(t210 != null) {items = items+"Pico ";}
				if(t211 != null) {items = items+"Pickles ";}
				if(t212 != null) { items = items+"Baccon ";
				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t213 != null) { items = items+"Chili ";
				query = "select price from bww_menu where item = 'chili' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t214 != null) { items = items+"Pork ";
				query = "select price from bww_menu where item = 'pork' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t215 != null) { items = items+"Steak ";
				query = "select price from bww_menu where item = 'steak' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				
			    }
     		if (pork != null) {
     			flag=true;
				items = items+"Pulled Pork Samdwich - Sauce : "+s3+"with toppings - ";
				double price = 0.0;
				query = "select price from bww_menu where item = 'pork' AND type='sandwiches'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				
				if(t31 != null) {items = items+"American ";}
				if(t32 != null) {items = items+"Cheddar ";}
				if(t33 != null) {items = items+"Pepper Jack ";}
				if(t34 != null) {items = items+"Swiss ";}
				if(t35 != null) {items = items+"Queso ";}
				if(t36 != null) {items = items+"Lettuce ";}
				if(t37 != null) {items = items+"Tomato ";}
				if(t38 != null) {items = items+"Onion ";}
				if(t39 != null) {items = items+"Jalapeno ";}
				if(t310 != null) {items = items+"Pico ";}
				if(t311 != null) {items = items+"Pickles ";}
				if(t312 != null) { items = items+"Baccon ";
				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t313 != null) { items = items+"Chili ";
				query = "select price from bww_menu where item = 'chili' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t314 != null) { items = items+"Pork ";
				query = "select price from bww_menu where item = 'pork' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t315 != null) { items = items+"Steak ";
				query = "select price from bww_menu where item = 'steak' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				
			    }
	if (chicken != null) {
		flag=true;
		items = items+"Chicken Sandwich - Sauce : "+s4+" ("+radio+") "+"with toppings - ";
		double price = 0.0;
		query = "select price from bww_menu where item = 'chicken' AND type='sandwiches'";
		rs = st.executeQuery(query);
		while (rs.next()) {
			price = rs.getDouble("price");
		}
		total = total + price;
		
		if(t41 != null) {items = items+"American ";}
		if(t42 != null) {items = items+"Cheddar ";}
		if(t43 != null) {items = items+"Pepper Jack ";}
		if(t44 != null) {items = items+"Swiss ";}
		if(t45 != null) {items = items+"Queso ";}
		if(t46 != null) {items = items+"Lettuce ";}
		if(t47 != null) {items = items+"Tomato ";}
		if(t48 != null) {items = items+"Onion ";}
		if(t49 != null) {items = items+"Jalapeno ";}
		if(t410 != null) {items = items+"Pico ";}
		if(t411 != null) {items = items+"Pickles ";}
		if(t412 != null) { items = items+"Baccon ";
		query = "select price from bww_menu where item = 'baccon' AND type='additions'";
		rs = st.executeQuery(query);
		while (rs.next()) {
			price = rs.getDouble("price");
		}
		total = total + price;
		}
		if(t413 != null) { items = items+"Chili ";
		query = "select price from bww_menu where item = 'chili' AND type='additions'";
		rs = st.executeQuery(query);
		while (rs.next()) {
			price = rs.getDouble("price");
		}
		total = total + price;
		}
		if(t414 != null) { items = items+"Pork ";
		query = "select price from bww_menu where item = 'pork' AND type='additions'";
		rs = st.executeQuery(query);
		while (rs.next()) {
			price = rs.getDouble("price");
		}
		total = total + price;
		}
		if(t415 != null) { items = items+"Steak ";
		query = "select price from bww_menu where item = 'steak' AND type='additions'";
		rs = st.executeQuery(query);
		while (rs.next()) {
			price = rs.getDouble("price");
		}
		total = total + price;
		
		}
		
	    }
	
		 total= Double.parseDouble(new DecimalFormat("##.##").format(total));	
    if(!flag) return flag;
	CartBeanBww cartBean = null;

	Object objCartBean = session.getAttribute("cart");

	if (objCartBean != null) {
		cartBean = (CartBeanBww) objCartBean;
	} else {
		cartBean = new CartBeanBww();
		session.setAttribute("cart", cartBean);
	}

	cartBean.addCartItem(type, items, total);
     
     		}
     		 catch(Exception e)
     		{
     			e.printStackTrace();
     		}
     	return flag;	
     				
	}
public boolean addBurgers(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
	{
			
			HttpSession session = request.getSession();

			// for output display
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			String items = "";
			double total = 0.0;
			boolean flag=false;
			  // database connection
     		String url = "jdbc:mysql://localhost:3306/onlinesite";
     		Connection con = null;
     		Statement st = null;
     		ResultSet rs = null;
     		
     		//main items
     		String black_bleu = request.getParameter("burgers0");
     		String jack_daddy = request.getParameter("burgers1");
     		String nacho = request.getParameter("burgers2");
     		String steak = request.getParameter("burgers3");
     		String own = request.getParameter("burgers4");
     		String black_bean = request.getParameter("burgers5");
     		
     		//type
     		String b0 = request.getParameter("black0");
     		String b1 = request.getParameter("black1");
     		String b2 = request.getParameter("black2");
     		String b3 = request.getParameter("black3");
     		String b4 = request.getParameter("black4");
     		String b5 = request.getParameter("black5");
     		
     		//toppings-1
     		String t1 = request.getParameter("Topping11");
     		String t2 = request.getParameter("Topping12");
     		String t3 = request.getParameter("Topping13");
     		String t4 = request.getParameter("Topping14");
     		String t5 = request.getParameter("Topping15");
     		String t6 = request.getParameter("Topping16");
     		String t7 = request.getParameter("Topping17");
     		String t8 = request.getParameter("Topping18");
     		String t9 = request.getParameter("Topping19");
     		String t10 = request.getParameter("Topping110");
     		String t11 = request.getParameter("Topping111");
     		String t12 = request.getParameter("Topping112");
     		String t13 = request.getParameter("Topping113");
     		String t14 = request.getParameter("Topping114");
     		String t15 = request.getParameter("Topping115");
     		
     		String t21 = request.getParameter("Topping21");
     		String t22 = request.getParameter("Topping22");
     		String t23 = request.getParameter("Topping23");
     		String t24 = request.getParameter("Topping24");
     		String t25 = request.getParameter("Topping25");
     		String t26 = request.getParameter("Topping26");
     		String t27 = request.getParameter("Topping27");
     		String t28 = request.getParameter("Topping28");
     		String t29 = request.getParameter("Topping29");
     		String t210 = request.getParameter("Topping210");
     		String t211 = request.getParameter("Topping211");
     		String t212 = request.getParameter("Topping212");
     		String t213 = request.getParameter("Topping213");
     		String t214 = request.getParameter("Topping214");
     		String t215 = request.getParameter("Topping215");
     		
     		String t31 = request.getParameter("Topping31");
     		String t32 = request.getParameter("Topping32");
     		String t33 = request.getParameter("Topping33");
     		String t34 = request.getParameter("Topping34");
     		String t35 = request.getParameter("Topping35");
     		String t36 = request.getParameter("Topping36");
     		String t37 = request.getParameter("Topping37");
     		String t38 = request.getParameter("Topping38");
     		String t39 = request.getParameter("Topping39");
     		String t310 = request.getParameter("Topping310");
     		String t311 = request.getParameter("Topping311");
     		String t312 = request.getParameter("Topping312");
     		String t313 = request.getParameter("Topping313");
     		String t314 = request.getParameter("Topping314");
     		String t315 = request.getParameter("Topping315");
     		
     		String t41 = request.getParameter("Topping41");
     		String t42 = request.getParameter("Topping42");
     		String t43 = request.getParameter("Topping43");
     		String t44 = request.getParameter("Topping44");
     		String t45 = request.getParameter("Topping45");
     		String t46 = request.getParameter("Topping46");
     		String t47 = request.getParameter("Topping47");
     		String t48 = request.getParameter("Topping48");
     		String t49 = request.getParameter("Topping49");
     		String t410 = request.getParameter("Topping410");
     		String t411 = request.getParameter("Topping411");
     		String t412 = request.getParameter("Topping412");
     		String t413 = request.getParameter("Topping413");
     		String t414 = request.getParameter("Topping414");
     		String t415 = request.getParameter("Topping415");
     		
     		String t51 = request.getParameter("Topping51");
     		String t52 = request.getParameter("Topping52");
     		String t53 = request.getParameter("Topping53");
     		String t54 = request.getParameter("Topping54");
     		String t55 = request.getParameter("Topping55");
     		String t56 = request.getParameter("Topping56");
     		String t57 = request.getParameter("Topping57");
     		String t58 = request.getParameter("Topping58");
     		String t59 = request.getParameter("Topping59");
     		String t510 = request.getParameter("Topping510");
     		String t511 = request.getParameter("Topping511");
     		String t512 = request.getParameter("Topping512");
     		String t513 = request.getParameter("Topping513");
     		String t514 = request.getParameter("Topping514");
     		String t515 = request.getParameter("Topping515");
     		
     		String t61 = request.getParameter("Topping61");
     		String t62 = request.getParameter("Topping62");
     		String t63 = request.getParameter("Topping63");
     		String t64 = request.getParameter("Topping64");
     		String t65 = request.getParameter("Topping65");
     		String t66 = request.getParameter("Topping66");
     		String t67 = request.getParameter("Topping67");
     		String t68 = request.getParameter("Topping68");
     		String t69 = request.getParameter("Topping69");
     		String t610 = request.getParameter("Topping610");
     		String t611 = request.getParameter("Topping611");
     		String t612 = request.getParameter("Topping612");
     		String t613 = request.getParameter("Topping613");
     		String t614 = request.getParameter("Topping614");
     		String t615 = request.getParameter("Topping615");
     		
     		

     		try {

     			Class.forName("com.mysql.jdbc.Driver").newInstance();
     			con = DriverManager.getConnection(url, "root", "password");
     			String query = "";
     			st = con.createStatement();
     			
     			if (black_bleu != null) {
     				flag=true;
   				items = items+"Black & Blue Burger - ";
   				if(b0 != null) items = items+b0+" With toppings : ";
   				double price = 0.0;
   				query = "select price from bww_menu where item = 'black_bleu'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				if(t1 != null) {items = items+"American ";}
				if(t2 != null) {items = items+"Cheddar ";}
				if(t3 != null) {items = items+"Pepper Jack ";}
				if(t4 != null) {items = items+"Swiss ";}
				if(t5 != null) {items = items+"Queso ";}
				if(t6 != null) {items = items+"Lettuce ";}
				if(t7 != null) {items = items+"Tomato ";}
				if(t8 != null) {items = items+"Onion ";}
				if(t9 != null) {items = items+"Jalapeno ";}
				if(t10 != null) {items = items+"Pico ";}
				if(t11 != null) {items = items+"Pickles ";}
				if(t12 != null) { items = items+"Baccon ";
				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t13 != null) { items = items+"Chili ";
				query = "select price from bww_menu where item = 'chili' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t14 != null) { items = items+"Pork ";
				query = "select price from bww_menu where item = 'pork' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t15 != null) { items = items+"Steak ";
				query = "select price from bww_menu where item = 'steak' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
   			    }

     			if (jack_daddy != null) {
     				flag=true;
   				items = items+"Big Jack Daddy Burger - ";
   				if(b1 != null) items = items+b1+" With toppings : ";
   				double price = 0.0;
   				query = "select price from bww_menu where item = 'jack_daddy'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				if(t21 != null) {items = items+"American ";}
				if(t22 != null) {items = items+"Cheddar ";}
				if(t23 != null) {items = items+"Pepper Jack ";}
				if(t24 != null) {items = items+"Swiss ";}
				if(t25 != null) {items = items+"Queso ";}
				if(t26 != null) {items = items+"Lettuce ";}
				if(t27 != null) {items = items+"Tomato ";}
				if(t28 != null) {items = items+"Onion ";}
				if(t29 != null) {items = items+"Jalapeno ";}
				if(t210 != null) {items = items+"Pico ";}
				if(t211 != null) {items = items+"Pickles ";}
				if(t212 != null) { items = items+"Baccon ";
				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t213 != null) { items = items+"Chili ";
				query = "select price from bww_menu where item = 'chili' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t214 != null) { items = items+"Pork ";
				query = "select price from bww_menu where item = 'pork' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t215 != null) { items = items+"Steak ";
				query = "select price from bww_menu where item = 'steak' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
   			}

     			if (nacho != null) {
     				flag=true;
   				items = items+"Screamin' Nacho Burger - ";
   				if(b2 != null) items = items+b2+" With toppings : ";
   				double price = 0.0;
   				query = "select price from bww_menu where item = 'nacho'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				if(t31 != null) {items = items+"American ";}
				if(t32 != null) {items = items+"Cheddar ";}
				if(t33 != null) {items = items+"Pepper Jack ";}
				if(t34 != null) {items = items+"Swiss ";}
				if(t35 != null) {items = items+"Queso ";}
				if(t36 != null) {items = items+"Lettuce ";}
				if(t37 != null) {items = items+"Tomato ";}
				if(t38 != null) {items = items+"Onion ";}
				if(t39 != null) {items = items+"Jalapeno ";}
				if(t310 != null) {items = items+"Pico ";}
				if(t311 != null) {items = items+"Pickles ";}
				if(t312 != null) { items = items+"Baccon ";
				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t313 != null) { items = items+"Chili ";
				query = "select price from bww_menu where item = 'chili' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t314 != null) { items = items+"Pork ";
				query = "select price from bww_menu where item = 'pork' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				if(t315 != null) { items = items+"Steak ";
				query = "select price from bww_menu where item = 'steak' AND type='additions'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
				}
				
   			}

     			if (steak != null) {
     				flag=true;
   				items = items+"Juicy Steak Burger - ";
   				if(b3 != null) items = items+b3+" With toppings : ";
   				double price = 0.0;
   				query = "select price from bww_menu where item = 'steak' AND type='burgers'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				if(t41 != null) {items = items+"American ";}
   				if(t42 != null) {items = items+"Cheddar ";}
   				if(t43 != null) {items = items+"Pepper Jack ";}
   				if(t44 != null) {items = items+"Swiss ";}
   				if(t45 != null) {items = items+"Queso ";}
   				if(t46 != null) {items = items+"Lettuce ";}
   				if(t47 != null) {items = items+"Tomato ";}
   				if(t48 != null) {items = items+"Onion ";}
   				if(t49 != null) {items = items+"Jalapeno ";}
   				if(t410 != null) {items = items+"Pico ";}
   				if(t411 != null) {items = items+"Pickles ";}
   				if(t412 != null) { items = items+"Baccon ";
   				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t413 != null) { items = items+"Chili ";
   				query = "select price from bww_menu where item = 'chili' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t414 != null) { items = items+"Pork ";
   				query = "select price from bww_menu where item = 'pork' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t415 != null) { items = items+"Steak ";
   				query = "select price from bww_menu where item = 'steak' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				}				
   			}

     			if (own != null) {
     				flag=true;
   				items = items+"Build your own Burger - ";
   				if(b4 != null) items = items+b4+" With toppings : ";
   				double price = 0.0;
   				query = "select price from bww_menu where item = 'own'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				if(t51 != null) {items = items+"American ";}
   				if(t52 != null) {items = items+"Cheddar ";}
   				if(t53 != null) {items = items+"Pepper Jack ";}
   				if(t54 != null) {items = items+"Swiss ";}
   				if(t55 != null) {items = items+"Queso ";}
   				if(t56 != null) {items = items+"Lettuce ";}
   				if(t57 != null) {items = items+"Tomato ";}
   				if(t58 != null) {items = items+"Onion ";}
   				if(t59 != null) {items = items+"Jalapeno ";}
   				if(t510 != null) {items = items+"Pico ";}
   				if(t511 != null) {items = items+"Pickles ";}
   				if(t512 != null) { items = items+"Baccon ";
   				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t513 != null) { items = items+"Chili ";
   				query = "select price from bww_menu where item = 'chili' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t514 != null) { items = items+"Pork ";
   				query = "select price from bww_menu where item = 'pork' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t515 != null) { items = items+"Steak ";
   				query = "select price from bww_menu where item = 'steak' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				}		
   			}

     			if (black_bean != null) {
     				flag=true;
   				items = items+"Black Bean Patty Burger - ";
   				if(b5 != null) items = items+b5+" With toppings : ";
   				double price = 0.0;
   				query = "select price from bww_menu where item = 'black_bean'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				if(t61 != null) {items = items+"American ";}
   				if(t62 != null) {items = items+"Cheddar ";}
   				if(t63 != null) {items = items+"Pepper Jack ";}
   				if(t64 != null) {items = items+"Swiss ";}
   				if(t65 != null) {items = items+"Queso ";}
   				if(t66 != null) {items = items+"Lettuce ";}
   				if(t67 != null) {items = items+"Tomato ";}
   				if(t68 != null) {items = items+"Onion ";}
   				if(t69 != null) {items = items+"Jalapeno ";}
   				if(t610 != null) {items = items+"Pico ";}
   				if(t611 != null) {items = items+"Pickles ";}
   				if(t612 != null) { items = items+"Baccon ";
   				query = "select price from bww_menu where item = 'baccon' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t613 != null) { items = items+"Chili ";
   				query = "select price from bww_menu where item = 'chili' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t614 != null) { items = items+"Pork ";
   				query = "select price from bww_menu where item = 'pork' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				}
   				if(t615 != null) { items = items+"Steak ";
   				query = "select price from bww_menu where item = 'steak' AND type='additions'";
   				rs = st.executeQuery(query);
   				while (rs.next()) {
   					price = rs.getDouble("price");
   				}
   				total = total + price;
   				
   				}		
   			}
     			
     			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
                if(!flag) return flag;
    			CartBeanBww cartBean = null;

    			Object objCartBean = session.getAttribute("cart");

    			if (objCartBean != null) {
    				cartBean = (CartBeanBww) objCartBean;
    			} else {
    				cartBean = new CartBeanBww();
    				session.setAttribute("cart", cartBean);
    			}

    			cartBean.addCartItem(type, items, total);
     			
     		}
     		catch(Exception e)
     		{
     			e.printStackTrace();
     		}
     	return flag;		

	}
public boolean addSalads(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException 
{
		
		HttpSession session = request.getSession();

		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String items = "";
		double total = 0.0;
		boolean flag= false;
		//main items
		String honey_bbq_chicken = request.getParameter("salads1");
		String chicken_caesar = request.getParameter("salads2");
        String grilled_chicken = request.getParameter("salads5");
        String asian_zing = request.getParameter("salads6");
        String chicken_tender = request.getParameter("salads7");
        String side_salad = request.getParameter("salads8");
		String chicken_chop = request.getParameter("salads9");
		
		//dressings
        String d1 = request.getParameter("dressing1");
        String o1 = request.getParameter("onside1");
        String d2 = request.getParameter("dressing2");
        String o2 = request.getParameter("onside2");
        String d3 = request.getParameter("dressing3");
        String o3 = request.getParameter("onside3");
        String d4 = request.getParameter("dressing4");
        String o4 = request.getParameter("onside4");
        String d5 = request.getParameter("dressing5");
        String o5 = request.getParameter("onside5");
        String d6 = request.getParameter("dressing6");
        String o6 = request.getParameter("onside6");
        String d7 = request.getParameter("dressing7");
        String o7 = request.getParameter("onside7");
        
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
     			

    			if (honey_bbq_chicken != null) {
    				flag=true;
    				 pw.println(" Dressing is : "+d1);
    			     pw.println("onside is : "+o1);
    				items = items+" Honey BBQ chicken salad with Dressing : "+d1;
    				if (o1 != null) {
    					items = items+" (on the side)";}
    				double price = 0.0;
    				query = "select price from bww_menu where item = 'honey_bbq_chicken'";
    				rs = st.executeQuery(query);
    				while (rs.next()) {
    					price = rs.getDouble("price");
    				}
    				total = total + price;
    			}
    			if (chicken_caesar != null) {
    				flag=true;
    				items = items+" Chicken Caesar salad with Dressing : "+d2;
    				if (o2 != null)
    					items = items+" (on the side)";
    				double price = 0.0;
    				query = "select price from bww_menu where item = 'chicken_caesar'";
    				rs = st.executeQuery(query);
    				while (rs.next()) {
    					price = rs.getDouble("price");
    				}
    				total = total + price;
    			}
    			if (grilled_chicken != null) {
    				flag=true;
    				items = items+" Grilled chicken Salad with Dressing : "+d3;
    				if (o3 != null)
    					items = items+" (on the side)";
    				String value = request.getParameter("salads_3");
    				items = items+" "+ value;
    				double price = 0.0;
    				query = "select price from bww_menu where item = 'grilled_chicken'";
    				rs = st.executeQuery(query);
    				while (rs.next()) {
    					price = rs.getDouble("price");
    				}
    				total = total + price;
    			}
    			if (asian_zing != null) {
    				flag=true;
    				items = items+" Assian salad with Dressing : "+d4;
    				if (o4 != null)
    					items = items+" (on the side)";
    				double price = 0.0;
    				query = "select price from bww_menu where item = 'asian_zing'";
    				rs = st.executeQuery(query);
    				while (rs.next()) {
    					price = rs.getDouble("price");
    				}
    				total = total + price;
    			}
    			if (chicken_tender != null) {
    				flag=true;
    				items = items+" Chicken Tender salad with Dressing : "+d5;
    				if (o5 != null)
    					items = items+" (on the side)";
    				double price = 0.0;
    				query = "select price from bww_menu where item = 'chicken_tender'";
    				rs = st.executeQuery(query);
    				while (rs.next()) {
    					price = rs.getDouble("price");
    				}
    				total = total + price;
    			}
    			if (side_salad != null) {
    				flag=true;
    				items = items+" Side Salad with Dressing : "+d6;
    				if (o6 != null)
    					items = items+" (on the side)";
    				double price = 0.0;
    				query = "select price from bww_menu where item = 'side_salad'";
    				rs = st.executeQuery(query);
    				while (rs.next()) {
    					price = rs.getDouble("price");
    				}
    				total = total + price;
    			}
    			if (chicken_chop != null) {
    				flag=true;
    				items = items+" Chicken Tender salad with Dressing : "+d7;
    				if (o7 != null)
    					items = items+" (on the side)";
    				double price = 0.0;
    				query = "select price from bww_menu where item = 'chicken_chop'";
    				rs = st.executeQuery(query);
    				while (rs.next()) {
    					price = rs.getDouble("price");
    				}
    				total = total + price;
    			}
    			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
            if(!flag) return flag;
			CartBeanBww cartBean = null;

			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}

			cartBean.addCartItem(type, items, total);
    			
     		}
     		catch(Exception e)
     		{
     			
     		}
     		return flag;
}
	
public boolean addSides(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//main items
		String items = "";
		double total = 0.0;
		boolean flag = false;
		
		//main items
		String potato_wedges_basket = request.getParameter("sides0");
		String potato_wedges_basket_c =  request.getParameter("sides1");
		String potato_wedges_regular = request.getParameter("sides2");
		String potato_wedges_regular_c = request.getParameter("sides3");
		String onion_rings = request.getParameter("sides4");
		String onion_rings_basket = request.getParameter("sides5");
		String french_fries = request.getParameter("sides14");
		String french_fries_basket = request.getParameter("sides15");
		String buffalo_chips_basket = request.getParameter("sides6");
		String buffalo_chips_basket_c = request.getParameter("sides7");
		String buffalo_chips_regular = request.getParameter("sides8");
		String buffalo_chips_regular_c = request.getParameter("sides9");
		String side_salad = request.getParameter("sides10");
        String coleslaw = request.getParameter("sides11");
        String mac_cheese =request.getParameter("sides12");
        String veggie_boat = request.getParameter("sides13");
		
		//seasoning
        String s1 = request.getParameter("seasoning1");
        String s2 = request.getParameter("seasoning2");
        String s3 = request.getParameter("seasoning3");
        String s4 = request.getParameter("seasoning4");
        String s5 = request.getParameter("seasoning5");
        String s6 = request.getParameter("seasoning6");
        String s7 = request.getParameter("seasoning7");
        String s8 = request.getParameter("seasoning8");
		
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
			
			if (potato_wedges_basket != null) {
				flag=true;
				items = items+" Potato Wedges Basket (Seasoning : "+s1+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'potato_wedges_basket'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (potato_wedges_basket_c != null) {
				flag=true;
				items = items+" Potato Wedges Basket with Cheese (Seasoning : "+s1+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'potato_wedges_basket_c'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (potato_wedges_regular  != null) {
				flag=true;
				items = items+" Potato Wedges Regular (Seasoning : "+s1+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'potato_wedges_regular'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (potato_wedges_regular_c  != null) {
				flag=true;
				items = items+" Potato Wedges Regular with Cheese (Seasoning : "+s1+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'potato_wedges_regular_c'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (onion_rings_basket  != null) {
				flag=true;
				items = items+" Onion Rings basket (Seasoning : "+s2+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'onion_rings_basket'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (onion_rings  != null) {
				flag=true;
				items = items+" Onion Rings Regular (Seasoning : "+s2+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'onion_rings'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (french_fries_basket  != null) {
				flag=true;
				items = items+" French Fries Basket (Seasoning : "+s8+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'french_fries_basket'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (french_fries  != null) {
				flag=true;
				items = items+" French Fries Regular (Seasoning : "+s8+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'french_fries'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (buffalo_chips_basket != null) {
				flag=true;
				items = items+" Buffalo Chips Basket (Seasoning : "+s3+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'buffalo_chips_basket'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (buffalo_chips_basket_c != null) {
				flag=true;
				items = items+"  Buffalo Chips Basket with Cheese (Seasoning : "+s3+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'buffalo_chips_basket_c'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (buffalo_chips_regular  != null) {
				flag=true;
				items = items+" Buffalo Chips Regular (Seasoning : "+s3+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'buffalo_chips_regular'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			
			if (buffalo_chips_regular_c  != null) {
				flag=true;
				items = items+" Buffalo Chips Regular with Cheese (Seasoning : "+s3+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'buffalo_chips_regular_c'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (side_salad != null) {
				flag=true;
				items = items+" Side Salad (Seasoning : "+s4+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'side_salad'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (coleslaw != null) {
				flag=true;
				items = items+" Coleslaw (Seasoning : "+s5+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'coleslaw'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (mac_cheese != null) {
				flag=true;
				items = items+" Mac n Cheese (Seasoning : "+s6+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'mac_cheese'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			if (veggie_boat != null) {
				flag=true;
				items = items+" Veggie Boat (Seasoning : "+s7+")";
				double price = 0.0;
				query = "select price from bww_menu where item = 'veggie_boat'";
				rs = st.executeQuery(query);
				while (rs.next()) {
					price = rs.getDouble("price");
				}
				total = total + price;
			}
			 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
			if(!flag) return flag;
			CartBeanBww cartBean = null;

			Object objCartBean = session.getAttribute("cart");

			if (objCartBean != null) {
				cartBean = (CartBeanBww) objCartBean;
			} else {
				cartBean = new CartBeanBww();
				session.setAttribute("cart", cartBean);
			}
            pw.println("items are :"+items);
            pw.println("Total : "+total);
			cartBean.addCartItem(type, items, total);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
		
}
	
	public boolean addSharables(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		boolean flag=false;
		//main items
		String items = "";
		double total = 0.0;
		String nachos = request.getParameter("sharables0");
		String nachos_chicken = request.getParameter("sharables1");
		String cheeseburger_slammers = request.getParameter("sharables2");
		String pork_slammers = request.getParameter("sharables3");
		String chicken_tender_slammers = request.getParameter("sharables4");
		String sliced_steak_slammers  = request.getParameter("sharables5");
		String mini_beef_dog_slammers = request.getParameter("sharables6");
		String mini_beef_dog_slammers_ccsauce = request.getParameter("sharables7");
		String fried_pickles = request.getParameter("sharables8");
		String the_sampler = request.getParameter("sharables9");
		String tablegating_sampler = request.getParameter("sharables10");
		String mozzarrela_sticks = request.getParameter("sharables11");
		String chips_salsa = request.getParameter("sharables12");
		String chili_con_queso_dip = request.getParameter("sharables13");
		String pretzels = request.getParameter("sharables14");
		String roasted_garlic_mushrooms = request.getParameter("sharables15");
		String mini_corn_dogs = request.getParameter("sharables16");
		String spinach_artichoke_dip = request.getParameter("sharables17");
		String chicken_quesadila = request.getParameter("sharables18");
		String jalapeno_pepper_bites = request.getParameter("sharables19");
		String molten_dip = request.getParameter("sharables20");
		
		//sauce
		String sauce3 = request.getParameter("sauce3");
		String sauce4 = request.getParameter("sauce4");
		String sauce5 = request.getParameter("sauce5");
		String sauce6 = request.getParameter("sauce6");
		String sauce9 = request.getParameter("sauce9");
		String sauce10 = request.getParameter("sauce10");
		String seasoning = request.getParameter("seasoning");
		
		String sauce = request.getParameter("sampler_check");
		String queso = request.getParameter("pretzel1");
		String bww = request.getParameter("pretzel2");
		String mustard = request.getParameter("pretzel3");
		String bwwSauce = request.getParameter("bwwSauce");
		

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
					
					if (nachos != null) {
						flag=true;
						items = items+" Nachos ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'nachos'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (nachos_chicken != null) {
						flag=true;
						items = items+" Nachos with Chicken ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'nachos_chicken'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (cheeseburger_slammers != null) {
						flag=true;
						items = items+" Cheeseburger Slammers ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'cheeseburger_slammers'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (pork_slammers != null) {
						flag=true;
						items = items+" Pork Slammers  (sauce - "+sauce3+")";
						double price = 0.0;
						query = "select price from bww_menu where item = 'pork_slammers'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (chicken_tender_slammers != null) {
						flag=true;
						items = items+" Chicken Tender Slammers  (sauce - "+sauce4+")";
						double price = 0.0;
						query = "select price from bww_menu where item = 'chicken_tender_slammers'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
										
					if (sliced_steak_slammers != null) {
						flag=true;
						items = items+"Sliced Steak Slammers   (sauce - "+sauce5+")";
						double price = 0.0;
						query = "select price from bww_menu where item = 'sliced_steak_slammers'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (mini_beef_dog_slammers != null) {
						flag=true;
						items = items+"Mini Beef Dog Slammers   (sauce - "+sauce6+")";
						double price = 0.0;
						query = "select price from bww_menu where item = 'mini_beef_dog_slammers'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (mini_beef_dog_slammers_ccsauce != null) {
						flag=true;
						items = items+"Mini Beef Dog Slammers with Cheese and Chili   (sauce - "+sauce6+")";
						double price = 0.0;
						query = "select price from bww_menu where item = 'mini_beef_dog_slammers_ccsauce'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (fried_pickles != null) {
						flag=true;
						items = items+"Fried Pickles ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'fried_pickles'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (the_sampler != null) {
						flag=true;
						items = items+"The Sampler   (sauce - "+sauce9+"), with ";
						if(sauce != null){ items=items+sauce; }
						double price = 0.0;
						query = "select price from bww_menu where item = 'the_sampler'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (tablegating_sampler != null) {
						flag=true;
						items = items+"Tablegating Sampler   (sauce - "+sauce10+"), seasoning - "+seasoning;
						double price = 0.0;
						query = "select price from bww_menu where item = 'tablegating_sampler'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (mozzarrela_sticks != null) {
						flag=true;
						items = items+"Mozzarrela_Sticks ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'mozzarrela_sticks'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (chips_salsa != null) {
						flag=true;
						items = items+"Chips n salsa ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'chips_salsa'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (chili_con_queso_dip != null) {
						flag=true;
						items = items+"Chili corn queso dip";
						double price = 0.0;
						query = "select price from bww_menu where item = 'chili_con_queso_dip'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (pretzels != null) {
						flag=true;
						items = items+"3 Soft Pretzels, sauces - ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'pretzels'";
						if(queso != null) {items = items+"Queso ";}
						if(mustard != null) {items = items+"Mustard ";}
						if(bww != null) {
							items = items+bwwSauce+" ";
							}
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (roasted_garlic_mushrooms != null) {
						flag=true;
						items = items+"Roasted Garlic Mushrooms ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'roasted_garlic_mushrooms'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (mini_corn_dogs != null) {
						flag=true;
						items = items+"Mini Corn Dogs ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'mini_corn_dogs'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (spinach_artichoke_dip != null) {
						flag=true;
						items = items+"Spinach Artichoke Dip ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'spinach_artichoke_dip'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (chicken_quesadila != null) {
						flag=true;
						items = items+"Chicken Quesadila ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'chicken_quesadila'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					
					if (jalapeno_pepper_bites != null) {
						flag=true;
						items = items+"Jalapeno Pepper Bites ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'jalapeno_pepper_bites'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					if (molten_dip != null) {
						flag=true;
						items = items+"Molten Dip ";
						double price = 0.0;
						query = "select price from bww_menu where item = 'molten_dip'";
						rs = st.executeQuery(query);
						while (rs.next()) {
							price = rs.getDouble("price");
						}
						total = total + price;
					}
					 total= Double.parseDouble(new DecimalFormat("##.##").format(total));
					if(!flag) return flag;
					CartBeanBww cartBean = null;
					Object objCartBean = session.getAttribute("cart");

					if (objCartBean != null) {
						cartBean = (CartBeanBww) objCartBean;
					} else {
						cartBean = new CartBeanBww();
						session.setAttribute("cart", cartBean);
					}
					cartBean.addCartItem(type, items, total);			
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return flag;
	
	}
	
	//delete from cart
		 protected void deleteCart(HttpServletRequest request) {
			  HttpSession session = request.getSession();
			  String strItemIndex = request.getParameter("itemIndex");
			  CartBeanBww cartBean = null;
			  
			  Object objCartBean = session.getAttribute("cart");
			  if(objCartBean!=null) {
			   cartBean = (CartBeanBww) objCartBean ;
			  } else {
			   cartBean = new CartBeanBww();
			  }
			  cartBean.deleteCartItem(strItemIndex);
			 }
}
