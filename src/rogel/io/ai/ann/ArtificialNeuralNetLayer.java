package rogel.io.ai.ann;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * An auxiliary class to help group <tt>ArtificialNeuron</tt>s into layers that
 * make up the <tt>ArtificialNeuralNet</tt>.
 * @see <tt>ArtificialNeuron.java</tt>
 * @author recardona
 */
public class ArtificialNeuralNetLayer implements List<ArtificialNeuron> {
	
	private List<ArtificialNeuron> neuronsInLayer;
	
	/**
	 * Creates an <tt>ArtificialNeuralNetLayer</tt> with a specified number of <tt>ArtificialNeuron</tt>s, 
	 * with each neuron carrying a specific number of random input weights.  The <tt>ArtificialNeuron</tt>s
	 * that are created are the default ones, as defined in <tt>ArtificialNeuron.java</tt>
	 * 
	 * @param numberOfNeurons number of <tt>ArtificialNeuron</tt>s in this layer
	 * @param numberOfInputWeightsPerNeuron number of random (between -1.0 and 1.0) weights for each <tt>ArtificialNeuron</tt>
	 * @see <tt>ArtificialNeuron.java</tt>
	 */
	public ArtificialNeuralNetLayer(int numberOfNeurons, int numberOfInputWeightsPerNeuron) 
	{
		this.neuronsInLayer = new LinkedList<ArtificialNeuron>();
		
		ArtificialNeuron temp = null;
		for(int neuronIdx = 0; neuronIdx < numberOfNeurons; neuronIdx++) {
			temp = new ArtificialNeuron.Builder(numberOfInputWeightsPerNeuron).build();
			this.neuronsInLayer.add(temp);
		}
	}
	
	/**
	 * Creates a completely empty <tt>ArtificialNeuralNetLayer</tt>.
	 */
	public ArtificialNeuralNetLayer() {
		this(0, 0);
	}
	
	/**
	 * For each <tt>ArtificialNeuron</tt> in this layer, apply a corresponding set of input weights. 
	 * @param layerInputWeights a list of neuron weights that will be applied to the ArtificialNeurons, respectively
	 * @throws IllegalArgumentException thrown if the number of layerInputWeights is not equal to the number of ArtificialNeurons
	 */
	public void setInputWeightsForAll(List<LinkedList<Double>> layerInputWeights)
	{
		if(layerInputWeights.size() > this.neuronsInLayer.size()) {
			throw new IllegalArgumentException("number of inputWeights ("+layerInputWeights.size()
					+") is too great for number of neurons in this layer ("+this.neuronsInLayer.size()+")");
		} else if(layerInputWeights.size() < this.neuronsInLayer.size()) {
			throw new IllegalArgumentException("number of inputWeights ("+layerInputWeights.size()
					+") is insufficient for number of neurons in this layer ("+this.neuronsInLayer.size()+")");
		}
		
		Iterator<LinkedList<Double>> inputWeightsIterator = layerInputWeights.iterator();
		Iterator<ArtificialNeuron> neuronsIterator = this.neuronsInLayer.iterator();
		
		while(neuronsIterator.hasNext() && inputWeightsIterator.hasNext()) {
			neuronsIterator.next().setInputWeights(inputWeightsIterator.next());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(ArtificialNeuron a : this.neuronsInLayer) {
			sb.append(a.toString()); sb.append("\n");
		}
		
		return sb.toString();
	}
		
	/* ------- List interface methods ------- */
	// These methods are wrappers to the corresponding LinkedList<?> methods.
	
	@Override
	public boolean add(ArtificialNeuron e) {
		return this.neuronsInLayer.add(e);
	}

	@Override
	public void add(int index, ArtificialNeuron element) {
		this.neuronsInLayer.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends ArtificialNeuron> c) {
		return this.neuronsInLayer.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ArtificialNeuron> c) {
		return this.neuronsInLayer.addAll(index, c);
	}

	@Override
	public void clear() {
		this.neuronsInLayer.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.neuronsInLayer.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.neuronsInLayer.containsAll(c);
	}

	@Override
	public ArtificialNeuron get(int index) {
		return this.neuronsInLayer.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.neuronsInLayer.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return this.neuronsInLayer.isEmpty();
	}

	@Override
	public Iterator<ArtificialNeuron> iterator() {
		return this.neuronsInLayer.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.neuronsInLayer.lastIndexOf(o);
	}

	@Override
	public ListIterator<ArtificialNeuron> listIterator() {
		return this.neuronsInLayer.listIterator();
	}

	@Override
	public ListIterator<ArtificialNeuron> listIterator(int index) {
		return this.neuronsInLayer.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return this.neuronsInLayer.remove(o);
	}

	@Override
	public ArtificialNeuron remove(int index) {
		return this.neuronsInLayer.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.neuronsInLayer.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.neuronsInLayer.retainAll(c);
	}

	@Override
	public ArtificialNeuron set(int index, ArtificialNeuron element) {
		return this.neuronsInLayer.set(index, element);
	}

	@Override
	public int size() {
		return this.neuronsInLayer.size();
	}

	@Override
	public List<ArtificialNeuron> subList(int fromIndex, int toIndex) {
		return this.neuronsInLayer.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return this.neuronsInLayer.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.neuronsInLayer.toArray(a);
	}

}
