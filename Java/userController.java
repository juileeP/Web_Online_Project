import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.userBean;
import beans.CartBean;
import beans.CartBeanBww;
import beans.cartItemBean;
import beans.cartItemBww;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.google.gson.Gson;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

public class userController extends HttpServlet {
	
	boolean flagUser = false;

	public boolean isFlagUser() {
		return flagUser;
	}

	public void setFlagUser(boolean flagUser) {
		this.flagUser = flagUser;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

	    
		String strAction = request.getParameter("radio1");
        String form = request.getParameter("form");
        String edit = request.getParameter("type");

		pw.println("<html>");
		pw.println("<head>");
		pw.println("</head>");
		pw.println("<body>");
		//pw.println("YOUR ORDER :");
		pw.println("<br>");
	//	pw.println(" Form name "+form);
		
		if(form != null && !form.equals(""))
		{
			if(form.equals("payment")) 
			{
			    String token = request.getParameter("stripeToken");
			    payment(token,request, response);
			    pw.println("<br>");
			    sendEmail(request, response,flagUser);
			    response.sendRedirect("../finalConfirmPage.jsp");
			   /* pw.println("<p><b>Your order has been successfully placed<b><p>");
			    pw.println("<br>");
			    pw.println("<p> A confirmation email has been sent<p> ");
			    pw.println("<a href=\"mainPage.html\">Back to Main Page</a>");
			    */
			}
			
		}
      
		//Edit details for non-registered user
		if(edit != null && !edit.equals(""))
		{
			if(edit.equals("edit"))
			{
			//	pw.println("editing..");
				editUser(request, response);
				response.sendRedirect("../cart.jsp");
			}
		}
		
		if(form != null && !form.equals(""))
		{
			if(form.equals("paymentOrder")) 
			{
				String token = request.getParameter("stripeToken");
			    paymentOrder(token,request, response);
			    pw.println("<br>");
			    sendEmailOrder(request, response);
			    response.sendRedirect("../finalConfirmPage.jsp");
			   /* pw.println("<p><b>Your order has been successfully placed<b><p>");
			    pw.println("<br>");
			    pw.println("<p> A confirmation email has been sent<p> ");
			    pw.println("<a href=\"mainPage.html\">Back to Main Page</a>");*/
			}
		}
		
		//User details for non-registered customer
		if(form != null && !form.equals(""))
		{
			if(form.equals("infoForm")) 
			{
			setUser(request, response);
			response.sendRedirect("../restro.html"); 

			}
		}
		//User details for Registered customer
		if(form != null && !form.equals(""))
		{
			if(form.equals("userForm")) 
			{
			setRegUser(request, response);
			
			setFlagUser(true);
			response.sendRedirect("../restro.html"); 
			}
		}
		
		//Edit registered customer details 
		if(form != null && !form.equals(""))
		{
			if(form.equals("eidtUserForm")) 
			{
				pw.println("inside edit user form");
			editRegUser(request, response);
			pw.println("returning");
			response.sendRedirect("../success1.jsp"); 
			}
		}
		
				
		//Sending email for Contact Us comments form
		if(form != null && !form.equals(""))
		{
			if(form.equals("commentsForm")) 
			{
			sendCommentsEmail(request, response);
			response.sendRedirect("../confirmFeedback.html"); 
			}
		}
		//Payment refunds function
		if(form != null && !form.equals(""))
		{
			if(form.equals("adminPayment")) 
			{
		    String chargeId = request.getParameter("chargeId");	
			adminPayment(chargeId, request, response);
			//response.sendRedirect("../restro.html"); 
			}
		}
		//Directing to user Particular restaurant page, based on choice selected
		if (strAction != null && !strAction.equals("")) {
			if (strAction.equals("chipotle")) {
				//pw.println("Chipotle");	
				response.sendRedirect("../chip_o.html");
			}
			else {
				response.sendRedirect("../bww_order.html");
				//pw.println("BWW");	
			}
		}
	}
	
	//email sending for previously placed orders
	public void sendEmailOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String orderChip = (String)session.getAttribute("setOrderChip");
		String orderBWW = (String)session.getAttribute("setOrderBWW");
		double orderTotal = (Double)request.getSession().getAttribute("setTotal");
		String email = (String)session.getAttribute("email");
		String name = (String)session.getAttribute("userid");
		String no = (String)session.getAttribute("no");	
		String location = (String)session.getAttribute("location");
		//String time = request.getParameter("time");
		
