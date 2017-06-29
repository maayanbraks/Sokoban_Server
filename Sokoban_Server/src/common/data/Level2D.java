//2D is the basic view of game.

/**
* This class responsible to represent the level.
* @author Maayan & Eden
* @version 2D
*/

package common.data;

import java.beans.XMLDecoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;



public class Level2D implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Box> _boxes = new ArrayList<>();
	private ArrayList<Wall> _walls = new ArrayList<>();
	private ArrayList<Space> _spaces = new ArrayList<>();
	private ArrayList<TargetBox> _targetBoxes = new ArrayList<>();
	private ArrayList<Actor> _actors = new ArrayList<>();
	private int _height;//X
	private int _width;//Y
	private Item[][] _map;

	private int _counter;// count the steps

	////////////////////////////////////////////////
	private final String _id;// NEW

	/**
	 * C'TOR
	 */
	public Level2D() {
		this._boxes = new ArrayList<>();
		this._walls = new ArrayList<>();
		this._spaces = new ArrayList<>();
		this._targetBoxes = new ArrayList<>();
		this._actors = new ArrayList<>();

		this._height = 0;
		this._width = 0;
		_map = new Item[0][0];

		this._counter = 0;

		this._id = "0";
	}

	/**
	 * copy C'TOR
	 */
	public Level2D(Level2D lvl) {
		this._boxes = new ArrayList<>(lvl.getBoxes());
		this._walls = new ArrayList<>(lvl.getWalls());
		this._spaces = new ArrayList<>(lvl.getSpaces());
		this._targetBoxes = new ArrayList<>(lvl.getTargetBoxes());
		this._actors = new ArrayList<>(lvl.getActors());

		this._height = lvl._height;
		this._width = lvl._width;

		this.setMap(lvl._map, lvl._height, lvl._width);

		this._counter = lvl.getCounter();

		this._id = lvl.getId();
	}

	/**
	 * C'TOR
	 */
	public Level2D(int height, int width, Item[][] map, String id) {
		this._height = height;
		this._width = width;
		this._map = new Item[height][width];

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				this._map[i][j] = map[i][j];

		this._id = id;
	}

	/**
	 * C'TOR
	 */
	public Level2D(int height, int width, String id) {
		this._height = height;
		this._width = width;
		this._map = new Item[height][width];

		this._id = id;
	}

	/**
	 * This functions add Items to arrayLists
	 */
	public void addActorToArray(Item itm) {
		_actors.add((Actor) itm);
	}

	public void addBoxToArray(Item itm) {
		_boxes.add((Box) itm);
	}

	public int getCounter() {
		return _counter;
	}

	public void setCounter(int counter) {
		this._counter = counter;
	}

	public void addTargetBoxToArray(Item itm) {
		_targetBoxes.add((TargetBox) itm);
	}

	public void addWallToArray(Item itm) {
		_walls.add((Wall) itm);
	}

	public void addSpaceToArray(Space itm) {
		_spaces.add((Space) itm);
	}

	/**
	 * Getters and Setters
	 */
	public ArrayList<Box> getBoxes() {
		return _boxes;
	}

	public void setBoxes(ArrayList<Box> boxes) {
		this._boxes = boxes;
	}

	public ArrayList<Wall> getWalls() {
		return _walls;
	}

	public void setWalls(ArrayList<Wall> walls) {
		this._walls = walls;
	}

	public ArrayList<Space> getSpaces() {
		return _spaces;
	}

	public void setSpaces(ArrayList<Space> spaces) {
		this._spaces = spaces;
	}

	public ArrayList<TargetBox> getTargetBoxes() {
		return _targetBoxes;
	}

	public void setTargetBoxes(ArrayList<TargetBox> targetBoxes) {
		this._targetBoxes = targetBoxes;
	}

	public ArrayList<Actor> getActors() {
		return _actors;
	}

	public void setActors(ArrayList<Actor> actors) {
		Collections.copy(this._actors, actors);
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		this._height = height;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		this._width = width;
	}

	public Item[][] getMap() {
		return _map;
	}

	public void setMap(Item[][] map, int height, int width) {
		_map=new Item [height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this._map[i][j] = map[i][j];
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////

	public String getId() {
		return this._id;
	}

	/**
	 * This function change item in specific position
	 */
	public void setItemInPlace(Item itm, Position2D pos) {
		this._map[pos.getX()][pos.getY()] = itm;
	}

	/**
	 * This function return item in specific position
	 */
	public Item getItemInPlace(Position2D pos) {
		if(pos.getX()<_height && pos.getY()<_width && pos.getX()>=0 && pos.getY()>=0)
			return this._map[pos.getX()][pos.getY()];
		return null;
	}

	public char[][] toChar() {
		char[][] charMap = new char[_height][_width];

		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				charMap[i][j] = _map[i][j].getCh();
			}
		}

		return charMap;

	}

	public XMLDecoder XMLReading(XMLDecoder XC) {
		_map = new Item[_height][_width];
		for (int i = 0; i < _height; i++)
			for (int j = 0; j < _width; j++)
				_map[i][j] = (Item) XC.readObject();
		return XC;
	}

	public boolean isOnMap(Position2D pos){
		if( pos.getX()<0 || pos.getY()<0 || pos.getX()>=_height || pos.getY()>=_width)
			return false;
		else
			return true;
	}

	@Override
	public String toString() {
		String str="";
		str+=_height + "\n";
		str+=_width + "\n";
		for(int i=0;i<_height;i++){
			for(int j=0;j<_width;j++){
				str+=_map[i][j].getCh();
			}
			str+="\n";
		}
		str+=_id;
		return str;
	}

}
