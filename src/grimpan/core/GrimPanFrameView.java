package grimpan.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.event.ChangeListener;

import grimpan.shape.GrimShape;

import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.border.EtchedBorder;


public class GrimPanFrameView extends JFrame {

	private static final long serialVersionUID = 1L;
	private GrimPanController controller = null;
	private GrimPanModel model = null;
	private GrimPanFrameView thisClass = this;
	private DrawPanelView drawPanelView = null;

	private JPanel contentPane;
	
	public JCheckBoxMenuItem jcmiFill = null;
	JFileChooser jFileChooser1 = null;
	JFileChooser jFileChooser2 = null;

	JMenuBar panMenuBar;	
	JMenu fileMenu;
	JMenuItem jmiNew;
	JMenuItem jmiOpen;
	JMenuItem jmiSave;
	JMenuItem jmiSaveAs;
	JMenuItem jmiSaveAsSvg;
	JMenuItem jmiExit;
	JMenu shapeMenu;
	JRadioButtonMenuItem rdbtnmntmLine;
	JRadioButtonMenuItem rdbtnmntmPen;	
	JRadioButtonMenuItem rdbtnmntmPoly;
	JRadioButtonMenuItem rdbtnmntmRegular;
	JRadioButtonMenuItem rdbtnmntmOval;
	JMenu editMenu;	
	JMenuItem jmiRemove;
	JMenuItem jmiMove;
	JMenu settingMenu;
	JMenuItem jmiStroke;
	JMenuItem jmiStorkeColor;	
	JMenuItem jmiFillColor;

	JMenu helpMenu;	
	JMenuItem jmiAbout;

	ButtonGroup btnGroup = new ButtonGroup();
	JPanel statusPanel;
	JLabel shapeLbl;
	JLabel sizeLbl;
	JMenuItem rmiAdd;
	JLabel messageLbl;

	public JLabel modeLBL;
	private JMenuItem jmiRecovery;
	private JMenuItem jmiUndo;

	private JMenu decorateMenu;
	private JMenuItem jmiPaintGlass;
	private JMenuItem jmiPaintTexture;
	private JMenuItem jmiPaintBall;
	private JMenuItem jmiTransparent;

	/**
	 * Create the frame.
	 */
	public GrimPanFrameView(GrimPanController controller, GrimPanModel model) {
		super();
		this.controller = controller;
		this.model = model;
		this.model.setFrameView(this);

		initialize();
	}
	
