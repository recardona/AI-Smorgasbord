package rogel.io.ai.ann.test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import rogel.io.ai.ann.ArtificialNeuralNetLayer;
import rogel.io.ai.ann.ArtificialNeuron;
import rogel.io.ai.ann.activationfunctions.BinaryStepFunction;

public class ArtificialNeuralNetLayerTest {

	ArtificialNeuralNetLayer layer;
	ArtificialNeuron neuronA;
	ArtificialNeuron neuronB;
	
	@Before
	public void setUp() throws Exception {
		layer = new ArtificialNeuralNetLayer();
		
		//this builds an ArtificialNeuron with 2 weights,
		//and a BinaryStepFunction(1.0, 5.0, 1.3) and bias of 1
		LinkedList<Double> bWeights = new LinkedList<Double>();
		bWeights.add((1/3d));
		bWeights.add((2/3d));

		neuronA = new ArtificialNeuron.Builder(5)
			.inputWeights(bWeights)
			.activationFunction(new BinaryStepFunction(1.0, 5.0, 1.3)).bias(1).build();

		neuronB = new ArtificialNeuron.Builder(5)
			.inputWeights(bWeights)
			.activationFunction(new BinaryStepFunction(1.0, 5.0, 1.3)).bias(1).build();
		
		layer.add(neuronA);
		layer.add(neuronB);
	}

	@Test
	public void testSetInputWeightsForAll() {
		
		LinkedList<Double> newWeights = new LinkedList<Double>();
		newWeights.add((1/4d));
		newWeights.add((2/4d));
		newWeights.add((3/4d));
		
		List<LinkedList<Double>> layerInputWeights = new LinkedList<LinkedList<Double>>();
		layerInputWeights.add(newWeights);
		layerInputWeights.add(newWeights); //added twice (once for each neuron)
		
		layer.setInputWeightsForAll(layerInputWeights);
		
		List<Double> neuronAWeights = neuronA.getInputWeights();
		List<Double> neuronBWeights = neuronB.getInputWeights();
		
		assertEquals("Weights must have been set to the same thing", neuronAWeights, neuronBWeights);
		assertEquals("There should be 3 weights in the new assignment", 3, neuronAWeights.size());
		
		assertEquals("First weight should be 1/4th", (1/4d), neuronAWeights.get(0), 0.0);
		assertEquals("Second weight should be 2/4ths", (2/4d), neuronAWeights.get(1), 0.0);
		assertEquals("Third weight should be 3/4ths", (3/4d), neuronAWeights.get(2), 0.0);
		
		
	}

}
