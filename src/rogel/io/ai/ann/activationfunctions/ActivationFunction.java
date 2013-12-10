package rogel.io.ai.ann.activationfunctions;

import java.util.List;

/**
 * From Wikipedia: In computational networks, the activation function of a node
 * defines the output of that node given an input or set of inputs.
 * @author recardona
 * @see http://en.wikipedia.org/wiki/Activation_function
 */
public interface ActivationFunction 
{
	/**
	 * Computes an output value given a list of input values.
	 * @param input the inputs to the function
	 * @return a value which represents this function's output
	 */
	public double compute(List<Double> input);
}
