package UI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Algorithms.*;
import Algorithms.Greedy.*;

public class CatalogItem extends JPanel implements ActionListener{
	
	String[] designs = {"brute_force","decrease-and-conquer","divide-and-conquer","transform-and-conquer","greedy_technique","backtracking_technique"};
	JPanel screen, implPanel;
	JScrollPane scrollScreen;
	String currentAction = "INFO", gInput="";
	private AlgorithmCatalog ac;
	private BufferedImage bg;

	public CatalogItem(Dimension d, AlgorithmCatalog ac){
		this.ac = ac;
		setLayout(null);
		setSize((int)d.getWidth(),(int)d.getHeight());
		setLocation(0,0);
		setBackground(java.awt.Color.GREEN);
		setVisible(false);
		initialize(d);
	}

	@Override
	public void paintComponent(Graphics g){
		try{
			 bg = ImageIO.read(this.getClass().getClassLoader().getResource("src/items.png"));
			 g.drawImage(bg,0, 0, null);
		}catch(Exception e){g.fillRect(0,0,700,600);}
	}

	private void initialize(Dimension d){
		JButton[] itemButtons = new JButton[6];
		JButton left = new JButton(" ");
		JButton right = new JButton("  ");
		JButton info = new JButton("INFO");
		JButton menu = new JButton("MENU");
		JButton impl = new JButton("IMPLEMENTATION");
		start = new JButton("Start");
		input = new JTextField(15);
		inputLabel = new JLabel("INPUT");
		speedLabel = new JLabel("SPEED");
		speed = new JSlider(100,2000,550);

		itemButtons[0] = left;
		itemButtons[1] = right;
		itemButtons[2] = info;
		itemButtons[3] = impl;
		itemButtons[4] = menu;
		itemButtons[5] = start;
		
		try{
			right.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("src/right.png"))));
			left.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("src/left.png"))));
		}catch(Exception e){
			right.setText("Right");
			left.setText("Left");	
			System.out.println("Unable to load moveIcon.png");
		}

		left.setLocation(10,10);
		left.setSize(50,30); //temporary
		right.setLocation((int)d.getWidth()-60,10);
		right.setSize(50,30); //temporary

		implElements(false);
		inputLabel.setBounds(515, 250, 160,30);
		inputLabel.setForeground(Color.WHITE);
		input.setBounds(515, 275, 160,30);
		speedLabel.setBounds(515, 350, 160, 30);
		speedLabel.setForeground(Color.WHITE);
		speed.setBounds(515,390, 160, 30);
		this.add(input); //input field
		this.add(inputLabel);
		this.add(speedLabel);
		this.add(speed);

		for(int i=0; i<itemButtons.length; i++){
			if(i!=0 && i!=1){
				if(i==5)
					itemButtons[i].setLocation(515, 110+i*40);
				else
					itemButtons[i].setLocation(515, 40+i*40);
				itemButtons[i].setSize(160, 30);
			}
			if(i==1 || i==0){
				itemButtons[i].setBorderPainted(false);
				itemButtons[i].setContentAreaFilled(false);
			}

			itemButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
			itemButtons[i].setFocusPainted(false);
			itemButtons[i].addActionListener(this);
			this.add(itemButtons[i]);
		}

		scrollScreen = new JScrollPane();
		scrollScreen.setBounds(20, 70, 485, 480);
		this.add(scrollScreen);
		scrollScreen.getViewport().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				screen.repaint(); //try removing this 
				repaint();
			}
		});
		speed.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				sleepTime = ((JSlider)e.getSource()).getValue();
			}
		});
		infoScreen();//initial setup
		dij = new Dijkstra(this);
		sudoku = new SudokuSolver(this);
	}

	public void loadItem(String design){
		this.setVisible(true);
		setIndex(design);
		repaint();
	}

	private void setIndex(String design){
		design = design.replaceAll(" ","_").toLowerCase();
		int i = java.util.Arrays.asList(designs).indexOf(design);

		if(i==-1)
			index = 0;
		else
			index = i;
	}

	public void infoScreen(){
		screen = new JPanel(null){
			@Override
			public void paintComponent(Graphics g){
				int y = 10;
				try{ //
					BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("AlgoInfo/"+designs[index]+".txt")));
					String line;
					boolean isTitle = true;

					while((line=br.readLine())!=null){
						if(isTitle){
							g.setFont(new Font("TimesRoman", Font.BOLD, 30));
							g.drawString(line, 15, y+=45);
							g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
							isTitle = false;
						}else	
							g.drawString(line, 15, y+=18);
					}
					br.close();
				}catch(IOException ioe){System.out.println("Unable to read text file");};
				screen.setPreferredSize(new Dimension(400,y+50));
				updateUI();
			}
		};
		scrollScreen.getViewport().add(screen);
	}

	public void implementationScreen(){

		implPanel = new JPanel(null){
			@Override
			public void paintComponent(Graphics g){
				int y = 100;
				int x = 50;
				g.setFont(new Font("TimesRoman", Font.PLAIN, 15));

				//visual
				if(index<4){ //sorting implementation
					if(elements!=null){
						for(int element : intElements){
							g.drawRect(x,75,30,25);
							g.drawString(String.valueOf(element),x+8, 93);
							x+=30;
						}
					}
				}else if(index==4){ //dijkstra
					int farthestX = 300, farthestY = 347;
					Graphics2D g2 = (Graphics2D)g;
					g2.setStroke(new BasicStroke(2.0F));
					for(Edge e: dij.getLoader().getEdges()){
						if(e.getActivity().equals("active")){
							g2.setColor(Color.RED);
						}
						else if(e.getActivity().equals("done")){
							g2.setColor(Color.BLUE);
						}
						else{
							g2.setColor(Color.BLACK);
						}

						g2.drawLine(e.getStartX(), e.getStartY(), e.getEndX(), e.getEndY());
					}
					//normal vertex
					boolean usedIMG = true;
					BufferedImage img = null;

					for(Vertex v: dij.getLoader().getVertices()){
						if(v.getEndX()>farthestX)
							farthestX = v.getEndX();
						if(v.getEndY()>farthestY)
							farthestY = v.getEndY();

						try{
							if(v.getActivity().equals("active"))
								img = ImageIO.read(this.getClass().getClassLoader().getResource("src/vertexEval.png"));
							else if(v.getActivity().equals("done"))
								img = ImageIO.read(this.getClass().getClassLoader().getResource("src/vertexEvalDone.png"));
							else if(v.getActivity().equals("check"))
								img = ImageIO.read(this.getClass().getClassLoader().getResource("src/vertexCheck.png"));	
							else
								img = ImageIO.read(this.getClass().getClassLoader().getResource("src/vertex.png"));//source of nrmal vertex
						}catch(IOException ioe){
							usedIMG = false;
							System.out.println("Unable to load some images");
						};

						if(usedIMG)
							g.drawImage(img,v.getStartX()-15, v.getStartY()-15, null);
						else{
							g.setColor(Color.magenta);
							g.fillOval(v.getStartX()-15, v.getStartY()-15,30,30);
						}

						g.setColor(Color.black);
						g.drawString(v.getVertexName(),v.getStartX()+10, v.getStartY()-10);
					}

					y = 360;

					int minW = 48;
					for(Vertex v : dij.getLoader().getVertices()){
						if(v.getVertexName().length()*8>minW)
							minW = v.getVertexName().length()*8;
					}

					g.drawString("Vertex", x, y);
					g.drawString("Distance",x+minW+15, y);
					y+=24;

					int i = 0;
					for(Vertex v : dij.getLoader().getVertices()){
						g.drawString(v.getVertexName(),x,y);
						String dist = "";
						try{
							dist = String.format("%.2f",distance[i]);
						}catch(Exception e){dist="";};
						g.drawString(dist,x+minW+15,y);
						y+=16;
						i++;
					}
				}else if(index==5){ //sudoku
					x=100;
					if(sudoku!=null){
						int[][][] board = sudoku.getBoard();
						for(int i=0; i<9;i++){
							for(int j=0; j<9; j++){
								if(board[i][j][1]==1){
									g.setColor(Color.CYAN);
									g.fillRect(x,y,30,30);
								}
								else
									g.drawRect(x,y,30,30);
								g.setColor(Color.BLACK);

								if(board[i][j][0]!=0)
									g.drawString(String.valueOf(board[i][j][0]), x+8, y+18);
								x+=30;
							}
							y+=30;
							x=100;
						}
					}
				}

				try{ 
					BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("AlgoInfo/"+designs[index]+".txt")));
					String line;
					boolean isTitle = true;

					while((line=br.readLine())!=null)
						if(isTitle){
							g.setFont(new Font("TimesRoman", Font.BOLD, 30));
							g.drawString(line, 15, 55);
							g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
							isTitle = false;
							break;
						}
					br.close();
				}catch(IOException ioe){System.out.println("Unable to read text file");};
				if(x<400)
					x = 350;
				implPanel.setPreferredSize(new Dimension(x+50,y+50));
				updateUI();
			}
		};
		scrollScreen.getViewport().add(implPanel);
	}

	private void implElements(boolean isVisible){
		if(start!=null)
			start.setVisible(isVisible);
		if(input!=null){
			if(!isVisible)
				input.setText("");
			input.setVisible(isVisible);
		}
		if(inputLabel!=null)
			inputLabel.setVisible(isVisible);
		if(speedLabel!=null)
			speedLabel.setVisible(isVisible);
		if(speed!=null)
			speed.setVisible(isVisible);
		
		implInputs();
	}

	private void implInputs(){
		if(index<5 && currentAction.equals("IMPLEMENTATION")){
			if(input!=null)
				input.setVisible(true);
			if(inputLabel!=null)
				inputLabel.setVisible(true);
		}else{
			if(input!=null)
				input.setVisible(false);
			if(inputLabel!=null)
				inputLabel.setVisible(false);
		}
	}

	private boolean parseInput(){
		if(index<4){
			elements = input.getText().split(",");
			intElements = new int[elements.length];
			for(int i = 0;i<intElements.length; i++){
				try{
					intElements[i] = Integer.parseInt(elements[i]);
					if(intElements[i]>99){
						elements = null;
						popMessage("An element exceed 2 digits", "Error", JOptionPane.ERROR_MESSAGE);
						return false;	
					}
				}catch(NumberFormatException ne){
					elements = null;
					popMessage("Input contains illegal character", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				};
			}
		}else if(index==4){
			gInput = input.getText();
		}
		return true;
	}

	public String getDesign(){
		return designs[index];
	}

	public int[] getInput(){
		return intElements;
	}

	public String getGreedInput(){
		return gInput;
	}

	public void refresh(){
		if(index==4 && dij!=null)
			this.distance = dij.getDistances();
		if(implPanel!=null)
			implPanel.repaint();
		this.repaint();
	}

	public int getSleepTime(){
		return sleepTime;
	}

	public void setSudoku(SudokuSolver sudoku){
		this.sudoku = sudoku;
	}

	public Dijkstra getDijkstra(){
		return dij;
	}

	public SudokuSolver getSudoku(){
		return sudoku;
	}

	public void popMessage(String message, String head, int messageType){
		start.setText("Start");
		JOptionPane.showMessageDialog(null,message,head, messageType);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent event){
		String action = event.getActionCommand();
		if(action.equals(" ") || action.equals("Left")){ //left
			if(index==0)
				index = 5;
			else
				index--;
			implInputs();
		}else if(action.equals("  ") || action.equals("Right")){ //right
			if(index==5)
				index = 0;
			else
				index++;
			implInputs();
		}else if(action.equals("MENU")){
			this.setVisible(false);
			ac.getCatalogMenu().setVisible(true);
			currentAction = "INFO";
			implElements(false);
			if(implPanel!=null)	
				scrollScreen.getViewport().remove(implPanel);
			scrollScreen.getViewport().add(screen);
			implPanel = null; //destroy impl
		}else if(action.equals("INFO")){
			if(!currentAction.equals("INFO")){
				currentAction = "INFO";
				implElements(false);
				scrollScreen.getViewport().remove(implPanel);
				scrollScreen.getViewport().add(screen);
				implPanel = null; //destroy impl
			}
		}else if(action.equals("IMPLEMENTATION")){ 
			if(!currentAction.equals("IMPLEMENTATION")){
				currentAction = "IMPLEMENTATION";	
				implElements(true);
				implementationScreen();
				scrollScreen.getViewport().remove(screen);
			}
		}else if(action.equals("Start")){
			if(parseInput()){
				if(index==4)
					dij.reset();
				else if(index==5)
					sudoku.reset();

				start.setText("Stop");
				Algorithm algo = new Algorithm(this);
				algoRun = new Thread(algo);
				algoRun.start();
			}
		}else if(action.equals("Stop")){
			start.setText("Start");
			if(algoRun!=null)
				try{
					algoRun.stop();
				}catch(Exception e){};
		}
		if(action.equals("IMPLEMENTATION"))
			refresh();
		else
			repaint();
	}

	private int index = 0, sleepTime=550;
	private JButton start;
	private JLabel inputLabel, speedLabel;
	private JTextField input;
	private int[] intElements;
	private SudokuSolver sudoku;
	private Dijkstra dij;
	private String[] elements;
	private Thread algoRun;
	private JSlider speed;
	private double[] distance = new double[8];
}