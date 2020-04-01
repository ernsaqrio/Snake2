
package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;



public class Scheduler {
    
    private GameScene instanceGame;
    
    private ActionListener[] actions = {
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueSpeed(instanceGame.getPlay().getScore());
                startBonus();
                instanceGame.getPlay().moveSnake("Left");  
            }
        },
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueSpeed(instanceGame.getPlay().getScore());
                startBonus();
                instanceGame.getPlay().moveSnake("Right");
            }
        },
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueSpeed(instanceGame.getPlay().getScore());
                startBonus();
                instanceGame.getPlay().moveSnake("Up");
            }
        },
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueSpeed(instanceGame.getPlay().getScore());
                startBonus();
                instanceGame.getPlay().moveSnake("Down");
            }
        }
    };
    
    private Timer timerBonus;
    private Timer[] timerMotion;
    private int timeBonus, goal, delay, currentMotion;
    
    public Scheduler(GameScene instanceGame) {
        this.instanceGame = instanceGame;
        currentMotion = 0;
        delay = 500;
        goal = 100;
        timeBonus = (900 * 5); 
        timerMotion = new Timer[actions.length];
        
        createMotion();
        createBonus();
    }
    
    
    public void valueSpeed(int x) {
        if(x  >= goal && delay >= 100) {
            increaseSpeed();
            goal+=x;
        }
    }
    
    
    public void actuate(int e) {
        if (timerMotion[currentMotion].isRunning()) {
            timerMotion[currentMotion].stop();
            currentMotion = e;
            timerMotion[currentMotion].start();
        } else {
            currentMotion = e;
            timerMotion[currentMotion].start();
        }
    }
    
    private void createBonus() {
        timerBonus = new Timer(70, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                timeBonus -= timerBonus.getDelay();             
                instanceGame.changeColorPanel();
                
                if (!(instanceGame.getPlay().getBonus()) || timeBonus <= 0) {
                    instanceGame.getPlay().setBonus(false);
                    instanceGame.getPlay().deleteBonus();
                    instanceGame.restoreColorPanel();
                    timeBonus = (900 * 5);
                    timerBonus.stop();                   
                }
            }
        });
    }
    
    public void startBonus() {
        if (instanceGame.getPlay().getBonus() && !timerBonus.isRunning()) 
            timerBonus.start();      
    }
    
    private void stopBonus() {
        if (timerBonus.isRunning())
            timerBonus.stop();
    }
    
    private void createMotion() {
        for (int i = 0; i < timerMotion.length; i++) 
            timerMotion[i] = new Timer(delay, actions[i]);
    }
    public void startMotion(int t) {
        if (!timerMotion[t].isRunning()) 
            timerMotion[t].start();      
    }
    
    public void stopMotion(int t) {
        if (timerMotion[t].isRunning()) 
            timerMotion[t].stop();      
    }
    
    public void stopAllMotions() {
        for (Timer motion : timerMotion) 
            if (motion.isRunning()) 
                motion.stop();           
    }
    
    public void increaseSpeed() {
        delay-=80;
        
        for (Timer motion : timerMotion) 
            motion.setDelay(delay);       
    }
    
    public void restoreSpeed() {
        delay = 500;
        
        for (Timer motion : timerMotion) 
            motion.setDelay(delay);
    }
    
    public void stopAllTimers() {
        stopAllMotions();
        stopBonus();
    }
    
    public int getSpeed() {
        return delay;
    }
    
}
