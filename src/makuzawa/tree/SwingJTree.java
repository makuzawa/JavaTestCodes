package makuzawa.tree;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class SwingJTree {
	private JTree tree;
	private JScrollPane sp;
	/**コンストラクタ*/
	public SwingJTree(DefaultMutableTreeNode root){
		tree = new JTree(root);		//サンプルモデルを持つツリーを生成
		tree.setEditable(false);	//ノード名編集不可
		sp = new JScrollPane(tree);	//スクロールペインに配置
	}
	/**main() */
	public static void main(String[] args){
    	String classFiles[] = {
    			"com.a.b.c.d.leaf1",
    			"com.a.b.c.d.leaf2",
    			"com.a.b.c.d.leaf3",
    			"com.a.leaf1",
    			"com.b.a.c.d.leaf1",
    			"com.b.a.c.d.leaf2",
     			"com.leaf1",
     			"com.a.b.c.leaf1",
     			"com.a.b.c.leaf2",
     			"com.a.b.leaf1",
    			"com.a.b.leaf2",
    	};
    	ArrayList<ClassData> dataList = new ArrayList<ClassData>();
    	for(String file : classFiles) {
    		dataList.add(new ClassData(file));
    	}
    	FileTreeNode treeNode = new FileTreeNode(dataList);
    	
		SwingJTree tree = new SwingJTree(treeNode.getRootNode());
		JFrame frame = new JFrame("JTreeTest1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,200);
		frame.getContentPane().add(tree.sp,"Center");
		frame.setVisible(true);
	}
}