	private void initialize(){
		
		setTitle("�׸���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);

		panMenuBar = new JMenuBar();
		setJMenuBar(panMenuBar);

		fileMenu = new JMenu("File  ");
		panMenuBar.add(fileMenu);

		jmiNew = new JMenuItem("New  ");
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clearAllShape();
			}
		});
		fileMenu.add(jmiNew);

		jmiOpen = new JMenuItem("Open ");
		jmiOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.openAction();
			}
		});
		fileMenu.add(jmiOpen);

		jmiSave = new JMenuItem("Save ");
		jmiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveAction();
			}
		});
		fileMenu.add(jmiSave);

		jmiSaveAs = new JMenuItem("Save  As...");
		jmiSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveAsAction();
			}
		});
		fileMenu.add(jmiSaveAs);

		jmiExit = new JMenuItem("Exit");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		jmiRecovery = new JMenuItem("Recover");
		jmiRecovery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.recoveryAction();
			}
		});
		fileMenu.add(jmiRecovery);
		fileMenu.add(jmiExit);

		shapeMenu = new JMenu("Shape  ");
		panMenuBar.add(shapeMenu);

		rdbtnmntmLine = new JRadioButtonMenuItem("����");
		rdbtnmntmLine.setSelected(true);
		rdbtnmntmLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_LINE);
				}
			}
		});
		shapeMenu.add(rdbtnmntmLine);

		rdbtnmntmPen = new JRadioButtonMenuItem("����");
		rdbtnmntmPen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_PENCIL);
				}
			}
		});
		shapeMenu.add(rdbtnmntmPen);

		rdbtnmntmPoly = new JRadioButtonMenuItem("�ٰ���");
		rdbtnmntmPoly.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_POLYGON);
				}
			}
		});
		shapeMenu.add(rdbtnmntmPoly);

		rdbtnmntmRegular = new JRadioButtonMenuItem("���ٰ���");
		rdbtnmntmRegular.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_REGULAR);
					if (model.curDrawShape != null){
						model.shapeList
						.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
								model.getShapeStrokeColor(), model.getShapeFillColor()));
						model.curDrawShape = null;
					}
					Object[] possibleValues = { 
							"3", "4", "5", "6", "7",
							"8", "9", "10", "11", "12"
					};
					Object selectedValue = JOptionPane.showInputDialog(thisClass, 
							"Choose one", "Input",
							JOptionPane.INFORMATION_MESSAGE, null,
							possibleValues, possibleValues[0]);
					if (selectedValue==null) return; // no change
					model.setNPolygon(Integer.parseInt((String)selectedValue));
					//drawPanelView.repaint();
				}
			}
		});
		shapeMenu.add(rdbtnmntmRegular);

		rdbtnmntmOval = new JRadioButtonMenuItem("Ÿ����");
		rdbtnmntmOval.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_OVAL);
					if (model.curDrawShape != null){
						model.shapeList
						.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
								model.getShapeStrokeColor(), model.getShapeFillColor()));
						model.curDrawShape = null;
					}
				}
			}
		});
		shapeMenu.add(rdbtnmntmOval);

		btnGroup.add(rdbtnmntmLine);
		btnGroup.add(rdbtnmntmPen);
		btnGroup.add(rdbtnmntmPoly);
		btnGroup.add(rdbtnmntmRegular);
		btnGroup.add(rdbtnmntmOval);		

		editMenu = new JMenu("Edit  ");
		panMenuBar.add(editMenu);
		
		jmiUndo = new JMenuItem("Undo");
		jmiUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undoAction();
			}
		});
		editMenu.add(jmiUndo);

		rmiAdd = new JMenuItem("�߰�");
		editMenu.add(rmiAdd);

		jmiRemove = new JMenuItem("����");
		editMenu.add(jmiRemove);

		jmiMove = new JMenuItem("�̵�");
		jmiMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.setMoveShapeState();
			}
		});
		editMenu.add(jmiMove);

		settingMenu = new JMenu("Setting");
		panMenuBar.add(settingMenu);

		jmiStroke = new JMenuItem("���β�");
		jmiStroke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputVal = JOptionPane.showInputDialog(thisClass, "���β�", "1");
				if (inputVal!=null){
					model.setShapeStrokeWidth(Float.parseFloat(inputVal));
				}
				else {
					model.setShapeStrokeWidth(1f);
				}
			}
		});
		settingMenu.add(jmiStroke);

		jmiStorkeColor = new JMenuItem("�׵θ���");
		jmiStorkeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setBoundaryColorAction();
			}
		});
		settingMenu.add(jmiStorkeColor);

		jcmiFill = new JCheckBoxMenuItem("ä��");
		jcmiFill.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				boolean fillState = jcmiFill.getState();
				model.setShapeFill(fillState);
				drawPanelView.repaint();
			}
		});
		settingMenu.add(jcmiFill);

		jmiFillColor = new JMenuItem("ä���");
		jmiFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setFillColorAction();
			}
		});
		settingMenu.add(jmiFillColor);
		
		decorateMenu = new JMenu("Decorate");
		panMenuBar.add(decorateMenu);
		
		jmiPaintGlass = new JMenuItem("Glass");
		jmiPaintGlass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(Util.DECO_GLASS);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
							model.getShapeStrokeColor(), model.getShapeFillColor()));
					model.curDrawShape = null;
				}
				drawPanelView.repaint();
			}
		});
		decorateMenu.add(jmiPaintGlass);
		
		jmiPaintTexture = new JMenuItem("Texture");
		jmiPaintTexture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(Util.DECO_TEX);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
							model.getShapeStrokeColor(), model.getShapeFillColor()));
					model.curDrawShape = null;
				}
				drawPanelView.repaint();
			}
		});
		decorateMenu.add(jmiPaintTexture);
		
		jmiPaintBall = new JMenuItem("Ball");
		jmiPaintBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(Util.DECO_BALL);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
							model.getShapeStrokeColor(), model.getShapeFillColor()));
					model.curDrawShape = null;
				}
				drawPanelView.repaint();
			}
		});
		decorateMenu.add(jmiPaintBall);
		
		jmiTransparent = new JMenuItem("Transparent");
		jmiTransparent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(Util.DECO_TRANS);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
							model.getShapeStrokeColor(), model.getShapeFillColor()));
					model.curDrawShape = null;
				}
				drawPanelView.repaint();
			}
		});
		decorateMenu.add(jmiTransparent);

		helpMenu = new JMenu("Help  ");
		panMenuBar.add(helpMenu);

		jmiAbout = new JMenuItem("About");
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(thisClass,
						"GrimPan Ver0.2.2 \nProgrammed by cskim, cse, hufs.ac.kr", 
						"About", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		helpMenu.add(jmiAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		drawPanelView = new DrawPanelView();
		drawPanelView.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				String sizeText = String.format("Size: %d x %d  ", 
						drawPanelView.getSize().width, drawPanelView.getSize().height);
				thisClass.sizeLbl.setText(sizeText);
			}
		});
		contentPane.add(drawPanelView, BorderLayout.CENTER);
		controller.setDrawPanelView(drawPanelView);

		statusPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) statusPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		statusPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(statusPanel, BorderLayout.SOUTH);

		sizeLbl = new JLabel("Size:               ");
		statusPanel.add(sizeLbl);

		shapeLbl = new JLabel("Shape:              ");
		shapeLbl.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(shapeLbl);

		modeLBL = new JLabel("Mode:               ");
		statusPanel.add(modeLBL);

		messageLbl = new JLabel("  ");
		statusPanel.add(messageLbl);

		jFileChooser1 = new JFileChooser(model.getDefaultDir());
		jFileChooser1.setDialogTitle("Open Saved GrimPan");

		jFileChooser2 = new JFileChooser(model.getDefaultDir());
		jFileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
		jFileChooser2.setDialogTitle("Save As ...");

		model.setEditState(Util.SHAPE_LINE);
	}
	
}
