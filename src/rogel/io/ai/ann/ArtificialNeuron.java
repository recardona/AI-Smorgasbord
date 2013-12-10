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
	 */
	public double feed(List<Double> inputs) {
		
		//verify that the number of inputs match the number weights
		if(inputs.size() != inputWeights.size()) {
			throw new IllegalArgumentException("Number of inputs " +inputs.size()+ " exceeds number of weights " +inputWeights.size());
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
		 * Builds the default parameter list for the <tt>ArtificialNeuron</tt>.
		 * @param numberOfWeights the number of weights this <tt>ArtificialNeuron</tt>.
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
		 * Override existing weights.
		 * @param w the new weights
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
