package study.betwixt;

import java.util.ArrayList;
import java.util.List;


public  class  InfoBean {
	protected List<String> name = new ArrayList<String>();

    public List<String> getName() {
        return name;
    }
	public void setName(List<String> list) {
		this.name= list;
	}
    public void addName(String list) {
        this.name.add(list);
    }

}
