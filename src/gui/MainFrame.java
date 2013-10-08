package gui;

import java.awt.FileDialog;
import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import core.MusicGeneratorManager;

public class MainFrame extends JFrame {

	private MainPanel mainPanel;
	public MenuBar menus;
	private MusicGeneratorManager generator;
	private MainFrame frame;
	private RecordFrame recorder;
	
	public MainFrame(String title, MusicGeneratorManager _generator) throws HeadlessException {
		super(title);
		
		this.frame = this;		
		this.setGenerator(_generator);

        //Create and set up the content pane.
		mainPanel = new MainPanel(frame, getGenerator());
		mainPanel.setOpaque(true); //content panes must be opaque
        
		initMenu();
		
        setContentPane(mainPanel);
        setMenuBar(menus);
        
		recorder = new RecordFrame(this);
	}
	
	private void initMenu() {
		menus = new MenuBar();
		Menu edit  = new Menu("Файл", true);
		
		//------
		MenuItem saveToMidi = new MenuItem("Сохранить в MIDI файл");
		saveToMidi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 FileDialog fd = new FileDialog(frame, "Сохранение в MIDI файл...", FileDialog.SAVE);
	             fd.show();
	             
	             //write a MIDI file and stave properties to disk
	             if ( fd.getFile() != null)
	              	getGenerator().saveMidi(fd.getDirectory() + fd.getFile());
			}
		});
		
        edit.add( saveToMidi);
        
        //------
        MenuItem saveToMP3 = new MenuItem("Сохранить в MP3 файл");
        saveToMP3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(frame, "Сохранение в MP3 файл...", FileDialog.SAVE);
	             fd.show();
	             
	             //write a MIDI file and stave properties to disk
	             if ( fd.getFile() != null)
	              	getGenerator().saveMP3(fd.getDirectory() + fd.getFile());
			}
		});
        edit.add(saveToMP3);
        menus.add(edit);
	}
	
	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public MusicGeneratorManager getGenerator() {
		return generator;
	}

	public void setGenerator(MusicGeneratorManager generator) {
		this.generator = generator;
	}

	public void showRecorder() {
		recorder.setVisible(true);
		//Place to the right of generator
		recorder.setLocation(getLocation().x + getSize().width, getLocation().y); 
		recorder.setSize(recorder.getSize().width, getSize().height);
	}
}
