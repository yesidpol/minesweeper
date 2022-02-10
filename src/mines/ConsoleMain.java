package mines;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleMain {

	public static void main(String[] args) {
		Game game = new Game(10, 10, 10);
		
		System.out.println(game.toString());

		int x, y;
		Scanner in = new Scanner(System.in);

		do {
			System.out.print("Enter x.y.[+]: ");
			
			String input = in.nextLine();

			if(input.equals("exit")) {
				break;
			}
			Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)(.?)");
			Matcher matcher = pattern.matcher(input);

			if(!matcher.find()) {
				System.out.println("Input wrong");
				continue;
			}
			x = Integer.parseInt(matcher.group(1));
			y = Integer.parseInt(matcher.group(2));
			
			boolean mark = input.length() > 3 && !matcher.group(3).isEmpty();

			if(x >= 0 && y >= 0)
				game.changeSquareStatus(new BoardPoint(x,y), mark);
			
			for(int k = 0; k<20;k++) System.out.println("");
			System.out.println(game.toString());

			if(game.getStatus() == GameStatus.LOST) {
				System.out.println("YOU LOST :'(");
				break;
			} else if(game.getStatus() == GameStatus.WIN) {	
				System.out.println("YOU WON!!!");
				break;
			}

		} while(true);
		
		in.close();
	}

}
