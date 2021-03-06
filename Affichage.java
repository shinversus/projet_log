package projet_log;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Affichage extends JFrame {
	static final long serialVersionUID = 1;

	private Trace trace;
	private double[][] matrice;
	private int tMax;
	private int longMax;
	private JLabel nom_axe;


	public Affichage(){

		this.trace = new Trace();
		trace.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		trace.setLayout(null);//


		//affiche le nom des axes
		this.nom_axe = new JLabel();
		Font police = new Font("Tahoma", Font.ITALIC, 14); 
		nom_axe.setFont(police);
		nom_axe.setForeground(Color.BLACK);
		nom_axe.setBounds(5, 5,500,100);
		 //interpr�teur html pour passage � la ligne facile dans un seul JLabel
		nom_axe.setText("<html>axe x : coordon�es sur la route<br>axe y : temps en nombre d'incr�ment</html>");
		trace.add(nom_axe);


		this.setTitle("Affichage densit�");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(this.getWidth(),5);

		this.setContentPane(trace);
		this.setForeground(Color.gray);
		this.setVisible(true);
	}

	public void setMatrice(double[][]matrice){
		this.matrice=matrice;
		longMax=matrice.length;
		tMax=matrice[0].length;
		trace.init_dim();
	}
	
	//met � jour l'affichage de la densit�
	public void refresh(){
		trace.repaint();
	}




	//=========================================================================//
	public class Trace extends JPanel{
		static final long serialVersionUID = 1;
		private int longueur_case;
		private int hauteur_case;



		public void init_dim(){
			longueur_case=Math.round(this.getWidth()/longMax);
			hauteur_case=Math.round(this.getHeight()/tMax);

		}


		//colorie la fenetre d'affichage de la densit� avec des couleurs fonction de la densit� calcul�e
		public void paintComponent(Graphics g){
			int R;
			int G;
			int B;
			super.paintComponents(g);
			for(int i=0;i<tMax;i++){//parcour le temps
				for(int k=0;k<longMax;k++){//parcour l'espace
					R=(int)(matrice[k][i]*255);
					if(matrice[k][i]<0.5){
						G=(int)(matrice[k][i]*512);
					}else{
						G=(int)(512-matrice[k][i]*512);
					}
					B=(int)(255-matrice[k][i]*255);
					Color densite=new Color(R, G, B);
					g.setColor(densite);
					g.fillRect(k*longueur_case,this.getHeight()-i*hauteur_case,longueur_case,hauteur_case);//xdebut,ydebut,largeur,hauteur

				}
			}
		}
	}
}