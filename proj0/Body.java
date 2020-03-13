import java.lang.Math;

public class Body{

	public double xxPos, yyPos, xxVel, yyVel, mass;
	public String imgFileName;
	public static double gravitationalConstant = 6.67e-11;

	public Body(double xP, double yP, double xV, 
					double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		return Math.sqrt((xxPos - b.xxPos) * (xxPos - b.xxPos) 
							+ (yyPos - b.yyPos) * (yyPos - b.yyPos));
	}

	public double calcForceExertedBy(Body b) {
		return gravitationalConstant * mass * b.mass 
					/ (calcDistance(b) * calcDistance(b));
	}

	public double calcForceExertedByX(Body b) {
		return calcForceExertedBy(b) * (xxPos - b.xxPos) / calcDistance(b);
	}

	public double calcForceExertedByY(Body b) {
		return calcForceExertedBy(b) * (yyPos - b.yyPos) / calcDistance(b);
	}

	public double calcNetForceExertedByX(Body[] bodys) {
		double netForce = 0;
		for (Body b : bodys){
			if (b.equals(this) == false){
				netForce += calcForceExertedByX(b);
			}
		}
		return netForce;
	}

	public double calcNetForceExertedByY(Body[] bodys) {
		double netForce = 0;
		for (Body b : bodys){
			if (b.equals(this) == false){
				netForce += calcForceExertedByY(b);
			}
		}
		return netForce;
	}

	public void update(double dt, double fX, double fY) {
		double aNetX = fX / mass;
		double aNetY = fY / mass;
		xxVel += dt * aNetX;
		yyVel += dt * aNetY;
		xxPos += dt * xxVel;
		yyPos += dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
	}

}