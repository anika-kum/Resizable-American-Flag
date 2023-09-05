	// Flag starter kit
	
	/*
	 * Anika K.
	 * Mia Y.
	 */

	//NOTE: Instead of casting to int every time I need an int instead of a double, I figured it would be more accurate 
	// if I used Math.round, it would be more precise. However, I had to cast to a long before using it in order to get an integer.
	import java.awt.Color;
	import java.awt.Graphics;
	
	import javax.swing.JApplet;
	
	public class Flag extends JApplet {
		private final int STRIPES = 13;
	
		// SCALE FACTORS (A through L)
		//
		// Note: Constants in Java should always be ALL_CAPS, even
		// if we are using single letters to represent them
		//
		// NOTE 2: Do not delete or change the names of any of the
		// variables given here
		//
		// Set the constants to exactly what is specified in the documentation
		// REMEMBER: These are scale factors.  They are not numbers of pixels.
		// You will use these and the width and height of the Applet to figure
		// out how to draw the parts of the flag (stripes, stars, field).
		
		private final double A = 1.0;  // Hoist (height) of flag
		private final double B = 1.9;  // Fly (width) of flag
		private final double C = 0.5385;  // Hoist of Union
		private final double D = 0.76;  // Fly of Union
		private final double E = 0.054;  // See flag specification
		private final double F = 0.054;  // See flag specification
		private final double G = 0.063;  // See flag specification
		private final double H = 0.063;  // See flag specification
		private final double K = 0.0616;  // Diameter of star
		private final double L = 0.0769;  // Width of stripe
	
		// You will need to set values for these in paint()
		private double flag_width = 760;   // width of flag in pixels
		private double flag_height =400;  // height of flag in pixels
		private double stripe_height; // height of an individual stripe in pixels
		private double union_width; // width of union in pixels
		private double union_height; // height of union in pixels
		
		//the actual pixel amounts, which are also set in paint()
		
		private double G_PIXELS;
		private double H_PIXELS;
		private double E_PIXELS;
		private double F_PIXELS;
		private double r;
		
		// init() will automatically be called when an applet is run
		public void init() {
			// Choice of width = 1.9 * height to start off
			// 760 : 400 is ratio of FLY : HOIST
			setSize(760, 400);
			repaint();
		}
		
		// paint() will be called every time a resizing of an applet occurs
		public void paint(Graphics g) {
			
			//The logic behind the following code is to choose one of the new dimensions received from resizing
			//the Applet, and set the other one based on that.
			
			int curHeight = this.getHeight();
			int curWidth = this.getWidth();
	
			if (curHeight * B < curWidth * A) {  //the height is smaller than the width, so set the width based on that.
				flag_height = curHeight;
				flag_width = curHeight * B / A;
			}
			
			else { //the width is smaller than or equal to the height, so set the height based on that.
				flag_width = curWidth;
				flag_height = curWidth * A / B;
			} 
			
			int ratio = Math.round((float)(flag_width / B));
			//just used for the next lines of code (setting union_width and height)
			
			union_width = ratio*D;
			union_height = ratio*C;
			
			G_PIXELS = (G / B) * flag_width;
			H_PIXELS = (H / B) * flag_width;
			E_PIXELS = (E / A) * flag_height;
			F_PIXELS = (F / A) * flag_height;
			r = K/2 / B * flag_width; //radius of stars
			
			drawBackground(g);
			drawField(g);
			drawStripes(g);
			drawUnion(g);
			drawStars(g);
		}
	
		private void drawBackground(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		public void drawStripes(Graphics g) {
			g.setColor(Color.RED);
			for (int i=1; i<=STRIPES; i+=2) {
				g.fillRect(0, Math.round((float)((i-1)*flag_height/(STRIPES))), Math.round((float) flag_width), Math.round((float)(flag_height/STRIPES)));
			}
		}
	
		public void drawField(Graphics g) {
			// draw outline of rectangle
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, Math.round((float) flag_width), Math.round((float) flag_height));
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Math.round((float) flag_width), Math.round((float) flag_height));
		}
		
		public void drawUnion(Graphics g) { //our added method instead of combining it with drawField
			//draw Union
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, (Math.round((float) union_width)), Math.round((float) union_height));
		}
		
		public void drawStars(Graphics g) {
			
			int innerHeight = Math.round((float) (r * Math.sin(Math.toRadians(360-18)))); //perpendicular from inner points to x-axis
			// (called "h" on the board in class)
			g.setColor(Color.WHITE);
			
			//System.out.println("G_PIXELS: " + G_PIXELS + " e:" + e + " k: " + k);
			for (int row = 0; row<9; row++) {
				int[] xPoints = new int[10];
				int[] yPoints = new int[10];
				if (row % 2 == 0) {
					for (int column = 0; column<6; column++) { //six stars
						for (int i=54; i<360; i+=72) { //inner points
							xPoints[(i-18)/36] = Math.round((float)(Math.cos(Math.toRadians(360-i))*(innerHeight/Math.sin(Math.toRadians(360-54))) + G_PIXELS + 2*H_PIXELS*column));
							yPoints[(i-18)/36] = Math.round((float)(Math.sin(Math.toRadians(360-i))*(innerHeight/Math.sin(Math.toRadians(360-54))) + E_PIXELS + F_PIXELS*row));
						}
				
						for (int i=18; i<360; i+=72) { //outer points
							xPoints[(i-18)/36] = Math.round((float)(Math.cos(Math.toRadians(360-i))*r + G_PIXELS + 2*H_PIXELS*column));
							yPoints[(i-18)/36] = Math.round((float)(Math.sin(Math.toRadians(360-i))*r + E_PIXELS + F_PIXELS*row));
						}
						g.fillPolygon(xPoints, yPoints, 10);
					}
				}
			
				else {
					for (int column = 0; column<5; column++) { //five stars
						for (int i=54; i<360; i+=72) { //inner points
							xPoints[(i-18)/36] = Math.round((float)(Math.cos(Math.toRadians(360-i))*(innerHeight/Math.sin(Math.toRadians(360-54))) + G_PIXELS + 2*H_PIXELS*column + H_PIXELS));
							yPoints[(i-18)/36] = Math.round((float)(Math.sin(Math.toRadians(360-i))*(innerHeight/Math.sin(Math.toRadians(360-54))) + E_PIXELS + F_PIXELS*row));
						}
				
						for (int i=18; i<360; i+=72) { //outer points
							xPoints[(i-18)/36] = Math.round((float)(Math.cos(Math.toRadians(360-i))*r + G_PIXELS + H_PIXELS*column*2 + H_PIXELS));
							yPoints[(i-18)/36] = Math.round((float)(Math.sin(Math.toRadians(360-i))*r + E_PIXELS + F_PIXELS*row));
						}
						g.fillPolygon(xPoints, yPoints, 10);
					}
				}
			
			}
		}
	}
	 