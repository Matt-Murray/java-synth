import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.BiquadFilter;
import net.beadsproject.beads.ugens.Envelope;

//Synthesizer

public class Key_Listener extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	public static AudioContext ac = new AudioContext();
	WavePlayer wp = new WavePlayer(ac, 440.0f, Buffer.SINE);
	Glide gainGlide = new Glide(ac, 0.9f, 1500.0f);
	Gain g = new Gain(ac, 1, gainGlide);
    JLabel label;
    WavePlayer kick, snareNoise, snareTone;
	Envelope kickGainEnvelope, snareGainEnvelope;
	Gain kickGain, snareGain;
	BiquadFilter kickFilter, snareFilter;

    public Key_Listener(String s) {
    	
    	//set up window with key listener
        super(s);
        JPanel p = new JPanel();
        label = new JLabel("Key Listener!");
        p.add(label);
        add(p);
        addKeyListener(this);
        setSize(200, 100);
        setVisible(true);
        
        //connect gain to input and main out
        g.addInput(wp);
        ac.out.addInput(g);
        
        //start synthesizer and set pitch to 0Hz
    	ac.start();
    	wp.setFrequency(0.0f);
    	
    	//Drum Machine
		
		//set up envelope for kick gain
		kickGainEnvelope = new Envelope(ac, 0.0f);
		
		//construct kick WavePlayer
		kick = new WavePlayer(ac, 100.0f, Buffer.SINE);
		
		//set up filters
		kickFilter = new BiquadFilter(ac, BiquadFilter.BESSEL_LP, 500.0f, 1.0f);
		kickFilter.addInput(kick);
		
		//set up the gain
		kickGain = new Gain(ac, 1, kickGainEnvelope);
		kickGain.addInput(kickFilter);
		
		//connect gain to main out
		ac.out.addInput(kickGain);
		
		//set up snare envelope
		snareGainEnvelope = new Envelope(ac, 0.0f);
		
		//set up snare WavePlayers
		snareNoise = new WavePlayer(ac, 1.0f, Buffer.NOISE);
		snareTone = new WavePlayer(ac, 200.0f, Buffer.SINE);
		
		//set up filters
		snareFilter = new BiquadFilter(ac, BiquadFilter.BP_SKIRT, 2500.0f, 1.0f);
		snareFilter.addInput(snareNoise);
		snareFilter.addInput(snareTone);
		
		//set up gain
		snareGain = new Gain(ac, 1, snareGainEnvelope);
		snareGain.addInput(snareFilter);
		
		//connect gain to main out
		ac.out.addInput(snareGain);
    }
  
    @Override
    public void keyTyped(KeyEvent e) {}
   
    
  //set up keyboard input
  		@Override
  	    public void keyPressed(KeyEvent e) {

  	        if (e.getKeyCode() == KeyEvent.VK_0) {
  	          // attack segment
  	          kickGainEnvelope.addSegment(0.5f, 2.0f);
  	          // decay segment
  	          kickGainEnvelope.addSegment(0.2f, 5.0f);
  	          // release segment
  	          kickGainEnvelope.addSegment(0.0f, 50.0f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_4) {
  	          // attack segment
  	          snareGainEnvelope.addSegment(0.5f, 2.00f);
  	          // decay segment
  	          snareGainEnvelope.addSegment(0.2f, 8.0f);
  	          // release segment
  	          snareGainEnvelope.addSegment(0.0f, 80.0f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_C) {
  	        	wp.setFrequency(329.63f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_Z) {
  	        	wp.setFrequency(261.63f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_X) {
  	        	wp.setFrequency(293.66f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_C) {
  	        	wp.setFrequency(329.63f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_V) {
  	        	wp.setFrequency(349.23f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_B) {
  	        	wp.setFrequency(392.00f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_N) {
  	        	wp.setFrequency(440.00f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_M) {
  	        	wp.setFrequency(493.88f);
  	        }
  	        
  	        if (e.getKeyCode() == KeyEvent.VK_COMMA) {
  	        	wp.setFrequency(523.25f);
  	        }
  		}

    @Override
    public void keyReleased(KeyEvent e) {
            wp.setFrequency(0.0f);
        }
        

    public static void main(String[] args) {
        new Key_Listener("Key Listener Tester");
    }

}


