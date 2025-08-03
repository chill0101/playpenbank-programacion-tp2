// src/main/java/com/playpenbank/ui/window/ClientMenuWindow.java
package com.playpenbank.ui.window;

import com.playpenbank.dto.UserDTO;
import com.playpenbank.dto.AccountDTO;
import com.playpenbank.dto.TransactionDTO;
import com.playpenbank.model.PlaypenATM;
import com.playpenbank.service.AccountService;
import com.playpenbank.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientMenuWindow extends JPanel {
    private final UserDTO user;  // We need the DTO to fetch account and transactions
    private final AccountService accountService = new AccountService(); // Service to handle account operations
    private final TransactionService transactionService = new TransactionService(); // Service to handle transaction operations
    private final JLabel messageLabel; // JLable is used to display messages

    private JPanel createWelcomePanel() { // Constructs the welcome panel
        // Create a welcome panel with user information
        AccountDTO account = accountService.getAccountByClientId(user.getId()); // Fetch account details
        String accountNumber = ( account != null ) ? account.getAccountNumber() : "N/A"; // Get account number or set to N/A if not found
        JLabel label = new JLabel( // Welcome message displayed at the session start
                "<html><span style='font-size:18px;color:#278EF5;'>Bienvenido " + user.getName() + "! " +
                        "<br><span style='color:#555;'>DNI: " + user.getDni() +
                        "<br>Account: " + accountNumber +
                        "</span></html>", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Font
        JPanel panel = new JPanel(new BorderLayout()); // Layout for the panel
        panel.add(label, BorderLayout.CENTER); // Add label to the center of the panel
        return panel; // Return the constructed panel
    }

    /**
     * - Top: message label
     * - Tabbed navigation
     * - Balance Panel=
     * - Transactions Panel
     * - Deposit Panel
     * - Withdraw Panel
     * - Transfer Panel
     * - Buttons for actions
    * */
    public ClientMenuWindow( UserDTO user, ActionListener logoutListener ) {
        this.user = user;
        setLayout( new BorderLayout( 10, 10 ) ); // Set layout for the main panel

        //-- Top: message label
        messageLabel = new JLabel( " " );
        messageLabel.setForeground( Color.BLUE ); // SetForeground is to set font color
        add( messageLabel, BorderLayout.NORTH ); // add the message in NORTH position

        //-- Tabbed navigation
        JTabbedPane tabbedPane = new JTabbedPane( JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT ); // Create a tabbed pane for navigation. What we call 'pestañas'
        tabbedPane.setFont( new Font( "Segoe UI", Font.PLAIN, 13 ) ) ; // Set font for the tabs

        //-- Balance Panel
        JPanel balancePanel = new JPanel( new BorderLayout() );
        JLabel balanceLabel = new JLabel( "", SwingConstants.CENTER );
        balancePanel.add( balanceLabel, BorderLayout.CENTER );

        //-- Transactions Panel
        JPanel transactionsPanel = new JPanel( new BorderLayout() );
        DefaultTableModel tableModel = new DefaultTableModel( new String[]{"Tipo", "Monto", "Fecha y hora"}, 0 );
        JTable table = new JTable( tableModel );
        transactionsPanel.add( new JScrollPane(table), BorderLayout.CENTER );

        //-- Deposit Panel
        JPanel depositPanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        JTextField depositField = new JTextField( 10 );
        JButton confirmDepositBtn = new JButton( "Depositar" );
        depositPanel.add(new JLabel( "Monto:" ));
        depositPanel.add( depositField );
        depositPanel.add( confirmDepositBtn );

        //-- Withdraw Panel
        JPanel withdrawPanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        JTextField withdrawField = new JTextField( 10 );
        JButton confirmWithdrawBtn = new JButton( "Retirar" );
        withdrawPanel.add( new JLabel( "Monto:" ) );
        withdrawPanel.add( withdrawField )  ;
        withdrawPanel.add( confirmWithdrawBtn );

        //-- Transfer Panel
        JPanel transferPanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        JTextField transferAccountField = new JTextField( 10 );
        JTextField transferAmountField = new JTextField( 10 );
        JButton confirmTransferBtn = new JButton( "Transferir" );
        transferPanel.add( new JLabel( "Cuenta destino:" ) );
        transferPanel.add( transferAccountField );
        transferPanel.add( new JLabel( "Monto:" ) );
        transferPanel.add( transferAmountField );
        transferPanel.add( confirmTransferBtn );

        //-- Add tabs
        tabbedPane.addTab( "Bienvenido", createWelcomePanel() );
        tabbedPane.addTab( "Saldo", balancePanel );
        tabbedPane.addTab( "Historial", transactionsPanel );
        tabbedPane.addTab( "Depositar", depositPanel );
        tabbedPane.addTab( "Retirar", withdrawPanel );
        tabbedPane.addTab( "Transferir", transferPanel );
        tabbedPane.addTab( "Cerrar sesión", null );

        // -- Tab change logic
        tabbedPane.addChangeListener(e -> {
            int idx = tabbedPane.getSelectedIndex();
            switch ( idx ) {
                case 0: // Welcome
                    break;
                case 1: // Balance
                    AccountDTO account = accountService.getAccountByClientId( user.getId() );
                    if ( account != null ) {
                        balanceLabel.setText( "Saldo disponible: $" + String.format("%.2f", account.getBalance()) );
                    } else {
                        balanceLabel.setText( "No se encontró la cuenta." );
                    }
                    break;
                case 2: // Transactions
                    tableModel.setRowCount( 0 );
                    List<TransactionDTO> movimientos = transactionService.getMovementsByClientId( user.getId() );
                    for ( TransactionDTO t : movimientos ) {
                        tableModel.addRow(new Object[]{
                                t.getType(),
                                String.format("%.2f", t.getAmount()),
                                t.getTimestamp().replace('T', ' ').substring(0, 19)
                        });
                    }
                    break;
                case 3: // Deposit
                    depositField.setText("");
                    break;
                case 4: // Withdraw
                    withdrawField.setText("");
                    break;
                case 5: // Transfer
                    transferAccountField.setText("");
                    transferAmountField.setText("");
                    break;
                case 6: // Logout
                    logoutListener.actionPerformed( null );
                    break;
            }
        });

        // -- Button actions
        confirmDepositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble( depositField.getText());
                String result = PlaypenATM.getInstance().depositFromClient( user.getId(), amount );
                if ( "OK".equals(result) ) {
                    messageLabel.setText( "Depósito exitoso." );
                } else {
                    messageLabel.setText( result );
                }
            } catch (Exception ex) {
                messageLabel.setText( "Monto inválido. Intenta nuevamente." );
            }
        });

        confirmWithdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble( withdrawField.getText() );
                String result = PlaypenATM.getInstance().withdrawFromClient( user.getId(), amount );
                if ( "OK".equals(result) ) {
                    messageLabel.setText( "Retiro exitoso." );
                } else {
                    messageLabel.setText(result);
                }
            } catch (Exception ex) {
                messageLabel.setText( "Monto inválido. Intenta nuevamente." );
            }
        });

        confirmTransferBtn.addActionListener(e -> {
            try {
                String destAccount = transferAccountField.getText();
                double amount = Double.parseDouble( transferAmountField.getText() );
                boolean ok = accountService.transfer( user.getId(), destAccount, amount );
                if ( ok ) {
                    messageLabel.setText( "Transferencia exitoso." );
                } else {
                    messageLabel.setText( "Transferencia fallida. Chequear datos." );
                }
            } catch ( Exception ex ) {
                messageLabel.setText( "Datos de transferencia inválidos." );

            }
        });

        add( tabbedPane, BorderLayout.CENTER );
        tabbedPane.setSelectedIndex(0);
    }
}