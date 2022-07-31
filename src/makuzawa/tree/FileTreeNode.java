package makuzawa.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;

public class FileTreeNode {

	DefaultMutableTreeNode root;
	LinkedHashMap<String,LinkedList<ClassData>> map;

	public ArrayList<ClassData> listData;
	public FileTreeNode(ArrayList<ClassData> listData) {
		this.listData = new ArrayList<ClassData>(listData);
		root = parseClassDataList();
		map = this.createMap();

		//JTree tree = new JTree(root); 
	}

	public DefaultMutableTreeNode parseClassDataList() {
		ClassData rootData = new ClassData("+");
		ClassDataNode rootDataNode = new ClassDataNode(rootData);
		rootDataNode.setRoot(true);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDataNode);

		for(ClassData file : listData) {
			Pattern pattern = Pattern.compile("(.*\\.)(.*?)$");
			Matcher matcher = pattern.matcher(file.getName());
			DefaultMutableTreeNode startNode = root;
			String leaf="";
			String path="";

			if(matcher.find() && matcher.groupCount() == 2) {

				path = matcher.group(1);
				leaf = matcher.group(2);

				if(path.endsWith(".")) {
					path = path.substring(0,path.length()-1);
				}
				String pathsplit[] = path.split("\\.");

				for(String pkgpath:pathsplit) { 
					startNode = getNode(startNode,pkgpath);
				}

			} else {
				leaf = file.getName();

			}

			ClassData leafData = new ClassData(leaf,path);
			ClassDataNode leafDataNode = new ClassDataNode(leafData);
			leafDataNode.setLeaf(true);
			DefaultMutableTreeNode leafDataNodeTree = new DefaultMutableTreeNode(leafDataNode);
			startNode.add(leafDataNodeTree);
		}

