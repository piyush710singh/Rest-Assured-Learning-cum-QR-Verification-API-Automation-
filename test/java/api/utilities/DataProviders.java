package api.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    // This data provider returns all rows and columns from the "Users" sheet as a 2D array.
    @DataProvider(name = "Data")
    public String[][] getAllData() throws IOException{
        String path = "C:\\Users\\piyus\\IdeaProjects\\RestAssuredLearning\\src\\test\\resources\\Demo_Users.xlsx"; // Full Excel file path.
        XLUtility xl = new XLUtility(path); // Create helper object to read data from Excel.

        int rownum = xl.getRowCount("Users"); // Get the last row index from the sheet.
        int colcount = xl.getCellCount("Users", rownum ); // Get the number of cells in the last row.

        String apidata[][] = new String[rownum][colcount]; // Store sheet data in a 2D array.

        for(int i=1; i<=rownum;i++){ // Start from row 1 to skip the header row at index 0.
            for(int j=0; j<colcount;j++){ // Start from column 0 because columns are zero-indexed in POI.
                apidata[i-1][j] = xl.getCellData("Users", i, j); // Fill array row-by-row using Excel row data.
            }
        }
        return apidata; // Return all test data to TestNG.
    }

    // This data provider returns only the usernames from column 1.
    @DataProvider(name="UserNames")
    public String[] getUsernames() throws IOException{
        String path = "C:\\Users\\piyus\\IdeaProjects\\RestAssuredLearning\\src\\test\\resources\\Demo_Users.xlsx"; // Full Excel file path.
        XLUtility xl = new XLUtility(path); // Create helper object to read data from Excel.
        int rownum = xl.getRowCount("Users"); // Get the last row index from the sheet.
        String apidata[] = new String[rownum]; // Store one value from each data row.
        for(int i=1; i<=rownum;i++){ // Start from row 1 to skip the header row at index 0.
            apidata[i-1] =xl.getCellData("Users", i, 1); // Read column 1, which contains the username values.
        }
        return apidata; // Return the username list to TestNG.
    }
}
