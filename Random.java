/**
 * A class to return random numbers (int and double) within a user specified range. <br> 
 */
public class Random
{
   /** The object that will generate the random numbers.  Only one random number generator is ever needed. */
   private final static java.util.Random rand = new java.util.Random();

   /** 
    *  Returns a randomly generated integer between low and high (inclusive of both). 
    */
   public static int randomInt(int low, int high)
   {
      //rand.nextInt(num) returns a number between 0 (inclusive) and num - 1 (inclusive)
      //range initially is 0 to high - low (inclusive)
      //to correct the range, add low to the result to get low to high (inclusive of both)
      return rand.nextInt(high - low + 1) + low;
   }

   /** 
    *  Returns a randomly generated floating point number between low (inclusive) and high (exclusive).
    */
   public static double randomFloat(int low, int high)
   {
      //rand.nextDouble gives a number between 0.0 (inclusive) and 1.0 (exclusive)
      //randomFloat then gives a number between low (inclusive) and high (exclusive)
      return rand.nextDouble()*(high - low) + low;  
   }
}
