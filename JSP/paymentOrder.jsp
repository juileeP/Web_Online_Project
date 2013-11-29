<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
<style rel='stylesheet' type='text/css'>
div#container {
    position: relative;
    margin: 0 auto;
	height: auto;
	max-width: 1200px;
}
div#left {
	margin-top: 40px;
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	width: 350px;
	background: white;
	padding: 5px;
	height: auto;
	border-right: 1px solid black;
}
div#right {
	margin-top: 0px;
	height: auto;
    margin-left: 400px;
    background: white;
    border: 1px solid white;
    padding: 10px;
}
</style>
 <title>Stripe Getting Started Form</title>
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
Stripe.setPublishableKey('key');
jQuery(function($) {
  $('#payment-form').submit(function(event) {
    var $form = $(this);


    // Disable the submit button to prevent repeated clicks
    $form.find('button').prop('disabled', true);

    Stripe.createToken($form, stripeResponseHandler);

    // Prevent the form from submitting with the default action
    return false;
  });
});

var stripeResponseHandler = function(status, response) {
  var $form = $('#payment-form');

  if (response.error) {
    // Show the errors on the form
    $form.find('.payment-errors').text(response.error.message);
    $form.find('button').prop('disabled', false);
  } else {
    // token contains id, last4, and card type
    var token = response.id;
	//alert("Token is :"+token);
    // Insert the token into the form so it gets submitted to the server
    $form.append($('<input type="hidden" name="stripeToken" />').val(token));
    // and submit
    $form.get(0).submit();
  }
};

</script> 
<title>Payment Page</title>
</head>
<body>
<div id="container">
<h2 align="center"><font color="red">Order Confirmation / Payment</font></h2>

<jsp:useBean id="user" scope="session" class="beans.userBean" />

  
<div id="left">

<p>
<form action="user/userController" method=GET id="payment-form">
<input type="hidden" name="form" value="paymentOrder">
  <span class="payment-errors"></span>
  <input type="hidden" name="orderTotal" value=amt>

  

 <li><b>Name : </b> <%= session.getAttribute("userid") %><br>
 <li><b>Contact No : </b> <%= session.getAttribute("no") %><br>
 <li><b>Email : </b> <%= session.getAttribute("email") %><br>
 <li><b>Location :</b> <%= session.getAttribute("location") %><br> 
 <p>
<li><b><font color="red">Select Time :</font></b><br> 

 <%
 GregorianCalendar calendar = new GregorianCalendar();
 int hour = calendar.get(Calendar.HOUR_OF_DAY);
 int min = calendar.get(Calendar.MINUTE);
session.setAttribute("newTotal",session.getAttribute("setTotal"));

 if(hour>20 && min>45) {
 %>

<p>Sorry, we are closed !!! Cannot be delivered</p>
<%
}
else if(hour>20) { %>
<select name="time" size="1" id="time" value="time">
<option value="9:30 pm">9:30 pm</option>
</select>
<% }
else if(hour>19 && min>15) { %>
<select name="time" size="1" id="time" value="time">
<option value="8:45 pm">8:45 pm</option>
<option value="9:30 pm">9:30 pm</option>
</select>
<% } 
else if(hour>18 && min>30) { %>
 <select name="time" size="1" id="time" value="time">
<option value="select">select</option>
<option value="8:00 pm">8:00 pm</option>
<option value="8:45 pm">8:45 pm</option>
<option value="9:30 pm">9:30 pm</option>
</select>
<% } 
else if(hour>17 && min<45) { %>
<select name="time" size="1" id="time" value="time">
<option value="select">select</option>
<option value="7:15 pm">7:15 pm</option>
<option value="8:00 pm">8:00 pm</option>
<option value="8:45 pm">8:45 pm</option>
<option value="9:30 pm">9:30 pm</option>
<% }
else if(hour>17 && min<45) { %>
<select name="time" size="1" id="time" value="time">
<option value="select">select</option>
<option value="6:30 pm">6:30 pm</option>
<option value="7:15 pm">7:15 pm</option>
<option value="8:00 pm">8:00 pm</option>
<option value="8:45 pm">8:45 pm</option>
<option value="9:30 pm">9:30 pm</option>
<% } 
else if(hour<=17) {
 %>
 <select name="time" size="1" id="time" value="time">
<option value="select">select</option>
<option value="5:45 pm">5:45 pm</option>
<option value="6:30 pm">6:30 pm</option>
<option value="7:15 pm">7:15 pm</option>
<option value="8:00 pm">8:00 pm</option>
<option value="8:45 pm">8:45 pm</option>
<option value="9:30 pm">9:30 pm</option>
</select>
<% } %>
<form action="user/userController" method=GET id="payment-form">
<input type="hidden" name="form" value="payment">
  <span class="payment-errors"></span>
  <input type="hidden" name="orderTotal" value=amt>
 <c:set var="total" value="${cartChip.orderTotal+cart.orderTotal}" />
<<p><br><b>Delivery Fee : </b>
 <c:choose>
  <c:when test="${total <= 10}">
  <c:set var="deliveryFee" value="3.00" />
  <c:out value="${deliveryFee}" /><br><br>
  </c:when>
  <c:when test="${(total > 10) && (total <= 20)}">
  <c:set var="deliveryFee" value="4.00" />
  <c:out value="${deliveryFee}" /><br><br>
  </c:when>
  <c:when test="${(total > 20) && (total <= 25)}">
  <c:set var="deliveryFee" value="5.00" />
  <c:out value="${deliveryFee}" /><br><br>
  </c:when>
  <c:when test="${total > 25}">
  <c:set var="deliveryFee" value="${0.2*total}"/><fmt:formatNumber type="number" maxFractionDigits="2" value="${deliveryFee}"/>
  </c:when>
 </c:choose>
<c:set var="finalTotal" value="${newTotal+deliveryFee}"/>
<% double total1 = (Double)session.getAttribute("setTotal"); %>
<% String s = (String) pageContext.getAttribute("deliveryFee");
double deliveryFee = Double.parseDouble(s); 
double newTotal = total1+deliveryFee; %>

<br><b>Grand Total :</b><%= newTotal %><br><br>
 <label for="tip"><b>Enter tip amount: </b></label>
 <input id="tip" name="tip" type="text" /> <br><br>
  <div class="form-row">
    <label>
      <span>Card Number</span>
      <input type="text" size="20" data-stripe="number"/>
    </label>
  </div>
<br>
  <div class="form-row">
    <label>
      <span>CVC</span>
      <input type="text" size="4" data-stripe="cvc"/>
    </label>
  </div>
<br>
  <div class="form-row">
    <label>
      <span>Expiration (MM/YYYY)</span>
      <input type="text" size="2" data-stripe="exp-month"/>
    </label>
    <span> / </span>
    <input type="text" size="4" data-stripe="exp-year"/>
  </div>
<br>
  <button STYLE="color: #FDD017; background-color:  #FF2400;" type="submit">Submit Payment</button>
</form>

 </div>
 
  <div id="right">
 <p><b>Review Your Order</b></p>
 <%
    if ((session.getAttribute("setOrderChip") != null) && (session.getAttribute("setOrderChip") != "")) {
%>
 <br><%= session.getAttribute("setOrderChip") %><br><br>
 <% } 
 if((session.getAttribute("setOrderBWW") != null) && (session.getAttribute("setOrderBWW") != "")) {
%>
<br><%= session.getAttribute("setOrderBWW") %><br><br>
 <% } %>
 <br>Total is : <%= session.getAttribute("setTotal") %><br>
 
  </div>

</div>
</body>
</html>
