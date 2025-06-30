// Complete GUI Work by 21f9417 Ali Masood
// importpoem GUI work by Malaika Tariq
package presentationLayer;

import businessLogicLayer.IBLLFacade;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import transferObject.BookTO;
import transferObject.PoemsTO;
import transferObject.RootTO;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import main.XAMPPManager;

public class GUI_PO extends javax.swing.JFrame {

    private boolean editBookTitleChck = false;
    private boolean newPoemChck = false;
    private boolean newVerseInPoemChck = false;
    private boolean showSinglePoemChck = false;
    private boolean editPoemTitleChck = false;
    private boolean editVerseInPoemChck = false;
    private boolean poemRepeatInTableChck = false;
    private boolean bookRepeatInTableChck = false;
    boolean rootRepeatInTableChck = false;
    boolean newBookChck = false;
    boolean assignRootChck = false;
    boolean versesInRootTableChck = false;
    boolean languageChck = false;
    ArrayList<String> tokensList = new ArrayList<>();
    ArrayList<PoemsTO> poemByRoot = null;
    PanelRound tokensInTokenizePanelObj;
    PanelRound assignRootsPanelObj;
    int newVerseRow = 0;

    String bookTSendToOtherTab;
    String poemTSendToOtherTab;
    String misra1SendToOtherTab;
    String misra2SendToOtherTab;
    JFrame frame = new JFrame(); // Option Pane message dialog

    // object of BLL facade
    private IBLLFacade ibllFacade;

    public GUI_PO(IBLLFacade ibllFacade) {
        this.ibllFacade = ibllFacade;
        initComponents();
        setTabbedPaneUI();
        setComboBoxListSearching();
        btnsHoverStateColorChange();
        // by default language to Arabic (1)
        setLanguageOfApplication(1);
        dragAndDropFilePath();
    }

    private void setTabbedPaneUI() {
        menuTabsPane = new JTabbedPane() {
            @Override
            public void updateUI() {
                super.setUI(new TabbedPaneUI());
            }
        };
        menuTabsPane.putClientProperty("FlatLaf.style", "tabType: card; cardTabSelectionHeight: 0");
    }

    // enable us to search anything from dropdown list
    // used swingx all jar library
    private void setComboBoxListSearching() {
        AutoCompleteDecorator.decorate(bookTitleinPoemList);
        AutoCompleteDecorator.decorate(bookTitleinBooksList);
        AutoCompleteDecorator.decorate(rootsInRootsList);
        AutoCompleteDecorator.decorate(poemTitleinPoemList);
    }

