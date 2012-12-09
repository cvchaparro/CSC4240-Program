public class Architecture {
    private int numHiddenLayers;
    private int[] numNeuronsPerHiddenLayer;

    public Architecture() {
        this(1, new int[1]);
    }

    public Architecture(int numLayers, int[] neuronsPerLayer) {
        if (numLayers <= 0) {
            numLayers = 1;
        }

        numHiddenLayers = numLayers;
        numNeuronsPerHiddenLayer = new int[numLayers];

        if (numNeuronsPerHiddenLayer.length == neuronsPerLayer.length) {
            for (int i = 0; i < numNeuronsPerHiddenLayer.length; i++) {
                if (neuronsPerLayer[i] == 0) {
                    neuronsPerLayer[i] = 1;
                }
                numNeuronsPerHiddenLayer[i] = neuronsPerLayer[i];
            }
        }
    }

    public void setNumHiddenLayers(int numLayers) {
        if (numLayers <= 0) {
            numLayers = 1;
        }
        numHiddenLayers = numLayers;
    }

    public  void setNumNeuronsPerHiddenLayer(int layer, int numNeurons) {
        numNeuronsPerHiddenLayer[layer] = numNeurons;
    }

    public String toString() {
        String str = "";
        str += "Hidden Layers: " + numHiddenLayers + "\n";
        for (int i = 0; i < numNeuronsPerHiddenLayer.length; i++) {
            str += "Neurons in Layer " + (i + 1) + ": " + numNeuronsPerHiddenLayer[i] + "\n";
        }
        return str;
    }
}