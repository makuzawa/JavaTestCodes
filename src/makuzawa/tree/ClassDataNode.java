package makuzawa.tree;

public class ClassDataNode {
	//public String name;
	public ClassData data;
	public boolean isLeaf=false;
	public boolean isPkgNode=false;
	public boolean isRoot=false;
	
	public ClassData getData() {
		return data;
	}

	@Override
	public String toString() {
		// TODO 自動生成されたメソッド・スタブ
		return getName();
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean isPkgNode() {
		return isPkgNode;
	}
	public void setPkgNode(boolean isPkgNode) {
		this.isPkgNode = isPkgNode;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public ClassDataNode(ClassData data) {
		this.data = data;
	}
	
	public String getName() {
		return data.getName();
	}
	

}
