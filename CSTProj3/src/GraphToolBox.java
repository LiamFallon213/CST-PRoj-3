/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Collections;
import java.util.List;
import java.util.Vector;
/**
 *
 * @author yaw
 */
public class GraphToolBox {
    // return an array containing the vertex numbers of an optimal VC.
    public static int[] exactVC(Graph inputGraph) {
    	int[][] graphMatrix = inputGraph.getGraph();
    	int[] graphMatrixNodes =  new int[inputGraph.getGraph().length];
    	for (int i = 0; i < graphMatrix.length; i++) {
    		graphMatrixNodes[i] = i;					//O(n)
		}
        int[] vertexCover;
        int size = 2^graphMatrixNodes.length;
        Vector<Vector<Integer>> possibleVertexCovers = new Vector();
        findAllPremutations(possibleVertexCovers, graphMatrix, graphMatrixNodes);
        Vector<Vector<Integer>> vertexCovers = new Vector();
        
        for (int j = 0; j < possibleVertexCovers.size(); j++) {
        	if(verifyVC(possibleVertexCovers.get(j), inputGraph)) {
        		vertexCovers.add(possibleVertexCovers.get(j));
        	}
		}
        
        Vector<Integer> finalVC = vertexCovers.get(0);
        
        for (int i = 0; i < vertexCovers.size(); i++) {
			if(vertexCovers.get(i).size() < finalVC.size()) {
				finalVC = vertexCovers.get(i);
			}
		}

//        for (int i = 0; i < finalVC.size(); i++) {
//			System.out.print(finalVC.get(i)+" ");
//		}
//     for (int i = 0; i < possibleVertexCovers.size(); i++) {
//    		System.out.println(" "); 
//    		System.out.print(verifyVC(possibleVertexCovers.get(i), inputGraph) + ": ");
//			for (int j = 0; j < possibleVertexCovers.get(i).size(); j++) {
//				System.out.print(" " + possibleVertexCovers.get(i).get(j));
//		}
//	}

        
//        for (int i = 0; i < vertexCovers.size(); i++) {
//        	System.out.println(" "); 
//        	System.out.print(verifyVC(vertexCovers.get(i), inputGraph) + ": ");
//			for (int j = 0; j < vertexCovers.get(i).size(); j++) {
//				System.out.print(" " + vertexCovers.get(i).get(j));
//			}
//		}
        
        int[] output = new int[finalVC.size()];
        
        for (int i = 0; i < output.length; i++) {
	        output[i] = finalVC.get(i);
        }
        
		return output;
        
        
    }

    
    private static void findAllPremutations(Vector<Vector<Integer>> possibleVertexCovers, int[][] graphMatrix, int[] graphMatrixNodes) {
		int n = graphMatrixNodes.length;
		
		
		for(int i = 0; i < Math.pow(2, n); i++) {
			boolean[] boolMatrix = getBoolMatrix(i);
			Vector<Integer> premutation = new Vector();
			for (int j = 0; j < boolMatrix.length; j++) {
				if(boolMatrix[j]){
					premutation.add(graphMatrixNodes[j]);
				}
			}
			possibleVertexCovers.add(premutation);
		}
		
		
	}
    
    

	private static boolean[] getBoolMatrix(int input) { //converts to binary then makes a bool matrix based on the digits
		String binaryI = Integer.toBinaryString(input);
		int length = binaryI.length();
		char[] preOutput = new char[length];
		int c = 0;
		for(int j = length-1; j >= 0; j--) {
			preOutput[j] = binaryI.charAt(c);
			c++;
		}
		
		boolean[] output = new boolean[length];
		for (int i = 0; i < output.length; i++) {
			if(preOutput[i] == '1') output[i] = true;
			if(preOutput[i] == '0') output[i] = false;
		}
		return output;
	}

	// return (in polynomial time) an array containing the vertex numbers of a VC.
    public static int[] inexactVC(Graph inputGraph) {
    	int[][] graphMatrix = inputGraph.getGraph();
    	int[][] graphMatrixNodes =  new int[inputGraph.getGraph().length][2];
    	
    	for (int i = 0; i < graphMatrix.length; i++) {
    		graphMatrixNodes[i][0] = i;		
    		graphMatrixNodes[i][1] = graphMatrix[i].length;	//O(n)
		}
    	
    	graphMatrixNodes = sortLow(graphMatrixNodes);
    	
    	Vector<Integer> vertexCover = new Vector();
    	Vector<Integer> candidateVertexCover = new Vector();
    	
    	for (int i = 0; i < graphMatrixNodes.length; i++) {
    		vertexCover.add(graphMatrixNodes[i][0]);
		}
    	candidateVertexCover = vertexCover;
    	
    	for (int i = 0; i < graphMatrixNodes.length; i++) {
    		int test = graphMatrixNodes[i][0];
    		candidateVertexCover.removeElement(test);
    		if(verifyVC(candidateVertexCover, inputGraph)) {
    			vertexCover = (Vector<Integer>) candidateVertexCover.clone();
    		}else{
    			candidateVertexCover = (Vector<Integer>) vertexCover.clone();
    		}
    		
		}
    	
    	int[] output = new int[vertexCover.size()];
    	
    	for (int i = 0; i < output.length; i++) {
			output[i] = vertexCover.get(i);
		}
    	
		return output;
    }
    
