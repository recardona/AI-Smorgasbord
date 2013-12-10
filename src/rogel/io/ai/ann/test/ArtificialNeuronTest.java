package rogel.io.ai.ann.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;

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
		//this builds an ArtificialNeuron with 5 random weights, 
		//a BinaryStepFunction(0.5, 1, 0) and bias of 1
		a = new ArtificialNeuron.Builder(5)
			.build();
		
		//this builds an ArtificialNeuron with 5 random weights, 
		b = new ArtificialNeuron.Builder(5)
			.activationFunction(new BinaryStepFunction(1.0, 5.0, 1.3)).bias(2).build();
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
		fail("Not yet implemented");
	}

}
