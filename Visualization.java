/**
 * I think this is the same thing Ben did, except I put the squares in the center of the screen
 * and put spaces between them.
 */


package practice;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;

public class Visualization extends Application implements EventHandler<ActionEvent>{
	static int[] array;
	int minWidth, backgroundNum;
	
	public static void main(String[] args){
		array = new int[]{50, 50, 50};
		
		//prompt for the array
/** MY PARSER METHOD DOESN'T REALLY WORK, SO WE CAN'T TAKE USER INPUT WITH THIS YET
		System.out.println("Enter an array of integers in this format: 1,7,4,9,2,3 below");
		Scanner reader = new Scanner(System.in);
		String s = reader.next()
		//array = parser(s);
		System.out.println(SimpleSort.arrayToString(array));
		System.out.println("Hi:");
**/		
		launch(args);
	}
	
	public static int[] parser(String a){
		int[] returned = new int[a.length()/2];
		int index = 0;
		while(index < returned.length){
			for(int i = 0; i < a.length(); i++){
				int nextComma = a.indexOf(",", i + 1);
				if(nextComma == -1){
					returned[index] = Integer.parseInt(a.substring(i));
				}
				else if(i == 0){
					returned[index] = Integer.parseInt(a.substring(i, nextComma));
					index++;
				}
				else if(a.substring(i, i+1).contains(",")){
					returned[index] = Integer.parseInt(a.substring(i+1,nextComma));
					index++;
				}
			}
		}
		
		return returned;
	}
	
	public void start(Stage primaryStage){
		Stage window;
		window = primaryStage;
		window.setTitle("Sort Visualization");
		window.setMinWidth(1000);
		window.setMinHeight(500);

		//change the window size to accommodate the array
		//(1) Add up the lengths of the boxes (the numbers in the array)
		int sum = 0;
		for(int i = 0; i < array.length; i++){
			sum += array[i];
		}
		//(2) Set the min width to sum + 20*(the number of boxes + 1) - 20 pixels for margins and the space between the boxes
		minWidth = sum + 20*(array.length + 1);
		if(minWidth > window.getMinWidth())
			window.setMinWidth(minWidth);
		
		//draw the boxes on top
		//group that carries a bunch of children(?)
		BorderPane layout = new BorderPane();
        //a canvas is an image that can be drawn on using a set of graphics commands provided by a GraphicsObject.
        Canvas canvas = new Canvas(1000, 500);
        //returns the GraphicsContext associated with this canvas.
        GraphicsContext gc = canvas.getGraphicsContext2D();

      	Random generator = new Random();
		backgroundNum = generator.nextInt(15)+1;
		backgroundNum += generator.nextInt(16);
        
        //setFill to black - 6, 4, 7, 8, 9, 10, 11, 12, 13, 18, 26, 29
      	int a = backgroundNum;
      	if(a == 6 ||a == 4 ||a == 7 ||a == 8 ||a == 9 ||a == 10 ||a == 11 ||a == 12 ||a == 13 ||a == 18 ||a == 29){
      		drawBlackShapes(gc, array);
      	}else{
      		drawWhiteShapes(gc, array);
      	}
      	
        layout.getChildren().add(canvas);
        Scene scene = new Scene(layout);
		//22 is the best backgroundNum. Next are: 24, 30, 5, and 13
		scene.getStylesheets().add("practice/Lind" + backgroundNum +".css");
		
		//Add music
		//create a menu
		Menu museMenu = new Menu("Music");

		MenuItem wing = new MenuItem("WingWing");
		wing.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				String musicFile = "Violin-WingWing.mp3";     

				Media sound = new Media(new File(musicFile).toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.play();
			}
					
		});	
		
		museMenu.getItems().addAll(wing);
		MenuBar mb = new MenuBar();
		mb.getMenus().addAll(museMenu);
		//add it to the top of the scene
		layout.setTop(mb);
		
        window.setScene(scene);
		window.show();
	}
	

	private void drawBlackShapes(GraphicsContext gc, int[] arr) {
		//gc.drawImage(new Image("1.gif"), 0, 0);
		int xPos = (int) (gc.getCanvas().getWidth()/2 - minWidth/2);
		for(int i = 0; i < arr.length; i++){
			//parameters: upper left corner x-coordinate, upper left corner y-coordinate, width, height
			gc.fillRect(xPos, gc.getCanvas().getHeight()/2 - arr[i]/2, arr[i], arr[i]);
			xPos += arr[i] + 20;
		}
		
	}
	
	private void drawWhiteShapes(GraphicsContext gc, int[] arr) {
		gc.setFill(Color.WHITE);
		//gc.drawImage(new Image("1.gif"), 0, 0);
		int xPos = (int) (gc.getCanvas().getWidth()/2 - minWidth/2);
		for(int i = 0; i < arr.length; i++){
			//parameters: upper left corner x-coordinate, upper left corner y-coordinate, width, height
			gc.fillRect(xPos, gc.getCanvas().getHeight()/2 - arr[i]/2, arr[i], arr[i]);
			xPos += arr[i] + 20;
		}
		
	}

	public void handle(ActionEvent event){}
}
