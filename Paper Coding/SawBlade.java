
public class SawBlade{
	
	public SawBlade(int k){
		for(int i=0; i<k; i++){
			for(int j=0; j<=i; j++)
				blade(i, k, j);
			System.out.println("");
		}
	}

	private void blade(int level, int k, int j){
		for(int i=0; i<=level-j; i++)
			System.out.print("*");

		for(int i=1; i<k-level; i++)
			System.out.print(" ");
	}


	public static void main(String[] args){
		new SawBlade(6);
	}
}