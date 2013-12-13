package rogel.io.ai.ann.activationfunctions;

import java.util.List;

/**
 * A <tt>BinaryStepFunction</tt> is an <tt>ActivationFunction</tt>, with two
 * possible output values (for example <tt>A0</tt>, and <tt>A1</tt>), and a
 * threshold value.  
 * 
 * If the sum of the inputs is greater than the threshold, then one value
 * (say <tt>A0</tt>) is output. Otherwise, the other value (<tt>A1</tt>) is.
 * 
 * @author recardona
 * @see http://en.wikibooks.org/wiki/Artificial_Neural_Networks/Activation_Functions#Step_Function
 */
public class BinaryStepFunction implements ActivationFunction 
{
	private double valueIfLessThanThreshold;
	private double valueIfGreaterThanThreshold;
	private double threshold;
	
	/**
	 * Creates a <tt>BinaryStepFunction</tt>, which returns <tt>valueIfGreater</tt> if given a list
	 * of inputs that sum to more than the <tt>threshold</tt>, and <tt>valueIfLess</tt> otherwise.
	 * @param valueIfGreater the value to return if inputs sum to more than the <tt>threshold</tt>
	 * @param valueIfLess the value to return if inputs sum to less than the <tt>threshold</tt>
	 * @param threshold the threshold value of this function
	 */
	public BinaryStepFunction(double valueIfLess, double valueIfGreater, double threshold) {
		this.threshold = threshold;
		this.valueIfGreaterThanThreshold = valueIfGreater;
		this.valueIfLessThanThreshold = valueIfLess;
	}
	
	@Override
	public double compute(List<Double> input) {
		double sum = 0.0;
		
		for(Double value : input) {
			sum += value.doubleValue();
		}
		
		return sum > this.threshold ? this.valueIfGreaterThanThreshold : this.valueIfLessThanThreshold;
	}
	
	/**
	 * @return the threshold
	 */
	public double getThreshold() {
		return threshold;
	}

}
