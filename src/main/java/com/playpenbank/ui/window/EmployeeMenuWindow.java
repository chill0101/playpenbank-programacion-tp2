package com.playpenbank.ui.window;

import com.playpenbank.dto.TransactionDTO;
import com.playpenbank.model.PlaypenATM;
import com.playpenbank.service.AccountService;
import com.playpenbank.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeMenuWindow extends JPanel {
    private JLabel messageLabel;

    private JPanel createWelcomePanel() {
        String html = "<html>"
                + "<div style='text-align:center;'>"
                + "<span style='font-size:20px;color:#278EF5;font-weight:bold;'>Necesitamos más de usted.</span><br>"
                + "<span style='font-size:15px;color:#555;'>Su misión es importante.</span>"
                + "</div>"
                + "</html>";
        JLabel label = new JLabel(html, SwingConstants.CENTER);
        label.setFont( new Font("Segoe UI", Font.BOLD, 18) );
        JPanel panel = new JPanel( new BorderLayout() );
        panel.add( label, BorderLayout.CENTER );
        return panel;
    }

    private void updateStats( JTextArea statsArea ) {
        statsArea.setText(new TransactionService().getATMStatsString());
    }

    public EmployeeMenuWindow( ActionListener logoutListener ) {
        setLayout(new BorderLayout(10, 10));

        // Top: message label
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.BLUE);
        add(messageLabel, BorderLayout.NORTH);

        // Tabbed navigation
        JTabbedPane tabbedPane = new JTabbedPane( JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Transactions Panel
        JPanel transactionsPanel = new JPanel( new BorderLayout() );
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Tipo", "Monto", "Fecha y hora"}, 0);
        JTable table = new JTable( tableModel );
        transactionsPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Stats Pan el
        JPanel statsPanel = new JPanel( new BorderLayout() );
        JTextArea statsArea = new JTextArea( );
        statsArea.setEditable( false );
        statsPanel.add( new JScrollPane(statsArea), BorderLayout.CENTER );

        // Load ATM Panel
        JPanel loadPanel = new JPanel(new FlowLayout( FlowLayout.LEFT ) );
        JTextField loadAmountField = new JTextField( 10 );
        JButton confirmLoadBtn = new JButton( "Cargar ATM" );
        loadPanel.add( new JLabel("Monto a ingresar:") );
        loadPanel.add( loadAmountField );
        loadPanel.add( confirmLoadBtn );

        // Playpen Mode Panel
        JPanel playpenPanel = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        JButton playpenOnBtn = new JButton( "Activar Playpen Mode!" );
        JButton playpenOffBtn = new JButton( "Desactivar Playpen Mode" );
        playpenPanel.add( playpenOnBtn );
        playpenPanel.add( playpenOffBtn );

        // Add tabs
        tabbedPane.addTab( "Bienvenido", createWelcomePanel() );
        tabbedPane.addTab( "Historial de transacciones", transactionsPanel );
        tabbedPane.addTab( "Estadísticas ATM", statsPanel );
        tabbedPane.addTab( "Cargar ATM", loadPanel );
        tabbedPane.addTab( ":D", playpenPanel );
        tabbedPane.addTab( "Cerrar sesión", null );

        // Tab change logic
        tabbedPane.addChangeListener(e -> {
            int idx = tabbedPane.getSelectedIndex();
            switch (idx) {
                case 0: // Welcome
                    // Optionally clear or hide content, or do nothing
                    break;
                case 1: // Transactions
                    tableModel.setRowCount(0);
                    List<TransactionDTO> trnsct = new TransactionService().getAllTransactions();
                    for ( TransactionDTO t : trnsct ) {
                        String ts = t.getTimestamp().toString();
                        String shortTs = ts.length() >= 19 ? ts.substring(0, 19) : ts;
                        tableModel.addRow(new Object[]{
                                t.getType(),
                                String.format( "%.2f", t.getAmount() ),
                                shortTs
                        });
                    }
                    break;
                case 2: // Stats
                    statsArea.setText( new TransactionService().getATMStatsString() );
                    break;
                case 3: // Load ATM
                    loadAmountField.setText("");
                    break;
                case 4: // Playpen Mode
                    break;
                case 5: // Logout
                    logoutListener.actionPerformed( null );
                    break;
            }
        });

        // Button actions
        confirmLoadBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble( loadAmountField.getText() );
                PlaypenATM.getInstance().fill( amount ); // For this we call the singleton ATM instance
                messageLabel.setText( "ATM loaded with $" + String.format("%.2f", amount) );
                updateStats( statsArea ); // Refresh stats after loading
            } catch ( Exception ex ) {
                messageLabel.setText("Invalid amount.");
            }
        });

        playpenOnBtn.addActionListener(e -> {
            new AccountService().activatePlaypenMode(); // Method to activate playpen mode. Use it only under orders from the table.
            messageLabel.setText("Playpen Mode ACTIVATED. All accounts blocked.");
        });

        playpenOffBtn.addActionListener(e -> {
            new AccountService().deactivatePlaypenMode();
            messageLabel.setText("Playpen Mode DEACTIVATED. All accounts active.");
        });

        add( tabbedPane, BorderLayout.CENTER );
        tabbedPane.setSelectedIndex(0);
    }
}