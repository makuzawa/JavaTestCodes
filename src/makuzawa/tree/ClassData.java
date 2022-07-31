package makuzawa.tree;

public class ClassData {
	public String name;
	public String path;
	
	public ClassData(String name) {
		super();
		this.name = name;
		this.path = name;
	}
	
	public ClassData(String name,String fullname) {
		super();
		this.name = name;
		this.path = fullname;
	}

	public String getPath() {
		return this.path;
	}
	public String getFullName() {
		return path+"."+name;
	}
	public String getName() {
		return name;
	}
}
