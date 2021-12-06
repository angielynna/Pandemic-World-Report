/**
  * TCSS 143
  * Instructor: Raghavi Sakpal
  * PA3 - PandemicDashboard.java
  */

import java.io.*;
import java.util.*;

/**
  * Tester file to read from the dataset and report results
  * @author Angelynna Pyeatt
  * @version 26 November 2021
  */
public class PandemicDashboard {
   /**
     * main method throws FileNotFoundException and plays
     * through the dashboard while 8 is not inputted from user
     *
     * @param String[] theArgs
     */
   public static void main(String[] theArgs) throws FileNotFoundException{
      List<CovidCase> casesList = new 
        ArrayList<CovidCase>(fillCovidCaseList("owid-covid-data.csv"));
      boolean game = true; //game is on
      
      while(game) {
         displayDashboard();
         Scanner console = new Scanner(System.in);
         int input = console.nextInt();
         if(input == 8) { //exits program
            System.out.println("You've chosen to exit my program.");
            System.out.println("Goodbye.");
            game = false;
         } else {
            if(input == 1) { //Covid Cases by Countries
               Collections.sort(casesList);
               displayCovidCases(casesList);
               
            } else if (input == 2) { //by total Deaths
               Collections.sort(casesList, new CompareDeaths());
               displayCovidCases(casesList);
               
            } else if (input == 3) { //by total cases
               Collections.sort(casesList, new CompareCases());
               displayCovidCases(casesList);
               
            } else if (input == 4) { //min and max of total deaths
               Collections.sort(casesList, new CompareDeaths());
               displayMinMaxDeath(casesList);
               
            } else if (input == 5) { //min and max of total cases
               Collections.sort(casesList, new CompareCases());
               displayMinMaxCases(casesList);
               
            } else if (input == 6) { //search for a country and report 
                                     //mortality rate
               System.out.println("Enter a country to search for: ");
               String searchFor = console.next();
               
               //sort the list before doing binary search:
               Collections.sort(casesList);
               
               //use binary search to find where the country is located:
               int i = Collections.binarySearch(casesList, 
                                   new CovidCase(searchFor, 0.0, 0.0));
               if(i >= 0) {  //if the index of the country was 
                                 //found in the list
                  System.out.println("Covid Case Data:");
                  System.out.println("Country " + searchFor);
                  System.out.println("Total cases: " + 
                                     casesList.get(i).getTotalCases());
                  System.out.println("Total deaths: " + 
                                     casesList.get(i).getTotalDeaths());
                  System.out.format("\nMortality Rate: %.2f%c \n", 
                                     casesList.get(i).getDeathToCaseRatio(), 
                                     '%');
               } else {
                  System.out.println("The country you inputted can't be found.");
               }
               
            } else if (input == 7) { //top 10 country mortality rate
               Collections.sort(casesList, new CompareMortality());
               System.out.format("%-30S%-30S%-30S%S%n","COUNTRY", 
                         "TOTAL CASES", "TOTAL DEATHS", "MORTALITY");
               System.out.println("--------------------------------" +
                         "-----------------------------------------" +
                         "--------------------------");
               displayMortalityRate(casesList, 0);
            }
         }
      }
   }
   /** 
     * Method: To read data from the file and create an instance of 
     * CovidCase and add to a List and throws FileNotFoundException
     * Parameter: String fileName 
     * @return: List<CovidCase> caseList 
     */ 
   public static List<CovidCase> fillCovidCaseList(String fileName) 
                                       throws FileNotFoundException {
      List<CovidCase> caseList = new ArrayList<CovidCase>();
      Scanner sc = new Scanner(new File(fileName));
      sc.nextLine();
      String date = "2021-11-04";
      while(sc.hasNextLine()) {
         String line = sc.nextLine();
         String[] data = line.split(",");
         double cases = 0.0;
         double deaths = 0.0;
         if(data[3].equals(date)) {
            String country = data[2];
            if(!(country.equalsIgnoreCase("World")) && 
               !(country.equalsIgnoreCase("Asia")) &&
               !(country.equalsIgnoreCase("Upper Middle Income")) &&
               !(country.equalsIgnoreCase("Lower Middle Income")) &&
               !(country.equalsIgnoreCase("Europe")) &&
               !(country.equalsIgnoreCase("South America")) &&
               !(country.equalsIgnoreCase("North America")) &&
               !(country.equalsIgnoreCase("European Union")) &&
               !(country.equalsIgnoreCase("High Income"))) {
               
               if(!data[4].isEmpty()){ 
                  cases = Double.parseDouble(data[4]);
               }
               if(!data[7].isEmpty()){
                  deaths = Double.parseDouble(data[7]);
               }
               caseList.add(new CovidCase(country, cases, deaths));
            }
         }
      }
      return caseList;   
   }
   
