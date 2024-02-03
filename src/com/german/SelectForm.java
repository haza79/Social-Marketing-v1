package com.german;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;

public class SelectForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    public JTable tbCountryCode;
    private JTextField tfSearch;

    private String[] columnName = {"ID","country code","country name"};
    public DefaultTableModel model = new DefaultTableModel();

    public SelectForm() {
        model.setColumnIdentifiers(columnName);
        tbCountryCode.setModel(model);

        final TableRowSorter<TableModel> sorter3 = new TableRowSorter<TableModel>(model);
        tbCountryCode.setRowSorter(sorter3);

        setContentPane(contentPane);
        setModal(true);
        setSize(300,450);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String text = tfSearch.getText();
                if (text.trim().length() == 0) {
                    sorter3.setRowFilter(null);
                } else {
                    sorter3.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }

            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        SelectForm dialog = new SelectForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