		String from = "email";
		String toUser = "email";
		String toOwner = "email";
        String toChipotle = "email";
        String toBWW = "email";
        
        String subjectUser = "Beck and Call Food Delivery - Your order ";
        String subjectOwner = "Order details for "+name;
        String subjectChipotle = "Beck and Call Food Delivery Order Details (Chipotle)";
        String subjectBWW = "Beck and Call Food Delivery Order Details (BWW)  ";
        
        String messageUser = "Name : "+name+" \n ";
        messageUser = messageUser+"Location : "+location+" \n ";
        messageUser = messageUser+"Contact No : "+no+" \n ";
       // if(time != null)
	    //	contentOwner.append("Time : "+time+" \n");
        
        String messageOwner = "";
        if(orderChip != null && orderChip != "") {
        	messageUser = messageUser+orderChip.replace("<br/>", "\n");
        	messageOwner = messageOwner+orderChip.replace("<br/>", "\n");
        }
        if(orderBWW != null && orderBWW != "") {
        	messageUser = messageUser+orderBWW.replace("<br/>", "\n");
        	messageOwner = messageOwner+orderBWW.replace("<br/>", "\n");
        }
        
        //delivery fee should be calculated
 
        messageUser = messageUser+ "Grand Total : "+orderTotal+" \n";
        messageOwner = messageOwner+ "Grand Total : "+orderTotal+" \n";
        
