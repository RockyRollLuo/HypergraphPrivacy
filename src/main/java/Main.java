import algorithm.CoreDecomposition;
import algorithm.Randomizer;
import algorithm.StructureProperty;
import model.Hypergraph;
import model.Result;
import org.apache.log4j.Logger;
import util.ComputeUtils;
import util.FileIOUtils;
import util.GetUtils;
import util.SetOpt;
import util.SetOpt.Option;

import java.io.IOException;
import java.util.*;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    @Option(abbr = 's', usage = "Separate delimiter,0:tab,1:space,2:comma")
    public static int delimType = 0;

    @Option(abbr = 'S', usage = "sensitivity")
    public static int sensitivity = 2;

    @Option(abbr = 'e', usage = "epsilon")
    public static double epsilon = 0.5;

    @Option(abbr = 'c', usage = "whether to constructe nodeToEdgesMap, false:no, true:yes")
    public static boolean constructStructure = true;


    public static void main(String[] args) throws IOException {
        /*
        read parameters
         */
        Main main = new Main();
        args = SetOpt.setOpt(main, args);

        /*
        graph information
         */
        String datasetName = args[0];
        Hypergraph hypergraph = FileIOUtils.loadGraph(datasetName, GetUtils.getDelim(delimType), constructStructure);
        ArrayList<Integer> nodeList = hypergraph.getNodeList();
        HashMap<Integer, ArrayList<Integer>> edgeMap = hypergraph.getEdgeMap();
        HashMap<Integer, Integer> degreeMap = hypergraph.getDegreeMap();

        System.out.println("dataset:" + datasetName);
        System.out.println("node size:" + nodeList.size());
        System.out.println("edge size:" + edgeMap.size());
        System.out.println("max cardinality:" + hypergraph.getMaxCardinality());
        System.out.println("min cardinality:" + hypergraph.getMinCardinality());



        //randomize graph
        Randomizer randomizer = new Randomizer(hypergraph, sensitivity, epsilon);
        Hypergraph hypergraph_privacy = randomizer.run();
        HashMap<Integer, ArrayList<Integer>> edgeMap_privacy = hypergraph_privacy.getEdgeMap();
        HashMap<Integer, Integer> degreeMap_privacy = hypergraph_privacy.getDegreeMap();



        System.out.println("===degreeDistribution===");
        StructureProperty.degreeDistribution(degreeMap);
        StructureProperty.degreeDistribution(degreeMap_privacy);



//        System.out.println("===coreDistribution===");
//        CoreDecomposition coreDecomposition1 = new CoreDecomposition(hypergraph);
//        coreDecomposition1.run();
//        System.out.println(ComputeUtils.computeDistribution(coreDecomposition1.getCoreVMap()));
//
//        CoreDecomposition coreDecomposition2 = new CoreDecomposition(hypergraph_privacy);
//        coreDecomposition2.run();
//        System.out.println(ComputeUtils.computeDistribution(coreDecomposition2.getCoreVMap()));

    }
}
