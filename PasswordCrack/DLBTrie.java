import java.io.IOException;
import java.util.ArrayList;

public class DLBTrie{


  public DLBNode root = new DLBNode(' ', null, null);
  public boolean wordDone = false;
  public DLBTrie () {	  
  }

  public void addWord(String test){
	  String pass = test + "^";
	  char[] array = pass.toCharArray();
	  DLBNode node = root;
	  for (int i = 0; i < array.length; i++) {
			if (node.child != null) {
				node = node.child;
			} else {
				node.child = new DLBNode(array[i], null, null);
		
				node = node.child;
			}
			while (node.data != array[i]) {
		
				if (node.sibling != null) {
					node = node.sibling;
				} else {
					node.sibling = new DLBNode(array[i], null, null);
					node = node.sibling;
				}
			}
		}
  }
  
  public void addWordAndTime(String test, long time){
	  String pass = test + "^";
	  char[] array = pass.toCharArray();
	  DLBNode node = root;
	  for (int i = 0; i < array.length; i++) {
			if (node.child != null) {
				node = node.child;
			} else {
				node.child = new DLBNode(array[i], null, null);
		
				node = node.child;
			}
			while (node.data != array[i]) {
		
				if (node.sibling != null) {
					node = node.sibling;
				} else {
					node.sibling = new DLBNode(array[i], null, null);
					node = node.sibling;
				}
			}
		} 
	  node.time = System.nanoTime();
	  time = node.time;
  }
  
  public boolean search(String s) {
		DLBNode node = root.child;
		char[] array = s.toCharArray();
		for (int i = 0; i < array.length;) {
			if (node.data == array[i]) {
				node = node.child;
				i++;
			} else if (node.sibling != null) {
				node = node.sibling;
			} else if (node.sibling == null) {
				return false;
			}
		}

		return true;

	}
  

  public long searchAndTime(String s) {
	  DLBNode node = root.child;
		char[] array = s.toCharArray();
		for (int i = 0; i < array.length;) {
			if (node.data == array[i]) {
				node = node.child;
				i++;
			} else if (node.sibling != null) {
				node = node.sibling;
			} else if (node.sibling == null) {
				return 0;
			}
		}

		return node.getTime();
	}
  
  
}


