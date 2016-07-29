package base;

public class TLink extends Link{
	
	public TLink(String lid, String relType, String ID, String relatedID, String fileName){
		super(lid, relType,ID, relatedID, fileName);
	}
	
	public void printRelation(){
		System.out.println(lid + " : " + ID + " " + relType + " " + relatedID);
	}
}
