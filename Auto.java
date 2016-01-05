public class Auto(){

  final int FEETTOCOUNT = 0; //TODO: Measure 1 foot counts
  final int DEGREETOCOUNT = 0; //TODO: Measure 90 degree turn counts then / 90
  
 /**
 * Converts measured feet to encoder counts. Used when creating distances the robot can understand.
 *
 * @param feet
 * @return the counts equivalent to number of feet
 */
  private static int feetToCount(double feet){
    return feet * FEETTOCOUNT;
  }
  
  /**
   * Converts measured degrees to encoder counts.
   * @param degrees
   * @return the counts it will take to turn the given number of degrees
   */
  
  private static int degreeToCount(int degrees){
    return degrees * DEGREETOCOUNT;
  }
  
 /**
 *Moves the robot forward a designated distance
 *
 *@param feet
 */
  
  public void forward(int feet){
    int distance = feetToCount(feet);
    while(enc.get() <= distance){
      //TODO: Define new talons with same ports?
    }
  }

 /**
 * Turns the robot left or right a designated amount of degrees.
 *
 *@param direction The robot will turn right when true and left when false
 *@param degrees
 */
  public void turn(boolean direction, int degrees){
    int counts =
    if(direction){
      
    }else{
      
    }
  }

}//class