    private static int[][] sortLow(int[][] graphMatrixNodes) {
		int temp[] = new int[2];
		int n = graphMatrixNodes.length;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n; j++) {
				if(graphMatrixNodes[j-1][1] > graphMatrixNodes[j][1]) {
					temp = graphMatrixNodes[j-1];
					graphMatrixNodes[j-1] = graphMatrixNodes[j];
					graphMatrixNodes[j] = temp;
				}
			}
		}
		return graphMatrixNodes;
		
	}
    
    


	// return an array containing the vertex numbers of an optimal IS.
    public static int[] optimalIS(Graph inputGraph) {
    	int[][] graphMatrix = inputGraph.getGraph();
    	int[] graphMatrixNodes =  new int[inputGraph.getGraph().length];
    	for (int i = 0; i < graphMatrix.length; i++) {
    		graphMatrixNodes[i] = i;					//O(n)
		}
        Vector<Vector<Integer>> possibleIndependentSets = new Vector();
        findAllPremutations(possibleIndependentSets, graphMatrix, graphMatrixNodes);
        Vector<Vector<Integer>> independentSets = new Vector();
        
        for (int j = 0; j < possibleIndependentSets.size(); j++) {
        	if(verifyIS(possibleIndependentSets.get(j), inputGraph)) {
        		independentSets.add(possibleIndependentSets.get(j));
        	}
		}
        
        Vector<Integer> finalIS = independentSets.get(0);
        
        for (int i = 0; i < independentSets.size(); i++) {
			if(independentSets.get(i).size() > finalIS.size()) {
				finalIS = independentSets.get(i);
			}
		}

//        for (int i = 0; i < finalVC.size(); i++) {
//			System.out.print(finalVC.get(i)+" ");
//		}
//     for (int i = 0; i < possibleVertexCovers.size(); i++) {
//    		System.out.println(" "); 
//    		System.out.print(verifyVC(possibleVertexCovers.get(i), inputGraph) + ": ");
//			for (int j = 0; j < possibleVertexCovers.get(i).size(); j++) {
//				System.out.print(" " + possibleVertexCovers.get(i).get(j));
//		}
//	}

        
//        for (int i = 0; i < vertexCovers.size(); i++) {
//        	System.out.println(" "); 
//        	System.out.print(verifyVC(vertexCovers.get(i), inputGraph) + ": ");
//			for (int j = 0; j < vertexCovers.get(i).size(); j++) {
//				System.out.print(" " + vertexCovers.get(i).get(j));
//			}
//		}
        
        int[] output = new int[finalIS.size()];
        
        for (int i = 0; i < output.length; i++) {
	        output[i] = finalIS.get(i);
        }
        
		return output;
        
        
    }
    
    // return (in polynomial time) an array containing the vertex numbers of a IS.
    public static int[] inexactIS(Graph inputGraph) {
    	int[][] graphMatrix = inputGraph.getGraph();
    	int[][] graphMatrixNodes =  new int[inputGraph.getGraph().length][2];
    	
    	for (int i = 0; i < graphMatrix.length; i++) {
    		graphMatrixNodes[i][0] = i;
    		graphMatrixNodes[i][1] = graphMatrix[i].length;
		}
    	
    	graphMatrixNodes = sortLow(graphMatrixNodes);
    	
    	Vector<Integer> independentSet = new Vector();
    	Vector<Integer> candidateIndependentSet = new Vector();
    	
//    	for (int i = 0; i < graphMatrixNodes.length; i++) {
//    		independentSet.add(graphMatrixNodes[i]);
//		}
    	
    	
    	for (int i = 0; i < graphMatrixNodes.length; i++) {
    		int test = graphMatrixNodes[i][0];
    		candidateIndependentSet.add(test);
    		if(verifyIS(candidateIndependentSet, inputGraph)) {
    			independentSet = (Vector<Integer>) candidateIndependentSet.clone();
    		}else{
    			candidateIndependentSet = (Vector<Integer>) independentSet.clone();
    		}
    		
		}
    	
    	int[] output = new int[independentSet.size()];
    	
    	for (int i = 0; i < output.length; i++) {
			output[i] = independentSet.get(i);
		}
    	
		return output;
    }
    
    public static boolean verifyVC(int[] vertexCover, Graph inputGraph) { //O(n^2)
    	int[][] graphMatrix = inputGraph.getGraph();
    	int[] graphMatrixNodes =  new int[inputGraph.getGraph().length];
    	int[] connectedNodes = new int[graphMatrixNodes.length];
    	for (int i = 0; i < graphMatrix.length; i++) {
    		graphMatrixNodes[i] = graphMatrix[i][0];
    		connectedNodes[i] = -1;					//O(n)
		}
    	for (int i = 0; i < vertexCover.length; i++) {
    		connectedNodes[vertexCover[i]] = vertexCover[i];
    		for (int j = 0; j < graphMatrix[vertexCover[i]].length; j++) { //O(n^2)
    			int node = graphMatrix[vertexCover[i]][j];
				if(connectedNodes[node] == -1) {
					connectedNodes[node] = node;
				}
			}
		}
    	for (int i = 0; i < connectedNodes.length; i++) {
			if(connectedNodes[i] == -1) return false;
			/*else*/ 
		}
    	return true;
    }
    
    public static boolean verifyVC(Vector<Integer> vertexCoverin, Graph inputGraph) { //O(n^2)
    	int[] vertexCover = new int[vertexCoverin.size()];
    	for (int i = 0; i < vertexCoverin.size(); i++) {
    		vertexCover[i] = vertexCoverin.get(i);
		}
    	
    	int[][] graphMatrix = inputGraph.getGraph();
    	int[] graphMatrixNodes =  new int[inputGraph.getGraph().length];
    	int[] connectedNodes = new int[graphMatrixNodes.length];
//    	for (int i = 0; i < graphMatrix.length; i++) {
//    		graphMatrixNodes[i] = graphMatrix[i][0];
//    		connectedNodes[i] = -1;					//O(n)
//		}
    	int[][] graphEdges = new int[(int) Math.pow(graphMatrix.length,2)][2];
    	
    	for (int i = 0; i < graphEdges.length; i++) {
    		graphEdges[i][0] = -1;
    		graphEdges[i][1] = -1;
		}
    	int c = 0;
    	for (int i = 0; i < graphMatrix.length; i++) {
			for (int j = 0; j < graphMatrix[i].length; j++) {
				graphEdges[c][0] = i;
				graphEdges[c][1] = graphMatrix[i][j];
				c++;
			}
		}
    	boolean vertexCoverBool = true;
    	for (int i = 0; i < graphEdges.length; i++) {
			if(!(vertexCoverin.contains(graphEdges[i][0]))) {
				if(!(vertexCoverin.contains(graphEdges[i][1]))) {
					if(graphEdges[i][0] != -1 && graphEdges[i][1] != -1) {
						vertexCoverBool = false;
					}
				};
			};
		}
    	
    	
    	
//    	for (int i = 0; i < vertexCover.length; i++) {
//    		connectedNodes[vertexCover[i]] = vertexCover[i];
//    		for (int j = 0; j < graphMatrix[vertexCover[i]].length; j++) { //O(n^2)
//    			int node = graphMatrix[vertexCover[i]][j];
//				if(connectedNodes[node] == -1) {
//					connectedNodes[node] = node;
//				}
//			}
//		}
//    	for (int i = 0; i < connectedNodes.length; i++) {
//			if(connectedNodes[i] == -1) return false;
//			/*else*/ 
//		}
    	return vertexCoverBool;
    }
    
    public static boolean verifyIS(Vector<Integer> independentSetin, Graph inputGraph) { //O(n^2)
    	int[] independentSet = new int[independentSetin.size()];
    	int[][] graphMatrix = inputGraph.getGraph();
    	for (int i = 0; i < independentSetin.size(); i++) {
    		independentSet[i] = independentSetin.get(i);
    	}
    	
    	boolean indSetBool = true;
    	for (int i = 0; i < independentSet.length; i++) {
			for (int j = 0; j < graphMatrix[independentSet[i]].length; j++) {
				for (int j2 = 0; j2 < independentSet.length; j2++) {
					if(graphMatrix[independentSet[i]][j] == independentSet[j2]) {
						indSetBool = false;
					}
				}
			}
		}
		return indSetBool;
    	
    }
    
}
    
    

