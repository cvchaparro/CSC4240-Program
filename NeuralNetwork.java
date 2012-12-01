import java.util.ArrayList;
import java.util.List;


public class NeuralNetwork
{
	List<Neuron> inputNeurons;
	List<Neuron> outputNeurons;
	List<List<Neuron>> hiddenNeurons;
	
	public NeuralNetwork(int numInput, int numOutput, int numHiddenLayers, List<Integer> numNeuronsPerHiddenLayer)
	{
		inputNeurons = new ArrayList<Neuron>();
		
		for(int n = 0; n < numInput; n++)
		{
			inputNeurons.add(new InputNeuron());
		}
		
		for(int n = 0; n < numOutput; n++)
		{
			outputNeurons.add(new OutputNeuron());
		}
		
		hiddenNeurons = new ArrayList<List<Neuron>>();
		List<Neuron> prevLayer = inputNeurons;
		for(int n = 0; n < numHiddenLayers; n++)
		{
			List<Neuron> currLayer = new ArrayList<Neuron>();
			for(int m = 0; m < numNeuronsPerHiddenLayer.size(); m++)
			{
				currLayer.add(new HiddenNeuron());
			}
			
			for(int m = 0; m < prevLayer.size(); m++)
			{
				for(int l = 0; l < currLayer.size(); l++)
				{
					prevLayer.get(m).connect(currLayer.get(l));
				}
			}
			hiddenNeurons.add(currLayer);
			prevLayer = currLayer;
		}
		
		for(int n = 0; n < prevLayer.size(); n++)
		{
			for(int m = 0; m < outputNeurons.size(); m++)
			{
				prevLayer.get(n).connect(outputNeurons.get(m));
			}
		}
	}
}