        try {
            Properties props = new Properties();
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            final String user = "email";
            final String pass = "3028579045";
            
           Session sessionMail = Session.getDefaultInstance(props, new Authenticator(){ 
            	protected PasswordAuthentication
            	getPasswordAuthentication() {return new PasswordAuthentication(user,pass); }
            });

            
           //send to user
            MimeMessage msgUser = new MimeMessage(sessionMail);
            msgUser.setText(messageUser);
            msgUser.setSubject(subjectUser);
            msgUser.setFrom(new InternetAddress(email));
            msgUser.addRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
            Transport.send(msgUser);
            //pw.println("Email sent to user");
            
            //send to Owner
            MimeMessage msgOwner = new MimeMessage(sessionMail);
            msgOwner.setText(messageOwner);
            msgOwner.setSubject(subjectOwner);
            msgOwner.setFrom(new InternetAddress(from));
            msgOwner.addRecipient(Message.RecipientType.TO, new InternetAddress(toOwner));
            Transport.send(msgOwner);
           // pw.println("Email sent to Owner");
            
            //send to Chipotle
            if(orderChip != null && orderChip != "") {
            MimeMessage msgChipotle = new MimeMessage(sessionMail);
            msgChipotle.setText(orderChip.replace("<br/>", "\n"));
            msgChipotle.setSubject(subjectChipotle);
            msgChipotle.setFrom(new InternetAddress(from));
            msgChipotle.addRecipient(Message.RecipientType.TO, new InternetAddress(toChipotle));
            Transport.send(msgChipotle);
          // pw.println("Email sent to Chipotle");
            }
            
            //send to BWW
            if(orderBWW != null && orderBWW != "") {
            MimeMessage msgBWW = new MimeMessage(sessionMail);
            msgBWW.setText(orderBWW.replace("<br/>", "\n"));
            msgBWW.setSubject(subjectBWW);
            msgBWW.setFrom(new InternetAddress(from));
            msgBWW.addRecipient(Message.RecipientType.TO, new InternetAddress(toBWW));
            Transport.send(msgBWW);
           //pw.println("Email sent to BWW");
            }
            

        } catch (AddressException ex) {
            pw.println("Wrong Email address");

        } catch (MessagingException ex) {
        	ex.printStackTrace();
            pw.println("Messaging Exception");
        }
		
	}
	
	
	//payment function for previously placed order
	public void paymentOrder(String token, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		
		// for output display
		double orderTotal = 0.0;
		double tip = 0.0;
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
				
		//pw.println("Token is : "+token);
		if(request.getSession().getAttribute("setTotal")!=null)
		{
		Double stringTotal = (Double)request.getSession().getAttribute("setTotal");
		orderTotal = stringTotal.doubleValue();
		}
		tip = Double.parseDouble(request.getParameter("tip"));
        double sum = orderTotal+tip;
        sum = sum*100;
        int newSum =(int) sum;
		
		Stripe.apiKey = "privateKey";
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", newSum);
        chargeMap.put("currency", "usd");
        chargeMap.put("card", token);
        try {
            Charge charge = Charge.create(chargeMap);
        } catch (StripeException e) {
            e.printStackTrace();
        }
		
	}
	
	//Payment admin part to refund a charge
	public void adminPayment(String chargeId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		try{
		// for output display
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//retrieving a charge and refund a charge
		Stripe.apiKey = "privateKey";
		Charge ch = Charge.retrieve(chargeId);
		ch.refund();
		
		//capture a charge to keep it more than 7 days
		ch.capture();
		}
		catch (StripeException e) {
            e.printStackTrace();
        }
	}
	
	public void sendCommentsEmail(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String from = request.getParameter("email");
		//pw.println("From "+from);
		String name = request.getParameter("first");
		String subject = "Comments / Questions Form from "+name;
		//pw.println("subject");
		String to = "email";
		String message = request.getParameter("comments");
		//pw.println("Comments are : "+message);
		
		try {
            Properties props = new Properties();
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            final String user = "email";
            final String pass = "3028579045";
            
           Session sessionMail = Session.getDefaultInstance(props, new Authenticator(){ 
            	protected PasswordAuthentication
            	getPasswordAuthentication() {return new PasswordAuthentication(user,pass); }
            });

            
           //send to user
            MimeMessage msgUser = new MimeMessage(sessionMail);
            msgUser.setText(message);
            msgUser.setSubject(subject);
            msgUser.setFrom(new InternetAddress(from));
            msgUser.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            Transport.send(msgUser);
           /* pw.println("<br>");
            pw.println("Hi, "+name+" ! Your response has been successfuly saved.");
            pw.println("<br>");
            pw.println("Thanks for your feedback !");
            pw.println("<br>");
            pw.println("<a href=\"mainPage.html\">Back to Main Page</a>");*/
            //pw.println("Email sent to user");
		}
		catch (AddressException ex) {
            pw.println("Wrong Email address");

        } catch (MessagingException ex) {
        	ex.printStackTrace();
            pw.println("Messaging Exception");
        }
	}
	
	public void editRegUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();

		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//
		String name = request.getParameter("name");
		String contactNo = request.getParameter("no");
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		
		
		    if(name != null && !name.equals(""))
			{session.setAttribute("userdid",name);}
		    if(contactNo != null && !contactNo.equals(""))
			{session.setAttribute("no",contactNo);}
		    if(email != null && !email.equals(""))
			{session.setAttribute("email",email);}
		    if(location != "select" && !location.equals("select"))
			{session.setAttribute("location",location);}	
		    pw.println("at the end of editReguser");
		
	}
	public void setRegUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();

		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//
		String name = (String)session.getAttribute("userid");
		String contactNo = (String)session.getAttribute("no");
		String email = (String)session.getAttribute("email");
		String location = (String)session.getAttribute("location");

		/*pw.println(" Name is : "+name);
		pw.println(" Phone is : "+contactNo);
		pw.println(" Email is : "+email);
		pw.println(" Location is : "+location);*/
		userBean user = null;
		Object objCartBean = session.getAttribute("user");

		if (objCartBean != null) {
			user = (userBean) objCartBean;
		} else {
			user = new userBean();
			user.setname(name);
			user.setContactNo(contactNo);
			user.setEmail(email);
			user.setLocation(location);
			session.setAttribute("user", user);
		}
		
		
		
	}
	public void sendEmail(HttpServletRequest request, HttpServletResponse response, boolean flagUser)
			throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//Db part for storing orders
		String url = "jdbc:mysql://localhost:3306/onlinesite";
 		Connection con = null;
 		PreparedStatement stmt = null;
 		
 		//beans to be used
		CartBean chipotle;
		CartBeanBww bww;
		userBean UserBean;
		
		//user info
		String name="";
		String location="";
		String contactNo = "";
		String email = "";
		
		//chipotle variables
		String filling="";
		String rice="";
		String bean="";
		String toppings="";
		String sides="";
		String drinks="";
		String type="";
		String tacoType="";
		double total=0.0;
		
		//BWW variables
		String typeBww;
	    String items;
	    double totalBww=0.0;
		
	    //
	    String headerUser = "";
	    StringBuffer contentUser = new StringBuffer();
	    StringBuffer contentOwner = new StringBuffer();
	    StringBuffer contentChipotle = new StringBuffer();
	    StringBuffer contentBWW = new StringBuffer();
	    
	    double grandTotal = (Double)request.getSession().getAttribute("orderTotal");
	    String time = request.getParameter("time");
	    
	    Object objCartBean1 = session.getAttribute("user");
	   if (objCartBean1 != null) {
         UserBean = (userBean)objCartBean1;
         name = UserBean.getname();
         location = UserBean.getLocation();
         contactNo = UserBean.getContactNo();
         email = UserBean.getEmail();
	   }
	   
	    headerUser = "Hi, "+name+" ! ";
	    contentOwner.append("Name : "+name+" \n");
	    contentOwner.append("Contact No : "+contactNo+" \n");
	    contentOwner.append("Location : "+location+" \n");
	    if(time != null)
	    	contentOwner.append("Time : "+time+" \n");
	    
		Object objCartBean2 = session.getAttribute("cartChip");
		chipotle = (CartBean) objCartBean2;
		if (chipotle.getLineItemCount()>0) {
			contentUser.append("CHIPOTLE \n");
			contentOwner.append("CHIPOTLE \n");
			contentChipotle.append("CHIPOTLE \n");
			
			ArrayList<cartItemBean> chipotleCart = chipotle.getCartItems();
			for(int i=0; i<chipotleCart.size(); i++)
			{
			 contentUser.append("Order No "+(i+1)+" \n");
			 contentOwner.append("Order No "+(i+1)+" \n");
			 contentChipotle.append("Order No "+(i+1)+" \n");
			 type = chipotleCart.get(i).getType();
			 contentUser.append("Type : "+type+" \n");
			 contentOwner.append("Filling : "+filling+" \n");
			 contentChipotle.append("Filling : "+filling+" \n");
			 filling = chipotleCart.get(i).getFilling();	
			 contentUser.append("Filling : "+filling+" \n");
			 contentChipotle.append("Filling : "+filling+" \n");
			 contentOwner.append("Filling : "+filling+" \n");
			 rice = chipotleCart.get(i).getRice();
			 contentUser.append("Rice : "+rice+" \n");
			 contentOwner.append("Rice : "+rice+" \n");
			 contentChipotle.append("Rice : "+rice+" \n");
			 bean = chipotleCart.get(i).getBean();
			 contentUser.append("Bean : "+bean+" \n");
			 contentOwner.append("Bean : "+bean+" \n");
			 contentChipotle.append("Bean : "+bean+" \n");
			 toppings = chipotleCart.get(i).getToppings();
			 contentUser.append("Toppings : "+toppings+" \n");
			 contentOwner.append("Toppings : "+toppings+" \n");
			 contentChipotle.append("Toppings : "+toppings+" \n");
			 sides = chipotleCart.get(i).getSides();
			 if(sides != null && !sides.equals(""))
			 {contentUser.append("Sides : "+sides);
			 contentOwner.append("Sides : "+sides);
			 contentChipotle.append("Sides : "+sides);}
			 drinks = chipotleCart.get(i).getDrinks();
			 if(drinks != null && !drinks.equals(""))
			 {contentUser.append("Drinks : "+drinks);
			 contentOwner.append("Drinks : "+drinks);
			 contentChipotle.append("Drinks : "+drinks);}
			 tacoType = chipotleCart.get(i).getTacoType();
			 if(tacoType!= null && !tacoType.equals(""))
			 {contentUser.append("Taco Type : "+tacoType+" \n");
			 contentOwner.append("Taco Type : "+tacoType+" \n");
			 contentChipotle.append("Taco Type : "+tacoType+" \n");}
			 total = chipotleCart.get(i).getTotal();
			 contentUser.append("total for Order"+(i+1)+" : "+total+" \n \n \n");
			 contentOwner.append("total for Order"+(i+1)+" : "+total+" \n \n \n");
			 contentChipotle.append("total for Order"+(i+1)+" : "+total+" \n \n \n");
			}
		}
		
		//
		Object objCartBean3 = session.getAttribute("cart");
		bww = (CartBeanBww) objCartBean3;
		if(bww.getLineItemCount()>0){
			contentUser.append("BUFFALO WLD WINGS \n");
			contentOwner.append("BUFFALO WLD WINGS \n");
			contentBWW.append("BUFFALO WLD WINGS \n");
			
			ArrayList<cartItemBww> bwwCart = bww.getCartItems();
			for(int i=0; i<bwwCart.size(); i++)
			{
				contentUser.append("Order No "+(i+1)+" \n");	
				contentOwner.append("Order No "+(i+1)+" \n");	
				contentBWW.append("Order No "+(i+1)+" \n");	
				typeBww =  bwwCart.get(i).getType();
				contentUser.append("Type : "+typeBww+" \n");
				contentOwner.append("Type : "+typeBww+" \n");
				contentBWW.append("Type : "+typeBww+" \n");
				items =  bwwCart.get(i).getItems();
				contentUser.append(items+" \n");
				contentOwner.append(items+" \n");
				contentBWW.append(items+" \n");
				totalBww = bwwCart.get(i).getTotal();
				contentUser.append("total for Order"+(i+1)+" : "+totalBww+" \n");
				contentOwner.append("total for Order"+(i+1)+" : "+totalBww+" \n");
				contentBWW.append("total for Order"+(i+1)+" : "+totalBww+" \n");
			}
		}
		
		//delivery fee needs to be output accordingly 
		if(total+totalBww<10.00) {
			contentUser.append("Delivery Fee : $3.00 \n");
		    contentOwner.append("Delivery Fee : $3.00 \n");
		}
		else if((total+totalBww)>10.00 && (total+totalBww)<=20) {
			contentUser.append("Delivery Fee : $4.00 \n");
			contentOwner.append("Delivery Fee : $4.00 \n");
		}
		else if((total+totalBww)>20.00 && (total+totalBww)<=25) {
			contentUser.append("Delivery Fee : $5.00 \n");
			contentOwner.append("Delivery Fee : $5.00 \n");
		}
		else if((total+totalBww)>25) {
			contentUser.append("Delivery Fee : "+(0.2*(total+totalBww))+" \n");
			contentOwner.append("Delivery Fee : "+(0.2*(total+totalBww))+" \n");
		}
		
		//content.append("Tip : "+Double.parseDouble(request.getParameter("tip"))+" \n");
		contentUser.append("Grand Total : "+grandTotal+" \n");
		contentOwner.append("Grand Total : "+grandTotal+" \n");
		
		String from = "me@businessname.com";
        String toUser = "email";
        String toOwner = "email";
        String toChipotle = "email";
        String toBWW = "email";
        
        String subjectUser = "Beck and Call Food Delivery - Your order ";
        String subjectOwner = "Order details for "+name;
        String subjectChipotle = "Beck and Call Food Delivery Order Details (Chipotle) ";
        String subjectBWW = "Beck and Call Food Delivery Order Details (BWW)  ";
        String userOrder = contentUser.toString();
        String messageUser = headerUser+" "+userOrder;
        String messageOwner = contentOwner.toString();
        String messageChipotle = contentChipotle.toString();
        String messageBWW = contentBWW.toString();
        
        //Db part for storing orders
        try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, "root", "udfoodie");
			String query = "";
			if(isFlagUser())
			{
				//pw.println("Im true");
				query = "insert into member_orders (email, orderChip, orderBWW, orderTotal) values (?, ?, ?, ?)";
			    stmt = con.prepareStatement(query); 
	            stmt.setString(1,email);  
	            if (chipotle.getLineItemCount()>0)  {	
	            stmt.setString(2,messageChipotle.replace("\n", "<br/>"));
	            }
	            else{
	            	stmt.setString(2,null);
	            }
	            if (bww.getLineItemCount()>0)  {
	            stmt.setString(3,messageBWW.replace("\n", "<br/>"));
	            }
	            else{
	            	stmt.setString(3,null);
	            }
	            stmt.setDouble(4,totalBww+total);
	            stmt.executeUpdate(); 
			}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
       
        try {
            Properties props = new Properties();
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            final String user = "email";
            final String pass = "3028579045";
            
           Session sessionMail = Session.getDefaultInstance(props, new Authenticator(){ 
            	protected PasswordAuthentication
            	getPasswordAuthentication() {return new PasswordAuthentication(user,pass); }
            });

            
           //send to user
            MimeMessage msgUser = new MimeMessage(sessionMail);
            msgUser.setText(messageUser);
            msgUser.setSubject(subjectUser);
            msgUser.setFrom(new InternetAddress(from));
            msgUser.addRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
            Transport.send(msgUser);
            //pw.println("Email sent to user");
            
            //send to Owner
            MimeMessage msgOwner = new MimeMessage(sessionMail);
            msgOwner.setText(messageOwner);
            msgOwner.setSubject(subjectOwner);
            msgOwner.setFrom(new InternetAddress(from));
            msgOwner.addRecipient(Message.RecipientType.TO, new InternetAddress(toOwner));
            Transport.send(msgOwner);
           // pw.println("Email sent to Owner");
            
            //send to Chipotle
            if (chipotle.getLineItemCount()>0)  {
            MimeMessage msgChipotle = new MimeMessage(sessionMail);
            msgChipotle.setText(messageChipotle);
            msgChipotle.setSubject(subjectChipotle);
            msgChipotle.setFrom(new InternetAddress(from));
            msgChipotle.addRecipient(Message.RecipientType.TO, new InternetAddress(toChipotle));
            Transport.send(msgChipotle);
           // pw.println("Email sent to Chipotle");
            }
            
            //send to BWW
            if (bww.getLineItemCount()>0)  {
            MimeMessage msgBWW = new MimeMessage(sessionMail);
            msgBWW.setText(messageBWW);
            msgBWW.setSubject(subjectBWW);
            msgBWW.setFrom(new InternetAddress(from));
            msgBWW.addRecipient(Message.RecipientType.TO, new InternetAddress(toBWW));
            Transport.send(msgBWW);
           // pw.println("Email sent to BWW");
            }
            

        } catch (AddressException ex) {
            pw.println("Wrong Email address");

        } catch (MessagingException ex) {
        	ex.printStackTrace();
            pw.println("Messaging Exception");
        }
	}
	
	public void payment(String token, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		
		// for output display
		double orderTotal = 0.0;
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
				
		//pw.println("Token is : "+token);
		if(request.getSession().getAttribute("orderTotal")!=null)
		{
		Double stringTotal = (Double)request.getSession().getAttribute("orderTotal");
		orderTotal = stringTotal.doubleValue();
		}
		double tip = Double.parseDouble(request.getParameter("tip"));
		//pw.println("Tip : "+tip);
        double sum = orderTotal+tip;
        sum = sum*100;
        int newSum =(int) sum;
		//pw.println("Final amount to be charged : "+sum);
		
		Stripe.apiKey = "privateKey";
		//pw.println("I got the key");
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", newSum);
        chargeMap.put("currency", "usd");
       // pw.println("I created the chargeMap");
       // Map<String, Object> cardMap = new HashMap<String, Object>();
        //cardMap.put("number", "4242424242424242");
       // cardMap.put("exp_month", 12);
        //cardMap.put("exp_year", 2020);
        chargeMap.put("card", token);
        //pw.println("I got the token : "+token);
        try {
            Charge charge = Charge.create(chargeMap);
            //pw.println("Successfully charged");
            //pw.println("Charge is "+charge);
        } catch (StripeException e) {
            e.printStackTrace();
        }
		
	}
	
	public void editUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		HttpSession session = request.getSession();

		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//
		String name = request.getParameter("name");
		String contactNo = request.getParameter("no");
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		
		userBean user = null;
		Object objCartBean = session.getAttribute("user");

		if (objCartBean != null) {
			user = (userBean) objCartBean;
		} else {
			user = new userBean();
		}
		    if(name != null && !name.equals(""))
			{user.setname(name);}
		    if(contactNo != null && !contactNo.equals(""))
			{user.setContactNo(contactNo);}
		    if(email != null && !email.equals(""))
			{user.setEmail(email);}
		    if(location != "select" && !location.equals("select"))
			{user.setLocation(location);}
			session.setAttribute("user", user);
		}
		
	
	
	public void setUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		HttpSession session = request.getSession();

		// for output display
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//
		String name = request.getParameter("name");
		String contactNo = request.getParameter("no");
		String email = request.getParameter("email");
		String location = request.getParameter("location");

		userBean user = null;
		Object objCartBean = session.getAttribute("user");

		if (objCartBean != null) {
			user = (userBean) objCartBean;
		} else {
			user = new userBean();
			user.setname(name);
			user.setContactNo(contactNo);
			user.setEmail(email);
			user.setLocation(location);
			session.setAttribute("user", user);
		}
		
		
		
	}
}