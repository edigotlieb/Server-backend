

/**
 *
 * @author talpiot
 */
public abstract class Request {    
   
    public enum TYPE {
       USER,APP,DTD
    }
    
   abstract public Credentials getCreds();
   abstract public TYPE getType();
   
}
