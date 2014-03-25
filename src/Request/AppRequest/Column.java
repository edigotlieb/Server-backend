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

    private String colName;
    private COL_TYPE type;
    private int size;

    public Column(String colName, COL_TYPE dataType, int size) {
        this.colName = colName;
        this.type = dataType;
        this.size = size;
    }

    @Override
    public String toString() {
        return colName + " " + type + "(" + size + ")";
    }
}
