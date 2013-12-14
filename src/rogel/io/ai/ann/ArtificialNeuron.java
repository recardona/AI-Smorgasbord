package rogel.io.ai.ann;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import rogel.io.ai.ann.activationfunctions.ActivationFunction;
import rogel.io.ai.ann.activationfunctions.BinaryStepFunction;

/**
 * From Wikipedia: An artificial neuron is a mathematical function conceived as an abstraction of
 * a biological neuron. The artificial neuron receives one or more inputs (representing the one or
 * more dendrites) and sums them to produce an output (representing a biological neuron's axon).
 * @author recardona
 * @see http://en.wikipedia.org/wiki/Artificial_neuron
 */
public class ArtificialNeuron {

	private List<Double> inputWeights;
	private ActivationFunction activationFunction;
	private double bias;
	
	/**
	 * Feeds the list of inputs to this Neuron, and returns the activation level, given its
	 * internal weight structure, as well as its activationFunction. 
	 * @param inputs the inputs to this <tt>ArtificialNeuron</tt>
	 * @return the output activation level of this <tt>ArtificialNeuron</tt>
	 * @throws IllegalArgumentException if the number of inputs does not match the number of weights 
	 */
	public double feed(List<Double> inputs) {
		
		//verify that the number of inputs match the number weights
		if(inputs.size() != inputWeights.size()) {
			throw new IllegalArgumentException("Number of inputs " +inputs.size()+ " does not match number of weights " +inputWeights.size());
		}
		
		//weigh the inputs accordingly
		List<Double> weightedInputs = new LinkedList<Double>();
		for(int listIdx = 0; listIdx < inputs.size(); listIdx++) {
			double weightedInput = inputs.get(listIdx) * inputWeights.get(listIdx);
			weightedInputs.add(weightedInput);
		}
		
		//add the bias to the inputs - allows us to shift the activation function
		weightedInputs.add(this.bias);
		
		return activationFunction.compute(weightedInputs);
	}
	
	
	
	
	
	/**
	 * @param inputWeights the inputWeights to set
	 * @throws IllegalArgumentException if the inputWeights are null, or empty
	 */
	public void setInputWeights(List<Double> inputWeights) {
		
		if(inputWeights == null) {
			throw new IllegalArgumentException("Weights cannot be null.");
		} else if(inputWeights.size() < 1) {
			throw new IllegalArgumentException("Must have at least one weight.");
		}
		
		this.inputWeights = inputWeights;
	}
	
	
	/**
	 * @return the inputWeights
	 */
	public List<Double> getInputWeights() {
		return inputWeights;
	}





	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ArtificialNeuron: ");
		sb.append("(Weights: "); sb.append(this.inputWeights.toString()); sb.append("), ");
		sb.append("(Bias: "); sb.append(this.bias); sb.append("), ");
		sb.append("(ActivationFunction: "); sb.append(this.activationFunction.toString()); sb.append(")]");
		return sb.toString();
	}



	/**
	 * Private constructor, which should enforce the use of the Builder.
	 * @param b a builder object.
	 */
	private ArtificialNeuron(Builder b) {
		this.inputWeights = b.inputWeights;
		this.activationFunction = b.activationFunction;
		this.bias = b.bias;
	}
	
	/**
	 * A Builder for the <tt>ArtificialNeuron</tt>.
	 * @author recardona
	 */
	public static class Builder {
		private List<Double> inputWeights;
		private ActivationFunction activationFunction;
		private double bias;
		
		/**
		 * Builds the default parameter list for the <tt>ArtificialNeuron</tt>: all weights are
		 * initialized to a value between -1.0 and 1.0, and the bias is set to 1. 
		 * @param numberOfWeights the number of weights for this <tt>ArtificialNeuron</tt>.
		 * @throws IllegalArgumentException if the number of weights is less than 1
		 */
		public Builder(int numberOfWeights) {
			
			if(numberOfWeights < 1) {
				throw new IllegalArgumentException(this.getClass().getName()+" requires at least one weight.");
			}
			
			//Generate random weights
			Random weightGenerator = new Random(System.nanoTime());
			
			this.inputWeights = new LinkedList<Double>();
			for(int weightIdx = 0; weightIdx < numberOfWeights; weightIdx++) {
				double randomWeight = weightGenerator.nextDouble() - weightGenerator.nextDouble();
				this.inputWeights.add(randomWeight);
			}
			
			//Generate the default activation function as a BinaryStepFunction,
			//which produces 1, if the input is greater than 0.5, and 0 otherwise.
			this.activationFunction = new BinaryStepFunction(0.5, 1, 0);
			
			//Give a default bias of 1.
			this.bias = 1.0;
		}
		
		/**
		 * Override existing weights with parameter list.
		 * @param w the new weights
		 * @throws IllegalArgumentException if the parameter is null, or if there are no elements in it
		 */
		public Builder inputWeights(List<Double> w) {
			if(w == null || w.size() < 1) {
				throw new IllegalArgumentException(this.getClass().getName()+" requires at least one weight.");
			}
			
			this.inputWeights = w;
			return this;
		}
		
		/**
		 * Override the existing <tt>ActivationFunction</tt>.
		 * @param f the new <tt>ActivationFunction</tt>.
		 * @throws IllegalArgumentException if the parameter is null
		 */
		public Builder activationFunction(ActivationFunction f) {
			if(f == null) {
				throw new IllegalArgumentException(this.getClass().getName()+" requires an ActivationFunction.");
			}
			
			this.activationFunction = f;
			return this;
		}
		
		/**
		 * Override the existing bias. 
		 * @param b the new bias.
		 */
		public Builder bias(double b) {
			this.bias = b;
			return this;
		}
		
		/**
		 * @return the built <tt>ArtificialNeuron</tt>
		 */
		public ArtificialNeuron build() {
			return new ArtificialNeuron(this);
		}
	}	
}
