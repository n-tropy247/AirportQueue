/*
 * Copyright (C) 2018 Ryan Castelli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package AirportQueue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.UIManager.LookAndFeelInfo;

import java.util.LinkedList;

/**
 * Creating an example of queues in the scope of flight tracking
 *
 * @author castellir
 * @ver 1.0
 */
public class AirportQueue extends JPanel {

    private static final LinkedList INCOMING_FLIGHTS = new LinkedList();

    private static ActionEvent sendOverride;

    private static int option;

    private static JButton jbtnSend;

    private static JFrame jfrm;

    private static JTextArea jtaDisplay;

    private static JScrollPane jscrlp;

    private static JTextField jtfInput;

    private static String input;

    /**
     * Creates JFrame
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exe) {
            System.err.println("Nimbus unavailable");
        }

        option = 0;

        jfrm = new JFrame("Airport Queue");
        jfrm.setLayout(new BorderLayout()); //sets layout based on borders
        jfrm.setSize(700, 600); //sets size

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets screen dimensions

        double screenWidth = screenSize.getWidth(); //width of screen
        double screenHeight = screenSize.getHeight(); //height of screen

        jfrm.setLocation((int) screenWidth / 2 - 250, (int) screenHeight / 2 - 210); //sets location of chat to center

        jtaDisplay = new JTextArea(20, 40); //size of display
        jtaDisplay.setEditable(false); //display not editable
        jtaDisplay.setLineWrap(true); //lines wrap down

        jscrlp = new JScrollPane(jtaDisplay); //makes dispaly scrollable

        jtfInput = new JTextField(30); //sets character width of input field

        jbtnSend = new JButton("Send"); //sets button text

        jbtnSend.addActionListener(new handler()); //adds listener to button

        KeyListener key = new handler(); //adds handler for 'enter' key

        jtfInput.addKeyListener(key); //adds keylistener for 'enter'
        jfrm.add(jscrlp, BorderLayout.PAGE_START); //adds scrollable display to main frame

        sendOverride = new ActionEvent(jbtnSend, 1001, "Send"); //allows key to trigger same method as button

        JPanel p1 = new JPanel(); //panel for input/button

        p1.setLayout(new FlowLayout()); //flow layout for input/button
        p1.add(jtfInput, BorderLayout.LINE_END); //adds input to panel
        p1.add(jbtnSend, BorderLayout.LINE_END); //adds button to panel

        jfrm.add(p1, BorderLayout.PAGE_END); //add button/input to main frame

        jfrm.setVisible(true); //makes frame visible

        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kills application on close
        
        jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
    }

    /**
     * Sends commands from input to queue handler
     */
    private static class handler implements ActionListener, KeyListener {

