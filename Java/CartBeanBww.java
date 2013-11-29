package beans;

import beans.cartItemBww;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class CartBeanBww {


	 private ArrayList alCartItems = new ArrayList();
	 private double dblOrderTotal ;
	 
	 public int getLineItemCount() {
		  return alCartItems.size();
		 }
	 
	 public void deleteCartItem(String strItemIndex) {
		  int iItemIndex = 0;
	  
		  try {
		   iItemIndex = Integer.parseInt(strItemIndex);
		   alCartItems.remove(iItemIndex - 1);
		   calculateOrderTotal();
		  } catch(NumberFormatException nfe) {
		   System.out.println("Error while deleting cart item: "+nfe.getMessage());
		   nfe.printStackTrace();
		  }
		 }
	 
	 public void addCartItem(String type, String items, double total)   {
	

			   cartItemBww cartItem = new cartItemBww();
			   try {
			 
			    cartItem.setType(type);
			    cartItem.setItems(items);
			    cartItem.setTotal(total);
			    alCartItems.add(cartItem);
			    calculateOrderTotal();
			    
			     
			   } catch (NumberFormatException nfe) {
			    System.out.println("Error while parsing from String to primitive types: "+nfe.getMessage());
			    nfe.printStackTrace();
			   }
			  }
	 
	 public void addCartItem(cartItemBww cartItem) {
		  alCartItems.add(cartItem);
		 }
		  
		 public cartItemBww getCartItem(int iItemIndex) {
		  cartItemBww cartItem = null;
		  if(alCartItems.size()>iItemIndex) {
		   cartItem = (cartItemBww) alCartItems.get(iItemIndex);
		  }
		  return cartItem;
		 }
		  
		 public ArrayList getCartItems() {
		  return alCartItems;
		 }
		 public void setCartItems(ArrayList alCartItems) {
		  this.alCartItems = alCartItems;
		 }
		 
		 public double getOrderTotal() {
			  return dblOrderTotal;
			 }
	     public void setOrderTotal(double dblOrderTotal) {
			  this.dblOrderTotal = dblOrderTotal;
			 }
			  
	    protected void calculateOrderTotal()  {
			  double dblTotal = 0;
					
			  for(int counter=0;counter<alCartItems.size();counter++) {
			   cartItemBww cartItem = (cartItemBww) alCartItems.get(counter);
			  // pw.println("initial cost is : "+cartItem.getTotal());
			   dblTotal+=cartItem.getTotal();  
			     }
			 // pw.println("Total total is : "+dblTotal);
			  setOrderTotal(dblTotal);
          }
}
