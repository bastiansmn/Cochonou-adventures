package modele.outils.csv;

import java.io.*;

public class CSVObject {
   
   private String[][] array;
   private String separor;

   private String csvFile;

   /**
    * Initialize array as the corresponding String[][] array of the csv file
    * @param csvFile    Path to the csv file
    *
    *  It will use ',' as separtor
    */
   public CSVObject(String csvFile) {
      this(csvFile, ",");
   }


   /**
    * Initialize array as the corresponding String[][] array of the csv file
    * @param csvFile       Path to the csv file
    * @param csvSplitBy    Special separtor to use
    */
   public CSVObject(String csvFile, String csvSplitBy) {
      this.csvFile = csvFile;
      String line;
      this.separor = csvSplitBy;

      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
         int i = 0;
         while (br.readLine() != null) {
            i++;
         }

         this.array = new String[i][];

         i = 0;
         BufferedReader buffered = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)));
         while ((line = buffered.readLine()) != null) {
            String[] inLine = line.split(separor);
            this.array[i] = inLine;
            i++;
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }


   /**
    * Create a new CSV Object from a String[][]
    * @param s    The String[][] to convert to CSVObject
    */
   public CSVObject(String[][] s) {
      this.array = s;
   }


   /**
    * @return  the String at the position i, j
    */
   public String get(int i, int j) {
      return this.array[i][j];
   }


   public String getFile() {
      return csvFile;
   }

   /**
    * Get the longest word in the whole csv file
    * @return     the longuest word.
    */
   public String getLongestWord() {
      int max = array[0][0].length();
      String res = array[0][0];

      for (String[] sTab : array) {
         for (String s : sTab) {
            if (s.length() > max) {
               max = s.length();
               res = s;
            }
         }
      }
      return res;
   }


   /**
    * Print the csv file proprely
    * @param withSeparator   true make the separator prints, false only prints words.
    */
   public void print(boolean withSeparator) {
      int repeat = this.getLongestWord().length();
      for (String[] sTab : array) {
         for (String s : sTab) {
            System.out.print(s + " ".repeat(repeat - s.length() + 1) +  (withSeparator ? separor + " " : ""));
         }
         System.out.println();
      }
   }

   /**
    * Print the file with the separator.
    */
   public void print() {
      print(true);
   }

   /**
    * Get the position of a word
    * @param s    The word to find
    * @return     null is the word wasn't found.
    */
   public int[] getIndex(String s) {
      for (int i = 0; i < array.length; i++) {
         for (int j = 0; j < array[i].length; j++) {
            if (array[i][j].equals(s)) {
               return new int[] {i, j};
            }
         }
      }
      return null;
   }


   /**
    * Search for a word in the csv file
    * @param s    The word to find
    * @return     If the word is in the file
    */
   public boolean contains(String s) {
      for (String[] sTab : array) {
         for (String string : sTab) {
            if (string.equals(s)) {
               return true;
            }
         }
      }
      return false;
   }

   public void setWord(int[] position, String s) {
      this.array[position[0]][position[1]] = s;

   }


   /**
    * @return a copy of the array
    */
   public String[][] getArray() {
      String[][] res = new String[this.array.length][];

      for (int i = 0; i < array.length; i++) {
         res[i] = new String[array[i].length];
         for (int j = 0; j < array[i].length; j++) {
            res[i][j] = array[i][j];
         }
      }

      return res;
   }
}