package proyectofuego;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.*;

/**
 *
 * @author PC
 */
public class GeneralControlPanel extends JPanel{
    
    private FireControlPanel fireControlPanel;
    private ConvolutionControlPanel convolutionControlPanel;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;

    public FireControlPanel getFireControlPanel() {
        return fireControlPanel;
    }

    public ConvolutionControlPanel getConvolutionControlPanel() {
        return convolutionControlPanel;
    }

    public GeneralControlPanel(FireControlPanel fireControlPanel, ConvolutionControlPanel convolutionControlPanel) {
        this.fireControlPanel = fireControlPanel;
        this.convolutionControlPanel = convolutionControlPanel;
        initComponents();
    }
    
    public void initComponents(){
        
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.fill=GridBagConstraints.BOTH;         
        this.tabbedPane=new JTabbedPane();
        JScrollPane scrollPane=new JScrollPane(this.fireControlPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.tabbedPane.addTab("Fire Control Panel", scrollPane);
        this.tabbedPane.addTab("Convolution Control Panel", this.convolutionControlPanel);
        this.add(this.tabbedPane,c);
    }
}
