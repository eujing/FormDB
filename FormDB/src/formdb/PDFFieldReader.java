/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formdb;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import java.io.IOException;
import java.util.Set;

/**
 *
 * @author Francis
 */
public class PDFFieldReader {
    
    DataField[] dataFields;
    
    public PDFFieldReader (String path) {
        try {
            PdfReader reader = new PdfReader(path);
            AcroFields fields = reader.getAcroFields();
            reader.close();
            
            Set<String> fieldNames = fields.getFields().keySet();
            int N = fieldNames.size();
            dataFields = new DataField[N];
            int i = 0;
            for ( String s : fieldNames ) {
                dataFields[i] = new DataField (s,fields.getField(s),fields.getFieldType(s)==AcroFields.FIELD_TYPE_CHECKBOX);
                i++;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public DataField[] getDataFields () {
        return dataFields;
    }
    
}
