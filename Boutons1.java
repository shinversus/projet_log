package projet_log;
import javax.swing.JButton;

public class Boutons1 extends JButton {
	static final long serialVersionUID = 1;

	private String nom;
	private Fond fen;

	public Boutons1(String nom, Fond fenetre){
		super(nom);
		this.nom = nom;
		this.fen = fenetre;

		this.addActionListener(fen);

	}

}
