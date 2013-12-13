package rogel.io.ai.ann.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import rogel.io.ai.ann.ArtificialNeuron;
import rogel.io.ai.ann.activationfunctions.BinaryStepFunction;

public class ArtificialNeuronTest {

	ArtificialNeuron a;
	ArtificialNeuron b;
	ArtificialNeuron c;
	
	@Before
	public void setUp() throws Exception {
		//this builds an ArtificialNeuron with 3 weights, 
		//a BinaryStepFunction(0.5, 1, 0) and bias of 1
		LinkedList<Double> aWeights = new LinkedList<Double>();
		aWeights.add((1/4d));
		aWeights.add((2/4d));
		aWeights.add((3/4d));
		a = new ArtificialNeuron.Builder(10)
			.inputWeights(aWeights) //override existing weights
			.build();
		
		//this builds an ArtificialNeuron with 2 weights,
		//and a BinaryStepFunction(1.0, 5.0, 1.3) and bias of 1
		LinkedList<Double> bWeights = new LinkedList<Double>();
		bWeights.add((1/3d));
		bWeights.add((2/3d));
		b = new ArtificialNeuron.Builder(5)
			.inputWeights(bWeights)
			.activationFunction(new BinaryStepFunction(1.0, 5.0, 1.3)).bias(1).build();
	}
	
	@Test
	public void testBuilder() {
		try {
			c = new ArtificialNeuron.Builder(0).build();
		} catch (Exception e) {
			assertTrue("0 weights should be an illegal argument", (e instanceof IllegalArgumentException));
		}
		try {
			c = new ArtificialNeuron.Builder(1).activationFunction(null).build();
		} catch (Exception e) {
			assertTrue("A null activation function should be an illegal argument", (e instanceof IllegalArgumentException));
		}
		
		try {
			c = new ArtificialNeuron.Builder(1).inputWeights(null).build();
		} catch (Exception e) {
			assertTrue("A null set of input weights should be an illegal argument", (e instanceof IllegalArgumentException));
		}
		
		try {
			c = new ArtificialNeuron.Builder(1).inputWeights(new LinkedList<Double>()).build();
		} catch (Exception e) {
			assertTrue("An empty set of input weights should be an illegal argument", (e instanceof IllegalArgumentException));
		}
	}

	@Test
	public void testFeed() {
		//"a" is an ArtificialNeuron with:
		// weights (0.25, 0.50, 0.75),
		// a BinaryStepFunction (0.5, 1, 0) 
		// and bias of 1
		
		List<Double> twoInputs = new LinkedList<Double>();
		twoInputs.add(1.0);
		twoInputs.add(0.5);
		
		//Wrong parameters list!
		try {
			a.feed(twoInputs);
		} catch(Exception e) {
			assertTrue(e.getMessage(), (e instanceof IllegalArgumentException));
		}
		
		
		//"b" is an ArtificialNeuron with:
		// weights (0.33, 0.66) 
		// a BinaryStepFunction(1.0, 5.0, 1.3) and bias of 1
		
		/*
		 * b's weights are (1/3) and (2/3).
		 * b's inputs  are ( 1 ) and (1/2).
		 * b's bias is 1.
		 * 
		 * (1/3)*1 + (2/3)*(1/2) + 1 = 1.666...
		 * 
		 * 1.666... > 1.3 threshold, so return value should be 5.0 (as opposed to 1.0). 
		 */
		double activationLevel = b.feed(twoInputs);
		assertEquals("Given the input in "+twoInputs.toString()+ " the neuron should fire.", 5.0, activationLevel, 0.0);
		
		
		/*
		 * b's weights are (1/3) and (2/3).
		 * b's inputs  are (1/2) and ( 0 ).
		 * b's bias is 1.
		 * 
		 * (1/3)*(1/2) + (2/3)*(0) + 1 = 1.1666...
		 * 
		 * 1.1666... < 1.3 threshold, so return value should be 1.0 (as opposed to 5.0). 
		 * 
		 */
		twoInputs = new LinkedList<Double>();
		twoInputs.add(0.5);
		twoInputs.add(0.0);
		activationLevel = b.feed(twoInputs);
		assertEquals("Given the input in "+twoInputs.toString()+ " the neuron not should fire.", 1.0, activationLevel, 0.0);
		
		
		
		



		
	}

}
