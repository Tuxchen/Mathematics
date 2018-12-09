public class Lottoziehung {
	
	public static int[] minSort(int[] zahlen) {
		
		for(int i = 0; i < zahlen.length-1; i++) {
			int minimum = i;
			for(int j = i+1; j < zahlen.length; j++) {
				if(zahlen[minimum] > zahlen[j]) {
					minimum = j;
				}
			}
			int helper = zahlen[minimum];
			zahlen[minimum] = zahlen[i];
			zahlen[i] = helper;
		}
		
		return zahlen;
	}
	
	public static boolean isTwice(int[] zahlen) {
		boolean twice = false;
		
		for(int i = 0; i < zahlen.length; i++) {
			for(int j = i+1; j < zahlen.length; j++) {
				if(zahlen[i] == zahlen[j]) {
					twice = true;
				}
			}
		}
		
		return twice;
	}
	
	public static void main (String[] args) {
		
		int[] lottozahlen = new int[6];
		
		do {
			for(int i = 0; i < lottozahlen.length; i++) {
				lottozahlen[i] = (int) ((Math.random() * 49) + 1);
			}
		} while(isTwice(lottozahlen));
		
				
		System.out.println("Vor dem sortieren: ");
		for(int i : lottozahlen) {
			System.out.print(i + " ");
		}
		
		lottozahlen = minSort(lottozahlen);
		System.out.println("\nMit Minsort sortiert: ");
		for(int i : lottozahlen)
			System.out.print(i + " ");
	}
}