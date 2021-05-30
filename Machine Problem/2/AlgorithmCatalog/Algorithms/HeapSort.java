package Algorithms;

import UI.CatalogItem;

public class HeapSort implements AlgorithmItem{
	int[] inputs;
	private CatalogItem ci;

	public HeapSort(CatalogItem ci, int[] inputs){
		this.ci = ci;
		this.inputs = inputs;
		visualSleep();
	}

	public void run(){
		sort(inputs);
		ci.popMessage("Algorithm Done", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	private void sort(int[] inputs){
		for(int i= inputs.length/2-1; i>=0; i--)
			heapify(inputs, inputs.length, i);

		for(int i=inputs.length-1; i>=0; i--){
			int v = inputs[0];
			inputs[0] = inputs[i];
			inputs[i] = v;
			visualSleep();
			heapify(inputs, i, 0);
		}
	}

	private void heapify(int[] inputs, int size, int i){
		int largest = i;
		int l = 2*i+1;
		int r = 2*i+2;

		if(l<size && inputs[l]>inputs[largest])
			largest = l;
		if(r<size && inputs[r]>inputs[largest])
			largest = r;

		if(largest!=i){
			int v = inputs[i];
			inputs[i] = inputs[largest];
			inputs[largest] = v;
			visualSleep();
			heapify(inputs, size, largest);
		}
	}

	private void visualSleep(){
		try{
			ci.refresh();
			Thread.sleep(ci.getSleepTime());	
		}catch(Exception ie){ie.printStackTrace();};
	}
}