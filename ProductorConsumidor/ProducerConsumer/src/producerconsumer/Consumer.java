
package producerconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;
        
public class Consumer extends Thread {
    Buffer buffer;
    int waitConsumers;
    int id;
    String product;
    public static GUIFrame frame = new GUIFrame();
    
    Consumer(Buffer buffer, int waitConsumers, int id) {
        this.buffer = buffer;
        this.waitConsumers = waitConsumers;
        this.id = id;
    }
    
    @Override
    public synchronized void run() {
        System.out.println("Running Consumer...");
        
        
        while(true) {
            try {
                wait(waitConsumers);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
            product = this.buffer.consume();
            
                
                char operando = product.charAt(0);
                int num1 = Character.getNumericValue(product.charAt(1));
                int num2 = Character.getNumericValue(product.charAt(2));
                
                if(operando == '+'){
                    product = (num1 + num2) + "";
                }
                else if(operando == '-'){
                    product = (num1 - num2) + "";
                }
                else if(operando == '*'){
                    product = (num1*num2) + "";
                }
                else{
                    if(num2 == 0){
                        product = "NaN";
                    }
                    else{
                        product = (num1/num2) + "";
                    }
                }
                
            
            //System.out.println("Consumer consumed: " + product);
            Buffer.print("Consumer" + id + " consumed: " + product);
            frame.tablaConsumidor(this);
           // frame.setVisible(true);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