		return root;
	}

	private DefaultMutableTreeNode getNode(DefaultMutableTreeNode node,String path) {

		Enumeration enumnode = node.breadthFirstEnumeration();//幅優先探索
		int startDepth = node.getLevel();
		int targetDepth = startDepth+1;
		int currentDepth = startDepth;

		DefaultMutableTreeNode returnNode=null;
		DefaultMutableTreeNode curNode=null;

		while (enumnode.hasMoreElements() ) {
			if(currentDepth <= targetDepth) {
				curNode = (DefaultMutableTreeNode) enumnode.nextElement();

				if(curNode != null ) {
					currentDepth = curNode.getLevel();
					if(currentDepth == targetDepth) {
						ClassDataNode curNodeObject = (ClassDataNode) curNode.getUserObject();
						if(path.equals(curNodeObject.getName())) {
							returnNode=curNode;
							break;
						}
					}
				}
			} else {
				break;
			}
		}

		if(returnNode == null) {
			ClassData pkg = new ClassData(path);
			ClassDataNode pkgNode = new ClassDataNode(pkg);
			pkgNode.setPkgNode(true);
			returnNode = new DefaultMutableTreeNode(pkgNode);
			node.add(returnNode);
		}

		return returnNode;
	}


	public DefaultMutableTreeNode getRootNode() {
		return root;
	}

	public LinkedHashMap<String,LinkedList<ClassData>> getLeafMap() {
		return map;
	}



	public LinkedHashMap<String,LinkedList<ClassData>> createMap() {
		Enumeration enumnode = root.depthFirstEnumeration();//深さ優先探索;
		LinkedList<ClassData> list = new LinkedList<ClassData>();
		LinkedHashMap<String,LinkedList<ClassData>> linkedMap = 
				new LinkedHashMap<String,LinkedList<ClassData>>();

		DefaultMutableTreeNode beforeNode=null;

		/*最初の葉を探す*/
		while (enumnode.hasMoreElements() ) {
			DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) enumnode.nextElement();

			if(curNode.isLeaf()) {
				ClassDataNode curNodeObject = (ClassDataNode) curNode.getUserObject();
				if(curNodeObject.isLeaf) {
					beforeNode = curNode;
					list.add(curNodeObject.getData());
					break;
				}
			}
		}

		//葉が1つも見つからない場合はおかしいのでnull終了
		if(beforeNode == null) {
			return null;
		}

		/* 深さ優先探索に基づき兄弟から探す*/
		ClassDataNode curNodeObject=null;
		while (enumnode.hasMoreElements() ) {
			DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) enumnode.nextElement();

			if(curNode.isLeaf()) {

				curNodeObject = (ClassDataNode) curNode.getUserObject();
				if(curNodeObject.isLeaf) {

					if(beforeNode.getLevel() == curNode.getLevel()) {
						list.add(curNodeObject.getData());
					} else {
						ClassDataNode beforeNodeObject = (ClassDataNode) beforeNode.getUserObject();
						linkedMap.put(beforeNodeObject.getData().getPath(), list);
						list = new LinkedList<ClassData>();
						beforeNode = curNode;
						list.add(curNodeObject.getData());

					} 
				}
			}
		}

		/*最後の葉を付ける*/
		if(curNodeObject != null) {
			String key = curNodeObject.getData().getPath();
			if(!linkedMap.containsKey(key)) {
				linkedMap.put(key, list);
			}
		}

		return linkedMap;
	}

	public void printChildren() {

		//		List<String> reverseOrderedKeys = new ArrayList<String>(map.keySet());
		//		Collections.reverse(reverseOrderedKeys);
		//		for (String key : reverseOrderedKeys) {
		//		    LinkedList<ClassData> values = map.get(key);
		//		    System.out.println("group:"+key);
		//			for(ClassData data : values) {
		//				System.out.println(" " + data.getName());
		//			}
		//		}	

		for(Entry<String,LinkedList<ClassData>> entry : map.entrySet()){
			System.out.println("group:"+entry.getKey());
			for(ClassData data : entry.getValue()) {
				System.out.println(" " + data.getName());
			}
		}


	}

	//	class NodeInterface {
	//
	//
	//		public DefaultMutableTreeNode curNode;
	//		public DefaultMutableTreeNode beforeNode;
	//		public LinkedList<ClassData> list = new LinkedList<ClassData>();
	//		public LinkedHashMap<String,LinkedList<ClassData>> linkedMap =new LinkedHashMap<String,LinkedList<ClassData>>();
	//		public boolean continueFlag=true;
	//
	//		public void setBeforeNode(DefaultMutableTreeNode beforeNode) {
	//			this.beforeNode = beforeNode;
	//		}
	//		public void setContinue(boolean flag) {
	//			continueFlag = flag;
	//		}
	//		public DefaultMutableTreeNode getCurNode() {
	//			return curNode;
	//		}
	//		public void setCurNode(DefaultMutableTreeNode curNode) {
	//			this.curNode = curNode;
	//		}
	//		public LinkedList<ClassData> getList() {
	//			return list;
	//		}
	//		public void setList(LinkedList<ClassData> list) {
	//			this.list = list;
	//		}
	//		public LinkedHashMap<String, LinkedList<ClassData>> getLinkedMap() {
	//			return linkedMap;
	//		}
	//		public void setLinkedMap(LinkedHashMap<String, LinkedList<ClassData>> linkedMap) {
	//			this.linkedMap = linkedMap;
	//		}
	//		public DefaultMutableTreeNode getBeforeNode() {
	//			return beforeNode;
	//		}
	//		public boolean isContinue () {
	//			return continueFlag;
	//		}
	//
	//		public ClassDataNode getCurNodeObject() {
	//			return (ClassDataNode) curNode.getUserObject();
	//		}
	//
	//		public ClassDataNode getBeforeNodeObject() {
	//			return (ClassDataNode) beforeNode.getUserObject();
	//		}
	//	}
	//
	//
	//	public Consumer<NodeInterface> searchFirst = (ni) -> {
	//		ClassDataNode curNodeObject = ni.getCurNodeObject();
	//		ni.getList().add(curNodeObject.getData());
	//		ni.setBeforeNode(ni.getCurNode());
	//		ni.setContinue(false);
	//
	//
	//	};
	//
	//	public Consumer<NodeInterface> search = (ni) -> {
	//		DefaultMutableTreeNode beforeNode = ni.getBeforeNode();
	//		DefaultMutableTreeNode curNode = ni.getCurNode();
	//		LinkedList<ClassData> list = ni.getList();
	//
	//		ClassDataNode curNodeObject = ni.getCurNodeObject();
	//		if(beforeNode.getLevel() == curNode.getLevel()) {
	//
	//
	//			list.add(curNodeObject.getData());
	//		} else {
	//			ClassDataNode beforeNodeObject = ni.getBeforeNodeObject();
	//			ni.getLinkedMap().put(beforeNodeObject.getData().getPath(), list);
	//			list = new LinkedList<ClassData>();
	//			list.add(curNodeObject.getData());
	//			ni.setList(list);
	//			ni.setBeforeNode(curNode);
	//
	//
	//		} 
	//
	//	};
	//
	//	public void treeParse(Enumeration enumnode,NodeInterface ni,Consumer<NodeInterface> func) {
	//		while (enumnode.hasMoreElements() && ni.isContinue()) {
	//			DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) enumnode.nextElement();
	//
	//			if(curNode.isLeaf()) {
	//				ClassDataNode curNodeObject = (ClassDataNode) curNode.getUserObject();
	//
	//				ni.setCurNode(curNode);
	//				if(curNodeObject.isLeaf) {
	//					func.accept(ni);
	//
	//				}
	//
	//			}
	//		}
	//	}
	//	public void printChildren2() {
	//		Enumeration enumnode = root.depthFirstEnumeration();//.breadthFirstEnumeration();
	//
	//
	//		NodeInterface ni = new NodeInterface();	
	//
	//		treeParse(enumnode,ni,searchFirst);
	//		ni.setContinue(true);
	//		treeParse(enumnode,ni,search);
	//
	//		for(Entry<String,LinkedList<ClassData>> entry : ni.getLinkedMap().entrySet()){
	//			System.out.println("group:"+entry.getKey());
	//			for(ClassData data : entry.getValue()) {
	//				System.out.println(data.getFullName());
	//			}
	//		}
	//
	//	}


}
