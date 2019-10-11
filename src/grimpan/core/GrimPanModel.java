package grimpan.core;

import grimpan.command.Command;
import grimpan.state.*;
import grimpan.svg.SVGGrimShape;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class GrimPanModel implements Observable {

	private volatile static GrimPanModel uniqueModelInstance;
	public GrimPanController control;

	private ShapeFactory sf = ShapeFactory.getInstance(this);

	public EditState editState = null;
	public EditState savedAddState = null;
	public final EditState STATE_REGULAR = new RegularBuilderState(this, sf);
	public final EditState STATE_OVAL = new OvalBuilderState(this, sf);
	public final EditState STATE_POLYGON = new PolygonBuilderState(this, sf);
	public final EditState STATE_LINE = new LineBuilderState(this, sf);
	public final EditState STATE_PENCIL = new PencilBuilderState(this, sf);
	public final EditState STATE_MOVE = new MoveBuilderState(this,sf);
	public final EditState STATE_LOADING = new LoadingState();

	private double shapeStrokeWidth = 10;
	private Color shapeStrokeColor = Color.BLACK;
	private boolean shapeStroke = true;
	private boolean shapeFill = false;
	private Color shapeFillColor = null;

	public ObservableList<SVGGrimShape> shapeList = null;

	private Point2D startMousePosition = null;
	private Point2D currMousePosition = null;
	private Point2D prevMousePosition = null;

	public SVGGrimShape curDrawShape = null;
	public ArrayList<Point2D> polygonPoints = null;
	private int selectedShapeIndex = -1;

	private int nPolygon = 3;

	private File saveFile = null;

	private double paneWidth = 0;
	private double paneHeight = 0;

	private Point2D movedPos = null;

	private ArrayList<InvalidationListener> listenerList = null;
	public Stack<Command> undoCommandStack = null;

	public PropertyManager grimpanPM = null;


	public static GrimPanModel getInstance(GrimPanController control) {
		if (uniqueModelInstance == null) {
			synchronized (GrimPanModel.class) {
				if (uniqueModelInstance == null) {
					uniqueModelInstance = new GrimPanModel(control);
				}
			}
		}
		return uniqueModelInstance;
	}
	private GrimPanModel(GrimPanController control){

		this.control = control;
		this.shapeList = FXCollections.observableArrayList();
		this.shapeStrokeColor = Color.BLACK;
		this.shapeFillColor = Color.TRANSPARENT;
		this.polygonPoints = new ArrayList<Point2D>();
		this.listenerList = new ArrayList<InvalidationListener>();

		this.setEditState(STATE_PENCIL);
		this.notifyListeners();

		this.undoCommandStack = new Stack<Command>();
		this.grimpanPM = new SimplePropertyManager("/grimpan.properties");
		this.shapeStrokeWidth = Double.parseDouble(grimpanPM.getPanProperties().getProperty("default.stroke.width"));
		this.shapeStrokeColor = Color.web(grimpanPM.getPanProperties().getProperty("default.stroke.color"));
		this.shapeFillColor = Color.web(grimpanPM.getPanProperties().getProperty("default.fill.color"));

	}
	@Override
	public void addListener(InvalidationListener li) {
		this.listenerList.add(li);

	}
	@Override
	public void removeListener(InvalidationListener li) {
		this.listenerList.remove(li);

	}
	public void notifyListeners() {
		for (InvalidationListener lis : this.listenerList) {
			lis.invalidated(this);
		}
	}

	public EditState getEditState() {
		return editState;
	}

	public void setEditState(EditState editState) {
		this.editState = editState;
		notifyListeners();
	}

	public Point2D getStartMousePosition() {
		return startMousePosition;
	}

	public void setStartMousePosition(Point2D mousePosition) {
		this.startMousePosition = mousePosition;
	}
	public Point2D getPrevMousePosition() {
		return prevMousePosition;
	}

	public void setPrevMousePosition(Point2D mousePosition) {
		this.prevMousePosition = mousePosition;
	}

	public Point2D getCurrMousePosition() {
		return currMousePosition;
	}

	public void setCurrMousePosition(Point2D clickedMousePosition) {
		this.currMousePosition = clickedMousePosition;
	}
	/**
	 * @return the saveFile
	 */
	public File getSaveFile() {
		return saveFile;
	}

	/**
	 * @param saveFile the saveFile to set
	 */
	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
		//mainFrame.setTitle("�׸��� - "+saveFile.getPath());
	}
	/**
	 * @return the nPolygon
	 */
	public int getNPolygon() {
		return nPolygon;
	}

	/**
	 * @param nPolygon the nPolygon to set
	 */
	public void setNPolygon(int nPolygon) {
		this.nPolygon = nPolygon;
	}

	/**
	 * @return the selectedShape
	 */
	public int getSelectedShapeIndex() {
		return selectedShapeIndex;
	}

	/**
	 * @param selectedShape the selectedShape to set
	 */
	public void setSelectedShapeIndex(int selIndex) {
		this.selectedShapeIndex = selIndex;
	}

	public void getSelectedShape(){
		int selIndex = -1;
		Shape shape = null;
		for (int i=this.shapeList.size()-1; i >= 0; --i){
			shape = this.shapeList.get(i).getShape();
			if (shape.contains(this.getStartMousePosition().getX(), this.getStartMousePosition().getY())){
				selIndex = i;
				break;
			}
		}
		if (selIndex != -1){
			Color scolor = (Color)shape.getStroke();
			Color fcolor = (Color)shape.getFill();
			if (shape.getStroke()!=Color.TRANSPARENT){
				shape.setStroke(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 0.5));
			}
			if (shape.getFill()!=Color.TRANSPARENT){
				shape.setFill(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 0.5));
			}
		}
		this.setSelectedShapeIndex(selIndex);
	}

	/**
	 * @return the shapeStrokeColor
	 */
	public Color getShapeStrokeColor() {
		return shapeStrokeColor;
	}

	/**
	 * @param shapeStrokeColor the shapeStrokeColor to set
	 */
	public void setShapeStrokeColor(Color shapeStrokeColor) {
		this.shapeStrokeColor = shapeStrokeColor;
	}

	/**
	 * @return the shapeFill
	 */
	public boolean isShapeFill() {
		return shapeFill;
	}

	/**
	 * @param shapeFill the shapeFill to set
	 */
	public void setShapeFill(boolean shapeFill) {
		this.shapeFill = shapeFill;
	}

	/**
	 * @return the shapeFillColor
	 */
	public Color getShapeFillColor() {
		return shapeFillColor;
	}

	/**
	 * @param shapeFillColor the shapeFillColor to set
	 */
	public void setShapeFillColor(Color shapeFillColor) {
		this.shapeFillColor = shapeFillColor;
	}

	/**
	 * @return the shapeStrokeWidth
	 */
	public double getShapeStrokeWidth() {
		return shapeStrokeWidth;
	}

	/**
	 * @param shapeStrokeWidth the shapeStrokeWidth to set
	 */
	public void setShapeStrokeWidth(double shapeStrokeWidth) {
		this.shapeStrokeWidth = shapeStrokeWidth;
	}

	public boolean isShapeStroke() {
		return shapeStroke;
	}

	public void setShapeStroke(boolean shapeStroke) {
		this.shapeStroke = shapeStroke;
	}
	public double getPaneWidth() {
		return paneWidth;
	}
	public void setPaneWidth(double paneWidth) {
		this.paneWidth = paneWidth;
		this.notifyListeners();
	}
	public double getPaneHeight() {
		return paneHeight;
	}
	public void setPaneHeight(double paneHeight) {
		this.paneHeight = paneHeight;
		this.notifyListeners();
	}
	public String getSizeText() {
		StringBuilder text = new StringBuilder("Size:");
		if (paneWidth==0 || paneHeight==0)
			return text.toString();

		text.append(String.valueOf((int)paneWidth));
		text.append('x');
		text.append(String.valueOf((int)paneHeight));

		return text.toString();
	}
	public Point2D getMovedPos() {
		return movedPos;
	}
	public void setMovedPos(Point2D movedPos) {
		this.movedPos = movedPos;
	}
}
