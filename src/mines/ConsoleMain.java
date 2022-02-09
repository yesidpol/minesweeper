package mines;

import java.util.Scanner;

public class ConsoleMain {

	public static void main(String[] args) {
		Game game = new Game(10, 10, 10);
		
		game.initialize();
		
		System.out.println(game.toString());

        int x, y;
        Scanner in = new Scanner(System.in);

		do {
			System.out.print("Entered x y [.] value: ");
			
	        String input = in.nextLine();
	        input = input.trim();
	        
	        String[] values = input.split(" +");
	        
	        if(values.length<2) break;
	        
	        x = Integer.parseInt(values[0]);
	        y = Integer.parseInt(values[1]);
	        
	        boolean mark = input.length() > 3 && values[2].equals(".");
	       
	        if(x >= 0 && y >= 0)
	        	game.changeSquareStatus(new BoardPoint(x,y), mark);
	        
	        for(int k = 0; k<20;k++) System.out.println("");
	        System.out.println(game.toString());

		} while(x >= 0 && y >= 0);
		in.close();
	}

}
