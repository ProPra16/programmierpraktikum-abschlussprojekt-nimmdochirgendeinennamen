
package tracker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;

public class Tracker {

    public LocalTime time;
    
    String oldCode;
    String oldTest;

    public Tracker(String code, String test) {
        time = LocalTime.now();
        oldCode = code;
        oldTest = test;
    }

    public void dump(String code, String test) {
        String output = getDiff(code, test, oldCode, oldTest);
        time = LocalTime.now();
        
        try (BufferedWriter out = new BufferedWriter(new FileWriter("TrackerData.txt", true))) {
        	out.write(time.toString() + "\n" + output + "\n\n");
        } catch (IOException e) {}
        
        oldCode = code;
        oldTest = test;
    }

    private String getDiff(String code, String test, String oldCode, String oldTest) {
    	
    	boolean codeChanged = true;
    	boolean testChanged = true;
    
    	if (code.equals(oldCode)) codeChanged = false;
    	if (test.equals(oldTest)) testChanged = false;
	
    	String diffCode;
    	String diffTest;
    	
    	String testOut = "";
    	String codeOut = "";

    	if (codeChanged) {
    		diffCode = StringUtils.difference(oldCode, code);
    		codeOut = "Changed Code:\n" + diffCode;
    	}
    	
    	if (testChanged) {
		diffTest = StringUtils.difference(oldTest, test);
		testOut = "Changed Test:\n" + diffTest;
    	}
	
    	String endResult;
	
    	if(codeChanged && testChanged) endResult = codeOut + "\n\n" + testOut;
    	else endResult = codeOut + testOut;
	
    return endResult;   
    }
}	
