package test;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import test.domain.INPUT_PROBLEM;
import test.domain.TownGraph;
import test.domain.TownGraphEdge;
import test.domain.TownGraphEdgeBuilder;
import test.logic.VertexMapper;
import test.logic.algorithms.AlgorithmExecutor;
import test.logic.algorithms.AlgorithmExecutorFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <p>The class is responsible for taking input from the standard input mentioned in the problem definition document. 
 * 
 * <p>To be more precise the following INPUT format is assumed:
 *
 * <p>First line: a string containing graph edge definitions separated by white spaces. Each edge is defined as "XYN",
 * where 
 * <ul>
 *     <li>X - a character identifying from vertex
 *     <li>Y - a character identifying to vertex
 *     <li>N - positive integer distance between X and Y.
 * </ul>
 *
 * <p>Second line: a number of test queries
 * <p>Every next line can be one of the following
 * <ul>
 *     <li> P1 X1 X2 X3 
 *     <li> P2 X Y N
 *     <li> P3 X Y N     
 *     <li> P4 X Y     
 *     <li> P5 X Y N    
 * </ul>
 * <p> Explanation of the test queries format
 * <ul>
 *     <li> P1 X1 X2 X3 - problem 1: find the distance along the given route. X1 - first vertex, X2 - second vertex, ... 
 *     <li> P2 X Y N - problem 2: find the number of routes from START vertex to FINISH vertex with a less or equal number of STOPS. X - START vertex, Y - FINISH vertex, N - number of STOPS to consider 
 *     <li> P3 X Y N - problem 3: find the number of routes from START vertex to FINISH vertex with a exactly number of STOPS. X - START vertex, Y - FINISH vertex, N - number of STOPS to consider      
 *     <li> P4 X Y - problem 4: find the shortest distance from START vertex to FINISH vertex with at least one stop. X - START vertex, Y - FINISH vertex     
 *     <li> P5 X Y N - problem 5: find the number of different routes from START vertex to FINISH vertex with a distance of less than
 * MAX_DISTANCE. X - START vertex, Y - FINISH vertex, N - short integer representing MAX_DISTANCE     
 * </ul>
 * 
 * <p> Configuration from the description document:
 * <p>
 * <br/>AB5 BC4 CD8 DC8 DE6 AD5 CE2 EB3 AE7
 * <br/>10
 * <br/>P1 A B C
 * <br/>P1 A D
 * <br/>P1 A E B C D
 * <br/>P1 A E D
 * <br/>P2 C C 3
 * <br/>P3 A C 4
 * <br/>P4 A C
 * <br/>P4 B B
 * <br/>P5 C C 30
 * 
 * <p>The OUTPUT format: 
 * <p>OUTPUT #X: N
 * <p>where X - number of test case starting from 1, N - a number if answer exists, "NO SUCH ROUTE" otherwise
 */
public class TextExecutor {
        
    private static final String ANSWER_TEMPLATE = "OUTPUT #%s: %s"; 
    
    public static void main(final String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final VertexMapper mapper = new VertexMapper();
        final List<TownGraphEdge> edges = parseAndValidateEdges(br.readLine(), mapper);

        AlgorithmExecutorFactory factory = new AlgorithmExecutorFactory();
        
        final TownGraph townGraph = new TownGraph(edges, mapper.numberOfVertexes());
        
        int N = Integer.parseInt(br.readLine());
        int currentQuery = 0;
        
        while(currentQuery++ < N){
            String[] queryRequest = br.readLine().split("\\s");
            
            Preconditions.checkArgument(queryRequest.length >= 1, "The problem query is missing");

            final INPUT_PROBLEM inputProblem = INPUT_PROBLEM.valueOf(queryRequest[0]);

            final AlgorithmExecutor algorithm = factory.getAlgorithmExecutor(inputProblem);

            final String algorithmResult = algorithm.validateParametersAndExecute(townGraph, mapper, queryRequest);
            
            System.out.println(String.format(ANSWER_TEMPLATE, currentQuery, algorithmResult));
        }
    }

    private static List<TownGraphEdge> parseAndValidateEdges(String stringToParse, VertexMapper mapper) {
        
        String[] graphEdges = stringToParse.split("\\s");
        Preconditions.checkArgument(graphEdges.length > 0, "Missing graph edges");

        List<TownGraphEdge> edges = Lists.newArrayList();
        for(String strGraphEdge : graphEdges){
            Preconditions.checkArgument(strGraphEdge.length() >= 3, "Expected graph edge in 'XYN' format, where X and Y - a single characters and N - positive integer");
            Character fromVertex = strGraphEdge.charAt(0);
            Character toVertex = strGraphEdge.charAt(1);
            int distance = Integer.parseInt(strGraphEdge.substring(2));

            edges.add(new TownGraphEdgeBuilder().from(mapper.mapCharacterToId(fromVertex)).to(mapper.mapCharacterToId(toVertex)).distance(distance).build());
        }
        return edges;
    }
}
