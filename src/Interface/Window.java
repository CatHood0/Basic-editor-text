package Interface;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Window extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 2L;
    
    JScrollPane bar;
    private int Words = 0;
    private boolean OnSave = true, Wrap = false;
    private String lastName, TipoDeLetra = "Arial";
    String[] arrFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private File NameFiles = null;
    private JFileChooser chooser;
    
    private JLabel labelLines, labelWords;
    private JMenuBar Menu;
    private JMenu Theme;
    private JMenuItem Black, Purple, Default;
    private JMenu File, Edit, Format, View;
    private JMenuItem New, Open, Save, Delete, CloseFile;
    private JMenuItem  Cut, Copy, Paste, SelectAll, TimeOrDate;
    private JMenuItem WordWrap;
    private JMenuItem Zoom;
    private JMenuItem ZoomIn, ZoomOut, RestoreZoomDefault;
    
    private JSpinner SizeWords, TypeWord;
    private int StyleFont = 0;
    private JPanel panelMain, ToolsViews;
    private JTextArea textArea;
    
    private JToolBar Barra;
    private JButton Plain, Bold, Italic, BI;
    
    private int countSize = 16;
    
    public Window() {
        
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        Init();
        this.addWindowListener(new WindowsEvents());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    private void Init() {
        
        panels();
        labels();
        Buttons();
        Menus();
        TextArea();
        
    }
    
    private void panels() {
        
        panelMain = new JPanel();
        panelMain.setBackground(Color.GRAY);
        panelMain.setLayout(new BorderLayout());
        this.getContentPane().add(panelMain);
        
        ToolsViews = new JPanel();
        ToolsViews.setLayout(new FlowLayout());
        ToolsViews.setPreferredSize(new Dimension(710, 30));
        ToolsViews.setBackground(new Color(220, 220, 220));
        panelMain.add(ToolsViews, BorderLayout.PAGE_END);
        
        Barra = new JToolBar();
        Barra.setBackground(new Color(220,220,220));
        Barra.setOrientation(SwingConstants.VERTICAL);
        Barra.setPreferredSize(new Dimension(40,700));
        Barra.setFloatable(false);
        this.add(Barra, BorderLayout.LINE_START);
        
    }
    
    private void CountWords() {
        Words = textArea.getCaretPosition();
        labelWords.setText("Words: " + Words);
        
    }
    
    private void labels() {
        
        labelLines = new JLabel("Lines: 0");
        labelLines.setForeground(Color.BLACK);
        labelLines.setBounds(380, 10, 100, 20);
        labelLines.setFont(new Font("Times new roman", Font.PLAIN, 14));
        ToolsViews.add(labelLines);
        
        labelWords = new JLabel("Words: 0");
        labelWords.setForeground(Color.BLACK);
        labelWords.setBounds(450, 10, 100, 20);
        labelWords.setFont(new Font("Times new roman", Font.PLAIN, 14));
        ToolsViews.add(labelWords);
        
    }
    
    private void Buttons(){
        
        Plain = new JButton("PL");
        Plain.setFocusable(false);
        Plain.setFocusPainted(false);
        Plain.setFont(new Font("Arial", Font.PLAIN, 14));
        Plain.setHorizontalAlignment(0);
        Barra.add(Plain);
        
        Italic = new JButton("ITA");
        Italic.setFocusable(false);
        Italic.setFocusPainted(false);
        Italic.setFont(new Font("Arial", Font.PLAIN, 14));
        Italic.setHorizontalAlignment(0);
        Barra.add(Italic);
        
        Bold = new JButton("BO");
        Bold.setFocusable(false);
        Bold.setFocusPainted(false);
        Bold.setFont(new Font("Arial", Font.PLAIN, 14));
        Bold.setHorizontalAlignment(0);
        Barra.add(Bold);
        
        BI= new JButton("BI");
        BI.setFocusable(false);
        BI.setFocusPainted(false);
        BI.setFont(new Font("Arial", Font.PLAIN, 14));
        BI.setHorizontalAlignment(0);
        Barra.add(BI);
        var Change = new ChangeStyleWords();
        Plain.addActionListener(Change);
        Italic.addActionListener(Change);
        Bold.addActionListener(Change);
        BI.addActionListener(Change);
        
        TypeWord = new JSpinner(new SpinnerListModel(arrFonts));
        TypeWord.setBounds(70, 0, 140, 30);
        TypeWord.setValue("Arial");
        TipoDeLetra = (String) TypeWord.getModel().getValue();
        ToolsViews.add(TypeWord, FlowLayout.LEFT);
        TypeWord.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                TipoDeLetra = (String) TypeWord.getModel().getValue();
                textArea.setFont(new Font(TipoDeLetra, StyleFont, (int) SizeWords.getValue()));
            }
        });
        
        SizeWords = new JSpinner();
        SizeWords.setBounds(230, 0, 70, 30);
        SizeWords.setValue(16);
        ToolsViews.add(SizeWords, FlowLayout.LEFT);
        SizeWords.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, (int) SizeWords.getValue()));
            }
        });
    }
    
    private void CreateFiles(String ruta) {
        
        if (textArea.getText().isEmpty()) {
            NameFiles = null;
            this.setTitle("untitle");
            lastName = null;
        } else {
            
            int salida = JOptionPane.showConfirmDialog(null, "Do you want to save the changes made?");
            if (salida == 0) {
                JFileChooser chooserSaveFile = new JFileChooser();
                chooserSaveFile.setCurrentDirectory(new File("src\\File"));
                
                int response = chooserSaveFile.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    OnSave = true;
                    FileWriter archivo2;
                    PrintWriter leidaArchivo = null;
                    
                    if(lastName != null || !this.getTitle().equals("untitle")){
                        textArea.setText(null);
                    }
                    
                    try {
                        archivo2 = new FileWriter(chooserSaveFile.getSelectedFile().getAbsolutePath()+ ".txt");
                        NameFiles = new File(chooserSaveFile.getSelectedFile().getAbsolutePath()+".txt");
                        Save.setEnabled(false);
                        lastName = (String) chooserSaveFile.getSelectedFile().getName();
                        this.setTitle(lastName);
                        leidaArchivo = new PrintWriter(archivo2);
                        leidaArchivo.println(textArea.getText());
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "File not found");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        leidaArchivo.close();
                    }
                }
            } else if (salida == 1) {
                NameFiles = null;
                lastName = null;
                textArea.setText(null);
                this.setTitle("untitle");
            }
        }
    }
    
    
    private void OpenFiles() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("src\\File"));
        
        FileNameExtensionFilter Filter = new FileNameExtensionFilter("Text", "txt");
        chooser.setFileFilter(Filter);
        
        int responce = chooser.showOpenDialog(null);
        
        if (responce == JFileChooser.APPROVE_OPTION) {
            OnSave = true;
            File file = new File(chooser.getSelectedFile().getAbsolutePath());
            Scanner reader = null;
            
            try {
                reader = new Scanner(file);
                NameFiles = new File(chooser.getSelectedFile().getAbsolutePath());
                lastName = (String) chooser.getSelectedFile().getName();
                Save.setEnabled(false);
                this.setTitle(lastName);
                if (file.isFile()) {
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        textArea.setText(line);
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File not found");
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "The file not exist");
            }
        }
    }
    
    private void Save() {
        
        if (NameFiles.exists()) {
            OnSave = true;
            File fileSave = new File(NameFiles.getAbsolutePath());
            PrintWriter write = null;
            try {
                Save.setEnabled(false);
                write = new PrintWriter(fileSave);
                write.println(textArea.getText());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "You can't open a file as it doesn't exist");
            } finally {
                write.close();
            }
        }
        if (this.getTitle().equals("untitle")) {
            NameFiles = null;
            JFileChooser Selector = new JFileChooser();
            Selector.setCurrentDirectory(new File("src\\File"));
            
            int response = Selector.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                OnSave = true;
                FileWriter NewFile;
                PrintWriter leidaArchivo = null;
                
                try {
                    NewFile = new FileWriter(Selector.getSelectedFile().getAbsolutePath());
                    NameFiles = new File(Selector.getSelectedFile().getAbsolutePath());
                    lastName = (String) Selector.getSelectedFile().getName();
                    this.setTitle(lastName);
                    Save.setEnabled(false);
                    leidaArchivo = new PrintWriter(NewFile);
                    if (!textArea.getText().isEmpty()) {
                        leidaArchivo.println(textArea.getText());
                    }
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "File");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    leidaArchivo.close();
                }
            }
        }
    }
    
    private void CloseFiles() {
        NameFiles = null;
        textArea.setText(null);
        this.setTitle(null);
        lastName = null;
    }
    
    private void ExitUnSaveState() {
        
        int OptionSelected = JOptionPane.showConfirmDialog(null, "Do you want to save the changes made?");
        
        if (OptionSelected == 0) {
            if (NameFiles == null) {
                JFileChooser chooserSaveFile = new JFileChooser();
                chooserSaveFile.setCurrentDirectory(new File("src\\File"));
                
                int response = chooserSaveFile.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    OnSave = true;
                    File archivo2;
                    PrintWriter leidaArchivo = null;
                    
                    archivo2 = new File(chooserSaveFile.getSelectedFile().getAbsolutePath());
                    try {
                        NameFiles = new File(chooserSaveFile.getSelectedFile().getAbsolutePath());
                        String nombre = (String) NameFiles.getAbsolutePath();
                        System.out.println(nombre);
                        lastName = (String) chooserSaveFile.getSelectedFile().getName();
                        setTitle(lastName);
                        Save.setEnabled(false);
                        leidaArchivo = new PrintWriter(archivo2);
                        leidaArchivo.println(textArea.getText());
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "File not found");
                        
                    } finally {
                        leidaArchivo.close();
                    }
                }
            }
            else{
                Save();
            }
        }
    }
    
    
    private void Menus() {
        
        Menu = new JMenuBar();
        
        File = new JMenu("File"); New = new JMenuItem("New"); Open = new JMenuItem("Open File"); Save = new JMenuItem("Save");
        CloseFile = new JMenuItem("Close file");
        Delete = new JMenuItem("Delete File");
        Edit = new JMenu("Edit");
        //separator
        Cut = new JMenuItem("Cut"); Copy = new JMenuItem("Copy"); Paste = new JMenuItem("Paste");
        SelectAll = new JMenuItem("Select All");
        TimeOrDate = new JMenuItem("Time/Date");
        
        Format = new JMenu("Format"); WordWrap = new JCheckBoxMenuItem("WordWrap");
        
        View = new JMenu("View"); Zoom = new JMenu("Zoom");
        ZoomIn = new JMenuItem("Zoom in"); ZoomOut = new JMenuItem("Zoom out"); RestoreZoomDefault = new JMenuItem("Restore zoom default");
        
        
        Theme = new JMenu("Themes");  Default = new JMenuItem("Default"); Black = new JMenuItem("Black");
        Purple = new JMenuItem(" Dark Purple");
        
        this.setJMenuBar(Menu);
        Menu.add(File); Menu.add(Edit); Menu.add(Format); Menu.add(View); Menu.add(Theme);
        
        File.add(New); File.add(new JSeparator(0)); File.add(Open); File.add(Save);
        File.add(new JSeparator(0)); File.add(Delete);File.add(CloseFile);
        Edit.add(Cut); Edit.add(Copy); Edit.add(Paste); Edit.add(new JSeparator(0)); Edit.add(SelectAll); Edit.add(TimeOrDate);
        Format.add(WordWrap);
        View.add(Zoom);
        Zoom.add(ZoomIn); Zoom.add(ZoomOut); Zoom.add(new JSeparator(0)); Zoom.add(RestoreZoomDefault);
        Theme.add(Default); Theme.add(Black); Theme.add(Purple);
        
        New.addActionListener(this);  Open.addActionListener(this);  Save.addActionListener(this); Delete.addActionListener(this);
        Cut.addActionListener(this); Copy.addActionListener(this); Paste.addActionListener(this); SelectAll.addActionListener(this);
        TimeOrDate.addActionListener(this);
        CloseFile.addActionListener(this); WordWrap.addActionListener(this);
        ZoomIn.addActionListener(this); ZoomOut.addActionListener(this); RestoreZoomDefault.addActionListener(this);
        Black.addActionListener(this); Purple.addActionListener(this); Default.addActionListener(this);
    }
    
    private void TextArea() {
        
        textArea = new JTextArea();
        textArea.setText(null);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Arial", StyleFont, 16));
        
        
        bar = new JScrollPane(textArea);
        bar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelMain.add(bar, BorderLayout.CENTER);
        
        
        var KeyArea = new EventKeys();
        textArea.addKeyListener(KeyArea);
        var MouseArea = new mouseCursor();
        textArea.addMouseListener(MouseArea);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == New) {
            CreateFiles("src\\File");
        }
        if (e.getSource() == Open) {
            OpenFiles();
        }
        if (e.getSource() == Save) {
            try {
                Save();
            } catch (NullPointerException ex) {
                
                JFileChooser chooserSaveFile = new JFileChooser();
                chooserSaveFile.setCurrentDirectory(new File("src\\File"));
                
                int response = chooserSaveFile.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    FileWriter archivo2;
                    PrintWriter leidaArchivo = null;
                    
                    
                    try {
                        archivo2 = new FileWriter(chooserSaveFile.getSelectedFile().getAbsolutePath()+".txt");
                        NameFiles = new File(chooserSaveFile.getSelectedFile().getAbsolutePath()+".txt");
                        Save.setEnabled(false);
                        lastName = (String) chooserSaveFile.getSelectedFile().getName();
                        this.setTitle(lastName);
                        leidaArchivo = new PrintWriter(archivo2);
                        leidaArchivo.println(textArea.getText());
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "File not found");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        leidaArchivo.close();
                    }
                }
            }
        }
        if (e.getSource() == Cut) {
            Save.setEnabled(true);
            textArea.cut();
        }
        
        if (e.getSource() == Copy) {
            textArea.copy();
        }
        if (e.getSource() == Paste) {
            Save.setEnabled(true);
            CountWords();
            textArea.paste();
        }
        
        if (e.getSource() == WordWrap) {
            if (Wrap == false) {
                Save.setEnabled(true);
                Wrap = true;
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setCaretPosition(0);
            }else if (Wrap == true) {
                Save.setEnabled(false);
                textArea.setLineWrap(false);
                textArea.setWrapStyleWord(false);
                textArea.setCaretPosition(0);
            }
        }
        if (e.getSource() == TimeOrDate) {
            textArea.append(String.valueOf(new Date()));
        }
        
        if(e.getSource()==ZoomIn){
            countSize+= 4;
            textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, countSize));
            SizeWords.setValue(countSize);
        }
        
        if(e.getSource()==ZoomOut){
            countSize-= 4;
            textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, countSize));
            SizeWords.setValue(countSize);
        }
        if(e.getSource()==RestoreZoomDefault){
            countSize = 16;
            textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, countSize));
            SizeWords.setValue(countSize);
        }
        
        if(e.getSource()==SelectAll){
            textArea.selectAll();
        }
        
        if(e.getSource()==CloseFile){
            OnSave = true;
            Save.setEnabled(true);
            CloseFiles();
        }
        if(e.getSource()==Delete){
            try {
                if(NameFiles.exists() && lastName != null){
                    int OptionSelected = JOptionPane.showConfirmDialog(null, "Are you sure to delete this file?, you will not be able to restore this file again");
                    if(OptionSelected == 0){
                        File file2 = new File(NameFiles.getAbsolutePath());
                        file2.delete();
                        lastName = null;
                        NameFiles = null;
                        textArea.setText(null);
                        this.setTitle(null);
                    }
                }
            }catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "you don't have any open file");
            }
        }
        if(e.getSource()==Default){
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
            ToolsViews.setBackground(new Color(220,220,220));
            labelLines.setForeground(Color.BLACK);
            labelWords.setForeground(Color.BLACK);
            Barra.setBackground(new Color(220,220,220));
            Plain.setForeground(Color.BLACK);
            Bold.setForeground(Color.BLACK);
            Italic.setForeground(Color.BLACK);
            BI.setForeground(Color.BLACK);
        }
        
        if(e.getSource()==Black){
            textArea.setBackground(new Color(30,30,30));
            textArea.setForeground(Color.WHITE);
            ToolsViews.setBackground(new Color(50,50,50));
            labelLines.setForeground(Color.WHITE);
            labelWords.setForeground(Color.WHITE);
            Barra.setBackground(new Color(50,50,50));
            Plain.setForeground(Color.WHITE);
            Bold.setForeground(Color.WHITE);
            Italic.setForeground(Color.WHITE);
            BI.setForeground(Color.WHITE);
        }
        
        if(e.getSource()==Purple){
            textArea.setBackground(new Color(26,19,38));
            textArea.setForeground(new Color(0,255,174));
            ToolsViews.setBackground(new Color(26,19,38));
            labelLines.setForeground(new Color(0,255,174));
            labelWords.setForeground(new Color(0,255,174));
            Barra.setBackground(new Color(26,19,38));
            Plain.setForeground(new Color(0,255,174));
            Bold.setForeground(new Color(0,255,174));
            Italic.setForeground(new Color(0,255,174));
            BI.setForeground(new Color(0,255,174));
        }
    }
    
    class mouseCursor extends MouseAdapter{
        
        @Override
        public void mouseReleased(MouseEvent e) {
            
            if(e.getSource()==textArea){
                int count = textArea.getLineCount();
                labelLines.setText("Lines: " + count);
            }
        }
    }
    
    class EventKeys extends KeyAdapter {
        
        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getSource()==textArea) {
                OnSave = false;
                Save.setEnabled(true);
                CountWords();
                int countLine = textArea.getLineCount();
                labelLines.setText("Lines: " + countLine);
                
            }
            if(Words == 0 && lastName==null){
                OnSave = true;
            }
            
            
            
        }
    }
    class WindowsEvents extends WindowAdapter {
        
        @Override
        public void windowClosing(WindowEvent e) {
            if (OnSave == false) {
                ExitUnSaveState();
            }
            else{
                dispose();
            }
        }
    }
    
    class ChangeStyleWords implements ActionListener{
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==Italic){
                StyleFont = 2;
                textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, (int) SizeWords.getValue()));
            }
            if(e.getSource()==Plain){
                StyleFont = 0;
                textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, (int) SizeWords.getValue()));
            }
            if(e.getSource()==Bold){
                StyleFont = 1;
                textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, (int) SizeWords.getValue()));
            }
            if(e.getSource()==BI){
                StyleFont = 3;
                textArea.setFont(new Font(textArea.getFont().getFamily(), StyleFont, (int) SizeWords.getValue()));
            }
            
            
        }
    }
}
