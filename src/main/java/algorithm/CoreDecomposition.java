package algorithm;

import model.Hypergraph;
import model.Result;
import org.apache.log4j.Logger;
import util.ComputeUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class CoreDecomposition {
    private static final Logger LOGGER = Logger.getLogger(CoreDecomposition.class);

    private Hypergraph hypergraph;
    private HashMap<Integer, Integer> coreVMap;
    private HashMap<Integer, Integer> coreEMap;

    public CoreDecomposition(Hypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }


    /**
     * travel graph by nodeToEdgesMap
     * this method is very slow, due to the comparing edges
     *
     * @return
     */
    public Result run() {
        long startTime = System.nanoTime();

        //private properties of hypergraph
        ArrayList<Integer> nodeList = hypergraph.getNodeList();
        HashMap<Integer, ArrayList<Integer>> edgeMap = hypergraph.getEdgeMap();
        HashMap<Integer, ArrayList<Integer>> nodeToEdgesMap = hypergraph.getNodeToEdgesMap();

        //temp data
        HashMap<Integer, Integer> tempCoreVMap = new HashMap<>();
        HashMap<Integer, Integer> tempCoreEMap = new HashMap<>();

        ArrayList<Integer> tempNodeList = new ArrayList<>(nodeList);
        HashMap<Integer, ArrayList<Integer>> tempEdgeMap = new HashMap<>(edgeMap);
        HashMap<Integer, ArrayList<Integer>> tempNodeToEdgesMap = new HashMap<>(nodeToEdgesMap);

        //compute neighorsMap and degreeMap
        HashMap<Integer, Integer> degreeMap = hypergraph.getDegreeMap();
        Integer minDegreeNode = hypergraph.getMinDegreeNode(degreeMap);
        int minDegree = degreeMap.get(minDegreeNode);

        //compute core number of node
        for (int k = minDegree; ; k++) {
            if (tempNodeList.size() == 0) {
                break;
            }

            //delete nodes
            ArrayList<Integer> deleteNodes = new ArrayList<>();
            for (Integer v : tempNodeList) {
                if (degreeMap.get(v) <= k) {
                    deleteNodes.add(v);
                }
            }

            //new delete nodes
            while (!deleteNodes.isEmpty()) {
                ArrayList<Integer> newDeleteNodes = new ArrayList<>();

                for (Integer v : deleteNodes) {
                    if (!tempNodeList.contains(v)) continue;


                    for (Integer eId : tempNodeToEdgesMap.get(v)) {
                        for (Integer u : tempEdgeMap.get(eId)) {
                            if (!tempNodeList.contains(u)) continue;

                            //update degreeMap
                            int uDegree = degreeMap.get(u) - 1;
                            degreeMap.put(u, uDegree);
                            if (uDegree == k) {
                                newDeleteNodes.add(u);
                            }
                        }
                    }
                    tempCoreVMap.put(v, k); //core number of node

                    //update
                    tempNodeToEdgesMap.remove(v); //edges contain v need to update(one edge contain many nodes), very troublesome,
                    tempNodeList.remove(v);
                }
                deleteNodes = newDeleteNodes;
            }
        }

        //compute core number of edge
        tempCoreEMap = ComputeUtils.computeCoreEMapByCoreVMap(tempEdgeMap, tempCoreVMap);

        this.coreVMap = tempCoreVMap;
        this.coreEMap = tempCoreEMap;
        long endTime = System.nanoTime();
        double takenTime = (endTime - startTime) / 1.0E9D;
        LOGGER.info(takenTime);

        return new Result(coreVMap, coreEMap, takenTime, "Decomposition");
    }


    /**
     * getter and setter
     */

    public Hypergraph getHypergraph() {
        return hypergraph;
    }

    public void setHypergraph(Hypergraph hypergraph) {
        this.hypergraph = hypergraph;
    }

    public HashMap<Integer, Integer> getCoreEMap() {
        return coreEMap;
    }

    public void setCoreEMap(HashMap<Integer, Integer> coreEMap) {
        this.coreEMap = coreEMap;
    }

    public HashMap<Integer, Integer> getCoreVMap() {
        return coreVMap;
    }

    public void setCoreVMap(HashMap<Integer, Integer> coreVMap) {
        this.coreVMap = coreVMap;
    }

}