   /**
     * Method: To display the Covid Cases in sorted order based 
     * on either – Total Cases, Total Deaths & Mortality Ratio. 
     * Display Country, Total Cases & Total Deaths 
     *
     * @param: List<CovidCase> caseList (you are passing a sorted list) 
     * @return: void  
     */ 
   public static void displayCovidCases(List<CovidCase> caseList){
      System.out.format("%-40S%-30S%-20S%n","COUNTRY",
                        "TOTAL CASES", "TOTAL DEATHS");
      System.out.println("-----------------------------------------" + 
                        "-------------------------------------------");
                         
      for(int i = 0; i < caseList.size(); i++) {
         System.out.format("%-40S%-30S%-20S\n", caseList.get(i).getCountry(),
                           caseList.get(i).getTotalCases(), 
                           caseList.get(i).getTotalDeaths());
      }
   } 
   
   /**
     * Method: To display the dashboard options to the user.  
     * @param: None 
     * @return: None 
     */ 
   public static void displayDashboard(){
      System.out.println("Dashboard for reporting Covid Cases\n" +
                         "-----------------------------------\n" +
                         "Select one of the following options:\n" +
                         "1.Report (Display) Covid Cases by " +
                         "Countries.\n2.Report (Display) Covid Cases"+
                         " by Total Deaths (decreasing order).\n3." +
                         "Report (Display) Covid Cases by Total Cases"+
                         " (decreasing order).\n4.Report Countries " +
                         "with minimum and maximum number of Total " +
                         "Deaths.\n5.Report Countries with minimum " +
                         "and maximum number of Total Cases.\n6." +
                         "Search for a Country and report their " +
                         "Mortality Rate (death-to-case %).\n7.Report"+
                         " (Display) top 10 countries by their " +
                         "Mortality Rate (decreasing order).\n8.Exit" +
                         " from the program!");
   }
   
   /**
     * displays the minimum and maximum number of cases based off 
     * of the total cases
     *
     * @param List<CovidCase> caseList
     */
    public static void displayMinMaxCases(List<CovidCase> caseList) {
      System.out.println("Countries with Minimum Cases: ");
      double m = caseList.get(caseList.size() - 1).getTotalCases();
      double min = findMin(caseList, caseList.size(), m, 'C');
      printMin(caseList, caseList.size(), min, 'C');
      
      System.out.println("Countries with Maximum Cases: ");
      double max = findMax(caseList, caseList.size(), m, 'C');
      printMax(caseList, caseList.size(), max, 'C');
   }
   
   /**
     * displays the minimum and maximum based off of the number of 
     * total deaths
     *
     * @param List<CovidCase> caseList
     */ 
   public static void displayMinMaxDeath(List<CovidCase> caseList) {
      System.out.println("Countries with Minimum Deaths: ");
      double m = caseList.get(caseList.size() - 1).getTotalDeaths();
      double min = findMin(caseList, caseList.size(), m, 'D');
      printMin(caseList, caseList.size(), min, 'D');
      
      System.out.println("Countries with Maximum Deaths: ");
      double max = findMax(caseList, caseList.size(), m, 'D');
      printMax(caseList, caseList.size(), max, 'D');
   }
   
   
   /**
     * private helper method that recursively finds the minimum 
     * number of cases or deaths depending on the character sent
     *
     * @param * @param List<CovidCase> caseList
     * @param int index: starts at the end of the list and decrements
     *                   with recursion (First base/recursion case)
     * @param double min which is constantly updated
     * @param char c: will be either 'C' for total cases or 'D' for 
     *                  total deaths
     * @return double min
     */
   private static double findMin(List<CovidCase> caseList, 
                                 int index, double min, char c) {
      if(c == 'C') { //finds min number of cases
         if(index < 1) {
            return min;
         } else { 
            if(caseList.get(index - 1).getTotalCases() < min) {
               min = caseList.get(index - 1).getTotalCases();
            }
            return findMin(caseList, index - 1, min, 'C');
         }
      } else { //char is 'D'
      //finds min number of deaths
         if(index < 1) {
            return min;
         } else { 
            if(caseList.get(index - 1).getTotalDeaths() < min) {
               min = caseList.get(index - 1).getTotalDeaths();
            }
            return findMin(caseList, index - 1, min, 'D');
         }
      }

   }
   
   /**
     * private helper method that recursively finds the maximum 
     * number of cases or deaths depending on the character sent
     *
     * @param List<CovidCase> caseList
     * @param int index: starts at the end of the list and decrements
     *                   with recursion (First base/recursion case)
     * @param double max which is constantly updated
     * @param char c: will be either 'C' for total cases or 'D' for 
     *                  total deaths
     * @return double max
     */
   private static double findMax(List<CovidCase> caseList, 
                                 int index, double max, char c) {
      if(c == 'C') { //finds max number of cases
         if(index < 1) {
            return max;
         } else { 
            if(caseList.get(index - 1).getTotalCases() > max) {
               max = caseList.get(index - 1).getTotalCases();
            }
            return findMax(caseList, index - 1, max, 'C');
         }
      } else { //char is 'D'
      //finds max number of deaths
         if(index < 1) {
            return max;
         } else { 
            if(caseList.get(index - 1).getTotalDeaths() > max) {
               max = caseList.get(index - 1).getTotalDeaths();
            }
         return findMax(caseList, index - 1, max, 'D');
         }
      }
   }

