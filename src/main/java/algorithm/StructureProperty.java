package algorithm;

import model.Hypergraph;
import util.ComputeUtils;

import java.util.*;

public class StructureProperty {

    public static void degreeDistribution(HashMap<Integer, Integer> degreeMap) {

        HashMap<Integer,Integer> degreeDistributionMap= ComputeUtils.computeDistribution(degreeMap);

        List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(degreeDistributionMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return o1.getKey()-o2.getKey();
            }
        });

        ArrayList<Integer> degreeX = new ArrayList<>();
        ArrayList<Integer> degreeY = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            degreeX.add(entry.getKey());
            degreeY.add(entry.getValue());
        }
        System.out.println("x=np.array("+degreeX.toString()+")");
        System.out.println("y=np.array("+degreeY.toString()+")");
    }

    public static void degreeDifference(Hypergraph hypergraph1, Hypergraph hypergraph2) {

    }

    public static void cardinalityDistribution(HashMap<Integer, ArrayList<Integer>> edgeMap1, HashMap<Integer, ArrayList<Integer>> edgeMap2) {

        for (Integer eId : edgeMap1.keySet()) {
            ArrayList<Integer> e1 = edgeMap1.get(eId);
            ArrayList<Integer> e2 = edgeMap2.get(eId);





        }
    }



}
