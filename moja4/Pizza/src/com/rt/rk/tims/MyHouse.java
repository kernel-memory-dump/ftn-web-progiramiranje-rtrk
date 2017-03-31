package com.rt.rk.tims;



public class MyHouse {
   private boolean pizzaArrived = false;

   public void eatPizza(){
       synchronized(this){
           while(!pizzaArrived){
               try {
				wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
           }
       }
       System.out.println("yumyum..");
   }

   public void pizzaGuy(){
       synchronized(this){
            this.pizzaArrived = true;
            notifyAll();
       }
   }
}
