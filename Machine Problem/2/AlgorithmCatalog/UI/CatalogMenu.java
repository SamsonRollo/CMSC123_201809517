package UI;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class CatalogMenu extends JPanel implements ActionListener{
	
	private BufferedImage bg = null;
	private AlgorithmCatalog ac;

	public CatalogMenu(Dimension d, AlgorithmCatalog ac){
		this.ac = ac;
		setLayout(null);
		setBounds(0,0, (int)d.getWidth(), (int)d.getHeight());
		setBackground(java.awt.Color.BLUE);
		addButtons(d); //add About later
		repaint();
	}

	@Override
	public void paintComponent(Graphics g){
		try{
			 bg = ImageIO.read(this.getClass().getClassLoader().getResource("src/menuBG.png"));
			 g.drawImage(bg,0, 0, null);
		}catch(Exception e){g.fillRect(0,0,700,600);}
	}

	private void addButtons(Dimension d){ //changing the text needs to change the text in actionPerformed
		JButton[] menuButtons = new JButton[6];
		JButton bfButton = new JButton("BRUTE FORCE");
		JButton deCButton = new JButton("DECREASE-AND-CONQUER");
		JButton diCButton = new JButton("DIVIDE-AND-CONQUER");
		JButton tCButton = new JButton("TRANSFORM-AND-CONQUER");
		JButton gAButton = new JButton("GREEDY TECHNIQUE");
		JButton bTButton = new JButton("BACKTRACKING TECHNIQUE");
		menuButtons[0] = bfButton;
		menuButtons[1] = deCButton;
		menuButtons[2] = diCButton;
		menuButtons[3] = tCButton;
		menuButtons[4] = gAButton;
		menuButtons[5] = bTButton;

		for(int i=0; i<menuButtons.length; i++){
			menuButtons[i].setLocation((int)((d.getWidth()/2)-(250/2)), (int)(d.getHeight()-(100+50*(menuButtons.length-i)))); //horizontal center and 100 px from below
			menuButtons[i].setSize(250, 40);
			menuButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
			//menuButtons[i].setContentAreaFilled(false);
			menuButtons[i].setBorderPainted(false);
			menuButtons[i].setFocusPainted(false);
			menuButtons[i].addActionListener(this);
			this.add(menuButtons[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event){
		ac.getCatalogItem().loadItem(event.getActionCommand());
		this.setVisible(false);
	}
}