        /**
         * If button pressed
         *
         * @param ae
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand().equals("Send")) {
                input = jtfInput.getText();
                jtaDisplay.setText(jtaDisplay.getText() + "\nYou: " + input);
                switch (option) {
                    case 0:
                        if (!input.equals("")) {
                            switch (Integer.valueOf(input)) {
                                case 1:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nEnter Flight Number: ");
                                    option = 1;
                                    break;
                                case 2:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nFlight " + INCOMING_FLIGHTS.remove() + " has landed.");
                                    jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                                case 3:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nEnter Flight Number to check: ");
                                    option = 2;
                                    break;
                                case 4:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nThere are " + INCOMING_FLIGHTS.size() + " in the queue");
                                    jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                                case 5:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nFlight " + INCOMING_FLIGHTS.peek() + " is first in the queue");
                                    jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                                case 6:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nFlight " + INCOMING_FLIGHTS.peekLast() + " is last in the queue");
                                    jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                                case 7:
                                    INCOMING_FLIGHTS.removeLast();
                                    jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                                case 8:
                                    LinkedList dupes = new LinkedList();
                                    int c = INCOMING_FLIGHTS.size() - 1;
                                    for (int j = 0; j < INCOMING_FLIGHTS.size(); j++) {
                                        for (int i = j + 1; i < c; i++) {
                                            if ((int) INCOMING_FLIGHTS.get(j) == (int) INCOMING_FLIGHTS.get(i)) {
                                                dupes.add(INCOMING_FLIGHTS.get(i));
                                            }
                                        }
                                        c--;
                                    }
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nDuplicates: ");
                                    for (int j = 0; j < INCOMING_FLIGHTS.size(); j++) {
                                        jtaDisplay.setText(jtaDisplay.getText() + "\n" + dupes.get(j));
                                    }
                                    jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                                case 9:
                                    c = INCOMING_FLIGHTS.size() - 1;
                                    for (int j = 0; j < INCOMING_FLIGHTS.size(); j++) {
                                        for (int i = j + 1; i < c; i++) {
                                            if ((int) INCOMING_FLIGHTS.get(j) == (int) INCOMING_FLIGHTS.get(i)) {
                                                c--;
                                                INCOMING_FLIGHTS.remove(i);
                                            }
                                        }
                                        c--;
                                    }
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nDone!\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                                case 10:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nWhat flight do you want to remove?: ");
                                    option = 3;
                                    break;
                                case 11:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nWhat flight do you want to remove?: ");
                                    option = 4;
                                    break;
                                case 12:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nWhat flight do you want to add?: ");
                                    option = 5;
                                    break;
                                case 13:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nQueue: ");
                                    for (int j = INCOMING_FLIGHTS.size(); j > 0; j--) {
                                        jtaDisplay.setText(jtaDisplay.getText() + "\n" + (int) INCOMING_FLIGHTS.get(j));
                                    }
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nDone!\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                                    break;
                            }
                        }
                        break;
                    case 1:
                        INCOMING_FLIGHTS.add(input);
                        jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                        option = 0;
                        break;
                    case 2:
                        jtaDisplay.setText(jtaDisplay.getText() + "\nFlight is present: " + INCOMING_FLIGHTS.contains(input));
                        jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                        option = 0;
                        break;
                    case 3:
                        int flightRmvFirst = Integer.valueOf(input);
                        for (int j = 0; j < INCOMING_FLIGHTS.size(); j++) {
                            if ((int) INCOMING_FLIGHTS.get(j) == flightRmvFirst) {
                                INCOMING_FLIGHTS.remove(j);
                            }
                        }
                        jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                        option = 0;
                        break;
                    case 4:
                        int flightRmvLast = Integer.valueOf(input);
                        for (int j = INCOMING_FLIGHTS.size(); j > 0; j--) {
                            if ((int) INCOMING_FLIGHTS.get(j) == flightRmvLast) {
                                INCOMING_FLIGHTS.remove(j);
                            }
                        }
                        jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                        option = 0;
                        break;
                    case 5:
                        int flightInsertFront = Integer.valueOf(input);
                        INCOMING_FLIGHTS.add(0, flightInsertFront);
                        jtaDisplay.setText(jtaDisplay.getText() + "\n1. Add flight\n2. Remove first flight in list\n3. Check for flight\n4. Size of queue\n5. First in queue\n6. Last in queue\nRemove last in queue\n8. Check for duplicates\n9. Remove duplicates\n10. Remove first occurance of flight number\n11. Remove last occurance of flight number\n12. Add item to front of queue\n13. Print queue\nEnter Selection: ");
                        option = 0;
                        break;
                    default:
                        break;
                }
                jtfInput.setText("");
            }
        }

        /**
         * Listens for enter key pressed
         *
         * @param ke
         */
        @Override
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                actionPerformed(sendOverride);
            }
        }

        /**
         * Necessary override
         *
         * @param ke
         */
        @Override
        public void keyTyped(KeyEvent ke) {
        }

        /**
         * Necessary override
         *
         * @param ke
         */
        @Override
        public void keyReleased(KeyEvent ke) {
        }
    }
}
