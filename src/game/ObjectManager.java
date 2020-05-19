package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ObjectManager {
	ArrayList<GameObject> objectList;
	
	public ObjectManager() {
		objectList = new ArrayList<GameObject>();
	}
	public void addObject(GameObject gameObject) {
		objectList.add(gameObject);
	}
	public void removeObject(GameObject gameObject) {
		objectList.remove(gameObject);
	}
	public void tick() {
		for(int i=0;i<objectList.size();i++) {
			objectList.get(i).tick();
		}
	}
	
	public void render(Graphics2D g) {
		for(int i=0;i<objectList.size();i++) {
			objectList.get(i).render(g);
		}
	}
}
