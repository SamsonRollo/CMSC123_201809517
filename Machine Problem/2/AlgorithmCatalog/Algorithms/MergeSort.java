package Algorithms;

import UI.CatalogItem;

public class MergeSort implements AlgorithmItem{

	int[] inputs;
	private CatalogItem ci;

	public MergeSort(CatalogItem ci, int[] inputs){
		this.inputs = inputs;
		this.ci = ci;
		visualSleep();
	}

	public void run(){
		sort(inputs);
		ci.popMessage("Algorithm Done", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	private void sort(int[] inputs){
		if(inputs.length>1){
			int mid = inputs.length/2;
			int[] b = java.util.Arrays.copyOfRange(inputs, 0, mid);
			int[] c = java.util.Arrays.copyOfRange(inputs, mid, inputs.length);
			sort(b);
			sort(c);
			merge(inputs, b, c);
		}
	}

	private void merge(int[] a, int[] b, int[] c){
		int i = 0, j = 0, k = 0;

		while(i<b.length && j<c.length){
			if(b[i]<=c[j]){
				a[k] = b[i];
				i++;
			}else{
				a[k] = c[j];
				j++;
			}
			k++;
			visualSleep();
		}

		if(i==b.length){
			for(;j<c.length;j++, k++)
				a[k] = c[j];
		}else{
			for(;i<b.length;i++, k++)
				a[k] = b[i];
		}
		visualSleep();
	}

	private void visualSleep(){
		try{
			ci.refresh();
			Thread.sleep(ci.getSleepTime());	
		}catch(Exception ie){ie.printStackTrace();};
	}
}