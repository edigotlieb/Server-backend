package Request;



/**
 *
 * @author talpiot
 */
public abstract class Request {           
    
    protected Credentials creds;
    
    public Request(Credentials creds) {
        this.creds = creds;
    }
    
    public enum TYPE {
       USER,APP,DTD
    }
    
   abstract public Credentials getCreds();
   abstract public TYPE getType();
   
   
}
