
package producerconsumer;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Producer extends Thread {
    Buffer buffer;
    int waitProducers;
    int id;
    boolean isProducing = false;
    String product;
    public static GUIFrame frame = new GUIFrame();
    int idProduct=0;
    
    Producer(Buffer buffer, int waitProducers, int id) {
        this.buffer = buffer;
        this.waitProducers = waitProducers;
        this.id = id;
        
    }
    
    @Override
    public synchronized void run() {
        System.out.println("Running Producer...");
        String operaciones = "+-*/";
        String numeros = "0123456789";
        
        while(true){
            Random r = new Random();
            Random r2 = new Random();
            Random r3 = new Random();
                
            char operacion = operaciones.charAt(r.nextInt(4));
            char num1 = numeros.charAt(r.nextInt(10));
            char num2 = numeros.charAt(r.nextInt(10));
            product = new StringBuilder().append(operacion).append(num1).append(num2).toString();
            isProducing = true;
            try {
                wait(waitProducers);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.buffer.produce(product);
            
            idProduct++;
            //System.out.println("Producer produced: " + product);
            Buffer.print("Producer" + id + " produced: " + product);
            frame.tablaProductor(this);
            
            //frame.setVisible(true);
          
            System.out.println(isProducing);
            isProducing = false;
            
            
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
