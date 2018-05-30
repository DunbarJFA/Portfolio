/**
 * An extension of class ComplexNumber that creates a Real Number
 *
 * Contains unique methods for comparing values of two real numbers (greaterThan() and lessThan())
 *
 * @author Jerod Dunbar
 * @version 30 January 2017
 */
public class RealNumber extends ComplexNumber{
    /**
     * Subclass of ComplexNumber where the imaginary variable "b" is always 0.
     *
     * @param a The real part
     */
	public RealNumber(final float a){
		super(a,0);
	}

    /**
     * Takes in another RealNumber and compares it to the value of this real number to see if this is greater
     *
     * @param other The other real number
     * @return The boolean result of whether this is greater than other
     */
	public boolean greaterThan(RealNumber other){
	    if (this.a > other.a){
	        return true;
        }
        else if (!(this.a > other.a)){
	        return false;
        }
        else {
            return false;
        }
    }

    /**
     * Takes in another RealNumber and compares it to the value of this real number to see if this is lesser
     *
     * @param other The other real number
     * @return The boolean result of whether this is less than other
     */
    public boolean lessThan(RealNumber other){
	    if (this.a < other.a){
            return true;
        }
        else if (!(this.a < other.a)){
	        return false;
        }
        else {
            return false;
        }
    }
}//end of class RealNumber