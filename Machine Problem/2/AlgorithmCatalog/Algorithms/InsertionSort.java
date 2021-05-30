package Algorithms;

import UI.CatalogItem;

public class InsertionSort implements AlgorithmItem{

	int[] inputs;
	private CatalogItem ci;

	public InsertionSort(CatalogItem ci, int[] inputs){
		this.ci = ci;
		this.inputs = inputs;
		visualSleep();
	} 

	public void run(){
		sort();
		ci.popMessage("Algorithm Done", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	public void sort(){ //decrease and conquer
		for (int i=1;i<inputs.length;i++ ){
			int v = inputs[i];
			int j = i -1;

			while(j>=0 && inputs[j]>v){
				inputs[j+1] = inputs[j];
				j--;
				visualSleep();
			}
			inputs[j+1] = v;
			visualSleep();
		}
	}

	private void visualSleep(){
		try{
			ci.refresh();
			Thread.sleep(ci.getSleepTime());	
		}catch(Exception ie){ie.printStackTrace();};
	}
}