package Algorithms;

import UI.CatalogItem;
import Algorithms.Greedy.*;

public class Algorithm implements Runnable{
	
	private CatalogItem ci;

	public Algorithm(CatalogItem ci){
		this.ci = ci;
	}

	public void run(){
		String design = ci.getDesign();
		AlgorithmItem ai = null;

		if(design.equals("brute_force")){
			ai = new BubbleSort(ci, ci.getInput());
		}else if(design.equals("decrease-and-conquer")){
			ai = new InsertionSort(ci, ci.getInput());
		}else if(design.equals("divide-and-conquer")){
			ai = new MergeSort(ci, ci.getInput());
		}else if(design.equals("transform-and-conquer")){
			ai = new HeapSort(ci, ci.getInput());
		}else if(design.equals("greedy_technique")){
			ai = ci.getDijkstra();
		}else if(design.equals("backtracking_technique")){
			ai = ci.getSudoku();
		}

		if(ai!=null)
			ai.run();
	}
}