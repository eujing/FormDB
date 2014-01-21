/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffieldreader;

/**
 *
 * @author Francis
 */
public class DataField {
    
    String name;
    String value;
    boolean isBool;
    
    public DataField ( String name, String value, boolean isBool ) {
        this.name = name;
        this.value = value;
        this.isBool = isBool;
    }
    
    public String getFieldName() {
        return name;
    }
    
    public String getFieldValue() {
        return value;
    }
    
    public Boolean isBoolean() {
        return isBool;
    }
    
}
