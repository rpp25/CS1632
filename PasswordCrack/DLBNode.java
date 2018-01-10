

public class DLBNode{

  public char data = ' ';
  public DLBNode child;
  public DLBNode sibling;
  public long time;
  
  public DLBNode(){
	  
  }
  
  public DLBNode(char c, DLBNode child, DLBNode sib){
    this.data = c;
    this.child = child;
    this.sibling = sib;
  }

  public DLBNode(char c, DLBNode child, DLBNode sib, long time){
	    this.data = c;
	    this.child = child;
	    this.sibling = sib;
	    this.time = time;
	  }

  
  public long getTime(){
	  return time;
  }
/*  public DLBNode newSibling(char c){
    DLBNode newNode = this;
    while(this.right != null){
    	newNode.right = new DLBNode(c);
    }
    return newNode;
  }

  public void setChar(char c){
    this.data = c;
  }*/
}
