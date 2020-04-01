
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



@SuppressWarnings("serial")
public class App extends JFrame{
    
    private JPanel rootPanel;
    private static App app;
    
    private App() {
        super("Snake");
        createApp();
    }
    
    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        
        return app;
    }
    
    private void createApp() {
        double[] screen = calculateScreen();
        setPreferredSize(new Dimension((int) screen[0], (int) screen[1]));
        
        rootPanel = new JPanel();
        rootPanel.setBackground(Color.white);
        rootPanel.setPreferredSize(getPreferredSize());
        rootPanel.setFocusable(true);
        
        getContentPane().add(rootPanel);
                
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png"));
        setIconImage(icon);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        setVisible(true);
        setLocation((int) screen[0] / 2, (int) screen[1] / 4);
        setResizable(false);
        
        pack();
    }
    
    public void runScene(JPanel scene) {
        destroyScene();
        
        rootPanel.setBackground(scene.getBackground());
        rootPanel.add(scene);
        rootPane.revalidate();
        rootPanel.repaint();
    }
     
    private void destroyScene() {
        rootPanel.removeAll();
    }
    
    private double[] calculateScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screen.getWidth() * 0.5483;
        double height = screen.getHeight() * 0.727;
        
        return new double[]{width, height};
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getInstance().runScene(new MenuScene());
            }
        });
    }
}
