package Algorithms;

import UI.CatalogItem;

public class BubbleSort implements AlgorithmItem{

	int[] inputs;
	private CatalogItem ci;

	public BubbleSort(CatalogItem ci, int[] inputs){
		this.ci = ci;
		this.inputs = inputs;
		visualSleep();
	}

	public void run(){
		sort();
		ci.popMessage("Algorithm Done", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	public void sort(){ //brute force
		for(int i=0; i<inputs.length-1; i++){
			for(int j=0; j<inputs.length-1-i; j++){
				if(inputs[j+1]<inputs[j]){
					int temp  = inputs[j+1];
					inputs[j+1] = inputs[j];
					inputs[j] = temp;
					visualSleep();
				}
			}
		}
	}

	private void visualSleep(){
		try{
			ci.refresh();
			Thread.sleep(ci.getSleepTime());	
		}catch(Exception ie){ie.printStackTrace();};
	}
}