package beans;

import java.util.ArrayList;

public class CartBean {
	
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

	 public void addCartItem(String filling, String rice,
			 String bean, String toppings, String sides, String drinks, String tacoType, String type, double total)
			 {
			   cartItemBean cartItem = new cartItemBean();
			   try {
			 
			    cartItem.setFilling(filling);
			    cartItem.setRice(rice);
			    cartItem.setBean(bean);
			    cartItem.setToppings(toppings);
			    cartItem.setSides(sides);
			    cartItem.setDrinks(drinks);
			    cartItem.setTotal(total);
			    cartItem.setTacoType(tacoType);
			    cartItem.setType(type);
			    alCartItems.add(cartItem);
			    calculateOrderTotal();
			    
			     
			   } catch (NumberFormatException nfe) {
			    System.out.println("Error while parsing from String to primitive types: "+nfe.getMessage());
			    nfe.printStackTrace();
			   }
			  }
	 
	 public void addCartItem(cartItemBean cartItem) {
		  alCartItems.add(cartItem);
		 }
		  
		 public cartItemBean getCartItem(int iItemIndex) {
		  cartItemBean cartItem = null;
		  if(alCartItems.size()>iItemIndex) {
		   cartItem = (cartItemBean) alCartItems.get(iItemIndex);
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
			  
			 protected void calculateOrderTotal() {
			  double dblTotal = 0;
			  for(int counter=0;counter<alCartItems.size();counter++) {
			   cartItemBean cartItem = (cartItemBean) alCartItems.get(counter);
			   dblTotal+=cartItem.getTotal();
			    
			  }
			  setOrderTotal(dblTotal);
}
}