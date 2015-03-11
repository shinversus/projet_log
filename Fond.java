package projet_log;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fond extends JFrame  implements ActionListener{
	static final long serialVersionUID = 1;	

	private route route;
	private Boutons1 b_startstop;
	private Boutons1 b_increment;
	private Panneau fond;
	private boolean compteur = false;
	private Thread t;
	private int[] liste;
	private JLabel compteur_step = new JLabel(); 
	private JLabel label_num_voit[];
	
	public Fond(route route){

		this.setVisible(true);
		this.fond = new Panneau();
		fond.setPreferredSize(new Dimension(640, 480)); //
		fond.setLayout(null);//
		this.setTitle("Interface Graphique");
		this.setSize(4000, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.route=route;
		this.liste = route.get_route();
		
		
		//les boutons
		b_increment = new Boutons1("incr�mente", this);
		b_startstop = new Boutons1("start/stop", this);
		b_startstop.setBounds(this.getWidth()/2-60, 10,90,50);//
		b_increment.setBounds(this.getWidth()/2+60, 10,100,50);//
		fond.add(b_startstop);
		fond.add(b_increment);
		
	//les labels

		//label compteur de steps
		Font police = new Font("Tahoma", Font.BOLD, 18); 
		compteur_step.setFont(police);
		compteur_step.setForeground(Color.BLACK);
		compteur_step.setBounds(10, 5,500,100);//
		//compteur_step.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
		fond.add(compteur_step);
		
		//labels des num�ros de voitures
		label_num_voit = new JLabel[route.get_nb_voit()];
		Font police2 = new Font("Tahoma", Font.BOLD, 15); 
		
		for(int k=0;k<label_num_voit.length;k++){
			label_num_voit[k]  = new JLabel();
			label_num_voit[k].setText(""+k);
			label_num_voit[k].setFont(police2);
			fond.add(label_num_voit[k]);
		}
		//this.getContentPane().setLayout(null);
		
		
		this.getContentPane().add(fond);

	}



	public void actionPerformed(ActionEvent evenement) {
		String bouton_appuye = evenement.getActionCommand();

		if(bouton_appuye == "incr�mente"){
			route.step();
			fond.repaint();
		}
		/*else if (bouton_appuye == "dodo"){
			//rend le bouton cliquable ou non : mais ne marche pas avec thread.sleep()
			b.setEnabled(false);
			System.out.println("au lit");	
			b.setEnabled(true);
			System.out.println("debout");
		}*/
		else if (bouton_appuye == "start/stop"){

			if(compteur == false){
				b_increment.setEnabled(false);
				System.out.println("start");
				fond.set_bool_anim(true);
				this.t = new Thread(fond);
				fond.set_bool_anim(true);
				t.start();
				compteur = true;

			}
			else
			{
				System.out.println("stop");
				
				fond.set_bool_anim(false);
				compteur = false;
				b_increment.setEnabled(true);
			}
		}

		else{
			System.out.println("un autre bouton svp");
		}

	}

	/*public void actionPerformed(ActionEvent evnmt) {

		if(evnmt.getSource() == "incr�mente"){
			test.inc_numero();
		}
		else if(evnmt.getSource() == "d�cr�mente"){
			test.dec_numero();
		}
		else if (evnmt.getSource() == "affiche"){
			test.affiche_numero();
		}
		else{
			System.out.println("un autre bouton svp");
		}

	}*/









	public class Panneau extends JPanel implements Runnable{
		static final long serialVersionUID = 1;	
		private boolean b_run;
		private int hauteur_dess_route;


		public Panneau(){
			this.hauteur_dess_route = 140;
		}


		//methode de dessin
		public void paintComponent(Graphics g){

			super.paintComponents(g);
			
			int nb_case = liste.length; //nombre de cellule � dessiner sur la route
			int marge = 5; //les marges � gauche et � droite de la route
			int taille_case = (this.getWidth()-2*marge)/nb_case; // taille des cases � dessiner

			g.setColor(Color.blue); //fond de la fen�tre d'interface
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			g.setColor(Color.gray); //dessin de la route (x1,y1,x2,y2)
			g.fillRect(marge, this.getHeight()/2-hauteur_dess_route/2, this.getWidth()-marge, hauteur_dess_route);

			g.setColor(Color.black); //dessin des cellules
			for(int i=1;i<=nb_case+1;i++){
				g.drawLine(marge+taille_case*i, this.getHeight()/2-hauteur_dess_route/2, taille_case*i+marge, this.getHeight()/2+hauteur_dess_route/2);
			}
			
			//dessine les voitures dans les cases
			g.setColor(Color.red);
			int count = 0;
			for(int k=0; k<liste.length; k++){

				if(liste[k]>0){
					g.fillRect(taille_case*k+marge+4, this.getHeight()/2-hauteur_dess_route/2+15, taille_case-5, hauteur_dess_route-2*15);
					compteur_step.setText("incr�ment n� " + route.get_temps());
					label_num_voit[count].setBounds(taille_case*k+marge+taille_case/2-4, this.getHeight()/2-hauteur_dess_route/2+15, taille_case-5, hauteur_dess_route-2*15);
					count++;
				}
			}

		}

		

		//methode abstract venant de implements Runnable
		public void run(){
			//System.out.println("on est bien l�!");
			while(b_run){
				route.step();

				repaint();
				route.ecrireDensiteText();
				route.ecrireVoitureText();
				try {
					//Thread.sleep(300);
					Thread.sleep(50);//mode turbo affichage
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void set_bool_anim(boolean b){
			this.b_run = b;
		}

	}
}





