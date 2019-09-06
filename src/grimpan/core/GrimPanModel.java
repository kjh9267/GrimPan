package grimpan.core;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import grimpan.command.Command;
import grimpan.shape.DecorateShapeBuilder;
import grimpan.shape.DeleteShapeBuilder;
import grimpan.shape.IGrimShape;
import grimpan.shape.LineShapeBuilder;
import grimpan.shape.MoveShapeBuilder;
import grimpan.shape.OvalShapeBuilder;
import grimpan.shape.PencilShapeBuilder;
import grimpan.shape.PolygonShapeBuilder;
import grimpan.shape.RegularShapeBuilder;
import grimpan.shape.ShapeBuilder;

public class GrimPanModel {
	
	private volatile static GrimPanModel uniqueModelInstance;
	
	private GrimPanFrameView frameView = null;
	private GrimPanController controller = null;
	
	private String defaultDir = "/home/cskim/temp/";
	
	private int editState = Util.SHAPE_LINE;

	public final ShapeBuilder[] SHAPE_BUILDERS = {
		new RegularShapeBuilder(this),
		new OvalShapeBuilder(this),
		new PolygonShapeBuilder(this),
		new LineShapeBuilder(this),
		new PencilShapeBuilder(this),
		new MoveShapeBuilder(this),
		new DeleteShapeBuilder(this),
		new DecorateShapeBuilder(this),
		new DecorateShapeBuilder(this),
		new DecorateShapeBuilder(this),
		new DecorateShapeBuilder(this),
	};
	public ShapeBuilder sb = null;
	
	private float shapeStrokeWidth = 1f;
	private Color shapeStrokeColor = null;
	private boolean shapeFill = false;
	private Color shapeFillColor = null;
	
	public ArrayList<IGrimShape> shapeList = null;
	
	private Point mousePosition = null;
	private Point clickedMousePosition = null;
	private Point lastMousePosition = null;
	
	public Shape curDrawShape = null;
	public ArrayList<Point> polygonPoints = null;
	private int selectedShapeIndex = -1;
	private IGrimShape savedPositionShape = null;
	
	private int nPolygon = 3;
	
	private File saveFile = null;
	private File recoverFile = null;
	public Stack<Command> undoCommandStack = null;
	
	private GrimPanModel(){
		this.shapeList = new ArrayList<IGrimShape>();
		this.shapeStrokeColor = Color.BLACK;
		this.shapeFillColor = null;
		this.polygonPoints = new ArrayList<Point>();
		this.recoverFile = new File(defaultDir+"noname.rcv");
		this.undoCommandStack = new Stack<Command>();
	}
	public static GrimPanModel getInstance() {
		if (uniqueModelInstance == null) {
			synchronized (GrimPanModel.class) {
				if (uniqueModelInstance == null) {
					uniqueModelInstance = new GrimPanModel();
				}
			}
		}
		return uniqueModelInstance;
	}

	/**
	 * @return the mainFrame
	 */
	public GrimPanFrameView getFrameView() {
		return frameView;
	}
	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setFrameView(GrimPanFrameView mainFrame) {
		this.frameView = mainFrame;
	}
	public int getEditState() {
		return editState;
	}

	public void setEditState(int editState) {
		this.editState = editState;
		switch (editState) {
		case Util.EDIT_MOVE: 
			frameView.modeLBL.setText(String.format("Mode: %s  ", "�̵� "));
			break;
		case Util.DECO_GLASS: 
		case Util.DECO_TEX: 
		case Util.DECO_BALL: 
		case Util.DECO_TRANS: 
			frameView.modeLBL.setText(String.format("Mode: %s  ", "��� "));
			break;
		default: 
			frameView.modeLBL.setText(String.format("Mode: %s  ", "�߰� "));
			frameView.shapeLbl.setText(String.format("Shape: %s  ", Util.SHAPE_NAME[this.getEditState()]));
		}
		this.sb = SHAPE_BUILDERS[this.getEditState()];
	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
	}
	public Point getLastMousePosition() {
		return lastMousePosition;
	}

	public void setLastMousePosition(Point mousePosition) {
		this.lastMousePosition = mousePosition;
	}

	public Point getClickedMousePosition() {
		return clickedMousePosition;
	}

	public void setClickedMousePosition(Point clickedMousePosition) {
		this.clickedMousePosition = clickedMousePosition;
	}
	public void saveGrimPanData(File saveFile){
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(new FileOutputStream(saveFile));
			output.writeObject(this.shapeList);
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		}
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
		frameView.setTitle("�׸��� - "+saveFile.getPath());
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
	 * @param selectedShapeIndex the selectedShape to set
	 */
	public void setSelectedShapeIndex(int selectedShapeIndex) {
		this.selectedShapeIndex = selectedShapeIndex;
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
		if (this.shapeFill) {
			if (shapeFillColor==null)
				this.shapeFillColor = Color.WHITE;
		}
		else {
			this.shapeFillColor = null;
		}
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
	public float getShapeStrokeWidth() {
		return shapeStrokeWidth;
	}

	/**
	 * @param shapeStrokeWidth the shapeStrokeWidth to set
	 */
	public void setShapeStrokeWidth(float shapeStrokeWidth) {
		this.shapeStrokeWidth = shapeStrokeWidth;
		if (shapeStrokeWidth==0f) {
			shapeStrokeColor = null;
		}
	}
	/**
	 * @return the defaultDir
	 */
	public String getDefaultDir() {
		return defaultDir;
	}
	/**
	 * @param defaultDir the defaultDir to set
	 */
	public void setDefaultDir(String defaultDir) {
		this.defaultDir = defaultDir;
	}
	/**
	 * @return the controller
	 */
	public GrimPanController getController() {
		return controller;
	}
	/**
	 * @param controller the controller to set
	 */
	public void setController(GrimPanController controller) {
		this.controller = controller;
	}
	/**
	 * @return the recoverFile
	 */
	public File getRecoverFile() {
		return recoverFile;
	}
	/**
	 * @param recoverFile the recoverFile to set
	 */
	public void setRecoverFile(File recoverFile) {
		this.recoverFile = recoverFile;
	}
	/**
	 * @param grimShape
	 */
	public void setSavedPositionShape(IGrimShape grimShape) {
		savedPositionShape = grimShape.clone();
	}
	public IGrimShape getSavedPositionShape() {
		return savedPositionShape;		
	}
	
}
