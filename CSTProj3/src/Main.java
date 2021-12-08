
public class Main {

	public static void main(String[] args) {
		Graph test = new Graph("resources/graph2.txt");
		int[] testVC = {1,3};
//	    int[] output =	GraphToolBox.exactVC(test);
		int[] output =	GraphToolBox.inexactVC(test);
//	    int[] output =	GraphToolBox.optimalIS(test);
//		int[] output =	GraphToolBox.inexactIS(test);
		for (int i = 0; i < output.length; i++) {
			System.out.print(output[i]+" ");
		}
		System.out.println(" ");
		System.out.println(output.length);
	}

}
