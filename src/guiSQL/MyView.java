package guiSQL;

import sqlPlz.MyController;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by Victor on 11/04/2016.
 */
public class MyView extends JFrame {

    MyController controller;

    private final static int flowsNumber = 4;
    private final static int nombreRadio = 2;
    private final JLabel idCompte = new JLabel("Identifiant du compte");
    private final JLabel montantOperation = new JLabel("Montant de la transaction");
    private final JLabel result = new JLabel("");
    private final JTextField field = new JTextField();
    private final JTextField field2 = new JTextField();
    private final JPanel[] flows = new JPanel[flowsNumber];


    ButtonGroup group = new ButtonGroup();
    private final JRadioButton[] typeOperations = new JRadioButton[nombreRadio];
    private final JRadioButton b1 = new JRadioButton("Crédit");
    private final JRadioButton b2 = new JRadioButton("Débit");
    private final JButton button = new JButton("Valider l'opération");


    public MyView(MyController controller) {

        this.controller = controller;


        int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3);
        int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()) / 3;
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Ajout Operation");

        //Parametrage des JTextFields
        field.setColumns(8);
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new TextLimiterFilter(8));
        field2.setColumns(8);
        ((AbstractDocument) field2.getDocument()).setDocumentFilter(new TextLimiterFilter(8));

        //Initialisation des JLabel

        idCompte.setFont(Font.getFont(Font.MONOSPACED));
        montantOperation.setFont(Font.getFont(Font.MONOSPACED));

        //Initialisation des JButtons
        b1.setFont(Font.getFont(Font.MONOSPACED));
        typeOperations[0] = b1;
        b2.setFont(Font.getFont(Font.MONOSPACED));
        typeOperations[1] = b2;
        group.add(b1);
        group.add(b2);

        //Initialisation du container
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridLayout(4, 0));

        for (int i = 0; i < flowsNumber; ++i) {
            flows[i] = new JPanel();
            flows[i].setLayout(new FlowLayout(FlowLayout.CENTER, 100, 4 * (int) flows[i].getPreferredSize().getHeight()));
        }
        flows[0].add(idCompte);


        flows[0].add(field);
        flows[0].setBackground(new Color(255, 213, 194));
        flows[0].setMaximumSize(flows[0].getComponent(0).getPreferredSize());
        flows[1].add(montantOperation);
        flows[1].add(field2);

        flows[1].setBackground(new Color(0xF5F5DC));
        flows[2].add(b1);
        flows[2].add(b2);


        flows[2].setBackground(new Color(255, 213, 194));
        flows[3].add(button);
        flows[3].add(result);
        contentPane.add(flows[0]);
        contentPane.add(flows[1]);
        contentPane.add(flows[2]);
        contentPane.add(flows[3]);

        setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idCompteInput, montantOperationInput, playerInput = 1;
                try {
                    idCompteInput = Integer.parseInt(field.getText());
                    montantOperationInput = Integer.parseInt(field2.getText());
                    for (int i = 0; i < typeOperations.length; ++i) {
                        if (typeOperations[i].isSelected())
                            playerInput = i;
                    }
                    controller.send(idCompteInput, montantOperationInput, playerInput);

                } catch (NumberFormatException ex) {
                    System.out.println("caught " + ex.toString());

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }

        });


    }

    public void receive(int lastIdCompte, String lastIdOperation) {
        if(lastIdOperation.contains("SYSTEM.FK_COMPTE_OPERATION"))
            this.result.setText("Erreur : Vérifier l'id du compte indiqué.");
        else
            this.result.setText("Opération : " + lastIdOperation + " $ sur le compte " + lastIdCompte + ".");



    }


    private class TextLimiterFilter extends DocumentFilter {

        int limit;

        public TextLimiterFilter(int limit) {
            super();
            if (limit <= 0) throw new IllegalArgumentException();
            this.limit = limit;
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String s = "";
            for (int i = 0; i < text.length(); ++i) {
                if (Character.isDigit(text.charAt(i)))
                    s += text.charAt(i);
            }
            text = s;


            int currentLength = fb.getDocument().getLength();
            int overLimit = currentLength + text.length() - limit - length;

            if (overLimit > 0)
                text = text.substring(0, text.length() - overLimit);
            super.replace(fb, offset, length, text, attrs);
        }


    }

}