   /**
     * private helper method that uses nested recursion iterate through 
     * the list to display the countries with the min number of cases
     * or deaths depending on the character sent
     *
     * @param List<CovidCase> caseList
     * @param int index: starts at the end of the list and decrements
     *                   with recursion (First base/recursion case)
     * @param char c: will be either 'C' for total cases or 'D' for 
     *                  total deaths
     * @param double min: minimum number of cases
     */
   private static void printMin(List<CovidCase> caseList, 
                               int index, double min, char c){
      if(c == 'C'){ 
         if(index < 1) { //base case
         //indicates the list has been iterated all the way through
         
            System.out.println();
            
         } else { //recursive case
            if(caseList.get(index - 1).getTotalCases() == min) {
            //base case (2)
                              //minimum number of total cases
               System.out.format("Country: %s\nTotalCases: %s\n" + 
                                 "TotalDeaths: %s\n\n", 
                                 caseList.get(index - 1).getCountry(),
                                 caseList.get(index - 1).getTotalCases(),
                                 caseList.get(index - 1).getTotalDeaths());
            }
            printMin(caseList, index - 1, min, 'C');
         }
      } else { //char is 'D'
         if(index < 1) { //base case
         //indicates the list has been iterated all the way through
         
            System.out.println();
            
         } else { //recursive case
            if(caseList.get(index - 1).getTotalDeaths() == min) { 
            //base case (2)
                            //minimum number of total deaths
               System.out.format("Country: %s\nTotalCases: %s\n" + 
                                 "TotalDeaths: %s\n\n", 
                                 caseList.get(index - 1).getCountry(),
                                 caseList.get(index - 1).getTotalCases(),
                                 caseList.get(index - 1).getTotalDeaths());
            }
            printMin(caseList, index - 1, min, 'D');
         }
      }

   }
   /**
     * private helper method that uses nested recursion iterate through 
     * the list to display the countries with the max number of cases
     * or deaths depending on the character sent
     *
     * @param List<CovidCase> caseList
     * @param int index: starts at the end of the list and decrements
     *                   with recursion (First base/recursion case)
     * @param double max: maximum number of cases or deaths
     * @param char c: will be either 'C' for total cases or 'D' for 
     *                  total deaths
     */
   private static void printMax(List<CovidCase> caseList, 
                                int index, double max, char c){
      if(c == 'C') {
         if(index < 1) { //base case
          //indicates the list has been iterated all the way through
          
            System.out.println();
            
         } else { //recursive case
            if(caseList.get(index - 1).getTotalCases() == max) { 
            //base case (2)
                              //maximum number of total cases
               System.out.format("Country: %s\nTotalCases: %s\n" + 
                                 "TotalDeaths: %s\n\n", 
                                 caseList.get(index - 1).getCountry(),
                                 caseList.get(index - 1).getTotalCases(),
                                 caseList.get(index - 1).getTotalDeaths());
            }
            printMax(caseList, index - 1, max, 'C');
         }
      } else { //char is 'D'
         if(index < 1) { //base case
         //indicates the list has been iterated all the way through
         
            System.out.println();
         } else { //recursive case
            if(caseList.get(index - 1).getTotalDeaths() == max) { //base case
                            //maximum number of total deaths
               System.out.format("Country: %s\nTotalCases: %s\n" + 
                                 "TotalDeaths: %s\n\n", 
                                  caseList.get(index - 1).getCountry(),
                                 caseList.get(index - 1).getTotalCases(),
                                 caseList.get(index - 1).getTotalDeaths());
            }
            printMax(caseList, index - 1, max, 'D');
         }
      }

   }
   /**
     * private helper method that recursively displays the information about 
     * the countries with the top ten mortality rates
     *
     * @param List<CovidCase> caseList
     * @param index: starts at 0 and decrements with recursion
     */
   private static void displayMortalityRate(List<CovidCase> caseList, 
                                             int index){
      if(index > 9) {
         System.out.println();
      } else {
         System.out.format("%-30S%-30S%-30S%.2f%c%n", 
                           caseList.get(index).getCountry(), 
                           caseList.get(index).getTotalCases(),
                           caseList.get(index).getTotalDeaths(),
                           caseList.get(index).getDeathToCaseRatio(),
                           '%');
         displayMortalityRate(caseList, index + 1);
      }
   }
}
