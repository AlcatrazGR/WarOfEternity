package Items;

import java.io.Serializable;

/**
 * Class that adds the attributes of a gate / door item along with its 
 * get / set methods. It implements the Serializable interface in order 
 * that every object can be written in a file.
 * 
 * @author Vasilis Triantaris
 */
public class GateItem implements Serializable{
    
    //attributes that declares the way that the door/gate is blocking
    protected String blockingDirection;
      
    public void SetBlockingDirection(String bd){
        this.blockingDirection = bd;
    }
    
    public String GetBlockingDirection(){
        return this.blockingDirection;
    }
    
}
