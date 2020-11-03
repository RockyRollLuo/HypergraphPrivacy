package algorithm;

import model.Hypergraph;
import model.Result;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Randomizer {
    private static final Logger LOGGER = Logger.getLogger(Randomizer.class);

    private final Hypergraph hypergraph;
    private final int sensitivity; // usually 2
    private final double epsilon; //privacy budget

    /*
     * constructor
     */
    public Randomizer(Hypergraph hypergraph, int sensitivity, double epsilon) {
        this.hypergraph = hypergraph;
        this.sensitivity = sensitivity;
        this.epsilon = epsilon;
    }

    public Hypergraph run(){
        long startTime = System.nanoTime();

        // temp data
        ArrayList<Integer> nodeList = hypergraph.getNodeList();
        HashMap<Integer, ArrayList<Integer>> edgeMap = hypergraph.getEdgeMap();
        int n = nodeList.size();
        int m = edgeMap.size();

        //flip probability
        Double p=1/(1+Math.exp(epsilon/sensitivity));

        HashMap<Integer, ArrayList<Integer>> newEdgeMap = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : edgeMap.entrySet()) {
            Integer eId = entry.getKey();
            ArrayList<Integer> edge = entry.getValue();

            ArrayList<Integer> newEdge = new ArrayList<>(edge);
            for (Integer node : nodeList) {
//                if (new Double(Math.random()).compareTo(p)<0) { //flip
                if (Math.random()<p) { //flip
                    if (newEdge.contains(node)) {
                        newEdge.remove(node);
                    } else {
//                        newEdge.add(node);
                    }
                }
            }
            newEdgeMap.put(eId, newEdge);
        }

        long endTime = System.nanoTime();
        double takenTime = (endTime - startTime) / 1.0E9D;
        LOGGER.error(takenTime);

        return new Hypergraph(nodeList,newEdgeMap,true);
    }





}
