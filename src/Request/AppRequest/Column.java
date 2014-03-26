/**
 * FILE : Colomb.java
 * AUTHORS : Erez Gotlieb
 * DESCRIPTION :
 */
package Request.AppRequest;

public class Column {

    public enum COL_TYPE {
        INT, VARCHAR, TIME_STAMP, DATE
    }

    private final String colName;
    private final COL_TYPE type;
    private final int size; // -1 for default
    boolean autoInc = false; 
    boolean isPrimary = false;
    
    public Column(String colName, COL_TYPE dataType, int size) {
        this.colName = colName;
        this.type = dataType;
        this.size = size;
    }

    
    public Column(String colName, COL_TYPE dataType, int size, boolean autoInc, boolean isPrimary) {
        this(colName, dataType, size);
        this.autoInc = autoInc;
        this.isPrimary = isPrimary;
    }
    
    
    
    @Override
    public String toString() {
        return colName + " " + type + "(" + size + ")";
    }
}
