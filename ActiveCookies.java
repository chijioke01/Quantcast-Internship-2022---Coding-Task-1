
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ActiveCookies {
	
    public static void main(String[] args) {
    	commandLineLength(args.length);
        String dir = args[0];
        String fileName = args[1];
        String timeZone = args[2];
        String dateStamp = args[3];
        commandLineTesting(timeZone,dateStamp);
        fileTest(fileName);
        LinkedHashMap<String, Integer> cookies = new LinkedHashMap<String, Integer>();
        fileRead(fileName, dateStamp, cookies);
        ActiveCheck(cookies);
    }
    
    /* 
    fileRead function loops through the text file 
    and adds the name and frequency into a hash map
    */
    public static LinkedHashMap<String, Integer> fileRead(String fileName, String dateStamp,
            LinkedHashMap<String, Integer> cookies) {
        try {
            File file = new File(fileName);
            Scanner read = new Scanner(file);

            String data = read.nextLine(); // skips to 2nd line where data starts

            while (read.hasNextLine()) {
                data = read.nextLine();
                int comma = data.indexOf(",");
                String cookieName = data.substring(0, comma);
                String cookieDate = data.substring(comma + 1, comma + 11);
                if (cookieDate.equals(dateStamp)) {
                    if (cookies.containsKey(cookieName)) {
                        cookies.put(cookieName, cookies.get(cookieName) + 1);
                    } else {
                        cookies.put(cookieName, 1);
                    }
                }
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        return cookies;
    }

    /* 
    Activecheck function Loops through the cookies HashMap to find 
    which cookies were the most frequent and prints them out
    */
    public static void ActiveCheck(LinkedHashMap<String, Integer> cookies) {
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : cookies.entrySet()) {
            int frequency = entry.getValue();
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }
        for (Map.Entry<String, Integer> entry : cookies.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                System.out.println(entry.getKey());
            }
        }
    }
    
    @Test 
    // Test checks number of command line arguments
    public static void commandLineLength(int argsCount) {
    	Assertions.assertEquals(argsCount, 4);
    }
    
    @Test
    // commnadLineTesting function checks if command line arguments are properly written
    public static void commandLineTesting(String timeZone, String dateStamp) {
    	Assertions.assertEquals(timeZone, "-d");
		Assertions.assertEquals(dateStamp.length(), 10);
    }
    
    @Test 
    // Test checks if the file contains cookie data
    public static void fileTest(String fileName) {
    	int count = 0;
        try {
            File file = new File(fileName);
            Scanner read = new Scanner(file);
            String data;
            while (read.hasNextLine()) {
                data = read.nextLine();
                count++;
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    	Assertions.assertTrue(count >= 2);
    }
}
