package practice;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;

public class BubbleSortVisualization extends Application implements EventHandler<ActionEvent>{
	static Stage window;
	static int[] array;
	static double height;
	static double width;
	static int backgroundNum;
	static Button start, next;
	static boolean inOrder;

	public static void main(String[] args) {
		inOrder = false;
		array = new int[]{50, 50, 50};
		launch(args);
	}
	
	public void start(Stage primaryStage){
		//size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//width = screenSize.getWidth() - 10;
		//height = screenSize.getHeight() - 30;
		width = 1000;
		height = 500;
		
		
		//set the size of the stage
		window = primaryStage;
		window.setTitle("Bubble Sort Visualization");
		window.setMinWidth(width);
		window.setMinHeight(height);
		
		
		//choose a background
		Random generator = new Random();
      	backgroundNum = generator.nextInt(30)+1;
      	//Lind16.css never seems to work, no matter how I change it, so I decided that it's cursed.
      	if(backgroundNum == 16){
      		backgroundNum = 22;
      	}
      	
      	
      	//Make a menu
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
        
        
        //add a "Start" Button
        start = new Button("Start Sort");
        start.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e){
        		bubbleSort();
        	}
        });
		
        
		//create a layout
		BorderPane layout = new BorderPane();
		layout.setTop(mb);
		layout.setCenter(start);
		
		//make a scene 
		Scene scene = new Scene(layout);
		//22 is the best backgroundNum. Next are: 24, 30, 5, and 13
        scene.getStylesheets().add("practice/Lind" + backgroundNum +".css");
        window.setScene(scene);
        
        //display the window
        window.show();
	}
	
	public static void updateWindow(int[] arr, int[] toSwap){
		//create a new window
		final Stage window2 = new Stage();
		window2.setTitle("Bubble Sort Visualization");
		window2.setMinWidth(width);
		window2.setMinHeight(height);
		
		//choose a background image
		Random generator = new Random();
      	backgroundNum = generator.nextInt(30)+1;
      	//Lind16.css never seems to work, no matter how I change it, so I decided that it's cursed.
      	if(backgroundNum == 16){
      		backgroundNum = 22;
      	}
		
      	//draw the array on top
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
      	//setFill to black - 6, 4, 7, 8, 9, 10, 11, 12, 13, 18, 26, 29
      	int a = backgroundNum;
      	if(a == 6 ||a == 4 ||a == 7 ||a == 8 ||a == 9 ||a == 10 ||a == 11 ||a == 12 ||a == 13 ||a == 18 ||a == 29){
      		drawBlackShapes(gc, arr, toSwap);
      	}else{
      		drawWhiteShapes(gc, arr, toSwap);
      	}
      	
        //add a "Next" Button
        next = new Button("Next");
        next.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e){
        		window2.close();
        	}
        });

      	//set up the layout
		BorderPane layout = new BorderPane();
        layout.getChildren().add(canvas);
        layout.setBottom(next);
        
        
        Scene scene = new Scene(layout);
        //22 is the best backgroundNum. Next are: 24, 30, 5, and 13
        scene.getStylesheets().add("practice/Lind" + backgroundNum +".css");
        window2.setScene(scene);
        
        //display the new window
        window2.show();
	}
	
	private static void drawBlackShapes(GraphicsContext gc, int[] arr, int[] a) {
		int arrayLength = 20*arr.length + 20;
		for(int i = 0; i <arr.length; i++){
			arrayLength += arr[i];
		}
		int xPos = (int) (gc.getCanvas().getWidth()/2 - arrayLength/2 + 20);
		for(int i = 0; i < arr.length; i++){
			gc.setFill(Color.BLACK);
			if(i == a[0] || i == a[1])
				gc.setFill(Color.RED);
		
			//parameters: upper left corner x-coordinate, upper left corner y-coordinate, width, height
			gc.fillRect(xPos, gc.getCanvas().getHeight()/2 - arr[i]/2, arr[i], arr[i]);
			xPos += arr[i] + 20;
		}
		
	}
	
	private static void drawWhiteShapes(GraphicsContext gc, int[] arr, int[] a) {
		int arrayLength = 20*arr.length + 20;
		for(int i = 0; i <arr.length; i++){
			arrayLength += arr[i];
		}
		int xPos = (int) (gc.getCanvas().getWidth()/2 - arrayLength/2 + 20);
		for(int i = 0; i < arr.length; i++){
			gc.setFill(Color.WHITE);
			if(i == a[0] || i == a[1])
				gc.setFill(Color.RED);
			
			//parameters: upper left corner x-coordinate, upper left corner y-coordinate, width, height
			gc.fillRect(xPos, gc.getCanvas().getHeight()/2 - arr[i]/2, arr[i], arr[i]);
			xPos += arr[i] + 20;
		}
		
	}
	
	
	//______________________________________________________________________________________________
	public static void bubbleSort(){
		ArrayList<Integer[]> stages = new ArrayList<Integer[]>();
		ArrayList<Integer[]> toSwap = new ArrayList<Integer[]>();
		stages.add(0, copyOf(array));
		while(!inOrder){
			int numSwaps = 0;
			for(int i = 1; i < array.length; i++){
				//compare cell to that before it
				if(array[i] < array[i - 1]){
					int[] swapped = {i, i -1};
					toSwap.add(0, copyOf(swapped));
					//swap cells if it's out of order
					array = swap(array, i, i-1);
					numSwaps++;
					stages.add(0, copyOf(array));
				}
			}
			if(numSwaps == 1){
				inOrder = true;
			}
		}
		
		//first window - last window that user sees. Completely sorted window.
		updateWindow(IntegerToInt(stages.get(0)), new int[]{-1, -1});
		
		for(int i = 1; i < stages.size(); i++){
			updateWindow(IntegerToInt(stages.get(i)), IntegerToInt(toSwap.get(i - 1)));
		}
	}
	

	private static Integer[] copyOf(int[] array2) {
		Integer[] copy = new Integer[array2.length];
		for(int i = 0; i < copy.length; i++){
			copy[i] = array2[i];
		}
		return copy;
	}
	
	private static int[] IntegerToInt(Integer[] arr){
		int[] intArr = new int[arr.length];
		for(int i = 0; i < arr.length; i++){
			intArr[i] = arr[i].intValue();
		}
		return intArr;
	}
	
	public static int[] swap(int[] array, int a, int b){
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
		return array;
	}

	public void handle(ActionEvent arg0) {}

}
