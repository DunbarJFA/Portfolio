/**
 *A program for creating a Complex Number.
 *
 * Methods include getters (for real and imaginary parts), an overridden equals method, an overridden toString, addition,
 * subtraction, multiplication, and division.
 *
 *@author Jerod Dunbar
 *@version 30 January 2017
 */
public class ComplexNumber{
	/** The real part of the complex number.*/
    protected float a;
    /** The imaginary part of the complex number.*/
	protected float b;

    /**
     * Returns the value of the real part of a given complex number.
     *
     * @return the real part "float a"
     */
	public float getA(){
		return this.a;
	}

    /**
     * Returns the value of the imaginary part of a given complex number.
     *
     * @return the imaginary part "float b"
     */
	public float getB(){
		return this.b;
	}

    /**
     * Constructs a new Complex Number with the given real and imaginary parts.
     *
     * @param a The real part
     * @param b The imaginary part
     */
	public ComplexNumber(final float a, final float b){
		this.a = a;
		this.b = b;
	}

    /**
     * Takes a separate complex number and, after verification of non-null and instanceof ComplexNumber,
     * tests for real and imaginary parts that match with the constructed complex number.
     *
     * @param other The other complex number
     * @return The boolean result of the comparison of this and other
     */
	@Override
	public boolean equals(Object other){
		if (!(other instanceof ComplexNumber)){
			return false;
		}
		else if (other == null){
			return false;
		}
		else {
			ComplexNumber num = (ComplexNumber)other;
			return ((num.a == this.a) && (num.b == this.b));
		}
	}

    /**
     * Returns the string literal representation of a complex number.
     *
     * @param num The complex number being represented
     * @return The value of num.a "+" the value of num.b and "i."
     */
	@Override
	public String toString(){

		return ("this.a \"+\" this.b \"i.\"");
	}

    /**
     * Returns the result of this Complex Number added to another.
     *
     * @param other The complex number to be added.
     * @return The sum of the two complex numbers (result also a complex number)
     */
	public ComplexNumber add(ComplexNumber other){
		float newA = this.a + other.a;
		float newB = this.b + other.b;
		
		return new ComplexNumber(newA,newB);
	}

    /**
     * Returns the result of another complex number subtracted from this complex number.
     * @param other The complex number to be subtracted
     * @return The result of this - other
     */
	public ComplexNumber subtract(ComplexNumber other){
		float newA = this.a - other.a;
		float newB = this.b - other.b;
		
		return new ComplexNumber(newA,newB);
	}

    /**
     * Returns the result of the multiplication of this complex number and another.
     *
     * @param other The other complex number to be multiplied.
     * @return The result of this * other
     */
	public ComplexNumber multiply(ComplexNumber other){
		float newA = (this.a * other.a) - (this.b * other.b);
		float newB = (this.b * other.a) + (this.a * other.b);
		
		return new ComplexNumber(newA,newB);
	}

    /**
     * Returns the quotient of this complex number divided by the other complex number.
     *
     * @throws ArithmeticException if denominator is 0.
     * @param other The complex number to be divided by
     * @return The result of this/other
     */
	public ComplexNumber divide(ComplexNumber other){
		if ((other.a * other.a) + (other.b * other.b) == 0){
			throw new ArithmeticException("Cannot divide by zero.");
		}
		
		float newA = ((this.a * other.a) + (this.b * other.b)) / (other.a * other.a) + (other.b * other.b);
		float newB = ((this.b * other.a) - (this.a * other.b)) / (other.a * other.a) + (other.b * other.b);
		
		return new ComplexNumber(newA,newB);
		
		
	}
	
}//end of class ComplexNumber