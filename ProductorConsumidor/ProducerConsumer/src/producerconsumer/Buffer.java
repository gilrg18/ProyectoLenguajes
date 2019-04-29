
package producerconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Buffer {
    
    private String buffer;
    private String[] elements;
    private int lastElementPos;
    private int size;
    private GUIFrame frame;
    private int tareas=0;
    String product;
    
    Buffer(int size, GUIFrame frame) {
        this.buffer = "";
        this.size = size;
        this.elements = new String[size];
        this.lastElementPos = -1;
        this.frame = frame;
        this.frame.setProgress(0);
    }
    
    synchronized String consume() {
        
        if(this.lastElementPos == -1) {
            try {
                wait(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        product = buffer;
        
        System.out.println("PRODUCTO---------" + buffer);
        if(lastElementPos-1 >= 0){
            this.buffer = elements[lastElementPos-1];
            if(lastElementPos > 0){
                lastElementPos--;
                frame.setProgress((lastElementPos+1)*100/size);
            }
            System.out.println("Last element position:" + lastElementPos);
        }else{
            this.buffer = "";
            lastElementPos = -1;
            frame.setProgress(0);
            System.out.println("Last element position:" + lastElementPos);
        }
        
        notify();
        
        return product;
    }
    
    synchronized void produce(String product) {
        tareas++;
        frame.setTareas(tareas);
        if(this.lastElementPos == this.size-1) {
            try {
                wait(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.buffer = product;
        if(lastElementPos < size-1){
            lastElementPos++;
            frame.setProgress((lastElementPos+1)*100/size);
        }
        elements[lastElementPos] = product;
        System.out.println("Last element position:" + lastElementPos);
        notify();
    }
    
    static int count = 1;
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
}
