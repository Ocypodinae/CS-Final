package practice;

public class SimpleSort {
	public static void main(String[] args){
		int[] arr = {10, 20, 0, 8, 4, 3};
		System.out.println("BubbleSort: " + arrayToString(bubbleSort(arr)));
		System.out.println("InsertionSort: " + arrayToString(insertionSort(arr)));
		System.out.println("SelectionSort: " + arrayToString(selectionSort(arr)));
	}
	public static int[] bubbleSort(int[] arr){
		boolean inOrder = false;
		
		while(!inOrder){
			int numSwaps = 0;
			for(int i = 1; i < arr.length; i++){
				//compare cell to that before it
				if(arr[i] < arr[i - 1]){
					//swap cells if it's out of order
					arr = swap(arr, i, i-1);
					numSwaps++;
				}
			}
			if(numSwaps == 1)
				inOrder = true;
		}
		
		return arr;
	}
	
	public static int[] selectionSort(int[] arr){
		for(int i = 0; i < arr.length; i++){
			int min = Integer.MAX_VALUE;
			int minIndex = i;
			for(int j = i; j < arr.length; j++){
				if(arr[j] < min){
					min = arr[j];
					minIndex = j;
				}
			}
			
			arr = swap(arr, i , minIndex);
		}
		
		return arr;
	}
	
	public static int[] insertionSort(int[] arr){
		for(int i = 1; i < arr.length; i++){
			int current = arr[i];
			int insertIndex = i;
			//compare to all the values before it
			for(int j = 0;  j < i; j++){
				//iterate through until you find a value greater than the current value
				if(arr[j] > current){
					insertIndex = j;
				}
			}
			//shift everything and insert the current value into its rightful place
			arr = shiftAndInsert(arr, i, insertIndex);
		}
		
		return arr;
	}
	
	public static int[] swap(int[] array, int a, int b){
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
		return array;
	}
	
	public static int[] shiftAndInsert(int[] array, int currentIndex, int insertIndex){
		//Assumption: currentIndex > insertIndex
		int temp = array[currentIndex];
		for(int i = currentIndex; i > insertIndex; i--){
			array[i] = array[i - 1];
		}
		array[insertIndex] = temp;
		return array;
	}
	
	public static String arrayToString(int[] array){
		String a = "";
		for(int i = 0; i < array.length; i++){
			a += array[i] + ", ";
		}
		return a;
	}
}
