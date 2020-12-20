package graph;

public class Main {

    public static void main(String[] args) {

        for(float p=0; p<1.1;p+=0.1f) {
			for(float q=0; q<1.1;q+=0.1f) {
				Heuristique heuristique1= new Heuristique1(p, q);
				System.out.print(heuristique1.red_sequence().size()+" ");
			}
			System.out.println();
        }

		for(float p=0; p<1.1;p+=0.1f) {
			for(float q=0; q<1.1;q+=0.1f) {
				Heuristique heuristique2= new Heuristique2(p, q);
				System.out.print(heuristique2.red_sequence().size()+" ");
			}
			System.out.println();
		}
    }

    
}
