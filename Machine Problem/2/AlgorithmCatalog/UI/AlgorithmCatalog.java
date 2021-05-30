package UI;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.Dimension;

public class AlgorithmCatalog extends JFrame{

	CatalogItem item;
	CatalogMenu menu;
	
	public AlgorithmCatalog(){
		setSize(700,600);
		setTitle("Algorithm Design Catalog");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		loadMenu();
	}

	public void loadMenu(){
		menu = new CatalogMenu(new Dimension(this.getWidth(), this.getHeight()), this);
		item = new CatalogItem(new Dimension(this.getWidth(), this.getHeight()), this);
		this.getContentPane().add(menu);
		this.getContentPane().add(item);
	}

	public CatalogItem getCatalogItem(){
		return item;
	}

	public CatalogMenu getCatalogMenu(){
		return menu;
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new AlgorithmCatalog().setVisible(true);
			}
		});
	}
}