/**
  * TCSS 143
  * Instructor: Raghavi Sakpal
  * PA3 - CovidCase.java
  */

import java.io.*;
import java.util.*;

/**
  * CovidCase is an object class that implements Comparable
  * @author Angelynna Pyeatt
  * @version 26 November 2021
  */
 
public class CovidCase implements Comparable<CovidCase> {

   /**String value representing the country*/
   private String country;
   
   /**double value holding the number of cases*/
   private double totalCases;
   
   /**double value holding the total deaths*/
   private double totalDeaths;
   
   /**double value holding the death to case ratio*/
   private double deathToCaseRatio;
   
   /**
     * default constructor instantiates private variables
     *
     * @param String country: the current country
     * @param double totalCases: the country's total cases
     * @param double totalDeaths: the country's total deaths
     */
   public CovidCase(String country, double totalCases, double totalDeaths) {
      this.country = country;
      this.totalCases = totalCases;
      this.totalDeaths = totalDeaths;
      if(totalCases == 0 || totalDeaths == 0) {
         deathToCaseRatio = 0.0;
      } else {
         deathToCaseRatio = (totalDeaths / totalCases)  * 100;
      }
   }
   
   /**
     * gets/returns country
     * 
     * @return String country
     */
   public String getCountry() {
      return this.country;
   }
   
   /**
     * gets/returns total cases
     * 
     * @return double totalCases
     */
   public double getTotalCases() {
      return this.totalCases;
   }
   
   /**
     * gets/returns totalDeaths
     * 
     * @return double totalDeaths
     */
   public double getTotalDeaths() {
      return this.totalDeaths;
   }
   
   /**
     * gets/returns death to case ratio
     *
     * @return double deathToCaseRatio
     */
   public double getDeathToCaseRatio() {
      return this.deathToCaseRatio;
   }
   
   /**
     * compareTo based on name of country (alphabetically)
     *
     * @param CovidCase cc
     * @return integer variable comparing the current CovidCase object
     *            with the one sent in the parameter
     */
   public int compareTo(CovidCase cc) {
      return this.getCountry().compareTo(cc.getCountry());
   }
}


/**
  * CovidDeathsCompare compares the number of deaths in each country
  * and implements Comparable<CovidCase>
  * @author Angelynna Pyeatt
  * @version 26 November 2021
  */
class CompareDeaths implements Comparator<CovidCase>{
   /**
     * compares the number of deaths in each country
     *
     * @param CovidCase cc to compare to the other covid case
     * @param CovidCase otherCC to compare to first parameter
     * @return int value sorting the countries in decreasing order based
     *          off of the number of deaths
     */
    public int compare(CovidCase cc, CovidCase otherCC) {
      return Double.compare(otherCC.getTotalDeaths(), 
                              cc.getTotalDeaths());
   }
}

 /**
   * CompareCases compares the number of covid cases in each
   * country and implements Comparable<CovidCase>
   * @author Angelynna Pyeatt
   * @version 26 November 2021
   */
class CompareCases implements Comparator<CovidCase>{
   /**
     * compares the number of covid cases in each country
     *
     * @param CovidCase cc to compare to the other covid case
     * @param CovidCase otherCC to compare to first parameter
     * @return int value sorting the countries in decreasing order
     *          based off of the number of cases
     */
    public int compare(CovidCase cc, CovidCase otherCC) {
      return Double.compare(otherCC.getTotalCases(), 
                              cc.getTotalCases());
   }
}
/**
  * CompareMortality compares the country's mortality rate
  * and implements Comparable<CovidCase>
  * @author Angelynna Pyeatt
  * @version 26 November 2021
  */
class CompareMortality implements Comparator<CovidCase> {
    /**
     * compares death to rate ratio in each country
     *
     * @param CovidCase cc to compare to the other covid case
     * @param CovidCase otherCC to compare to first parameter
     * @return int value sorting the countries in decreasing order
     *          based off of the number of cases
     */
   public int compare(CovidCase cc, CovidCase otherCC) {
      return Double.compare(otherCC.getDeathToCaseRatio(), 
                              cc.getDeathToCaseRatio());
   }
}
