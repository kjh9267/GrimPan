package grimpan.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import grimpan.command.AddCommand;
import grimpan.command.Command;
import grimpan.command.MoveCommand;
import grimpan.svg.SVGGrimShape;
import grimpan.svg.SaxSVGPathParseHandler;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class GrimPanController {

	public static Thread thread = null;
	public GrimPanModel model = null;
	public GrimPanPaneView view = null;
	public boolean key = false;

	public GrimPanController(){		
		this.model = GrimPanModel.getInstance(this);
	}
	void openAction() {

		FileChooser fileChooser1 = new FileChooser();
		fileChooser1.setTitle("Open Saved GrimPan");
		fileChooser1.setInitialDirectory(new File(Utils.DEFAULT_DIR));
		fileChooser1.getExtensionFilters().add(new ExtensionFilter("SVG File (*.grimpan.svg)", "*.grimpan.svg", "*.SVG"));
		File selFile = fileChooser1.showOpenDialog(view.parentStage);

		if (selFile == null) return;

		model.setSaveFile(selFile);
		readShapeFromSVGSaveFile();
	}
	int countPathNode(){

		Scanner inscan = null;
		int pathNodeCount = 0;
		try {
			inscan = new Scanner(model.getSaveFile());
			String inline = "";
			while (inscan.hasNext()){
				inline = inscan.nextLine();
				if (inline.indexOf("path")>0)
					pathNodeCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println("path node count="+pathNodeCount);
		inscan.close();
		return pathNodeCount;
	}

	void readShapeFromSVGSaveFile() {

		model.setEditState(model.STATE_LOADING); // Loading�� �Է� ����
		
		SVGParseTask parseTask = new SVGParseTask();
		view.progressBar.progressProperty().unbind();
		view.progressBar.progressProperty().bind(parseTask.progressProperty());

		parseTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
				new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				ObservableList<SVGGrimShape> parseShapeList = parseTask.getValue();

				for (SVGGrimShape gsh:parseShapeList) {
					model.shapeList.add(gsh);
				}

				String fileName = model.getSaveFile().getName();
				view.parentStage.setTitle("GrimPan - "+fileName);
				view.drawPane.redraw();
				
				// Loading�� cancel�� ���
				if(key) {
					view.parentStage.setTitle("GrimPan");
					view.initDrawPane();
					key = false;
				}
				
				model.setEditState(model.STATE_PENCIL); // Loading �� pencil ���� ����
			}
		});

		view.initDrawPane();

		// Start the Task.
		thread = new Thread(parseTask);
		thread.start();	
	}
	
	// Loading�� cancel
	void cancel() {
		key = true;
		thread.interrupt();
	} 
	
	void saveAction() {
		if (model.getSaveFile()==null){
			model.setSaveFile(new File(Utils.DEFAULT_DIR+"noname.grimpan.svg"));
			view.parentStage.setTitle("GrimPan - "+Utils.DEFAULT_DIR+"noname.grimpan.svg");
		}
		File selFile = model.getSaveFile();
		saveGrimPanSVGShapes(selFile);	
	}

	void saveAsAction() {
		FileChooser fileChooser2 = new FileChooser();
		fileChooser2.setTitle("Save As ...");
		fileChooser2.setInitialDirectory(new File(Utils.DEFAULT_DIR));
		fileChooser2.getExtensionFilters().add(new ExtensionFilter("SVG File (*.grimpan.svg)", "*.grimpan.svg", "*.SVG"));
		File selFile = fileChooser2.showSaveDialog(view.parentStage);

		model.setSaveFile(selFile);
		view.parentStage.setTitle("GrimPan - "+selFile.getName());

		saveGrimPanSVGShapes(selFile);	

	}

	void saveGrimPanSVGShapes(File saveFile){
		PrintWriter svgout = null;
		try {
			svgout = new PrintWriter(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		svgout.println("<?xml version='1.0' encoding='utf-8' standalone='no'?>");
		//svgout.println("<!DOCTYPE grimpan.svg PUBLIC '-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'>");
		svgout.print("<grimpan.svg xmlns:grimpan.svg='http://www.w3.org/2000/svg' ");
		svgout.println("     xmlns='http://www.w3.org/2000/svg' ");
		svgout.print("width='"+view.getWidth()+"' ");
		svgout.print("height='"+view.getHeight()+"' ");
		svgout.println("overflow='visible' xml:space='preserve'>");
		for (SVGGrimShape gs:model.shapeList){
			svgout.println("    "+gs.getSVGShapeString());
		}
		svgout.println("</grimpan.svg>");
		svgout.close();
	}
	public void addShapeAction() {
		Command addCommand = new AddCommand(model, model.curDrawShape);
		model.undoCommandStack.push(addCommand);// save for undo
		addCommand.execute();
		model.notifyListeners();
	}

	public void moveShapeAction() {
		Command moveCommand = new MoveCommand(model, model.getMovedPos());
		model.undoCommandStack.push(moveCommand);// save for undo
		moveCommand.execute();
		model.notifyListeners();
	}
	public void undoAction() {
		if (model.undoCommandStack.isEmpty())
			return;

		Command comm = model.undoCommandStack.pop();
		comm.undo();

		model.notifyListeners();
	}

	public class SVGParseTask extends Task<ObservableList<SVGGrimShape>> implements Runnable {

		long totPathCount = 0;
		ObservableList<SVGGrimShape> gshapeList = null;

		SVGParseTask thisTask = this;

		public SVGParseTask() {
			totPathCount = countPathNode();
		}
		@Override
		protected ObservableList<SVGGrimShape> call() throws Exception {

			SaxSVGPathParseHandler saxTreeHandler = new SaxSVGPathParseHandler(this); 
			this.gshapeList = saxTreeHandler.gshapeList;
			this.gshapeList.addListener((ListChangeListener.Change<? extends SVGGrimShape> change) ->{
				while(change.next()){
					if(change.wasAdded()){
						System.out.println(gshapeList.size()+" "+ totPathCount);
						thisTask.updateProgress(gshapeList.size(), totPathCount);
					}
				}			
			});
			try {
				SAXParserFactory saxf = SAXParserFactory.newInstance();

				SAXParser saxParser = saxf.newSAXParser();
				saxParser.parse(new InputSource(new FileInputStream(model.getSaveFile())), saxTreeHandler);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			return saxTreeHandler.gshapeList;
		}

	}
}