    // to change the default color of buttons on hovor to custom
    private void btnsHoverStateColorChange() {
        createPoemBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createPoemBtn.setBackground(new Color(182, 141, 64));
                createPoemBtn.setForeground(new Color(244, 235, 208));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createPoemBtn.setBackground(new Color(244, 235, 204));
                createPoemBtn.setForeground(new Color(46, 103, 90));
            }
        });
        importPoemPanelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                importPoemPanelBtn.setBackground(new Color(182, 141, 64));
                importPoemPanelBtn.setForeground(new Color(244, 235, 208));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                importPoemPanelBtn.setBackground(new Color(244, 235, 204));
                importPoemPanelBtn.setForeground(new Color(46, 103, 90));
            }
        });
    }

    // to switch between English and Arabic
    // 0 for English, 1 for Arabic
    private void setLanguageOfApplication(int type) {
        if (type == 0) { // For English
            changeLangHelpingFunc("English");
            appTitle.setFont(new Font("Calibri", 1, 21));
            topPanel.add(appTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 14, -1, -1));
            languageChck = false;
        } else if (type == 1) { // For Arabic
            changeLangHelpingFunc("Arabic");
            appTitle.setFont(new Font("Calibri", 1, 24));
            topPanel.add(appTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 14, -1, -1));
            languageChck = true;
        }
    }

    public static void applyLaFOnJFrame() {
        try {
            // Here you can select the selected theme class name in JTatto
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            //Logger.getLogger(GUI_PO.class.getName()).log(Level.SEVERE, null, ex);
        }
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("TextComponent.arc", 20);
        UIManager.put("Component.arrowType", "chevron");
        UIManager.put("Component.focusWidth", 2);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
        UIManager.put("TabbedPane.selectedBackground", new Color(182, 141, 64));
        UIManager.put("[style]Panel.myRoundPanel",
                "[light]background: tint(@background,50%);"
                + "[dark]background: shade(@background,15%);"
                + "[light]border: 16,16,16,16,shade(@background,10%),,20;"
                + "[dark]border: 16,16,16,16,tint(@background,10%),,20");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        poemPanelChckBtnGroup = new javax.swing.ButtonGroup();
        viewVersePopupMenu = new javax.swing.JPopupMenu();
        assignRoots = new javax.swing.JMenuItem();
        assignTokens = new javax.swing.JMenuItem();
        changeLanguageMenu = new javax.swing.JPopupMenu();
        setLanguageMenu = new javax.swing.JMenu();
        englishMenuItem = new javax.swing.JMenuItem();
        arabicMenuItem = new javax.swing.JMenuItem();
        mainPanel = new javax.swing.JPanel();
        menuTabsPane = new javax.swing.JTabbedPane();
        PanelRound dashboardPanel;
        dashboardPanel = new PanelRound();
        PanelRound booksPanel;
        booksPanel = new PanelRound();
        PanelRound booksCRUDPanelRound;
        booksCRUDPanelRound = new PanelRound();
        bookTablePanel = new javax.swing.JPanel();
        booksTableScrollPane = new javax.swing.JScrollPane();
        booksTable = new javax.swing.JTable();
        PanelRound bookTitlePanelinBook;
        bookTitlePanelinBook = new PanelRound();
        bookinBooksTitleLbl = new javax.swing.JLabel();
        bookTitleinBooksList = new javax.swing.JComboBox<>();
        PanelRound bookTypePanelinBook;
        bookTypePanelinBook = new PanelRound();
        newBookRadioBtn = new javax.swing.JRadioButton();
        createBookBtn = new javax.swing.JButton();
        PanelRound poemsPanel;
        poemsPanel = new PanelRound();
        PanelRound poemsCRUDPanel;
        poemsCRUDPanel = new PanelRound();
        importPoemPanel = new javax.swing.JPanel();
        importPoemBrowsePanel = new JPanel();
        browseTxtField = new JTextField();
        browseFilePathBtn = new javax.swing.JButton();
        importPoemBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        bookTitleinImportPoemList = new javax.swing.JComboBox<>();
        bookinPoemTitleLbl1 = new javax.swing.JLabel();
        closeImportPoemPanelBtn = new javax.swing.JButton();
        createPoemBtn = new javax.swing.JButton();
        PanelRound bookTitlePanelinPoem;
        bookTitlePanelinPoem = new PanelRound();
        bookinPoemTitleLbl = new javax.swing.JLabel();
        bookTitleinPoemList = new javax.swing.JComboBox<>();
        poemTitleLbl = new javax.swing.JLabel();
        poemTitleinPoemList = new javax.swing.JComboBox<>();
        PanelRound poemTypePanelinPoemRound;
        poemTypePanelinPoemRound = new PanelRound();
        newPoemRadioBtn = new javax.swing.JRadioButton();
        existingPoemRadioBtn = new javax.swing.JRadioButton();
        importPoemPanelBtn = new javax.swing.JButton();
        poemTablePanel = new javax.swing.JPanel();
        poemsTableScrollPane = new javax.swing.JScrollPane();
        poemsTable = new javax.swing.JTable();
        PanelRound tokenPanel;
        tokenPanel = new PanelRound();
        PanelRound tokensInnerPanel;
        tokensInnerPanel = new PanelRound();
        PanelRound tokenizePanel;
        tokenizePanel = new PanelRound();
        PanelRound tokenizationPanel;
        tokenizationPanel = new PanelRound();
        PanelRound verseInTokenizePanel;
        verseInTokenizePanel = new PanelRound();
        misra2InTokeniz = new javax.swing.JTextField();
        misra1InTokeniz = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        splitVerseBtn = new javax.swing.JButton();
        saveTokensBtn = new javax.swing.JButton();
        tokensTablePanel = new javax.swing.JPanel();
        tokensTableScrollPane = new javax.swing.JScrollPane();
        tokensTable = new javax.swing.JTable();
        PanelRound rootsPanel;
        rootsPanel = new PanelRound();
        PanelRound rootsCRUDPanelRound;
        rootsCRUDPanelRound = new PanelRound();
        PanelRound assignRootsPanel;
        assignRootsPanel = new PanelRound();
        PanelRound assignRootsInnerPanel;
        assignRootsInnerPanel = new PanelRound();
        PanelRound suggRootsPanelinAssignRoots;
        suggRootsPanelinAssignRoots = new PanelRound();
        bookinBooksTitleLbl2 = new javax.swing.JLabel();
        rootsInAssignRootsList = new javax.swing.JComboBox<>();
        manualRootsTxtField = new javax.swing.JTextField();
        bookinBooksTitleLbl3 = new javax.swing.JLabel();
        assignRootsBtn = new javax.swing.JButton();
        PanelRound versesAssignPoemsPanel;
        versesAssignPoemsPanel = new PanelRound();
        misra1InRootsTxtField = new javax.swing.JTextField();
        misra2InRootsTxtField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        assignRootsTablePanel = new javax.swing.JPanel();
        assignRootsTableScrollPane = new javax.swing.JScrollPane();
        assignRootsTable = new javax.swing.JTable();
        closeAssignRootsPanelBtn = new javax.swing.JButton();
        rootsTablePanel = new javax.swing.JPanel();
        rootsTableScrollPane = new javax.swing.JScrollPane();
        rootsTable = new javax.swing.JTable();
        PanelRound rootPanelinRoots;
        rootPanelinRoots = new PanelRound();
        bookinBooksTitleLbl1 = new javax.swing.JLabel();
        rootsInRootsList = new javax.swing.JComboBox<>();
        PanelRound searchModePanelinRoot;
        searchModePanelinRoot = new PanelRound();
        searchRootModeRadioBtn = new javax.swing.JRadioButton();
        PanelRound leftSidePanel;
        leftSidePanel = new PanelRound();
        logo = new javax.swing.JLabel();
        topPanel = new javax.swing.JPanel();
        appTitle = new javax.swing.JLabel();
        changeLanguageBtn = new javax.swing.JButton();

        assignRoots.setBackground(new java.awt.Color(244, 235, 208));
        assignRoots.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        assignRoots.setForeground(new java.awt.Color(46, 103, 90));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("presentationLayer/Bundle"); // NOI18N
        assignRoots.setText(bundle.getString("GUI_PO.assignRoots.text")); // NOI18N
        assignRoots.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignRootsActionPerformed(evt);
            }
        });
        viewVersePopupMenu.add(assignRoots);

        assignTokens.setBackground(new java.awt.Color(244, 235, 208));
        assignTokens.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        assignTokens.setForeground(new java.awt.Color(46, 103, 90));
        assignTokens.setText(bundle.getString("GUI_PO.assignTokens.text")); // NOI18N
        assignTokens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignTokensActionPerformed(evt);
            }
        });
        viewVersePopupMenu.add(assignTokens);

        changeLanguageMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        setLanguageMenu.setForeground(new java.awt.Color(46, 103, 90));
        setLanguageMenu.setText(bundle.getString("GUI_PO.setLanguageMenu.text")); // NOI18N
        setLanguageMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setLanguageMenu.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        englishMenuItem.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        englishMenuItem.setForeground(new java.awt.Color(46, 103, 90));
        englishMenuItem.setText(bundle.getString("GUI_PO.englishMenuItem.text")); // NOI18N
        englishMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                englishMenuItemActionPerformed(evt);
            }
        });
        setLanguageMenu.add(englishMenuItem);

        arabicMenuItem.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        arabicMenuItem.setForeground(new java.awt.Color(46, 103, 90));
        arabicMenuItem.setText(bundle.getString("GUI_PO.arabicMenuItem.text")); // NOI18N
        arabicMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arabicMenuItemActionPerformed(evt);
            }
        });
        setLanguageMenu.add(arabicMenuItem);

        changeLanguageMenu.add(setLanguageMenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainPanel.setBackground(new java.awt.Color(182, 141, 64));

        menuTabsPane.setBackground(new java.awt.Color(214, 173, 96));
        menuTabsPane.setForeground(new java.awt.Color(244, 235, 208));
        menuTabsPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        menuTabsPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuTabsPane.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        menuTabsPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuTabsPaneMouseClicked(evt);
            }
        });

        dashboardPanel.setBackground(new java.awt.Color(244, 235, 208));
        dashboardPanel.setRoundTopRight(25);
        dashboardPanel.setRoundBottomLeft(25);
        dashboardPanel.setRoundBottomRight(25);
        dashboardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        menuTabsPane.addTab(bundle.getString("GUI_PO.dashboardPanel.TabConstraints.tabTitle"), dashboardPanel); // NOI18N

        booksPanel.setBackground(new java.awt.Color(244, 235, 208));
        booksPanel.setRoundBottomRight(25);
        booksPanel.setRoundBottomLeft(25);
        booksPanel.setRoundBottomRight(25);
        booksPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        booksCRUDPanelRound.setBackground(new java.awt.Color(182, 141, 64));
        booksCRUDPanelRound.setAllCornersRound(25);
        booksCRUDPanelRound.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        booksTableScrollPane.setBackground(new java.awt.Color(244, 235, 208));
        booksTableScrollPane.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        booksTable.setBackground(new java.awt.Color(244, 235, 208));
        booksTable.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        booksTable.setForeground(new java.awt.Color(46, 103, 90));
        booksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book #", "Title", "Author", "Poems Count", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        booksTable.setGridColor(new java.awt.Color(204, 204, 204));
        booksTable.setRowHeight(34);
        booksTable.setSelectionBackground(new java.awt.Color(214, 173, 96));
        booksTable.setSelectionForeground(new java.awt.Color(244, 235, 208));
        booksTable.setShowGrid(true);
        booksTable.setShowVerticalLines(false);
        booksTable.getTableHeader().setReorderingAllowed(false);
        booksTableScrollPane.setViewportView(booksTable);
        if (booksTable.getColumnModel().getColumnCount() > 0) {
            booksTable.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("GUI_PO.booksTable.columnModel.title0")); // NOI18N
            booksTable.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("GUI_PO.booksTable.columnModel.title1")); // NOI18N
            booksTable.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("GUI_PO.booksTable.columnModel.title2")); // NOI18N
            booksTable.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("GUI_PO.booksTable.columnModel.title3")); // NOI18N
            booksTable.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("GUI_PO.booksTable.columnModel.title4")); // NOI18N
        }

        javax.swing.GroupLayout bookTablePanelLayout = new javax.swing.GroupLayout(bookTablePanel);
        bookTablePanel.setLayout(bookTablePanelLayout);
        bookTablePanelLayout.setHorizontalGroup(
            bookTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(booksTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );
        bookTablePanelLayout.setVerticalGroup(
            bookTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(booksTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );

        booksCRUDPanelRound.add(bookTablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 870, 290));

        bookTitlePanelinBook.setBackground(new java.awt.Color(244, 235, 208));
        bookTitlePanelinBook.setAllCornersRound(25);
        bookTitlePanelinBook.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookinBooksTitleLbl.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookinBooksTitleLbl.setForeground(new java.awt.Color(46, 103, 90));
        bookinBooksTitleLbl.setText(bundle.getString("GUI_PO.bookinBooksTitleLbl.text")); // NOI18N
        bookTitlePanelinBook.add(bookinBooksTitleLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        bookTitleinBooksList.setBackground(new java.awt.Color(244, 235, 208));
        bookTitleinBooksList.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookTitleinBooksList.setForeground(new java.awt.Color(46, 103, 90));
        bookTitleinBooksList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookTitleinBooksList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookTitleinBooksListActionPerformed(evt);
            }
        });
        bookTitlePanelinBook.add(bookTitleinBooksList, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 200, 30));

        booksCRUDPanelRound.add(bookTitlePanelinBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 350, 90));

        bookTypePanelinBook.setBackground(new java.awt.Color(244, 235, 208));
        bookTypePanelinBook.setAllCornersRound(25);
        bookTypePanelinBook.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        newBookRadioBtn.setBackground(new java.awt.Color(244, 235, 208));
        poemPanelChckBtnGroup.add(newBookRadioBtn);
        newBookRadioBtn.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        newBookRadioBtn.setForeground(new java.awt.Color(46, 103, 90));
        newBookRadioBtn.setText(bundle.getString("GUI_PO.newBookRadioBtn.text")); // NOI18N
        newBookRadioBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookTypePanelinBook.add(newBookRadioBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        booksCRUDPanelRound.add(bookTypePanelinBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 50, 120, 40));

        createBookBtn.setBackground(new java.awt.Color(244, 235, 208));
        createBookBtn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        createBookBtn.setForeground(new java.awt.Color(46, 103, 90));
        createBookBtn.setText(bundle.getString("GUI_PO.createBookBtn.text")); // NOI18N
        createBookBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createBookBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBookBtnActionPerformed(evt);
            }
        });
        booksCRUDPanelRound.add(createBookBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 120, 40));
        //createPoemBtn.setEnabled(false);

        booksPanel.add(booksCRUDPanelRound, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 51, 950, 520));

        menuTabsPane.addTab(bundle.getString("GUI_PO.booksPanel.TabConstraints.tabTitle"), booksPanel); // NOI18N

        poemsPanel.setBackground(new java.awt.Color(244, 235, 208));
        poemsPanel.setRoundTopRight(25);
        poemsPanel.setRoundBottomLeft(25);
        poemsPanel.setRoundBottomRight(25);

        poemsCRUDPanel.setBackground(new java.awt.Color(182, 141, 64));
        poemsCRUDPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        poemsCRUDPanel.setAllCornersRound(25);

        importPoemPanel.setBackground(new java.awt.Color(214, 173, 96));
        importPoemPanel.setForeground(new java.awt.Color(18, 38, 32));
        importPoemPanel.setVisible(false);
        importPoemPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        importPoemBrowsePanel.setBackground(new java.awt.Color(182, 141, 64));

        browseTxtField.setBackground(new java.awt.Color(244, 235, 208));
        browseTxtField.setBorder(null);
        browseTxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                browseTxtFieldKeyPressed(evt);
            }
        });

        browseFilePathBtn.setBackground(new java.awt.Color(182, 141, 64));
        browseFilePathBtn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        browseFilePathBtn.setForeground(new java.awt.Color(244, 235, 208));
        browseFilePathBtn.setText(bundle.getString("GUI_PO.browseFilePathBtn.text")); // NOI18N
        browseFilePathBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browseFilePathBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFilePathBtnActionPerformed(evt);
            }
        });

        importPoemBtn.setBackground(new java.awt.Color(182, 141, 64));
        importPoemBtn.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        importPoemBtn.setForeground(new java.awt.Color(244, 235, 208));
        importPoemBtn.setText(bundle.getString("GUI_PO.importPoemBtn.text")); // NOI18N
        importPoemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importPoemBtnActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(244, 235, 208));
        jLabel8.setText(bundle.getString("GUI_PO.jLabel8.text")); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentationLayer/images/cloud.png"))); // NOI18N

        javax.swing.GroupLayout importPoemBrowsePanelLayout = new javax.swing.GroupLayout(importPoemBrowsePanel);
        importPoemBrowsePanel.setLayout(importPoemBrowsePanelLayout);
        importPoemBrowsePanelLayout.setHorizontalGroup(
            importPoemBrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importPoemBrowsePanelLayout.createSequentialGroup()
                .addGroup(importPoemBrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(importPoemBrowsePanelLayout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addGroup(importPoemBrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(browseFilePathBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(importPoemBrowsePanelLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel11))
                    .addGroup(importPoemBrowsePanelLayout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(importPoemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(importPoemBrowsePanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(browseTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        importPoemBrowsePanelLayout.setVerticalGroup(
            importPoemBrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importPoemBrowsePanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(browseFilePathBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(browseTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(importPoemBtn)
                .addGap(27, 27, 27))
        );

        importPoemPanel.add(importPoemBrowsePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        bookTitleinImportPoemList.setBackground(new java.awt.Color(244, 235, 208));
        bookTitleinImportPoemList.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookTitleinImportPoemList.setForeground(new java.awt.Color(46, 103, 90));
        bookTitleinImportPoemList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        importPoemPanel.add(bookTitleinImportPoemList, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 200, 30));

        bookinPoemTitleLbl1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookinPoemTitleLbl1.setForeground(new java.awt.Color(244, 235, 208));
        bookinPoemTitleLbl1.setText(bundle.getString("GUI_PO.bookinPoemTitleLbl1.text")); // NOI18N
        importPoemPanel.add(bookinPoemTitleLbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 38, 70, 20));

        closeImportPoemPanelBtn.setBackground(new java.awt.Color(244, 235, 208));
        closeImportPoemPanelBtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        closeImportPoemPanelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentationLayer/images/close.png"))); // NOI18N
        closeImportPoemPanelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeImportPoemPanelBtnActionPerformed(evt);
            }
        });
        importPoemPanel.add(closeImportPoemPanelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        poemsCRUDPanel.add(importPoemPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 500, 430));

        createPoemBtn.setBackground(new java.awt.Color(244, 235, 208));
        createPoemBtn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        createPoemBtn.setForeground(new java.awt.Color(46, 103, 90));
        createPoemBtn.setText(bundle.getString("GUI_PO.createPoemBtn.text")); // NOI18N
        createPoemBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createPoemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPoemBtnActionPerformed(evt);
            }
        });
        poemsCRUDPanel.add(createPoemBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 170, 120, 30));
        //createPoemBtn.setEnabled(false);

        bookTitlePanelinPoem.setBackground(new java.awt.Color(244, 235, 208));
        bookTitlePanelinPoem.setAllCornersRound(25);
        bookTitlePanelinPoem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookinPoemTitleLbl.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookinPoemTitleLbl.setForeground(new java.awt.Color(46, 103, 90));
        bookinPoemTitleLbl.setText(bundle.getString("GUI_PO.bookinPoemTitleLbl.text")); // NOI18N
        bookTitlePanelinPoem.add(bookinPoemTitleLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        bookTitleinPoemList.setBackground(new java.awt.Color(244, 235, 208));
        bookTitleinPoemList.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookTitleinPoemList.setForeground(new java.awt.Color(46, 103, 90));
        bookTitleinPoemList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookTitleinPoemList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookTitleinPoemListActionPerformed(evt);
            }
        });
        bookTitlePanelinPoem.add(bookTitleinPoemList, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 200, 30));

        poemTitleLbl.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        poemTitleLbl.setForeground(new java.awt.Color(46, 103, 90));
        poemTitleLbl.setText(bundle.getString("GUI_PO.poemTitleLbl.text")); // NOI18N
        bookTitlePanelinPoem.add(poemTitleLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        poemTitleinPoemList.setBackground(new java.awt.Color(244, 235, 208));
        poemTitleinPoemList.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        poemTitleinPoemList.setForeground(new java.awt.Color(46, 103, 90));
        poemTitleinPoemList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        poemTitleinPoemList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poemTitleinPoemListActionPerformed(evt);
            }
        });
        bookTitlePanelinPoem.add(poemTitleinPoemList, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 200, 30));
        poemTitleinPoemList.setEnabled(false);

        poemsCRUDPanel.add(bookTitlePanelinPoem, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 350, 140));

        poemTypePanelinPoemRound.setBackground(new java.awt.Color(244, 235, 208));
        poemTypePanelinPoemRound.setAllCornersRound(25);

        newPoemRadioBtn.setBackground(new java.awt.Color(244, 235, 208));
        poemPanelChckBtnGroup.add(newPoemRadioBtn);
        newPoemRadioBtn.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        newPoemRadioBtn.setForeground(new java.awt.Color(46, 103, 90));
        newPoemRadioBtn.setText(bundle.getString("GUI_PO.newPoemRadioBtn.text")); // NOI18N
        newPoemRadioBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newPoemRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPoemRadioBtnActionPerformed(evt);
            }
        });

        existingPoemRadioBtn.setBackground(new java.awt.Color(244, 235, 208));
        poemPanelChckBtnGroup.add(existingPoemRadioBtn);
        existingPoemRadioBtn.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        existingPoemRadioBtn.setForeground(new java.awt.Color(46, 103, 90));
        existingPoemRadioBtn.setText(bundle.getString("GUI_PO.existingPoemRadioBtn.text")); // NOI18N
        existingPoemRadioBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingPoemRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingPoemRadioBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout poemTypePanelinPoemRoundLayout = new javax.swing.GroupLayout(poemTypePanelinPoemRound);
        poemTypePanelinPoemRound.setLayout(poemTypePanelinPoemRoundLayout);
        poemTypePanelinPoemRoundLayout.setHorizontalGroup(
            poemTypePanelinPoemRoundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(poemTypePanelinPoemRoundLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(poemTypePanelinPoemRoundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(existingPoemRadioBtn)
                    .addComponent(newPoemRadioBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        poemTypePanelinPoemRoundLayout.setVerticalGroup(
            poemTypePanelinPoemRoundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, poemTypePanelinPoemRoundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(newPoemRadioBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(existingPoemRadioBtn)
                .addContainerGap())
        );

        poemsCRUDPanel.add(poemTypePanelinPoemRound, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 120, 60));

        importPoemPanelBtn.setBackground(new java.awt.Color(244, 235, 208));
        importPoemPanelBtn.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        importPoemPanelBtn.setForeground(new java.awt.Color(46, 103, 90));
        importPoemPanelBtn.setText(bundle.getString("GUI_PO.importPoemPanelBtn.text")); // NOI18N
        importPoemPanelBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        importPoemPanelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importPoemPanelBtnActionPerformed(evt);
            }
        });
        poemsCRUDPanel.add(importPoemPanelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 60, 120, 30));

        poemsTableScrollPane.setBackground(new java.awt.Color(244, 235, 208));
        poemsTableScrollPane.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        poemsTable.setBackground(new java.awt.Color(244, 235, 208));
        poemsTable.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        poemsTable.setForeground(new java.awt.Color(46, 103, 90));
        poemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "--", "--", "Actions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        poemsTable.setGridColor(new java.awt.Color(204, 204, 204));
        poemsTable.setRowHeight(34);
        poemsTable.setSelectionBackground(new java.awt.Color(214, 173, 96));
        poemsTable.setSelectionForeground(new java.awt.Color(244, 235, 208));
        poemsTable.setShowGrid(true);
        poemsTable.setShowVerticalLines(false);
        poemsTable.getTableHeader().setReorderingAllowed(false);
        poemsTableScrollPane.setViewportView(poemsTable);
        if (poemsTable.getColumnModel().getColumnCount() > 0) {
            poemsTable.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("GUI_PO.poemsTable.columnModel.title0")); // NOI18N
            poemsTable.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("GUI_PO.poemsTable.columnModel.title1")); // NOI18N
            poemsTable.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("GUI_PO.poemsTable.columnModel.title2")); // NOI18N
            poemsTable.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("GUI_PO.poemsTable.columnModel.title3")); // NOI18N
        }

        javax.swing.GroupLayout poemTablePanelLayout = new javax.swing.GroupLayout(poemTablePanel);
        poemTablePanel.setLayout(poemTablePanelLayout);
        poemTablePanelLayout.setHorizontalGroup(
            poemTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(poemsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );
        poemTablePanelLayout.setVerticalGroup(
            poemTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(poemsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );

        poemsCRUDPanel.add(poemTablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 870, 270));

        javax.swing.GroupLayout poemsPanelLayout = new javax.swing.GroupLayout(poemsPanel);
        poemsPanel.setLayout(poemsPanelLayout);
        poemsPanelLayout.setHorizontalGroup(
            poemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, poemsPanelLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(poemsCRUDPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        poemsPanelLayout.setVerticalGroup(
            poemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, poemsPanelLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(poemsCRUDPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        menuTabsPane.addTab(bundle.getString("GUI_PO.poemsPanel.TabConstraints.tabTitle"), poemsPanel); // NOI18N

        tokenPanel.setBackground(new java.awt.Color(244, 235, 208));
        tokenPanel.setRoundTopRight(25);
        tokenPanel.setRoundBottomLeft(25);
        tokenPanel.setRoundBottomRight(25);
        tokenPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tokensInnerPanel.setBackground(new java.awt.Color(182, 141, 64));
        tokensInnerPanel.setAllCornersRound(25);
        tokensInnerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tokenizePanel.setBackground(new java.awt.Color(244, 235, 208));
        tokenizePanel.setAllCornersRound(25);
        tokenizePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tokenizationPanel.setBackground(new java.awt.Color(182, 141, 64));
        tokenizationPanel.setAllCornersRound(25);
        tokenizationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verseInTokenizePanel.setBackground(new java.awt.Color(244, 235, 208));
        verseInTokenizePanel.setAllCornersRound(25);
        verseInTokenizePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        misra2InTokeniz.setBackground(new java.awt.Color(244, 235, 208));
        misra2InTokeniz.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        misra2InTokeniz.setForeground(new java.awt.Color(46, 103, 90));
        misra2InTokeniz.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        verseInTokenizePanel.add(misra2InTokeniz, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 28, 260, 34));

        misra1InTokeniz.setBackground(new java.awt.Color(244, 235, 208));
        misra1InTokeniz.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        misra1InTokeniz.setForeground(new java.awt.Color(46, 103, 90));
        misra1InTokeniz.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        verseInTokenizePanel.add(misra1InTokeniz, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 28, 260, 34));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(46, 103, 90));
        jLabel1.setText(bundle.getString("GUI_PO.jLabel1.text")); // NOI18N
        verseInTokenizePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(336, 33, -1, 34));

        splitVerseBtn.setBackground(new java.awt.Color(244, 235, 208));
        splitVerseBtn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        splitVerseBtn.setForeground(new java.awt.Color(46, 103, 90));
        splitVerseBtn.setText(bundle.getString("GUI_PO.splitVerseBtn.text")); // NOI18N
        splitVerseBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        verseInTokenizePanel.add(splitVerseBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 85, 82, -1));

        tokenizationPanel.add(verseInTokenizePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 690, 130));

        saveTokensBtn.setBackground(new java.awt.Color(244, 235, 208));
        saveTokensBtn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        saveTokensBtn.setForeground(new java.awt.Color(46, 103, 90));
        saveTokensBtn.setText(bundle.getString("GUI_PO.saveTokensBtn.text")); // NOI18N
        saveTokensBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveTokensBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTokensBtnActionPerformed(evt);
            }
        });
        tokenizationPanel.add(saveTokensBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, -1, 30));

        tokenizePanel.add(tokenizationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 790, 390));
        PanelRound tokensInTokenizePanel;
        tokensInTokenizePanel = new PanelRound();
        tokensInTokenizePanel.setBackground(new java.awt.Color(244, 235, 208));
        tokensInTokenizePanel.setAllCornersRound(25);
        tokenizationPanel.add(tokensInTokenizePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 690, 140));
        tokensInTokenizePanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tokensInTokenizePanelObj = tokensInTokenizePanel;

        splitVerseBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tokensList.clear();
                if (tokensInTokenizePanel.getComponentCount() != 0) {
                    tokensInTokenizePanel.removeAll();
                    tokensInTokenizePanel.revalidate();
                    tokensInTokenizePanel.repaint();
                }
                String misra1 = misra1InTokeniz.getText();
                String misra2 = misra2InTokeniz.getText();
                if ((!misra1.equals("")) || (!misra2.equals(""))) {
                    tokensInTokenizePanel.removeAll();
                    tokensInTokenizePanel.revalidate();
                    tokensInTokenizePanel.repaint();
                    ArrayList<String> tokens = ibllFacade.returnTokens(misra1, misra2);
                    JTextField[] tokensTxtFields = new JTextField[tokens.size()];
                    for (int i = 0; i < tokens.size(); ++i) {
                        tokensTxtFields[i] = new JTextField(tokens.get(i));
                        tokensTxtFields[i].setBackground(new java.awt.Color(244, 235, 208));
                        tokensTxtFields[i].setFont(new java.awt.Font("Calibri", 1, 14));
                        tokensTxtFields[i].setForeground(new java.awt.Color(46, 103, 90));
                        tokensTxtFields[i].setPreferredSize(new java.awt.Dimension(73, 24));
                        tokensTxtFields[i].setEditable(false);
                        tokensInTokenizePanel.add(tokensTxtFields[i]);
                        tokensList.add(tokensTxtFields[i].getText());
                    }
                    tokensInTokenizePanel.revalidate();
                    tokensInTokenizePanel.repaint();
                }
            }
        });

        tokensInnerPanel.add(tokenizePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 870, 460));
        tokenizePanel.setVisible(false);
        tokenizePanelObj =  tokenizePanel;

        tokensTableScrollPane.setBackground(new java.awt.Color(244, 235, 208));
        tokensTableScrollPane.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        tokensTable.setBackground(new java.awt.Color(244, 235, 208));
        tokensTable.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        tokensTable.setForeground(new java.awt.Color(46, 103, 90));
        tokensTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Token #", "Token", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tokensTable.setGridColor(new java.awt.Color(204, 204, 204));
        tokensTable.setRowHeight(34);
        tokensTable.setSelectionBackground(new java.awt.Color(214, 173, 96));
        tokensTable.setSelectionForeground(new java.awt.Color(244, 235, 208));
        tokensTable.setShowGrid(true);
        tokensTable.setShowVerticalLines(false);
        tokensTable.getTableHeader().setReorderingAllowed(false);
        tokensTableScrollPane.setViewportView(tokensTable);
        if (tokensTable.getColumnModel().getColumnCount() > 0) {
            tokensTable.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("GUI_PO.tokensTable.columnModel.title0")); // NOI18N
            tokensTable.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("GUI_PO.tokensTable.columnModel.title1")); // NOI18N
            tokensTable.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("GUI_PO.tokensTable.columnModel.title2")); // NOI18N
        }

        javax.swing.GroupLayout tokensTablePanelLayout = new javax.swing.GroupLayout(tokensTablePanel);
        tokensTablePanel.setLayout(tokensTablePanelLayout);
        tokensTablePanelLayout.setHorizontalGroup(
            tokensTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tokensTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
        );
        tokensTablePanelLayout.setVerticalGroup(
            tokensTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tokensTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        tokensInnerPanel.add(tokensTablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 850, 430));

        tokenPanel.add(tokensInnerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 33, 950, 540));

        menuTabsPane.addTab(bundle.getString("GUI_PO.tokenPanel.TabConstraints.tabTitle"), tokenPanel); // NOI18N

        rootsPanel.setBackground(new java.awt.Color(244, 235, 208));
        rootsPanel.setRoundTopRight(25);
        rootsPanel.setRoundBottomLeft(25);
        rootsPanel.setRoundBottomRight(25);
        rootsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rootsCRUDPanelRound.setBackground(new java.awt.Color(182, 141, 64));
        rootsCRUDPanelRound.setAllCornersRound(25);
        rootsCRUDPanelRound.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        assignRootsPanel.setAllCornersRound(25);
        assignRootsPanel.setBackground(new java.awt.Color(214, 173, 96));
        assignRootsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        assignRootsPanel.setVisible(false);

        assignRootsInnerPanel.setAllCornersRound(25);
        assignRootsInnerPanel.setBackground(new java.awt.Color(182, 141, 64));
        assignRootsInnerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        suggRootsPanelinAssignRoots.setBackground(new java.awt.Color(244, 235, 208));
        suggRootsPanelinAssignRoots.setAllCornersRound(25);
        suggRootsPanelinAssignRoots.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookinBooksTitleLbl2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookinBooksTitleLbl2.setForeground(new java.awt.Color(46, 103, 90));
        bookinBooksTitleLbl2.setText(bundle.getString("GUI_PO.bookinBooksTitleLbl2.text")); // NOI18N
        suggRootsPanelinAssignRoots.add(bookinBooksTitleLbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        rootsInAssignRootsList.setBackground(new java.awt.Color(244, 235, 208));
        rootsInAssignRootsList.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        rootsInAssignRootsList.setForeground(new java.awt.Color(46, 103, 90));
        rootsInAssignRootsList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rootsInAssignRootsList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rootsInAssignRootsListActionPerformed(evt);
            }
        });
        suggRootsPanelinAssignRoots.add(rootsInAssignRootsList, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, 30));

        manualRootsTxtField.setBackground(new java.awt.Color(244, 235, 208));
        manualRootsTxtField.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        manualRootsTxtField.setForeground(new java.awt.Color(46, 103, 90));
        manualRootsTxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                manualRootsTxtFieldKeyPressed(evt);
            }
        });
        suggRootsPanelinAssignRoots.add(manualRootsTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, 30));

        bookinBooksTitleLbl3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookinBooksTitleLbl3.setForeground(new java.awt.Color(46, 103, 90));
        bookinBooksTitleLbl3.setText(bundle.getString("GUI_PO.bookinBooksTitleLbl3.text")); // NOI18N
        suggRootsPanelinAssignRoots.add(bookinBooksTitleLbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        assignRootsBtn.setBackground(new java.awt.Color(244, 235, 208));
        assignRootsBtn.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        assignRootsBtn.setForeground(new java.awt.Color(46, 103, 90));
        assignRootsBtn.setText(bundle.getString("GUI_PO.assignRootsBtn.text")); // NOI18N
        assignRootsBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        assignRootsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignRootsBtnActionPerformed(evt);
            }
        });
        suggRootsPanelinAssignRoots.add(assignRootsBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 120, 30));

        assignRootsInnerPanel.add(suggRootsPanelinAssignRoots, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 170, 250));

        versesAssignPoemsPanel.setAllCornersRound(25);
        versesAssignPoemsPanel.setBackground(new java.awt.Color(244, 235, 208));
        versesAssignPoemsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        misra1InRootsTxtField.setEditable(false);
        misra1InRootsTxtField.setBackground(new java.awt.Color(244, 235, 208));
        misra1InRootsTxtField.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        misra1InRootsTxtField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        misra1InRootsTxtField.setForeground(new java.awt.Color(46, 103, 90));
        versesAssignPoemsPanel.add(misra1InRootsTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 280, 30));

        misra2InRootsTxtField.setEditable(false);
        misra2InRootsTxtField.setBackground(new java.awt.Color(244, 235, 208));
        misra2InRootsTxtField.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        misra2InRootsTxtField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        misra2InRootsTxtField.setForeground(new java.awt.Color(46, 103, 90));
        versesAssignPoemsPanel.add(misra2InRootsTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 280, 30));

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(46, 103, 90));
        jLabel2.setText(bundle.getString("GUI_PO.jLabel2.text")); // NOI18N
        versesAssignPoemsPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, -1, 34));

        assignRootsInnerPanel.add(versesAssignPoemsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 700, 90));

        assignRootsTableScrollPane.setBackground(new java.awt.Color(244, 235, 208));
        assignRootsTableScrollPane.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        assignRootsTable.setBackground(new java.awt.Color(244, 235, 208));
        assignRootsTable.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        assignRootsTable.setForeground(new java.awt.Color(46, 103, 90));
        assignRootsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "----", "----"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        assignRootsTable.setGridColor(new java.awt.Color(204, 204, 204));
        assignRootsTable.setRowHeight(34);
        assignRootsTable.setSelectionBackground(new java.awt.Color(214, 173, 96));
        assignRootsTable.setSelectionForeground(new java.awt.Color(244, 235, 208));
        assignRootsTable.setShowGrid(true);
        assignRootsTable.setShowVerticalLines(false);
        assignRootsTable.getTableHeader().setReorderingAllowed(false);
        assignRootsTableScrollPane.setViewportView(assignRootsTable);

        javax.swing.GroupLayout assignRootsTablePanelLayout = new javax.swing.GroupLayout(assignRootsTablePanel);
        assignRootsTablePanel.setLayout(assignRootsTablePanelLayout);
        assignRootsTablePanelLayout.setHorizontalGroup(
            assignRootsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(assignRootsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        assignRootsTablePanelLayout.setVerticalGroup(
            assignRootsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(assignRootsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        assignRootsInnerPanel.add(assignRootsTablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 500, 250));

        closeAssignRootsPanelBtn.setBackground(new java.awt.Color(244, 235, 208));
        closeAssignRootsPanelBtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        closeAssignRootsPanelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentationLayer/images/close.png"))); // NOI18N
        closeAssignRootsPanelBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeAssignRootsPanelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAssignRootsPanelBtnActionPerformed(evt);
            }
        });
        assignRootsInnerPanel.add(closeAssignRootsPanelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, 30, -1));

        assignRootsPanel.add(assignRootsInnerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 760, 440));

        assignRootsPanelObj = assignRootsPanel;

        rootsCRUDPanelRound.add(assignRootsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 780, 460));

        rootsTableScrollPane.setBackground(new java.awt.Color(244, 235, 208));
        rootsTableScrollPane.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        rootsTable.setBackground(new java.awt.Color(244, 235, 208));
        rootsTable.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        rootsTable.setForeground(new java.awt.Color(46, 103, 90));
        rootsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Root #", "Root", "Status", "Verse Count", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rootsTable.setGridColor(new java.awt.Color(204, 204, 204));
        rootsTable.setRowHeight(34);
        rootsTable.setSelectionBackground(new java.awt.Color(214, 173, 96));
        rootsTable.setSelectionForeground(new java.awt.Color(244, 235, 208));
        rootsTable.setShowGrid(true);
        rootsTable.setShowVerticalLines(false);
        rootsTable.getTableHeader().setReorderingAllowed(false);
        rootsTableScrollPane.setViewportView(rootsTable);
        if (rootsTable.getColumnModel().getColumnCount() > 0) {
            rootsTable.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("GUI_PO.rootsTable.columnModel.title0")); // NOI18N
            rootsTable.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("GUI_PO.rootsTable.columnModel.title1")); // NOI18N
            rootsTable.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("GUI_PO.rootsTable.columnModel.title2")); // NOI18N
            rootsTable.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("GUI_PO.rootsTable.columnModel.title3")); // NOI18N
            rootsTable.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("GUI_PO.rootsTable.columnModel.title4")); // NOI18N
        }

        javax.swing.GroupLayout rootsTablePanelLayout = new javax.swing.GroupLayout(rootsTablePanel);
        rootsTablePanel.setLayout(rootsTablePanelLayout);
        rootsTablePanelLayout.setHorizontalGroup(
            rootsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        rootsTablePanelLayout.setVerticalGroup(
            rootsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootsTablePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(rootsTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        rootsCRUDPanelRound.add(rootsTablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 830, 340));

        rootPanelinRoots.setBackground(new java.awt.Color(244, 235, 208));
        rootPanelinRoots.setAllCornersRound(25);
        rootPanelinRoots.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookinBooksTitleLbl1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bookinBooksTitleLbl1.setForeground(new java.awt.Color(46, 103, 90));
        bookinBooksTitleLbl1.setText(bundle.getString("GUI_PO.bookinBooksTitleLbl1.text")); // NOI18N
        rootPanelinRoots.add(bookinBooksTitleLbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        rootsInRootsList.setBackground(new java.awt.Color(244, 235, 208));
        rootsInRootsList.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        rootsInRootsList.setForeground(new java.awt.Color(46, 103, 90));
        rootsInRootsList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rootsInRootsList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rootsInRootsListActionPerformed(evt);
            }
        });
        rootPanelinRoots.add(rootsInRootsList, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 210, 30));

        rootsCRUDPanelRound.add(rootPanelinRoots, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 310, 70));

        searchModePanelinRoot.setBackground(new java.awt.Color(244, 235, 208));
        searchModePanelinRoot.setAllCornersRound(25);
        searchModePanelinRoot.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchRootModeRadioBtn.setBackground(new java.awt.Color(244, 235, 208));
        poemPanelChckBtnGroup.add(searchRootModeRadioBtn);
        searchRootModeRadioBtn.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        searchRootModeRadioBtn.setForeground(new java.awt.Color(46, 103, 90));
        searchRootModeRadioBtn.setText(bundle.getString("GUI_PO.searchRootModeRadioBtn.text")); // NOI18N
        searchRootModeRadioBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchRootModeRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchRootModeRadioBtnActionPerformed(evt);
            }
        });
        searchModePanelinRoot.add(searchRootModeRadioBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        rootsCRUDPanelRound.add(searchModePanelinRoot, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, 120, 40));

        rootsPanel.add(rootsCRUDPanelRound, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 40, 930, 530));

        menuTabsPane.addTab(bundle.getString("GUI_PO.rootsPanel.TabConstraints.tabTitle"), rootsPanel); // NOI18N

        menuTabsPane.setSelectedComponent(dashboardPanel);

        //leftSidePanel.putClientProperty( FlatClientProperties.STYLE_CLASS, "myRoundPanel" );
        leftSidePanel.setBackground(new java.awt.Color(18, 38, 32));
        leftSidePanel.setRoundTopRight(25);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentationLayer/images/logo.png"))); // NOI18N

        javax.swing.GroupLayout leftSidePanelLayout = new javax.swing.GroupLayout(leftSidePanel);
        leftSidePanel.setLayout(leftSidePanelLayout);
        leftSidePanelLayout.setHorizontalGroup(
            leftSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftSidePanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(logo)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        leftSidePanelLayout.setVerticalGroup(
            leftSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftSidePanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(logo)
                .addContainerGap(492, Short.MAX_VALUE))
        );

        topPanel.setBackground(new java.awt.Color(18, 38, 32));
        topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        appTitle.setBackground(new java.awt.Color(244, 235, 208));
        appTitle.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        appTitle.setForeground(new java.awt.Color(244, 235, 208));
        appTitle.setText(bundle.getString("GUI_PO.appTitle.text")); // NOI18N
        topPanel.add(appTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(515, 14, -1, -1));

        changeLanguageBtn.setBackground(new java.awt.Color(18, 38, 32));
        changeLanguageBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentationLayer/images/gear.png"))); // NOI18N
        changeLanguageBtn.setText(bundle.getString("GUI_PO.changeLanguageBtn.text")); // NOI18N
        changeLanguageBtn.setBorder(null);
        changeLanguageBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        changeLanguageBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeLanguageBtnMouseClicked(evt);
            }
        });
        topPanel.add(changeLanguageBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, 40, 30));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(leftSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menuTabsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menuTabsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(leftSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        tabsPaneObj=menuTabsPane;

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // To convert title count in arabic numbers
    // shashka to show poem's count in arabic numbers
    private String numToArabic(int c) {
        int arabic_zero_unicode = 1632;
        String str = "" + c;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            sb.append((char) ((int) str.charAt(i) - 48 + arabic_zero_unicode));
        }
        str = sb.toString();
        return str;
    }

    private void updateBooksData() {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"كتاب #", "عنوان", "مؤلف", "عدد القصائد", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Book #", "Title", "Author", "Poems Count", "Actions"};
            columnNames = colNam;
        }
        booksTableModel = new DefaultTableModel(columnNames, 0);
        bookTitleinBooksList.removeAllItems();
        bookTitleinPoemList.removeAllItems();
        bookTitleinImportPoemList.removeAllItems();
        bookTitleinPoemList.addItem("");
        bookTitleinImportPoemList.addItem("");
        bookTitleinBooksList.addItem("");
        int c = 1;
        ArrayList<BookTO> books = ibllFacade.getAllBooksBO();
        for (BookTO b : books) {
            String author = b.getBookAuthor();
            if (author == null) {
                author = "";
            }
            booksTableModel.addRow(new Object[]{numToArabic(c), b.getBookName(), author, numToArabic(b.getPoemsCount())});
            bookTitleinBooksList.addItem(b.getBookName());
            bookTitleinPoemList.addItem(b.getBookName());
            bookTitleinImportPoemList.addItem(b.getBookName());
            c++;
        }

        booksTable.setModel(booksTableModel);
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        booksTable.getColumnModel().getColumn(3).setPreferredWidth(3);
        booksTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        setColumnContentCenter(1, booksTable);
        setColumnContentCenter(2, booksTable);
    }

    private void updatePoemsTitle() {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"قصيدة #", "عنوان القصيدة", "--", "--", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Poem #", "Poem Title", "--", "--", "Actions"};
            columnNames = colNam;
        }
        poemsTableModel = new DefaultTableModel(columnNames, 0);
        poemTitleinPoemList.removeAllItems();
        poemTitleinPoemList.addItem("");
        int c = 1;
        ArrayList<String> result = ibllFacade.getAllPoemsBO();
        for (String str : result) {
            poemsTableModel.addRow(new Object[]{numToArabic(c), str, "", ""});
            poemTitleinPoemList.addItem(str + " -  " + numToArabic(c));
            c++;
        }

        poemsTable.setModel(poemsTableModel);
        poemsTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        poemsTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        setColumnContentCenter(1, poemsTable);
        setColumnContentCenter(2, poemsTable);
        setColumnContentCenter(3, poemsTable);
    }

    private void updatePoemsTitleByBookInBooks(String bookName) {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"قصيدة #", "عنوان كتاب", "عنوان القصيدة", "--", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Poem #", "Book Title", "Poem Title", "--", "Actions"};
            columnNames = colNam;
        }
        booksTableModel = new DefaultTableModel(columnNames, 0);
        int c = 1;
        ArrayList<String> result = ibllFacade.getAllPoemsByBookBO(bookName);
        booksTableModel.addRow(new Object[]{"", bookName, "--", "--"});
        for (String str : result) {
            booksTableModel.addRow(new Object[]{numToArabic(c), "", str, ""});
            c++;
        }

        booksTable.setModel(booksTableModel);
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        booksTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        setColumnContentCenter(1, booksTable);
        setColumnContentCenter(2, booksTable);
        setColumnContentCenter(3, booksTable);
    }

    private void updatePoemsTitleByBookInPoems(String bookName) {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"قصيدة #", "عنوان القصيدة", "--", "--", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Poem #", "Poem Title", "--", "--", "Actions"};
            columnNames = colNam;
        }
        poemsTableModel = new DefaultTableModel(columnNames, 0);
        poemTitleinPoemList.removeAllItems();
        poemTitleinPoemList.addItem("");
        int c = 1;
        ArrayList<String> result = ibllFacade.getAllPoemsByBookBO(bookName);
        for (String str : result) {
            poemsTableModel.addRow(new Object[]{numToArabic(c), str, "", ""});
            poemTitleinPoemList.addItem(str + " -  " + numToArabic(c));
            c++;
        }

        poemsTable.setModel(poemsTableModel);
        poemsTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        poemsTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        setColumnContentCenter(1, poemsTable);
        setColumnContentCenter(2, poemsTable);
        setColumnContentCenter(3, poemsTable);
    }

    private void updateCompletePoemInPoemsTable(ArrayList<PoemsTO> poem) {
        poemsTable.getColumnName(0);
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"بيت شعر #", "عنوان القصيدة", "ميسرا ١", "ميسرا ٢", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Verse #", "Poem Title", "Misra 1", "Misra 2", "Actions"};
            columnNames = colNam;
        }
        poemsTableModel = new DefaultTableModel(columnNames, 0);
        int i = 0;
        if (poem.isEmpty()) {
            poemsTableModel.addRow(new Object[]{"", poem.get(i).getPoemTitle(), "EMPTY POEM!", ""});
        }
        int c = 1;
        for (PoemsTO obj : poem) {
            if (i == 0) {
                if ((poem.get(0).getMisra1() == null) && (poem.get(0).getMisra2() == null)) {
                    poemsTableModel.addRow(new Object[]{"", obj.getPoemTitle(), "EMPTY POEM!", ""});
                    showSinglePoemChck = true;
                    break;
                } else {
                    poemsTableModel.addRow(new Object[]{"", obj.getPoemTitle(), "--", "--"});
                    showSinglePoemChck = true;
                }
                i++;
            }
            if ((obj.getMisra1() != null) || (obj.getMisra2() != null)) {
                poemsTableModel.addRow(new Object[]{numToArabic(c), "", obj.getMisra1(), obj.getMisra2()});
                showSinglePoemChck = true;
                c++;
            }
        }

        poemsTable.setModel(poemsTableModel);
        poemsTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        poemsTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        setColumnContentCenter(1, poemsTable);
        setColumnContentCenter(2, poemsTable);
        setColumnContentCenter(3, poemsTable);
    }

    private void updateCompletePoemInRootsTable(ArrayList<PoemsTO> poem) {
        rootsTable.getColumnName(0);
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"بيت شعر #", "عنوان القصيدة", "ميسرا ١", "ميسرا ٢", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Verse #", "Poem Title", "Misra 1", "Misra 2", "Actions"};
            columnNames = colNam;
        }
        rootsTableModel = new DefaultTableModel(columnNames, 0);
        int i = 0;
        if (poem.isEmpty()) {
            rootsTableModel.addRow(new Object[]{"", poem.get(i).getPoemTitle(), "EMPTY POEM!", ""});
        }
        int c = 1;
        for (PoemsTO obj : poem) {
            if (i == 0) {
                if (poem.size() == 1) {
                    rootsTableModel.addRow(new Object[]{"", obj.getPoemTitle(), "EMPTY POEM!", ""});
                    break;
                } else {
                    rootsTableModel.addRow(new Object[]{"", obj.getPoemTitle(), "--", "--"});
                }
                i++;
            }
            if ((obj.getMisra1() != null) || (obj.getMisra2() != null)) {
                rootsTableModel.addRow(new Object[]{numToArabic(c), "", obj.getMisra1(), obj.getMisra2()});
                c++;
            }
        }

        rootsTable.setModel(rootsTableModel);
        rootsTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        rootsTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        setColumnContentCenter(0, rootsTable);
        setColumnContentCenter(1, rootsTable);
        setColumnContentCenter(2, rootsTable);
        setColumnContentCenter(3, rootsTable);
    }

    private void updateTokensInTable() {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"رمز #", "الرموز", "عرض العلامات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Token #", "Tokens", "View Tags"};
            columnNames = colNam;
        }
        tokensTableModel = new DefaultTableModel(columnNames, 0);
        int c = 1;
        ArrayList<TokenTO> result = ibllFacade.getAllTokens();
        if (result.isEmpty()) {
            tokensTableModel.addRow(new Object[]{"", "No tokens exist in Table."});
        } else {
            for (TokenTO to : result) {
                tokensTableModel.addRow(new Object[]{numToArabic(c), to.getToken()});
                c++;
            }
        }
        tokensTable.setModel(tokensTableModel);
    }

    private void updateAssignRootsList(ArrayList<String> tokens) {
        rootsInAssignRootsList.removeAllItems();
        rootsInAssignRootsList.addItem("");
        ArrayList<String> roots;
        for (String s : tokens) {
            roots = ibllFacade.getRootsByToken(s);
            for (String s1 : roots) {
                if (!s1.equals("")) {
                    rootsInAssignRootsList.addItem(s1);
                }
            }
        }
    }

    private void updateRootsData() {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"جذر #", "جذور", "حالة", "عدد الآيات", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Root #", "Roots", "Status", "Verse Count", "Actions"};
            columnNames = colNam;
        }
        rootsTableModel = new DefaultTableModel(columnNames, 0);
        rootsInRootsList.removeAllItems();
        rootsInRootsList.addItem("");
        int c = 1;
        HashMap<RootTO, Integer> result = ibllFacade.getAllRoots();
        if (result.isEmpty()) {
            rootsTableModel.addRow(new Object[]{"", "No roots exist in Table."});
        } else {
            for (Map.Entry<RootTO, Integer> m : result.entrySet()) {
                rootsTableModel.addRow(new Object[]{numToArabic(c), m.getKey().getRootName(), m.getKey().getStatus(), m.getValue()});
                rootsInRootsList.addItem(m.getKey().getRootName());
                c++;
            }
        }
        rootsTable.setModel(rootsTableModel);
    }

    private void updateVersesByRoot(String root) {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"بيت شعر #", "جذور", "ميسرا ١", "ميسرا ٢", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Verse #", "Root", "Misra 1", "Misra 2", "Actions"};
            columnNames = colNam;
        }
        rootsTableModel = new DefaultTableModel(columnNames, 0);
        rootsTableModel.addRow(new Object[]{"", root, "--", "--"});
        int c = 1;
        poemByRoot = ibllFacade.getVersesAgainstRoot(root);
        if (poemByRoot.isEmpty()) {
            rootsTableModel.addRow(new Object[]{"", "No Verses against this Root."});
        } else {
            for (PoemsTO to : poemByRoot) {
                rootsTableModel.addRow(new Object[]{numToArabic(c), "", to.getMisra1(), to.getMisra2()});
                c++;
            }
        }
        rootsTable.setModel(rootsTableModel);
        rootsTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        rootsTable.getColumnModel().getColumn(1).setPreferredWidth(3);
        rootsTable.getColumnModel().getColumn(2).setPreferredWidth(170);
        rootsTable.getColumnModel().getColumn(3).setPreferredWidth(170);
        rootsTable.getColumnModel().getColumn(4).setPreferredWidth(3);
    }

    private void updateAssignRootsInTable() {
        String[] columnNames;
        if (languageChck) {
            String[] colNam = {"جذور", "", "", "أجراءات"};
            columnNames = colNam;
        } else {
            String[] colNam = {"Roots", "", "", "Actions"};
            columnNames = colNam;
        }
        assignRootsTableModel = new DefaultTableModel(columnNames, 0);
        assignRootsTable.setModel(assignRootsTableModel);
        assignRootsTable.getColumnModel().getColumn(0).setPreferredWidth(170);
        assignRootsTable.getColumnModel().getColumn(1).setPreferredWidth(0);
        assignRootsTable.getColumnModel().getColumn(2).setPreferredWidth(0);
        assignRootsTable.getColumnModel().getColumn(3).setPreferredWidth(170);
    }

    private void addButtonsInBooksTable() {
        TableActionEvent event;
        event = new TableActionEvent() {
            String prevBookT = null;
            String prevPoemT = null;
            String poemCount = null;

            @Override
            public void onEdit(int row) {
                if (!booksTableModel.getValueAt(row, 0).equals("")) {
                    String bookT = booksTableModel.getValueAt(row, 1).toString();
                    if (!bookT.equals("") && (!booksTableModel.getValueAt(row, 3).equals(""))) { // edit book title
                        prevBookT = bookT;
                        poemCount = booksTableModel.getValueAt(row, 3).toString();
                        booksTableModel.setValueAt("Edit by double click.", row, 3);
                        editBookTitleChck = true;
                    } else if ((booksTableModel.getValueAt(0, 0).equals("")) && (booksTableModel.getValueAt(row, 1).equals(""))
                            && !(booksTableModel.getValueAt(row, 2).equals(""))) { // edit poem title
                        prevPoemT = booksTableModel.getValueAt(row, 2).toString();
                        booksTableModel.setValueAt("Edit by double click.", row, 1);
                        editPoemTitleChck = true;
                    }
                }
            }

            @Override
            public void onDelete(int row) {
                if (booksTable.isEditing()) {
                    booksTable.getCellEditor().stopCellEditing();
                }
                if (!booksTableModel.getValueAt(row, 0).equals("")) {
                    String bookT = booksTableModel.getValueAt(row, 1).toString();
                    if (!bookT.equals("") && (!booksTableModel.getValueAt(row, 3).equals(""))) { // delete book
                        if (ibllFacade.deleteBookBO(bookT)) {
                            booksTableModel.removeRow(row);
                            JOptionPane.showMessageDialog(frame, "Book Deleted succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Book Deletion failed!");
                        }
                    } else if ((booksTableModel.getValueAt(0, 0).equals("")) && (booksTableModel.getValueAt(row, 1).equals(""))
                            && !(booksTableModel.getValueAt(row, 2).equals(""))) { // delete poem
                        bookT = booksTableModel.getValueAt(0, 1).toString();
                        if (ibllFacade.deletePoemBO(bookT, booksTableModel.getValueAt(row, 2).toString(), row + 1)) {
                            booksTableModel.removeRow(row);
                            JOptionPane.showMessageDialog(frame, "Poem Deleted succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Poem Deletion failed!");
                        }
                    }
                }
            }

            @Override
            public void onView(MouseEvent e, int row) {
                String book;
                if ((booksTableModel.getValueAt(0, 0).toString().equals("")) && (!booksTableModel.getValueAt(0, 1).toString().equals(""))
                        && (booksTableModel.getValueAt(0, 2).toString().equals("--"))) {
                    bookRepeatInTableChck = false;
                    updateBooksData();
                    addButtonsInBooksTable();
                    setColumnContentCenter(0, booksTable);
                    setColumnContentCenter(3, booksTable);
                    bookRepeatInTableChck = true;
                } else if ((!booksTableModel.getValueAt(0, 0).toString().equals(""))
                        || (!booksTableModel.getValueAt(row, 1).toString().equals("")) || (!booksTableModel.getValueAt(row, 3).toString().equals(""))) {
                    // when Book are showing in the table
                    book = booksTableModel.getValueAt(row, 1).toString();
                    updatePoemsTitleByBookInBooks(book);
                    addButtonsInBooksTable();
                    setColumnContentCenter(0, booksTable);
                } else {
                    JOptionPane.showMessageDialog(frame, "Verses can be seen in Poems Tab.");
                }
            }

            @Override
            public void onDone(int row) {
                String bookT, poemT;

                if (newBookChck) { // creating new book
                    bookT = booksTableModel.getValueAt(0, 0).toString();
                    poemT = booksTableModel.getValueAt(0, 2).toString();
                    String author = booksTableModel.getValueAt(0, 1).toString();
                    if (bookT.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Book Title can't be empty!");
                    } else if (poemT.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Poem Title can't be empty!");
                    } else {
                        if (ibllFacade.insertBookBO(new BookTO(bookT, author, 1))) {
                            if (ibllFacade.addNewPoemBO(new PoemsTO(bookT, poemT, "", "", "", ""))) {
                                bookRepeatInTableChck = false;
                                JOptionPane.showMessageDialog(frame, "Book inserted succesfully!");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Poem insertion failed in new Book.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Book insertion failed!");
                        }
                        updateBooksData();
                        addButtonsInBooksTable();
                        bookRepeatInTableChck = true;
                    }
                    newBookChck = false;
                } else if (editBookTitleChck) { // edit title of Book
                    bookT = booksTableModel.getValueAt(row, 1).toString();
                    String author = booksTableModel.getValueAt(row, 2).toString();
                    if (booksTableModel.getValueAt(row, 0).equals("")) {
                        JOptionPane.showMessageDialog(frame, "Must be in Book's List Table");
                    } else {
                        if (ibllFacade.updateBookBO(prevBookT, bookT, author)) {
                            booksTableModel.setValueAt(poemCount, row, 3);
                            JOptionPane.showMessageDialog(frame, "Updated succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Updation Failed!");
                        }
                    }
                    editBookTitleChck = false;
                } else if (editPoemTitleChck) { // edit title of poem
                    bookT = booksTableModel.getValueAt(0, 1).toString();
                    poemT = booksTableModel.getValueAt(row, 2).toString();
                    if (poemT.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Must be in Poem's Verses View");
                    } else {
                        if (ibllFacade.updatePoemsTitleBO(bookT, prevPoemT, poemT, row + 2)) {
                            booksTableModel.setValueAt("", row, 1);
                            JOptionPane.showMessageDialog(frame, "Updated succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Updation Failed!");
                        }
                    }
                    editPoemTitleChck = false;
                }
            }
        };
        booksTable.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        booksTable.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
    }

    private void addButtonsInPoemsTable() {
        TableActionEvent event;
        event = new TableActionEvent() {
            String prevPoemT = null;
            String prevMisra1 = null;
            String prevMisra2 = null;
            String bookT = null;

            @Override
            public void onEdit(int row) {
                if ((bookTitleinPoemList.getSelectedItem() == null) || (bookTitleinPoemList.getSelectedItem().toString().equals(""))) {
                    JOptionPane.showMessageDialog(frame, "Book must be selected.");
                } else if (!newPoemChck) {
                    bookT = bookTitleinPoemList.getSelectedItem().toString();
                    String poemT = poemsTableModel.getValueAt(row, 1).toString();
                    String misra1 = poemsTableModel.getValueAt(row, 2).toString();
                    String misra2 = poemsTableModel.getValueAt(row, 3).toString();
                    if (!poemT.equals("") && (misra1.equals("")) // ِedit poem title
                            && (misra1.equals(""))) { // must be in poems list not in verses mode
                        prevPoemT = poemT;
                        editPoemTitleChck = true;
                        poemsTableModel.setValueAt("Edit Title by double click on it.", row, 2);
                    } else if (poemT.equals("") && (!(misra1.equals("")) // edit verses
                            || !(misra2.equals("")))) {
                        prevMisra1 = misra1;
                        prevMisra2 = misra2;
                        poemsTableModel.setValueAt("Edit Verse by double click on it.", row, 1);
                        editVerseInPoemChck = true;
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Poem Type must be 'Existing Poem'");
                }

            }

            @Override
            public void onDelete(int row) {
                String book, poemT, misra1, misra2;
                if (poemsTable.isEditing()) {
                    poemsTable.getCellEditor().stopCellEditing();
                }
                if ((bookTitleinPoemList.getSelectedItem() == null) || (bookTitleinPoemList.getSelectedItem().toString().equals(""))) {
                    JOptionPane.showMessageDialog(frame, "Book must be selected.");
                } else {
                    book = bookTitleinPoemList.getSelectedItem().toString();
                    poemT = poemsTableModel.getValueAt(row, 1).toString();
                    if (!poemT.equals("") && (poemsTableModel.getValueAt(row, 2).equals(""))
                            && (poemsTableModel.getValueAt(row, 3).equals(""))) { // poems list mode, deleting poem
                        if (ibllFacade.deletePoemBO(book, poemT, row + 1)) {
                            poemsTableModel.removeRow(row);
                            JOptionPane.showMessageDialog(frame, "Poem Deleted succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Poem Deletion failed!");
                        }
                    } else if (poemsTableModel.getValueAt(row, 1).toString().equals("") && (!(poemsTableModel.getValueAt(row, 2).toString().equals(""))
                            || !(poemsTableModel.getValueAt(row, 3).toString().equals("")))) { // deleting verses, opened poem mode                       
                        misra1 = poemsTableModel.getValueAt(row, 2).toString();
                        misra2 = poemsTableModel.getValueAt(row, 3).toString();
                        String[] verse = {misra1, misra2};
                        if (ibllFacade.deleteVerseOfAPoemBO(book, poemsTableModel.getValueAt(0, 1).toString(), verse)) {
                            poemsTableModel.removeRow(row);
                            JOptionPane.showMessageDialog(frame, "Verse Deleted succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Verse Deletion failed!");
                        }
                    }
                }
            }

            @Override
            public void onView(MouseEvent e, int row) {
                String book, poemT;
                if ((bookTitleinPoemList.getSelectedItem() == null) || (bookTitleinPoemList.getSelectedItem().toString().equals(""))) {
                    JOptionPane.showMessageDialog(frame, "Book must be selected.");
                } else {
                    book = bookTitleinPoemList.getSelectedItem().toString();
                    poemT = poemsTableModel.getValueAt(row, 1).toString();
                    if (poemT.equals("") && (poemsTableModel.getValueAt(row, 2).toString().equals(""))
                            && (poemsTableModel.getValueAt(row, 3).toString().equals(""))) {
                        // if we press view button while creating new poem
                        JOptionPane.showMessageDialog(frame, "No Poem created yet.");
                    } else if (!poemT.equals("") && (poemsTableModel.getValueAt(row, 2).equals(""))) { // view verses of poem
                        newVerseRow = row + 1;
                        ArrayList<PoemsTO> poem = ibllFacade.getAPoemByBookBO(book, poemT, row + 1);
                        updateCompletePoemInPoemsTable(poem);
                        showSinglePoemChck = true;
                        addButtonsInPoemsTable();
                    } else if (!poemT.equals("") && ((poemsTableModel.getValueAt(row, 2).toString().equals("--"))
                            || (poemsTableModel.getValueAt(row, 2).toString().equals("EMPTY POEM!")))) { // go back to list of poems
                        // phly jb ye check nh thy to table me poems list 2 bar add ho rhi thi
                        // jb humny existing poem wala select kya ho q k wo id if condition m aa kr
                        // phly poemsList wala action perform chala deta th jis se issue hota th ye
                        // ab ye chck lga dya k jb existing poem m hon gy to ye chck true krdy gy
                        // jis se poemsList ka action perform wala if glt ho jy ga and wo print nh kry ga apna b
                        // then yaha se print krwa kr again chck false kr dy gy ta k list se select kr k b hum dekh sky poems
                        poemRepeatInTableChck = true;
                        updatePoemsTitleByBookInPoems(bookTitleinPoemList.getSelectedItem().toString());
                        showSinglePoemChck = false;
                        addButtonsInPoemsTable();
                        poemRepeatInTableChck = false;
                    } else if (showSinglePoemChck) {
                        if (!e.isPopupTrigger()) {
                            bookTSendToOtherTab = bookTitleinPoemList.getSelectedItem().toString();
                            poemTSendToOtherTab = poemsTableModel.getValueAt(0, 1).toString();
                            misra1SendToOtherTab = poemsTableModel.getValueAt(row, 2).toString();
                            misra2SendToOtherTab = poemsTableModel.getValueAt(row, 3).toString();
                            viewVersePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                        }

                    }
                }
            }

            @Override
            public void onDone(int row) {
                PoemsTO transferObj = new PoemsTO();
                String book, poemT, misra1, misra2;

                if (newPoemChck) { // creating new poem
                    book = bookTitleinPoemList.getSelectedItem().toString();
                    if (book.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Book must be selected.");
                    } else {
                        transferObj.setBookTitle(book);
                    }
                    poemT = poemsTableModel.getValueAt(row, 1).toString();
                    if (poemT.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Poem Title can't be empty.");
                    } else {
                        transferObj.setPoemTitle(poemT);
                        misra1 = poemsTableModel.getValueAt(row, 2).toString();
                        misra2 = poemsTableModel.getValueAt(row, 3).toString();
                        if (misra1.equals("") && (misra2.equals(""))) {
                            JOptionPane.showMessageDialog(frame, "Enter atleast one Misra.");
                        } else if (!(misra1.equals("")) || !(misra2.equals(""))) {
                            transferObj.setMisra1(misra1);
                            transferObj.setMisra2(misra2);
                            if (ibllFacade.addNewPoemBO(transferObj)) {
                                if (bookTitleinPoemList.getSelectedItem() == null) {
                                    updatePoemsTitle();
                                } else {
                                    poemRepeatInTableChck = true;
                                    updatePoemsTitleByBookInPoems(bookTitleinPoemList.getSelectedItem().toString());
                                    poemRepeatInTableChck = false;
                                }
                                JOptionPane.showMessageDialog(frame, "Poem inserted succesfully!");
                                addButtonsInPoemsTable();
                            } else {
                                JOptionPane.showMessageDialog(frame, "Poem insertion Failed!");
                            }
                        }
                    }
                    newPoemChck = false;
                } else if (newVerseInPoemChck) { // creating new verse  in poem
                    book = bookTitleinPoemList.getSelectedItem().toString();
                    if (book.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Book must be selected.");
                    } else {
                        transferObj.setBookTitle(book);
                    }
                    poemT = poemsTableModel.getValueAt(0, 1).toString();
                    if (poemT.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Poem must be selected.");
                    } else {
                        transferObj.setPoemTitle(poemT);
                    }
                    misra1 = poemsTableModel.getValueAt(row, 2).toString();
                    misra2 = poemsTableModel.getValueAt(row, 3).toString();
                    if (misra1.equals("") && (misra2.equals(""))) {
                        JOptionPane.showMessageDialog(frame, "Enter atleast one Misra.");
                    } else if (!(misra1.equals("")) || !(misra2.equals(""))) {
                        transferObj.setMisra1(misra1);
                        transferObj.setMisra2(misra2);
                        if (ibllFacade.addNewVerseBO(transferObj, newVerseRow)) {
                            if (bookTitleinPoemList.getSelectedItem() == null) {
                                updatePoemsTitle();
                            } else {
                                poemRepeatInTableChck = true;
                                updatePoemsTitleByBookInPoems(bookTitleinPoemList.getSelectedItem().toString());
                                poemRepeatInTableChck = false;
                            }
                            JOptionPane.showMessageDialog(frame, "Verse inserted succesfully!");
                            addButtonsInPoemsTable();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Verse insertion Failed!");
                        }
                    }
                    newVerseInPoemChck = false;
                } else if (editPoemTitleChck) { // edit title of poem
                    poemT = poemsTableModel.getValueAt(row, 1).toString();
                    if (poemT.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Must be in Poem's List Table");
                    } else {
                        if (ibllFacade.updatePoemsTitleBO(bookT, prevPoemT, poemT, row + 1)) {
                            poemsTableModel.setValueAt("", row, 2);
                            JOptionPane.showMessageDialog(frame, "Updated succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Updation Failed!");
                        }
                    }
                    editPoemTitleChck = false;
                } else if (editVerseInPoemChck) { // edit verse of poem
                    poemT = poemsTableModel.getValueAt(0, 1).toString();
                    if (poemT.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Must be in Poem's Verses View");
                    } else {
                        misra1 = poemsTableModel.getValueAt(row, 2).toString();
                        misra2 = poemsTableModel.getValueAt(row, 3).toString();
                        PoemsTO prevObj = new PoemsTO(bookT, poemT, prevMisra1, prevMisra2, null, null);
                        PoemsTO newObj = new PoemsTO(bookT, poemT, misra1, misra2, null, null);
                        if (ibllFacade.updatePoemVerseBO(prevObj, newObj)) {
                            poemsTableModel.setValueAt("", row, 1);
                            JOptionPane.showMessageDialog(frame, "Updated succesfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Updation Failed!");
                        }
                    }
                    editVerseInPoemChck = false;
                }
            }
        };
        poemsTable.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        poemsTable.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
        poemsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.CENTER);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
    }

    private void setColumnContentCenter(int colNum, JTable table) {
        if (colNum < table.getColumnCount()) {
            table.getColumnModel().getColumn(colNum).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                    return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
                }
            });
        }
    }

    private void addButtonInTokensTable(int col) {
        ViewTableEvent event;
        event = new ViewTableEvent() {
            @Override
            public void onView(int row) {
                if (tokensTableModel.getColumnCount() == 3) {
                    String token = tokensTableModel.getValueAt(row, 1).toString();
                    String[] columnNames;
                    if (languageChck) {
                        String[] colNam = {"بطاقة شعار #", "رمز مميز", "العلامات", "يكتب", "أجراءات"};
                        columnNames = colNam;
                    } else {
                        String[] colNam = {"Tag #", "Token", "Tags", "Type", "Action"};
                        columnNames = colNam;
                    }
                    tokensTableModel = new DefaultTableModel(columnNames, 0);
                    tokensTableModel.addRow(new Object[]{"", token, "--", "--"});
                    int c = 1;
                    ArrayList<TokenTagsTO> result = ibllFacade.getTagsOfAToken(token);
                    if (result.isEmpty()) {
                        tokensTableModel.addRow(new Object[]{"", "", "No Tags exist of this Token"});
                    } else {
                        for (TokenTagsTO to : result) {
                            tokensTableModel.addRow(new Object[]{numToArabic(c), "", to.getTags(), ""});
                            c++;
                        }
                    }
                    tokensTable.setModel(tokensTableModel);
                    tokensTable.getColumnModel().getColumn(0).setPreferredWidth(3);
                    tokensTable.getColumnModel().getColumn(1).setPreferredWidth(3);
                    tokensTable.getColumnModel().getColumn(2).setPreferredWidth(170);
                    tokensTable.getColumnModel().getColumn(3).setPreferredWidth(3);
                    tokensTable.getColumnModel().getColumn(4).setPreferredWidth(3);
                    setColumnContentCenter(2, tokensTable);
                    setColumnContentCenter(3, tokensTable);
                    addButtonInTokensTable(4);
                } else {
                    updateTokensInTable();
                    addButtonInTokensTable(2);
                }
            }
        };
        tokensTable.getColumnModel().getColumn(col).setCellRenderer(new ViewTableCellRender(new ImageIcon(getClass().getResource("/presentationLayer/images/analysis.png"))));
        tokensTable.getColumnModel().getColumn(col).setCellEditor(new ViewTableCellEditor(event, new ImageIcon(getClass().getResource("/presentationLayer/images/analysis.png"))));
        setColumnContentCenter(0, tokensTable);
        setColumnContentCenter(1, tokensTable);
    }

    private void addButtonInRootsTable() {
        ViewTableEvent event;
        event = new ViewTableEvent() {
            @Override
            public void onView(int row) {
                if (!rootsTableModel.getValueAt(0, 0).equals("")) {
                    String root = rootsTableModel.getValueAt(row, 1).toString();
                    updateVersesByRoot(root);
                    addButtonInRootsTable();
                } else if (!rootsTableModel.getValueAt(row, 0).equals("") && !versesInRootTableChck) {
                    updateCompletePoemInRootsTable(ibllFacade.getAPoemByVerseBO(poemByRoot.get(row - 1)));
                    addButtonInRootsTable();
                    versesInRootTableChck = true;
                } else {
                    rootRepeatInTableChck = false;
                    updateRootsData();
                    addButtonInRootsTable();
                    rootRepeatInTableChck = true;
                    versesInRootTableChck = false;
                }
            }
        };
        rootsTable.getColumnModel().getColumn(4).setCellRenderer(new ViewTableCellRender(new ImageIcon(getClass().getResource("/presentationLayer/images/analysis.png"))));
        rootsTable.getColumnModel().getColumn(4).setCellEditor(new ViewTableCellEditor(event, new ImageIcon(getClass().getResource("/presentationLayer/images/analysis.png"))));
        setColumnContentCenter(0, rootsTable);
        setColumnContentCenter(1, rootsTable);
        setColumnContentCenter(2, rootsTable);
        setColumnContentCenter(3, rootsTable);
    }

    private void addButtonInAssignRootsTable() {
        ViewTableEvent event;
        event = new ViewTableEvent() {
            @Override
            public void onView(int row) {
                if (assignRootsTable.isEditing()) {
                    assignRootsTable.getCellEditor().stopCellEditing();
                }
                assignRootsTableModel.removeRow(row);
            }
        };
        assignRootsTable.getColumnModel().getColumn(3).setCellRenderer(new ViewTableCellRender(new ImageIcon(getClass().getResource("/presentationLayer/images/remove.png"))));
        assignRootsTable.getColumnModel().getColumn(3).setCellEditor(new ViewTableCellEditor(event, new ImageIcon(getClass().getResource("/presentationLayer/images/remove.png"))));
        setColumnContentCenter(0, assignRootsTable);
        setColumnContentCenter(1, assignRootsTable);
        setColumnContentCenter(2, assignRootsTable);
    }

    private void assignRootsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignRootsActionPerformed
        assignRootChck = false;
        tabsPaneObj.setSelectedIndex(4);
        rootsInRootsList.setEnabled(false);
        searchRootModeRadioBtn.setEnabled(false);
        rootsTablePanel.setVisible(false);
        assignRootsPanelObj.setVisible(true);
        misra1InRootsTxtField.setText(misra1SendToOtherTab);
        misra2InRootsTxtField.setText(misra2SendToOtherTab);
        updateAssignRootsList(ibllFacade.returnTokens(misra1SendToOtherTab, misra2SendToOtherTab));
        updateAssignRootsInTable();
        assignRootChck = true;
    }//GEN-LAST:event_assignRootsActionPerformed

    private void assignTokensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignTokensActionPerformed
        tabsPaneObj.setSelectedIndex(3);
        tokensTablePanel.setVisible(false);
        tokensInTokenizePanelObj.removeAll();
        tokenizePanelObj.setVisible(true);
        misra1InTokeniz.setText(misra1SendToOtherTab);
        misra2InTokeniz.setText(misra2SendToOtherTab);
    }//GEN-LAST:event_assignTokensActionPerformed

    private void menuTabsPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuTabsPaneMouseClicked
        bookRepeatInTableChck = false;
        rootRepeatInTableChck = false;
        updateBooksData();
        addButtonsInBooksTable();
        setColumnContentCenter(0, booksTable);
        setColumnContentCenter(3, booksTable);
        updatePoemsTitle();
        addButtonsInPoemsTable();
        updateTokensInTable();
        addButtonInTokensTable(2);
        updateRootsData();
        addButtonInRootsTable();
        bookRepeatInTableChck = true;
        rootRepeatInTableChck = true;
    }//GEN-LAST:event_menuTabsPaneMouseClicked

    private void importPoemPanelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importPoemPanelBtnActionPerformed
        if (importPoemPanel.isVisible()) {
            bookTitleinPoemList.setEnabled(true);
            poemTablePanel.setVisible(true);
            newPoemRadioBtn.setEnabled(true);
            existingPoemRadioBtn.setEnabled(true);
            createPoemBtn.setEnabled(true);
            browseTxtField.setText("");
            importPoemPanel.setVisible(false);
        } else {
            bookTitleinPoemList.setEnabled(false);
            poemTitleinPoemList.setEnabled(false);
            poemTablePanel.setVisible(false);
            newPoemRadioBtn.setEnabled(false);
            existingPoemRadioBtn.setEnabled(false);
            createPoemBtn.setEnabled(false);
            importPoemPanel.setVisible(true);

        }
    }//GEN-LAST:event_importPoemPanelBtnActionPerformed

    private void existingPoemRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingPoemRadioBtnActionPerformed
        if (existingPoemRadioBtn.isSelected()) {
            poemTitleinPoemList.setEnabled(true);
            if (languageChck) {
                createPoemBtn.setText("آية جديدة");
            } else {
                createPoemBtn.setText("New Verse");
            }
        } else {
            existingPoemRadioBtn.setSelected(false);
        }
    }//GEN-LAST:event_existingPoemRadioBtnActionPerformed

    private void newPoemRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPoemRadioBtnActionPerformed
        if (newPoemRadioBtn.isSelected()) {
            poemTitleinPoemList.setEnabled(false);
            if (languageChck) {
                createPoemBtn.setText("يخلق");
            } else {
                createPoemBtn.setText("Create");
            }
        } else {
            newPoemRadioBtn.setSelected(false);
        }
    }//GEN-LAST:event_newPoemRadioBtnActionPerformed

    private void poemTitleinPoemListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poemTitleinPoemListActionPerformed
        if (poemTitleinPoemList.isEnabled() && !poemRepeatInTableChck) {
            if (((poemTitleinPoemList.getSelectedItem() == null) || (poemTitleinPoemList.getSelectedIndex() == 0))
                    && (bookTitleinPoemList.getSelectedIndex() > 0) && showSinglePoemChck) {
                updatePoemsTitleByBookInPoems(bookTitleinPoemList.getSelectedItem().toString());
                showSinglePoemChck = false;
            } else if ((poemTitleinPoemList.getItemCount() > 1) && (poemTitleinPoemList.getSelectedIndex() > 0)) {
                if (bookTitleinPoemList.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(frame, "Book must be selected.");
                } else {
                    String[] poemTitle = poemTitleinPoemList.getSelectedItem().toString().split("\\-");
                    poemTitle[0] = poemTitle[0].substring(0, poemTitle[0].length() - 1);
                    ArrayList<PoemsTO> poem = ibllFacade.getAPoemByBookBO(bookTitleinPoemList.getSelectedItem().toString(),
                            poemTitle[0], poemTitleinPoemList.getSelectedIndex());
                    newVerseRow = poemTitleinPoemList.getSelectedIndex();
                    updateCompletePoemInPoemsTable(poem);
                }
            }
            addButtonsInPoemsTable();
        }
    }//GEN-LAST:event_poemTitleinPoemListActionPerformed

    private void bookTitleinPoemListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookTitleinPoemListActionPerformed
        if ((bookTitleinPoemList.getSelectedItem() == null) || (bookTitleinPoemList.getItemCount() == 1)
                || (bookTitleinPoemList.getSelectedIndex() == 0)) {
            updatePoemsTitle();
        } else if ((bookTitleinPoemList.getItemCount() > 1) && (bookTitleinPoemList.getSelectedIndex() > 0)) {
            poemRepeatInTableChck = true;

            updatePoemsTitleByBookInPoems(bookTitleinPoemList.getSelectedItem().toString());
            poemRepeatInTableChck = false;
        }
        addButtonsInPoemsTable();
    }//GEN-LAST:event_bookTitleinPoemListActionPerformed

    private void createPoemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPoemBtnActionPerformed
        if (newPoemRadioBtn.isSelected()) {
            if ((bookTitleinPoemList.getSelectedItem() == null) || (bookTitleinPoemList.getSelectedItem().toString().equals(""))) {
                JOptionPane.showMessageDialog(frame, "Book Title must be selected.");
            } else {
                String[] columnNames;
                if (languageChck) {
                    String[] colNam = {"بيت شعر #", "عنوان القصيدة", "ميسرا ١", "ميسرا ٢", "أجراءات"};
                    columnNames = colNam;
                } else {
                    String[] colNam = {"Title #", "Title Poem", "Misra 1", "Misra 2", "Actions"};
                    columnNames = colNam;
                }
                poemsTableModel = new DefaultTableModel(columnNames, 0);
                poemsTableModel.addRow(new Object[]{"", "", "", ""});
                poemsTable.setModel(poemsTableModel);
                addButtonsInPoemsTable();
                newPoemChck = true;
                newVerseInPoemChck = false;
            }
        } else if (existingPoemRadioBtn.isSelected()) {
            if (showSinglePoemChck) {
                if ((bookTitleinPoemList.getSelectedItem() == null) || (bookTitleinPoemList.getSelectedItem().toString().equals(""))) {
                    JOptionPane.showMessageDialog(frame, "Book Title must be selected.");
                } else {
                    if (!(poemsTableModel.getValueAt(0, 1).toString().equals("")) && ((poemsTableModel.getValueAt(0, 2).toString().equals("--"))
                            || (poemsTableModel.getValueAt(0, 2).toString().equals("EMPTY POEM!")))) {
                        poemsTableModel.setValueAt("--", 0, 2);
                        poemsTableModel.setValueAt("--", 0, 3);
                        poemsTableModel.addRow(new Object[]{"", "", "", ""});
                        poemsTable.setModel(poemsTableModel);
                        addButtonsInPoemsTable();
                        newPoemChck = false;
                        newVerseInPoemChck = true;
                    } else {
                        JOptionPane.showMessageDialog(frame, "No poem is showing.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "First select poem from table by view it.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Type of Poem must be selected.");
        }
    }//GEN-LAST:event_createPoemBtnActionPerformed

    private void closeImportPoemPanelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeImportPoemPanelBtnActionPerformed
        importPoemPanelBtn.doClick();
    }//GEN-LAST:event_closeImportPoemPanelBtnActionPerformed

    private void importPoemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importPoemBtnActionPerformed
        if (browseTxtField.getText().length() == 0) {
            JOptionPane.showMessageDialog(frame, "Browse Path can't be empty.");
        } else if ("".equals((String) bookTitleinPoemList.getSelectedItem())) {
            if (!ibllFacade.importFileBO(null, browseTxtField.getText())) {
                JOptionPane.showMessageDialog(frame, "Unable to read file\n Hint: Invalid Path!");
            }
        } else {
            if (!ibllFacade.importFileBO((String) bookTitleinPoemList.getSelectedItem(), browseTxtField.getText())) {
                JOptionPane.showMessageDialog(frame, "Unable to read file\n Hint: Invalid Path!");
            } else {
                updatePoemsTitle();
            }

        }
    }//GEN-LAST:event_importPoemBtnActionPerformed

    private void browseFilePathBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFilePathBtnActionPerformed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents", "file", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\syeda\\Downloads"));
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            browseTxtField.setText(selectedFile.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(frame, "No file choosen!");
        }
    }//GEN-LAST:event_browseFilePathBtnActionPerformed

    private void browseTxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_browseTxtFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            importPoemBtn.doClick();
        }
    }//GEN-LAST:event_browseTxtFieldKeyPressed

    private void createBookBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBookBtnActionPerformed
        if (newBookRadioBtn.isSelected()) {
            String[] columnNames;
            if (languageChck) {
                String[] colNam = {"عنوان كتاب ", "مؤلف", "عنوان القصيدة", "--", "أجراءات"};
                columnNames = colNam;
            } else {
                String[] colNam = {"Book Title", "Author", "Poem Title", "--", "Actions"};
                columnNames = colNam;
            }
            booksTableModel = new DefaultTableModel(columnNames, 0);
            booksTableModel.addRow(new Object[]{"", "", "", ""});
            booksTable.setModel(booksTableModel);
            addButtonsInBooksTable();
            newBookChck = true;
        }
    }//GEN-LAST:event_createBookBtnActionPerformed

    private void bookTitleinBooksListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookTitleinBooksListActionPerformed
        if (bookRepeatInTableChck) {
            if ((bookTitleinBooksList.getSelectedItem() == null) || ((bookTitleinBooksList.getItemCount() == 1)
                    || (bookTitleinBooksList.getSelectedIndex() == 0))) {
                updateBooksData();
            } else if ((bookTitleinBooksList.getItemCount() > 1) && (bookTitleinBooksList.getSelectedIndex() > 0)) {
                updatePoemsTitleByBookInBooks(bookTitleinBooksList.getSelectedItem().toString());
            }
            addButtonsInBooksTable();
        }
        setColumnContentCenter(0, booksTable);
        setColumnContentCenter(3, booksTable);
    }//GEN-LAST:event_bookTitleinBooksListActionPerformed

    private void saveTokensBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTokensBtnActionPerformed
        String[] tokens = new String[tokensList.size()];
        int i = 0;
        for (String s : tokensList) {
            tokens[i] = s;
            ++i;
        }
        if (tokens.length > 0) {
            if (ibllFacade.insertTokens((new PoemsTO(bookTitleinPoemList.getSelectedItem().toString(),
                    poemsTableModel.getValueAt(0, 1).toString(), misra1SendToOtherTab, misra2SendToOtherTab, "", "")), tokens)) {
                JOptionPane.showMessageDialog(frame, "Tokens along with there Tags Saved!");
            } else {
                JOptionPane.showMessageDialog(frame, "Assignment Failure!\n"
                        + "Hint: Tags may already exists!\nHint: Unable to assign Tags to some Tokens!");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Assignment Failure!\n"
                    + "Hint: Tags may already exists!\n Hint: Unable to assign Tags to some Tokens!");
        }
        misra1InTokeniz.setText("");
        misra2InTokeniz.setText("");
        tokensTablePanel.setVisible(true);
        tokenizePanelObj.setVisible(false);
        updateTokensInTable();
        addButtonInTokensTable(2);
    }//GEN-LAST:event_saveTokensBtnActionPerformed

    private void rootsInRootsListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rootsInRootsListActionPerformed
        if (rootRepeatInTableChck) {
            if ((rootsInRootsList.getSelectedItem() == null) || ((rootsInRootsList.getItemCount() == 1)
                    || (rootsInRootsList.getSelectedIndex() == 0))) {
                updateRootsData();
            } else if ((rootsInRootsList.getItemCount() > 1) && (rootsInRootsList.getSelectedIndex() > 0)) {
                updateVersesByRoot(rootsInRootsList.getSelectedItem().toString());
            }
            addButtonInRootsTable();
        }
    }//GEN-LAST:event_rootsInRootsListActionPerformed

    private void searchRootModeRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchRootModeRadioBtnActionPerformed
        rootRepeatInTableChck = searchRootModeRadioBtn.isSelected();
    }//GEN-LAST:event_searchRootModeRadioBtnActionPerformed

    private void rootsInAssignRootsListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rootsInAssignRootsListActionPerformed
        if (assignRootChck) {
            String root = rootsInAssignRootsList.getSelectedItem().toString();
            boolean chck = true;
            // prevent redundancy of same values in table
            for (int i = 0; i < assignRootsTable.getRowCount(); ++i) {
                if (root == assignRootsTableModel.getValueAt(i, 0)) {
                    chck = false;
                }
            }
            if (chck) {
                assignRootsTableModel.addRow(new Object[]{root});
            }
            addButtonInAssignRootsTable();
        }
    }//GEN-LAST:event_rootsInAssignRootsListActionPerformed

    private void closeAssignRootsPanelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAssignRootsPanelBtnActionPerformed
        rootsInRootsList.setEnabled(true);
        searchRootModeRadioBtn.setEnabled(true);
        rootsTablePanel.setVisible(true);
        assignRootsPanelObj.setVisible(false);
        manualRootsTxtField.setText("");
        updateRootsData();
    }//GEN-LAST:event_closeAssignRootsPanelBtnActionPerformed

    private void assignRootsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignRootsBtnActionPerformed
        for (int i = 0; i < assignRootsTable.getRowCount(); ++i) {
            String root = assignRootsTableModel.getValueAt(i, 0).toString();
            if (!ibllFacade.insertRootsByVerse(new RootTO(root, "Verified"), new PoemsTO(bookTSendToOtherTab, poemTSendToOtherTab,
                    misra1SendToOtherTab, misra2SendToOtherTab, "", ""))) {
                JOptionPane.showMessageDialog(frame, "Insertion Failure!");
            }
        }
        closeAssignRootsPanelBtn.doClick();
    }//GEN-LAST:event_assignRootsBtnActionPerformed

    private void manualRootsTxtFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_manualRootsTxtFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String root = manualRootsTxtField.getText();
            if (root.length() >= 2 && root.length() <= 4) {
                boolean chck = true;
                // prevent redundancy of same values in table
                for (int i = 0; i < assignRootsTable.getRowCount(); ++i) {
                    if (root == assignRootsTableModel.getValueAt(i, 0)) {
                        chck = false;
                    }
                }
                if (chck) {
                    assignRootsTableModel.addRow(new Object[]{root});
                    manualRootsTxtField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Root size must be in 2..4.");
            }
        }
    }//GEN-LAST:event_manualRootsTxtFieldKeyPressed

    private void changeLanguageBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeLanguageBtnMouseClicked
        setLanguageMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        changeLanguageMenu.show(evt.getComponent(), -70, 34);
    }//GEN-LAST:event_changeLanguageBtnMouseClicked

    private void englishMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_englishMenuItemActionPerformed
        setLanguageOfApplication(0);
    }//GEN-LAST:event_englishMenuItemActionPerformed

    private void arabicMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arabicMenuItemActionPerformed
        setLanguageOfApplication(1);
    }//GEN-LAST:event_arabicMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        XAMPPManager.stopXAMPP();
    }//GEN-LAST:event_formWindowClosing

    private void dragAndDropFilePath() {
        importPoemBrowsePanel.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }

                Transferable transferable = support.getTransferable();
                try {
                    List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    if (files != null && !files.isEmpty()) {
                        String filePath = files.get(0).getPath(); // Get the first file's path
                        browseTxtField.setText(filePath);
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                }
                return true;
            }
        });
    }

    /*public static void main(String[] args) {
        XAMPPManager.startXAMPP();
        IPoemsDAO ipoemDAO = new PoemsDAO();
        IBookDAO ibookDao = new BookDAO();
        IRootDAO irootDao = new RootDAO();
        ITokenDAO itokenDao = new TokensDAO();
        IDALFacade idalF = new DALFacade(ipoemDAO, ibookDao, irootDao, itokenDao);
        IBookBO bookBo = new BookBO(idalF);
        IPoemBO objPoem = new PoemBO(idalF);
        IRootBO irootBo = new RootBO(idalF);
        ITokenBO itokenBo = new TokenBO(idalF);
        IBLLFacade bllF = new BLLFacade(objPoem, bookBo, irootBo, itokenBo);
        applyLaFOnJFrame();
        java.awt.EventQueue.invokeLater(() -> {
            new GUI_PO(bllF).setVisible(true);
        });
    }*/

    //this function will setting all components text explicitly
    // so that we can internationalize it
    private void changeLangHelpingFunc(String language) {
        Locale locale = null;
        if (language.equals("English")) {
            locale = new Locale("en", "US");
        } else if (language.equals("Arabic")) {
            locale = new Locale("ar", "AE");
        }
        Locale.setDefault(locale);
        ResourceBundle rb = ResourceBundle.getBundle("presentationLayer.Bundle");

        createBookBtn.setText(rb.getString("GUI_PO.createBookBtn.text"));
        newBookRadioBtn.setText(rb.getString("GUI_PO.newBookRadioBtn.text"));
        saveTokensBtn.setText(rb.getString("GUI_PO.saveTokensBtn.text"));
        splitVerseBtn.setText(rb.getString("GUI_PO.splitVerseBtn.text"));
        assignRootsBtn.setText(rb.getString("GUI_PO.assignRootsBtn.text"));
        bookinBooksTitleLbl3.setText(rb.getString("GUI_PO.bookinBooksTitleLbl3.text"));
        bookinBooksTitleLbl.setText(rb.getString("GUI_PO.bookinBooksTitleLbl.text"));
        tabsPaneObj.setTitleAt(1, rb.getString("GUI_PO.booksPanel.TabConstraints.tabTitle"));
        tabsPaneObj.setTitleAt(2, rb.getString("GUI_PO.poemsPanel.TabConstraints.tabTitle"));
        tabsPaneObj.setTitleAt(3, rb.getString("GUI_PO.tokenPanel.TabConstraints.tabTitle"));
        tabsPaneObj.setTitleAt(0, rb.getString("GUI_PO.dashboardPanel.TabConstraints.tabTitle"));
        tabsPaneObj.setTitleAt(4, rb.getString("GUI_PO.rootsPanel.TabConstraints.tabTitle"));
        poemsTable.getColumnModel().getColumn(3).setHeaderValue(rb.getString("GUI_PO.poemsTable.columnModel.title3"));
        poemsTable.getColumnModel().getColumn(2).setHeaderValue(rb.getString("GUI_PO.poemsTable.columnModel.title2"));
        poemsTable.getColumnModel().getColumn(0).setHeaderValue(rb.getString("GUI_PO.poemsTable.columnModel.title0"));
        rootsTable.getColumnModel().getColumn(4).setHeaderValue(rb.getString("GUI_PO.rootsTable.columnModel.title4"));
        rootsTable.getColumnModel().getColumn(3).setHeaderValue(rb.getString("GUI_PO.rootsTable.columnModel.title3"));
        rootsTable.getColumnModel().getColumn(2).setHeaderValue(rb.getString("GUI_PO.rootsTable.columnModel.title2"));
        rootsTable.getColumnModel().getColumn(1).setHeaderValue(rb.getString("GUI_PO.rootsTable.columnModel.title1"));
        rootsTable.getColumnModel().getColumn(0).setHeaderValue(rb.getString("GUI_PO.rootsTable.columnModel.title0"));
        booksTable.getColumnModel().getColumn(0).setHeaderValue(rb.getString("GUI_PO.booksTable.columnModel.title0"));
        booksTable.getColumnModel().getColumn(1).setHeaderValue(rb.getString("GUI_PO.booksTable.columnModel.title1"));
        booksTable.getColumnModel().getColumn(2).setHeaderValue(rb.getString("GUI_PO.booksTable.columnModel.title2"));
        booksTable.getColumnModel().getColumn(3).setHeaderValue(rb.getString("GUI_PO.booksTable.columnModel.title3"));
        booksTable.getColumnModel().getColumn(4).setHeaderValue(rb.getString("GUI_PO.booksTable.columnModel.title4"));
        tokensTable.getColumnModel().getColumn(0).setHeaderValue(rb.getString("GUI_PO.tokensTable.columnModel.title0"));
        tokensTable.getColumnModel().getColumn(1).setHeaderValue(rb.getString("GUI_PO.tokensTable.columnModel.title1"));
        tokensTable.getColumnModel().getColumn(2).setHeaderValue(rb.getString("GUI_PO.tokensTable.columnModel.title2"));
        assignTokens.setText(rb.getString("GUI_PO.assignTokens.text"));
        assignRoots.setText(rb.getString("GUI_PO.assignRoots.text"));
        importPoemPanelBtn.setText(rb.getString("GUI_PO.importPoemPanelBtn.text"));
        existingPoemRadioBtn.setText(rb.getString("GUI_PO.existingPoemRadioBtn.text"));
        newPoemRadioBtn.setText(rb.getString("GUI_PO.newPoemRadioBtn.text"));
        poemTitleLbl.setText(rb.getString("GUI_PO.poemTitleLbl.text"));
        bookinPoemTitleLbl.setText(rb.getString("GUI_PO.bookinPoemTitleLbl.text"));
        appTitle.setText(rb.getString("GUI_PO.appTitle.text"));
        searchRootModeRadioBtn.setText(rb.getString("GUI_PO.searchRootModeRadioBtn.text"));
        createPoemBtn.setText(rb.getString("GUI_PO.createPoemBtn.text"));
        bookinPoemTitleLbl1.setText(rb.getString("GUI_PO.bookinPoemTitleLbl1.text"));
        bookinBooksTitleLbl2.setText(rb.getString("GUI_PO.bookinBooksTitleLbl2.text"));
        jLabel8.setText(rb.getString("GUI_PO.jLabel8.text"));
        importPoemBtn.setText(rb.getString("GUI_PO.importPoemBtn.text"));
        browseFilePathBtn.setText(rb.getString("GUI_PO.browseFilePathBtn.text"));
        bookinBooksTitleLbl1.setText(rb.getString("GUI_PO.bookinBooksTitleLbl1.text"));
        setLanguageMenu.setText(rb.getString("GUI_PO.setLanguageMenu.text"));
        englishMenuItem.setText(rb.getString("GUI_PO.englishMenuItem.text"));
        arabicMenuItem.setText(rb.getString("GUI_PO.arabicMenuItem.text"));

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appTitle;
    private javax.swing.JMenuItem arabicMenuItem;
    private javax.swing.JMenuItem assignRoots;
    private javax.swing.JButton assignRootsBtn;
    private javax.swing.JPanel assignRootsInnerPanel;
    private javax.swing.JPanel assignRootsPanel;
    private javax.swing.JTable assignRootsTable;
    private javax.swing.JPanel assignRootsTablePanel;
    private javax.swing.JScrollPane assignRootsTableScrollPane;
    private javax.swing.JMenuItem assignTokens;
    private javax.swing.JPanel bookTablePanel;
    private javax.swing.JPanel bookTitlePanelinBook;
    private javax.swing.JPanel bookTitlePanelinPoem;
    private javax.swing.JComboBox<String> bookTitleinBooksList;
    private javax.swing.JComboBox<String> bookTitleinImportPoemList;
    private javax.swing.JComboBox<String> bookTitleinPoemList;
    private javax.swing.JPanel bookTypePanelinBook;
    private javax.swing.JLabel bookinBooksTitleLbl;
    private javax.swing.JLabel bookinBooksTitleLbl1;
    private javax.swing.JLabel bookinBooksTitleLbl2;
    private javax.swing.JLabel bookinBooksTitleLbl3;
    private javax.swing.JLabel bookinPoemTitleLbl;
    private javax.swing.JLabel bookinPoemTitleLbl1;
    private javax.swing.JPanel booksCRUDPanelRound;
    private javax.swing.JPanel booksPanel;
    private javax.swing.JTable booksTable;
    private javax.swing.JScrollPane booksTableScrollPane;
    private javax.swing.JButton browseFilePathBtn;
    private javax.swing.JTextField browseTxtField;
    private javax.swing.JButton changeLanguageBtn;
    private javax.swing.JPopupMenu changeLanguageMenu;
    private javax.swing.JButton closeAssignRootsPanelBtn;
    private javax.swing.JButton closeImportPoemPanelBtn;
    private javax.swing.JButton createBookBtn;
    private javax.swing.JButton createPoemBtn;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JMenuItem englishMenuItem;
    private javax.swing.JRadioButton existingPoemRadioBtn;
    private javax.swing.JPanel importPoemBrowsePanel;
    private javax.swing.JButton importPoemBtn;
    private javax.swing.JPanel importPoemPanel;
    private javax.swing.JButton importPoemPanelBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel leftSidePanel;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField manualRootsTxtField;
    private javax.swing.JTabbedPane menuTabsPane;
    private javax.swing.JTextField misra1InRootsTxtField;
    private javax.swing.JTextField misra1InTokeniz;
    private javax.swing.JTextField misra2InRootsTxtField;
    private javax.swing.JTextField misra2InTokeniz;
    private javax.swing.JRadioButton newBookRadioBtn;
    private javax.swing.JRadioButton newPoemRadioBtn;
    private javax.swing.ButtonGroup poemPanelChckBtnGroup;
    private javax.swing.JPanel poemTablePanel;
    private javax.swing.JLabel poemTitleLbl;
    private javax.swing.JComboBox<String> poemTitleinPoemList;
    private javax.swing.JPanel poemTypePanelinPoemRound;
    private javax.swing.JPanel poemsCRUDPanel;
    private javax.swing.JPanel poemsPanel;
    private javax.swing.JTable poemsTable;
    private javax.swing.JScrollPane poemsTableScrollPane;
    private javax.swing.JPanel rootPanelinRoots;
    private javax.swing.JPanel rootsCRUDPanelRound;
    private javax.swing.JComboBox<String> rootsInAssignRootsList;
    private javax.swing.JComboBox<String> rootsInRootsList;
    private javax.swing.JPanel rootsPanel;
    private javax.swing.JTable rootsTable;
    private javax.swing.JPanel rootsTablePanel;
    private javax.swing.JScrollPane rootsTableScrollPane;
    private javax.swing.JButton saveTokensBtn;
    private javax.swing.JPanel searchModePanelinRoot;
    private javax.swing.JRadioButton searchRootModeRadioBtn;
    private javax.swing.JMenu setLanguageMenu;
    private javax.swing.JButton splitVerseBtn;
    private javax.swing.JPanel suggRootsPanelinAssignRoots;
    private javax.swing.JPanel tokenPanel;
    private javax.swing.JPanel tokenizationPanel;
    private javax.swing.JPanel tokenizePanel;
    private javax.swing.JPanel tokensInnerPanel;
    private javax.swing.JTable tokensTable;
    private javax.swing.JPanel tokensTablePanel;
    private javax.swing.JScrollPane tokensTableScrollPane;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel verseInTokenizePanel;
    private javax.swing.JPanel versesAssignPoemsPanel;
    private javax.swing.JPopupMenu viewVersePopupMenu;
    // End of variables declaration//GEN-END:variables
    private DefaultTableModel poemsTableModel;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel tokensTableModel;
    private DefaultTableModel rootsTableModel;
    private DefaultTableModel assignRootsTableModel;
    private JTabbedPane tabsPaneObj;
    private PanelRound tokenizePanelObj;
}
