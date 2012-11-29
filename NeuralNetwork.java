public class NeuralNetwork {
    public NeuralNetwork(FileIO file, int numHiddenLayers, int[] numNodes, double rate, double error) {
        System.out.print(numHiddenLayers + "  :  ");
        for (int i = 0; i < numNodes.length; i++) {
            System.out.print(numNodes[i] + "  :  ");
        }
        System.out.print(rate + "  :  ");
        System.out.println(error);
    }
}