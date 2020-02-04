

public class Triangle{
	public static void main(String[] args){	
		new Triangle(10);
	}

	public Triangle(int k){
		for(int i=0; i<k; i++){
			for(int j=i+1; j<k; j++)
				System.out.print(" ");
			for(int l=0; l<=i; l++)
				System.out.print("* ");
			System.out.println("");
		}
	}
}